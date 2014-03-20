package esarc.bts.ticketscan.model.ticket;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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

	public static List<Ticket> ticketListFromJSON(String json) throws JSONException {
		List<Ticket> list = new ArrayList<Ticket>();
		
		JSONArray jsonArray = new JSONArray(json);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject ticketJSON = jsonArray.getJSONObject(i);
			Ticket ticket = Ticket.loadJson(ticketJSON.toString());
			list.add(ticket);
		}
		
		return list;
	}
}
