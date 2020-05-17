package pk.edu.pucit.eventreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

          RecyclerView events;
          @Override
          protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate (savedInstanceState);
                    setContentView (R.layout.activity_main);

                    ConstraintLayout emptyView = findViewById (R.id.empty_view);
                    events = findViewById (R.id.RVForEvents);
                    events.setLayoutManager (new LinearLayoutManager (this));


                    events.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
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

          public void addReminder(View view) {
                    startActivity (new Intent (this, AddReminder.class));
          }
}
