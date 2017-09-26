import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Preprost streznik, ki sprejema TCP povezave in odgovarja
 * klientu tako, da ponavlja vse, kar mu klient poslje.
 * Ce klient poslje {@code "bye"}, ga streznik odklopi.
 * 
 * @author Andrej Bauer
 *
 */
public class EchoServer {
	private static final int defaultEchoPort = 4444;
	public static void main(String[] args) {
		int echoPort = defaultEchoPort;
		if (args.length > 0) {
			echoPort = Integer.parseInt(args[0]);
		}
		ServerSocket listener = null;
		try {
			
			listener = new ServerSocket(echoPort);
			System.out.println("EchoServer listening on port " + echoPort);
			while (true) {
				Socket socket = listener.accept();
				EchoHandler handler = new EchoHandler(socket/*, frame*/);
				System.out.println("Client connected on socket "  + socket);
				handler.start();
			}
		} catch (IOException exc) {
			System.out.println("Connection error: " + exc);
		} finally {
			if (listener != null) {
				try {
					listener.close();
				} catch (IOException exc) {
					System.out.println("Error on closing the listener: " + exc);
				}
			}
		}
	}
	
	public void logIn(String person) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/users");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
	
		Request.Post(url)
			   .execute();
	}
	
	public void logOut(String person) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/users");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
		
		Request.Delete(url)
               .execute();
	}
	
	public void sendMessage(JSONObject arg, String person) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/messages");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
		if (arg.getBoolean("global") == true) {
			String message = "{ \"global\" : true, \"text\" : \""+ arg.getString("text") + "\"  }";
			Request.Post(url);
		} else {
			String message = "{ \"global\" : false, \"recipient\" : \"" + arg.getString("recipient") + "\", \"text\" : "
								+ arg.getString("text") + "}";
			Request.Post(url);
		}
	}
	public JSONArray readMessage(String person) throws URISyntaxException, ClientProtocolException, IOException {
		URIBuilder builder = new URIBuilder("http://chitchat.andrej.com/messages");
		builder.addParameter("username", person);
		URI url = new URI(builder.toString());
		
		String responseBody = Request.Get(url)
		          .execute()
		          .returnContent()
		          .asString();
		Pattern sporocilo = Pattern.compile("\\{(.*?)\\}");
		Matcher matchPattern = sporocilo.matcher(responseBody);
		if (responseBody.length() == 2) {
			return new JSONArray();
		} else {
			JSONArray array = new JSONArray();
			while(matchPattern.find()) {
				JSONObject message = new JSONObject("{" + matchPattern.group(1) + "}");
				array.put(message);
	        }
			return array;
		}
	}
}

