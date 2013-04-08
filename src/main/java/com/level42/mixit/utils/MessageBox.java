package com.level42.mixit.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

import com.level42.mixit.R;

/**
 * Classe utilitaire pour les boites de dialog.
 */
public class MessageBox {

    /**
     * On masque le constructeur par défaut
     */
    private MessageBox() {
    }
    
    /**
     * Construit et affiche une boite de dialog de type "Erreur".
     *
     * @param title
     *            Titre de la boite
     * @param message
     *            Message
     * @param validationListener
     *            Ecouteur sur le bouton OK
     * @param context
     *            Contexte parent du dialog
     */
    public static void showError(String title, String message,
            OnClickListener validationListener, Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setButton(context.getString(R.string.btn_dialog_ok),
                validationListener);
        alertDialog.show();
    }

    /**
     * Construit et affiche une boite de dialog de type "Information".
     *
     * @param message Message
     * @param context Contexte parent du dialog
     */
    public static void showInformation(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Construit et affiche une boite de dialog de type "Information".
     *
     * @param message Message
     * @param context Contexte parent du dialog
     */
    public static ProgressDialog getProgressDialog(Context context) {
        return ProgressDialog.show(context, null,
                context.getString(R.string.loading_message_talks), false, true);
    }
}
