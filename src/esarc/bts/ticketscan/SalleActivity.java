package esarc.bts.ticketscan;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONException;

import esarc.bts.ticketscan.model.event.Event;
import esarc.bts.ticketscan.model.salle.Salle;
import esarc.bts.ticketscan.model.salle.SalleAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class SalleActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_salle);
		//Récupération de la liste des tickets de l'intent
		Intent intent = getIntent();
		ArrayList<Salle> list = new ArrayList<Salle>();
		if (intent != null){
			try {
				list = (ArrayList<Salle>) Salle.salleListFromJSON(intent.getStringExtra("listSalle"));
			} catch (JSONException e) {
				Log.e("JSONE", e.getMessage());
			} catch (ParseException e) {
				Log.e("Parse", e.getMessage());
			}
		}
        
        SalleAdapter adapter = new SalleAdapter(this,list);
        setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Salle salle = (Salle) l.getItemAtPosition(position);
		
		
		Intent intent = new Intent(this, EventActivity.class);
		intent.putExtra("listEvent", salle.listEventToJson());
		startActivity(intent);
	}

}
