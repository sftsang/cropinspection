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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;

public class TextInputDialogFragment  extends DialogFragment implements
	DialogInterface.OnClickListener{
		
	private FieldActivity field_activity;
	private View form = null;
	Context ctxt;

	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.text_input_dialog, null);
		field_id = getArguments().getInt("field_id");
		list_title = getArguments().getString("list_title");
		this.list_position = getArguments().getInt("list_position");
		
		String open_pollinated_crops = getArguments().getString("open_pollinated_crops");
		String objectionable_weeds = getArguments().getString("objectionable_weeds");
		String comments = getArguments().getString("comments");
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
    	form = inflater.inflate(R.layout.text_input_dialog, null);
    	String general_data = null;
    	
    	if (list_title == "Open Pollinated Crops") {
    		general_data = open_pollinated_crops;
    	} else if (list_title == "Objectionable Weeds") {
    		general_data = objectionable_weeds;                		
    	} else if (list_title == "Additional Comments") {
    		general_data = comments;
    	}
    	
    	TextView general_text = (EditText) form.findViewById(R.id.general_text);
    	general_text.setText(general_data);
	
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		
		return(builder.setTitle(list_title).setView(form)
		        .setPositiveButton(android.R.string.ok, this)
		        .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		EditText general_text = (EditText)form.findViewById(R.id.general_text);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		b.putString("general_data", general_text.getEditableText().toString());
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
				DatabaseHelper.getInstance(getActivity()).saveGeneralTextDialog(b[0]);
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
				
				if (list_title == "Open Pollinated Crops") {
					field_activity.field_details.open_pollinated_crops = b.getString("general_data");
				} else if (list_title == "Objectionable Weeds") {
					field_activity.field_details.objectionable_weeds = b.getString("general_data");
				} else if (list_title == "Additional Comments" ){
					field_activity.field_details.comments = b.getString("general_data");
				}
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}

}
