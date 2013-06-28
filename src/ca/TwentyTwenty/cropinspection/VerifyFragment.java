package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;

import com.actionbarsherlock.app.SherlockFragment;

public class VerifyFragment extends SherlockFragment {
	View result;
	public VerifyBaseAdapter vba;
	ListView lv;
	
	static VerifyFragment newInstance() {
		VerifyFragment frag = new VerifyFragment();
		Bundle args = new Bundle();

		frag.setArguments(args);
		
		return(frag);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		
		result = inflater.inflate(R.layout.completed_view, container, false);
		
		vba = new VerifyBaseAdapter(getActivity(), ((FieldActivity) getActivity()).fieldDetails);
		lv = (ListView)result.findViewById(R.id.completed_row_field_no);
		lv.setAdapter(vba);
		
		setHasOptionsMenu(true);
		return(result);
	}
	
	private class VerifyBaseAdapter extends BaseAdapter {
	    private ArrayList<FieldDetailItem> fieldDetailList;
	    
	    private LayoutInflater mInflater;
	 
	    public VerifyBaseAdapter(Context context, ArrayList<FieldDetailItem> results) {
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
	
	public class CompletedField {
		public String field_no;
		public String field_details;
		int field_id;
		
		public CompletedField(String field_no, String field_details, int field_id) {
			this.field_no = field_no;
			this.field_details = field_details;
			this.field_id = field_id;
		}
	}
}
