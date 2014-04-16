package esarc.bts.ticketscan.model.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

//Uses AsyncTask to create a task away from the main UI thread. This
// task takes a
// URL string and uses it to create an HttpUrlConnection. Once the
// connection
// has been established, the AsyncTask downloads the contents of the
// webpage as
// an InputStream. Finally, the InputStream is converted into a string,
// which is
// displayed in the UI by the AsyncTask's onPostExecute method.
public class Connection extends AsyncTask<String, Void, String> {

    private static final int CONNECTTIMEOUT = 15000;
    private static final int TIMEOUT        = 10000;
    private String           id;
    private String           mdp;
    private String           methode;

    public Connection(final String pId, final String pMdp, final String pMethode) {
        this.id = pId;
        this.mdp = pMdp;
        this.setMethode(this.methode);
    }

    public Connection() {
        this.id = null;
        this.mdp = null;
        this.setMethode("GET");
    }

    @Override
    protected final String doInBackground(final String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return Connection.downloadUrl(urls[0], this.getMethode(),
                    this.getId(), this.getMdp());
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(final String result) {
        // System.out.println(result);
    }

    public static boolean isNetworkAvailable(final Context c) {
        boolean rep;

        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            rep = true;

        } else {
            rep = false;
        }

        System.out.println("isNetworkAvailable = " + rep);
        return rep;
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.
    public static String downloadUrl(final String myurl, final String methode,
            final String id, final String mdp) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Temps de lecture max en milliseconde

            conn.setReadTimeout(TIMEOUT);

            // Temps de connexion max en milliseconde
            conn.setConnectTimeout(CONNECTTIMEOUT);

            // La methode de la requete HTTP
            if (methode == "POST") {
                conn.setRequestMethod("POST");
                conn.setRequestProperty("id", id);
                conn.setRequestProperty("mdp", mdp);

            } else {
                conn.setRequestMethod("GET");
            }

            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();

            System.out.println("The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = toString(is);
            is.close();
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public final String getId() {
        return this.id;
    }

    public final void setId(final String pId) {
        this.id = pId;
    }

    public final String getMdp() {
        return this.mdp;
    }

    public final void setMdp(final String pMdp) {
        this.mdp = pMdp;
    }

    // Reads an InputStream and converts it to a String.
    private static String toString(final InputStream stream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    public final String getMethode() {
        return this.methode;
    }

    public final void setMethode(final String pMethode) {
        this.methode = pMethode;
    }
}
