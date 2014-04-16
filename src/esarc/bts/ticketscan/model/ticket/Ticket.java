package esarc.bts.ticketscan.model.ticket;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import esarc.bts.ticketscan.model.client.Client;

public class Ticket {
    private Client  client;
    private int     code;
    private Boolean valide;

    public Ticket(final Client pClient, final int pCode, final Boolean pValide) {
        super();
        this.client = pClient;
        this.setCode(pCode);
        this.setValide(pValide);
    }

    public final Client getClient() {
        return this.client;
    }

    public final void setClient(final Client pClient) {
        this.client = pClient;
    }

    public final int getCode() {
        return this.code;
    }

    public final void setCode(final int pCode) {
        this.code = pCode;
    }

    public final Boolean getValide() {
        return this.valide;
    }

    public final void setValide(final Boolean pValide) {
        this.valide = pValide;
    }

    public static Ticket loadJson(final String json) throws JSONException {
        JSONObject jsonT = new JSONObject(json);
        Client client = Client.loadJson(jsonT.getString("client"));
        return new Ticket(client, jsonT.getInt("code"),
                jsonT.getBoolean("valide"));
    }

    public static List<Ticket> ticketListFromJSON(final String json)
            throws JSONException {
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
