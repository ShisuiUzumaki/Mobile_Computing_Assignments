package pk.edu.pucit.eventreminder;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import static pk.edu.pucit.eventreminder.data.ERContract.EREntry;

public class MainActivity extends AppCompatActivity implements
          LoaderManager.LoaderCallbacks<Cursor>,
          ERAdaptor.OnEventClickListener {

          private static final String TAG = MainActivity.class.getSimpleName ();

          RecyclerView events;
          ERAdaptor mRVAdaptor;
          ConstraintLayout emptyView;
          private static final int LOADER_ID = 29374;

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
                    getLoaderManager().initLoader(LOADER_ID, null, this);
          }

          @Override
          public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
                    super.onSaveInstanceState (outState, outPersistentState);
                    Log.i (TAG, "On save instance state change");
          }

          @Override
          public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    super.onConfigurationChanged (newConfig);
          }

          @Override
          public boolean onCreateOptionsMenu(Menu menu) {
                    getMenuInflater ().inflate (R.menu.menu_main,menu);
                    return super.onCreateOptionsMenu (menu);
          }

          @Override
          public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                    try {
                              switch(item.getItemId ()){
                                        case R.id.Add:
                                                  startActivity (new Intent (this, AddReminder.class));
                                                  break;
                                        case R.id.Settings:
                                                  Intent settings = new Intent (this, ERSettings.class);
                                                  startActivity (settings);
                                                  break;
                                        case R.id.Help:
                                                  break;
                                        default:
                                                  // TODO  mention sth here
                                                  throw new IllegalArgumentException ("Item not found");
                              }
                    }catch (Exception exc){
                              Log.e(TAG, Objects.requireNonNull (exc.getMessage ()));
                    }
                    return true;
          }

          public void addReminder(View view) {
                    startActivity (new Intent (this, AddReminder.class));
          }

          @Override
          public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String[] projection = {
                              EREntry.EVENT_ID,
                              EREntry.EVENT_TITLE,
                              EREntry.EVENT_DATE,
                              EREntry.EVENT_TIME,
                              EREntry.EVENT_REPEAT,
                              EREntry.EVENT_REPEAT_NO,
                              EREntry.EVENT_REPEAT_TYPE
                    };
                    // This loader will execute the ContentProvider's query method on a background thread
                    return new CursorLoader (this,   // Parent activity context
                              EREntry.CONTENT_URI,         // Query the content URI for the current reminder
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

                    //Generate URI to be passed to Edit Reminder
                    Uri currentItemID = ContentUris.withAppendedId(EREntry.CONTENT_URI, id);

                    // Set the URI on the data field of the intent
                    intent.setData(currentItemID);

                    startActivity(intent);

          }

          @Override
          protected void onPause() {
                    super.onPause ();
                    Log.i (TAG, "On pause called");
          }

          @Override
          protected void onStop() {
                    super.onStop ();
                    Log.i (TAG, "On stop called");
          }

          @Override
          protected void onDestroy() {
                    super.onDestroy ();
                    Log.i (TAG, "On destroy called");
          }
}
