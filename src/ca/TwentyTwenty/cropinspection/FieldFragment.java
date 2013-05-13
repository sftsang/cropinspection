package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class FieldFragment extends SherlockFragment {
	public static final String FIELD_ID = "fieldId";
	private RelativeLayout editor = null;
	
	static FieldFragment newInstance(int fieldId) {
		FieldFragment frag = new FieldFragment();
		Bundle args = new Bundle();
		
		args.putInt(FIELD_ID, fieldId);
		frag.setArguments(args);
		
		return(frag);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.fieldedit, container, false);
//		int position = getArguments().getInt(KEY_POSITION, -1);
		
		editor = (RelativeLayout)result.findViewById(R.id.fieldedit); 
//				(EditText)result.findViewById(R.id.editor);
		
//		DatabaseHelper.getInstance(getActivity()).getNoteAsync(position, this);
		
		String list_array[] = {"East", "South", "West", "North"};
		
		ArrayList<FieldDetail> fieldDetails = GetFieldDetails();
		
		final ListView lv = (ListView)result.findViewById(R.id.fielddetails);
        lv.setAdapter(new MyCustomBaseAdapter(getActivity(), fieldDetails));
        
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                FieldDetail fullObject = (FieldDetail)o;
                Toast.makeText(getActivity(), "You have chosen: " + " " + fullObject.getId(), Toast.LENGTH_SHORT).show();
            }
        });
		
		setHasOptionsMenu(true);
		return(result);
	}
	
	private ArrayList<FieldDetail> GetFieldDetails() {
		ArrayList<FieldDetail> details = new ArrayList<FieldDetail>();
		
		FieldDetail fd = new FieldDetail();
		fd.setId("East");
		fd.setCrop("3m / Chem Fallow/ Good");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("South");
		fd.setCrop("3m / Chem Fallow/ Good");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("West");
		fd.setCrop("3m / Chem Fallow/ Good");
		
		fd = new FieldDetail();
		fd.setId("North");
		fd.setCrop("3m / Chem Fallow/ Good");
		
		fd = new FieldDetail();
		fd.setId("Open Pollinated Crops");
		fd.setCrop("None within 800 meters of field.");
		fd.setFieldCenterLng("None within 1200 meters of field.");

		fd = new FieldDetail();
		fd.setId("Off-type 1");
		fd.setCrop("0/0/0/0/0/0");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Off-type 2");
		fd.setCrop("0/0/0/0/0/0");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Off-type 3");
		fd.setCrop("0/0/0/0/0/0");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Weeds 1");
		fd.setCrop("0/0/0/0/0/0");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Weeds 2");
		fd.setCrop("0/0/0/0/0/0");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Weeds 3");
		fd.setCrop("0/0/0/0/0/0");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Uniformity");
		fd.setCrop("Average");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Appearance");
		fd.setCrop("Good");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Weed Condition");
		fd.setCrop("Trace");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Flowering Sync");
		fd.setCrop("80/70");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("QA");
		fd.setCrop("Good");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Plants/M2");
		fd.setCrop("32");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Objectional Weeds");
		fd.setCrop("None found");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Additional Comments");
		fd.setCrop("None");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Inspected By");
		fd.setCrop("410/805 2013-07-13");
		details.add(fd);
		
		fd = new FieldDetail();
		fd.setId("Checked By");
		fd.setCrop("123 on 2013-07-12");
		details.add(fd);
		
		return details;
	}
	
	private class MyCustomBaseAdapter extends BaseAdapter {
	    private ArrayList<FieldDetail> fieldDetailList;
	    
	    private LayoutInflater mInflater;
	 
	    public MyCustomBaseAdapter(Context context, ArrayList<FieldDetail> results) {
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
	            holder.txtId = (TextView) convertView.findViewById(R.id.crvtop);
	            holder.txtCrop = (TextView) convertView.findViewById(R.id.crvmiddle);
	            holder.txtFieldCenterLng = (TextView) convertView.findViewById(R.id.crvbottom);
	 
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	 
	        holder.txtId.setText(fieldDetailList.get(position).getId());
	        holder.txtCrop.setText(fieldDetailList.get(position).getCrop());
	        holder.txtFieldCenterLng.setText(fieldDetailList.get(position).getFieldCenterLng());
	 
	        return convertView;
	    }
	 
	    class ViewHolder {
	        TextView txtId;
	        TextView txtCrop;
	        TextView txtFieldCenterLng;
	    }
	}

}
