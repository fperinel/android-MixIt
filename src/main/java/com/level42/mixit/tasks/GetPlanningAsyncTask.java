package com.level42.mixit.tasks;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.GroupedTalks;
import com.level42.mixit.services.IPlanningService;
import com.level42.mixit.utils.Utils;

/**
 * Tâche asynchrone pour la collecte des informations des sessions.
 */
public class GetPlanningAsyncTask extends
        AsyncTask<Object, Integer, List<GroupedTalks>> {

    /**
     * Listener.
     */
    private OnTaskPostExecuteListener<List<GroupedTalks>> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption.
     */
    private Exception cancelReason;

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected List<GroupedTalks> doInBackground(Object... params) {
        try {
            IPlanningService service = (IPlanningService) params[0];
            Integer delay = (Integer) params[1];
            return service.getTalksForPlanning(delay);
        } catch (FunctionnalException e) {
            Log.e(Utils.logTag, e.getMessage());
            cancelReason = e;
        } catch (TechnicalException e) {
            Log.e(Utils.logTag, e.getMessage());
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
    protected void onPostExecute(List<GroupedTalks> result) {
        super.onPostExecute(result);

        Log.d(Utils.logTag, "Nombre de talks : " + result.size());

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
            OnTaskPostExecuteListener<List<GroupedTalks>> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
