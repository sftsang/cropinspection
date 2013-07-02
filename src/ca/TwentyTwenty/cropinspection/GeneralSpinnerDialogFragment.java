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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;

public class GeneralSpinnerDialogFragment extends DialogFragment implements
	DialogInterface.OnClickListener{
	
	private FieldActivity field_activity;
	private View form = null;
	Context ctxt;

	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.spinner_dialog, null);
		field_id = getArguments().getInt("field_id");
		list_title = getArguments().getString("list_title");
		this.list_position = getArguments().getInt("list_position");
		
		String list_title = getArguments().getString("list_title");
		String crop_condition_uniformity = getArguments().getString("crop_condition_uniformity");
		String crop_condition_appearance = getArguments().getString("crop_condition_appearance");
		String crop_condition_weed = getArguments().getString("crop_condition_weed");
		String qa = getArguments().getString("qa");
		
    	String general_data = null;
    	
    	Spinner general_spinner = (Spinner)form.findViewById(R.id.general_spinner);
		List<String> spinner_list = new ArrayList<String>();
		spinner_list.add("");
		
		if (list_title == "Uniformity") {
			spinner_list.add("Above Average");
			spinner_list.add("Average");
			spinner_list.add("Below Average");
			general_data = crop_condition_uniformity;
		} else if (list_title == "Appearance") {
			spinner_list.add("Good");
			spinner_list.add("Fair");
			spinner_list.add("Poor");
			general_data = crop_condition_appearance;
		} else if (list_title == "Weed Condition") {
			spinner_list.add("None found");
			spinner_list.add("Trace");
			spinner_list.add("Few");
			spinner_list.add("Numerous");
			spinner_list.add("Very Weedy");
			general_data = crop_condition_weed;
		} else if (list_title == "QA") {
			spinner_list.add("Good");
			spinner_list.add("Fair");
			general_data = qa;
		} 
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinner_list); 
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		general_spinner.setAdapter(dataAdapter);
		
		// set off_type/weed spinner to the correct value
		if (general_data != "") {
			general_spinner.setSelection(dataAdapter.getPosition(general_data));
		} else {
			general_spinner.setSelection(dataAdapter.getPosition(""));
		}
		
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		
		return(builder.setTitle(list_title).setView(form)
		        .setPositiveButton(android.R.string.ok, this)
		        .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		Spinner general_spinner = (Spinner)form.findViewById(R.id.general_spinner);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		b.putString("general_data", general_spinner.getSelectedItem().toString());
		b.putString("list_title", list_title);
	
		// spin up bg thread to save to db
		AsyncSaver saver = new AsyncSaver();
	    saver.execute(b);	
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
//			Log.w("stuff", b[0].toString());
			try {
				DatabaseHelper.getInstance(getActivity()).saveGeneralSpinnerDialog(b[0]);
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
				fd.list_item_middle = b.getString("general_data");
				ListView lv = (ListView) field_activity.findViewById(R.id.fielddetails);
				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
				ba.notifyDataSetChanged();
				
				// update the appropriate parts of the field object too
				String list_title = b.getString("list_title");
				if (list_title == "Uniformity" ){
					field_activity.field_details.crop_condition_uniformity = b.getString("general_data");
				} else if (list_title == "Appearance" ){
					field_activity.field_details.crop_condition_appearance = b.getString("general_data");
				} else if (list_title == "Weed Condition" ){
					field_activity.field_details.crop_condition_weed = b.getString("general_data");
				} else if (list_title == "QA" ){
					field_activity.field_details.qa = b.getString("general_data");
				}
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}
}
