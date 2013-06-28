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

public class FlowerSyncDialogFragment extends DialogFragment implements
	DialogInterface.OnClickListener{
		
	private FieldActivity field_activity;
	private View form = null;
	Context ctxt;

	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.flowering_sync_dialog, null);
		field_id = getArguments().getInt("field_id");
		list_title = getArguments().getString("list_title");
		this.list_position = getArguments().getInt("list_position");
		
		String flowering_male = getArguments().getString("flowering_male");
		String flowering_female = getArguments().getString("flowering_female");
    	                	
        String[] nums = new String[21];
        for(int i=0; i<nums.length; i++)
           nums[i] = Integer.toString(i*5);
    	
    	NumberPicker male_sync = (NumberPicker)form.findViewById(R.id.male_sync);
    	male_sync.setMaxValue(nums.length-1);
    	male_sync.setMinValue(0);
    	male_sync.setWrapSelectorWheel(false);
    	male_sync.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    	male_sync.setDisplayedValues(nums);
    	
    	// make sure we don't crash if this is a new field
    	if (!flowering_male.isEmpty()){
    		male_sync.setValue(Integer.valueOf(flowering_male)/5);
    	} else {
    		male_sync.setValue(50);
    	}
    	
    	
    	NumberPicker female_sync = (NumberPicker)form.findViewById(R.id.female_sync);
    	female_sync.setMaxValue(nums.length-1);
    	female_sync.setMinValue(0);
    	female_sync.setWrapSelectorWheel(false);
    	female_sync.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    	female_sync.setDisplayedValues(nums);
    	
    	// make sure we don't crash if this is a new field
    	if (!flowering_female.isEmpty()){
    		female_sync.setValue(Integer.valueOf(flowering_female)/5);
    	} else {
    		female_sync.setValue(50);
    	}
    	
		
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		
		return(builder.setTitle(list_title).setView(form)
		        .setPositiveButton(android.R.string.ok, this)
		        .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		NumberPicker male_sync = (NumberPicker)form.findViewById(R.id.male_sync);
		NumberPicker female_sync = (NumberPicker)form.findViewById(R.id.female_sync);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		b.putString("male_sync", String.valueOf(male_sync.getValue()*5));
		b.putString("female_sync", String.valueOf(female_sync.getValue()*5));
	
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
				DatabaseHelper.getInstance(getActivity()).saveFlowerSyncDialog(b[0]);
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
				fd.list_item_middle = b.getString("male_sync") + "/" + b.getString("female_sync");
				ListView lv = (ListView) field_activity.findViewById(R.id.fielddetails);
				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
				ba.notifyDataSetChanged();
				
				// update the appropriate parts of the field object too				
				field_activity.field_details.flowering_male = b.getString("male_sync");
				field_activity.field_details.flowering_female = b.getString("female_sync");
				
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}
}
