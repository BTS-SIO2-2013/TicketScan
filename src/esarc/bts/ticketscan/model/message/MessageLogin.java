package esarc.bts.ticketscan.model.message;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageLogin {
	
	private boolean autOk;
	private String message;
	
	public boolean isAutOk() {
		return autOk;
	}
	public void setAutOk(boolean autOk) {
		this.autOk = autOk;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageLogin(boolean autOk, String message) {
		super();
		this.autOk = autOk;
		this.message = message;
	}
	public MessageLogin() {
		this.autOk = true;
		this.message = "";
	}
	
	public static MessageLogin fromJSON(String json) throws JSONException {
		MessageLogin messagelogin = new MessageLogin();
		
		JSONObject jsonObj = new JSONObject(json);
		
		messagelogin.setAutOk(jsonObj.getBoolean("autOk"));
		messagelogin.setMessage(jsonObj.getString("message"));
		
		return messagelogin;
						
		
	}
}
