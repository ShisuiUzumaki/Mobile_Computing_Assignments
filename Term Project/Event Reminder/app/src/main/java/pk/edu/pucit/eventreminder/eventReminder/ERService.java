package pk.edu.pucit.eventreminder.eventReminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import java.io.Serializable;
import java.util.Objects;

import pk.edu.pucit.eventreminder.AddReminder;
import pk.edu.pucit.eventreminder.R;
import pk.edu.pucit.eventreminder.data.ERContract;

public class ERService extends IntentService {

          private static final int PI_REQUEST_CODE = 0;

          private static final Uri notification = RingtoneManager.getDefaultUri (
                    RingtoneManager.TYPE_RINGTONE);
          private static Ringtone r = null;

          private static final String TAG = ERService.class.getSimpleName(); //TAG to be used in constructor
          private static final int NOTIFICATION_ID = 25342;  //id for notification
          Cursor  cursor;  // cursor for handling data of an event and providing it to activity to show to user

          public ERService() {
                    super (TAG);
          }

          //This is a deep link intent, and needs the task stack
          public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {

                    Intent broadcastToBePerformed = new Intent(context, ERReceiver.class);
                    broadcastToBePerformed.setData(uri);
                    return PendingIntent.getBroadcast (context, PI_REQUEST_CODE, broadcastToBePerformed,
                              PendingIntent.FLAG_UPDATE_CURRENT);
          }

          @Override
          protected void onHandleIntent(@Nullable Intent intent) {

                    assert intent != null;
                    Uri uri = intent.getData();

                    if(!Objects.equals (intent.getStringExtra ("stop"), "stop ringtone")){
                              r = RingtoneManager.getRingtone (getApplicationContext (), notification);
                              //Display a notification to view the task details
                              Intent action = new Intent(this, AddReminder.class);
                              action.setData(uri);
                              PendingIntent operation = TaskStackBuilder.create(this)
                                        .addNextIntentWithParentStack(action)
                                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                              //Grab the task description
                              if(uri != null){
                                        cursor = getContentResolver().query(uri, null, null,
                                                  null, null);
                              }

                              String description = "";
                              try {
                                        if (cursor != null && cursor.moveToFirst()) {
                                                  description = ERContract.getColumnString(cursor,
                                                            ERContract.EREntry.EVENT_TITLE);
                                        }
                              }
                              finally {
                                        if (cursor != null) {
                                                  cursor.close();
                                        }
                              }
                              String channelId = "12345";

                              Intent stopRingtone = new Intent (getApplicationContext (), ERService.class);
                              stopRingtone.putExtra ("stop","stop ringtone");
                              PendingIntent piStopRingtone = PendingIntent.getService (getApplicationContext (),2341,
                                        stopRingtone, 0);

                              if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                        CharSequence name = "Event Reminder channel";
                                        String descriptor = "Event reminder notification channel";
                                        int importance =NotificationManager.IMPORTANCE_HIGH;
                                        NotificationChannel chanel
                                                  = new NotificationChannel (channelId,  name, importance);
                                        chanel.setDescription(description);
                                        NotificationManager manager = getSystemService (NotificationManager.class);
                                        assert manager != null;
                                        manager.createNotificationChannel (chanel);
                              }


                              Notification note = new NotificationCompat.Builder(this, channelId)
                                        .setContentTitle(getString(R.string.app_name))
                                        .setContentText(description)
                                        .setColorized (true)
                                        .setColor (getResources ().getColor (R.color.LightGreen))
                                        .setPriority (NotificationCompat.PRIORITY_HIGH)
                                        .setCategory (NotificationCompat.CATEGORY_EVENT)
                                        .setSmallIcon(R.drawable.ic_add_alert)
                                        .setContentIntent(operation)
                                        .addAction (R.mipmap.ic_launcher, "dismiss", piStopRingtone)
                                        .setAutoCancel(true)
                                        .build();

                              NotificationManagerCompat manager =
                                        NotificationManagerCompat.from (getApplicationContext ());
                              manager.notify(NOTIFICATION_ID, note);
                              if(r != null && !r.isPlaying ()){
                                        r.play ();
                              }
                    }
                    else{
                              if(r != null && r.isPlaying ()){
                                        r.stop ();;
                                        r = null;
                                        intent.putExtra ("stop",  "null");
                              }
                    }


          }
}
