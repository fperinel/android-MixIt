package com.level42.mixit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.utils.Utils;

public class GetTalkAsyncTask extends AsyncTask<Object, Integer, Talk> {
	
	/**
	 * Listener
	 */
	private OnTaskPostExecuteListener<Talk> onTaskPostExecuteListener = null;
	
	private Exception cancelReason;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}

	@Override
	protected Talk doInBackground(Object... params) {
		try {
			ITalkService service = (ITalkService) params[0];
			Integer talkId = (Integer) params[1];
			return service.getTalk(talkId);
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
		
		if(onTaskPostExecuteListener != null && cancelReason != null) {
			onTaskPostExecuteListener.onTaskInterruptListener(cancelReason);
		}
		if(onTaskPostExecuteListener != null && cancelReason == null) {
			onTaskPostExecuteListener.onTaskCancelledListener();
		}
	}
	
	@Override
	protected void onPostExecute(Talk result) {
		super.onPostExecute(result);

		Log.d(Utils.LOGTAG, "Talk : " + result.getId().toString() + " chargé");
		
		if(onTaskPostExecuteListener != null) {
			onTaskPostExecuteListener.onTaskPostExecuteListener(result);
		}
	}
	
	public void setPostExecuteListener(OnTaskPostExecuteListener<Talk> taskPostExecute){
		onTaskPostExecuteListener = taskPostExecute;
	}
	
}
