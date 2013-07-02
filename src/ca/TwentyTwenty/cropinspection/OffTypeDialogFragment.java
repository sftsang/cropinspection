package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.List;

import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;

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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class OffTypeDialogFragment extends DialogFragment implements
	DialogInterface.OnClickListener{

	private FieldActivity field_activity;
	private View form;
	Context ctxt;

	int field_id = 0;
	private int list_position;
	private String list_title;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		form = getActivity().getLayoutInflater().inflate(R.layout.off_type_weeds_dialog, null);
	
		field_id = getArguments().getInt("field_id");
		list_title = getArguments().getString("list_title");
		this.list_position = getArguments().getInt("list_position");
		
		String other_crop_count_1_name = getArguments().getString("other_crop_count_1_name");
		String other_crop_count_2_name = getArguments().getString("other_crop_count_2_name");
		String other_crop_count_3_name = getArguments().getString("other_crop_count_3_name");
		
		String weed_count_1_name = getArguments().getString("weed_count_1_name");
		String weed_count_2_name = getArguments().getString("weed_count_2_name");
		String weed_count_3_name = getArguments().getString("weed_count_3_name");
		
		int other_crop_count_1_1 = getArguments().getInt("other_crop_count_1_1");
		int other_crop_count_1_2 = getArguments().getInt("other_crop_count_1_2");
		int other_crop_count_1_3 = getArguments().getInt("other_crop_count_1_3");
		int other_crop_count_1_4 = getArguments().getInt("other_crop_count_1_4");
		int other_crop_count_1_5 = getArguments().getInt("other_crop_count_1_5");
		int other_crop_count_1_6 = getArguments().getInt("other_crop_count_1_6");
                                          
		int other_crop_count_2_1 = getArguments().getInt("other_crop_count_2_1");
		int other_crop_count_2_2 = getArguments().getInt("other_crop_count_2_2");
		int other_crop_count_2_3 = getArguments().getInt("other_crop_count_2_3");
		int other_crop_count_2_4 = getArguments().getInt("other_crop_count_2_4");
		int other_crop_count_2_5 = getArguments().getInt("other_crop_count_2_5");
		int other_crop_count_2_6 = getArguments().getInt("other_crop_count_2_6");
                                          
		int other_crop_count_3_1 = getArguments().getInt("other_crop_count_3_1");
		int other_crop_count_3_2 = getArguments().getInt("other_crop_count_3_2");
		int other_crop_count_3_3 = getArguments().getInt("other_crop_count_3_3");
		int other_crop_count_3_4 = getArguments().getInt("other_crop_count_3_4");
		int other_crop_count_3_5 = getArguments().getInt("other_crop_count_3_5");
		int other_crop_count_3_6 = getArguments().getInt("other_crop_count_3_6");		
		
		int weed_count_1_1 = getArguments().getInt("weed_count_1_1");
		int weed_count_1_2 = getArguments().getInt("weed_count_1_2");
		int weed_count_1_3 = getArguments().getInt("weed_count_1_3");
		int weed_count_1_4 = getArguments().getInt("weed_count_1_4");
		int weed_count_1_5 = getArguments().getInt("weed_count_1_5");
		int weed_count_1_6 = getArguments().getInt("weed_count_1_6");
                                          
		int weed_count_2_1 = getArguments().getInt("weed_count_2_1");
		int weed_count_2_2 = getArguments().getInt("weed_count_2_2");
		int weed_count_2_3 = getArguments().getInt("weed_count_2_3");
		int weed_count_2_4 = getArguments().getInt("weed_count_2_4");
		int weed_count_2_5 = getArguments().getInt("weed_count_2_5");
		int weed_count_2_6 = getArguments().getInt("weed_count_2_6");
                                          
		int weed_count_3_1 = getArguments().getInt("weed_count_3_1");
		int weed_count_3_2 = getArguments().getInt("weed_count_3_2");
		int weed_count_3_3 = getArguments().getInt("weed_count_3_3");
		int weed_count_3_4 = getArguments().getInt("weed_count_3_4");
		int weed_count_3_5 = getArguments().getInt("weed_count_3_5");
		int weed_count_3_6 = getArguments().getInt("weed_count_3_6");
		
        if (list_title == "Off-type 1" || 
            list_title == "Off-type 2" ||
            list_title == "Off-type 3") {
        
        	Spinner off_type_spinner = (Spinner)form.findViewById(R.id.type_name);
			List<String> off_type = new ArrayList<String>();
			off_type.add("");
			off_type.add("Non-pollen shedding off-type");
			off_type.add("Brassica napus");
			off_type.add("Male fertile (pollen shedder)");
			off_type.add("Fasciated plants");
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, off_type); 
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			off_type_spinner.setAdapter(dataAdapter);
			
			// set off_type/weed spinner to the correct value
			if (list_title == "Off-type 1") {
				if (other_crop_count_1_name != "") {
					off_type_spinner.setSelection(dataAdapter.getPosition(other_crop_count_1_name));
				} else {
					off_type_spinner.setSelection(dataAdapter.getPosition(""));
				}
			} else if (list_title == "Off-type 2") {
				if (other_crop_count_1_name != "") {
					off_type_spinner.setSelection(dataAdapter.getPosition(other_crop_count_2_name));
				} else {
					off_type_spinner.setSelection(dataAdapter.getPosition(""));
				}
			} else if (list_title == "Off-type 3") {
				if (other_crop_count_1_name != "") {
					off_type_spinner.setSelection(dataAdapter.getPosition(other_crop_count_3_name));
				} else {
					off_type_spinner.setSelection(dataAdapter.getPosition(""));
				}
			}
        } else if (list_title == "Weeds 1" ||
                    list_title == "Weeds 2" ||
                    list_title == "Weeds 3") {
        	
        	Spinner weed_spinner = (Spinner)form.findViewById(R.id.type_name);
			List<String> weed = new ArrayList<String>();
			weed.add("");
			weed.add("Mustard");
			weed.add("Oilseed Radish");
			weed.add("White Mustard");
			weed.add("Cleavers (Bedstraw)");
			weed.add("Wild Mustard");
			weed.add("Wild Radish");

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, weed); 
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			weed_spinner.setAdapter(dataAdapter);
			
			// set off_type/weed spinner to the correct value
			if (list_title == "Weeds 1") {
				if (other_crop_count_1_name != "") {
					weed_spinner.setSelection(dataAdapter.getPosition(weed_count_1_name));
				} else {
					weed_spinner.setSelection(dataAdapter.getPosition(""));
				}
			} else if (list_title == "Weeds 2") {
				if (other_crop_count_1_name != "") {
					weed_spinner.setSelection(dataAdapter.getPosition(weed_count_2_name));
				} else {
					weed_spinner.setSelection(dataAdapter.getPosition(""));
				}
			} else if (list_title == "Weeds 3") {
				if (other_crop_count_1_name != "") {
					weed_spinner.setSelection(dataAdapter.getPosition(weed_count_3_name));
				} else {
					weed_spinner.setSelection(dataAdapter.getPosition(""));
				}
			}
		}
        
        // set values
        int other_count1 = 0;
    	int other_count2 = 0;
    	int other_count3 = 0;
    	int other_count4 = 0;
    	int other_count5 = 0;
    	int other_count6 = 0;
    	
        if (list_title == "Off-type 1") {
        	other_count1 = other_crop_count_1_1;
        	other_count2 = other_crop_count_1_2;
        	other_count3 = other_crop_count_1_3;
        	other_count4 = other_crop_count_1_4;
        	other_count5 = other_crop_count_1_5;
        	other_count6 = other_crop_count_1_6;
        } else if (list_title == "Off-type 2") {
        	other_count1 = other_crop_count_2_1;
        	other_count2 = other_crop_count_2_2;
        	other_count3 = other_crop_count_2_3;
        	other_count4 = other_crop_count_2_4;
        	other_count5 = other_crop_count_2_5;
        	other_count6 = other_crop_count_2_6;
        } else if (list_title == "Off-type 3") {
        	other_count1 = other_crop_count_3_1;
        	other_count2 = other_crop_count_3_2;
        	other_count3 = other_crop_count_3_3;
        	other_count4 = other_crop_count_3_4;
        	other_count5 = other_crop_count_3_5;
        	other_count6 = other_crop_count_3_6;
        } else if (list_title == "Weeds 1") {
        	other_count1 = weed_count_1_1;
        	other_count2 = weed_count_1_2;
        	other_count3 = weed_count_1_3;
        	other_count4 = weed_count_1_4;
        	other_count5 = weed_count_1_5;
        	other_count6 = weed_count_1_6;
        } else if (list_title == "Weeds 2") {
        	other_count1 = weed_count_2_1;
        	other_count2 = weed_count_2_2;
        	other_count3 = weed_count_2_3;
        	other_count4 = weed_count_2_4;
        	other_count5 = weed_count_2_5;
        	other_count6 = weed_count_2_6;
        } else if (list_title == "Weeds 3") {
        	other_count1 = weed_count_3_1;
        	other_count2 = weed_count_3_2;
        	other_count3 = weed_count_3_3;
        	other_count4 = weed_count_3_4;
        	other_count5 = weed_count_3_5;
        	other_count6 = weed_count_3_6;
        }
        
		
		NumberPicker off_type_picker1 = (NumberPicker)form.findViewById(R.id.type_p1);
		off_type_picker1.setMaxValue(99);
		off_type_picker1.setMinValue(0);
		off_type_picker1.setWrapSelectorWheel(false);
		off_type_picker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		off_type_picker1.setValue(other_count1);
		
		NumberPicker off_type_picker2 = (NumberPicker)form.findViewById(R.id.type_p2);
		off_type_picker2.setMaxValue(99);
		off_type_picker2.setMinValue(0);
		off_type_picker2.setWrapSelectorWheel(false);
		off_type_picker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		off_type_picker2.setValue(other_count2);
		
		NumberPicker off_type_picker3 = (NumberPicker)form.findViewById(R.id.type_p3);
		off_type_picker3.setMaxValue(99);
		off_type_picker3.setMinValue(0);
		off_type_picker3.setWrapSelectorWheel(false);
		off_type_picker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		off_type_picker3.setValue(other_count3);

		NumberPicker off_type_picker4 = (NumberPicker)form.findViewById(R.id.type_p4);
		off_type_picker4.setMaxValue(99);
		off_type_picker4.setMinValue(0);
		off_type_picker4.setWrapSelectorWheel(false);
		off_type_picker4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		off_type_picker4.setValue(other_count4);
		
		NumberPicker off_type_picker5 = (NumberPicker)form.findViewById(R.id.type_p5);
		off_type_picker5.setMaxValue(99);
		off_type_picker5.setMinValue(0);
		off_type_picker5.setWrapSelectorWheel(false);
		off_type_picker5.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		off_type_picker5.setValue(other_count5);
		
		NumberPicker off_type_picker6 = (NumberPicker)form.findViewById(R.id.type_p6);
		off_type_picker6.setMaxValue(99);
		off_type_picker6.setMinValue(0);
		off_type_picker6.setWrapSelectorWheel(false);
		off_type_picker6.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		off_type_picker6.setValue(other_count6);
	  
		AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
		
		return(builder.setTitle(list_title).setView(form)
		        .setPositiveButton(android.R.string.ok, this)
		        .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		Spinner type_name = (Spinner)form.findViewById(R.id.type_name);
		
		NumberPicker type_p1 = (NumberPicker)form.findViewById(R.id.type_p1);
		NumberPicker type_p2 = (NumberPicker)form.findViewById(R.id.type_p2);
		NumberPicker type_p3 = (NumberPicker)form.findViewById(R.id.type_p3);
		NumberPicker type_p4 = (NumberPicker)form.findViewById(R.id.type_p4);
		NumberPicker type_p5 = (NumberPicker)form.findViewById(R.id.type_p5);
		NumberPicker type_p6 = (NumberPicker)form.findViewById(R.id.type_p6);
		
		// package into a bundle for easy transport
		Bundle b = new Bundle();
		b.putInt("field_id", field_id);
		b.putString("type_name", type_name.getSelectedItem().toString());
		b.putInt("type_p1", type_p1.getValue());
		b.putInt("type_p2", type_p2.getValue());
		b.putInt("type_p3", type_p3.getValue());
		b.putInt("type_p4", type_p4.getValue());
		b.putInt("type_p5", type_p5.getValue());
		b.putInt("type_p6", type_p6.getValue());
		b.putString("list_title", list_title);
	
		// spin up bg thread to save to db
		AsyncSaver saver = new AsyncSaver();
	    saver.execute(b);		
		
		//Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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
			//Log.w("stuff", b[0].toString());
			try {
				DatabaseHelper.getInstance(getActivity()).saveOffTypeDialog(b[0]);
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
				fd.list_item_middle = String.valueOf(b.getInt("type_p1")) + "/" + String.valueOf(b.getInt("type_p2")) + "/" + String.valueOf(b.getInt("type_p3")) + "/" 
									  + String.valueOf(b.getInt("type_p4")) + "/" + String.valueOf(b.getInt("type_p5")) + "/" + String.valueOf(b.getInt("type_p6"));
				fd.list_item_bottom = b.getString("type_name");
				ListView lv = (ListView) field_activity.findViewById(R.id.fielddetails);
				BaseAdapter ba = (BaseAdapter) lv.getAdapter();
				ba.notifyDataSetChanged();
				
				// update the appropriate parts of the field object too
				String list_title = b.getString("list_title");
				if (list_title == "Off-type 1") {
					field_activity.field_details.other_crop_count_1_1 = b.getInt("type_p1");
					field_activity.field_details.other_crop_count_1_2 = b.getInt("type_p2");
					field_activity.field_details.other_crop_count_1_3 = b.getInt("type_p3");
					field_activity.field_details.other_crop_count_1_4 = b.getInt("type_p4");
					field_activity.field_details.other_crop_count_1_5 = b.getInt("type_p5");
					field_activity.field_details.other_crop_count_1_6 = b.getInt("type_p6");
					field_activity.field_details.other_crop_count_1_name = b.getString("type_name");
				} else if (list_title == "Off-type 2") {
					field_activity.field_details.other_crop_count_2_1 = b.getInt("type_p1");
					field_activity.field_details.other_crop_count_2_2 = b.getInt("type_p2");
					field_activity.field_details.other_crop_count_2_3 = b.getInt("type_p3");
					field_activity.field_details.other_crop_count_2_4 = b.getInt("type_p4");
					field_activity.field_details.other_crop_count_2_5 = b.getInt("type_p5");
					field_activity.field_details.other_crop_count_2_6 = b.getInt("type_p6");
					field_activity.field_details.other_crop_count_2_name = b.getString("type_name");
				} else if (list_title == "Off-type 3") {
					field_activity.field_details.other_crop_count_3_1 = b.getInt("type_p1");
					field_activity.field_details.other_crop_count_3_2 = b.getInt("type_p2");
					field_activity.field_details.other_crop_count_3_3 = b.getInt("type_p3");
					field_activity.field_details.other_crop_count_3_4 = b.getInt("type_p4");
					field_activity.field_details.other_crop_count_3_5 = b.getInt("type_p5");
					field_activity.field_details.other_crop_count_3_6 = b.getInt("type_p6");
					field_activity.field_details.other_crop_count_3_name = b.getString("type_name");
				} else if (list_title == "Weeds 1") {
					field_activity.field_details.weed_count_1_1 = b.getInt("type_p1");
					field_activity.field_details.weed_count_1_2 = b.getInt("type_p2");
					field_activity.field_details.weed_count_1_3 = b.getInt("type_p3");
					field_activity.field_details.weed_count_1_4 = b.getInt("type_p4");
					field_activity.field_details.weed_count_1_5 = b.getInt("type_p5");
					field_activity.field_details.weed_count_1_6 = b.getInt("type_p6");
					field_activity.field_details.weed_count_1_name = b.getString("type_name");
				} else if (list_title == "Weeds 2") {
					field_activity.field_details.weed_count_2_1 = b.getInt("type_p1");
					field_activity.field_details.weed_count_2_2 = b.getInt("type_p2");
					field_activity.field_details.weed_count_2_3 = b.getInt("type_p3");
					field_activity.field_details.weed_count_2_4 = b.getInt("type_p4");
					field_activity.field_details.weed_count_2_5 = b.getInt("type_p5");
					field_activity.field_details.weed_count_2_6 = b.getInt("type_p6");
					field_activity.field_details.weed_count_2_name = b.getString("type_name");
				} else if (list_title == "Weeds 3") {
					field_activity.field_details.weed_count_2_1 = b.getInt("type_p1");
					field_activity.field_details.weed_count_2_2 = b.getInt("type_p2");
					field_activity.field_details.weed_count_2_3 = b.getInt("type_p3");
					field_activity.field_details.weed_count_2_4 = b.getInt("type_p4");
					field_activity.field_details.weed_count_2_5 = b.getInt("type_p5");
					field_activity.field_details.weed_count_2_6 = b.getInt("type_p6");
					field_activity.field_details.weed_count_2_name = b.getString("type_name");
				}
			} else {
				Toast.makeText(ctxt, "Change NOT Saved! Try again.", Toast.LENGTH_LONG).show();
			}
		}
	}
}