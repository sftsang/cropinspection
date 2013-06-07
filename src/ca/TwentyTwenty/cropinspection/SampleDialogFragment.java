package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SampleDialogFragment extends DialogFragment implements
  DialogInterface.OnClickListener {
	
	private View form=null;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	  form = getActivity().getLayoutInflater()
	                      .inflate(R.layout.dialog, null);
	  
	  Spinner customer_spinner = (Spinner)form.findViewById(R.id.customer_spinner);
	  
	  List<String> customer_list = new ArrayList<String>();
	  customer_list.add("Bayer");
	  customer_list.add("Monsanto");
	  customer_list.add("Pioneer");
	  customer_list.add("Cargill");
	  customer_list.add("HyTech");
	  
	  ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
	          android.R.layout.simple_spinner_item, customer_list); 
	  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  customer_spinner.setAdapter(dataAdapter);
	
	  AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
	
	  return(builder.setTitle(R.string.dlg_title).setView(form)
	                .setPositiveButton(android.R.string.ok, this)
	                .setNegativeButton(android.R.string.cancel, null).create());
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
	  String template=getActivity().getString(R.string.toast);
	  Spinner search_customer=(Spinner)form.findViewById(R.id.customer_spinner);
	  EditText search_filter=(EditText)form.findViewById(R.id.search_filter);
	  EditText search_status=(EditText)form.findViewById(R.id.search_status);
	  String msg=
	      String.format(template, search_status.getText().toString(),
	    		  search_filter.getText().toString());
	
	  Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onDismiss(DialogInterface unused) {
	  super.onDismiss(unused);
	  
	  Log.d(getClass().getSimpleName(), "Goodbye!");
	}
	
	@Override
	public void onCancel(DialogInterface unused) {
	  super.onCancel(unused);
	  
	  Toast.makeText(getActivity(), R.string.back, Toast.LENGTH_LONG).show();
	}
}
