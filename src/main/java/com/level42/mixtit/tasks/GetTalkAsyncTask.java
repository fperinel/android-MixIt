package com.level42.mixtit.tasks;

import android.os.AsyncTask;

import com.level42.mixtit.exceptions.CommunicationException;
import com.level42.mixtit.exceptions.NotFoundException;
import com.level42.mixtit.listeners.OnTaskPostExecuteListener;
import com.level42.mixtit.models.Talk;
import com.level42.mixtit.services.ITalkService;

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
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
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
