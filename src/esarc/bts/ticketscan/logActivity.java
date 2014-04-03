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
	protected void onCreate(Bundle savedInstanceState) {
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
			String listJSON2 = this.recupererJSON();

			String listJSON = "["
					+ "{\"nom\":\"Bacalan\","
					+ "\"listeDesEvenements\": "
					+ "["
					+ "{\"libelle\":\"Concert\",\"date\":\"20/03/2014\","
					+ "\"listeDesTickets\": "
					+ "[{\"client\":{\"nom\":\"Jean-claude\",\"prenom\":\"Zinedine\"},\"code\":22 ,\"valide\":false},"
					+ "{\"client\":{\"nom\":\"Grosmelon\",\"prenom\":\"Jean-mich\"},\"code\":15 ,\"valide\":true}]},"
					+ "{\"libelle\":\"Magie\",\"date\":\"21/03/2014\","
					+ "\"listeDesTickets\": "
					+ "["
					+ "{\"client\":{\"nom\":\"Ferie\",\"prenom\":\"Tristan_bgdu33\"},\"code\":1 ,\"valide\":false},"
					+ "{\"client\":{\"nom\":\"Diouf\",\"prenom\":\"Adam\"},\"code\":20 ,\"valide\":true}"
					+ "]"
					+ "}"
					+ "]"
					+ "},"
					+ "{\"nom\":\"Meriadec\","
					+ "\"listeDesEvenements\":"
					+ "["
					+ "{\"libelle\":\"Danse\",\"date\":\"27/03/2014\","
					+ "\"listeDesTickets\": "
					+ "["
					+ "{\"client\":{\"nom\":\"Obahamas\",\"prenom\":\"Grandebarack\"},\"code\":22 ,\"valide\":false},"
					+ "{\"client\":{\"nom\":\"Pesse\",\"prenom\":\"Robert\"},\"code\":10 ,\"valide\":true}"
					+ "]"
					+ "},"
					+ "{\"libelle\":\"Theatre\",\"date\":\"01/04/2014\","
					+ "\"listeDesTickets\":"
					+ "["
					+ "{\"client\":{\"nom\":\"M�m�m�gros\",\"prenom\":\"Alexis\"},\"code\":1 ,\"valide\":false},"
					+ "{\"client\":{\"nom\":\"Lepompierdefer\",\"prenom\":\"Xavier\"},\"code\":20 ,\"valide\":true}"
					+ "]" + "}" + "]" + "}" + "]";

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
			Toast.makeText(this.getApplicationContext(),
					messageLogin.getMessage().toString(), Toast.LENGTH_SHORT)
					.show();
		}

	}

	private String recupererJSON() {
		String url = "https://trello-attachments.s3.amazonaws.com/5328073ffea1f3f032aa2352/533d0c222ee1771451f6ea76/cdcd7465eabe9ba7fa600b3e43f5be51/Json.json";
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
			Toast.makeText(this.getApplicationContext(),
					"No network connection available.", Toast.LENGTH_SHORT)
					.show();
		}
		return rep;
	}

	public void envoyerDonneeConnexion(String login, String mdp) {

		user user = new user(login, Hash.toSHA(mdp));
		user.toJSONLog();
		// TODO envoyez le JSON a la BDD

	}
}
