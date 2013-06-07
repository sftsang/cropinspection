package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class FieldFragment extends SherlockFragment implements DatabaseHelper.FieldDetailListener{
	public static final String FIELD_ID = "fieldId";
	private RelativeLayout editor = null;
	public Field field_details = null;
	public MyCustomBaseAdapter mcba = null;
	public ArrayList<FieldDetailItem> fieldDetails = null;
	ListView lv = null;
	View result = null;
	
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
		
		result = inflater.inflate(R.layout.fieldedit, container, false);
		int field_id = getArguments().getInt(FIELD_ID, -1);
		
		editor = (RelativeLayout)result.findViewById(R.id.fieldedit); 
		
////		DatabaseHelper.getInstance(getActivity()).getNoteAsync(position, this);
		
		fieldDetails = GetFieldDetails();
		mcba = new MyCustomBaseAdapter(getActivity(), fieldDetails);
		
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
				bg_company.setText(String.valueOf(field_details.customer_id));
				
				TextView bg_crop = (TextView)yourCustomView.findViewById(R.id.bg_crop);
				bg_crop.setText(field_details.crop);

				TextView bg_growerno = (TextView)yourCustomView.findViewById(R.id.bg_growerno);
				bg_growerno.setText(field_details.grower_no);

				TextView bg_seqno = (TextView)yourCustomView.findViewById(R.id.bg_seqno);
				bg_seqno.setText(field_details.seq_no);

				TextView bg_acres = (TextView)yourCustomView.findViewById(R.id.bg_acres);
				bg_acres.setText(field_details.acres);

				TextView bg_location = (TextView)yourCustomView.findViewById(R.id.bg_location);
				bg_location.setText(field_details.field_location);

				TextView bg_malecc = (TextView)yourCustomView.findViewById(R.id.bg_malecc);
				bg_malecc.setText(field_details.male_crop_certificate_no);

				TextView bg_male_sealing = (TextView)yourCustomView.findViewById(R.id.bg_male_sealing);
				bg_male_sealing.setText(field_details.male_seed_sealing_no);

				TextView bg_male_class = (TextView)yourCustomView.findViewById(R.id.bg_male_class);
				bg_male_class.setText("find it");

				TextView bg_malenum_tags = (TextView)yourCustomView.findViewById(R.id.bg_malenum_tags);
				bg_malenum_tags.setText(field_details.male_tags);

				TextView bg_femalecc = (TextView)yourCustomView.findViewById(R.id.bg_femalecc);
				bg_femalecc.setText(field_details.female_crop_certificate_no);

				TextView bg_female_sealing = (TextView)yourCustomView.findViewById(R.id.bg_female_sealing);
				bg_female_sealing.setText(field_details.female_seed_sealing_no);

				TextView bg_female_class = (TextView)yourCustomView.findViewById(R.id.bg_female_class);
				bg_female_class.setText("find it");

				TextView bg_femalenum_tags = (TextView)yourCustomView.findViewById(R.id.bg_femalenum_tags);
				bg_femalenum_tags.setText(field_details.female_tags);

				TextView bg_prev1yr = (TextView)yourCustomView.findViewById(R.id.bg_prev1yr);
				bg_prev1yr.setText(field_details.previous_crop1);

				TextView bg_prev2yr = (TextView)yourCustomView.findViewById(R.id.bg_prev2yr);
				bg_prev2yr.setText(field_details.previous_crop2);

				TextView bg_prev3yr = (TextView)yourCustomView.findViewById(R.id.bg_prev3yr);
				bg_prev3yr.setText(field_details.previous_crop3);
				
				TextView bg_prev4yr = (TextView)yourCustomView.findViewById(R.id.bg_prev4yr);
				bg_prev4yr.setText(field_details.previous_crop4);
				
				TextView bg_prev5yr = (TextView)yourCustomView.findViewById(R.id.bg_prev5yr);
				bg_prev5yr.setText(field_details.previous_crop5);
				
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
                Object o = lv.getItemAtPosition(position);
                FieldDetailItem fullObject = (FieldDetailItem)o;
                View yourCustomView = null;
//                Toast.makeText(getActivity(), "You have chosen: " + " " + fullObject.list_item_top, Toast.LENGTH_SHORT).show();
                
                // depending upon fullObject.list_item_top show different dialogs
                // probably have to put it into an enum to make a case statement
                
                if (fullObject.list_item_top == "East" || 
                	fullObject.list_item_top == "West" ||
                	fullObject.list_item_top == "South" ||
                	fullObject.list_item_top == "North") {
                	
                	LayoutInflater inflater = LayoutInflater.from(getActivity());
                	yourCustomView = inflater.inflate(R.layout.isolation_dialog, null);
                    
                    Spinner isolation_distance_spinner = (Spinner)yourCustomView.findViewById(R.id.isolation_size);
					List<String> isolation_distance = new ArrayList<String>();
					isolation_distance.add("1m");
					isolation_distance.add("2m");
					isolation_distance.add("3m");
					isolation_distance.add("4m");
					isolation_distance.add("5m");
					isolation_distance.add("6m");
					isolation_distance.add("7m");
					isolation_distance.add("8m");
					isolation_distance.add("9m");
					isolation_distance.add("10m");
					isolation_distance.add("11m");
					isolation_distance.add("12m");
					isolation_distance.add("13m");
					isolation_distance.add("14m");
					isolation_distance.add("15m");
					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, isolation_distance); 
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					isolation_distance_spinner.setAdapter(dataAdapter);
					
					// set isolation distance spinner to the correct value
					if (fullObject.list_item_top == "East") {
						isolation_distance_spinner.setSelection(dataAdapter.getPosition(field_details.east_isolation_size + "m"));
					} else if (fullObject.list_item_top == "West") {
						isolation_distance_spinner.setSelection(dataAdapter.getPosition(field_details.west_isolation_size + "m"));
					} else if (fullObject.list_item_top == "South") {
						isolation_distance_spinner.setSelection(dataAdapter.getPosition(field_details.south_isolation_size + "m"));
					} else if (fullObject.list_item_top == "North") {
						isolation_distance_spinner.setSelection(dataAdapter.getPosition(field_details.north_isolation_size + "m"));
					}
					
					Spinner isolation_type_spinner = (Spinner)yourCustomView.findViewById(R.id.isolation_type);
					List<String> isolation_type = new ArrayList<String>();
					isolation_type.add("Sod");
					isolation_type.add("Cultivated");
					isolation_type.add("Chem Fallow");
					isolation_type.add("Clean Crop");
					isolation_type.add("Mowed");
					isolation_type.add("Track");
					dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, isolation_type); 
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					isolation_type_spinner.setAdapter(dataAdapter);
					
					// set isolation type spinner to the correct value
					if (fullObject.list_item_top == "East") {
						isolation_type_spinner.setSelection(dataAdapter.getPosition(field_details.east_isolation_type));
					} else if (fullObject.list_item_top == "West") {
						isolation_type_spinner.setSelection(dataAdapter.getPosition(field_details.west_isolation_type));
					} else if (fullObject.list_item_top == "South") {
						isolation_type_spinner.setSelection(dataAdapter.getPosition(field_details.south_isolation_type));
					} else if (fullObject.list_item_top == "North") {
						isolation_type_spinner.setSelection(dataAdapter.getPosition(field_details.north_isolation_type));
					}
					
					Spinner isolation_condition_spinner = (Spinner)yourCustomView.findViewById(R.id.isolation_condition);
					List<String> isolation_condition = new ArrayList<String>();
					isolation_condition.add("Good");
					isolation_condition.add("Fair");
					isolation_condition.add("Poor");
					dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, isolation_condition); 
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					isolation_condition_spinner.setAdapter(dataAdapter);
					
					// set isolation condition spinner to the correct value
					if (fullObject.list_item_top == "East") {
						isolation_condition_spinner.setSelection(dataAdapter.getPosition(field_details.east_isolation_condition));
					} else if (fullObject.list_item_top == "West") {
						isolation_condition_spinner.setSelection(dataAdapter.getPosition(field_details.west_isolation_condition));
					} else if (fullObject.list_item_top == "South") {
						isolation_condition_spinner.setSelection(dataAdapter.getPosition(field_details.south_isolation_condition));
					} else if (fullObject.list_item_top == "North") {
						isolation_condition_spinner.setSelection(dataAdapter.getPosition(field_details.north_isolation_condition));
					}
                    
                    TextView isolation_statement = (EditText) yourCustomView.findViewById(R.id.isolation_statement);
                    if (fullObject.list_item_top == "East") {
                    	isolation_statement.setText(field_details.east_isolation_crop);
					} else if (fullObject.list_item_top == "West") {
						isolation_statement.setText(field_details.west_isolation_crop);
					} else if (fullObject.list_item_top == "South") {
						isolation_statement.setText(field_details.south_isolation_crop);
					} else if (fullObject.list_item_top == "North") {
						isolation_statement.setText(field_details.north_isolation_crop);
					}
                	
                } else if (fullObject.list_item_top == "Off-type 1" || 
	                       fullObject.list_item_top == "Off-type 2" ||
	                       fullObject.list_item_top == "Off-type 3" ||
	                       fullObject.list_item_top == "Weeds 1" ||
	                       fullObject.list_item_top == "Weeds 2" ||
	                       fullObject.list_item_top == "Weeds 3") {
                	
                	LayoutInflater inflater = LayoutInflater.from(getActivity());
                	yourCustomView = inflater.inflate(R.layout.off_type_weeds_dialog, null);
                    
                    if (fullObject.list_item_top == "Off-type 1" || 
                        fullObject.list_item_top == "Off-type 2" ||
                        fullObject.list_item_top == "Off-type 3") {
                    
                    	Spinner off_type_spinner = (Spinner)yourCustomView.findViewById(R.id.type_name);
    					List<String> off_type = new ArrayList<String>();
    					off_type.add("");
    					off_type.add("grey pubescence and imperfect black hilum");
    					off_type.add("Brown pubescence, Black hilum");
    					off_type.add("Grey Pubescence, Yellow Hilum");
    					off_type.add("Non-pollen shedding off-type");
    					off_type.add("Dark brown pubescence and yellow hilum");
    					off_type.add("Brassica napus");
    					off_type.add("Brown pubescence, yellow hilum");
    					off_type.add("Male fertile (pollen shedder)");
    					off_type.add("Fasciated plants");
    					off_type.add("Grey Pubescence, Imperfect Black Hilum");
    					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, off_type); 
    					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    					off_type_spinner.setAdapter(dataAdapter);
    					
    					// set off_type/weed spinner to the correct value
    					if (fullObject.list_item_top == "Off-type 1") {
    						if (field_details.other_crop1_name != "") {
    							off_type_spinner.setSelection(dataAdapter.getPosition(field_details.other_crop1_name));
    						} else {
    							off_type_spinner.setSelection(dataAdapter.getPosition(""));
    						}
    					} else if (fullObject.list_item_top == "Off-type 2") {
    						if (field_details.other_crop1_name != "") {
    							off_type_spinner.setSelection(dataAdapter.getPosition(field_details.other_crop2_name));
    						} else {
    							off_type_spinner.setSelection(dataAdapter.getPosition(""));
    						}
    					} else if (fullObject.list_item_top == "Off-type 3") {
    						if (field_details.other_crop1_name != "") {
    							off_type_spinner.setSelection(dataAdapter.getPosition(field_details.other_crop3_name));
    						} else {
    							off_type_spinner.setSelection(dataAdapter.getPosition(""));
    						}
    					}
                    } else if (fullObject.list_item_top == "Weeds 1" ||
	 	                       fullObject.list_item_top == "Weeds 2" ||
	 	                       fullObject.list_item_top == "Weeds 3") {
                    	
                    	Spinner weed_spinner = (Spinner)yourCustomView.findViewById(R.id.type_name);
    					List<String> weed = new ArrayList<String>();
    					weed.add("");
    					weed.add("Corn (no kernels)");
    					weed.add("Brassica napus");
    					weed.add("B. napus");
    					weed.add("Cleavers Bedstraw");
    					weed.add("Corn (with kernels)");
    					weed.add("Wild Mustard");
    					weed.add("Brassica juncea");
    					
    					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, weed); 
    					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    					weed_spinner.setAdapter(dataAdapter);
    					
    					// set off_type/weed spinner to the correct value
    					if (fullObject.list_item_top == "Weeds 1") {
    						if (field_details.other_crop1_name != "") {
    							weed_spinner.setSelection(dataAdapter.getPosition(field_details.weed1_name));
    						} else {
    							weed_spinner.setSelection(dataAdapter.getPosition(""));
    						}
    					} else if (fullObject.list_item_top == "Weeds 2") {
    						if (field_details.other_crop1_name != "") {
    							weed_spinner.setSelection(dataAdapter.getPosition(field_details.weed2_name));
    						} else {
    							weed_spinner.setSelection(dataAdapter.getPosition(""));
    						}
    					} else if (fullObject.list_item_top == "Weeds 3") {
    						if (field_details.other_crop1_name != "") {
    							weed_spinner.setSelection(dataAdapter.getPosition(field_details.weed3_name));
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
                	
                    if (fullObject.list_item_top == "Off-type 1") {
                    	other_count1 = field_details.other_crop1_count1;
                    	other_count2 = field_details.other_crop1_count2;
                    	other_count3 = field_details.other_crop1_count3;
                    	other_count4 = field_details.other_crop1_count4;
                    	other_count5 = field_details.other_crop1_count5;
                    	other_count6 = field_details.other_crop1_count6;
                    } else if (fullObject.list_item_top == "Off-type 2") {
                    	other_count1 = field_details.other_crop2_count1;
                    	other_count2 = field_details.other_crop2_count2;
                    	other_count3 = field_details.other_crop2_count3;
                    	other_count4 = field_details.other_crop2_count4;
                    	other_count5 = field_details.other_crop2_count5;
                    	other_count6 = field_details.other_crop2_count6;
                    } else if (fullObject.list_item_top == "Off-type 3") {
                    	other_count1 = field_details.other_crop3_count1;
                    	other_count2 = field_details.other_crop3_count2;
                    	other_count3 = field_details.other_crop3_count3;
                    	other_count4 = field_details.other_crop3_count4;
                    	other_count5 = field_details.other_crop3_count5;
                    	other_count6 = field_details.other_crop3_count6;
                    } else if (fullObject.list_item_top == "Weeds 1") {
                    	other_count1 = field_details.weed1_count1;
                    	other_count2 = field_details.weed1_count2;
                    	other_count3 = field_details.weed1_count3;
                    	other_count4 = field_details.weed1_count4;
                    	other_count5 = field_details.weed1_count5;
                    	other_count6 = field_details.weed1_count6;
                    } else if (fullObject.list_item_top == "Weeds 2") {
                    	other_count1 = field_details.weed2_count1;
                    	other_count2 = field_details.weed2_count2;
                    	other_count3 = field_details.weed2_count3;
                    	other_count4 = field_details.weed2_count4;
                    	other_count5 = field_details.weed2_count5;
                    	other_count6 = field_details.weed2_count6;
                    } else if (fullObject.list_item_top == "Weeds 3") {
                    	other_count1 = field_details.weed3_count1;
                    	other_count2 = field_details.weed3_count2;
                    	other_count3 = field_details.weed3_count3;
                    	other_count4 = field_details.weed3_count4;
                    	other_count5 = field_details.weed3_count5;
                    	other_count6 = field_details.weed3_count6;
                    }
                    
					
					NumberPicker off_type_picker1 = (NumberPicker)yourCustomView.findViewById(R.id.type_p1);
					off_type_picker1.setMaxValue(99);
					off_type_picker1.setMinValue(0);
					off_type_picker1.setWrapSelectorWheel(false);
					off_type_picker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					off_type_picker1.setValue(other_count1);
					
					NumberPicker off_type_picker2 = (NumberPicker)yourCustomView.findViewById(R.id.type_p2);
					off_type_picker2.setMaxValue(99);
					off_type_picker2.setMinValue(0);
					off_type_picker2.setWrapSelectorWheel(false);
					off_type_picker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					off_type_picker2.setValue(other_count2);
					
					NumberPicker off_type_picker3 = (NumberPicker)yourCustomView.findViewById(R.id.type_p3);
					off_type_picker3.setMaxValue(99);
					off_type_picker3.setMinValue(0);
					off_type_picker3.setWrapSelectorWheel(false);
					off_type_picker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					off_type_picker3.setValue(other_count3);

					NumberPicker off_type_picker4 = (NumberPicker)yourCustomView.findViewById(R.id.type_p4);
					off_type_picker4.setMaxValue(99);
					off_type_picker4.setMinValue(0);
					off_type_picker4.setWrapSelectorWheel(false);
					off_type_picker4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					off_type_picker4.setValue(other_count4);
					
					NumberPicker off_type_picker5 = (NumberPicker)yourCustomView.findViewById(R.id.type_p5);
					off_type_picker5.setMaxValue(99);
					off_type_picker5.setMinValue(0);
					off_type_picker5.setWrapSelectorWheel(false);
					off_type_picker5.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					off_type_picker5.setValue(other_count5);
					
					NumberPicker off_type_picker6 = (NumberPicker)yourCustomView.findViewById(R.id.type_p6);
					off_type_picker6.setMaxValue(99);
					off_type_picker6.setMinValue(0);
					off_type_picker6.setWrapSelectorWheel(false);
					off_type_picker6.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
					off_type_picker6.setValue(other_count6);
				
                } else if (fullObject.list_item_top == "Uniformity" || 
                		fullObject.list_item_top == "Appearance" || 
                		fullObject.list_item_top == "Weed Condition" || 
                		fullObject.list_item_top == "QA"){
                	
                	LayoutInflater inflater = LayoutInflater.from(getActivity());
                	yourCustomView = inflater.inflate(R.layout.spinner_dialog, null);
                	String general_data = null;
                	
                	Spinner general_spinner = (Spinner)yourCustomView.findViewById(R.id.general_spinner);
					List<String> spinner_list = new ArrayList<String>();
					spinner_list.add("");
					
					if (fullObject.list_item_top == "Uniformity") {
						spinner_list.add("Good");
						spinner_list.add("Above Average");
						spinner_list.add("Average");
						spinner_list.add("Below Average");
						general_data = field_details.crop_condition_uniformity;
						
					} else if (fullObject.list_item_top == "Appearance") {
						spinner_list.add("Good");
						spinner_list.add("Fair");
						spinner_list.add("Poor");
						general_data = field_details.crop_condition_appearance;
					} else if (fullObject.list_item_top == "Weed Condition") {
						spinner_list.add("None found");
						spinner_list.add("Trace");
						spinner_list.add("Few");
						spinner_list.add("Numerous");
						general_data = field_details.crop_condition_weed;
					} else if (fullObject.list_item_top == "QA") {
						spinner_list.add("Good");
						spinner_list.add("Fair");
						general_data = field_details.qa;
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
                } else if (fullObject.list_item_top == "Open Pollinated Crops" || 
                		fullObject.list_item_top == "Plants/M2" || 
                		fullObject.list_item_top == "Objectionable Weeds" || 
                		fullObject.list_item_top == "Additional Comments"){
                	
                	LayoutInflater inflater = LayoutInflater.from(getActivity());
                	yourCustomView = inflater.inflate(R.layout.text_input_dialog, null);
                	String general_data = null;
                	
                	if (fullObject.list_item_top == "Open Pollinated Crops") {
                		general_data = field_details.open_pollinated_crops;
                	} else if (fullObject.list_item_top == "Plants/M2") {
                		general_data = field_details.plants_per_m2;
                	} else if (fullObject.list_item_top == "Objectionable Weeds") {
                		general_data = field_details.objectionable_weeds;                		
                	} else if (fullObject.list_item_top == "Additional Comments") {
                		general_data = field_details.comments;
                	}
                	
                	TextView general_text = (EditText) yourCustomView.findViewById(R.id.general_text);
                	general_text.setText(general_data);
                	
                } else if (fullObject.list_item_top == "Flowering Sync"){
                	LayoutInflater inflater = LayoutInflater.from(getActivity());
                	yourCustomView = inflater.inflate(R.layout.flowering_sync_dialog, null);
                	                	
                    String[] nums = new String[21];
                    
                    for(int i=0; i<nums.length; i++)
                       nums[i] = Integer.toString(i*5);
                	
                	NumberPicker male_sync = (NumberPicker)yourCustomView.findViewById(R.id.male_sync);
                	male_sync.setMaxValue(nums.length-1);
                	male_sync.setMinValue(0);
                	male_sync.setWrapSelectorWheel(false);
                	male_sync.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                	male_sync.setDisplayedValues(nums);
                	//male_sync.setValue(100/5 -1);
                	//male_sync.setValue(other_count1);
                	
                	NumberPicker female_sync = (NumberPicker)yourCustomView.findViewById(R.id.female_sync);
                	female_sync.setMaxValue(nums.length-1);
                	female_sync.setMinValue(0);
                	female_sync.setWrapSelectorWheel(false);
                	female_sync.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                	female_sync.setDisplayedValues(nums);
                	//male_sync.setValue(other_count1);
					
                } else {
                	LayoutInflater inflater = LayoutInflater.from(getActivity());
                	yourCustomView = inflater.inflate(R.layout.dialog, null);
                    
                    final TextView etName = (EditText) yourCustomView.findViewById(R.id.search_filter);
                    
                }
                
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
	                .setTitle(fullObject.list_item_top)
	                .setView(yourCustomView)
	                .setPositiveButton("OK", null)
	                .setNegativeButton("Cancel", null).create();
	            
	            dialog.show();
                
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
        });
        
		setHasOptionsMenu(true);
		return(result);
	}
	
	private ArrayList<FieldDetailItem> GetFieldDetails() {
		ArrayList<FieldDetailItem> details = new ArrayList<FieldDetailItem>();
		
		if (field_details != null){
			Log.w("d", field_details.east_isolation_size);
			
			FieldDetailItem fd = new FieldDetailItem("East", field_details.east_isolation_size + "m / " + field_details.east_isolation_type  + " / " + field_details.east_isolation_condition, field_details.east_isolation_crop);
			details.add(fd);
			
			fd = new FieldDetailItem("West", field_details.west_isolation_size + "m / " + field_details.west_isolation_type  + " / " + field_details.west_isolation_condition,  field_details.west_isolation_crop);
			details.add(fd);
			
			fd = new FieldDetailItem("South", field_details.south_isolation_size + "m / " + field_details.south_isolation_type  + " / " + field_details.south_isolation_condition,  field_details.south_isolation_crop);
			details.add(fd);
			
			fd = new FieldDetailItem("North", field_details.north_isolation_size + "m / " + field_details.north_isolation_type  + " / " + field_details.north_isolation_condition,  field_details.north_isolation_crop);
			details.add(fd);
			
			fd = new FieldDetailItem("Open Pollinated Crops", field_details.open_pollinated_crops, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Off-type 1", field_details.other_crop1_count1 + "/" +
												   field_details.other_crop1_count2 + "/" +
												   field_details.other_crop1_count3 + "/" +
												   field_details.other_crop1_count4 + "/" +
												   field_details.other_crop1_count5 + "/" +
												   field_details.other_crop1_count6, field_details.other_crop1_name);
			details.add(fd);
			
			fd = new FieldDetailItem("Off-type 2", field_details.other_crop2_count1 + "/" +
												   field_details.other_crop2_count2 + "/" +
												   field_details.other_crop2_count3 + "/" +
												   field_details.other_crop2_count4 + "/" +
												   field_details.other_crop2_count5 + "/" +
												   field_details.other_crop2_count6, field_details.other_crop2_name);
			details.add(fd);
			
			fd = new FieldDetailItem("Off-type 3", field_details.other_crop3_count1 + "/" +
												   field_details.other_crop3_count2 + "/" +
												   field_details.other_crop3_count3 + "/" +
												   field_details.other_crop3_count4 + "/" +
												   field_details.other_crop3_count5 + "/" +
												   field_details.other_crop3_count6, field_details.other_crop3_name);
			details.add(fd);
			
			fd = new FieldDetailItem("Weeds 1", field_details.weed1_count1 + "/" +
											    field_details.weed1_count2 + "/" +
											    field_details.weed1_count3 + "/" +
											    field_details.weed1_count4 + "/" +
											    field_details.weed1_count5 + "/" +
											    field_details.weed1_count6, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Weeds 2", field_details.weed2_count1 + "/" +
											    field_details.weed2_count2 + "/" +
											    field_details.weed2_count3 + "/" +
											    field_details.weed2_count4 + "/" +
											    field_details.weed2_count5 + "/" +
											    field_details.weed2_count6, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Weeds 3", field_details.weed3_count1 + "/" +
											    field_details.weed3_count2 + "/" +
											    field_details.weed3_count3 + "/" +
											    field_details.weed3_count4 + "/" +
											    field_details.weed3_count5 + "/" +
											    field_details.weed3_count6, null);
			details.add(fd);
			
			
			fd = new FieldDetailItem("Uniformity", field_details.crop_condition_uniformity, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Appearance", field_details.crop_condition_appearance, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Weed Condition", field_details.crop_condition_weed, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Flowering Sync", field_details.flowering_female + "/" + field_details.flowering_male, null);
			details.add(fd);
			
			fd = new FieldDetailItem("QA", field_details.qa, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Plants/M2", field_details.plants_per_m2, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Objectionable Weeds", field_details.objectionable_weeds, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Additional Comments", field_details.comments, null);
			details.add(fd);
			
			fd = new FieldDetailItem("Inspected By", field_details.inspector_1_id + "/" + field_details.inspector_2_id + " on " + field_details.date_inspected, null);
			details.add(fd);
			
			// this isn't in the normal db... we need to add this
			fd = new FieldDetailItem("Checked By", "TODO", null);
			details.add(fd);			
		}
		
		return details;
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
		field_details = field;
		
		fieldDetails = GetFieldDetails();
			
		mcba = new MyCustomBaseAdapter(getActivity(), fieldDetails);
		lv.setAdapter(mcba);
		//mcba.notifyDataSetChanged();
		
	   TextView field_no = (TextView)result.findViewById(R.id.field_no);
	   field_no.setText(field.field_no);
	   
	   TextView customer_comments = (TextView)result.findViewById(R.id.fieldcomments);
	   customer_comments.setText(field.internal_client_comments);
	}

}
