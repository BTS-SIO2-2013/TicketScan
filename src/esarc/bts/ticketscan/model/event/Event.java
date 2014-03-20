package esarc.bts.ticketscan.model.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import esarc.bts.ticketscan.model.ticket.Ticket;

public class Event {
	
	private String libelle;
	private Date dateEvent;
	private List<Ticket> listTicket;
	
	public Event(){
		super();
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		if (libelle != null) 
				this.libelle = libelle;
		else this.libelle = null;
	}
	
	public Date getDateEvent() {
		return dateEvent;
	}
	public void setDateEvent(Date dateEvent) {
		if (dateEvent != null)
			this.dateEvent = dateEvent;
		else this.dateEvent = null;
	}
	public List<Ticket> getListTicket() {
		return listTicket;
	}
	public void setListTicket(List<Ticket> listTicket) {
		if (listTicket != null)
			this.listTicket = listTicket;
		else
			this.listTicket = null;
	}
	
	public void addTicket (Ticket ticket) {
		this.listTicket.add(ticket);
	}
	
	public void removeTicket( Ticket ticket) {
		this.listTicket.remove(ticket);
	}
	public static Event loadJson(String json) throws JSONException, ParseException {
		JSONObject jsonT = new JSONObject(json);
		Event event = new Event();
		
		event.setLibelle(jsonT.getString("nom"));
		String dateStr = jsonT.getString("date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		event.setDateEvent(sdf.parse(dateStr));
		event.setListTicket(Ticket.ticketListFromJSON(jsonT.getString("listeDesTickets")));
		
		return event;
		
	}
}