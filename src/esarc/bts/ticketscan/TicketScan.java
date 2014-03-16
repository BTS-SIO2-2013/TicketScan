package esarc.bts.ticketscan;

import java.util.ArrayList;

import org.json.JSONException;

import esarc.bts.ticketscan.model.ticket.Ticket;
import esarc.bts.ticketscan.model.ticket.TicketAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class TicketScan extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket_scan);
		
	       
        ArrayList<Ticket> list = new ArrayList<Ticket>();
        try {
			Ticket ticket1 = Ticket.loadJson("{\"nom\":\"Jean-claude\",\"code\":22}");
			list.add(ticket1);
        } catch (JSONException e) {
			Log.e("JSONE", e.getMessage());
		}
        list.add(new Ticket("Spectacle",20));
        list.add(new Ticket("Magie", 23));
        ListView listVue = (ListView) findViewById(R.id.TicketListView);
        TicketAdapter adapter = new TicketAdapter(this,list);
        listVue.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ticket_scan, menu);
		return true;
	}

}
