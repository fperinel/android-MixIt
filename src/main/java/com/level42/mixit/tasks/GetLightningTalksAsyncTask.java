package com.level42.mixit.tasks;

import java.util.List;

import android.os.AsyncTask;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.services.ILightningTalkService;

public class GetLightningTalksAsyncTask extends AsyncTask<ILightningTalkService, Integer, List<LightningTalk>> {
	
	/**
	 * Listener
	 */
	private OnTaskPostExecuteListener<List<LightningTalk>> onTaskPostExecuteListener = null;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}
	
	@Override
	protected void onProgressUpdate(Integer... values){
		super.onProgressUpdate(values);
	}

	@Override
	protected List<LightningTalk> doInBackground(ILightningTalkService... params) {
		try {
			ILightningTalkService service = (ILightningTalkService) params[0];
			return service.getLightningTalks();
		} catch (FunctionnalException e) {
			e.printStackTrace();
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<LightningTalk> result) {
		super.onPostExecute(result);
		
		if(onTaskPostExecuteListener != null) {
			onTaskPostExecuteListener.onTaskPostExecuteListener(result);
		}
	}
	
	public void setPostExecuteListener(OnTaskPostExecuteListener<List<LightningTalk>> taskPostExecute){
		onTaskPostExecuteListener = taskPostExecute;
	}
	
}
