//description, return to main frame; creates a new jpanel though.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class classReturn implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		C.frame.setVisible(false);
		C.frame.remove(C.display);
		C.frame.add(C.myPanel);
		C.frame.pack();
		C.frame.setVisible(true);
	}

}
