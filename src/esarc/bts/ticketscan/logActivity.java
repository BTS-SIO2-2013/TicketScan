package esarc.bts.ticketscan;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import esarc.bts.ticketscan.model.Connection.Connection;
import esarc.bts.ticketscan.model.hash.Hash;
import esarc.bts.ticketscan.model.message.MessageLogin;
import esarc.bts.ticketscan.model.user.user;

public class logActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.setContentView(R.layout.activity_log);
    }

    // Se produit lorsqu'on clique sur le bouton de connexion
    public void onClick(final View v) {
    	EditText txtNom = (EditText) findViewById(R.id.txtNom);
    	EditText txtMDP = (EditText) findViewById(R.id.txtMDP);

    	// Envoie des donnees (JSON) au serveur
    	String json = envoyerDonneeConnexion(txtNom.getText().toString(), txtMDP.getText().toString());
    	System.out.println("Json: " + json);
    	// Verification des donnees recus
    	verifier(json);
    }

    public void onClickTxtMDPOublie(final View v) {
	Toast.makeText(getApplicationContext(), "Coucou ", Toast.LENGTH_SHORT)
		.show();
    }

    public void verifier(String json) {
	MessageLogin messageLogin = new MessageLogin();
	ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarLog);

	// On affiche la progress bar
	progressBar.setVisibility(View.VISIBLE);

	try {
	    messageLogin = MessageLogin.fromJSON(json);
	} catch (JSONException e) {
	    e.printStackTrace();
	}

	if (messageLogin.isAutOk()) {
	    // Debug
	    System.out.println("Authentification OK");

	    // On masque la progress bar
	    progressBar.setVisibility(View.INVISIBLE);

	    // Initialisation en dur pour l'instant, pas de liaison BDD
	    String listJSON2 = recupererJSON();

	    // On affiche le layout suivant
	    Intent intent = new Intent(this, SalleActivity.class);
	    // On envoie les données au layout
	    intent.putExtra("listSalle", listJSON2);
	    // On "affiche"
	    this.startActivity(intent);

	} else {
	    // Debug
	    System.out.println("Authentification KO : "
		    + messageLogin.getMessage().toString());

	    // Si l'authentification n'est pas correct on affiche un toast avec
	    // le message d'erreur associé
	    Toast.makeText(getApplicationContext(),
		    messageLogin.getMessage().toString(), Toast.LENGTH_SHORT)
		    .show();
	}

    }

    private String recupererJSON() {
	String url = "https://trello-attachments.s3.amazonaws.com/5328073ffea1f3f032aa2352/533d0c222ee1771451f6ea76/0ebd22b00f5416e8aa2f607e16f1a84d/Json.json";
	String rep = "";

	// Pour le localhost IP: 10.0.2.2 (correspond à l'émulateur)
	// String url = "http://10.0.2.2/test.json";

	if (Connection.isNetworkAvailable(this)) {
	    try {
		rep = new Connection().execute(url).get();
	    } catch (InterruptedException e) {
		System.out.println("InterruptedException: " + e);
		e.printStackTrace();
	    } catch (ExecutionException e) {
		System.out.println("ExecutionException: " + e);
		e.printStackTrace();
	    }
	} else {
	    System.out.println("No network connection available.");
	    Toast.makeText(getApplicationContext(),
		    "No network connection available.", Toast.LENGTH_SHORT)
		    .show();
	}
	return rep;
    }

    public String envoyerDonneeConnexion(final String login, final String mdp) {

	user user = new user(login, Hash.toSHA(mdp));
	user.toJSONLog();
	// TODO envoyez le JSON a la BDD
	String url = "http://127.0.0.1/BilletMaster/mobile/connection.php";
	String rep = "";

	// Pour le localhost IP: 10.0.2.2 (correspond a l'emulateur)
	// String url = "http://10.0.2.2/test.json";

	if (Connection.isNetworkAvailable(this)) {
	    try {
		rep = new Connection(login,mdp,"POST").execute(url).get();
	    } catch (InterruptedException e) {
		System.out.println("InterruptedException: " + e);
		e.printStackTrace();
	    } catch (ExecutionException e) {
		System.out.println("ExecutionException: " + e);
		e.printStackTrace();
	    }
	} else {
	    System.out.println("No network connection available.");
	    Toast.makeText(getApplicationContext(),
		    "No network connection available.", Toast.LENGTH_SHORT)
		    .show();
	}
	return rep;
    }
}
