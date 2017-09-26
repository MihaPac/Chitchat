import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GetText extends TimerTask {
	ChatFrame chatFrame = new ChatFrame();
	
	public void run() {
	       chatFrame.getMessages();
	}
}