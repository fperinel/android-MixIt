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
public final class MessageBox {

    /**
     * On masque le constructeur par défaut.
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
    public static void showError(final String title, final String message,
            final OnClickListener validationListener, final Context context) {
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
    public static void showInformation(final String message, final Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Construit et affiche une boite de dialog de type "Information".
     *
     * @param context Contexte parent du dialog
     */
    public static ProgressDialog getProgressDialog(final Context context) {
        return MessageBox.getProgressDialog(context, true);
    }

    /**
     * Construit et affiche une boite de dialog de type "Information".
     *
     * @param context Contexte parent du dialog
     */
    public static ProgressDialog getProgressDialog(final Context context, final boolean cancelable) {
        return ProgressDialog.show(context, null, context.getString(R.string.loading_message), false, cancelable);
    }
}
