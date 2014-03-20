package esarc.bts.ticketscan;

import java.util.ArrayList;

import org.json.JSONException;

import esarc.bts.ticketscan.model.ticket.Ticket;
import esarc.bts.ticketscan.model.ticket.TicketAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class TicketScan extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_scan);
		
		//Récupération de la liste des tickets de l'intent
		Intent intent = getIntent();
		Log.e("Tickets", intent.getStringExtra("listTicketStr"));
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		if (intent != null){
			try {
				list = (ArrayList<Ticket>) Ticket.ticketListFromJSON(intent.getStringExtra("listTicketStr"));
			} catch (JSONException e) {
				Log.e("JSONE", e.getMessage());
			}
		}
		
	       
        //ArrayList<Ticket> list = new ArrayList<Ticket>();
        /*try {
			Ticket ticket1 = Ticket.loadJson("{\"nom\":\"Jean-claude\",\"code\":22 ,\"valide\":false}");
			Ticket ticket2 = Ticket.loadJson("{\"nom\":\"Jean-Miche\",\"code\":15 ,\"valide\":true}");
			list.add(ticket1);
        	//ArrayList<Ticket> list = 
        } catch (JSONException e) {
			Log.e("JSONE", e.getMessage());
		}*/
        TicketAdapter adapter = new TicketAdapter(this,list);
        setListAdapter(adapter);
	}


}
