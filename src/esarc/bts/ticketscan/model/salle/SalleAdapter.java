package esarc.bts.ticketscan.model.salle;

import java.util.List;

import esarc.bts.ticketscan.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SalleAdapter extends ArrayAdapter<Salle> {
	
	public SalleAdapter(Context context, List<Salle> salle) {
		super(context,R.layout.salleview , salle);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			View ligne = convertView;
		
			if (ligne == null){
			LayoutInflater inflater = (LayoutInflater) getContext()
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
			ligne = inflater.inflate(R.layout.salleview, parent,false);
			}
			
			
			TextView nom = (TextView) ligne.findViewById(R.id.textNom);
			TextView adresse = (TextView) ligne.findViewById(R.id.textAdresse);
			
			Salle salle = getItem(position);
			
			if (nom != null && salle != null){
				nom.setText(salle.getNom());
			}
			if (adresse != null && salle !=null ){
				adresse.setText(salle.getAdresse());
			}
			
			return ligne;
	}

}
