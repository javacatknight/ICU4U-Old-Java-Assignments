import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

//convert to hashmap?
public class working implements ActionListener {
	JFrame frame;
	JPanel myPanel;
	JPanel sideLeft, sideRight;
	JTextArea display;
	JTextField textName;
	JLabel instruction;
	JButton add;
	JComboBox<String> fileDisplay;
	Vector<String> booksString = new Vector<String>();

	//anthony uses a list so that it can be sorted properly

	public working() throws FileNotFoundException {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(800, 400));
		frame.setLocation(200, 200);

		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());
		sideLeft = new JPanel();
		sideLeft.setLayout(new BoxLayout(sideLeft, BoxLayout.PAGE_AXIS));

		// SIDELEFT
		textName = new JTextField();
		textName.setPreferredSize(new Dimension(100, 30));

		add = new JButton("ADD FILE");
		add.setPreferredSize(new Dimension(20, 20));
		instruction = new JLabel("Filename should include .txt extension");
		add.setActionCommand("Add");
		add.addActionListener(this);

		booksString.add("Alice.txt");
		booksString.add("Moby.txt");
		fileDisplay = new JComboBox<String>(booksString); // Interestingly, the jcombobox is mutable!


		fileDisplay.addActionListener(this);
		fileDisplay.setActionCommand("Files");

		sideLeft.add(instruction);
		sideLeft.add(textName);
		sideLeft.add(add);
		sideLeft.add(fileDisplay);
		myPanel.add(sideLeft, BorderLayout.WEST);

		// SIDERIGHT
		sideRight = new JPanel();
		sideRight.setSize(200, 200);
		display = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(display);
		sideRight.add(scrollPane);
		myPanel.add(scrollPane, BorderLayout.CENTER);

		frame.add(myPanel);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws FileNotFoundException {
		new Main();

	}

	public void important(BufferedReader br) throws IOException {
		long start = System.currentTimeMillis();

		Map<Word, String> tree = new TreeMap<Word, String>(Collections.reverseOrder());
		String line;

		while ((line = br.readLine()) != null) {
			if (line.equals("")) {
				continue;
			}

			line = " " + line.toLowerCase().trim() + " "; // added spaces make things easier
			int search = 0;

// APPOSTROPHES
			// jonas's, catherine's, *special case: he's, there's
			while ((search = line.indexOf("'s")) != -1) {
				line = line.substring(0, search) + line.substring(search + 2);
			}
			// wasn't, isn't, hasn't, won't
			while ((search = line.indexOf("n't")) != -1) {
				line = line.substring(0, search) + line.substring(search + 3);
			}

			// i'm, you're, you've, i'll
			while (line.indexOf("'") != -1) {
				if ((search = line.indexOf("'m")) != -1) {
					line = line.substring(0, search) + line.substring(search + 2);
					continue;
				} else if ((search = line.indexOf("'re")) != -1) {
					line = line.substring(0, search) + line.substring(search + 3);
					continue;
				} else if ((search = line.indexOf("'ve")) != -1) {
					line = line.substring(0, search) + line.substring(search + 3);
					continue;
				} else if ((search = line.indexOf("'ll")) != -1) {
					line = line.substring(0, search) + line.substring(search + 3);
					continue;
				}

				// jonas' 'twas - remove beginning/ending... apostrophe
				if ((search = line.indexOf("'")) != -1)
					line = line.substring(0, search) + line.substring(search + 1);
			}

// DASHES
			// double dash
			while ((search = line.indexOf("--")) != -1) {
				line = line.substring(0, search) + " " + line.substring(search + 2); // ex. see?--Posted --> see? Posted
			}

			// REMOVE ONLY end/beginning dashes
			if (line.indexOf("-") == 1) {
				line = line.substring(2);
			}
			if ((search = line.indexOf("-")) == line.length() - 2)
				line = line.substring(0, search);

			// OTHER SYMBOLS
			StringTokenizer st = new StringTokenizer(line, "\".,?_/!*123456789()<>[]:; "); // ignore all numbers, - is

			while (st.hasMoreTokens()) {
				line = st.nextToken();
				Word word = new Word(line);
				tree.put(word, word.getWord());
				//System.out.print(word.getWord() + word.getFrequency());
			}
		}

		long end = System.currentTimeMillis();
		String data = String.format("Total Time: %d milliseconds %n%n 20 Most Frequent Words %n%n %s %s%n",
				(end - start), "\tWords", "\t\tFrequency");

		// top twenty iterate through
		Iterator<Map.Entry<Word, String>> itr = tree.entrySet().iterator();
		int x = 20;
		if (tree.size()<20) {
			x = tree.size();
		}
		
		for (int i = 0; i < x; i++) {
			Map.Entry<Word, String> entry = itr.next();
			data += "" + (i + 1) + ")\t";
			data += String.format("%-40s%s%n", entry.getKey().getWord(), "\t" + entry.getKey().getFrequency());
		}

		display.setText(data);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		if (eventName.equals("Add")) {
			String fileName = textName.getText();

			
			try { // if contains do not add,
				if (!booksString.contains(fileName)) {
					BufferedReader br = new BufferedReader(new FileReader(fileName));	//technically bad practice but checks fileName effectively
					booksString.add(fileName);
				} 
					textName.setText(" ");
				

			} catch (FileNotFoundException exception) {
				JOptionPane.showMessageDialog(frame, "Invalid file, re-enter.");
				textName.setText("");
			}

		} else if (eventName.equals("Files")) {
			//fileDisplay = (JComboBox) e.getSource();
			
			String chosenFile = (String) fileDisplay.getSelectedItem();
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(chosenFile));
				try {
					important(br);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e2) {

			}
			

		}

	}

}
