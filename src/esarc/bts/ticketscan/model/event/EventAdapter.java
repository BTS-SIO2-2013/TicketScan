package esarc.bts.ticketscan.model.event;

import java.text.SimpleDateFormat;
import java.util.List;

import esarc.bts.ticketscan.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event> {

	public EventAdapter(Context context, List<Event> event) {
		super(context,R.layout.eventview , event);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			View ligne = convertView;
		
			if (ligne == null){
			LayoutInflater inflater = (LayoutInflater) getContext()
				        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
			ligne = inflater.inflate(R.layout.eventview, parent,false);
			}
			
			
			TextView libelle = (TextView) ligne.findViewById(R.id.textNom);
			TextView dateEvent = (TextView) ligne.findViewById(R.id.textDate);
			
			Event event = getItem(position);
			
			if (libelle != null && event != null){
				libelle.setText(event.getLibelle());
			}
			if (dateEvent != null && event !=null ){
				dateEvent.setText(event.getDateEvent().toString());
			}
			
			return ligne;
	}
}
