/*
NAME: Catherine
DATE: 11/13/2020
DESCRIPTION:
CD Manager -> allows you to look at your cds and change them, as well as their songs.
Driver menu, has the control of the main menu and the sub-menus. Methods cover most of submenu 1's options.
Major variables include: static cdList and static cdNumber; there is only one for the entire class to share.
NOTE:
To help navigate the methods, internal comments describe which of the submenu options this method is involved with 
ex: 1.2 -> submenu 1, option 2  
 */

import java.util.*;
import java.io.*;

public class Driver {
	static ArrayList<CD> cdList = new ArrayList<CD>();
	static int cdNumber = 0; // submenu 2

	// parameters: menu option, input reader
	// description: displays text for options
	// returns: submenu choice
	public static int displayMenu(int menuNum, BufferedReader stdIn) throws IOException {

		if (menuNum == 0) {
			System.out.println("----------  MAIN MENU  -----------");
			System.out.println("1) Accessing your list of CDs");
			System.out.println("2) Accessing within a particular CD");
			System.out.println("3) Exit");
			System.out.println("----------------------------------");
		} else if (menuNum == 1) {
			System.out.println("\n---------  SUB-MENU #1  ----------");
			System.out.println("1) Display all of your CDs");
			System.out.println("2) Display info on a particular CD");
			System.out.println("3) Add a CD");
			System.out.println("4) Remove a CD");
			System.out.println("5) Copy a CD");
			System.out.println("6) Create a sub-CD");
			System.out.println("7) List songs in common between two CDs");
			System.out.println("8) Return back to main menu.");
			System.out.println("----------------------------------");
		} else {
			System.out.println("\n---------  SUB-MENU #2  ----------");
			System.out.println("1) Display all songs (in the last sorted order) ");
			System.out.println("2) Display info on a particular song ");
			System.out.println("3) Add song");
			System.out.println("4) Remove Song (4 options)");
			System.out.println("5) Sort songs (3 options)");
			System.out.println("6) Return back to main menu");
			System.out.println("----------------------------------");
		}

		System.out.print("\n\nPlease enter your choice:  ");

		int choice = Integer.parseInt(stdIn.readLine());

		return choice;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		int mainMenuChoice, subMenuChoice;

		do { // RUN AGAIN. Terminates when asked.
			mainMenuChoice = displayMenu(0, stdIn);

			/*
			 * MainMenu Option 1: submenuchoices must be within boundaries; if it is option
			 * 8, exit to main menu. If no CDs are added, will return you to beginning of
			 * submenu.
			 */

			if (mainMenuChoice == 1) {
				do {
					try {
						subMenuChoice = displayMenu(1, stdIn);

						if (subMenuChoice <= 0 || subMenuChoice > 8)
							throw new NumberFormatException();

						if (subMenuChoice == 8) {
							break;
						}

						if (cdList.size() == 0 && subMenuChoice != 3)
							throw new IOException();
						menuOneHandler(subMenuChoice, stdIn);
					} catch (NumberFormatException e) {
						System.out.println("Invalid, enter again.");
					} catch (IOException e) {
						System.out.println("You have no CDS. PLEASE ADD CDS.");
					}
				} while (true);

			} else if (mainMenuChoice == 2) {
				/*
				 * MainMenu Option 2: if no cds, returns you to main menu. else: asks for a cd's
				 * number first. submenuchoices must be within boundaries; if it is option 6,
				 * exit to main menu
				 */
				if (cdList.size() != 0) {
					boolean cdValid = false;

					do {

						while (cdValid == false) {
							System.out.println("What is the particular CD's number: ");

							try {
								cdNumber = Integer.parseInt(stdIn.readLine());

								if (cdNumber <= 0 || cdNumber > cdList.size())
									throw new NumberFormatException();
								else {
									cdValid = true;
									break;
								}

							} catch (NumberFormatException e) {
								System.out.println("Invalid. Re-enter.");
							}
						}

						subMenuChoice = displayMenu(2, stdIn);
						if (subMenuChoice <= 0 || subMenuChoice > 6)
							throw new NumberFormatException();
						if (subMenuChoice == 6) {
							break;
						}
						menuTwoHandler(subMenuChoice, cdNumber, stdIn);

					} while (true);

				} else {
					System.out.println("Sorry, no CDs. Return to main menu.");
				}
			} else if (mainMenuChoice == 3) {
				System.exit(0);
			}

		} while (true);

	}

	// Description: Handles all options in first submenu.
	// Parameters: chosen option -> subMenuChoice
	// Return: none needed.
	public static void menuOneHandler(int subMenuChoice, BufferedReader stdIn) {
		// LISTS SONGS.
		if (subMenuChoice == 1) {
			System.out.print(listCDTitles());

			// DISPLAYS INFORMATION OF THAT SONG -> cd.toString()
		} else if (subMenuChoice == 2) {
			System.out.println((cdList.get(getNumber(stdIn, 1) - 1)).toString());

			// ADD CD ->
		} else if (subMenuChoice == 3) {
			do {
				System.out.print("File name: ");
				try { // IOEXCEPTION
					String fileName = stdIn.readLine();
					BufferedReader fileIn = new BufferedReader(new FileReader(fileName));
					String cdTitle = fileIn.readLine(); // CD TITLE
					int numSongs = Integer.parseInt(fileIn.readLine()); // NUMBER OF CDS
					cdList.add(new CD(cdTitle, numSongs));

					// ADDS SONGS
					for (int i = 0; i < numSongs; i++) {
						cdList.get(cdList.size() - 1).addSong(new Song(fileIn.readLine(), fileIn.readLine(),
								fileIn.readLine(), Integer.parseInt(fileIn.readLine()), new Time(fileIn.readLine())));
					}

					break;

				} catch (NumberFormatException | IOException e) {
					System.out.println("Numformat, IO");
				}
			} while (true);

			// REMOVE a CD
		} else if (subMenuChoice == 4) {
			System.out.print(listCDTitles());
			System.out.println("What CD would you like to remove?");
			cdList.remove(getNumber(stdIn, 1) - 1);

			// COPIES/MAKES a NEW CD, ADDS TO LIST. -> Different title
		} else if (subMenuChoice == 5) {
			System.out.print(listCDTitles());
			System.out.println("What CD would you like to copy?");
			int cdNumber = getNumber(stdIn, 1) - 1;
			cdList.add(new CD(cdList.get(cdNumber)));

			// MAKES A SUB-CD, ADDS TO LIST. REQUIRES START, END INDEX.
		} else if (subMenuChoice == 6) {
			System.out.println("What CD would you like to make a subCD from?");
			int cdNumber = getNumber(stdIn, 1) - 1;

			int startOrEnd = 0; // DECIDES if this is start or endindex.
			int start = 0, end = 0;

			for (int i = 0; i <= 1; i++) {
				do {
					if (startOrEnd == 0)
						System.out.println("What's the START INDEX of the CD? (Beginning from 0) ");
					else
						System.out.println("What's the END INDEX of the CD? (INCLUSIVE) ");

					try {
						if (startOrEnd == 0) {
							start = Integer.parseInt(stdIn.readLine());
							if (start < 0)
								throw new NumberFormatException();

						} else {
							end = Integer.parseInt(stdIn.readLine());
							if (end >= (cdList.get(cdNumber)).getNumSongs())
								throw new NumberFormatException();
						}
						startOrEnd++;
						break;

					} catch (NumberFormatException | IOException e) {
						System.out.println("Invalid index.");
					}
				} while (true);
			}

			cdList.add(new CD(cdList.get(cdNumber), start, end));

			// PRINTS STRING LIST OF TITLES OF COMMON SONGS
		} else if (subMenuChoice == 7) {
			System.out.print(listCDTitles());
			System.out.println("First CD?");
			int cdNumber1 = getNumber(stdIn, 1) - 1;
			System.out.println("Second CD?");
			int cdNumber2 = getNumber(stdIn, 1) - 1;

			if (cdNumber1 == cdNumber2) {
				cdList.get(cdNumber1).listSongTitles();
			} else {
				ArrayList<String> commonList = new ArrayList<String>();
				Collections.sort(cdList.get(cdNumber1).getSongList());
				Collections.sort(cdList.get(cdNumber2).getSongList());

				// ITERATES THROUGH SONGS OF THE LISTS.
				int index = 0;
				for (int i = 0; i < cdList.get(cdNumber1).getSongList().size(); i++) {
					for (int q = index; q < cdList.get(cdNumber2).getSongList().size(); q++) {
						if (cdList.get(cdNumber1).getSongList().get(i)
								.equals(cdList.get(cdNumber2).getSongList().get(q))) {
							commonList.add(cdList.get(cdNumber1).getSongList().get(i).getTitle());
							index = q;
							break;
						}
					}
				}
				for (int i = 0; i < commonList.size(); i++) {
					System.out.println(commonList.get(i));
				}
			}

		}

	}

	// Description: Handles all options in second submenu.
	// Parameters: chosen option -> subMenuChoice
	// Return: none needed.
	public static void menuTwoHandler(int subMenuChoice, int cdNumber, BufferedReader stdIn) {
		CD cd = cdList.get(cdNumber - 1); //object, so all pointers will change.
		//DISPLAY ALL SONGS IN LAST SORTED ORDER
		if (subMenuChoice == 1) {
			System.out.print((cd).listSongTitles() + "\n");
		//PICKS ONE SONG, DISPLAYS ALL 5 CHARACTERISTICS
		} else if (subMenuChoice == 2) {
			System.out.println("What song do you want to look at?");
			int number = getNumber(stdIn, 2) - 1;
			System.out.println((cd).getSongList().get(number).toString());
		//ADD SONG
		} else if (subMenuChoice == 3) {
			cd.addSong(stdIn);
		//REMOVE A SONG -> 4 options
		} else if (subMenuChoice == 4) {	//I left 4 in main in order to use getNumber();
			
			System.out.print((cd).listSongTitles() + "\n");
			System.out.println(
					"Remove a song \n'1' -> by song number\n'2' -> by song title \n'3' -> the first song in the list \n'4' -> the last song in the list");
			int x = checkChoice(stdIn, 4);
			
			//removed:
			cd.decNumSongs();
			if (x == 3) {
				cd.removeSongs(0);
			} else if (x == 4) {
				cd.removeSongs(cd.getSongList().size() - 1);
			} else if (x == 1) {
				cd.removeSongs(getNumber(stdIn, 2)-1);
			} else {
				cd.removeSongs(stdIn);
			}
		} else if (subMenuChoice == 5) {
			System.out.println("Sort by\n'1' -> title\n'2' -> artist\n'3' -> time");
			int x = checkChoice(stdIn, 5);
			
			if (x == 1)
				Collections.sort(cd.getSongList());
			else if (x == 2)
				Collections.sort(cd.getSongList(), new SortByArtist());
			else
				System.out.println("Sorted in ascending order of time.");
				Collections.sort(cd.getSongList(), new SortByTime());
		}
	}
	
	// 1.1, 1.4, 1.5, 1.7
	// Parameters: NONE BECAUSE STATIC/GLOBAL SCOPE VARIABLE
	// Description: Prints all the cd titles
	// Return: String
	public static String listCDTitles() {
			String titles = "";
			for (int i = 0; i < cdList.size(); i++) {
				titles += (i + 1) + ") " + (cdList.get(i)).getTitle() + "\n";
			}
			return titles;
		}

// The next 2 methods, checkcHoice and getNumber are very similar.
	//2.4, 2.5
	// Parameters: inputreader, and a variable identitfying if this is case for 2.4 or 2.5
	// Description: Checks validity of choice, reasks if wrong.
	// Return: chosen (valid) option
	public static int checkChoice (BufferedReader stdIn, int choice) {
		while (true) {
			try {
				int x = Integer.parseInt(stdIn.readLine());
				if (choice == 4)
					if (x < 1 || x > 4) {
						throw new NumberFormatException();
					} 		
				else {
					if (x < 1 || x > 3) {
						throw new NumberFormatException();
					} 
				}

				return x;
			} catch (NumberFormatException | IOException e) {
				System.out.println("Invalid, re-enter.");
				// System.out.println("Numformat, IO");
			}
		}
	}
	
	// 1.1, 1.4, 1.5, 1.7, 2.2, 2.4
	// Parameters: input
	// Description: gets and checks if cd/song nubmer is valid
	// Return: the cd or song number
	public static int getNumber(BufferedReader stdIn, int submenuNum) { // num format
		while (true) {
			try {
				if (submenuNum == 1)
					System.out.println("CD #: ");
				else if (submenuNum == 2)
					System.out.println("Song #: ");

				int x = Integer.parseInt(stdIn.readLine());

				if (submenuNum == 1) {
					if (x <= 0 || x > cdList.size())
						throw new NumberFormatException();
				} else {

					if (x <= 0 || x > cdList.get(cdNumber - 1).getSongList().size())
						throw new NumberFormatException();
				}

				return x;

			} catch (NumberFormatException | IOException e) {
				System.out.println("Invalid, re-enter.");
				// System.out.println("Numformat, IO");
			}
		}

	}

}
