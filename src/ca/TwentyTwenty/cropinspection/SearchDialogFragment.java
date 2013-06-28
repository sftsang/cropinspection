package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	  form = getActivity().getLayoutInflater().inflate(R.layout.search_dialog, null);
	  
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
	  
	  EditText search_filter = (EditText) form.findViewById(R.id.search_filter);
	  
	  Spinner status_spinner = (Spinner)form.findViewById(R.id.status_spinner);
	  List<String> status_list = new ArrayList<String>();
	  status_list.add("All");
	  status_list.add("Field Assigned");
	  status_list.add("Inspection Completed");
	  status_list.add("Field Not Ready");
	  status_list.add("Field Ready Expired");
	  status_list.add("Field Reinspection");
	  status_list.add("Field Ready");
	  status_list.add("Inspection Pending Review");
	  dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, status_list); 
	  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  status_spinner.setAdapter(dataAdapter);
	
	  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
	  return(builder.setTitle("Field Search").setView(form)
	                .setPositiveButton(android.R.string.ok, this)
	                .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
//		((AbstractMapActivity) getActivity()).f dismiss(R.layout.search_dialog);
		// clear map
		// do a search
		// add markers to map
		// dismiss the dialog
//		this.getFragmentManager().findFragmentById(layout.main).getActivity().		
		Spinner customer_spinner = (Spinner)form.findViewById(R.id.customer_spinner);
		EditText search_filter = (EditText)form.findViewById(R.id.search_filter);
		Spinner status_spinner = (Spinner)form.findViewById(R.id.status_spinner);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putString("customer", customer_spinner.getSelectedItem().toString());
		b.putString("search_filter", search_filter.getEditableText().toString());
		b.putString("status", status_spinner.getSelectedItem().toString());
	
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
		ArrayList<Field> fields;
		
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
				// update the UI
//				FieldDetailItem fd = ((FieldActivity) getActivity()).fieldDetails.get(list_position);
//				fd.list_item_middle = String.valueOf(b.getInt("type_p1")) + "/" + String.valueOf(b.getInt("type_p2")) + "/" + String.valueOf(b.getInt("type_p3")) + "/" 
//									  + String.valueOf(b.getInt("type_p4")) + "/" + String.valueOf(b.getInt("type_p5")) + "/" + String.valueOf(b.getInt("type_p6"));
//				fd.list_item_bottom = b.getString("type_name");
//				ListView lv = (ListView) ((FieldActivity) getActivity()).findViewById(R.id.fielddetails);
//				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
//				ba.notifyDataSetChanged();
//				
//				// update the appropriate parts of the field object too
//				String list_title = b.getString("list_title");
//				if (list_title == "Off-type 1") {
//					((FieldActivity) getActivity()).field_details.other_crop1_count1 = b.getInt("type_p1");
//					((FieldActivity) getActivity()).field_details.weed2_name = b.getString("type_name");
//				}
			} else {
				Toast.makeText(map_activity.getApplicationContext(), "No fields found.", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
}
