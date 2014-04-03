package esarc.bts.ticketscan;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import esarc.bts.ticketscan.model.hash.Hash;
import esarc.bts.ticketscan.model.message.MessageLogin;
import esarc.bts.ticketscan.model.user.user;

public class logActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_log);
	}

	// Se produit lorsqu'on clique sur le bouton de connexion
	public void onClick(View v) {
		EditText txtNom = (EditText) this.findViewById(R.id.txtNom);
		EditText txtMDP = (EditText) this.findViewById(R.id.txtMDP);

		// Envoie des donnees (JSON) au serveur
		this.envoyerDonneeConnexion(txtNom.getText().toString(), txtMDP
				.getText().toString());

		// Verification des donnees recus
		this.verifier();
	}

	public void onClickTxtMDPOublie(View v) {
		Toast.makeText(this.getApplicationContext(), "Coucou ",
				Toast.LENGTH_SHORT).show();
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
			e.printStackTrace();
		}

		if (messageLogin.isAutOk()) {
			// Debug
			System.out.println("Authentification OK");

			// On masque la progress bar
			progressBar.setVisibility(View.INVISIBLE);

			// Initialisation en dur pour l'instant, pas de liaison BDD
			String listJSon = "["
					+ "{\"nom\":\"Bacalan\","
					+ "\"listeDesEvenements\": "
					+ "["
					+ "{\"nom\":\"Concert\",\"date\":\"20/03/2014\","
					+ "\"listeDesTickets\": "
					+ "[{\"nom\":\"Jean-claude\",\"code\":22 ,\"valide\":false},"
					+ "{\"nom\":\"Jean-Michel\",\"code\":15 ,\"valide\":true}]},"
					+ "{\"nom\":\"Magie\",\"date\":\"21/03/2014\","
					+ "\"listeDesTickets\": " + "["
					+ "{\"nom\":\"Tristan\",\"code\":1 ,\"valide\":false},"
					+ "{\"nom\":\"Adam\",\"code\":20 ,\"valide\":true}" + "]"
					+ "}" + "]" + "}," + "{\"nom\":\"Meriadec\","
					+ "\"listeDesEvenements\":" + "["
					+ "{\"nom\":\"Danse\",\"date\":\"27/03/2014\","
					+ "\"listeDesTickets\": " + "["
					+ "{\"nom\":\"Alain\",\"code\":10 ,\"valide\":false},"
					+ "{\"nom\":\"Robert\",\"code\":10 ,\"valide\":true}" + "]"
					+ "}," + "{\"nom\":\"Theatre\",\"date\":\"01/04/2014\","
					+ "\"listeDesTickets\":" + "["
					+ "{\"nom\":\"Alexis\",\"code\":1 ,\"valide\":false},"
					+ "{\"nom\":\"Xavier\",\"code\":20 ,\"valide\":true}" + "]"
					+ "}" + "]" + "}" + "]";

			// On affiche le layout suivant
			Intent intent = new Intent(this, SalleActivity.class);
			// On envoie les données au layout
			intent.putExtra("listSalle", listJSon);
			// On "affiche"
			this.startActivity(intent);

		} else {
			// Debug
			System.out.println("Authentification KO");

			// Si l'authentification n'est pas correct on affiche un toast avec
			// le message d'erreur associé
			Toast.makeText(this.getApplicationContext(),
					messageLogin.getMessage().toString(), Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void envoyerDonneeConnexion(String login, String mdp) {

		user user = new user(login, Hash.toSHA(mdp));
		user.toJSONLog();
		// TODO envoyez le JSON a la BDD

	}
}
