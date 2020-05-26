package pk.edu.pucit.eventreminder.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ERContentProvider extends ContentProvider {

          public static final String LOG_TAG = ERContentProvider.class.getSimpleName();

          private static final int REMINDER = 100;

          private static final int REMINDER_ID = 101;

          private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

          static {

                    sUriMatcher.addURI(ERContract.CONTENT_AUTHORITY,
                              ERContract.ER_TABLE_PATH, REMINDER);

                    sUriMatcher.addURI(ERContract.CONTENT_AUTHORITY,
                              ERContract.ER_TABLE_PATH + "/#", REMINDER_ID);

          }

          private ERDBHelper mERDBHelper;
          private ERDBAdaptor mERDBAdaptor;

          public ERContentProvider() {
          }

          @Override
          public boolean onCreate() {
                    this.mERDBHelper = new ERDBHelper (getContext ());
                    this.mERDBAdaptor = new ERDBAdaptor ();
                    return false;
          }

          @Override
          public String getType(@NonNull Uri uri) {
                    final int match = sUriMatcher.match(uri);
                    switch (match) {
                              case REMINDER:
                                        return ERContract.EREntry.CONTENT_LIST_TYPE;
                              case REMINDER_ID:
                                        return ERContract.EREntry.CONTENT_ITEM_TYPE;
                              default:
                                        throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
                    }
          }

          @Override
          public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                              String[] selectionArgs, String sortOrder) {
                    SQLiteDatabase database = mERDBHelper.getReadableDatabase();

                    // This cursor will hold the result of the query
                    Cursor cursor = null;

                    int match = sUriMatcher.match(uri);
                    switch (match) {
                              case REMINDER:
                                        cursor = database.query(ERContract.EREntry.TABLE_NAME, projection, selection, selectionArgs,
                                                  null, null, sortOrder);
                                        break;
                              case REMINDER_ID:
                                        selection = ERContract.EREntry.EVENT_ID + "=?";
                                        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                                        cursor = database.query(ERContract.EREntry.TABLE_NAME, projection, selection, selectionArgs,
                                                  null, null, sortOrder);
                                        break;
                              default:
                                        throw new IllegalArgumentException("Cannot query unknown URI " + uri);
                    }

                    //this is called to register Loader's ForceLoadObserver with current context's
                    //uri so that when a change occurs because of getContext().getContentResolver().notifyChange(uri, contentObserver)
                    //Loader is notified

                    cursor.setNotificationUri(Objects.requireNonNull (getContext ()).getContentResolver(), uri);
                    return cursor;

          }

          @Override
          public Uri insert(@NonNull Uri uri, ContentValues values) {
                    final int match = sUriMatcher.match(uri);
                    switch (match) {
                              case REMINDER:
                                        Uri resultUri = mERDBAdaptor.insertReminder (mERDBHelper,uri,values);
                                        Objects.requireNonNull (getContext ()).getContentResolver().notifyChange(uri, null);
                                        return resultUri;

                              default:
                                        throw new IllegalArgumentException("Insertion is not supported for " + uri);
                    }
          }

          @Override
          public int update(@NonNull Uri uri, ContentValues values, String selection,
                            String[] selectionArgs) {
                    final int match = sUriMatcher.match(uri);
                    int rowsUpdated = 0;
                    switch (match) {
                              case REMINDER:
                                        rowsUpdated =mERDBAdaptor.updateReminder(mERDBHelper, values, selection, selectionArgs);
                                        break;
                              case REMINDER_ID:
                                        selection = ERContract.EREntry.EVENT_ID + "=?";
                                        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                                        rowsUpdated = mERDBAdaptor.updateReminder(mERDBHelper, values, selection, selectionArgs);
                                        break;
                              default:
                                        throw new IllegalArgumentException("Update is not supported for " + uri);
                    }
                    if (rowsUpdated != 0) {
                              Objects.requireNonNull (getContext ()).getContentResolver().notifyChange(uri, null);
                    }
                    return rowsUpdated;
          }

          @Override
          public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

                    int rowsDeleted;

                    final int match = sUriMatcher.match(uri);
                    switch (match) {
                              case REMINDER:
                                        rowsDeleted = mERDBAdaptor.delete(mERDBHelper,selection, selectionArgs);
                                        break;
                              case REMINDER_ID:
                                        selection = ERContract.EREntry.EVENT_ID + "=?";
                                        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                                        rowsDeleted = mERDBAdaptor.delete(mERDBHelper, selection, selectionArgs);
                                        break;
                              default:
                                        throw new IllegalArgumentException("Deletion is not supported for " + uri);
                    }

                    if (rowsDeleted != 0) {
                              Objects.requireNonNull (getContext ()).getContentResolver().notifyChange(uri, null);
                    }

                    return rowsDeleted;
          }

}
