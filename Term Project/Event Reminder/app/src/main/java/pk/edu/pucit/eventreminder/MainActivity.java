package pk.edu.pucit.eventreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

          @Override
          protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate (savedInstanceState);
                    setContentView (R.layout.activity_main);
          }

          @Override
          public boolean onCreateOptionsMenu(Menu menu) {
                    getMenuInflater ().inflate (R.menu.menu_main,menu);
                    return super.onCreateOptionsMenu (menu);
          }

          @Override
          public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                    switch(item.getItemId ()){
                              case R.id.Add:
                                        startActivity (new Intent (this, AddReminder.class));
                                        break;
                              case R.id.Settings:
                                        break;
                              case R.id.Help:
                                        break;
                              default:
                                        // TODO  mention sth here
                    }
                    return true;
          }
}
