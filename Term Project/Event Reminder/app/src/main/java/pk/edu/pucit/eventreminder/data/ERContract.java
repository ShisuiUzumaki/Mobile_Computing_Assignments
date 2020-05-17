package pk.edu.pucit.eventreminder.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class ERContract {
          private ERContract() {}

          public static final String CONTENT_AUTHORITY = "pk.edu.pucit.eventreminder";

          public static final String ER_TABLE_PATH= "er_reminderTable";

          public static final Uri ER_BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


          public static final class EREntry implements BaseColumns {

                    public static final Uri CONTENT_URI = Uri.withAppendedPath(ER_BASE_CONTENT_URI,
                              ER_TABLE_PATH);

                    public static final String CONTENT_LIST_TYPE =
                              ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                                        CONTENT_AUTHORITY + "/" +
                                        ER_TABLE_PATH;

                    public static final String CONTENT_ITEM_TYPE =
                              ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                                        + CONTENT_AUTHORITY + "/"
                                        + ER_TABLE_PATH;

                    public final static String TABLE_NAME = "events_reminders";

                    public final static String EVENT_ID = BaseColumns._ID;

                    public static final String EVENT_TITLE = "title";
                    public static final String EVENT_DATE = "date";
                    public static final String EVENT_TIME = "time";
                    public static final String EVENT_REPEAT = "repeat";
                    public static final String EVENT_REPEAT_NO = "repeat_no";
                    public static final String EVENT_REPEAT_TYPE = "repeat_type";
                    public static final String EVENT_ACTIVE = "active";

          }

          public static String getColumnString(Cursor cursor, String columnName) {
                    return cursor.getString( cursor.getColumnIndex(columnName) );
          }
}
