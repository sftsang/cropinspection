package ca.TwentyTwenty.cropinspection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import ca.TwentyTwenty.cropinspection.FieldXmlParser.Customer;
import ca.TwentyTwenty.cropinspection.FieldXmlParser.Field;

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
      db.execSQL("CREATE TABLE fields (id INTEGER PRIMARY KEY, customer_id INTEGER, year INTEGER, area TEXT, crop_sub_kind TEXT, latin_name TEXT, " +
						              "variety TEXT, grower_no TEXT, seq_no TEXT, field_no TEXT, acres INTEGER, contract_grower_name TEXT, agronomist TEXT, " +
						              "male_year INTEGER, male_crop_certificate_no TEXT, male_seed_sealing_no TEXT, male_tags TEXT, female_year INTEGER, " +
						              "female_crop_certificate_no TEXT, female_seed_sealing_no TEXT, female_tags TEXT, previous_crop_1 TEXT, previous_crop_2 TEXT, " +
						              "previous_crop_3 TEXT, previous_crop_4 TEXT, previous_crop_5 TEXT, east_isolation_size INTEGER, east_isolation_type TEXT, " +
						              "east_isolation_condition TEXT, east_isolation_crop TEXT, south_isolation_size INTEGER, south_isolation_type TEXT, " +
						              "south_isolation_condition TEXT, south_isolation_crop TEXT, west_isolation_size INTEGER, west_isolation_type TEXT, " +
						              "west_isolation_condition TEXT, west_isolation_crop TEXT, north_isolation_size INTEGER, north_isolation_type TEXT, " +
						              "north_isolation_condition TEXT, north_isolation_crop TEXT, open_pollinated_crops TEXT, crop_condition_uniformity TEXT, " +
						              "crop_condition_appearance TEXT, crop_condition_weed TEXT, objectionable_weeds TEXT, flowering_male INTEGER, " +
						              "flowering_female INTEGER, report_comments TEXT, internal_comments TEXT, customer_comments TEXT, date_inspected TEXT, " +
						              "date_ready TEXT, field_location TEXT, gps_min_lng REAL, gps_min_lat REAL, gps_max_lng REAL, gps_max_lat REAL, " +
						              "inspector_1_id INTEGER, inspector_2_id INTEGER, reinspection_of_id INTEGER, other_crop_count_1_name TEXT, " +
						              "other_crop_count_1_1 INTEGER, other_crop_count_1_2 INTEGER, other_crop_count_1_3 INTEGER, other_crop_count_1_4 INTEGER, " +
						              "other_crop_count_1_5 INTEGER, other_crop_count_1_6 INTEGER, other_crop_count_2_name TEXT, other_crop_count_2_1 INTEGER, " +
						              "other_crop_count_2_2 INTEGER, other_crop_count_2_3 INTEGER, other_crop_count_2_4 INTEGER, other_crop_count_2_5 INTEGER, " +
						              "other_crop_count_2_6 INTEGER, other_crop_count_3_name TEXT, other_crop_count_3_1 INTEGER, other_crop_count_3_2 INTEGER, " +
						              "other_crop_count_3_3 INTEGER, other_crop_count_3_4 INTEGER, other_crop_count_3_5 INTEGER, other_crop_count_3_6 INTEGER, " +
						              "weed_count_1_name TEXT, weed_count_1_1 INTEGER, weed_count_1_2 INTEGER, weed_count_1_3 INTEGER, weed_count_1_4 INTEGER, " +
						              "weed_count_1_5 INTEGER, weed_count_1_6 INTEGER, weed_count_2_name TEXT, weed_count_2_1 INTEGER, weed_count_2_2 INTEGER, " +
						              "weed_count_2_3 INTEGER, weed_count_2_4 INTEGER, weed_count_2_5 INTEGER, weed_count_2_6 INTEGER, weed_count_3_name TEXT, " +
						              "weed_count_3_1 INTEGER, weed_count_3_2 INTEGER, weed_count_3_3 INTEGER, weed_count_3_4 INTEGER, weed_count_3_5 INTEGER, " +
						              "weed_count_3_6 INTEGER, date_signed TEXT, signed_by_id INTEGER, revision INTEGER, contract_grower_company TEXT, " +
						              "contract_grower_no TEXT, to_be_inspected_by TEXT, seeding_date TEXT, internal_client_comments TEXT, report_client_comments TEXT, " +
						              "date_ready_to TEXT, crop_kind TEXT, field_entrance_lng TEXT, field_entrance_lat TEXT, field_center_lng TEXT, field_center_lat TEXT, " +
						              "field_location_type TEXT, qa TEXT, plants_per_m2 INTEGER, updated_at TEXT, crop TEXT)");
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

  interface FieldListener {
    void setField(List fields);
  }
  
  interface CustomerListener {
    void setCustomers(List customer);
  }
  
  private class GetCustomerList extends AsyncTask<Void, Void, List> {
	  private CustomerListener listener = null;
	  
	  GetCustomerList(CustomerListener listener){
		  this.listener = listener;
	  }
	  
	  @Override
	  protected List doInBackground(Void... params) {
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
    	  Log.w("fromdb", iFields.next().field_center_lat);
      }
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
	    	  String crop = c.getString(c.getColumnIndex("crop"));
	    	  String field_entrance_lng = c.getString(c.getColumnIndex("field_entrance_lng"));
	    	  String field_entrance_lat = c.getString(c.getColumnIndex("field_entrance_lat"));
	    	  String field_center_lng = c.getString(c.getColumnIndex("field_center_lng"));
	    	  String field_center_lat = c.getString(c.getColumnIndex("field_center_lat"));
	    	  
	    	  fieldList.add(new Field(id, crop, field_entrance_lng, field_entrance_lat, field_center_lng, field_center_lat));
	    	  Log.w("got here", "here!");
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
		  String[] args= { String.valueOf(f.id), f.crop_name, f.field_center_lat, f.field_center_lng, f.field_entrance_lat, f.field_entrance_lng };
		  getWritableDatabase().execSQL("INSERT OR REPLACE INTO fields (id, crop, field_center_lat, field_center_lng, field_entrance_lat, field_entrance_lng) VALUES (?, ?, ?, ?, ?, ?)",
	                                     args);
		}		
		return(null);
	  }
  }
  
  private class SaveCustomerList extends AsyncTask<Void, Void, Void> {
	  private List customers;
	  
	  SaveCustomerList(List customers) {
		  this.customers = customers;
	  }
	  
	  // insert each customer into the db
	  @Override
	  protected Void doInBackground(Void... params) {
		// iterate through customer list
		ListIterator cs = customers.listIterator();
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
  
  public List selectAllFields() {
		List list = new ArrayList();
		Cursor c = getReadableDatabase().rawQuery("SELECT * FROM fields", null);
		if (c.moveToFirst()) {
			do {
				int id = c.getInt((c.getColumnIndex("id")));
		    	String crop = c.getString(c.getColumnIndex("crop"));
		    	String field_entrance_lng = c.getString(c.getColumnIndex("field_entrance_lng"));
		        String field_entrance_lat = c.getString(c.getColumnIndex("field_entrance_lat"));
		    	String field_center_lng = c.getString(c.getColumnIndex("field_center_lng"));
		    	String field_center_lat = c.getString(c.getColumnIndex("field_center_lat"));
				list.add(new Field(id, crop, field_entrance_lng, field_entrance_lat, field_center_lng, field_center_lat));
			} while (c.moveToNext());
		}
		if (c != null && !c.isClosed()) {
			c.close();
		}
		return list;
  }
}