package esarc.bts.ticketscan.model.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

    @Override
    protected String doInBackground(final String... urls) {

	// params comes from the execute() call: params[0] is the url.
	try {
	    return Connection.downloadUrl(urls[0]);
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
    public static String downloadUrl(final String myurl) throws IOException {
	InputStream is = null;

	try {
	    URL url = new URL(myurl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    // Temps de lecture max en milliseconde
	    conn.setReadTimeout(10000);

	    // Temps de connexion max en milliseconde
	    conn.setConnectTimeout(15000);

	    // La methode de la requete HTTP
	    conn.setRequestMethod("GET");

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

    // Reads an InputStream and converts it to a String.
    private static String toString(final InputStream stream)
	    throws IOException, UnsupportedEncodingException {
	BufferedReader r = new BufferedReader(new InputStreamReader(stream));
	StringBuilder total = new StringBuilder();
	String line;
	while ((line = r.readLine()) != null) {
	    total.append(line);
	}
	return total.toString();
    }

}
