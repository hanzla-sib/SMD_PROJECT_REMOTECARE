package com.waqasahmad.remotecare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    Context context;
    private static String DATABASE_NAME = "mydb.db";
    private static int DATABASE_VERSION = 1;
    private static String createTableQuery = "create table imageInfo (email TEXT" + ",image BLOB)";
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageinbyte;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(createTableQuery);
            Toast.makeText(context, "Table created succesfully inside our database", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // storing images in sqlLite for faster retrieval

    public void storeImage(ModelClassoffline objectmodelclass) {
        try {
            SQLiteDatabase objectSqliteDatabse = this.getWritableDatabase();
            Bitmap imagetostorebitmap = objectmodelclass.getImage();
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imagetostorebitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            imageinbyte = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();
            objectContentValues.put("email", objectmodelclass.getEmail());
            objectContentValues.put("image", imageinbyte);
            long checkifqueryruns = objectSqliteDatabse.insert("imageInfo", null, objectContentValues);
            if (checkifqueryruns != -1) {
                Toast.makeText(context, "Data Inserted in our table", Toast.LENGTH_SHORT).show();
                objectSqliteDatabse.close();
            } else {
                Toast.makeText(context, "fails to add data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // making an array list of all images
    public ArrayList<ModelClassoffline> getAllimagesdata() {
        try {
            SQLiteDatabase objectSqliteDatabase = this.getReadableDatabase();
            ArrayList<ModelClassoffline> objectModelclasslist = new ArrayList<>();
            Cursor objectCursor = objectSqliteDatabase.rawQuery("select * from imageInfo", null);
            if (objectCursor.getCount() != 0) {
                while (objectCursor.moveToNext()) {
                    String nameofemail = objectCursor.getString(0);
                    byte[] imageBytes = objectCursor.getBlob(1);
                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    objectModelclasslist.add(new ModelClassoffline(nameofemail, objectBitmap));
                }
                return objectModelclasslist;
            } else {
                Toast.makeText(context, "No values exists in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;

        }
    }
}

