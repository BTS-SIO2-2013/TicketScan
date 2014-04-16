package esarc.bts.ticketscan.model.message;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageLogin {

    private boolean autOk;
    private String  message;

    public final boolean isAutOk() {
        return autOk;
    }

    public final void setAutOk(final boolean pAutOk) {
        this.autOk = pAutOk;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String pMessage) {
        this.message = pMessage;
    }

    public MessageLogin(final boolean pAutOk, final String pMessage) {
        this.autOk = pAutOk;
        this.message = pMessage;
    }

    public MessageLogin() {
        this.autOk = false;
        this.message = "Non instancier";
    }

    public static MessageLogin fromJSON(final String json) throws JSONException {
        MessageLogin messagelogin = new MessageLogin();

        JSONObject jsonObj = new JSONObject(json);

        messagelogin.setAutOk(jsonObj.getBoolean("autOk"));
        messagelogin.setMessage(jsonObj.getString("message"));

        return messagelogin;

    }
}
