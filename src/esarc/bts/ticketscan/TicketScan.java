package esarc.bts.ticketscan;

import java.util.ArrayList;

import org.json.JSONException;

import esarc.bts.ticketscan.model.ticket.Ticket;
import esarc.bts.ticketscan.model.ticket.TicketAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class TicketScan extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_scan);
		
	       
        ArrayList<Ticket> list = new ArrayList<Ticket>();
        try {
			Ticket ticket1 = Ticket.loadJson("{\"nom\":\"Jean-claude\",\"code\":22 ,\"valide\":false}");
			Ticket ticket2 = Ticket.loadJson("{\"nom\":\"Jean-Miche\",\"code\":15 ,\"valide\":true}");
			list.add(ticket1);
			list.add(ticket2);
        } catch (JSONException e) {
			Log.e("JSONE", e.getMessage());
		}
        list.add(new Ticket("Spectacle",20, false));
        list.add(new Ticket("Magie", 23, true));
        TicketAdapter adapter = new TicketAdapter(this,list);
        setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ticket_scan, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		super.onListItemClick(l, v, position, id);
		Ticket ticket = (Ticket) l.getItemAtPosition(position);
		if (ticket.getValide()== false){
			v.setBackgroundColor(android.graphics.Color.BLUE);
			ticket.setValide(true);
		}
		else {
			v.setBackgroundColor(android.graphics.Color.RED);
			ticket.setValide(false);
		}
		//Toast.makeText(getApplicationContext(), "Ticket pos:"+position+" id:"+id, Toast.LENGTH_LONG).show();
		
	}
	

}
