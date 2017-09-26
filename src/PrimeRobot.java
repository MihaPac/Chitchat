import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;

public class PrimeRobot extends TimerTask {
	private ChatFrame chat;
	private int k;
	
	private static boolean isPrime(int n) {
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) { return false; }
		}
		return true;
	}

	public PrimeRobot(ChatFrame chat) {
		this.chat = chat;
		this.k = 2;
	}

	/**
	 * Activate the robot!
	 */
	public void activate() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 5000, 1000);
	}
	
	@Override
	public void run() {
		if (isPrime(this.k)) {
			try {
				chat.addMessage("primer", Integer.toString(this.k) + " is prime");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.k++;
	}
}
