package esarc.bts.ticketscan.model.salle;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import esarc.bts.ticketscan.model.event.Event;

public class Salle {

    private String      nom;
    private List<Event> listeEvent;

    public Salle() {
        super();
        this.listeEvent = new ArrayList<Event>();
    }

    public final String getNom() {
        return this.nom;
    }

    public final void setNom(final String pNom) {
        this.nom = pNom;
    }

    public final List<Event> getListeEvent() {
        return this.listeEvent;
    }

    public final void setListeEvent(final List<Event> pListeEvent) {
        this.listeEvent = pListeEvent;
    }

    public final void addEvent(final Event event) {
        this.listeEvent.add(event);
    }

    public final void removeEvent(final Event event) {
        this.listeEvent.remove(event);
    }

    public static Salle loadJson(final String json) throws JSONException,
            ParseException {

        JSONObject jsonT = new JSONObject(json);
        Salle salle = new Salle();

        salle.setNom(jsonT.getString("nom"));
        salle.setListeEvent(Event.eventListFromJSON(jsonT
                .getString("listeDesEvenements")));

        return salle;

    }

    public final String listEventToJson() {
        String sortie = "[";
        String separateur = "";
        for (final Event event : this.listeEvent) {
            sortie += separateur;
            sortie += "{\"libelle\":\"" + event.getLibelle() + "\",";
            sortie += "\"date\":\"" + event.getDateEvent().toString() + "\",";
            sortie += "\"listeDesTickets\":" + event.listTicketToJson() + "}";
            separateur = ",";
        }
        sortie += "]";
        return sortie;
    }

    public static List<Salle> salleListFromJSON(final String json)
            throws JSONException, ParseException {
        List<Salle> list = new ArrayList<Salle>();

        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject eventJSON = jsonArray.getJSONObject(i);
            Salle salle = Salle.loadJson(eventJSON.toString());
            list.add(salle);
        }

        return list;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (this.listeEvent == null) {
            result += 0;
        } else {
            result += this.listeEvent.hashCode();
        }
        result = prime * result;
        if (this.nom == null) {
            result += 0;
        } else {
            result += this.nom.hashCode();
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
        Salle other = (Salle) obj;
        if (this.nom == null) {
            if (other.nom != null) {
                return false;
            }
        } else if (!this.nom.equals(other.nom)) {
            return false;
        }
        return true;
    }
}
