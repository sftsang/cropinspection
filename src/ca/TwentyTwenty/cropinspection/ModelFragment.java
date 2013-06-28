package ca.TwentyTwenty.cropinspection;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragment;

public class ModelFragment extends SherlockFragment {
  private SharedPreferences prefs = null;
  private PrefsLoadTask prefsTask = null;
  

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setRetainInstance(true);
    deliverModel();
  }

  synchronized private void deliverModel() {
	if (prefs != null) {
		// if we already have our preferences pass it on to the main activity
//		Log.w("prefsm", prefs.getString("lmsUrl", null));
		Log.w("setPrefs", "got here");
		//((CropInspectionActivity)getActivity()).setPrefs(prefs);
	} else {
		// we haven't loaded our preferences yet, so lets go get them in the bg
		Log.w("loadPrefs", "got here");
		prefsTask = new PrefsLoadTask();
		executeAsyncTask(prefsTask,
                getActivity().getApplicationContext());
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
      ModelFragment.this.prefs=localPrefs;
      ModelFragment.this.prefsTask=null;
      deliverModel();
    }
  }
}
