import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Hello ChitChat!
 */
public class Pogovor {
	
    public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
    	URI uri = new URIBuilder("http://chitchat.andrej.com/users")
    	          	.addParameter("username", "mihael")
    	          	.build();
    	
    	String response = Request.Post(uri)
    							 .execute()
    							 .returnContent()
    							 .asString();
    	System.out.println(response);
    	
        try {
            String hello = Request.Get("http://chitchat.andrej.com/users")
                                  .execute()
                                  .returnContent().asString();
            System.out.println(hello);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response logout = Request.Delete(uri)
				 .execute();
    }
}