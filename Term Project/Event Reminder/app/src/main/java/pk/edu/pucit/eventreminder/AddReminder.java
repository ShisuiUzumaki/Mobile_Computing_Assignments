package pk.edu.pucit.eventreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class AddReminder extends AppCompatActivity {

          @Override
          protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate (savedInstanceState);
                    setContentView (R.layout.activity_add_reminder);

                   Toolbar myToolbar = (Toolbar) findViewById (R.id.AddReminderToolbar);
                    setSupportActionBar (myToolbar);
                    Objects.requireNonNull (getSupportActionBar ()).setTitle("Add Reminder");
                    myToolbar.setSubtitle("by Shisui Uzumaki");


//                    if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
//                              myToolbar.setElevation(10f);
//                    }

          }

          public void selectRepeatType(View view) {
          }

          public void setRepeatNo(View view) {
          }

          public void onSwitchRepeat(View view) {
          }

          public void setTime(View view) {
          }

          public void setDate(View view) {
          }

          public void selectFab1(View view) {
          }
}
