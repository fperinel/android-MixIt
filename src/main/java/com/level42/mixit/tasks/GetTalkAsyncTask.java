package com.level42.mixit.tasks;

import android.os.AsyncTask;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.ITalkService;

public class GetTalkAsyncTask extends AsyncTask<Object, Integer, Talk> {
	
	/**
	 * Listener
	 */
	private OnTaskPostExecuteListener<Talk> onTaskPostExecuteListener = null;
	
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
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Talk result) {
		super.onPostExecute(result);
		
		if(onTaskPostExecuteListener != null) {
			onTaskPostExecuteListener.onTaskPostExecuteListener(result);
		}
	}
	
	public void setPostExecuteListener(OnTaskPostExecuteListener<Talk> taskPostExecute){
		onTaskPostExecuteListener = taskPostExecute;
	}
	
}
