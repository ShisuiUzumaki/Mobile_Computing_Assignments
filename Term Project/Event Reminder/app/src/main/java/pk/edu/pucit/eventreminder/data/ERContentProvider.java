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
import java.util.concurrent.atomic.AtomicInteger;

import static pk.edu.pucit.eventreminder.data.ERContract.*;

public class ERContentProvider extends ContentProvider {

          public static final String TAG  = ERContentProvider.class.getSimpleName();

          private static final int REMINDER_LIST = 100;
          
          private static final int REMINDER_ITEM_ID = 101;

          private static final UriMatcher ERUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

          static {

                    ERUriMatcher.addURI(ER_CONTENT_AUTHORITY,
                              ER_TABLE_PATH, REMINDER_LIST);

                    ERUriMatcher.addURI(ER_CONTENT_AUTHORITY,
                              ER_TABLE_PATH + "/#", REMINDER_ITEM_ID);

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
                    // code for deciding which uri is used what is requested
                    final int match = ERUriMatcher.match(uri);
                    switch (match) {
                              case REMINDER_LIST:
                                        return EREntry.CONTENT_LIST_TYPE;
                              case REMINDER_ITEM_ID:
                                        return EREntry.CONTENT_ITEM_TYPE;
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

                    // code for deciding which uri is used what is requested
                    int match = ERUriMatcher.match(uri);
                    switch (match) {
                              case REMINDER_LIST:
                                        cursor = database.query(EREntry.TABLE_NAME, projection, selection, selectionArgs,
                                                  null, null, sortOrder);
                                        break;
                              case REMINDER_ITEM_ID:
                                        selection = EREntry.EVENT_ID + "=?";
                                        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                                        cursor = database.query(EREntry.TABLE_NAME, projection, selection, selectionArgs,
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

                    // code for deciding which uri is used what is requested
                    final int match = ERUriMatcher.match(uri);
                    if (match == REMINDER_LIST) {
                              Uri resultUri = mERDBAdaptor.insertReminder (mERDBHelper, uri, values);
                              Objects.requireNonNull (getContext ()).getContentResolver ().notifyChange (uri, null);
                              return resultUri;
                    }
                    throw new IllegalArgumentException ("Insertion is not supported for " + uri);
          }

          @Override
          public int update(@NonNull Uri uri, ContentValues values, String selection,
                            String[] selectionArgs) {

                    // code for deciding which uri is used what is requested
                    final int match = ERUriMatcher.match(uri);
                    AtomicInteger rowsUpdated = new AtomicInteger ();
                    switch (match) {
                              case REMINDER_LIST:
                                        rowsUpdated.set (mERDBAdaptor.updateReminder (mERDBHelper, values, selection, selectionArgs));
                                        break;
                              case REMINDER_ITEM_ID:
                                        selection = EREntry.EVENT_ID + "=?";
                                        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                                        rowsUpdated.set (mERDBAdaptor.updateReminder (mERDBHelper, values, selection, selectionArgs));
                                        break;
                              default:
                                        throw new IllegalArgumentException("Update is not supported for " + uri);
                    }
                    if (rowsUpdated.get () != 0) {
                              Objects.requireNonNull (getContext ()).getContentResolver().notifyChange(uri, null);
                    }
                    return rowsUpdated.get ();
          }

          @Override
          public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

                    AtomicInteger rowsDeleted = new AtomicInteger ();
                    // code for deciding which uri is used what is requested
                    final int match = ERUriMatcher.match(uri);
                    switch (match) {
                              case REMINDER_LIST:
                                        rowsDeleted.set (mERDBAdaptor.delete (mERDBHelper, selection, selectionArgs));
                                        break;
                              case REMINDER_ITEM_ID:
                                        selection = EREntry.EVENT_ID + "=?";
                                        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                                        rowsDeleted.set (mERDBAdaptor.delete (mERDBHelper, selection, selectionArgs));
                                        break;
                              default:
                                        throw new IllegalArgumentException("Deletion is not supported for " + uri);
                    }

                    if (rowsDeleted.get () != 0) {
                              Objects.requireNonNull (getContext ()).getContentResolver().notifyChange(uri, null);
                    }

                    return rowsDeleted.get ();
          }

}
