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
 * Tâche asynchrone pour la collecte des informations d'un lightning talk.
 */
public class GetLightningTalkAsyncTask extends
        AsyncTask<Object, Integer, LightningTalk> {

    /**
     * Listener.
     */
    private OnTaskPostExecuteListener<LightningTalk> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption.
     */
    private Exception cancelReason;

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
    protected void onPostExecute(LightningTalk result) {
        super.onPostExecute(result);

        if (result != null) {
            Log.d(Utils.LOGTAG, "Lightning talks : " + result.getId().toString() + " chargé");
        } else {
            Log.d(Utils.LOGTAG, "Aucun Lightning talks");
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
            OnTaskPostExecuteListener<LightningTalk> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
