//Description: program shows (left to right) what the deck would look like from top to bottom in order for all the cards to be shown face up from 1 to n
//Deque used.

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class C extends JPanel implements ActionListener {
	static JFrame frame;
	static JPanel myPanel;
	JButton deckSize;
	JButton exit;
	JTextField input;

	static JPanel display;

	public C() {
		// construct jpanel with jbuttons + field
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(400, 400));
		frame.setLocation(200, 200);

		myPanel = new JPanel();
		myPanel.setLayout(new GridLayout(4, 4));

		input = new JTextField();
		deckSize = new JButton("Deck Size");
		deckSize.addActionListener(this);
		exit = new JButton("Exit");
		exit.addActionListener(new Exit());

		myPanel.add(input);
		myPanel.add(deckSize);
		myPanel.add(exit);

		frame.add(myPanel);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		new C();

	}

	public void actionPerformed(ActionEvent e) {
		// error check
		try {
			int x = Integer.parseInt(input.getText());
			if (x < 1 || x > 25) {
				throw new NumberFormatException();
			}
			work(x);

		} catch (NumberFormatException E) {
			input.setText(null); // old text is deleted
			System.out.println("Try again.");
		}

	}

	public void work(int x) {
		Deque<Integer> q = new LinkedList<Integer>();

		//repeat until last number has been added
		while (true) {
			if (x == 1) {
				q.addFirst(1);
				break;
			}
			// 1) step one
			q.addFirst(x);

			// 2) step two
			q.addFirst(q.getLast());
			q.removeLast();
			x--;
		}

		frame.remove(myPanel);
		display = new JPanel();
		ImageIcon[] images = new ImageIcon[q.size()];
		JLabel[] labels = new JLabel[q.size()];
		if (q.size() < 4) {

			display.setLayout(new GridLayout(1, 4));
		} else {

			display.setLayout(new GridLayout(3, 10));
		}

		System.out.println(q);

		Iterator<Integer> iter = q.iterator();
		for (int i = 0; i < q.size(); i++) {
			String imageFileName = iter.next() + ".gif";

			images[i] = new ImageIcon(imageFileName);
			Image pic = images[i].getImage(); // transform it
			Image newimg = pic.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
			images[i] = new ImageIcon(newimg);

			labels[i] = new JLabel(images[i]);
			display.add(labels[i]);

		}
		JButton returnMain = new JButton("RETURN");
		returnMain.addActionListener(new classReturn());
		display.add(returnMain);

		frame.add(display);
		frame.pack();
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e, int a) {
	}
}
