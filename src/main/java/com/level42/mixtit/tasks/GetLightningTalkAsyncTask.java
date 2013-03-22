package com.level42.mixtit.tasks;

import android.os.AsyncTask;

import com.level42.mixtit.exceptions.FunctionnalException;
import com.level42.mixtit.exceptions.TechnicalException;
import com.level42.mixtit.listeners.OnTaskPostExecuteListener;
import com.level42.mixtit.models.LightningTalk;
import com.level42.mixtit.services.ILightningTalkService;

public class GetLightningTalkAsyncTask extends AsyncTask<Object, Integer, LightningTalk> {
	
	/**
	 * Listener
	 */
	private OnTaskPostExecuteListener<LightningTalk> onTaskPostExecuteListener = null;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}

	@Override
	protected LightningTalk doInBackground(Object... params) {
		try {
			ILightningTalkService service = (ILightningTalkService) params[0];
			Integer talkId = (Integer) params[1];
			return service.getLightningTalk(talkId);
		} catch (FunctionnalException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(LightningTalk result) {
		super.onPostExecute(result);
		
		if(onTaskPostExecuteListener != null) {
			onTaskPostExecuteListener.onTaskPostExecuteListener(result);
		}
	}
	
	public void setPostExecuteListener(OnTaskPostExecuteListener<LightningTalk> taskPostExecute){
		onTaskPostExecuteListener = taskPostExecute;
	}
	
}
