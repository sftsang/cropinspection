package ca.TwentyTwenty.cropinspection;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

public class DownloadCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context ctxt, Intent intent) {
		File update = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), DownloadCheckService.UPDATE_FILENAME);

		if(update.exists()) {
			ctxt.startService(new Intent(ctxt, DownloadInstallService.class));
		}

	}

}
