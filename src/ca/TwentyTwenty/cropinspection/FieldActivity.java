package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;

import ca.TwentyTwenty.cropinspection.FieldFragment.FieldDetailItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class FieldActivity extends AbstractMapActivity {
	public static final String EXTRA_FIELD_ID = "ca.TwentyTwenty.cropinspection.fieldId";
	public ArrayList<FieldDetailItem> fieldDetails = new ArrayList<FieldDetailItem>();
	public Field field_details;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
			int fieldId = getIntent().getIntExtra(EXTRA_FIELD_ID, 0);
			if (fieldId > 0) {
				Fragment f = FieldFragment.newInstance(fieldId);
				getSupportFragmentManager().beginTransaction().add(android.R.id.content, f).commit();
			}
		}
	}
}
