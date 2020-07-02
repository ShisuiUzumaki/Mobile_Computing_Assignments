package pk.edu.pucit.eventreminder.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ERDBAdaptor {

          private static final String LOG_TAG = ERDBHelper.class.getSimpleName();

          public boolean queryReminder(ERDBHelper mDBHelper, ContentValues values){
                    boolean flag = false;
                    String[] selectionArgs = {
                              values.get (ERContract.EREntry.EVENT_TIME)+"",
                              values.get (ERContract.EREntry.EVENT_DATE)+""};
                    SQLiteDatabase database = mDBHelper.getReadableDatabase ();
                    Cursor cursor = database.query (ERContract.EREntry.TABLE_NAME,
                              null,
                              ERContract.EREntry.EVENT_TIME+"=? AND " +
                                                  ERContract.EREntry.EVENT_DATE+"=?",
                               selectionArgs,
                              null,
                              null,
                              null);
                    if(cursor.moveToNext ()){
                              flag = true;
                    }
                    return flag;
          }

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
