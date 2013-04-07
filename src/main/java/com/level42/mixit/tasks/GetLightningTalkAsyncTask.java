package com.level42.mixit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.utils.Utils;

/**
 * Tâche asynchrone pour la collecte des informations d'un lightning talk
 */
public class GetLightningTalkAsyncTask extends
        AsyncTask<Object, Integer, LightningTalk> {

    /**
     * Listener
     */
    private OnTaskPostExecuteListener<LightningTalk> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption
     */
    private Exception cancelReason;

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onPreExecute()
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected LightningTalk doInBackground(Object... params) {
        try {
            ILightningTalkService service = (ILightningTalkService) params[0];
            Integer talkId = (Integer) params[1];
            return service.getLightningTalk(talkId);
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
    protected void onPostExecute(LightningTalk result) {
        super.onPostExecute(result);

        Log.d(Utils.LOGTAG, "Lightning talks : " + result.getId().toString()
                + " chargé");

        if (onTaskPostExecuteListener != null) {
            onTaskPostExecuteListener.onTaskPostExecuteListener(result);
        }
    }

    /**
     * Permet de renseigner un listener
     * 
     * @param taskPostExecute
     *            Listener de la tâche
     */
    public void setPostExecuteListener(
            OnTaskPostExecuteListener<LightningTalk> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
