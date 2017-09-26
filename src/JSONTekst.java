import java.util.Date;

import org.json.JSONObject;


public class JSONTekst {
	/*"global" : false,
	"recipient" : "miki",
	"sender" : "pluto",
	"text": "How are you miki? Woof.",
	"sent_at" : "2017-05-25T11:12:32Z"*/
	/*
	private Boolean global;
	private String recipient;
	private String sender;
	private String text;
	private Date sent_at;
	*/
	
	//Kako bi torej dekodiral string jsona?
			
	String str;
	JSONObject obj = new JSONObject(str);
	//in potem lahko naredis vse tiste stvari, okej, okej
	
	
	public static void main(String[] args) {
		String str = "{ \"name\": \"Alice\", \"age\": 20 }";
		JSONObject obj = new JSONObject(str);
		String n = obj.getString("name");
		int a = obj.getInt("age");
		System.out.println(n + " " + a);  // prints "Alice 20"
	}
	
	/*
	@SuppressWarnings("unused")
	private Uporabnik() { }
	
	public JSONTekst(Boolean global, String recipient, String sender, String text, Date sent_at) {
		this.global = global;
		this.recipient = recipient;
		this.sender = sender;
		this.text = text;
		this.sent_at = sent_at;
	}

	/*@Override
	public String toString() {
		return "Uporabnik [username=" + username + ", lastActive=" + lastActive + "]";
	}*/
	/*
	@JsonProperty("global")
	public Boolean getGlobal() {
		return global;
	}
	
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("last_active")
	public Date getLastActive() {
		return this.lastActive;
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}
	*/
	
}
