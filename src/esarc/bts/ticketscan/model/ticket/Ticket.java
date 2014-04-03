package esarc.bts.ticketscan.model.ticket;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import esarc.bts.ticketscan.model.client.Client;

public class Ticket {
	private Client client;
	private int code;
	private Boolean valide;

	public Ticket(Client client, int code, Boolean valide) {
		super();
		this.client = client;
		this.setCode(code);
		this.setValide(valide);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Boolean getValide() {
		return valide;
	}

	public void setValide(Boolean valide) {
		this.valide = valide;
	}

	public static Ticket loadJson(String json) throws JSONException {
		JSONObject jsonT = new JSONObject(json);
		Client client = Client.loadJson(jsonT.getString("client"));
		return new Ticket(client,jsonT.getInt("code"), jsonT.getBoolean("valide"));
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
