package ca.TwentyTwenty.cropinspection;

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
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;
import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;

public class ReadyFieldDialogFragment extends DialogFragment implements
	DialogInterface.OnClickListener {
		
	private FieldActivity field_activity;
	private View form = null;
	Context ctxt;

	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.field_ready_dialog, null);
		field_id = getArguments().getInt("field_id");
		list_title = getArguments().getString("list_title");
		this.list_position = getArguments().getInt("list_position");
		
		String date_ready = getArguments().getString("date_ready");
		String date_ready_to = getArguments().getString("date_ready_to");
    	
        DatePicker date_ready_pick = (DatePicker)form.findViewById(R.id.date_ready);
    	
    	// make sure we don't crash if this is a new field
    	if (!date_ready.isEmpty()){
    		String[] new_date_ready = date_ready.split("-");
    		int year = (int)Integer.valueOf(new_date_ready[0]);
    		int month = (int)Integer.valueOf(new_date_ready[1])-1; // date picker months start at 0 
    		int day = (int)Integer.valueOf(new_date_ready[2]);
    		date_ready_pick.updateDate(year, month, day);
    	}
    	
    	
    	DatePicker date_ready_to_pick = (DatePicker)form.findViewById(R.id.date_ready_to);
    	    	
    	// make sure we don't crash if this is a new field
    	if (!date_ready_to.isEmpty()){
			String[] new_date_ready = date_ready_to.split("-");
			int year = (int)Integer.valueOf(new_date_ready[0]);
			int month = (int)Integer.valueOf(new_date_ready[1])-1;  // date picker months start at 0
			int day = (int)Integer.valueOf(new_date_ready[2]);
			date_ready_to_pick.updateDate(year, month, day);
		}    	
		
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		
		return(builder.setTitle(list_title).setView(form)
		        .setPositiveButton(android.R.string.ok, this)
		        .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		DatePicker date_ready_pick = (DatePicker)form.findViewById(R.id.date_ready);
		DatePicker date_ready_to_pick = (DatePicker)form.findViewById(R.id.date_ready_to);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		// months in date picker start from 0(!) so we have to adjust this...
		String month_ready = String.valueOf((int)date_ready_pick.getMonth()+1);
		String month_ready_to = String.valueOf((int)date_ready_pick.getMonth()+1);
		
		b.putString("date_ready", String.valueOf(date_ready_pick.getYear() + "-" + month_ready + "-" +date_ready_pick.getDayOfMonth()));
		b.putString("date_ready_to", String.valueOf(date_ready_to_pick.getYear() + "-" + month_ready_to + "-" + date_ready_to_pick.getDayOfMonth()));
	
		// spin up bg thread to save to db
		AsyncSaver saver = new AsyncSaver();
	    saver.execute(b);
	}
	
	@Override
	public void onDismiss(DialogInterface unused) {
	  super.onDismiss(unused);
	  
//	  Log.d(getClass().getSimpleName(), "Goodbye!");
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
				DatabaseHelper.getInstance(getActivity()).saveFieldReadyDialog(b[0]);
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
				fd.list_item_middle = b.getString("date_ready") + " to " + b.getString("date_ready_to");
				ListView lv = (ListView) field_activity.findViewById(R.id.fielddetails);
				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
				ba.notifyDataSetChanged();
				
				// update the appropriate parts of the field object too				
				field_activity.field_details.date_ready = b.getString("date_ready");
				field_activity.field_details.date_ready_to = b.getString("date_ready_to");
				
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}
}
