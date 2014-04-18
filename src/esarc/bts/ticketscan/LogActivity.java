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
import esarc.bts.ticketscan.model.user.User;

public class LogActivity extends Activity {
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_log);
    }

    // Se produit lorsqu'on clique sur le bouton de connexion
    public final void onClick(final View v) {
        EditText txtNom = (EditText) this.findViewById(R.id.txtNom);
        EditText txtMDP = (EditText) this.findViewById(R.id.txtMDP);

        // Envoie des donnees (JSON) au serveur
        String json = this.envoyerDonneeConnexion(txtNom.getText().toString(),
                txtMDP.getText().toString());
        System.out.println("Json: " + json);
        // Verification des donnees recus
        this.verifier(json);
    }

    public final void onClickTxtMDPOublie(final View v) {
        Toast.makeText(this.getApplicationContext(), "Coucou ",
                Toast.LENGTH_SHORT).show();
    }

    public final void verifier(final String json) {
        MessageLogin messageLogin = new MessageLogin();
        ProgressBar progressBar = (ProgressBar) this
                .findViewById(R.id.progressBarLog);

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
        String url = "https://trello-attachments.s3.amazonaws.com"
                + "/5328073ffea1f3f032aa2352/533d0c222ee1771451f6ea76/"
                + "0ebd22b00f5416e8aa2f607e16f1a84d/Json.json";
        String json = this.connexion(url, "GET", null, null);
        return json;
    }

    public final String envoyerDonneeConnexion(final String login,
            final String mdp) {

        User user = new User(login, Hash.toSHA(mdp));
        user.toJSONLog();

        ProgressBar progressBar = (ProgressBar) this
                .findViewById(R.id.progressBarLog);

        // On affiche la progress bar
        progressBar.setVisibility(View.VISIBLE);

        // TODO envoyez les données par URL
        String url = "http://172.16.1.39/BilletMaster/mobile/connection.php";
        String json = this.connexion(url, "POST", user.getLogin(),
                user.getMdp());
        return json;
    }

    private String connexion(final String url, final String method,
            final String id, final String mdp) {
        String rep = "";

        // Pour le localhost IP: 10.0.2.2 (correspond a l'emulateur)
        // String url = "http://10.0.2.2/test.json";

        if (Connection.isNetworkAvailable(this)) {
            try {
                rep = new Connection(id, mdp, method).execute(url).get();
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
}
