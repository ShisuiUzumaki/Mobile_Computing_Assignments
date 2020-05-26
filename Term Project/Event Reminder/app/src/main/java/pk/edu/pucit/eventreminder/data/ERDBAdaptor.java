package pk.edu.pucit.eventreminder.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

class ERDBAdaptor {

          private static final String LOG_TAG = ERDBHelper.class.getSimpleName();

          Uri insertReminder(ERDBHelper mDbHelper, Uri uri, ContentValues values) {

                    SQLiteDatabase
                              database = mDbHelper.getWritableDatabase();

                    long id = database.insert(ERContract.EREntry.TABLE_NAME,
                              null,
                              values);

                    if (id == -1) {
                              Log.e(LOG_TAG, "Failed to insert row for " + uri);
                              return null;
                    }

                    return ContentUris.withAppendedId(uri, id);
          }

          int updateReminder(ERDBHelper mDbHelper,  ContentValues values, String selection, String[] selectionArgs) {

                    //if nothing to update
                    if (values.size() == 0) {
                              return 0;
                    }

                    SQLiteDatabase database = mDbHelper.getWritableDatabase();

                    return database.update(ERContract.EREntry.TABLE_NAME, values, selection, selectionArgs);
          }

          int delete(ERDBHelper mDbHelper, String selection, String[] selectionArgs){
                    SQLiteDatabase database = mDbHelper.getWritableDatabase();
                    return database.delete(ERContract.EREntry.TABLE_NAME, selection, selectionArgs);
          }

}
