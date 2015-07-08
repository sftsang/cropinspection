package ca.TwentyTwenty.cropinspection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class CropInspectionActivity extends AbstractMapActivity implements
	OnNavigationListener {
	
	private SharedPreferences prefs;
	private static final String STATE_NAV="nav";
	private static final int[] MAP_TYPE_NAMES= { R.string.normal,
	      									     R.string.hybrid, 
	      										 R.string.satellite, 
	      										 R.string.terrain };
	private static final int[] MAP_TYPES= { GoogleMap.MAP_TYPE_NORMAL,
	      									GoogleMap.MAP_TYPE_HYBRID, 
	      									GoogleMap.MAP_TYPE_SATELLITE,
	      									GoogleMap.MAP_TYPE_TERRAIN };
	public GoogleMap map=null;
	private static final String MODEL = "model";
	private HashMap<Marker, Field> fieldMarkerMap;
	public CropInspectionActivity map_activity = this;

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
	          map.setMyLocationEnabled(true);
	          map.getUiSettings().setMyLocationButtonEnabled(true);
	          map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	        }
	        
	        // package into a bundle for easy transport
			Bundle b = new Bundle();
			b.putString("customer", "All");
			b.putString("search_filter", "");
			b.putString("status", "Field Assigned");
		
			// spin up bg thread and find in db
			AsyncSearch search = new AsyncSearch();
			search.execute(b);
	        
			// show the user's current co-ordinates
			map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
				@Override
				public void onMapLongClick(LatLng arg0) {
					// need to make a dialog here
					Toast.makeText(getApplicationContext(), arg0.toString(), Toast.LENGTH_LONG).show();
				}
			});
			
//			Location findme = map.getMyLocation();
//	        double latitude = findme.getLatitude();
//	        double longitude = findme.getLongitude();
//	        LatLng latLng = new LatLng(latitude, longitude);
	   }

	   prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	}
	
	public void setPrefs(SharedPreferences prefs){
	  this.prefs = prefs;
    }
	
	@Override
    public void onResume(){
	    super.onResume();
	    
	    // we just need to make sure we have an auth token at this point
	    // if not we have to tell the user and direct them to re-enter information
	    if (prefs != null){
	    	Log.w("authtlogin", prefs.getString("AuthToken", ""));
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.CANADA);			
	    	Log.w("last_sync", sdf.format(new Date(prefs.getLong("last_sync", 0))));
		    if(prefs.getString("AuthToken",null) == null) {
	  	    	Intent intent = new Intent(CropInspectionActivity.this, LoginActivity.class);
	  	    	startActivityForResult(intent,0);
	  	    }
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
	    Double field_entrance_lat = Double.valueOf(field.field_entrance_lat);
	    Double field_entrance_lng = Double.valueOf(field.field_entrance_lng);
	    String field_status = null;
	    String field_date_range = "";
	    Date date_ready = null;
	    Date date_ready_to = null;
	    Date date_ready_to_same = null;
	    Date now = new Date();
	    
	    LatLng field_lat_lng = new LatLng(field_center_lat, field_center_lng);
	    LatLng entrance_lat_lng = new LatLng(field_entrance_lat, field_entrance_lng);
	    
	    try {
	    	if (field.date_ready != null  && !field.date_ready.isEmpty()) {
				date_ready = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(field.date_ready);
				date_ready_to = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(field.date_ready_to);
				
				// deal with situation where field is only ready for 1 day
				date_ready_to_same = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH).parse(field.date_ready_to + " 11:59:00");
				 
				if ((date_ready.before(now) && date_ready_to.after(now)) || (date_ready.before(now) && date_ready_to_same.after(now))) {
			    	field_status = "FIELD READY";
			    } else {
			    	field_status = "FIELD NOT READY";
			    }
	    	} else {
		    	field_status = "FIELD NOT READY";
		    }
		} catch (ParseException e) {
			field_status = "FIELD NOT READY";
			e.printStackTrace();
		}
	    
	    if (field.date_ready != null  && !field.date_ready.isEmpty()) {
	    	field_date_range = "Date Ready: " + field.date_ready + " to " + field.date_ready_to + "";
	    }
	    
//	    int icon_res = 0;
//	    
//	    switch (field.status) {
//	    case "inspection_completed":
//	    	icon_res = R.drawable.field_complete;
//	    	break;
//	    case "field_not_ready":
//	    	icon_res = R.drawable.field_notready;
//	    	break;
//	    case "field_reinspection":
//	    	icon_res = R.drawable.field_reinspect;
//	    	break;
//	    case "field_ready":
//	    	icon_res = R.drawable.field_ready;
//	    	break;
//	    case "field_assigned":
//	    	icon_res = R.drawable.field_assigned;
//	    	break;
//	    }
	    
	    Marker m = map.addMarker(new MarkerOptions().position(field_lat_lng)
	                                     .title(field.field_no)
	                                     .snippet(field.crop +  "\n" +
	                                    		 field.field_location + "\n" + 
	                                    		 "Grower: "+ field.contract_grower + "\n" +
	                                    		 "Agronomist: "+ field.agronomist + "\n" +
	                                    		 "Acres: "+ field.acres  + "\n" +
	                                    		 "Assigned: " + field.field_assigned + "\n\n" +
	                                    		 field_status + "\n" +
	                                    		 field_date_range)
	                                      .icon(BitmapDescriptorFactory.fromResource(field.getIcon())));
	    
	    //.fromResource(poi.mType.mResId));
	    
	    // build field entrance marker
	   map.addMarker(new MarkerOptions().position(entrance_lat_lng)
	    		.icon(BitmapDescriptorFactory.fromResource(R.drawable.field_entrance)));
	    
//	    // connect the field to the entrance
	    map.addPolyline(new PolylineOptions()
					     .add(field_lat_lng, entrance_lat_lng)
					     .width(2)
					     .color(Color.MAGENTA));
	    
	    return m;
	  }
	  
	  public void setupFields(List<Field> fields) {
		  ListIterator<Field> lit = fields.listIterator();
	      fieldMarkerMap = new HashMap<Marker, Field>();
	      LatLngBounds.Builder buildBounds = new LatLngBounds.Builder();
	      while(lit.hasNext()) {
	        Field field = (Field)lit.next();
	        Marker m = addMarker(map, field);
	        fieldMarkerMap.put(m, field);
	        
	        // build our our bounds so we can have the right zoom level
	        buildBounds.include(m.getPosition());
	      }
	      
	      // finish building the bounds and zoom
	      LatLngBounds bounds = buildBounds.build();
	      CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,600,600,5);
    	  map.animateCamera(cu);
	      
	      map.setInfoWindowAdapter(new MapInfoWindowAdapter(getLayoutInflater()));
	      
	      // suppress infowindows for entrance markers 
	      map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				// entrance markers have no titles
				if (marker.getTitle() == null) {
					return true;
				} else {
					return false;
				}
			}
	      });
	      
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
	  
		private class AsyncSearch extends AsyncTask<Bundle, Void, ArrayList<Field>> {
			ArrayList<Field> fields;
			
			@Override
			protected ArrayList<Field> doInBackground(Bundle... b) {
				//Log.w("stuff", b[0].toString());
				try {
					fields = DatabaseHelper.getInstance(getApplicationContext()).searchFieldsDialog(b[0]);
				} catch (Exception e){
					Log.w("error searching", "Something went wrong with the search");
				}
				return fields;
			}
			
			@Override
			public void onPostExecute(ArrayList<Field> fields) {
				if (!fields.isEmpty()) {
					map_activity.map.clear();
					map_activity.setupFields(fields);
				} else {
					Toast.makeText(map_activity.getApplicationContext(), "Sorry, no fields for this search.", Toast.LENGTH_LONG).show();
				}
			}
		}
}
