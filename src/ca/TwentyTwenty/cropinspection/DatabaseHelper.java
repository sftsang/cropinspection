package ca.TwentyTwenty.cropinspection;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import ca.TwentyTwenty.cropinspection.FieldXmlParser.Customer;

public class DatabaseHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "cropinspection.db";
  private static final int SCHEMA_VERSION = 1;
  private static DatabaseHelper singleton = null;
  private Context ctxt = null;

  synchronized static DatabaseHelper getInstance(Context ctxt) {
    if (singleton == null) {
      singleton=new DatabaseHelper(ctxt.getApplicationContext());
    }

    return(singleton);
  }

  private DatabaseHelper(Context ctxt) {
    super(ctxt, DATABASE_NAME, null, SCHEMA_VERSION);
    this.ctxt = ctxt;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    try {
      db.beginTransaction();
      db.execSQL("CREATE TABLE customers (id INTEGER PRIMARY KEY, name TEXT)");
//      db.execSQL("CREATE TABLE fields (id INTEGER PRIMARY KEY, customer_id INTEGER, year INTEGER, area TEXT, crop_sub_kind TEXT, latin_name TEXT, " +
//						              "variety TEXT, grower_no TEXT, seq_no TEXT, field_no TEXT, acres INTEGER, contract_grower_name TEXT, agronomist TEXT, " +
//						              "male_year INTEGER, male_crop_certificate_no TEXT, male_seed_sealing_no TEXT, male_tags TEXT, female_year INTEGER, " +
//						              "female_crop_certificate_no TEXT, female_seed_sealing_no TEXT, female_tags TEXT, previous_crop_1 TEXT, previous_crop_2 TEXT, " +
//						              "previous_crop_3 TEXT, previous_crop_4 TEXT, previous_crop_5 TEXT, east_isolation_size INTEGER, east_isolation_type TEXT, " +
//						              "east_isolation_condition TEXT, east_isolation_crop TEXT, south_isolation_size INTEGER, south_isolation_type TEXT, " +
//						              "south_isolation_condition TEXT, south_isolation_crop TEXT, west_isolation_size INTEGER, west_isolation_type TEXT, " +
//						              "west_isolation_condition TEXT, west_isolation_crop TEXT, north_isolation_size INTEGER, north_isolation_type TEXT, " +
//						              "north_isolation_condition TEXT, north_isolation_crop TEXT, open_pollinated_crops TEXT, crop_condition_uniformity TEXT, " +
//						              "crop_condition_appearance TEXT, crop_condition_weed TEXT, objectionable_weeds TEXT, flowering_male INTEGER, " +
//						              "flowering_female INTEGER, report_comments TEXT, internal_comments TEXT, customer_comments TEXT, date_inspected TEXT, " +
//						              "date_ready TEXT, field_location TEXT, gps_min_lng REAL, gps_min_lat REAL, gps_max_lng REAL, gps_max_lat REAL, " +
//						              "inspector_1_id INTEGER, inspector_2_id INTEGER, reinspection_of_id INTEGER, other_crop_count_1_name TEXT, " +
//						              "other_crop_count_1_1 INTEGER, other_crop_count_1_2 INTEGER, other_crop_count_1_3 INTEGER, other_crop_count_1_4 INTEGER, " +
//						              "other_crop_count_1_5 INTEGER, other_crop_count_1_6 INTEGER, other_crop_count_2_name TEXT, other_crop_count_2_1 INTEGER, " +
//						              "other_crop_count_2_2 INTEGER, other_crop_count_2_3 INTEGER, other_crop_count_2_4 INTEGER, other_crop_count_2_5 INTEGER, " +
//						              "other_crop_count_2_6 INTEGER, other_crop_count_3_name TEXT, other_crop_count_3_1 INTEGER, other_crop_count_3_2 INTEGER, " +
//						              "other_crop_count_3_3 INTEGER, other_crop_count_3_4 INTEGER, other_crop_count_3_5 INTEGER, other_crop_count_3_6 INTEGER, " +
//						              "weed_count_1_name TEXT, weed_count_1_1 INTEGER, weed_count_1_2 INTEGER, weed_count_1_3 INTEGER, weed_count_1_4 INTEGER, " +
//						              "weed_count_1_5 INTEGER, weed_count_1_6 INTEGER, weed_count_2_name TEXT, weed_count_2_1 INTEGER, weed_count_2_2 INTEGER, " +
//						              "weed_count_2_3 INTEGER, weed_count_2_4 INTEGER, weed_count_2_5 INTEGER, weed_count_2_6 INTEGER, weed_count_3_name TEXT, " +
//						              "weed_count_3_1 INTEGER, weed_count_3_2 INTEGER, weed_count_3_3 INTEGER, weed_count_3_4 INTEGER, weed_count_3_5 INTEGER, " +
//						              "weed_count_3_6 INTEGER, date_signed TEXT, signed_by_id INTEGER, revision INTEGER, contract_grower_company TEXT, " +
//						              "contract_grower_no TEXT, to_be_inspected_by TEXT, seeding_date TEXT, internal_client_comments TEXT, report_client_comments TEXT, " +
//						              "date_ready_to TEXT, crop_kind TEXT, field_entrance_lng TEXT, field_entrance_lat TEXT, field_center_lng TEXT, field_center_lat TEXT, " +
//						              "field_location_type TEXT, qa TEXT, plants_per_m2 INTEGER, updated_at TEXT, crop TEXT, comments TEXT, contract_grower TEXT)");
      db.execSQL("CREATE TABLE fields (acres TEXT, agronomist TEXT, area TEXT, comments TEXT, contract_grower TEXT, crop TEXT, crop_condition_appearance TEXT, crop_condition_uniformity TEXT, " +
      		"crop_condition_weed TEXT, customer_id INTEGER, date_inspected TEXT, date_ready TEXT, date_ready_to TEXT, east_isolation_condition TEXT, east_isolation_crop TEXT, east_isolation_size TEXT, " +
      		"east_isolation_type TEXT, female_crop_certificate_no TEXT, female_seed_sealing_no TEXT, female_tags TEXT, female_year TEXT, field_center_lat REAL, field_center_lng REAL, " +
      		"field_entrance_lat REAL, field_entrance_lng REAL, field_location TEXT, field_no TEXT, flowering_female TEXT, flowering_male TEXT, gps_max_lat REAL, gps_max_lng REAL, gps_min_lat REAL, " +
      		"gps_min_lng REAL, grower_no TEXT, id INTEGER PRIMARY KEY, inspector_1_id INTEGER, inspector_2_id INTEGER, internal_client_comments TEXT, male_crop_certificate_no TEXT, " +
      		"male_seed_sealing_no TEXT, male_tags TEXT, male_year TEXT, north_isolation_condition TEXT, north_isolation_crop TEXT, north_isolation_size TEXT, north_isolation_type TEXT, " +
      		"objectionable_weeds TEXT, open_pollinated_crops TEXT, other_crop1_count1 INTEGER, other_crop1_count2 INTEGER, other_crop1_count3 INTEGER, other_crop1_count4 INTEGER, " +
      		"other_crop1_count5 INTEGER, other_crop1_count6 INTEGER, other_crop1_name TEXT, other_crop2_count1 INTEGER, other_crop2_count2 INTEGER, other_crop2_count3 INTEGER, " +
      		"other_crop2_count4 INTEGER, other_crop2_count5 INTEGER, other_crop2_count6 INTEGER, other_crop2_name TEXT, other_crop3_count1 INTEGER, other_crop3_count2 INTEGER, " +
      		"other_crop3_count3 INTEGER, other_crop3_count4 INTEGER, other_crop3_count5 INTEGER, other_crop3_count6 INTEGER, other_crop3_name TEXT, plants_per_m2 TEXT, previous_crop1 TEXT, " +
      		"previous_crop2 TEXT, previous_crop3 TEXT, previous_crop4 TEXT, previous_crop5 TEXT, qa TEXT, reinspection_of_id INTEGER, seq_no TEXT, south_isolation_condition TEXT, " +
      		"south_isolation_crop TEXT, south_isolation_size TEXT, south_isolation_type TEXT, weed1_count1 INTEGER, weed1_count2 INTEGER, weed1_count3 INTEGER, weed1_count4 INTEGER, weed1_count5 INTEGER, " +
      		"weed1_count6 INTEGER, weed1_name TEXT, weed2_count1 INTEGER, weed2_count2 INTEGER, weed2_count3 INTEGER, weed2_count4 INTEGER, weed2_count5 INTEGER, weed2_count6 INTEGER, weed2_name TEXT, " +
      		"weed3_count1 INTEGER, weed3_count2 INTEGER, weed3_count3 INTEGER, weed3_count4 INTEGER, weed3_count5 INTEGER, weed3_count6 INTEGER, weed3_name TEXT, west_isolation_condition TEXT, " +
      		"west_isolation_crop TEXT, west_isolation_size TEXT, west_isolation_type TEXT, year TEXT, status TEXT)");
      
      saveFieldsFromXML();
      
      db.setTransactionSuccessful();
    }
    finally {
      db.endTransaction();
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    throw new RuntimeException(ctxt.getString(R.string.on_upgrade_error));
  }

  void getFieldAsync() {
    ModelFragment.executeAsyncTask(new GetFieldList());
  }
  
  void getFieldDetailAsync(int field_id, FieldDetailListener listener){
	ModelFragment.executeAsyncTask(new GetFieldDetail(listener), field_id);
  }

  void saveFieldAsync(int position, String note) {
    ModelFragment.executeAsyncTask(new SaveNoteTask(position, note));
  }

  void deleteNoteAsync(int position) {
    ModelFragment.executeAsyncTask(new DeleteNoteTask(), position);
  }
  
  void saveCustomerListAsync(List customers) {
    ModelFragment.executeAsyncTask(new SaveCustomerList(customers));
  }
  
  void saveFieldListAsync(List fields) {
	ModelFragment.executeAsyncTask(new SaveFieldList(fields));
  }
  
  void saveFieldsFromXML() {
	ModelFragment.executeAsyncTask(new SaveFieldsFromXML());
  }

  interface FieldListener {
    void setField(List fields);
  }
  
  interface FieldDetailListener {
	 void setField(Field field);
  }
  
  interface CustomerListener {
    void setCustomers(List customer);
  }
  
  // on initial setup take a set of default fiends and load those values into db
  private class SaveFieldsFromXML extends AsyncTask<Void, Void, Void>{

	  @Override
	  protected Void doInBackground(Void... params) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setValidating(false);
			XmlPullParser myxml = factory.newPullParser();
			InputStream xml = ctxt.getApplicationContext().getAssets().open("fields/fields.xml");
			
			FieldXmlParser fieldXmlParser = new FieldXmlParser();
			
			List<List> fxp = fieldXmlParser.parse(xml);
			List<Customer> customers = fxp.get(0);
			List<Field> fields = fxp.get(1);
			DatabaseHelper.getInstance(ctxt.getApplicationContext()).saveCustomerListAsync(customers);
			DatabaseHelper.getInstance(ctxt.getApplicationContext()).saveFieldListAsync(fields);
	    } catch (Exception e) {
	    	
	    }

		return(null);
	  }
}
  
  private class GetCustomerList extends AsyncTask<Void, Void, List> {
	  private CustomerListener listener = null;
	  
	  GetCustomerList(CustomerListener listener){
		  this.listener = listener;
	  }
	  
	  @Override
	  protected List<Customer> doInBackground(Void... params) {
		  Cursor c = getReadableDatabase().rawQuery("SELECT * FROM customers", null);
		  ArrayList<Customer> customerList = new ArrayList<Customer>();
	      c.moveToFirst();
	      while(!c.isAfterLast()){
	    	  int id = c.getInt((c.getColumnIndex("id")));
	    	  String cname = c.getString(c.getColumnIndex("name"));
	    	  customerList.add(new Customer(id, cname));
	    	  c.moveToNext();
	      }
	      c.close();
	      return(customerList);	
	  }
	  
    @Override
    public void onPostExecute(List fields) {
      Iterator<Field> iFields = fields.iterator();
     	      
      while (iFields.hasNext()){
    	  //Log.w("fromdb", iFields.next().field_center_lat);
      }
    }
  }
  
  private class GetFieldDetail extends AsyncTask<Integer, Void, Field> {
	  private FieldDetailListener listener = null;
		
	  GetFieldDetail(FieldDetailListener listener) {
		  this.listener = listener;
	  }
	  

	  @Override
	  protected Field doInBackground(Integer... params) {
		  String[] args = { params[0].toString() };
		  
		  Cursor c = getReadableDatabase().rawQuery("SELECT * FROM fields WHERE id = ?", args);
		  c.moveToFirst();
		  Field f = new Field(c);
		  
		  if (c.isAfterLast()) {
			  return(null);
		  }
		  
		  c.close();
		  
		  return f;
	  }
	  
	  @Override
	  public void onPostExecute(Field field) {
		listener.setField(field);
	  }
  }

  private class GetFieldList extends AsyncTask<Void, Void, List<Field>> {
    private FieldListener listener = null;

    GetFieldList() {
      this.listener = listener;
    }

	  @Override
	  protected List<Field> doInBackground(Void... params) {
		  Cursor c = getReadableDatabase().rawQuery("SELECT * FROM fields", null);
		  ArrayList<Field> fieldList = new ArrayList<Field>();
	      c.moveToFirst();
	      while(!c.isAfterLast()){
	    	  int id = c.getInt((c.getColumnIndex("id")));
	    	  String field_no = c.getString(c.getColumnIndex("field_no"));
	    	  String field_location = c.getString(c.getColumnIndex("field_location"));
	    	  String crop = c.getString(c.getColumnIndex("crop"));
	    	  String agronomist = c.getString(c.getColumnIndex("agronomist"));
	    	  String acres = c.getString(c.getColumnIndex("acres"));
	  		  String contract_grower = c.getString(c.getColumnIndex("contract_grower"));
	  		  Double field_center_lat = c.getDouble(c.getColumnIndex("field_center_lat"));
	    	  Double field_center_lng = c.getDouble(c.getColumnIndex("field_center_lng"));
	    	  Double field_entrance_lat = c.getDouble(c.getColumnIndex("field_entrance_lat"));
	  		  Double field_entrance_lng = c.getDouble(c.getColumnIndex("field_entrance_lng"));
	  		  String status = c.getString(c.getColumnIndex("status"));
	  		  
	  		  
	    	  
	    	  // GET only what we need to display on the main map and query on click
	    	  fieldList.add(new Field(id, field_no, field_location, crop, agronomist, acres,
	    		contract_grower, field_center_lat, field_center_lng, field_entrance_lat, field_entrance_lng, status));
	    	  //Log.w("got here", "here!");
	    	  c.moveToNext();
	      }
	      c.close();
	      return(fieldList);	
	  }

//	    @Override
//	    public void onPostExecute(List fields) {
//	      Iterator<Field> iFields = fields.iterator();
//	     	      
//	      while (iFields.hasNext()){
//	    	  Log.w("fromdb", iFields.next().field_center_lat);
//	      }
//	    }
  }
  
  
  
  private class SaveFieldList extends AsyncTask<Void, Void, Void> {
	  private List fields;
	  
	  SaveFieldList(List fields) {
		  this.fields = fields;
	  }
	  
	  // insert each field into the db
	  @Override
	  protected Void doInBackground(Void... params) {
		// iterate through field list
		ListIterator fs = fields.listIterator();
		while(fs.hasNext()) {
		  Field f = (Field) fs.next();
		  
		  String[] args= { f.acres, f.agronomist, f.area, f.comments, f.contract_grower, f.crop, f.crop_condition_appearance, f.crop_condition_uniformity, 
				  f.crop_condition_weed, String.valueOf(f.customer_id), f.date_inspected, f.date_ready, f.date_ready_to, f.east_isolation_condition, 
				  f.east_isolation_crop, f.east_isolation_size, f.east_isolation_type, f.female_crop_certificate_no, f.female_seed_sealing_no, f.female_tags, 
				  f.female_year, String.valueOf(f.field_center_lat), String.valueOf(f.field_center_lng), String.valueOf(f.field_entrance_lat), 
				  String.valueOf(f.field_entrance_lng), f.field_location, f.field_no, f.flowering_female, f.flowering_male, String.valueOf(f.gps_max_lat), 
				  String.valueOf(f.gps_max_lng), String.valueOf(f.gps_min_lat), String.valueOf(f.gps_min_lng), f.grower_no, String.valueOf(f.id), 
				  String.valueOf(f.inspector_1_id), String.valueOf(f.inspector_2_id), f.internal_client_comments, f.male_crop_certificate_no, f.male_seed_sealing_no, 
				  f.male_tags, f.male_year, f.north_isolation_condition, f.north_isolation_crop, f.north_isolation_size, f.north_isolation_type, f.objectionable_weeds, 
				  f.open_pollinated_crops, String.valueOf(f.other_crop1_count1), String.valueOf(f.other_crop1_count2), String.valueOf(f.other_crop1_count3), 
				  String.valueOf(f.other_crop1_count4), String.valueOf(f.other_crop1_count5), String.valueOf(f.other_crop1_count6), f.other_crop1_name, 
				  String.valueOf(f.other_crop2_count1), String.valueOf(f.other_crop2_count2), String.valueOf(f.other_crop2_count3), String.valueOf(f.other_crop2_count4), 
				  String.valueOf(f.other_crop2_count5), String.valueOf(f.other_crop2_count6), f.other_crop2_name, String.valueOf(f.other_crop3_count1), 
				  String.valueOf(f.other_crop3_count2), String.valueOf(f.other_crop3_count3), String.valueOf(f.other_crop3_count4), String.valueOf(f.other_crop3_count5), 
				  String.valueOf(f.other_crop3_count6), f.other_crop3_name, f.plants_per_m2, f.previous_crop1, f.previous_crop2, f.previous_crop3, f.previous_crop4, 
				  f.previous_crop5, f.qa, String.valueOf(f.reinspection_of_id), f.seq_no, f.south_isolation_condition, f.south_isolation_crop, f.south_isolation_size, 
				  f.south_isolation_type, String.valueOf(f.weed1_count1), String.valueOf(f.weed1_count2), String.valueOf(f.weed1_count3), String.valueOf(f.weed1_count4), 
				  String.valueOf(f.weed1_count5), String.valueOf(f.weed1_count6), f.weed1_name, String.valueOf(f.weed2_count1), String.valueOf(f.weed2_count2), 
				  String.valueOf(f.weed2_count3), String.valueOf(f.weed2_count4), String.valueOf(f.weed2_count5), String.valueOf(f.weed2_count6), f.weed2_name, 
				  String.valueOf(f.weed3_count1), String.valueOf(f.weed3_count2), String.valueOf(f.weed3_count3), String.valueOf(f.weed3_count4), 
				  String.valueOf(f.weed3_count5), String.valueOf(f.weed3_count6), f.weed3_name, f.west_isolation_condition, f.west_isolation_crop, f.west_isolation_size, 
				  f.west_isolation_type, f.year, f.status };
		  
		  getWritableDatabase().execSQL("INSERT OR REPLACE INTO fields (acres, agronomist, area, comments, contract_grower, crop, crop_condition_appearance, " +
		  		"crop_condition_uniformity, crop_condition_weed, customer_id, date_inspected, date_ready, date_ready_to, east_isolation_condition, " +
		  		"east_isolation_crop, east_isolation_size, east_isolation_type, female_crop_certificate_no, female_seed_sealing_no, female_tags, female_year, " +
		  		"field_center_lat, field_center_lng, field_entrance_lat, field_entrance_lng, field_location, field_no, flowering_female, flowering_male, gps_max_lat, " +
		  		"gps_max_lng, gps_min_lat, gps_min_lng, grower_no, id, inspector_1_id, inspector_2_id, internal_client_comments, male_crop_certificate_no, " +
		  		"male_seed_sealing_no, male_tags, male_year, north_isolation_condition, north_isolation_crop, north_isolation_size, north_isolation_type, " +
		  		"objectionable_weeds, open_pollinated_crops, other_crop1_count1, other_crop1_count2, other_crop1_count3, other_crop1_count4, other_crop1_count5, " +
		  		"other_crop1_count6, other_crop1_name, other_crop2_count1, other_crop2_count2, other_crop2_count3, other_crop2_count4, other_crop2_count5, " +
		  		"other_crop2_count6, other_crop2_name, other_crop3_count1, other_crop3_count2, other_crop3_count3, other_crop3_count4, other_crop3_count5, " +
		  		"other_crop3_count6, other_crop3_name, plants_per_m2, previous_crop1, previous_crop2, previous_crop3, previous_crop4, previous_crop5, qa, " +
		  		"reinspection_of_id, seq_no, south_isolation_condition, south_isolation_crop, south_isolation_size, south_isolation_type, weed1_count1, weed1_count2, " +
		  		"weed1_count3, weed1_count4, weed1_count5, weed1_count6, weed1_name, weed2_count1, weed2_count2, weed2_count3, weed2_count4, weed2_count5, weed2_count6, " +
		  		"weed2_name, weed3_count1, weed3_count2, weed3_count3, weed3_count4, weed3_count5, weed3_count6, weed3_name, west_isolation_condition, west_isolation_crop, " +
		  		"west_isolation_size, west_isolation_type, year, status) " +
		  		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
		  		"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
		  		"?, ?, ?, ?, ?, ?, ?, ?)", 
		  		args);
		}		
		
		return(null);
	  }
  }
  
  private class SaveCustomerList extends AsyncTask<Void, Void, Void> {
	  private List<Customer> customers;
	  
	  SaveCustomerList(List<Customer> customers) {
		  this.customers = customers;
	  }
	  
	  // insert each customer into the db
	  @Override
	  protected Void doInBackground(Void... params) {
		// iterate through customer list
		ListIterator<Customer> cs = customers.listIterator();
		while(cs.hasNext()) {
		  Customer cust = (Customer) cs.next();
		  
//			  Log.w("customer object", cust.name);
		  String[] args= { String.valueOf(cust.id), cust.name };
		
		  getWritableDatabase().execSQL("INSERT OR REPLACE INTO customers (id, name) VALUES (?, ?)",
	                                     args);
		  
		}		
		return(null);
	  }
  }
  
  private class SaveNoteTask extends AsyncTask<Void, Void, Void> {
    private int position;
    private String note=null;

    SaveNoteTask(int position, String note) {
      this.position=position;
      this.note=note;
    }

    @Override
    protected Void doInBackground(Void... params) {
      String[] args= { String.valueOf(position), note };

      getWritableDatabase().execSQL("INSERT OR REPLACE INTO notes (position, prose) VALUES (?, ?)",
                                    args);

      return(null);
    }
  }

  private class DeleteNoteTask extends AsyncTask<Integer, Void, Void> {
    @Override
    protected Void doInBackground(Integer... params) {
      String[] args= { params[0].toString() };

      getWritableDatabase().execSQL("DELETE FROM notes WHERE position=?",
                                    args);

      return(null);
    }
  }
  
  public List<Field> selectAllFields() {
		List<Field> list = new ArrayList<Field>();
		Cursor c = getReadableDatabase().rawQuery("SELECT * FROM fields", null);
		if (c.moveToFirst()) {
			do {
				Bundle b = new Bundle();
				
				b.putString("acres",c.getString(c.getColumnIndex("acres")));
				b.putString("agronomist",c.getString(c.getColumnIndex("agronomist")));
				b.putString("area",c.getString(c.getColumnIndex("area")));
				b.putString("comments",c.getString(c.getColumnIndex("comments")));
				b.putString("contract_grower",c.getString(c.getColumnIndex("contract_grower")));
				b.putString("crop",c.getString(c.getColumnIndex("crop")));
				b.putString("crop_condition_appearance",c.getString(c.getColumnIndex("crop_condition_appearance")));
				b.putString("crop_condition_uniformity",c.getString(c.getColumnIndex("crop_condition_uniformity")));
				b.putString("crop_condition_weed",c.getString(c.getColumnIndex("crop_condition_weed")));
				b.putInt("customer_id", c.getInt(c.getColumnIndex("customer_id")));
				b.putString("date_inspected",c.getString(c.getColumnIndex("date_inspected")));
				b.putString("date_ready",c.getString(c.getColumnIndex("date_ready")));
				b.putString("date_ready_to",c.getString(c.getColumnIndex("date_ready_to")));
				b.putString("east_isolation_condition",c.getString(c.getColumnIndex("east_isolation_condition")));
				b.putString("east_isolation_crop",c.getString(c.getColumnIndex("east_isolation_crop")));
				b.putString("east_isolation_size",c.getString(c.getColumnIndex("east_isolation_size")));
				b.putString("east_isolation_type",c.getString(c.getColumnIndex("east_isolation_type")));
				b.putString("female_crop_certificate_no",c.getString(c.getColumnIndex("female_crop_certificate_no")));
				b.putString("female_seed_sealing_no",c.getString(c.getColumnIndex("female_seed_sealing_no")));
				b.putString("female_tags",c.getString(c.getColumnIndex("female_tags")));
				b.putString("female_year",c.getString(c.getColumnIndex("female_year")));
				b.putDouble("field_center_lat", c.getDouble(c.getColumnIndex("field_center_lat")));
				b.putDouble("field_center_lng", c.getDouble(c.getColumnIndex("field_center_lng")));
				b.putDouble("field_entrance_lat", c.getDouble(c.getColumnIndex("field_entrance_lat")));
				b.putDouble("field_entrance_lng", c.getDouble(c.getColumnIndex("field_entrance_lng")));
				b.putString("field_location",c.getString(c.getColumnIndex("field_location")));
				b.putString("field_no",c.getString(c.getColumnIndex("field_no")));
				b.putString("flowering_female",c.getString(c.getColumnIndex("flowering_female")));
				b.putString("flowering_male",c.getString(c.getColumnIndex("flowering_male")));
				b.putDouble("gps_max_lat", c.getDouble(c.getColumnIndex("gps_max_lat")));
				b.putDouble("gps_max_lng", c.getDouble(c.getColumnIndex("gps_max_lng")));
				b.putDouble("gps_min_lat", c.getDouble(c.getColumnIndex("gps_min_lat")));
				b.putDouble("gps_min_lng", c.getDouble(c.getColumnIndex("gps_min_lng")));
				b.putString("grower_no",c.getString(c.getColumnIndex("grower_no")));
				b.putInt("id", c.getInt(c.getColumnIndex("id")));
				b.putInt("inspector_1_id", c.getInt(c.getColumnIndex("inspector_1_id")));
				b.putInt("inspector_2_id", c.getInt(c.getColumnIndex("inspector_2_id")));
				b.putString("internal_client_comments",c.getString(c.getColumnIndex("internal_client_comments")));
				b.putString("male_crop_certificate_no",c.getString(c.getColumnIndex("male_crop_certificate_no")));
				b.putString("male_seed_sealing_no",c.getString(c.getColumnIndex("male_seed_sealing_no")));
				b.putString("male_tags",c.getString(c.getColumnIndex("male_tags")));
				b.putString("male_year",c.getString(c.getColumnIndex("male_year")));
				b.putString("north_isolation_condition",c.getString(c.getColumnIndex("north_isolation_condition")));
				b.putString("north_isolation_crop",c.getString(c.getColumnIndex("north_isolation_crop")));
				b.putString("north_isolation_size",c.getString(c.getColumnIndex("north_isolation_size")));
				b.putString("north_isolation_type",c.getString(c.getColumnIndex("north_isolation_type")));
				b.putString("objectionable_weeds",c.getString(c.getColumnIndex("objectionable_weeds")));
				b.putString("open_pollinated_crops",c.getString(c.getColumnIndex("open_pollinated_crops")));
				b.putInt("other_crop1_count1", c.getInt(c.getColumnIndex("other_crop1_count1")));
				b.putInt("other_crop1_count2", c.getInt(c.getColumnIndex("other_crop1_count2")));
				b.putInt("other_crop1_count3", c.getInt(c.getColumnIndex("other_crop1_count3")));
				b.putInt("other_crop1_count4", c.getInt(c.getColumnIndex("other_crop1_count4")));
				b.putInt("other_crop1_count5", c.getInt(c.getColumnIndex("other_crop1_count5")));
				b.putInt("other_crop1_count6", c.getInt(c.getColumnIndex("other_crop1_count6")));
				b.putString("other_crop1_name",c.getString(c.getColumnIndex("other_crop1_name")));
				b.putInt("other_crop1_count1", c.getInt(c.getColumnIndex("other_crop1_count1")));
				b.putInt("other_crop1_count2", c.getInt(c.getColumnIndex("other_crop1_count2")));
				b.putInt("other_crop1_count3", c.getInt(c.getColumnIndex("other_crop1_count3")));
				b.putInt("other_crop1_count4", c.getInt(c.getColumnIndex("other_crop1_count4")));
				b.putInt("other_crop1_count5", c.getInt(c.getColumnIndex("other_crop1_count5")));
				b.putInt("other_crop1_count6", c.getInt(c.getColumnIndex("other_crop1_count6")));
				b.putString("other_crop1_name",c.getString(c.getColumnIndex("other_crop1_name")));
				b.putInt("other_crop2_count1", c.getInt(c.getColumnIndex("other_crop2_count1")));
				b.putInt("other_crop2_count2", c.getInt(c.getColumnIndex("other_crop2_count2")));
				b.putInt("other_crop2_count3", c.getInt(c.getColumnIndex("other_crop2_count3")));
				b.putInt("other_crop2_count4", c.getInt(c.getColumnIndex("other_crop2_count4")));
				b.putInt("other_crop2_count5", c.getInt(c.getColumnIndex("other_crop2_count5")));
				b.putInt("other_crop2_count6", c.getInt(c.getColumnIndex("other_crop2_count6")));
				b.putString("other_crop2_name",c.getString(c.getColumnIndex("other_crop2_name")));
				b.putInt("other_crop3_count1", c.getInt(c.getColumnIndex("other_crop3_count1")));
				b.putInt("other_crop3_count2", c.getInt(c.getColumnIndex("other_crop3_count2")));
				b.putInt("other_crop3_count3", c.getInt(c.getColumnIndex("other_crop3_count3")));
				b.putInt("other_crop3_count4", c.getInt(c.getColumnIndex("other_crop3_count4")));
				b.putInt("other_crop3_count5", c.getInt(c.getColumnIndex("other_crop3_count5")));
				b.putInt("other_crop3_count6", c.getInt(c.getColumnIndex("other_crop3_count6")));
				b.putString("other_crop3_name",c.getString(c.getColumnIndex("other_crop3_name")));
				b.putString("plants_per_m2",c.getString(c.getColumnIndex("plants_per_m2")));
				b.putString("previous_crop1",c.getString(c.getColumnIndex("previous_crop1")));
				b.putString("previous_crop2",c.getString(c.getColumnIndex("previous_crop2")));
				b.putString("previous_crop3",c.getString(c.getColumnIndex("previous_crop3")));
				b.putString("previous_crop4",c.getString(c.getColumnIndex("previous_crop4")));
				b.putString("previous_crop5",c.getString(c.getColumnIndex("previous_crop5")));
				b.putString("qa",c.getString(c.getColumnIndex("qa")));
				b.putInt("reinspection_of_id", c.getInt(c.getColumnIndex("reinspection_of_id")));
				b.putString("seq_no",c.getString(c.getColumnIndex("seq_no")));
				b.putString("south_isolation_condition",c.getString(c.getColumnIndex("south_isolation_condition")));
				b.putString("south_isolation_crop",c.getString(c.getColumnIndex("south_isolation_crop")));
				b.putString("south_isolation_size",c.getString(c.getColumnIndex("south_isolation_size")));
				b.putString("south_isolation_type",c.getString(c.getColumnIndex("south_isolation_type")));
				b.putInt("weed1_count1", c.getInt(c.getColumnIndex("weed1_count1")));
				b.putInt("weed1_count2", c.getInt(c.getColumnIndex("weed1_count2")));
				b.putInt("weed1_count3", c.getInt(c.getColumnIndex("weed1_count3")));
				b.putInt("weed1_count4", c.getInt(c.getColumnIndex("weed1_count4")));
				b.putInt("weed1_count5", c.getInt(c.getColumnIndex("weed1_count5")));
				b.putInt("weed1_count6", c.getInt(c.getColumnIndex("weed1_count6")));
				b.putString("weed1_name",c.getString(c.getColumnIndex("weed1_name")));
				b.putInt("weed2_count1", c.getInt(c.getColumnIndex("weed2_count1")));
				b.putInt("weed2_count2", c.getInt(c.getColumnIndex("weed2_count2")));
				b.putInt("weed2_count3", c.getInt(c.getColumnIndex("weed2_count3")));
				b.putInt("weed2_count4", c.getInt(c.getColumnIndex("weed2_count4")));
				b.putInt("weed2_count5", c.getInt(c.getColumnIndex("weed2_count5")));
				b.putInt("weed2_count6", c.getInt(c.getColumnIndex("weed2_count6")));
				b.putString("weed2_name",c.getString(c.getColumnIndex("weed2_name")));
				b.putInt("weed3_count1", c.getInt(c.getColumnIndex("weed3_count1")));
				b.putInt("weed3_count2", c.getInt(c.getColumnIndex("weed3_count2")));
				b.putInt("weed3_count3", c.getInt(c.getColumnIndex("weed3_count3")));
				b.putInt("weed3_count4", c.getInt(c.getColumnIndex("weed3_count4")));
				b.putInt("weed3_count5", c.getInt(c.getColumnIndex("weed3_count5")));
				b.putInt("weed3_count6", c.getInt(c.getColumnIndex("weed3_count6")));
				b.putString("weed3_name",c.getString(c.getColumnIndex("weed3_name")));
				b.putString("west_isolation_condition",c.getString(c.getColumnIndex("west_isolation_condition")));
				b.putString("west_isolation_crop",c.getString(c.getColumnIndex("west_isolation_crop")));
				b.putString("west_isolation_size",c.getString(c.getColumnIndex("west_isolation_size")));
				b.putString("west_isolation_type",c.getString(c.getColumnIndex("west_isolation_type")));
				b.putString("year",c.getString(c.getColumnIndex("year")));
				b.putString("status",c.getString(c.getColumnIndex("status")));
				Field f = new Field(b);
				//Log.w("fieldinfo:", f.crop);
				list.add(f);
			} while (c.moveToNext());
		}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		return list;
  }
}