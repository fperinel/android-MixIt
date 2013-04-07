package com.level42.mixit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.level42.mixit.exceptions.FunctionnalException;
import com.level42.mixit.exceptions.TechnicalException;
import com.level42.mixit.listeners.OnTaskPostExecuteListener;
import com.level42.mixit.models.LightningTalk;
import com.level42.mixit.services.ILightningTalkService;
import com.level42.mixit.utils.Utils;

public class GetLightningTalkAsyncTask extends
        AsyncTask<Object, Integer, LightningTalk> {

    /**
     * Listener
     */
    private OnTaskPostExecuteListener<LightningTalk> onTaskPostExecuteListener = null;

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
    protected void onPostExecute(LightningTalk result) {
        super.onPostExecute(result);

        Log.d(Utils.LOGTAG, "Lightning talks : " + result.getId().toString()
                + " chargé");

        if (onTaskPostExecuteListener != null) {
            onTaskPostExecuteListener.onTaskPostExecuteListener(result);
        }
    }

    public void setPostExecuteListener(
            OnTaskPostExecuteListener<LightningTalk> taskPostExecute) {
        onTaskPostExecuteListener = taskPostExecute;
    }

}
