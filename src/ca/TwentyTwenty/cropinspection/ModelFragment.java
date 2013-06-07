package ca.TwentyTwenty.cropinspection;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import ca.TwentyTwenty.cropinspection.FieldXmlParser.Customer;

import com.actionbarsherlock.app.SherlockFragment;

public class ModelFragment extends SherlockFragment {
  private FieldContents contents = null;
  private ContentsLoadTask contentsTask = null;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    setRetainInstance(true);
    deliverModel();
  }

  synchronized private void deliverModel() {
    if (contents != null) {
//      ((CropInspection)getActivity()).setupPager(contents);
    }
    else {
      if (contents == null && contentsTask == null) {
        contentsTask=new ContentsLoadTask();
        executeAsyncTask(contentsTask,
                         getActivity().getApplicationContext());
      }
    }
  }

  @TargetApi(11)
  static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task,
                                          T... params) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    } else {
      task.execute(params);
    }
  }

  private class ContentsLoadTask extends AsyncTask<Context, Void, Void> {
    private FieldContents localContents=null;
    private Exception e=null;
    List<Customer> customers = null;
    List<Field> fields = null;

    @Override
    protected Void doInBackground(Context... ctxt) {
      try {
//        StringBuilder buf=new StringBuilder();
//        InputStream xml = ctxt[0].getAssets().open("fields/fields2.xml");
//        BufferedReader in = new BufferedReader(new InputStreamReader(xml));
//        String str;
//
//        while ((str=in.readLine()) != null) {
//          buf.append(str);
//        }
//        
//        in.close();

//        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        factory.setValidating(false);
//        XmlPullParser myxml = factory.newPullParser();
//        InputStream xml = ctxt[0].getAssets().open("fields/fields.xml");
//        
//        FieldXmlParser fieldXmlParser = new FieldXmlParser();
//        
//        //localContents = new FieldContents(fieldXmlParser.parse(xml));
//        List<List> fxp = fieldXmlParser.parse(xml);
//        List customers = fxp.get(0);
//        List fields = fxp.get(1);
//        DatabaseHelper.getInstance(ctxt[0].getApplicationContext()).saveCustomerListAsync(customers);
//        DatabaseHelper.getInstance(ctxt[0].getApplicationContext()).saveFieldListAsync(fields);
      }
      catch (Exception e) {
        this.e=e;
      }

      return(null);
    }
    
    

    @Override
    public void onPostExecute(Void arg0) {
      if (e == null) {
        ModelFragment.this.contents=localContents;
        ModelFragment.this.contentsTask=null;
        //deliverModel();
      }
      else {
        Log.e(getClass().getSimpleName(), "Exception loading contents",
              e);
      }
    }
  }

  private class PrefsLoadTask extends AsyncTask<Context, Void, Void> {
    SharedPreferences localPrefs=null;

    @Override
    protected Void doInBackground(Context... ctxt) {
      localPrefs=PreferenceManager.getDefaultSharedPreferences(ctxt[0]);
      localPrefs.getAll();

      return(null);
    }

    @Override
    public void onPostExecute(Void arg0) {
//      ModelFragment.this.prefs=localPrefs;
//      ModelFragment.this.prefsTask=null;
      deliverModel();
    }
  }
}
