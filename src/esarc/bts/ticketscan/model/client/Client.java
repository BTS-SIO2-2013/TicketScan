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
        return this.prenom;
    }

    public final void setPrenom(final String pPrenom) {
        this.prenom = pPrenom;
    }

    public final String getNom() {
        return this.nom;
    }

    public final void setNom(final String pNom) {
        this.nom = pNom;
    }

    public static Client loadJson(final String json) throws JSONException {
        JSONObject jsonT = new JSONObject(json);
        return new Client(jsonT.getString("nom"), jsonT.getString("prenom"));
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if (this.nom == null) {
            result += 0;
        } else {
            result += this.nom.hashCode();
        }
        result = prime * result;
        if (this.prenom == null) {
            result += 0;
        } else {
            result += this.prenom.hashCode();
        }
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Client other = (Client) obj;
        if (this.nom == null) {
            if (other.nom != null) {
                return false;
            }
        } else if (!this.nom.equals(other.nom)) {
            return false;
        }
        if (this.prenom == null) {
            if (other.prenom != null) {
                return false;
            }
        } else if (!this.prenom.equals(other.prenom)) {
            return false;
        }
        return true;
    }

}
