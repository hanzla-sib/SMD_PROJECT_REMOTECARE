package com.waqasahmad.remotecare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDialog {
    public void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("This is my dialog message")
                .setTitle("My Dialog Title");

        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.cancel();
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
