import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7861086191760021048L;
	private JTextArea output;
	private JTextField input;
	private JTextField ime;

	public ChatFrame() {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		this.setTitle("ChitChat");

		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		JPanel vnos = new JPanel();
		vnos.setLayout(layout);
		JLabel napis = new JLabel("vzdevek:");
		vnos.add(napis);
		ime = new JTextField(System.getProperty("user.name"), 20);
		vnos.add(ime);
		GridBagConstraints vnosConstraint = new GridBagConstraints();
		vnosConstraint.fill = GridBagConstraints.HORIZONTAL;
		vnosConstraint.gridx = 0;
		vnosConstraint.gridy = 0;
		pane.add(vnos, vnosConstraint);
		
		this.output = new JTextArea(20, 40);
		this.output.setEditable(false);
		GridBagConstraints outputConstraint = new GridBagConstraints();
		outputConstraint.fill = GridBagConstraints.BOTH;
		outputConstraint.weightx = 1;
		outputConstraint.weighty = 1;
		outputConstraint.gridx = 0;
		outputConstraint.gridy = 1;
		JScrollPane scrollpane = new JScrollPane(this.output,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.add(scrollpane, outputConstraint);
		
		DefaultCaret caret = (DefaultCaret)output.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		this.input = new JTextField(40);
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		outputConstraint.weightx = 1;
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
		
		addWindowListener(new WindowAdapter() {
		    public void windowOpened(WindowEvent e){
		    	input.requestFocusInWindow();
		    }
		});
	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */

	
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + person + ": " + message + "\n");
	}
	
	public void actionPerformed(ActionEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				this.addMessage(this.ime.getText(), this.input.getText());
				this.input.setText("");
			}
		}		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
