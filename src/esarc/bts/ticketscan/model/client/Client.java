package esarc.bts.ticketscan.model.client;

import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	private String nom;
	private String prenom;

	public Client(final String pNom, final String pPrenom) {
		super();
		this.setNom(pNom);
		this.setPrenom(pPrenom);
	}

	public final String getPrenom() {
		return prenom;
	}

	public final void setPrenom(final String pPrenom) {
		this.prenom = pPrenom;
	}

	public final String getNom() {
		return nom;
	}

	public final void setNom(final String pNom) {
		this.nom = pNom;
	}

	public static Client loadJson(final String json) throws JSONException {
		JSONObject jsonT = new JSONObject(json);
		return new Client(jsonT.getString("nom"), jsonT.getString("prenom"));
	}

}
