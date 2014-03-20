package esarc.bts.ticketscan.model.user;

public class user {
	private int id;
	private String nom;
	private String prenom;
	private String mail;
	private String telephone;
	private String login;
	private String mdp;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return this.mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public user() {
		this.id = -1;
		this.nom = "";
		this.prenom = "";
		this.mail = "";
		this.telephone = "";
		this.login = "";
		this.mdp = "";
	}

	public user(String login, String mdp) {
		this.id = -1;
		this.nom = "";
		this.prenom = "";
		this.mail = "";
		this.telephone = "";
		this.login = login;
		this.mdp = mdp;
	}

	public user(int id, String nom, String prenom, String mail,
			String telephone, String login, String mdp) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.telephone = telephone;
		this.login = login;
		this.mdp = mdp;
	}

	public String toJSONFull() {
		String json = "{\"id\":" + this.getId() + "," + "\"nom\":\""
				+ this.getNom() + "\"," + "\"prenom\":\"" + this.getPrenom()
				+ "\"," + "\"mail\":\"" + this.getMail() + "\","
				+ "\"telephone\":\"" + this.getTelephone() + "\","
				+ "\"login\":\"" + this.getLogin() + "\"," + "\"mdp\":\""
				+ this.getMdp() + "\"}";

		System.out.println(json);
		return json;
	}

	public String toJSONLog() {
		String json = "{\"login\":\"" + this.getLogin() + "\"," + "\"mdp\":\""
				+ this.getMdp() + "\"}";

		System.out.println(json);
		return json;
	}
}
