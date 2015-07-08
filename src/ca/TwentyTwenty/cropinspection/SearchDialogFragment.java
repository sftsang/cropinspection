package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchDialogFragment extends DialogFragment implements
  DialogInterface.OnClickListener {
	
	private View form = null;
	private CropInspectionActivity map_activity;
	private SharedPreferences prefs;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	  form = getActivity().getLayoutInflater().inflate(R.layout.search_dialog, null);
	  prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	  
	  // Customer search field
	  Spinner customer_spinner = (Spinner)form.findViewById(R.id.customer_spinner);
	  List<String> customer_list = new ArrayList<String>();
	  customer_list.add("All");
	  customer_list.add("Bayer");
	  customer_list.add("Monsanto");
	  customer_list.add("Pioneer");
	  customer_list.add("Dow");
	  customer_list.add("HyTech");
	  customer_list.add("Other");
	  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, customer_list); 
	  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  customer_spinner.setAdapter(dataAdapter);
	  String stored_search_customer = prefs.getString("search_customer", "");
	  customer_spinner.setSelection(dataAdapter.getPosition(stored_search_customer));
	  
	  // Keyword search field
	  EditText search_filter = (EditText) form.findViewById(R.id.search_filter);
	  search_filter.setText(prefs.getString("search_filter", ""));
	  
	  // Field status search field
	  Spinner status_spinner = (Spinner)form.findViewById(R.id.status_spinner);
	  List<String> status_list = new ArrayList<String>();
	  status_list.add("All");
	  status_list.add("Fields Assigned To Me");
	  status_list.add("Inspection Completed");
	  status_list.add("Field Not Ready");
	  status_list.add("Field Ready Expired");
	  status_list.add("Field Reinspection");
	  status_list.add("Field Ready");
	  status_list.add("Inspection Pending Review");
	  dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, status_list); 
	  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  status_spinner.setAdapter(dataAdapter);
	  String stored_search_status = prefs.getString("search_status", "");
	  status_spinner.setSelection(dataAdapter.getPosition(stored_search_status));
	
	  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
	  return(builder.setTitle("Field Search").setView(form)
	                .setPositiveButton(android.R.string.ok, this)
	                .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {		
		Spinner customer_spinner = (Spinner)form.findViewById(R.id.customer_spinner);
		EditText search_filter = (EditText)form.findViewById(R.id.search_filter);
		Spinner status_spinner = (Spinner)form.findViewById(R.id.status_spinner);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putString("customer", customer_spinner.getSelectedItem().toString());
		b.putString("search_filter", search_filter.getEditableText().toString());
		b.putString("status", status_spinner.getSelectedItem().toString());
		b.putString("uid", prefs.getString("uid", null));
		
		// while we are at it remember these choices for next time
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("search_customer", customer_spinner.getSelectedItem().toString());
		editor.putString("search_filter", search_filter.getEditableText().toString());
		editor.putString("search_status", status_spinner.getSelectedItem().toString());		
        editor.commit();
      
//        Log.w("auth",json.getJSONObject("data").getString("uid"));
        
		// spin up bg thread and find in db
		AsyncSearch search = new AsyncSearch();
		search.execute(b);		
		
		//Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onDismiss(DialogInterface unused) {
	  super.onDismiss(unused);
	  
//	  Log.d(getClass().getSimpleName(), "Goodbye!");
	}
	
	@Override
	public void onCancel(DialogInterface unused) {
	  super.onCancel(unused);
	  
//	  Toast.makeText(getActivity(), R.string.back, Toast.LENGTH_LONG).show();
	}
	
	// DO THIS EVERYWHERE
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			map_activity = (CropInspectionActivity) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + "Not CropInspectionActivity class instance");
		}
	}
	
	private class AsyncSearch extends AsyncTask<Bundle, Void, ArrayList<Field>> {
		ArrayList<Field> fields = new ArrayList<Field>();
		
		@Override
		protected ArrayList<Field> doInBackground(Bundle... b) {
			//Log.w("stuff", b[0].toString());
			try {
				fields = DatabaseHelper.getInstance(getActivity()).searchFieldsDialog(b[0]);
			} catch (Exception e){
				Log.w("error searching", "Something went wrong with the search");
			}
			return fields;
		}
		
		@Override
		public void onPostExecute(ArrayList<Field> fields) {
			if (!fields.isEmpty()) {
				map_activity.map.clear();
				map_activity.setupFields(fields);
			} else {
				Toast.makeText(map_activity.getApplicationContext(), "No fields found.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
}
