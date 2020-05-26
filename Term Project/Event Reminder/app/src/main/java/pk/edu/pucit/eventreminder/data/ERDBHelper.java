package pk.edu.pucit.eventreminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ERDBHelper extends SQLiteOpenHelper {

          private static final String DATABASE_NAME = "EventReminderDataBase";

          private static final int DATABASE_VERSION = 1;

          ERDBHelper(@Nullable Context context) {
                    super (context, DATABASE_NAME, null, DATABASE_VERSION);
          }

          @Override
          public void onCreate(SQLiteDatabase db) {

                    String SQL_CREATE_ALARM_TABLE =  "CREATE TABLE "
                              + ERContract.EREntry.TABLE_NAME + " ("
                              + ERContract.EREntry.EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                              + ERContract.EREntry.EVENT_TITLE + " TEXT NOT NULL, "
                              + ERContract.EREntry.EVENT_DATE + " TEXT NOT NULL, "
                              + ERContract.EREntry.EVENT_TIME + " TEXT NOT NULL, "
                              + ERContract.EREntry.EVENT_REPEAT + " TEXT NOT NULL, "
                              + ERContract.EREntry.EVENT_REPEAT_NO + " TEXT NOT NULL, "
                              + ERContract.EREntry.EVENT_REPEAT_TYPE + " TEXT NOT NULL"
                              + " );";

                    // Execute the SQL statement
                    db.execSQL(SQL_CREATE_ALARM_TABLE);
          }

          @Override
          public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

          }
}
