package pk.edu.pucit.eventreminder.eventReminder;

import android.app.AlarmManager;
import android.content.Context;

public class AlarmManagerProvider {
          private static final String TAG = AlarmManagerProvider.class.getSimpleName();
          private static AlarmManager sAlarmManager;

          //synchronized so that only one thread can use it at a time and no inconsistencies occur
          public static synchronized void injectAlarmManager(AlarmManager alarmManager) {
                    if (sAlarmManager != null) {
                              throw new IllegalStateException("Alarm Manager Already Set");
                    }
                    sAlarmManager = alarmManager;
          }

           static synchronized AlarmManager getAlarmManager(Context context) {
                    if (sAlarmManager == null) {
                              sAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    }
                    return sAlarmManager;
          }
}
