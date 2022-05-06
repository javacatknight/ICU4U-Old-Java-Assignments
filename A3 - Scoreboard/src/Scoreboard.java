
/*
 * Name: Catherine
 * Date: 10/27 (Late)
 * Description: Scoreboard stores the information of players (name, score, power). Scoreboard provides two functions.
 * a) Find (1) player by name
 * b) Find all players with power
 * 
 * Used: comparable interface, comparator interface, binary search, array sort.
 */
import java.util.*;
import java.io.*;

public class Scoreboard {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Scanner inConsole = new Scanner(System.in);
		ArrayList<Player> list = initializing(); // Initializes the player list.

		int found = 0;
		String choose = "";
		while (true) {
			System.out.println(
					"If you are looking for a player by NAME, type '1'. If you are looking for players by POWER, type '2'. If you want to exit, type '3'");
			choose = inConsole.nextLine();
			if (choose.equals("1")) { // FIND PLAYER BY NAME
				// Search input:
				while (true) {
					System.out.print("Enter a name or type 'EXIT'");
					String input = inConsole.nextLine();
					found = 0;
					if (input.equalsIgnoreCase("EXIT")) {
						break;
					}

					// Find name
					try {
						Collections.sort(list, new sortByName());
						found = Collections.binarySearch(list, new Player(0, input.toLowerCase(), null),
								new sortByName());
					} catch (NumberFormatException e) {
						System.out.print("Flag");
					}
					if (found >= 0) {
						System.out.println(list.get(found)); // supposed to auto call toString
					} else {
						System.out.println("NOT FOUND");
					}
				}

			} else if (choose.equals("2")) { // FIND PLAYERS BY POWER
				while (true) {
					// Search input:
					System.out.print("Enter a power or type 'EXIT'");
					found = 0;
					String input = inConsole.nextLine();
					if (input.equalsIgnoreCase("EXIT")) {
						break;
					}
				//	input = input.toLowerCase();

					// Find power
					try {
						Collections.sort(list, new sortByPower());
						found = Collections.binarySearch(list, new Player(0, null, input.toLowerCase()),
								new sortByPower());
					} catch (NumberFormatException e) {
						System.out.print("Flag");
					}

					if (found < 0) {
						System.out.println("NOT FOUND");
					} else {
						// find leftmost and rightmost that share power.
						// must keep the original list in case person wants to run program again. copy
						// list.
						// clear all other elements from list. search by alpha order and print.
						ArrayList<Player> samePowerList = new ArrayList<Player>(list);
						int left = found, right = found;
						String powerName = samePowerList.get(found).getPower();

						// binary search only finds one occurrence, not all. check left and right.
						while (left - 1 >= 0) {
							if (samePowerList.get(left - 1).getPower().equalsIgnoreCase(powerName)) {
								left--;
							} else {
								break;
							}
						}
						while (right + 1 < samePowerList.size()) {

							if (samePowerList.get(right + 1).getPower().equalsIgnoreCase(powerName)) {
								right++;
							} else {
								break;
							}
						}

						for (int i = 0; i < left; i++) {
							samePowerList.remove(i);
						}

						//
						if (right != samePowerList.size() - 1) { //if right isn't the last element
							for (int i = right+1; i < samePowerList.size()-1; i++) {
								samePowerList.remove(i);
							}
						}

						Collections.sort(samePowerList, new sortByName());
						for (int i = 0; i < samePowerList.size(); i++) {
							System.out.println(samePowerList.get(i) + "\n");
						}
					}
				}

			} else if (choose.equals("3")) {
				inConsole.close();
				System.exit(0);
			}

		}

	}

	// parameters (none)
	// return: list of players
	// description: reads input in, initializes score, name, power, assigns ranks,
	// adds this player instance to the list.
	public static ArrayList<Player> initializing() throws FileNotFoundException, IOException {
		// READS IN INPUT FROM FILE.
		BufferedReader in = new BufferedReader(new FileReader("yes.txt"));
		String s = "";
		ArrayList<Player> list = new ArrayList<Player>();

		// SCORE, NAME, POWER are INITIALIZED.
		// PLAYER INSTANCES are ADDED to the LIST
		while ((s = in.readLine()) != null) {
			try {
				StringTokenizer st = new StringTokenizer(s); // Improper Inputs - HANDLED

				int length = st.countTokens();
				if (length >= 3) { // Improper Inputs - HANDLED ex: what if there is no score?
									// numberformatexception
					int score = Integer.parseInt(st.nextToken());
					if (score < 0)
						throw new NumberFormatException();
					length--;

					String name = "";
					while (length > 1) {
						name += st.nextToken() + " ";
						length--;
					}
					name = name.trim();
					list.add(new Player(score, name, st.nextToken()));
				}
			} catch (NumberFormatException e) {
				System.out.print("flag:");
			}
		}
		in.close();

		// RANKING: SORT BY DESCENDING SCORE (comparable interface + compareTo method)
		// If score is the same, rank is shared between player, with the next player's
		// rank taking a gap.
		Player.setTotal(list.size());
		int lastScore = -1;
		int recurring = 0;
		int rank = 1;
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getScore() == lastScore) {
				rank--;
				do {
					recurring++;
					list.get(i).setRank(rank);
					i++;
				} while (list.get(i).getScore() == lastScore);
			}

			if (recurring > 0) {
				rank += recurring + 1;
				recurring = 0;
			}

			list.get(i).setRank(rank);
			lastScore = list.get(i).getScore();
			rank++;
		}

		return list;
	}

}
