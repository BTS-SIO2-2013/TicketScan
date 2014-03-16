package esarc.bts.ticketscan.model.ticket;

import org.json.JSONException;
import org.json.JSONObject;

public class Ticket {
	private String nom;
	private int code;

	public Ticket(String nom, int code) {
		super();
		this.nom = nom;
		this.setCode(code);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static Ticket loadJson(String json) throws JSONException {
		JSONObject jsonT = new JSONObject(json);
		return new Ticket(jsonT.getString("nom"),jsonT.getInt("code"));
	}
}
