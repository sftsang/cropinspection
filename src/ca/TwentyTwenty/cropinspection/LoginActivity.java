package ca.TwentyTwenty.cropinspection;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.savagelook.android.UrlJsonAsyncTask;

public class LoginActivity extends AbstractMapActivity {
	
//	private final static String LOGIN_API_ENDPOINT_URL = "http://192.168.2.186:8084/api/v1/sessions.json";
//	private final static String LOGIN_API_ENDPOINT_URL = "http://192.168.7.4:8084/api/v1/sessions.json";
	private final static String LOGIN_API_ENDPOINT_URL = "http://crop.2020seedlabs.ca/api/v1/sessions.json";
	private SharedPreferences prefs;
	private String mUserName;
	private String mUserPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_login);
	    
	    prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    
	    EditText userNameField = (EditText) findViewById(R.id.userName);
	    userNameField.setText(prefs.getString("lmsUserName", ""));
	    
	    EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
	    userPasswordField.setText(prefs.getString("lmsPassword", ""));
	}
	
	public void login(View button) {		
	    EditText userNameField = (EditText) findViewById(R.id.userName);
	    mUserName = userNameField.getText().toString();	    
	    EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
	    mUserPassword = userPasswordField.getText().toString();

	    if (mUserName.length() == 0 || mUserPassword.length() == 0) {
	        // input fields are empty
	        Toast.makeText(this, "Please complete all the fields",
	            Toast.LENGTH_LONG).show();
	        return;
	    } else {
	        LoginTask loginTask = new LoginTask(LoginActivity.this);
	        loginTask.setMessageLoading("Logging in...");
	        loginTask.execute(LOGIN_API_ENDPOINT_URL);
	    }
	}
	
	private class LoginTask extends UrlJsonAsyncTask {
	    public LoginTask(Context context) {
	        super(context);
	    }

	    @Override
	    protected JSONObject doInBackground(String... urls) {
	        DefaultHttpClient client = new DefaultHttpClient();
	        HttpPost post = new HttpPost(urls[0]);
	        JSONObject holder = new JSONObject();
	        JSONObject userObj = new JSONObject();
	        String response = null;
	        JSONObject json = new JSONObject();

	        try {
	            try {
	                // setup the returned values in case
	                // something goes wrong
	                json.put("success", false);
	                json.put("info", "Something went wrong. Retry!");
	                // add the user email and password to
	                // the params
	                userObj.put("login", mUserName);
	                userObj.put("password", mUserPassword);
	                holder.put("user", userObj);
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
	                json.put("info", "Username and/or password are invalid. Retry!");
	            } catch (IOException e) {
	                e.printStackTrace();
	                Log.e("IO", "" + e);
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	            Log.e("JSON", "" + e);
	        }

	        return json;
	    }

	    @Override
	    protected void onPostExecute(JSONObject json) {
	        try {
	            if (json.getBoolean("success")) {
	                // everything is ok
	                SharedPreferences.Editor editor = prefs.edit();
	                // save the returned auth_token into
	                // the SharedPreferences
	                Log.w("auth",json.getJSONObject("data").getString("uid"));
	                editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
	                editor.putString("uid", json.getJSONObject("data").getString("uid"));
	                editor.commit();

	                // launch the HomeActivity and close this one
	                Intent intent = new Intent(getApplicationContext(), CropInspectionActivity.class);
	                startActivity(intent);
	                finish();
	            }
	            Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
	            //Toast.makeText(context, "Successfully Logged In!", Toast.LENGTH_LONG).show();
	        } catch (Exception e) {
	            // something went wrong: show a Toast
	            // with the exception message
	            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
	        } finally {
	            super.onPostExecute(json);
	        }
	    }
	}
//
//	@Override
//	public void onBackPressed() {
//	    Intent startMain = new Intent(Intent.ACTION_MAIN);
//	    startMain.addCategory(Intent.CATEGORY_HOME);
//	    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	    startActivity(startMain);
//	    finish();
//	}

}
