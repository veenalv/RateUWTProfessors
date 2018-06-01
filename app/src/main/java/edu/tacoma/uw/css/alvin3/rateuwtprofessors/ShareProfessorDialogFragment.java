/**
 * TCSS 450 - Spring 2018 Team 8.
 * @author Alvin Nguyen
 * @author Maksim B Voznyarskiy
 * @author Hui Ting Cai
 */
package edu.tacoma.uw.css.alvin3.rateuwtprofessors;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import static java.net.Proxy.Type.HTTP;

/**
 * Fragment that is used to display the share content dialog.
 */
public class ShareProfessorDialogFragment extends android.support.v4.app.DialogFragment {
    /** message that the will be shared. */
    private String mMessage;

    /**
     * Sets the content message.
     *
     * @param message is the message to set the mMessage to.
     */
    public void setMessage(String message) {
        mMessage = message;
    }

    /**
     * Method called to create the share content dialog.
     *
     * @param savedInstanceState is the bundle.
     *
     * @return the dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Share this rating!")
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    /**
                     * On click listener for the share button.
                     *
                     * @param dialog is the dialog.
                     * @param which is which button is pressed.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // share the rating
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        sendIntent.setData(Uri.parse("sms:"));
                        sendIntent.putExtra("sms_body", mMessage);
                        startActivity(sendIntent);
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * On click listener for the cancel button.
                     *
                     * @param dialog is the dialog.
                     * @param which is which button is pressed.
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User cancelled the dialog
                    }
                });
        // create the AlertDialog object and return it
        return builder.create();
    }
}
