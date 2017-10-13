import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

public class ChitChat {

	public static void main(String[] args) throws ClientProtocolException, URISyntaxException, IOException, InterruptedException {
		ChatFrame chatFrame = new ChatFrame();
		chatFrame.pack();
		chatFrame.setVisible(true);
	}
}