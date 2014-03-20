package esarc.bts.ticketscan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class logActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_log);
	}

	public void onClick(View v) {
		EditText txtNom = (EditText) this.findViewById(R.id.txtNom);
		EditText txtMDP = (EditText) this.findViewById(R.id.txtMDP);

		Toast.makeText(this.getApplicationContext(),
				"ID: " + txtNom.getText() + "\nMDP: " + txtMDP.getText(),
				Toast.LENGTH_SHORT).show();

	}
}
