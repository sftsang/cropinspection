/***
Copyright (c) 2012 CommonsWare, LLC
Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
by applicable law or agreed to in writing, software distributed under the
License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.

From _The Busy Coder's Guide to Android Development_
  http://commonsware.com/Android
*/

package ca.TwentyTwenty.cropinspection;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;
import ca.TwentyTwenty.cropinspection.FieldXmlParser.Customer;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class AbstractMapActivity extends SherlockFragmentActivity {
	protected static final String TAG_ERROR_DIALOG_FRAGMENT="errorDialog";
	
	public static final String SERVER_URL = "http://crop.2020seedlabs.ca/crop_inspections/";
//	public static final String SERVER_URL = "http://192.168.7.4:8084/crop_inspections/";
	public static final String QUERY_FILE = "sync_xml";
	public static final String QUERY_OPTIONS = "?auth_token=";
	public static final String QUERY_URL = SERVER_URL + QUERY_FILE + QUERY_OPTIONS;
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  getSupportMenuInflater().inflate(R.menu.options, menu);
	  return(super.onCreateOptionsMenu(menu));
	}
	
	public void showMe() {
	   new SearchDialogFragment().show(getSupportFragmentManager(), "search");
	}
	
//	public void setPrefs(SharedPreferences prefs){
//	  this.prefs = prefs;
//    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	  switch (item.getItemId()) {
	    case android.R.id.home:
	      return(true);
	    case R.id.settings:
	      startActivity(new Intent(this, Preferences.class));
	      return(true);
	    case R.id.legal:
	      startActivity(new Intent(this, LegalNoticesActivity.class));
	      return(true);
	    case R.id.map:
	      startActivity(new Intent(this, CropInspectionActivity.class));
	      return(true);
	    case R.id.search:
	      showMe();
	      return(true);
	    case R.id.sync:
	    	// 1. if there is no LastSync then skip to 3
	    	// 2. execute task to find records that need to be pushed up onto server and push only those up via API
			//	    pushing up information:
			//    		overwrite fields and set status
			//    		update field_user_assigned to clear a user from a field when complete
	    	// 3. get and parse xml fields through API 
	    	// 4. update the records
	    	
//			Long current_time = System.currentTimeMillis();
//			
//			// sync was successful... make sure to store last sync date/time for next time 
//			SharedPreferences.Editor editor = prefs.edit();
//			editor.putLong("LastSync", current_time);
//            editor.commit();
//            
	      // home
	      //loadTasksFromAPI("http://192.168.7.4:8084/api/v1/tasks.json");
	      // 20/20
	      //loadTasksFromAPI("http://192.168.2.186:8084/api/v1/tasks.json");
	      //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	      AsyncDownloader syncer = new AsyncDownloader();	  	
	      syncer.execute();
	      //startService(new Intent(this, DownloadCheckService.class));
		  return(true);
	    case R.id.check:
	      startActivity(new Intent(this, VerifyActivity.class));
	      return(true);
	  }
		
	  return super.onOptionsItemSelected(item);
	}
	
	protected boolean readyToGo() {
	  int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	
	  if (status == ConnectionResult.SUCCESS) {
	    return(true);
	  }
	  else if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
	    ErrorDialogFragment.newInstance(status)
	                       .show(getSupportFragmentManager(),
	                             TAG_ERROR_DIALOG_FRAGMENT);
	  }
	  else {
	    Toast.makeText(this, R.string.no_maps, Toast.LENGTH_LONG).show();
	    finish();
	  }
	  
	  return(false);
	}
	
	public static class ErrorDialogFragment extends DialogFragment {
	  static final String ARG_STATUS="status";
	
	  static ErrorDialogFragment newInstance(int status) {
	    Bundle args=new Bundle();
	
	    args.putInt(ARG_STATUS, status);
	
	    ErrorDialogFragment result=new ErrorDialogFragment();
	
	    result.setArguments(args);
	
	    return(result);
	  }
	
	  @Override
	  public Dialog onCreateDialog(Bundle savedInstanceState) {
	    Bundle args=getArguments();
	
	    return GooglePlayServicesUtil.getErrorDialog(args.getInt(ARG_STATUS),
	                                                 getActivity(), 0);
	  }
	
	  @Override
	  public void onDismiss(DialogInterface dlg) {
	    if (getActivity() != null) {
	      getActivity().finish();
	    }
	  }
	}
	
	// inner class for download
	private class AsyncDownloader extends AsyncTask<String, String, Integer> {
		
		ProgressDialog pd;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Long last_sync = prefs.getLong("last_sync", 0);
		
		@Override 
		protected void onPreExecute(){
			String msg = "Syncing...";
			pd = ProgressDialog.show(AbstractMapActivity.this, null, msg, true);
		}
		
		@Override
		protected Integer doInBackground(String... auth_token) {
		   // find all fields that were modified since last sync
		   ArrayList<Field> modified_fields = DatabaseHelper.getInstance(getApplicationContext()).getModifiedDeviceFields(last_sync);
		   
		   Log.w("modified_fields", String.valueOf(modified_fields.size()));
		   
		   if (modified_fields.size() > 0) {
			   JSONObject upload_result = new JSONObject();
			   try {				   
				  // push these modified fields up to the server
				  upload_result = uploadFields(modified_fields);
				  // if something went wrong with the upload, cancel the update
				   if (upload_result.getBoolean("success")){
					   // pull down XML file and update
					   XmlPullParser receivedData = tryDownloadingXmlData(SERVER_URL + QUERY_FILE + QUERY_OPTIONS + prefs.getString("AuthToken", ""));
					   tryParsingXmlData(receivedData);
				   } else {
					   // do something
				   }
			   } catch (Error e) {
				   // do something
			   } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   } else {
			   // there are no modified field so just pull from the server
			   XmlPullParser receivedData = tryDownloadingXmlData(SERVER_URL + QUERY_FILE + QUERY_OPTIONS + prefs.getString("AuthToken", ""));
			   tryParsingXmlData(receivedData);
		   }
		   
		   return 1;
		}
		
		private JSONObject uploadFields(ArrayList<Field> modified_fields){
			// call API and update server
			String error_msg = "";
			String auth_token = prefs.getString("AuthToken", "");
			DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(SERVER_URL + "upload_mobile" + QUERY_OPTIONS + auth_token);
	        JSONObject holder = new JSONObject();
	        JSONObject fieldObj = new JSONObject();
	        String response = null;
	        JSONObject json = new JSONObject();

			try{
				Log.w("uploading","here");
				// package the modified_fields as JSONArray
				JSONArray ja = new JSONArray();
				for (int i=0;i<modified_fields.size();i++) {
					String mf_json = modified_fields.get(i).toJSON();
				    ja.put(mf_json);
				}	        

		        try {
		            try {
		                // setup the returned values in case
		                // something goes wrong
		                json.put("success", false);
		                json.put("info", "Something went wrong. Retry!");

		                // add the users's info to the post params
		                fieldObj.put("modified_fields", ja.toString());
		                holder.put("fields", fieldObj);
		                StringEntity se = new StringEntity(holder.toString());
		                post.setEntity(se);

		                // setup the request headers
		                post.setHeader("Accept", "application/json");
		                post.setHeader("Content-Type", "application/json");

		                ResponseHandler<String> responseHandler = new BasicResponseHandler();
		                response = client.execute(post, responseHandler);
		                json = new JSONObject(response);

		            } catch (HttpResponseException e) {
		                e.printStackTrace();
		                Log.e("ClientProtocol", "" + e);
		            } catch (IOException e) {
		                e.printStackTrace();
		                Log.e("IO", "" + e);
		            }
		        } catch (JSONException e) {
		            e.printStackTrace();
		            Log.e("JSON", "" + e);
		        }

		        return json;
			} catch(Error e) {
				error_msg = e.toString();
			}
			
			return json;
		}
		
		private XmlPullParser tryDownloadingXmlData(String authorized_url) {
			try {
				Log.w("started download", "here");
//				Log.w("query string", "http://" + prefs.getString("lmsUrl", null) + prefs.getString("lmsUsername", null) + prefs.getString("lmsPassword", null));
				URL xmlUrl = new URL(authorized_url);
								
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setValidating(false);
				XmlPullParser myxml = factory.newPullParser();
				myxml.setInput(xmlUrl.openStream(), null);
				return myxml;
				
			} catch (XmlPullParserException e) {
				Log.e("XmlE", "xmlpullparser exception", e);
			} catch (IOException e) {
				Log.e("XmlE","xmlpullparser exception", e);
			}
			return null;
		}
		
		private void tryParsingXmlData(XmlPullParser receivedData) {
			if (receivedData != null) {
				try {
					//InputStream xml = ctxt.getApplicationContext().getAssets().open("fields/fields.xml");
					
					Log.w("started parsing", "here");
					
					FieldXmlParser fieldXmlParser = new FieldXmlParser();
					
					List<List> fxp = fieldXmlParser.parse(receivedData);
					List<Customer> customers = fxp.get(0);
					List<Field> fields = fxp.get(1);
					
					DatabaseHelper.getInstance(getApplicationContext()).saveFieldListAsync(fields);
					
					
//			    	pulling down information:
//		    		make sure to not overwrite fields that have been modified locally
//		    			how can we tell if we shouldn't update the field entirely?
//		    				check field device modification date
//		    				if fdmd is greater than last sync date (we need to store this) then do not replace wholesale
//		    					we can only update field ready date from/to and assigned
//			    		make sure to be able to mark fields assigned
					
					// find out when this device last synced
					
					
					// find all fields that were not modified since last sync
					//DatabaseHelper.getInstance(getApplicationContext()).
					
					// update these non-modified fields 
					
					// find all fields that were modified since last sync
					
					// update only ready dates and assigned to for these modified fields
					
					
					Log.w("stopped parsing", "here");
					
					// set last time synced
					SharedPreferences.Editor edit_pref = prefs.edit();
					edit_pref.putLong("last_sync", System.currentTimeMillis());
					edit_pref.commit();
				} catch (XmlPullParserException e) {
					Log.e("XmlE","xmlpullparser exception", e);
				} catch (IOException e) {
					Log.e("XmlE","xmlpullparser exception", e);
				}
			}
		}
		
		@Override
		protected void onProgressUpdate(String... values){
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Integer result){
			super.onPostExecute(result);
			pd.dismiss();
			
			Log.w("last_sync", String.valueOf(System.currentTimeMillis()));
		}
	}
}