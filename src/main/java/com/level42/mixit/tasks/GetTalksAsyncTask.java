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

/**
 * Tâche asynchrone pour la collecte de la liste des talks.
 */
public class GetTalksAsyncTask extends
        AsyncTask<ITalkService, Integer, List<Talk>> {

    /**
     * Listener.
     */
    private OnTaskPostExecuteListener<List<Talk>> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption.
     */
    private Exception cancelReason;

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected List<Talk> doInBackground(ITalkService... params) {
        try {
            ITalkService service = (ITalkService) params[0];
            return service.getTalks();
        } catch (FunctionnalException e) {
            Log.e(Utils.LOGTAG, e.getMessage());
            Log.d(Utils.LOGTAG, e.getCause().getMessage());
            cancelReason = e;
        } catch (TechnicalException e) {
            Log.e(Utils.LOGTAG, e.getMessage());
            Log.d(Utils.LOGTAG, e.getCause().getMessage());
            cancelReason = e;
        }
        this.cancel(true);
        return null;
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onCancelled()
     */
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

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(List<Talk> result) {
        super.onPostExecute(result);
        
        if (result != null) {
            Log.d(Utils.LOGTAG, "Nombre de talks : " + result.size());
        } else {
            Log.d(Utils.LOGTAG, "Aucun talks");
        }        

        if (onTaskPostExecuteListener != null) {
            onTaskPostExecuteListener.onTaskPostExecuteListener(result);
        }
    }

    /**
     * Permet de renseigner un listener.
     * @param taskPostExecute
     *            Listener de la tâche
     */
    public void setPostExecuteListener(
            OnTaskPostExecuteListener<List<Talk>> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
