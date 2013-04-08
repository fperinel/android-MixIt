package com.level42.mixit.tasks;

import java.util.List;
import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.utils.Utils;

/**
 * Tâche asynchrone pour la collecte des informations des talks.
 */
public class GetLightningTalksAsyncTask extends
        AsyncTask<ILightningTalkService, Integer, List<LightningTalk>> {

    /**
     * Listener.
     */
    private OnTaskPostExecuteListener<List<LightningTalk>> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption.
     */
    private Exception cancelReason;

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected List<LightningTalk> doInBackground(
            ILightningTalkService... params) {
        try {
            ILightningTalkService service = (ILightningTalkService) params[0];
            return service.getLightningTalks();
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
    protected void onPostExecute(List<LightningTalk> result) {
        super.onPostExecute(result);

        Log.d(Utils.logTag, "Nombre de lightning talks : " + result.size());

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
            OnTaskPostExecuteListener<List<LightningTalk>> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
