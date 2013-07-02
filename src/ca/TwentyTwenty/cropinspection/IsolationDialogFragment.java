package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;

public class IsolationDialogFragment extends DialogFragment implements
	DialogInterface.OnClickListener{
	
	private FieldActivity field_activity;
	private View form;
	Context ctxt;
	
	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.isolation_dialog, null);
		ctxt = this.getActivity();
  
		this.list_position = getArguments().getInt("list_position");
		field_id = getArguments().getInt("field_id");
		
		list_title = getArguments().getString("list_title");
		String east_isolation_size = getArguments().getString("east_isolation_size");
		String west_isolation_size = getArguments().getString("west_isolation_size");
		String south_isolation_size = getArguments().getString("south_isolation_size");
		String north_isolation_size = getArguments().getString("north_isolation_size");
		
		String east_isolation_type = getArguments().getString("east_isolation_type");
		String west_isolation_type = getArguments().getString("west_isolation_type");
		String south_isolation_type = getArguments().getString("south_isolation_type");
		String north_isolation_type = getArguments().getString("north_isolation_type");
		
		String east_isolation_condition = getArguments().getString("east_isolation_condition");
		String west_isolation_condition = getArguments().getString("west_isolation_condition");
		String south_isolation_condition = getArguments().getString("south_isolation_condition");
		String north_isolation_condition = getArguments().getString("north_isolation_condition");
		
		String east_isolation_crop = getArguments().getString("east_isolation_crop");
		String west_isolation_crop = getArguments().getString("west_isolation_crop");
		String south_isolation_crop = getArguments().getString("south_isolation_crop");
		String north_isolation_crop = getArguments().getString("north_isolation_crop");
		
		Spinner isolation_distance_spinner = (Spinner)form.findViewById(R.id.isolation_size);
		List<String> isolation_distance = new ArrayList<String>();
		isolation_distance.add("1m");
		isolation_distance.add("2m");
		isolation_distance.add("3m");
		isolation_distance.add("4m");
		isolation_distance.add("5m");
		isolation_distance.add("6m");
		isolation_distance.add("7m");
		isolation_distance.add("8m");
		isolation_distance.add("9m");
		isolation_distance.add("10m");
		isolation_distance.add("11m");
		isolation_distance.add("12m");
		isolation_distance.add("13m");
		isolation_distance.add("14m");
		isolation_distance.add("15m");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, isolation_distance); 
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		isolation_distance_spinner.setAdapter(dataAdapter);
		
		// set isolation distance spinner to the correct value
		if (list_title == "East") {
		isolation_distance_spinner.setSelection(dataAdapter.getPosition(east_isolation_size + "m"));
		} else if (list_title == "West") {
		isolation_distance_spinner.setSelection(dataAdapter.getPosition(west_isolation_size + "m"));
		} else if (list_title == "South") {
		isolation_distance_spinner.setSelection(dataAdapter.getPosition(south_isolation_size + "m"));
		} else if (list_title == "North") {
		isolation_distance_spinner.setSelection(dataAdapter.getPosition(north_isolation_size + "m"));
		}
		
		Spinner isolation_type_spinner = (Spinner)form.findViewById(R.id.isolation_type);
		List<String> isolation_type = new ArrayList<String>();
		isolation_type.add("Sod");
		isolation_type.add("Cultivated");
		isolation_type.add("Chem Fallow");
		isolation_type.add("Clean Crop");
		isolation_type.add("Mowed");
		isolation_type.add("Track");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, isolation_type); 
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		isolation_type_spinner.setAdapter(dataAdapter);
		
		// set isolation type spinner to the correct value
		if (list_title == "East") {
			isolation_type_spinner.setSelection(dataAdapter.getPosition(east_isolation_type));
		} else if (list_title == "West") {
			isolation_type_spinner.setSelection(dataAdapter.getPosition(west_isolation_type));
		} else if (list_title == "South") {
			isolation_type_spinner.setSelection(dataAdapter.getPosition(south_isolation_type));
		} else if (list_title == "North") {
			isolation_type_spinner.setSelection(dataAdapter.getPosition(north_isolation_type));
		}
		
		Spinner isolation_condition_spinner = (Spinner)form.findViewById(R.id.isolation_condition);
		List<String> isolation_condition = new ArrayList<String>();
		isolation_condition.add("Good");
		isolation_condition.add("Fair");
		isolation_condition.add("Poor");
		dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, isolation_condition); 
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		isolation_condition_spinner.setAdapter(dataAdapter);
		
		// set isolation condition spinner to the correct value
		if (list_title == "East") {
			isolation_condition_spinner.setSelection(dataAdapter.getPosition(east_isolation_condition));
		} else if (list_title == "West") {
			isolation_condition_spinner.setSelection(dataAdapter.getPosition(west_isolation_condition));
		} else if (list_title == "South") {
			isolation_condition_spinner.setSelection(dataAdapter.getPosition(south_isolation_condition));
		} else if (list_title == "North") {
			isolation_condition_spinner.setSelection(dataAdapter.getPosition(north_isolation_condition));
		}
      
      TextView isolation_statement = (EditText) form.findViewById(R.id.isolation_statement);
		if (list_title == "East") {
		   isolation_statement.setText(east_isolation_crop);
		} else if (list_title == "West") {
			isolation_statement.setText(west_isolation_crop);
		} else if (list_title == "South") {
			isolation_statement.setText(south_isolation_crop);
		} else if (list_title == "North") {
			isolation_statement.setText(north_isolation_crop);
		}
	  
	  AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
	  
	  return(builder.setTitle(list_title).setView(form)
              .setPositiveButton(android.R.string.ok, this)
              .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		Spinner isolation_size = (Spinner)form.findViewById(R.id.isolation_size);
		Spinner isolation_condition = (Spinner)form.findViewById(R.id.isolation_condition);
		Spinner isolation_type = (Spinner)form.findViewById(R.id.isolation_type);
		EditText isolation_crop = (EditText)form.findViewById(R.id.isolation_statement);
		
		// need to get rid of m for size
		String iso_size = isolation_size.getSelectedItem().toString();
		String db_iso_size = iso_size.substring(0, iso_size.length() - 1);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		b.putString("isolation_condition", isolation_condition.getSelectedItem().toString());
		b.putString("isolation_crop", isolation_crop.getText().toString());
		b.putString("isolation_size", db_iso_size);
		b.putString("isolation_type", isolation_type.getSelectedItem().toString());
		b.putString("list_title", list_title);
		
		// spin up bg thread to save to db
		AsyncSaver saver = new AsyncSaver();
	    saver.execute(b);
		
//		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onDismiss(DialogInterface unused) {
	  super.onDismiss(unused);
	  Log.d(getClass().getSimpleName(), "Goodbye!");
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			field_activity = (FieldActivity) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + "Not FieldActivity class instance");
		}
	}
	
	@Override
	public void onCancel(DialogInterface unused) {
	  super.onCancel(unused);
	  Toast.makeText(getActivity(), R.string.back, Toast.LENGTH_LONG).show();
	}
	
	
	private class AsyncSaver extends AsyncTask<Bundle, Void, Bundle> {

		@Override
		protected Bundle doInBackground(Bundle... b) {
			try {
				DatabaseHelper.getInstance(getActivity()).saveIsolationDialog(b[0]);
				return b[0];
			} catch (Exception e){
				return new Bundle();
			}
		}
		
		@Override
		public void onPostExecute(Bundle b) {
			if (!b.isEmpty()) {
				
				// update the UI
				FieldDetailItem fd = field_activity.fieldDetails.get(list_position);
				fd.list_item_middle = b.getString("isolation_size") + "m / " + b.getString("isolation_type") + " / " + b.getString("isolation_condition");
				fd.list_item_bottom = b.getString("isolation_crop");
				ListView lv = (ListView) field_activity.findViewById(R.id.fielddetails);
				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
				ba.notifyDataSetChanged();
				
				// update the appropriate parts of the field object too
				String list_title = b.getString("list_title");
				if (list_title == "East") {
					field_activity.field_details.east_isolation_size = b.getString("isolation_size");
					field_activity.field_details.east_isolation_type = b.getString("isolation_type");
					field_activity.field_details.east_isolation_condition = b.getString("isolation_condition");
					field_activity.field_details.east_isolation_crop = b.getString("isolation_crop");
				} else if (list_title == "West") {
					field_activity.field_details.west_isolation_size = b.getString("isolation_size");
					field_activity.field_details.west_isolation_type = b.getString("isolation_type");
					field_activity.field_details.west_isolation_condition = b.getString("isolation_condition");
					field_activity.field_details.west_isolation_crop = b.getString("isolation_crop");
				} else if (list_title == "South") {
					field_activity.field_details.south_isolation_size = b.getString("isolation_size");
					field_activity.field_details.south_isolation_type = b.getString("isolation_type");
					field_activity.field_details.south_isolation_condition = b.getString("isolation_condition");
					field_activity.field_details.south_isolation_crop = b.getString("isolation_crop");
				} else if (list_title == "North") {
					field_activity.field_details.north_isolation_size = b.getString("isolation_size");
					field_activity.field_details.north_isolation_type = b.getString("isolation_type");
					field_activity.field_details.north_isolation_condition = b.getString("isolation_condition");
					field_activity.field_details.north_isolation_crop = b.getString("isolation_crop");
				}
				
//				Toast.makeText(ctxt, String.valueOf(b.getLong("record_modified_at")), Toast.LENGTH_LONG).show();
				Log.w("record_modified_at", String.valueOf(b.getLong("record_modified_at")));
				
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}
}
