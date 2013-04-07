package com.level42.mixit.tasks;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.utils.Utils;

public class GetTalksAsyncTask extends
        AsyncTask<ITalkService, Integer, List<Talk>> {

    /**
     * Listener
     */
    private OnTaskPostExecuteListener<List<Talk>> onTaskPostExecuteListener = null;

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
    protected List<Talk> doInBackground(ITalkService... params) {
        try {
            ITalkService service = (ITalkService) params[0];
            return service.getTalks();
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
    protected void onPostExecute(List<Talk> result) {
        super.onPostExecute(result);

        Log.d(Utils.LOGTAG, "Nombre de talks : " + result.size());

        if (onTaskPostExecuteListener != null) {
            onTaskPostExecuteListener.onTaskPostExecuteListener(result);
        }
    }

    public void setPostExecuteListener(
            OnTaskPostExecuteListener<List<Talk>> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
