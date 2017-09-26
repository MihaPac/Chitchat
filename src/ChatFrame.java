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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7861086191760021048L;
	private JTextArea output;
	private JTextField input;
	private JTextField ime;
	private String staro_ime;
	private Boolean on = true;
	Timer timer = new Timer();
	
	private EchoServer echoserver = new EchoServer();

	public ChatFrame() throws ClientProtocolException, URISyntaxException, IOException, InterruptedException {
		
		
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		this.setTitle("ChitChat");
		
		//echoserver.main(new String[0]);

		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		JPanel vnos = new JPanel();
		vnos.setLayout(layout);
		JLabel napis = new JLabel("vzdevek:");
		vnos.add(napis);
		this.ime = new JTextField(System.getProperty("user.name"), 20);
		vnos.add(ime);
		GridBagConstraints vnosConstraint = new GridBagConstraints();
		vnosConstraint.fill = GridBagConstraints.HORIZONTAL;
		vnosConstraint.gridx = 0;
		vnosConstraint.gridy = 0;
		pane.add(vnos, vnosConstraint);
		ime.addKeyListener(this);

		/*Login takoj ko se zazene program*/
		staro_ime = this.ime.getText();
		echoserver.logIn(staro_ime);
		
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
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
		    	try {
					on = false;
					echoserver.logOut(staro_ime);
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		
		
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				if (on == true) {
					getMessages();
				}
			  }
			}, 0, 100);
	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */

	//napise sporocilo v aplikacijo
	public void addMessage(String person, String message) throws ClientProtocolException, URISyntaxException, IOException {
		String chat = this.output.getText();
		this.output.setText(chat + person + ": " + message + "\n");
	}
	
	//poslje sporocilo serverju
	public void sendMessage(Boolean global, String person, String message) throws ClientProtocolException, URISyntaxException, IOException {
		if (message.isEmpty() == false) {
			if (global == true) {
				String arg = "{ \"global\" : true, \"text\" : " + message + "}";
				JSONObject json = new JSONObject(arg);
				this.echoserver.sendMessage(json, person);
			}
		} else { 
			if (global == true) {
				String arg = "{ \"global\" : true, \"text\" : \"\"}";
				JSONObject json = new JSONObject(arg);
				this.echoserver.sendMessage(json, person);
			}
		}
	}
	
	public void sendPrivMessage(Boolean global, String person, String recipient, String message) throws ClientProtocolException, URISyntaxException, IOException {
		if (message.isEmpty() == false) {
			if (global == false) {
				String arg = "{ \"global\" : false, \"recipient\" : " + recipient + ", \"text\" : " + message + "}";
				JSONObject json = new JSONObject(arg);
				this.echoserver.sendMessage(json, person);
			}
		} else { 
			if (global == false) {
				String arg = "{ \"global\" : false, \"recipient\" : " + recipient + ", \"text\" : \"\"}";
				JSONObject json = new JSONObject(arg);
				this.echoserver.sendMessage(json, person);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				try {
					this.addMessage(staro_ime, this.input.getText());
					this.sendMessage(true, staro_ime, this.input.getText());
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.input.setText("");
			}
		}		
		if (e.getSource() == this.ime) {
			if (e.getKeyChar() == '\n') {
				try {
					on = false;
					echoserver.logOut(staro_ime);
					echoserver.logIn(this.ime.getText());
					staro_ime = this.ime.getText();
					on = true;
				} catch (ClientProtocolException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (URISyntaxException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
	}

	public void getMessages() {
		try {
			JSONArray list = (JSONArray) echoserver.readMessage(staro_ime);
			for(int i = 0; i < list.length(); i++) {
				JSONObject sporocilo = list.getJSONObject(i);
				this.addMessage(sporocilo.getString("sender"), sporocilo.getString("text"));
			}
			
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
