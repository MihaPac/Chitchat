import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

public class JSON {

	private EchoServer echoserver = new EchoServer();
	
	public static void main(String[] args) throws ClientProtocolException, URISyntaxException, IOException {
		logIn("Mihael");
		sendMessage("Mihael", "Neko sporocilo1");
		sendMessage("Mihael", "Neko sporocilo2");
		sendMessage("Mihael", "Neko sporocilo3");
		readMessage("Mihael");
		logOut("Mihael");
	}
	
	public static void readMessage(String person) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/messages");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
		
		String responseBody = Request.Get(url)
		          .execute()
		          .returnContent()
		          .asString();
		
		System.out.println(responseBody);
	}
	public static void logIn(String person) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/users");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
	
		Response response = Request.Post(url)
				   .execute();
			
		System.out.println(response);
	}
	
	public static void logOut(String person) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/users");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
		
		String response = Request.Delete(url)
			   .execute()
			   .returnContent()
			   .asString();
		
		System.out.println(response);
	}
	
	public static void sendMessage(String person, String input) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/messages");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
		
		String message = "{ \"global\" : true, \"text\" : \""+ input + "\"  }";
		
		String responseBody = Request.Post(url)
		          .bodyString(message, ContentType.APPLICATION_JSON)
		          .execute()
		          .returnContent()
		          .asString();
		
		System.out.println(responseBody);
	}
}
