package com.level42.mixit.tasks;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Favoris;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.utils.Utils;

/**
 * Tâche asynchrone pour la collecte de la liste des talks.
 */
public class GetFavoritesAsyncTask extends
        AsyncTask<Object, Integer, List<Favoris>> {

    /**
     * Listener.
     */
    private OnTaskPostExecuteListener<List<Favoris>> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption.
     */
    private Exception cancelReason;

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected List<Favoris> doInBackground(Object... params) {
        try {
            ITalkService service = (ITalkService) params[0];
            Integer id = (Integer) params[1];
            return service.getFavoris(id);
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
    protected void onPostExecute(List<Favoris> result) {
        super.onPostExecute(result);

        Log.d(Utils.LOGTAG, "Nombre de favoris : " + result.size());

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
            OnTaskPostExecuteListener<List<Favoris>> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
