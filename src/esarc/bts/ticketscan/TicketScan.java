package esarc.bts.ticketscan;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import esarc.bts.ticketscan.model.ticket.Ticket;
import esarc.bts.ticketscan.model.ticket.TicketAdapter;
import esarc.bts.zxing.integration.android.IntentIntegrator;
import esarc.bts.zxing.integration.android.IntentResult;

public class TicketScan extends ListActivity {
    private Button scanBtn;
    private TextView formatTxt, contentTxt;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_scan);
        // Récupération de la liste des tickets de l'intent
        Intent intent = getIntent();
        ArrayList<Ticket> list = new ArrayList<Ticket>();
        if (intent != null) {
            try {
                list = (ArrayList<Ticket>) Ticket.ticketListFromJSON(intent
                        .getStringExtra("listTicket"));
            } catch (JSONException e) {
                Log.e("JSONE", e.getMessage());
            }
        }
        TicketAdapter adapter = new TicketAdapter(this, list);
        setListAdapter(adapter);
        // Barcode Reader
        this.formatTxt = (TextView) findViewById(R.id.scan_format);
        this.contentTxt = (TextView) findViewById(R.id.scan_content);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ticket_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
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

    @Override
    public final void onActivityResult(final int requestCode,
            final int resultCode, final Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            this.formatTxt.setText("FORMAT: " + scanFormat);
            this.contentTxt.setText("CONTENT: " + scanContent);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aucune donnée", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected final void onListItemClick(final ListView l, final View v,
            final int position, final long id) {
        super.onListItemClick(l, v, position, id);
        Ticket ticket = (Ticket) l.getItemAtPosition(position);
        if (ticket.getValide()) {
            v.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            ticket.setValide(false);
        } else {
            v.setBackgroundColor(android.graphics.Color.GREEN);
            ticket.setValide(true);
        }
    }

    public final Button getScanBtn() {
        return this.scanBtn;
    }

    public final void setScanBtn(final Button pScanBtn) {
        this.scanBtn = pScanBtn;
    }
}
