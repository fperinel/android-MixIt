package com.level42.mixit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.Member;
import com.level42.mixit.services.IEntityService;
import com.level42.mixit.utils.Utils;

/**
 * Tâche asynchrone pour la collecte de la liste des talks.
 */
public class GetMemberIdAsyncTask extends
        AsyncTask<Object, Integer, Member> {

    /**
     * Listener.
     */
    private OnTaskPostExecuteListener<Member> onTaskPostExecuteListener = null;

    /**
     * Raison de l'interuption.
     */
    private Exception cancelReason;

    /*
     * (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected Member doInBackground(Object... params) {
        try {
            IEntityService service = (IEntityService) params[0];
            String login = (String) params[1];
            return service.getMemberIdByLogin(login);
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
    protected void onPostExecute(Member result) {
        super.onPostExecute(result);

        if (result != null) {
            Log.d(Utils.LOGTAG, "Membre : " + result.getLogin() + " trouvé");
        } else {
            Log.w(Utils.LOGTAG, "Membre introuvable");            
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
            OnTaskPostExecuteListener<Member> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
