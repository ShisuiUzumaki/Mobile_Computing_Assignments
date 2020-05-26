package pk.edu.pucit.eventreminder.eventReminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import java.util.Objects;

public class ERReceiver extends BroadcastReceiver {

          @Override
          public void onReceive(Context context, Intent intent) {

                    Intent serviceToBeStarted = new Intent (context, ERService.class);
                    assert intent != null;
                    Toast.makeText (context, "Alarm time happened", Toast.LENGTH_LONG).show ();
                    Uri uri = intent.getData();
                    serviceToBeStarted.setData (uri);
                    context.startService (serviceToBeStarted);
          }
}
