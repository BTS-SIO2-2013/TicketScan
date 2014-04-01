package esarc.bts.ticketscan;

import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONException;

import esarc.bts.ticketscan.model.salle.Salle;
import esarc.bts.ticketscan.model.salle.SalleAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class SalleActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_salle);
		
		// Initialisation en dur pour l'instant, pas de liaison BDD
		ArrayList<Salle> list = new ArrayList<Salle>();
		try {
			Salle salle1 = Salle.loadJson("{\"nom\":\"Bacalan\",\"adresse\":\"16 rue de l'eglise\"," +
					"\"listeDesEvenements\": " +
						"[{\"nom\":\"Concert\",\"date\":\"20/03/2014\"," +
							"\"listeDesTickets\": " +
								"[{\"nom\":\"Jean-claude\",\"code\":22 ,\"valide\":false}," +
								"{\"nom\":\"Jean-Michel\",\"code\":15 ,\"valide\":true}]}," +
						"{\"nom\":\"Magie\",\"date\":\"21/03/2014\"," +
							"\"listeDesTickets\": " +
								"[{\"nom\":\"Tristan\",\"code\":1 ,\"valide\":false}," +
								"{\"nom\":\"Adam\",\"code\":20 ,\"valide\":true}]}]}");
			Salle salle2 = Salle.loadJson("{\"nom\":\"Meriadec\",\"adresse\":\"1 rue de la Mairie\"," +
					"\"listeDesEvenements\":[{\"nom\":\"Danse\",\"date\":\"27/03/2014\"," +
					"\"listeDesTickets\": [{\"nom\":\"Alain\",\"code\":10 ,\"valide\":false}," +
					"{\"nom\":\"Robert\",\"code\":10 ,\"valide\":true}]}," +
					"{\"nom\":\"Theatre\",\"date\":\"01/04/2014\"," +
					"\"listeDesTickets\": [{\"nom\":\"Alexis\",\"code\":1 ,\"valide\":false}," +
					"{\"nom\":\"Xavier\",\"code\":20 ,\"valide\":true}]}]}");
			list.add(salle1);
			list.add(salle2);
        } catch (JSONException e) {
			Log.e("JSONE", e.getMessage());
		} catch (ParseException e) {
			Log.e("PARSE", e.getMessage());
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
