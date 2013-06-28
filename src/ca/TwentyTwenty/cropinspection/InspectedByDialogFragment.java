package ca.TwentyTwenty.cropinspection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class InspectedByDialogFragment extends DialogFragment implements
	DialogInterface.OnClickListener{
	
	private FieldActivity field_activity;
	private View form = null;
	Context ctxt;

	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.inspected_dialog, null);
		field_id = getArguments().getInt("field_id");
		list_title = getArguments().getString("list_title");
		this.list_position = getArguments().getInt("list_position");
		
		String list_title = getArguments().getString("list_title");
		int inspector_1_id = getArguments().getInt("inspector_1");
		int inspector_2_id = getArguments().getInt("inspector_2");
		
    	TextView inspector_1 = (EditText) form.findViewById(R.id.inspector_1);
    	TextView inspector_2 = (EditText) form.findViewById(R.id.inspector_2);
    	 
    	if (inspector_1_id != 0){
    		inspector_1.setText(String.valueOf(inspector_1_id));
    	}
    	
    	if (inspector_2_id != 0){
    		inspector_2.setText(String.valueOf(inspector_2_id));
    	}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		return(builder.setTitle(list_title).setView(form)
		        .setPositiveButton(android.R.string.ok, this)
		        .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		TextView inspector_1 = (TextView)form.findViewById(R.id.inspector_1);
		TextView inspector_2 = (TextView)form.findViewById(R.id.inspector_2);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		b.putString("inspector_1_id", inspector_1.getText().toString());
		b.putString("inspector_2_id", inspector_2.getText().toString());
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
			Log.w("stuff", b[0].toString());
			try {
				DatabaseHelper.getInstance(getActivity()).saveInspectedByDialog(b[0]);
				return b[0];
			} catch (Exception e){
				return new Bundle();
			}
		}
		
		@Override
		public void onPostExecute(Bundle b) {
			if (!b.isEmpty()) {
//				Log.w("ope", String.valueOf(list_position));
				Log.w("ope", b.toString());
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.CANADA);
				
				// update the UI
				FieldDetailItem fd = field_activity.fieldDetails.get(list_position);
				fd.list_item_middle = b.getString("inspector_1_id") + "/" + b.getString("inspector_2_id") + " on " + sdf.format(new Date(b.getLong("date_inspected")));
				ListView lv = (ListView) field_activity.findViewById(R.id.fielddetails);
				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
				ba.notifyDataSetChanged();
				
				// update the appropriate parts of the field object too
				if (!b.getString("inspector_1_id").isEmpty()) {
					field_activity.field_details.inspector_1_id = Integer.valueOf(b.getString("inspector_1_id"));
				} else {
					field_activity.field_details.inspector_1_id = 0;
				}
				
				if (!b.getString("inspector_2_id").isEmpty()) {				
					field_activity.field_details.inspector_2_id = Integer.valueOf(b.getString("inspector_2_id"));
				} else {
					field_activity.field_details.inspector_2_id = 0;
				}
				
				
				
				field_activity.field_details.date_inspected = b.getLong("date_inspected");
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}
}
