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
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;
import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;

public class PlantsM2DialogFragment  extends DialogFragment implements
	DialogInterface.OnClickListener{
		
	private FieldActivity field_activity;
	private View form = null;
	Context ctxt;

	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.picker_dialog, null);
		field_id = getArguments().getInt("field_id");
		list_title = getArguments().getString("list_title");
		this.list_position = getArguments().getInt("list_position");
		
		String list_title = getArguments().getString("list_title");
		String plants_per_m2 = getArguments().getString("plants_per_m2");
		
		NumberPicker plants_m2 = (NumberPicker)form.findViewById(R.id.general_picker);
		plants_m2.setMaxValue(100);
    	plants_m2.setMinValue(0);
    	plants_m2.setWrapSelectorWheel(false);
    	plants_m2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    	if (!plants_per_m2.isEmpty()){
    		plants_m2.setValue(Integer.valueOf(plants_per_m2));
    	}
    	
		
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		
		return(builder.setTitle(list_title).setView(form)
		        .setPositiveButton(android.R.string.ok, this)
		        .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		NumberPicker plants_per_m2 = (NumberPicker)form.findViewById(R.id.general_picker);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		b.putString("plants_per_m2", String.valueOf(plants_per_m2.getValue()));
	
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
				DatabaseHelper.getInstance(getActivity()).saveGeneralPickerDialog(b[0]);
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
				fd.list_item_middle = b.getString("plants_per_m2");
				ListView lv = (ListView) field_activity.findViewById(R.id.fielddetails);
				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
				ba.notifyDataSetChanged();
				
				// update the appropriate parts of the field object too				
				field_activity.field_details.plants_per_m2 = b.getString("plants_per_m2");
				
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}


}
