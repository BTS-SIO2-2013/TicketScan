package esarc.bts.ticketscan.model.salle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import esarc.bts.ticketscan.model.event.Event;

public class Salle {
	private String nom;
	private List<Event> listeEvent;
	
	public Salle() {
		super();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Event> getListeEvent() {
		return listeEvent;
	}

	public void setListeEvent(List<Event> listeEvent) {
		this.listeEvent = listeEvent;
	}
	
	public void addEvent (Event event) {
		this.listeEvent.add(event);
	}
	
	public void removeEvent( Event event) {
		this.listeEvent.remove(event);
	}

	public static Salle loadJson(String json) throws JSONException, ParseException {
		
		JSONObject jsonT = new JSONObject(json);
		Salle salle = new Salle();
		
		salle.setNom(jsonT.getString("nom"));
		salle.setListeEvent(Event.eventListFromJSON(jsonT.getString("listeDesEvenements")));
		
		return salle;
		
	}

	public String listEventToJson() {
		String sortie = "[";
		String separateur = "";
		for (final Event event: this.listeEvent) {
			sortie += separateur;
			sortie += "{\"libelle\":" + event.getLibelle() + ",";
			sortie += "\"date\": \"" + event.getDateEvent().toString() + "\",";
			sortie += "\"listeDesTickets\":" + event.listTicketToJson() + "}";
			separateur = ",";
		}
		sortie += "]";
		return sortie;
	}

	public static List<Salle> salleListFromJSON(String json) throws JSONException, ParseException {
		List<Salle> list = new ArrayList<Salle>();
		
		JSONArray jsonArray = new JSONArray(json);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject eventJSON = jsonArray.getJSONObject(i);
			Salle salle = Salle.loadJson(eventJSON.toString());
			list.add(salle);
		}
		
		return list;
	}
}
