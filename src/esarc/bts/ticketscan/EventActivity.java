package esarc.bts.ticketscan;

import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONException;
import esarc.bts.ticketscan.model.event.Event;
import esarc.bts.ticketscan.model.event.EventAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class EventActivity extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_scan);
		
		//Récupération de la liste des tickets de l'intent
				Intent intent = getIntent();
				ArrayList<Event> list = new ArrayList<Event>();
				if (intent != null){
					try {
						list = (ArrayList<Event>) Event.eventListFromJSON(intent.getStringExtra("listEvent"));
					} catch (JSONException e) {
						Log.e("JSONE", e.getMessage());
					} catch (ParseException e) {
						Log.e("Parse", e.getMessage());
					}
				}
        
        EventAdapter adapter = new EventAdapter(this,list);
        setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Event event = (Event) l.getItemAtPosition(position);
		
		
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
