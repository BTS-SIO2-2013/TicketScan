package esarc.bts.ticketscan;

import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONException;

import esarc.bts.ticketscan.model.event.Event;
import esarc.bts.ticketscan.model.event.EventAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class EventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_scan);
		
		ArrayList<Event> list = new ArrayList<Event>();
        try {
			Event event1 = Event.loadJson("{\"nom\":\"Concert\",\"date\":\"20/03/2014\"," +
					"\"listeDesTickets\": [{\"nom\":\"Jean-claude\",\"code\":22}]}");
			list.add(event1);
        } catch (JSONException e) {
			Log.e("JSONE", e.getMessage());
		} catch (ParseException e) {
			Log.e("PARSE", e.getMessage());
		} 
        list.add(new Event());//, new Date(2014,03,19,19,15)));
        list.add(new Event());//, new Date(2014,03,20,15,45)));
        ListView listVue = (ListView) findViewById(R.id.EventListView);
        EventAdapter adapter = new EventAdapter(this,list);
        listVue.setAdapter(adapter);
	}

}
