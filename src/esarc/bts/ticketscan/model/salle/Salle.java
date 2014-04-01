package esarc.bts.ticketscan.model.salle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import esarc.bts.ticketscan.model.event.Event;

public class Salle {
	private String nom;
	private String adresse;
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

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
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
		salle.setAdresse(jsonT.getString("adresse"));
		salle.setListeEvent(Event.eventListFromJSON(jsonT.getString("listeDesEvenements")));
		
		return salle;
		
	}

	public String listEventToJson() {
		String sortie = "[";
		String separateur = "";
		for (final Event event: this.listeEvent) {
			sortie += separateur;
			sortie += "{\"nom\":" + event.getLibelle() + ",";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateStr = "";
			if (event.getDateEvent() != null)
				dateStr = sdf.format(event.getDateEvent());
			sortie += "\"date\": \"" + dateStr + "\",";
			sortie += "\"listeDesTickets\":" + event.listTicketToJson() + "}";
			separateur = ",";
		}
		sortie += "]";
		return sortie;
	}
}
