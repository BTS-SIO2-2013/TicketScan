package esarc.bts.ticketscan;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import esarc.bts.ticketscan.model.message.MessageLogin;
import esarc.bts.ticketscan.model.user.user;

public class logActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_log);
	}

	public void onClick(View v) {
		EditText txtNom = (EditText) this.findViewById(R.id.txtNom);
		EditText txtMDP = (EditText) this.findViewById(R.id.txtMDP);

		Toast.makeText(this.getApplicationContext(),
				"ID: " + txtNom.getText() + "\nMDP: " + txtMDP.getText(),
				Toast.LENGTH_SHORT).show();
		// Envoie des données (JSON) au serveur
		this.envoyerDonnee(txtNom.getText().toString(), txtMDP.getText()
				.toString());
		// Verification des données reçus
		this.verifier();
	}

	public void verifier() {
		MessageLogin messageLogin = new MessageLogin();
		ProgressBar progressBar = (ProgressBar) this
				.findViewById(R.id.progressBarLog);

		// On affiche la progress bar
		progressBar.setVisibility(View.VISIBLE);

		// Variable Json permettant de simuler une authentification correcte sur
		// le serveur
		String json = "{\"autOk\":\"" + true + "\"," + "\"message\":\"" + null
				+ "\"}";

		try {
			messageLogin = MessageLogin.fromJSON(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (messageLogin.isAutOk()) {
			Toast.makeText(this.getApplicationContext(), "Authentification OK",
					Toast.LENGTH_SHORT).show();

			// On masque la progress bar
			progressBar.setVisibility(View.INVISIBLE);

			// On affiche le layout suivant
			Intent intent = new Intent(this, EventActivity.class);
			this.startActivity(intent);

		} else {
			Toast.makeText(this.getApplicationContext(), "Authentification KO",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void envoyerDonnee(String login, String mdp) {

		user user = new user(login, mdp);
		user.toJSONLog();
		// todo.envoyez le JSON à la BDD

	}
}
