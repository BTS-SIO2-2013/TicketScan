package esarc.bts.ticketscan.model.user;

public class User {
    private int    id;
    private String nom;
    private String prenom;
    private String mail;
    private String telephone;
    private String login;
    private String mdp;

    public final int getId() {
        return this.id;
    }

    public final void setId(final int pId) {
        this.id = pId;
    }

    public final String getNom() {
        return this.nom;
    }

    public final void setNom(final String pNom) {
        this.nom = pNom;
    }

    public final String getPrenom() {
        return this.prenom;
    }

    public final void setPrenom(final String pPrenom) {
        this.prenom = pPrenom;
    }

    public final String getMail() {
        return this.mail;
    }

    public final void setMail(final String pMail) {
        this.mail = pMail;
    }

    public final String getTelephone() {
        return this.telephone;
    }

    public final void setTelephone(final String pTelephone) {
        this.telephone = pTelephone;
    }

    public final String getLogin() {
        return this.login;
    }

    public final void setLogin(final String pLogin) {
        this.login = pLogin;
    }

    public final String getMdp() {
        return this.mdp;
    }

    public final void setMdp(final String pMdp) {
        this.mdp = pMdp;
    }

    public User() {
        this.id = -1;
        this.nom = "";
        this.prenom = "";
        this.mail = "";
        this.telephone = "";
        this.login = "";
        this.mdp = "";
    }

    public User(final String pLogin, final String pMdp) {
        this.id = -1;
        this.nom = "";
        this.prenom = "";
        this.mail = "";
        this.telephone = "";
        this.login = pLogin;
        this.mdp = pMdp;
    }

    public User(final int pId, final String pNom, final String pPrenom,
            final String pMail, final String pTelephone, final String pLogin,
            final String pMdp) {
        this.id = pId;
        this.nom = pNom;
        this.prenom = pPrenom;
        this.mail = pMail;
        this.telephone = pTelephone;
        this.login = pLogin;
        this.mdp = pMdp;
    }

    public final String toJSONFull() {
        String json = "{\"id\":" + this.getId() + "," + "\"nom\":\""
                + this.getNom() + "\"," + "\"prenom\":\"" + this.getPrenom()
                + "\"," + "\"mail\":\"" + this.getMail() + "\","
                + "\"telephone\":\"" + this.getTelephone() + "\","
                + "\"login\":\"" + this.getLogin() + "\"," + "\"mdp\":\""
                + this.getMdp() + "\"}";

        System.out.println(json);
        return json;
    }

    public final String toJSONLog() {
        String json = "{\"login\":\"" + this.getLogin() + "\"," + "\"mdp\":\""
                + this.getMdp() + "\"}";

        System.out.println(json);
        return json;
    }
}
