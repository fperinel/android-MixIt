package com.level42.mixit.tasks;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.utils.Utils;

public class GetLightningTalksAsyncTask extends
	AsyncTask<ILightningTalkService, Integer, List<LightningTalk>> {

    /**
     * Listener
     */
    private OnTaskPostExecuteListener<List<LightningTalk>> onTaskPostExecuteListener = null;

    private Exception cancelReason;

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
	super.onProgressUpdate(values);
    }

    @Override
    protected List<LightningTalk> doInBackground(
	    ILightningTalkService... params) {
	try {
	    ILightningTalkService service = (ILightningTalkService) params[0];
	    return service.getLightningTalks();
	} catch (FunctionnalException e) {
	    Log.e(Utils.LOGTAG, e.getMessage());
	    cancelReason = e;
	} catch (TechnicalException e) {
	    Log.e(Utils.LOGTAG, e.getMessage());
	    cancelReason = e;
	}
	this.cancel(true);
	return null;
    }

    @Override
    protected void onCancelled() {
	super.onCancelled();

	if (onTaskPostExecuteListener != null && cancelReason != null) {
	    onTaskPostExecuteListener.onTaskInterruptListener(cancelReason);
	}
	if (onTaskPostExecuteListener != null && cancelReason == null) {
	    onTaskPostExecuteListener.onTaskCancelledListener();
	}
    }

    @Override
    protected void onPostExecute(List<LightningTalk> result) {
	super.onPostExecute(result);

	Log.d(Utils.LOGTAG, "Nombre de lightning talks : " + result.size());

	if (onTaskPostExecuteListener != null) {
	    onTaskPostExecuteListener.onTaskPostExecuteListener(result);
	}
    }

    public void setPostExecuteListener(
	    OnTaskPostExecuteListener<List<LightningTalk>> taskPostExecute) {
	onTaskPostExecuteListener = taskPostExecute;
    }

}
