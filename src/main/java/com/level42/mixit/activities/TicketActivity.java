package com.level42.mixit.activities;

import java.util.EnumMap;
import java.util.Map;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.level42.mixit.R;
import com.level42.mixit.utils.MessageBox;
import com.level42.mixit.utils.Utils;

/**
 * Ecran d'affichage ou d'aquisition du QRCode du ticket
 */
@ContentView(R.layout.activity_ticket)
public class TicketActivity extends RoboActivity {
    
    /**
     * Espace de nom des préférences
     */
    public static final String PREF_NS = "mixit";
    
    /**
     * Clef de préférence code du ticket 1
     */
    public static final String PREF_TICKET1 = "ticket1";
    
    /**
     * Clef de préférence format du ticket 1
     */
    public static final String PREF_TICKET_FORMAT1 = "ticket_format1";   

    /**
     * Clef de préférence type de correction QRCode du ticket 1
     */
    public static final String PREF_TICKET_CORRECTION1 = "ticket_correction1";
    
    /**
     * Clef de préférence code du ticket 2
     */
    public static final String PREF_TICKET2 = "ticket2";

    /**
     * Clef de préférence format du ticket 2
     */
    public static final String PREF_TICKET_FORMAT2 = "ticket_format2";   

    /**
     * Clef de préférence type de correction QRCode du ticket 2
     */
    public static final String PREF_TICKET_CORRECTION2 = "ticket_correction2";

    /**
     * Hauteur du QRCode
     */
    private static final int QRCODE_HEIGHT = 400;

    /**
     * Largeur du QRCode
     */
    private static final int QRCODE_WIDTH = 400;

    private static final String  BARCODE_PACKAGE = "com.google.zxing.client.android";

    /**
     * Service de gestion des préférences
     */
    private SharedPreferences settings;
    
    /**
     * QRCode ticket 1
     */
    private String ticket1 = null;
    
    /**
     * QRCode correction ticket 1
     */
    private String ticketErrorCorrection1 = null;
    
    /**
     * QRCode ticket 2
     */
    private String ticket2 = null;
    
    /**
     * QRCode correction ticket 2
     */
    private String ticketErrorCorrection2 = null;
    
    /**
     * COntrôle : Image du ticket 1
     */
    @InjectView(R.id.imageTicket1)
    private ImageView imageTicket1;
    
    /**
     * COntrôle : Image du ticket 2
     */
    @InjectView(R.id.imageTicket2)
    private ImageView imageTicket2;
    
    /*
     * (non-Javadoc)
     * @see roboguice.activity.RoboTabActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.settings = getSharedPreferences(TicketActivity.PREF_NS, MODE_PRIVATE);
        
        // Affiche le ticket déjà enregistré
        if (settings.contains(TicketActivity.PREF_TICKET1)) {
            ticket1 = settings.getString(TicketActivity.PREF_TICKET1, null);
            ticketErrorCorrection1 = settings.getString(TicketActivity.PREF_TICKET_CORRECTION1, null);
        }
        if (settings.contains(TicketActivity.PREF_TICKET2)) {
            ticket2 = settings.getString(TicketActivity.PREF_TICKET2, null);
            ticketErrorCorrection2 = settings.getString(TicketActivity.PREF_TICKET_CORRECTION2, null);
        } 
        this.showTicket();
    }

    /**
     * Affiche les tickets
     */
    protected void showTicket() {
        if (ticket1 != null) {
            imageTicket1.setImageBitmap(this.constructQRCode(ticket1, ticketErrorCorrection1));
        }
        if (ticket2 != null) {
            imageTicket2.setImageBitmap(this.constructQRCode(ticket2, ticketErrorCorrection2));
        }
    }
    
    /**
     * Construit un QR code
     * @param ticketQR
     * @param correctionQR
     * @return Image du QRCode
     */
    protected Bitmap constructQRCode(String ticketQR, String correctionQR) {
        ProgressDialog progress = null;
        OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,
                    int which) {
                finish();
            }
        };
        try {
            progress = MessageBox.getProgressDialog(TicketActivity.this);
            QRCodeWriter qrWriter = new QRCodeWriter();

            Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            
            if (ErrorCorrectionLevel.H.toString().equals(correctionQR)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            }
            if (ErrorCorrectionLevel.L.toString().equals(correctionQR)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            }
            if (ErrorCorrectionLevel.M.toString().equals(correctionQR)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            }
            if (ErrorCorrectionLevel.Q.toString().equals(correctionQR)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
            }
            
            BitMatrix result = qrWriter.encode(ticketQR, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT, hints);
            return Utils.generateImageFromQRCode(result);
        } catch (WriterException e) {
            MessageBox.showError(getResources().getString(R.string.label_dialog_error), 
                    getResources().getString(R.string.exception_message_CommunicationException), 
                    listener, TicketActivity.this);
            return null;
        } finally {
            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }
        }
    }
    
    /**
     * Action d'ajout d'un ticket
     * @param v
     */
    public void actionAddTicket(View v) {
        if (Utils.checkpackage(TicketActivity.this, BARCODE_PACKAGE)) {
            Intent intent = new Intent(BARCODE_PACKAGE + ".SCAN");
            intent.setPackage(BARCODE_PACKAGE);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, v.getId());
        } else {
            Uri marketUri = Uri.parse("market://details?id=" + BARCODE_PACKAGE);
            Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(marketUri);
            startActivity(marketIntent);
        }
    }
    
    /**
     * Resultat de l'activité
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == R.id.imageTicket1) {
            if (resultCode == RESULT_OK) {
                ticket1 = intent.getStringExtra("SCAN_RESULT");
                ticketErrorCorrection1 = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
                
                SharedPreferences.Editor editor = this.settings.edit();
                editor.putString(TicketActivity.PREF_TICKET1, ticket1);
                editor.putString(TicketActivity.PREF_TICKET_CORRECTION1, ticketErrorCorrection1);
                editor.commit();
                
                this.showTicket();
            }
        }
        if (requestCode == R.id.imageTicket2) {
            if (resultCode == RESULT_OK) {
                ticket2 = intent.getStringExtra("SCAN_RESULT");
                ticketErrorCorrection2 = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");
                
                SharedPreferences.Editor editor = this.settings.edit();
                editor.putString(TicketActivity.PREF_TICKET2, ticket2);
                editor.putString(TicketActivity.PREF_TICKET_CORRECTION2, ticketErrorCorrection2);
                editor.commit();
                
                this.showTicket();
            }
        }
    }
    
}