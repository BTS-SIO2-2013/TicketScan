package esarc.bts.ticketscan;

import java.util.ArrayList;

import org.json.JSONException;

import esarc.bts.ticketscan.model.ticket.Ticket;
import esarc.bts.ticketscan.model.ticket.TicketAdapter;
import esarc.bts.zxing.integration.android.IntentIntegrator;
import esarc.bts.zxing.integration.android.IntentResult;

import android.widget.Toast;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListActivity;

import android.content.Intent;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TicketScan extends ListActivity {

	private Button scanBtn;
	private TextView formatTxt, contentTxt;
	
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
        
     // Barcode Reader
	    formatTxt = (TextView)findViewById(R.id.scan_format);
	    contentTxt = (TextView)findViewById(R.id.scan_content);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.ticket_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.scan_button:
	        	IntentIntegrator scanIntegrator = new IntentIntegrator(this);
				scanIntegrator.initiateScan();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			formatTxt.setText("FORMAT: " + scanFormat);
			contentTxt.setText("CONTENT: " + scanContent);
		}else{
		    Toast toast = Toast.makeText(getApplicationContext(), 
		            "Aucune donnée", Toast.LENGTH_SHORT);
		    toast.show();
		}
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
