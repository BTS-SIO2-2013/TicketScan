package esarc.bts.ticketscan.model.event;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import esarc.bts.ticketscan.model.client.Client;
import esarc.bts.ticketscan.model.dateevent.DateEvent;
import esarc.bts.ticketscan.model.ticket.Ticket;

public class Event {

    private String       libelle;
    private List<Ticket> listTicket;
    private DateEvent    dateEvent;

    public Event() {
        super();
        this.listTicket = new ArrayList<Ticket>();
    }

    public final String getLibelle() {
        return this.libelle;
    }

    public final void setLibelle(final String pLibelle) {
        if (pLibelle != null) {
            this.libelle = pLibelle;
        } else {
            this.libelle = null;
        }

    }

    public final DateEvent getDateEvent() {
        return this.dateEvent;
    }

    public final void setDateEvent(final DateEvent pDateEvent) {
        if (pDateEvent != null) {
            this.dateEvent = pDateEvent;
        } else {
            this.dateEvent = null;
        }
    }

    public final List<Ticket> getListTicket() {
        return this.listTicket;
    }

    public final void setListTicket(final List<Ticket> pListTicket) {
        if (pListTicket != null) {
            this.listTicket = pListTicket;
        } else {
            this.listTicket = null;
        }
    }

    public final String listTicketToJson() {
        String sortie = "[";
        String separateur = "";
        for (final Ticket ticket : this.listTicket) {
            sortie += separateur;
            Client client = ticket.getClient();
            sortie += "{\"client\":";
            sortie += "{\"nom\":" + client.getNom() + ",";
            sortie += "\"prenom\":" + client.getPrenom() + "},";
            sortie += "\"code\":" + ticket.getCode() + ",";
            sortie += "\"valide\":" + ticket.getValide() + "}";
            separateur = ",";
        }
        sortie += "]";
        return sortie;
    }

    public final void addTicket(final Ticket ticket) {
        this.listTicket.add(ticket);
    }

    public final void removeTicket(final Ticket ticket) {
        this.listTicket.remove(ticket);
    }

    public static Event loadJson(final String json) throws JSONException,
            ParseException {
        JSONObject jsonT = new JSONObject(json);
        Event event = new Event();

        event.setLibelle(jsonT.getString("libelle"));
        event.setDateEvent(DateEvent.stringToDate(jsonT.getString("date")));
        event.setListTicket(Ticket.ticketListFromJSON(jsonT
                .getString("listeDesTickets")));

        return event;

    }

    public static List<Event> eventListFromJSON(final String json)
            throws JSONException, ParseException {

        List<Event> list = new ArrayList<Event>();

        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject eventJSON = jsonArray.getJSONObject(i);
            Event event = Event.loadJson(eventJSON.toString());
            list.add(event);
        }

        return list;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (dateEvent == null) {
            result += 0;
        } else {
            result += dateEvent.hashCode();
        }
        if (libelle == null) {
            result += 0;
        } else {
            result += libelle.hashCode();
        }
        if (listTicket == null) {
            result += 0;
        } else {
            result += listTicket.hashCode();
        }
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (dateEvent == null) {
            if (other.dateEvent != null) {
                return false;
            }
        } else if (!dateEvent.equals(other.dateEvent)) {
            return false;
        }
        if (libelle == null) {
            if (other.libelle != null) {
                return false;
            }
        } else if (!libelle.equals(other.libelle)) {
            return false;
        }
        return true;
    }
}
