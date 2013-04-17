package com.level42.mixit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Talk;
import com.level42.mixit.services.ITalkService;
import com.level42.mixit.utils.Utils;

/**
 * Tâche asynchrone pour la collecte des informations d'un talk.
 */
public class GetTalkAsyncTask extends AsyncTask<Object, Integer, Talk> {

    /**
     * Listener.
     */
    private OnTaskPostExecuteListener<Talk> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption.
     */
    private Exception cancelReason;

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Talk doInBackground(Object... params) {
        try {
            ITalkService service = (ITalkService) params[0];
            Integer talkId = (Integer) params[1];
            return service.getTalk(talkId);
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
    protected void onPostExecute(Talk result) {
        super.onPostExecute(result);

        if (result != null) {
            Log.d(Utils.LOGTAG, "Talk : " + result.getId().toString() + " chargé");
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
            OnTaskPostExecuteListener<Talk> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
