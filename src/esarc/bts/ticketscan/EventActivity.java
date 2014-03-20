package esarc.bts.ticketscan;

import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONException;
import org.w3c.dom.ls.LSInput;

import esarc.bts.ticketscan.model.event.Event;
import esarc.bts.ticketscan.model.event.EventAdapter;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class EventActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_scan);
		
		ArrayList<Event> list = new ArrayList<Event>();
        try {
			Event event1 = Event.loadJson("{\"nom\":\"Concert\",\"date\":\"20/03/2014\"," +
					"\"listeDesTickets\": [{\"nom\":\"Jean-claude\",\"code\":22 ,\"valide\":false}," +
					"{\"nom\":\"Jean-Miche\",\"code\":15 ,\"valide\":true}]}");
			Event event2 = Event.loadJson("{\"nom\":\"Magie\",\"date\":\"21/03/2014\"," +
					"\"listeDesTickets\": [{\"nom\":\"Tristan\",\"code\":1 ,\"valide\":false}," +
					"{\"nom\":\"Adam\",\"code\":20 ,\"valide\":true}]}");
			list.add(event1);
			list.add(event2);
        } catch (JSONException e) {
			Log.e("JSONE", e.getMessage());
		} catch (ParseException e) {
			Log.e("PARSE", e.getMessage());
		}
        
        EventAdapter adapter = new EventAdapter(this,list);
        setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Event event = (Event) l.getItemAtPosition(position);
		//Toast.makeText(this, event.listTicketToJson(), Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(this, TicketScan.class);
		intent.putExtra("listTicket", event.listTicketToJson());
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ticket_scan, menu);
		return true;
	}

}
