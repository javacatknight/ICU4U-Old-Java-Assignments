//NAME: Catherine Qu
//DATE: 12/22 (LATE)
//DESCRIPTION: Simple GUI, allows files to be added and finds the top twenty most frequent words.
//Notes: Not sure it works correctly??? sometimes ctrl+f gave me very different answers, sometimes answers were close.
import java.awt.Graphics;
import java.awt.Font;
import java.util.Date;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;


import javax.swing.*;

public class Main implements ActionListener{


	
	
	JFrame frame;
	JPanel myPanel;			//main panel
	JPanel sideLeft, sideRight;	//side panels within main panel
	JTextArea display;
	JTextField textName;
	JLabel instruction;
	JButton add;
	JComboBox<String> fileDisplay;
	Vector<String> booksString = new Vector<String>();			//Legacy object, but dynamic so that when it is modified, the combo box is as well.


	//constructor/GUI
	public Main() throws FileNotFoundException {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(800, 400));
		frame.setLocation(200, 200);

		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());
		sideLeft = new JPanel();
		sideLeft.setLayout(new BoxLayout(sideLeft, BoxLayout.PAGE_AXIS));	

		// SIDELEFT PANEL - input
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

		// SIDERIGHT - output
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

	//parameters: takes in a bufferedreader per file to read from
	//description: reads in file, counts words, puts words in a hashmap (fast, no duplicates), converts to treeset (orderable -> comparable used), iterates through 20, output given to jtextarea.
	public void important(BufferedReader br) throws IOException {
		long start = System.currentTimeMillis();
		Map<String, Word> hash = new HashMap<String, Word>();	//--> string because unique + word object holds both the word + the frequency
		
//READ INPUT FROM FILE
		String line;
		while ((line = br.readLine()) != null) {
			if (line.equals("")) {
				continue;
			}

			line = " " + line.toLowerCase().trim() + " "; // added spaces make things easier
			int search = 0;									// INDEX

// APPOSTROPHES
			// jonas's, possessive +  *special case: he's, there's
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
				line = line.substring(0, search) + " " + line.substring(search + 2); // ex. see?--Posted     --> see? Posted
			}

			// REMOVE ONLY end/beginning dashes
			if (line.indexOf("-") == 1) {
				line = line.substring(2);
			}
			if ((search = line.indexOf("-")) == line.length() - 2)
				line = line.substring(0, search);

			// OTHER SYMBOLS
			StringTokenizer st = new StringTokenizer(line, "\".,?_/!*123456789()<>[]:&';+= "); // ignore all numbers, but leaves dashed wordss

			while (st.hasMoreTokens()) {
				line = st.nextToken();
				Word word = new Word(line);
				
				//WORD ALREADY EXISTS, update the frequency of word, then put the new (key,value) in to update the value
				if (hash.containsKey(line)) {
					word.addFrequency(hash.get(line).getFrequency());
				}
				hash.put(line, word);

			}
		}



		//sort your hashmap. treeset is ordered (but use comparator)
		Collection<Word>list = hash.values();
		TreeSet <Word> tree = new TreeSet <Word> (list);		
		
		
		long end = System.currentTimeMillis();
		String data = String.format("Total Time: %d milliseconds %n%n 20 Most Frequent Words %n%n %s %s%n",
				(end - start), "\tWords", "\t\tFrequency");
		
		// top twenty iterate through
		Iterator<Word> itr = tree.iterator();
		int x = 20;			//if size of file is less than 20, then iterate only as much as possible:
		if (tree.size()<20) {
			x = tree.size();
		}
		
		for (int i = 0; i < x; i++) {
			Word w = itr.next();
			data += "" + (i + 1) + ")\t";
			data += String.format("%-40s%s%n", w.getWord(), "\t" + w.getFrequency());
		}

		display.setText(data);

	}

	//description: action handler for button ("Add") and combobox ("Files") which calls the method (important)
	@Override
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		
		//button + jtextarea -> add file if legitmate filename
		if (eventName.equals("Add")) {
			String fileName = textName.getText();

			try { // if contains do not add,
				if (!booksString.contains(fileName)) {
					BufferedReader br = new BufferedReader(new FileReader(fileName));	//technically bad practice to create extra objects but checks fileName effectively
					booksString.add(fileName);
				} 
					textName.setText(" ");
				

			} catch (FileNotFoundException exception) {
				JOptionPane.showMessageDialog(frame, "Invalid file, re-enter.");
				textName.setText("");
			}

		} else if (eventName.equals("Files")) {
			//fileDisplay = (JComboBox) e.getSource();		//not sure what this line does so i omitted it...
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
