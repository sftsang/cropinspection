package ca.TwentyTwenty.cropinspection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FieldFragment extends SherlockFragment implements DatabaseHelper.FieldDetailListener{
	public static final String FIELD_ID = "fieldId";
	private FieldActivity field_activity;
	private RelativeLayout editor;
	public MyCustomBaseAdapter mcba;
	ListView lv;
	View result;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.CANADA);
	SharedPreferences prefs;
	
	static FieldFragment newInstance(int fieldId) {
		FieldFragment frag = new FieldFragment();
		Bundle args = new Bundle();
		
		args.putInt(FIELD_ID, fieldId);
		Log.w("fieldid", String.valueOf(fieldId));
		frag.setArguments(args);
		
		return(frag);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		
		prefs = PreferenceManager.getDefaultSharedPreferences(field_activity.getApplicationContext());
		result = inflater.inflate(R.layout.fieldedit, container, false);
		int field_id = getArguments().getInt(FIELD_ID, -1);
		
		editor = (RelativeLayout)result.findViewById(R.id.fieldedit); 
		
		mcba = new MyCustomBaseAdapter(getActivity(), field_activity.fieldDetails);
				
		lv = (ListView)result.findViewById(R.id.fielddetails);
        lv.setAdapter(mcba);
        
        DatabaseHelper.getInstance(getActivity()).getFieldDetailAsync(field_id, this);
        
        // popup Codes dialog
        Button codes_button = (Button)result.findViewById(R.id.codes);
        codes_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				final View yourCustomView = inflater.inflate(R.layout.field_codes, null);
				TextView field_codes = (TextView)yourCustomView.findViewById(R.id.code_content);
				field_codes.setText(Html.fromHtml(getResources().getString(R.string.codes)));
				
				AlertDialog dialog = new AlertDialog.Builder(getActivity())
	                .setTitle("Codes")
	                .setView(yourCustomView)
	                .setPositiveButton("OK", null).create();
	            dialog.show();
	            
//				Toast.makeText(getActivity(), "codes clicked!", Toast.LENGTH_SHORT).show();
			}
		});
        
        // popup Background dialog
        Button background_button = (Button)result.findViewById(R.id.background);
        background_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				final View yourCustomView = inflater.inflate(R.layout.background_info, null);
				
				TextView bg_company = (TextView)yourCustomView.findViewById(R.id.bg_company);
				bg_company.setText(String.valueOf(field_activity.field_details.customer_id));
				
				TextView bg_crop = (TextView)yourCustomView.findViewById(R.id.bg_crop);
				bg_crop.setText(field_activity.field_details.crop);

				TextView bg_growerno = (TextView)yourCustomView.findViewById(R.id.bg_growerno);
				bg_growerno.setText(field_activity.field_details.grower_no);

				TextView bg_seqno = (TextView)yourCustomView.findViewById(R.id.bg_seqno);
				bg_seqno.setText(field_activity.field_details.seq_no);

				TextView bg_acres = (TextView)yourCustomView.findViewById(R.id.bg_acres);
				bg_acres.setText(field_activity.field_details.acres);

				TextView bg_location = (TextView)yourCustomView.findViewById(R.id.bg_location);
				bg_location.setText(field_activity.field_details.field_location);

				TextView bg_malecc = (TextView)yourCustomView.findViewById(R.id.bg_malecc);
				bg_malecc.setText(field_activity.field_details.male_crop_certificate_no);

				TextView bg_male_sealing = (TextView)yourCustomView.findViewById(R.id.bg_male_sealing);
				bg_male_sealing.setText(field_activity.field_details.male_seed_sealing_no);

				TextView bg_male_class = (TextView)yourCustomView.findViewById(R.id.bg_male_class);
				bg_male_class.setText("find it");

				TextView bg_malenum_tags = (TextView)yourCustomView.findViewById(R.id.bg_malenum_tags);
				bg_malenum_tags.setText(field_activity.field_details.male_tags);

				TextView bg_femalecc = (TextView)yourCustomView.findViewById(R.id.bg_femalecc);
				bg_femalecc.setText(field_activity.field_details.female_crop_certificate_no);

				TextView bg_female_sealing = (TextView)yourCustomView.findViewById(R.id.bg_female_sealing);
				bg_female_sealing.setText(field_activity.field_details.female_seed_sealing_no);

				TextView bg_female_class = (TextView)yourCustomView.findViewById(R.id.bg_female_class);
				bg_female_class.setText("find it");

				TextView bg_femalenum_tags = (TextView)yourCustomView.findViewById(R.id.bg_femalenum_tags);
				bg_femalenum_tags.setText(field_activity.field_details.female_tags);

				TextView bg_prev1yr = (TextView)yourCustomView.findViewById(R.id.bg_prev1yr);
				bg_prev1yr.setText(field_activity.field_details.previous_crop1);

				TextView bg_prev2yr = (TextView)yourCustomView.findViewById(R.id.bg_prev2yr);
				bg_prev2yr.setText(field_activity.field_details.previous_crop2);

				TextView bg_prev3yr = (TextView)yourCustomView.findViewById(R.id.bg_prev3yr);
				bg_prev3yr.setText(field_activity.field_details.previous_crop3);
				
				TextView bg_prev4yr = (TextView)yourCustomView.findViewById(R.id.bg_prev4yr);
				bg_prev4yr.setText(field_activity.field_details.previous_crop4);
				
				TextView bg_prev5yr = (TextView)yourCustomView.findViewById(R.id.bg_prev5yr);
				bg_prev5yr.setText(field_activity.field_details.previous_crop5);
				
				AlertDialog dialog = new AlertDialog.Builder(getActivity())
	                .setTitle("Background")
	                .setView(yourCustomView)
	                .setPositiveButton("OK", null).create();
				dialog.show();
				
//				Toast.makeText(getActivity(), "background clicked!", Toast.LENGTH_SHORT).show();
			}
		});
        

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            	
            	// don't let the user modify completed fields
            	if (!field_activity.field_details.status.equals("inspection_completed")) {
	                Object o = lv.getItemAtPosition(position);
	                FieldDetailItem fullObject = (FieldDetailItem)o;
	                View yourCustomView = null;
	                
	                Bundle bundle = new Bundle();
	            	bundle.putInt("field_id", field_activity.field_details.id);
	            	bundle.putString("list_title", fullObject.list_item_top);
	            	bundle.putInt("list_position", position);
	                
	                if (fullObject.list_item_top == "East" || 
	                	fullObject.list_item_top == "West" ||
	                	fullObject.list_item_top == "South" ||
	                	fullObject.list_item_top == "North") {
	                	
	                	bundle.putString("east_isolation_size", field_activity.field_details.east_isolation_size);
	                	bundle.putString("west_isolation_size", field_activity.field_details.west_isolation_size);
	                	bundle.putString("south_isolation_size", field_activity.field_details.south_isolation_size);
	                	bundle.putString("north_isolation_size", field_activity.field_details.north_isolation_size);
	                	
	                	bundle.putString("east_isolation_type", field_activity.field_details.east_isolation_type);
	                	bundle.putString("west_isolation_type", field_activity.field_details.west_isolation_type);
	                	bundle.putString("south_isolation_type", field_activity.field_details.south_isolation_type);
	                	bundle.putString("north_isolation_type", field_activity.field_details.north_isolation_type);
	                	
	                	bundle.putString("east_isolation_condition", field_activity.field_details.east_isolation_condition);
	                	bundle.putString("west_isolation_condition", field_activity.field_details.west_isolation_condition);
	                	bundle.putString("south_isolation_condition", field_activity.field_details.south_isolation_condition);
	                	bundle.putString("north_isolation_condition", field_activity.field_details.north_isolation_condition);
	                	
	                	bundle.putString("east_isolation_crop", field_activity.field_details.east_isolation_crop);  //coded
	                	bundle.putString("west_isolation_crop", field_activity.field_details.west_isolation_crop);
	                	bundle.putString("south_isolation_crop", field_activity.field_details.south_isolation_crop);
	                	bundle.putString("north_isolation_crop", field_activity.field_details.north_isolation_crop);
	                	
	                	// create the dialog
	                	IsolationDialogFragment idf = new IsolationDialogFragment();
	                	idf.setArguments(bundle);
	                	idf.show(getFragmentManager(), "Isolation Dialog");
	                	
	                } else if (fullObject.list_item_top == "Off-type 1" || 
		                       fullObject.list_item_top == "Off-type 2" ||
		                       fullObject.list_item_top == "Off-type 3" ||
		                       fullObject.list_item_top == "Weeds 1" ||
		                       fullObject.list_item_top == "Weeds 2" ||
		                       fullObject.list_item_top == "Weeds 3") {
	                	
	                	bundle.putString("other_crop_count_1_name", field_activity.field_details.other_crop_count_1_name);
	                	bundle.putString("other_crop_count_2_name", field_activity.field_details.other_crop_count_2_name);
	                	bundle.putString("other_crop_count_3_name", field_activity.field_details.other_crop_count_3_name);
	
	                	bundle.putString("weed_count_1_name", field_activity.field_details.weed_count_1_name);
	                	bundle.putString("weed_count_2_name", field_activity.field_details.weed_count_2_name);
	                	bundle.putString("weed_count_3_name", field_activity.field_details.weed_count_3_name);
	
	                	bundle.putInt("other_crop_count_1_1", field_activity.field_details.other_crop_count_1_1);
	                	bundle.putInt("other_crop_count_1_2", field_activity.field_details.other_crop_count_1_2);
	                	bundle.putInt("other_crop_count_1_3", field_activity.field_details.other_crop_count_1_3);
	                	bundle.putInt("other_crop_count_1_4", field_activity.field_details.other_crop_count_1_4);
	                	bundle.putInt("other_crop_count_1_5", field_activity.field_details.other_crop_count_1_5);
	                	bundle.putInt("other_crop_count_1_6", field_activity.field_details.other_crop_count_1_6);
	                	                                      
	                	bundle.putInt("other_crop_count_2_1", field_activity.field_details.other_crop_count_2_1);
	                	bundle.putInt("other_crop_count_2_2", field_activity.field_details.other_crop_count_2_2);
	                	bundle.putInt("other_crop_count_2_3", field_activity.field_details.other_crop_count_2_3);
	                	bundle.putInt("other_crop_count_2_4", field_activity.field_details.other_crop_count_2_4);
	                	bundle.putInt("other_crop_count_2_5", field_activity.field_details.other_crop_count_2_5);
	                	bundle.putInt("other_crop_count_2_6", field_activity.field_details.other_crop_count_2_6);
	                	                                      
	                	bundle.putInt("other_crop_count_3_1", field_activity.field_details.other_crop_count_3_1);
	                	bundle.putInt("other_crop_count_3_2", field_activity.field_details.other_crop_count_3_2);
	                	bundle.putInt("other_crop_count_3_3", field_activity.field_details.other_crop_count_3_3);
	                	bundle.putInt("other_crop_count_3_4", field_activity.field_details.other_crop_count_3_4);
	                	bundle.putInt("other_crop_count_3_5", field_activity.field_details.other_crop_count_3_5);
	                	bundle.putInt("other_crop_count_3_6", field_activity.field_details.other_crop_count_3_6);
	
	                	bundle.putInt("weed_count_1_1", field_activity.field_details.weed_count_1_1);
	                	bundle.putInt("weed_count_1_2", field_activity.field_details.weed_count_1_2);
	                	bundle.putInt("weed_count_1_3", field_activity.field_details.weed_count_1_3);
	                	bundle.putInt("weed_count_1_4", field_activity.field_details.weed_count_1_4);
	                	bundle.putInt("weed_count_1_5", field_activity.field_details.weed_count_1_5);
	                	bundle.putInt("weed_count_1_6", field_activity.field_details.weed_count_1_6);
	                	                                      
	                	bundle.putInt("weed_count_2_1", field_activity.field_details.weed_count_2_1);
	                	bundle.putInt("weed_count_2_2", field_activity.field_details.weed_count_2_2);
	                	bundle.putInt("weed_count_2_3", field_activity.field_details.weed_count_2_3);
	                	bundle.putInt("weed_count_2_4", field_activity.field_details.weed_count_2_4);
	                	bundle.putInt("weed_count_2_5", field_activity.field_details.weed_count_2_5);
	                	bundle.putInt("weed_count_2_6", field_activity.field_details.weed_count_2_6);
	                	                                      
	                	bundle.putInt("weed_count_3_1", field_activity.field_details.weed_count_3_1);
	                	bundle.putInt("weed_count_3_2", field_activity.field_details.weed_count_3_2);
	                	bundle.putInt("weed_count_3_3", field_activity.field_details.weed_count_3_3);
	                	bundle.putInt("weed_count_3_4", field_activity.field_details.weed_count_3_4);
	                	bundle.putInt("weed_count_3_5", field_activity.field_details.weed_count_3_5);
	                	bundle.putInt("weed_count_3_6", field_activity.field_details.weed_count_3_6);
	                	
	                	// create the dialog
	                	OffTypeDialogFragment otdf = new OffTypeDialogFragment();
	                	otdf.setArguments(bundle);
	                	otdf.show(getFragmentManager(), "Off Type Dialog");
					
	                } else if (fullObject.list_item_top == "Uniformity" || 
	                		   fullObject.list_item_top == "Appearance" || 
	                		   fullObject.list_item_top == "Weed Condition" || 
	                		   fullObject.list_item_top == "QA"){
	                	
	                	bundle.putString("crop_condition_uniformity", field_activity.field_details.crop_condition_uniformity);
	                	bundle.putString("crop_condition_appearance", field_activity.field_details.crop_condition_appearance);
	                	bundle.putString("crop_condition_weed", field_activity.field_details.crop_condition_weed);
	                	bundle.putString("qa", field_activity.field_details.qa);
	                	
	            		// create the dialog
	            		GeneralSpinnerDialogFragment otdf = new GeneralSpinnerDialogFragment();
	                	otdf.setArguments(bundle);
	                	otdf.show(getFragmentManager(), "Spinner Dialog");
	                	
	                } else if (fullObject.list_item_top == "Open Pollinated Crops" || 
	                		fullObject.list_item_top == "Objectionable Weeds" || 
	                		fullObject.list_item_top == "Additional Comments"){
	                	
	                	bundle.putString("open_pollinated_crops", field_activity.field_details.open_pollinated_crops);
	                	bundle.putString("objectionable_weeds", field_activity.field_details.objectionable_weeds); // coded
	                	bundle.putString("comments", field_activity.field_details.comments); //coded
	
	                	// create the dialog
	            		TextInputDialogFragment tidf = new TextInputDialogFragment();
	            		tidf.setArguments(bundle);
	            		tidf.show(getFragmentManager(), "Text Input Dialog");
	                } else if (fullObject.list_item_top == "Plants/M2") {
	                	
	                	bundle.putString("plants_per_m2", field_activity.field_details.plants_per_m2);
	                	
	                	// create the dialog
	                	PlantsM2DialogFragment pmdf = new PlantsM2DialogFragment();
	                	pmdf.setArguments(bundle);
	                	pmdf.show(getFragmentManager(), "Plants per m2 Dialog");
	                	
	                } else if (fullObject.list_item_top == "Flowering Sync"){
	
	                	bundle.putString("flowering_male", field_activity.field_details.flowering_male);
	                	bundle.putString("flowering_female", field_activity.field_details.flowering_female);               	
	                	
	                	// create the dialog
	                	FlowerSyncDialogFragment fsdf = new FlowerSyncDialogFragment();
	                	fsdf.setArguments(bundle);
	                	fsdf.show(getFragmentManager(), "Flowering Sync Dialog");
	                } else if (fullObject.list_item_top == "Inspected By"){
	                	bundle.putInt("inspector_1", field_activity.field_details.inspector_1_id);
	                	bundle.putInt("inspector_2", field_activity.field_details.inspector_2_id);
	                	
	                	// create the dialog
	                	InspectedByDialogFragment ibdf = new InspectedByDialogFragment();
	                	ibdf.setArguments(bundle);
	                	ibdf.show(getFragmentManager(), "Inspected By Dialog");
	                	
	                } else if (fullObject.list_item_top == "Field Ready"){
	                	bundle.putString("date_ready", field_activity.field_details.date_ready);
	                	bundle.putString("date_ready_to", field_activity.field_details.date_ready_to);
	                	
	                	// hard code it so that only Richard/Kevin/Sherman can access this functionality
	                	String uid = prefs.getString("uid", "");
	                	Log.w("uid", uid);
	                	if (uid.equals("12") || uid.equals("410") || uid.equals("1083")){
	                		// create the dialog
		                	ReadyFieldDialogFragment rfdf = new ReadyFieldDialogFragment();
		                	rfdf.setArguments(bundle);
		                	rfdf.show(getFragmentManager(), "Ready Field Dialog");
	                	}
	                } else if (fullObject.list_item_top == "Remove Field From Device"){
	                		                	
	                	// hard code it so that only Richard/Kevin/Sherman can access this functionality
	                	String uid = prefs.getString("uid", "");
	                	Log.w("uid", uid);
	                	if (uid.equals("12") || uid.equals("410") || uid.equals("1083")){
	                		// create the dialog
	                		RemoveFieldDialogFragment refdf = new RemoveFieldDialogFragment();
	                		refdf.setArguments(bundle);
	                		refdf.show(getFragmentManager(), "Remove Field Dialog");
	                	}
	                } else {
	                	LayoutInflater inflater = LayoutInflater.from(getActivity());
	                	yourCustomView = inflater.inflate(R.layout.search_dialog, null);
	                    
	                    final TextView etName = (EditText) yourCustomView.findViewById(R.id.search_filter);
	                    
	                }
	                
	                // building custom multi-line views
	                // http://publicstaticdroidmain.com/2011/12/building-a-custom-multi-line-listview-in-android/
	                
	                // mix adapters and views in listview
	                // http://stackoverflow.com/questions/10535097/android-multiple-list-views-that-dont-scroll-independently/10535980#10535980
	                
	                // pass values to activity from dialog
	                // http://stackoverflow.com/questions/4279787/how-can-i-pass-values-between-a-dialog-and-an-activity
	                
	                // make picker pick strings instead of numbers
	                // http://stackoverflow.com/questions/16041905/android-picker-widget-for-arbitrary-strings
	                // String[] values = .....; picker.setDisplayedValues(values); will do the trick, where picker is a NumberPicker
	                
	                // disable picker soft-keyboard
	                // http://stackoverflow.com/questions/8854781/disable-soft-keyboard-on-numberpicker
	                // myNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            	}
            }
        });
        
		setHasOptionsMenu(true);
		return(result);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			field_activity = (FieldActivity) getActivity();
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + "Not FieldActivity class instance");
		}
	}
	
	private void GetFieldDetails() {
				
		if (field_activity.field_details != null){
			//Log.w("d", field_activity.field_details.east_isolation_size);
			
			FieldDetailItem fd = new FieldDetailItem("East", field_activity.field_details.east_isolation_size + "m / " + field_activity.field_details.east_isolation_type  + " / " + field_activity.field_details.east_isolation_condition, field_activity.field_details.east_isolation_crop);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("West", field_activity.field_details.west_isolation_size + "m / " + field_activity.field_details.west_isolation_type  + " / " + field_activity.field_details.west_isolation_condition,  field_activity.field_details.west_isolation_crop);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("South", field_activity.field_details.south_isolation_size + "m / " + field_activity.field_details.south_isolation_type  + " / " + field_activity.field_details.south_isolation_condition,  field_activity.field_details.south_isolation_crop);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("North", field_activity.field_details.north_isolation_size + "m / " + field_activity.field_details.north_isolation_type  + " / " + field_activity.field_details.north_isolation_condition,  field_activity.field_details.north_isolation_crop);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Open Pollinated Crops", field_activity.field_details.open_pollinated_crops, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Off-type 1", field_activity.field_details.other_crop_count_1_1 + "/" +
												   field_activity.field_details.other_crop_count_1_2 + "/" +
												   field_activity.field_details.other_crop_count_1_3 + "/" +
												   field_activity.field_details.other_crop_count_1_4 + "/" +
												   field_activity.field_details.other_crop_count_1_5 + "/" +
												   field_activity.field_details.other_crop_count_1_6, field_activity.field_details.other_crop_count_1_name);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Off-type 2", field_activity.field_details.other_crop_count_2_1 + "/" +
												   field_activity.field_details.other_crop_count_2_2 + "/" +
												   field_activity.field_details.other_crop_count_2_3 + "/" +
												   field_activity.field_details.other_crop_count_2_4 + "/" +
												   field_activity.field_details.other_crop_count_2_5 + "/" +
												   field_activity.field_details.other_crop_count_2_6, field_activity.field_details.other_crop_count_2_name);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Off-type 3", field_activity.field_details.other_crop_count_3_1 + "/" +
												   field_activity.field_details.other_crop_count_3_2 + "/" +
												   field_activity.field_details.other_crop_count_3_3 + "/" +
												   field_activity.field_details.other_crop_count_3_4 + "/" +
												   field_activity.field_details.other_crop_count_3_5 + "/" +
												   field_activity.field_details.other_crop_count_3_6, field_activity.field_details.other_crop_count_3_name);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Weeds 1", field_activity.field_details.weed_count_1_1 + "/" +
											    field_activity.field_details.weed_count_1_2 + "/" +
											    field_activity.field_details.weed_count_1_3 + "/" +
											    field_activity.field_details.weed_count_1_4 + "/" +
											    field_activity.field_details.weed_count_1_5 + "/" +
											    field_activity.field_details.weed_count_1_6, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Weeds 2", field_activity.field_details.weed_count_2_1 + "/" +
											    field_activity.field_details.weed_count_2_2 + "/" +
											    field_activity.field_details.weed_count_2_3 + "/" +
											    field_activity.field_details.weed_count_2_4 + "/" +
											    field_activity.field_details.weed_count_2_5 + "/" +
											    field_activity.field_details.weed_count_2_6, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Weeds 3", field_activity.field_details.weed_count_3_1 + "/" +
											    field_activity.field_details.weed_count_3_2 + "/" +
											    field_activity.field_details.weed_count_3_3 + "/" +
											    field_activity.field_details.weed_count_3_4 + "/" +
											    field_activity.field_details.weed_count_3_5 + "/" +
											    field_activity.field_details.weed_count_3_6, null);
			field_activity.fieldDetails.add(fd);
			
			
			fd = new FieldDetailItem("Uniformity", field_activity.field_details.crop_condition_uniformity, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Appearance", field_activity.field_details.crop_condition_appearance, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Weed Condition", field_activity.field_details.crop_condition_weed, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Flowering Sync", field_activity.field_details.flowering_male + "/" + field_activity.field_details.flowering_female, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("QA", field_activity.field_details.qa, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Plants/M2", field_activity.field_details.plants_per_m2, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Objectionable Weeds", field_activity.field_details.objectionable_weeds, null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Additional Comments", field_activity.field_details.comments, null);
			field_activity.fieldDetails.add(fd);
			
			// format the date
			String formatted_date_inspected = "n/a";
			Long date_inspected = field_activity.field_details.date_inspected;
			if (date_inspected > 0){
				formatted_date_inspected = sdf.format(new Date(date_inspected)); 
			}
			fd = new FieldDetailItem("Inspected By", field_activity.field_details.inspector_1_id + "/" + field_activity.field_details.inspector_2_id + " on " + formatted_date_inspected, null);
			field_activity.fieldDetails.add(fd);
			
			String date_ready = "Not Ready.";
			if (!field_activity.field_details.date_ready.isEmpty()){
				date_ready = field_activity.field_details.date_ready + " to " + field_activity.field_details.date_ready_to;
			}
			fd = new FieldDetailItem("Field Ready", date_ready, null);
			field_activity.fieldDetails.add(fd);
			
			// this isn't in the normal db... we need to add this
			fd = new FieldDetailItem("Checked By", "", null);
			field_activity.fieldDetails.add(fd);
			
			fd = new FieldDetailItem("Remove Field From Device", "", null);
			field_activity.fieldDetails.add(fd);
		}
	}
	
	private class MyCustomBaseAdapter extends BaseAdapter {
	    private ArrayList<FieldDetailItem> fieldDetailList;
	    
	    private LayoutInflater mInflater;
	 
	    public MyCustomBaseAdapter(Context context, ArrayList<FieldDetailItem> results) {
	    	fieldDetailList = results;
	        mInflater = LayoutInflater.from(context);
	    }
	 
	    public int getCount() {
	        return fieldDetailList.size();
	    }
	 
	    public Object getItem(int position) {
	        return fieldDetailList.get(position);
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder;
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.custom_row_view, null);
	            holder = new ViewHolder();
	            holder.detail_title = (TextView) convertView.findViewById(R.id.crvtop);
	            holder.detail_main_attrib = (TextView) convertView.findViewById(R.id.crvmiddle);
	            holder.detail_secondary_attrib = (TextView) convertView.findViewById(R.id.crvbottom);
	 
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	 
	        holder.detail_title.setText(fieldDetailList.get(position).list_item_top);
	        holder.detail_main_attrib.setText(fieldDetailList.get(position).list_item_middle);
	        holder.detail_secondary_attrib.setText(fieldDetailList.get(position).list_item_bottom);
	 
	        return convertView;
	    }
	 
	    class ViewHolder {
	        TextView detail_title;
	        TextView detail_main_attrib;
	        TextView detail_secondary_attrib;
	    }
	}
	
	public class FieldDetailItem {
		public String list_item_top;
		public String list_item_middle;
		public String list_item_bottom;
		
		public FieldDetailItem(String list_item_top, String list_item_middle, String list_item_bottom) {
			this.list_item_top = list_item_top;
			this.list_item_middle = list_item_middle;
			this.list_item_bottom = list_item_bottom;
		}
	}
	
	@Override
	public void setField(Field field) {
		Log.w("return field", field.agronomist);
		field_activity.field_details = field;
		
		GetFieldDetails();	
		mcba.notifyDataSetChanged();
		
		TextView field_no = (TextView)result.findViewById(R.id.field_no);
//		field_no.setText(field.field_no + " " + sdf.format(new Date(field.record_modified_at)));
		field_no.setText(field.field_no);
		
		TextView customer_comments = (TextView)result.findViewById(R.id.fieldcomments);
		customer_comments.setText(field.internal_client_comments);
		
		//Toast.makeText(getActivity(), field_activity.field_details.record_modified_at, Toast.LENGTH_LONG).show();
	}

}
