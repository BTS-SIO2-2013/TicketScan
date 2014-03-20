package esarc.bts.ticketscan;

import java.util.ArrayList;

import org.json.JSONException;

import esarc.bts.ticketscan.model.ticket.Ticket;
import esarc.bts.ticketscan.model.ticket.TicketAdapter;

import android.os.Bundle;
import android.app.ListActivity;

import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class TicketScan extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_scan);
		
		//Récupération de la liste des tickets de l'intent
		Intent intent = getIntent();
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		if (intent != null){
			try {
				list = (ArrayList<Ticket>) Ticket.ticketListFromJSON(intent.getStringExtra("listTicket"));
			} catch (JSONException e) {
				Log.e("JSONE", e.getMessage());
			}
		}
		
        TicketAdapter adapter = new TicketAdapter(this,list);
        setListAdapter(adapter);
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);
		Ticket ticket = (Ticket) l.getItemAtPosition(position);
		if (ticket.getValide()== false){
			v.setBackgroundColor(android.graphics.Color.BLUE);
			ticket.setValide(true);
		}
		else {
			v.setBackgroundColor(android.graphics.Color.TRANSPARENT);
			ticket.setValide(false);
		}
		
	}
	

}
