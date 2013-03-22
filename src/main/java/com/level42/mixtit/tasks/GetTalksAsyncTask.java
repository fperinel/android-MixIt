package com.level42.mixtit.tasks;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixtit.exceptions.FunctionnalException;
import com.level42.mixtit.exceptions.TechnicalException;
import com.level42.mixtit.listeners.OnTaskPostExecuteListener;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.services.ITalkService;
import com.level42.mixtit.utils.Utils;

public class GetTalksAsyncTask extends AsyncTask<ITalkService, Integer, List<Talk>> {
	
	/**
	 * Listener
	 */
	private OnTaskPostExecuteListener<List<Talk>> onTaskPostExecuteListener = null;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}

	@Override
	protected List<Talk> doInBackground(ITalkService... params) {
		try {
			ITalkService service = (ITalkService) params[0];
			return service.getTalks();
		} catch (FunctionnalException e) {
			Log.e(Utils.LOGTAG, e.getMessage());
		} catch (TechnicalException e) {
			Log.e(Utils.LOGTAG, e.getMessage());
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<Talk> result) {
		super.onPostExecute(result);
		
		if(onTaskPostExecuteListener != null) {
			onTaskPostExecuteListener.onTaskPostExecuteListener(result);
		}
	}
	
	public void setPostExecuteListener(OnTaskPostExecuteListener<List<Talk>> taskPostExecute){
		onTaskPostExecuteListener = taskPostExecute;
	}
	
}
