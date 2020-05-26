package pk.edu.pucit.eventreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pk.edu.pucit.eventreminder.data.ERContract;

public class MainActivity extends AppCompatActivity implements
          LoaderManager.LoaderCallbacks<Cursor>,
          ERAdaptor.OnEventClickListener {

          RecyclerView events;
          ERAdaptor mRVAdaptor;
          ConstraintLayout emptyView;
          private static final int VEHICLE_LOADER = 29374;

          @Override
          protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate (savedInstanceState);
                    setContentView (R.layout.activity_main);

                    emptyView = findViewById (R.id.empty_view);
                    emptyView.setVisibility(View.VISIBLE);
                    events = findViewById (R.id.RVForEvents);
                    events.setLayoutManager (new LinearLayoutManager (this));
                    mRVAdaptor = new ERAdaptor (this,null,this);
                    events.setAdapter (mRVAdaptor);
                    getLoaderManager().initLoader(VEHICLE_LOADER, null, this);
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

          @Override
          public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String[] projection = {
                              ERContract.EREntry.EVENT_ID,
                              ERContract.EREntry.EVENT_TITLE,
                              ERContract.EREntry.EVENT_DATE,
                              ERContract.EREntry.EVENT_TIME,
                              ERContract.EREntry.EVENT_REPEAT,
                              ERContract.EREntry.EVENT_REPEAT_NO,
                              ERContract.EREntry.EVENT_REPEAT_TYPE
                    };
                    // This loader will execute the ContentProvider's query method on a background thread
                    return new CursorLoader (this,   // Parent activity context
                              ERContract.EREntry.CONTENT_URI,         // Query the content URI for the current reminder
                              projection,             // Columns to include in the resulting Cursor
                              null,                   // No selection clause
                              null,                   // No selection arguments
                              null);                  // Default sort order

          }

          @Override
          public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    if(cursor.getCount () >0){
                              emptyView.setVisibility (View.GONE);
                    }else{
                              emptyView.setVisibility (View.VISIBLE);
                    }
                    mRVAdaptor.swapCursors(cursor);
          }

          @Override
          public void onLoaderReset(Loader<Cursor> loader) {
                    mRVAdaptor.swapCursors(null);
          }

          @Override
          public void onEventClick(int position, long id) {
                    Intent intent = new Intent(MainActivity.this, AddReminder.class);

                    Uri currentVehicleUri = ContentUris.withAppendedId(
                              ERContract.EREntry.CONTENT_URI, id);

                    // Set the URI on the data field of the intent
                    intent.setData(currentVehicleUri);

                    startActivity(intent);

          }
}
