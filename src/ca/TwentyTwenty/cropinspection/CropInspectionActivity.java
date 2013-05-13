package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import ca.TwentyTwenty.cropinspection.FieldXmlParser.Field;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.google.android.gms.internal.m;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CropInspectionActivity extends AbstractMapActivity implements
	OnNavigationListener {
	
	private static final String STATE_NAV="nav";
	private static final int[] MAP_TYPE_NAMES= { R.string.normal,
	      									     R.string.hybrid, 
	      										 R.string.satellite, 
	      										 R.string.terrain };
	private static final int[] MAP_TYPES= { GoogleMap.MAP_TYPE_NORMAL,
	      									GoogleMap.MAP_TYPE_HYBRID, 
	      									GoogleMap.MAP_TYPE_SATELLITE,
	      									GoogleMap.MAP_TYPE_TERRAIN };
	private GoogleMap map=null;
	private static final String MODEL = "model";
	private HashMap<Marker, Field> fieldMarkerMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getSupportFragmentManager().findFragmentByTag(MODEL) == null) {
			getSupportFragmentManager().beginTransaction()
									   .add(new ModelFragment(), MODEL)
									   .commit();
		}

	    if (readyToGo()) {
	        setContentView(R.layout.main);

	        SupportMapFragment mapFrag = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

	        initListNav();

	        map = mapFrag.getMap();

	        if (savedInstanceState == null) {
	          CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(51.04448, -114.05822));
	          CameraUpdate zoom=CameraUpdateFactory.zoomTo(6);

	          map.moveCamera(center);
	          map.animateCamera(zoom);
	        }
	        
	        // get the list of fields
	        List fields = DatabaseHelper.getInstance(this).selectAllFields();
	        
	        // create markers and all associated information
            setupFields(fields);
	        
//	        addMarker(map, 40.748963847316034, -73.96807193756104, R.string.un, R.string.united_nations);
//	        addMarker(map, 40.76866299974387, -73.98268461227417, R.string.lincoln_center, R.string.lincoln_center_snippet);
//	        addMarker(map, 40.765136435316755, -73.97989511489868, R.string.carnegie_hall, R.string.practice_x3);
//	        addMarker(map, 40.70686417491799, -74.01572942733765, R.string.downtown_club, R.string.heisman_trophy);
	   }
	}

	  @Override
	  public boolean onNavigationItemSelected(int itemPosition, long itemId) {
	    map.setMapType(MAP_TYPES[itemPosition]);

	    return(true);
	  }

	  @Override
	  public void onSaveInstanceState(Bundle savedInstanceState) {
	    super.onSaveInstanceState(savedInstanceState);
	    
	    savedInstanceState.putInt(STATE_NAV,
	                              getSupportActionBar().getSelectedNavigationIndex());
	  }
	  
	  @Override
	  public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    
	    getSupportActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_NAV));
	  }

	  private void initListNav() {
	    ArrayList<String> items=new ArrayList<String>();
	    ArrayAdapter<String> nav=null;
	    ActionBar bar=getSupportActionBar();

	    for (int type : MAP_TYPE_NAMES) {
	      items.add(getString(type));
	    }

	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	      nav=new ArrayAdapter<String>(bar.getThemedContext(),
	                                   android.R.layout.simple_spinner_item,
	                                   items);
	    }
	    else {
	      nav=new ArrayAdapter<String>(this,
	                                   android.R.layout.simple_spinner_item,
	                                   items);
	    }

	    nav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	    bar.setListNavigationCallbacks(nav, this);
	  }

//	  private void addMarker(GoogleMap map, double lat, double lon, int title, int snippet) {
//	    map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
//	                                     .title(getString(title))
//	                                     .snippet(getString(snippet)));
//	  }
	  
	  // make a map marker from a field object
	  private Marker addMarker(GoogleMap map, Field field) {
		Double field_center_lat = Double.valueOf(field.field_center_lat);
	    Double field_center_lng = Double.valueOf(field.field_center_lng);
	    Marker m = map.addMarker(new MarkerOptions().position(new LatLng(field_center_lat, field_center_lng))
	                                     .title(field.crop_name)
	                                     .snippet("my snippet"));
	    
	    return m;
	  }
	  
	  private void setupFields(List fields) {
		  ListIterator lit = fields.listIterator();
	      fieldMarkerMap = new HashMap<Marker, Field>();
	      while(lit.hasNext()) {
	        Field field = (Field)lit.next();
	        Marker m = addMarker(map, field);
	        fieldMarkerMap.put(m, field);
	      }
	      
	    map.setOnInfoWindowClickListener(new OnInfoWindowClickListener(){
	    	@Override
	    	public void onInfoWindowClick(Marker marker) {
	    		Field field = fieldMarkerMap.get(marker);
	    		Intent i = new Intent(getApplicationContext(), FieldActivity.class);
	    		Field fieldId = fieldMarkerMap.get(marker);
	    		i.putExtra(FieldActivity.EXTRA_FIELD_ID, field.id);
	    		startActivity(i);
//	    	    Toast.makeText(getBaseContext(), String.valueOf(field.id), Toast.LENGTH_LONG)
//	    	    			  .show();

	    	}
	    });
	 }
}
