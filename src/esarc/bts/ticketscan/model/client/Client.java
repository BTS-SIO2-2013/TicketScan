package esarc.bts.ticketscan.model.client;

import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	private String nom;
	private String prenom;

	public Client (String nom, String prenom){
		super();
		this.setNom(nom);
		this.setPrenom(prenom);
		}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public static Client loadJson(String json) throws JSONException {
		JSONObject jsonT = new JSONObject(json);
		return new Client(jsonT.getString("nom"),jsonT.getString("prenom"));
	}
	
}
