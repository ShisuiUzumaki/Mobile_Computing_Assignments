package pk.edu.pucit.eventreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NavUtils;
import android.app.LoaderManager;;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import pk.edu.pucit.eventreminder.data.ERContract;
import pk.edu.pucit.eventreminder.eventReminder.ERScheduler;

public
class AddReminder extends AppCompatActivity implements
          TimePickerDialog.OnTimeSetListener,
          DatePickerDialog.OnDateSetListener,
          LoaderManager.LoaderCallbacks<Cursor> {


          private static final int EXISTING_VEHICLE_LOADER = 24907;
          private static final String TAG = AddReminder.class.getSimpleName ();

          Toolbar myToolbar;
          private ConstraintLayout clTime , clDate, clRepeat, clRepeatInterval, clRepeatType;
          private EditText mTitleText;
          private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
          private Calendar mCalendar;
          private int mYear, mMonth, mHour, mMinute, mDay;
          private int oldYear, oldMonth, oldHour, oldMinute, oldDay;
          String oldAmOrPm;
          private long mRepeatTime;
          private Switch mRepeatSwitch;
          private String mTitle;
          private String mOldTitl;
          private String mTime;
          private String mDate;
          private String mRepeat;
          private String mRepeatNo;
          private String mRepeatType;
          private String mActive;

          // Uri in case user editing alarm
          private Uri mCurrentReminderUri;
          private boolean mReminderHasChanged = false;

          // Values for orientation change
          private static final String KEY_TITLE = "title_key";
          private static final String KEY_TIME = "time_key";
          private static final String KEY_DATE = "date_key";
          private static final String KEY_REPEAT = "repeat_key";
          private static final String KEY_REPEAT_NO = "repeat_no_key";
          private static final String KEY_REPEAT_TYPE = "repeat_type_key";
          private static final String KEY_ACTIVE = "active_key";

          // Constant values in milliseconds
          private static final long milMinute = 60000L;
          private static final long milHour = 3600000L;
          private static final long milDay = 86400000L;
          private static final long milWeek = 604800000L;
          private static final long milMonth = 2592000000L;

          private View.OnTouchListener mTouchListener
                    = new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent)
                    {
                              mReminderHasChanged = true;
                              return false;
                    }
          };

          @Override
          protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate (savedInstanceState);
                    setContentView (R.layout.activity_add_reminder);
                    myToolbar = (Toolbar) findViewById (R.id.AddReminderToolbar);
                    setSupportActionBar (myToolbar);
                    Objects.requireNonNull (getSupportActionBar ())
                              .setDisplayHomeAsUpEnabled (true);
                    Objects.requireNonNull (getSupportActionBar()).setHomeButtonEnabled(true);
                    Objects.requireNonNull (getSupportActionBar ()).setTitle("Add Reminder");
                    myToolbar.setSubtitle("by Shisui Uzumaki");

                    // Constraint Layouts initialized
                    clTime = findViewById (R.id.Time);
                    clDate = findViewById (R.id.Date);
                    clRepeat = findViewById (R.id.Repeat);
                    clRepeatInterval = findViewById (R.id.RepeatInterval);
                    clRepeatType = findViewById (R.id.RepeatType);

                    // set on touch listeners on constraint layouts
                    this.setOnTouchListeners ();

                    // set view by getting ids
                    mTitleText = findViewById(R.id.ReminderTitle);
                    mDateText = findViewById(R.id.set_date);
                    mTimeText = findViewById(R.id.set_time);
                    mRepeatText = findViewById(R.id.set_repeat);
                    mRepeatNoText = findViewById(R.id.set_repeat_no);
                    mRepeatTypeText = findViewById(R.id.set_repeat_type);
                    mRepeatSwitch = findViewById(R.id.repeat_switch);
                    mRepeatSwitch.setChecked (false);
                    mRepeatText.setText ("off");

                    // Initialize default values
                    mActive = "true";
                    mRepeat = "true";
                    mRepeatNo = Integer.toString(1);
                    mRepeatType = "Hour";

                    mCalendar = Calendar.getInstance();
                    mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                    mMinute = mCalendar.get(Calendar.MINUTE);
                    mYear = mCalendar.get(Calendar.YEAR);
                    mMonth = mCalendar.get(Calendar.MONTH) + 1;
                    mDay = mCalendar.get(Calendar.DATE);

                    if(mMonth < 10){
                              mDate = mDay + "/0" + mMonth + "/" + mYear;
                    }else{
                              mDate = mDay + "/" + mMonth + "/" + mYear;
                    }
                    mTime = generateTimeInAmOrPm(mHour ,mMinute);

                    // Setup Reminder Title EditText
                    mTitleText.addTextChangedListener(new TextWatcher () {

                              @Override
                              public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                              @Override
                              public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        mTitle = s.toString().trim();
                                        mTitleText.setError(null);
                              }

                              @Override
                              public void afterTextChanged(Editable s) {}
                    });

                    // Setup TextViews using reminder values
                    mDateText.setText(mDate);
                    mTimeText.setText(mTime);
                    mRepeatNoText.setText(mRepeatNo);
                    mRepeatTypeText.setText(mRepeatType);
                    mRepeatSwitch.setChecked (true);
                    mRepeatText.setText(String.format ("Every %s %s(s)", mRepeatNo, mRepeatType));
                    Intent intent = getIntent();
                    mCurrentReminderUri = intent.getData();
                    // check if its a new reminder
                    if (mCurrentReminderUri == null) {
                              mOldTitl = "";
                              myToolbar.setTitle (getString(R.string.AddReminderActivity_NewReminder));

                              // Invalidate the options menu, so the "Delete" menu option can be hidden.
                              // (It doesn't make sense to delete a reminder that hasn't been created yet.)
                              invalidateOptionsMenu();
                    }
                    else {
                              myToolbar.setTitle (getString(R.string.EditorActivityTitleEditReminder));
                              getLoaderManager().initLoader(EXISTING_VEHICLE_LOADER, null, this);
                    }

//                    if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
//                              myToolbar.setElevation(10f);
//                    }

                    // To save state on device rotation
                    if (savedInstanceState != null) {
                              String savedTitle = savedInstanceState.getString(KEY_TITLE);
                              mTitleText.setText(savedTitle);
                              mTitle = savedTitle;

                              String savedTime = savedInstanceState.getString(KEY_TIME);
                              mTimeText.setText(savedTime);
                              mTime = savedTime;

                              String savedDate = savedInstanceState.getString(KEY_DATE);
                              mDateText.setText(savedDate);
                              mDate = savedDate;

                              String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
                              mRepeatText.setText(saveRepeat);
                              mRepeat = saveRepeat;

                              String savedRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
                              mRepeatNoText.setText(savedRepeatNo);
                              mRepeatNo = savedRepeatNo;

                              String savedRepeatType = savedInstanceState.getString(KEY_REPEAT_TYPE);
                              mRepeatTypeText.setText(savedRepeatType);
                              mRepeatType = savedRepeatType;

                              mActive = savedInstanceState.getString(KEY_ACTIVE);
                    }

          }

          private void setOnTouchListeners(){
                    clTime.setOnTouchListener (mTouchListener);
                    clDate.setOnTouchListener (mTouchListener);
                    clRepeatType.setOnTouchListener (mTouchListener);
                    clRepeatInterval.setOnTouchListener (mTouchListener);

          }

          @Override
          protected void onSaveInstanceState (@NonNull Bundle outState) {
                    super.onSaveInstanceState(outState);

                    outState.putCharSequence(KEY_TITLE, mTitleText.getText());
                    outState.putCharSequence(KEY_TIME, mTimeText.getText());
                    outState.putCharSequence(KEY_DATE, mDateText.getText());
                    outState.putCharSequence(KEY_REPEAT, mRepeatText.getText());
                    outState.putCharSequence(KEY_REPEAT_NO, mRepeatNoText.getText());
                    outState.putCharSequence(KEY_REPEAT_TYPE, mRepeatTypeText.getText());
                    outState.putCharSequence(KEY_ACTIVE, mActive);
          }

          @Override
          public boolean onCreateOptionsMenu(Menu menu) {
                    // Inflate the menu options from the res/menu/menu_editor.xml file.
                    // This adds menu items to the app bar.
                    getMenuInflater().inflate(R.menu.add_reminder_menu, menu);
                    return true;
          }

          /**
           * This method is called after invalidateOptionsMenu(), so that the
           * menu can be updated (some menu items can be hidden or made visible).
           */
          @Override
          public boolean onPrepareOptionsMenu(Menu menu) {
                    super.onPrepareOptionsMenu(menu);
                    // If this is a new reminder, hide the "Delete" menu item.
                    if (mCurrentReminderUri == null) {
                              MenuItem menuItem = menu.findItem(R.id.discard_reminder);
                              menuItem.setVisible(false);
                    }
                    return true;
          }

          @Override
          public boolean onOptionsItemSelected(MenuItem item) {

                    // User clicked on a menu option in the app bar overflow menu
                    switch (item.getItemId()) {
                              // Respond to a click on the "Save" menu option
                              case R.id.save_reminder:
                                        if (mTitleText.getText().toString().length() == 0){
                                                  mTitleText.setError("Reminder Title cannot be blank!");
                                        }
                                        else {
                                                  saveEventReminder();
                                                  finish();
                                        }
                                        return true;
                              // Respond to a click on the "Delete" menu option
                              case R.id.discard_reminder:
                                        // Pop up confirmation dialog for deletion
                                        showDeleteConfirmationDialog();
                                        return true;
                              // Respond to a click on the "Up" arrow button in the app bar
                              case android.R.id.home:
                                        // If the reminder hasn't changed, continue with navigating up to parent activity
                                        // which is the {@link MainActivity}.
                                        if(!mTitleText.getText ().toString ().equals ("") && this.mOldTitl.equals("")){
                                                  mReminderHasChanged = true;
                                        }
                                        else if(!mTitleText.getText ().toString ().equals (mOldTitl)){
                                                  mReminderHasChanged = true;
                                        }
                                        if (!mReminderHasChanged) {
                                                  NavUtils.navigateUpFromSameTask(AddReminder.this);
                                                  return true;
                                        }

                                        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                                        // Create a click listener to handle the user confirming that
                                        // changes should be discarded.
                                        DialogInterface.OnClickListener discardButtonClickListener =
                                                  new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                      // User clicked "Discard" button, navigate to parent activity.
                                                                      NavUtils.navigateUpFromSameTask (AddReminder.this);
                                                            }
                                                  };

                                        // Show a dialog that notifies the user they have unsaved changes
                                        // TODO show unsaved changes
                                        showUnsavedChangesDialog(discardButtonClickListener);
                                        return true;
                    }

                    return super.onOptionsItemSelected(item);
          }

          // On clicking the repeat switch
          public void onSwitchRepeat(View view) {
                    boolean on = ((Switch) view).isChecked();
                    mReminderHasChanged = true;
                    if (on) {
                              mRepeat = "true";
                              if(mRepeatNo == null){
                                        mRepeatNo = "1";
                              }
                              if(mRepeatType == null){
                                        mRepeatType = "Minute";
                              }
                              mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                    } else {
                              mRepeat = "false";
                              mRepeatText.setText(R.string.RepeatOff);
                    }
          }

          // On clicking repeat type button
          public void selectRepeatType(View v){
                    final String[] items = new String[5];

                    items[0] = "Minute";
                    items[1] = "Hour";
                    items[2] = "Day";
                    items[3] = "Week";
                    items[4] = "Month";

                    // Create List Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Select Type");
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                              public void onClick(DialogInterface dialog, int item) {

                                        mRepeatType = items[item];
                                        mRepeatTypeText.setText(mRepeatType);
                                        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                              }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
          }

          // On clicking repeat interval button
          public void setRepeatNo(View v){
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Enter Number");

                    // Create EditText box to input repeat number
                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setView(input);
                    alert.setPositiveButton("Ok",
                              new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                                  if (input.getText().toString().length() == 0) {
                                                            mRepeatNo = Integer.toString(1);
                                                            mRepeatNoText.setText(mRepeatNo);
                                                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                                                  }
                                                  else {
                                                            mRepeatNo = input.getText().toString().trim();
                                                            mRepeatNoText.setText(mRepeatNo);
                                                            mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                                                  }
                                        }
                              });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int whichButton) {
                                        // do nothing
                              }
                    });
                    alert.show();
          }

          // On clicking Time picker
          public void setTime(View v){
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = new TimePickerDialog (this, this,
                              now.get(Calendar.HOUR_OF_DAY),
                              now.get (Calendar.MINUTE),
                              android.text.format.DateFormat.is24HourFormat (this));
                    tpd.show();
          }

          // On clicking Date picker
          public void setDate(View v){
                    Calendar now = Calendar.getInstance();

                    DatePickerDialog dpd = new DatePickerDialog (this, this,
                              now.get (Calendar.YEAR),
                              now.get (Calendar.MONTH),
                              now.get (Calendar.DAY_OF_MONTH));
                    dpd.show();
          }

          @Override
          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mDay = dayOfMonth;
                    oldDay = dayOfMonth;
                    mMonth = month+1;
                    oldMinute = month+1;
                    mYear = year;
                    oldYear = year;
                    month = month +1;
                    if(month < 10){
                              mDate = dayOfMonth + "/0" + month + "/" + year;
                    }else{
                              mDate = dayOfMonth + "/" + month + "/" + year;
                    }
                    if(mDay < 10){
                              mDate = "0"+mDate;
                    }
                    mDateText.setText(mDate);
          }

          @Override
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mTime = generateTimeInAmOrPm (hourOfDay,minute);
                    mHour = hourOfDay;
                    oldHour = hourOfDay;
                    mMinute = minute;
                    oldMinute = minute;
                    mTimeText.setText(mTime);
          }

          private void showUnsavedChangesDialog(
                    DialogInterface.OnClickListener discardButtonClickListener) {
                    // Create an AlertDialog.Builder and set the message, and click listeners
                    // for the postivie and negative buttons on the dialog.
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.UnsavedChangesDialogMsg);
                    builder.setPositiveButton(R.string.DiscardChanges, discardButtonClickListener);
                    builder.setNegativeButton(R.string.KeepEditing, new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                        // User clicked the "Keep editing" button, so dismiss the dialog
                                        // and continue editing the reminder.
                                        if (dialog != null) {
                                                  dialog.dismiss();
                                        }
                              }
                    });

                    // Create and show the AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
          }

          private void showDeleteConfirmationDialog() {
                    // Create an AlertDialog.Builder and set the message, and click listeners
                    // for the postivie and negative buttons on the dialog.
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.DeleteDialogMsg);
                    builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                        // User clicked the "Delete" button, so delete the reminder.
                                        deleteReminder();
                              }
                    });
                    builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                                        // User clicked the "Cancel" button, so dismiss the dialog
                                        // and continue editing the reminder.
                                        if (dialog != null) {
                                                  dialog.dismiss();
                                        }
                              }
                    });

                    // Create and show the AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
          }

          private void deleteReminder() {
                    // Only perform the delete if this is an existing reminder.
                    if (mCurrentReminderUri != null) {
                              // Call the ContentResolver to delete the reminder at the given content URI.
                              // Pass in null for the selection and selection args because the mCurrentreminderUri
                              // content URI already identifies the reminder that we want.
                              int rowsDeleted = getContentResolver().delete(mCurrentReminderUri,
                                        null, null);

                              // Show a toast message depending on whether or not the delete was successful.
                              if (rowsDeleted == 0) {
                                        // If no rows were deleted, then there was an error with the delete.
                                        Toast.makeText(this, getString(R.string.EditorDeleteReminderFailed),
                                                  Toast.LENGTH_LONG).show();
                              } else {
                                        // Otherwise, the delete was successful and we can display a toast.
                                        Toast.makeText(this, getString(R.string.EditorDeleteReminderSuccessful),
                                                  Toast.LENGTH_LONG).show();
                              }
                    }

                    // Close the activity
                    finish();
          }

          // save reminder
          public void saveEventReminder(){

                    ContentValues values = new ContentValues();

                    if(!mReminderHasChanged){
                         mHour = oldHour;
                         mMinute = oldMinute;
                         mYear = oldYear;
                         mDay = oldDay;
                         mMonth = oldMonth;
                         mTime = generateTimeInAmOrPm (mHour,mMinute);
                              if(mMonth < 10){
                                        mDate = mDay + "/0" + mMonth + "/" + mYear;
                              }else{
                                        mDate = mDay + "/" + mMonth + "/" + mYear;
                              }
                              if(mDay < 10){
                                        mDate = "0"+mDate;
                              }
                    }

                    values.put(ERContract.EREntry.EVENT_TITLE, mTitle);
                    values.put(ERContract.EREntry.EVENT_DATE, mDate);
                    values.put(ERContract.EREntry.EVENT_TIME, mTime);
                    values.put(ERContract.EREntry.EVENT_REPEAT, mRepeat);
                    values.put(ERContract.EREntry.EVENT_REPEAT_NO, mRepeatNo);
                    values.put(ERContract.EREntry.EVENT_REPEAT_TYPE, mRepeatType);
                    //values.put(ERContract.EREntry.EVENT_ACTIVE, mActive);

                    //Getting current time in milly
                    mCalendar.clear (Calendar.SECOND);
                    long timeNow = mCalendar.getTimeInMillis ();

                    // Set up calender for creating the notification
                    mCalendar.clear ();
                    mCalendar.set(Calendar.MONTH, --mMonth);
                    mCalendar.set(Calendar.YEAR, mYear);
                    mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                    mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                    mCalendar.set(Calendar.MINUTE, mMinute);
                    mCalendar.set(Calendar.SECOND, 0);

                    long selectedTimestamp =  mCalendar.getTimeInMillis();

                    // Check repeat type
                    switch (mRepeatType) {
                              case "Minute":
                                        mRepeatTime =
                                                  Integer.parseInt (mRepeatNo) * milMinute;
                                        break;
                              case "Hour":
                                        mRepeatTime =
                                                  Integer.parseInt (mRepeatNo) * milHour;
                                        break;
                              case "Day":
                                        mRepeatTime =
                                                  Integer.parseInt (mRepeatNo) * milDay;
                                        break;
                              case "Week":
                                        mRepeatTime =
                                                  Integer.parseInt (mRepeatNo) * milWeek;
                                        break;
                              case "Month":
                                        mRepeatTime =
                                                  Integer.parseInt (mRepeatNo) * milMonth;
                                        break;
                    }

                    if (mCurrentReminderUri == null) {
                              // This is a NEW reminder, so insert a new reminder into the provider,
                              // returning the content URI for the new reminder.
                              Uri newUri = getContentResolver().insert(ERContract.EREntry.CONTENT_URI,
                                        values);

                              // Show a toast message depending on whether or not the insertion was successful.
                              if (newUri == null) {
                                        // If the new content URI is null, then there was an error with insertion.
                                        Toast.makeText(this,
                                                  getString(R.string.EditorInsertReminderFailed),
                                                  Toast.LENGTH_LONG).show();
                              } else {
                                        // Otherwise, the insertion was successful and we can display a toast.
                                        Toast.makeText(this,
                                                  getString(R.string.EditorInsertReminderSuccessful),
                                                  Toast.LENGTH_LONG).show();
                              }
                    }
                    else {

                              int rowsAffected = getContentResolver().update(mCurrentReminderUri, values, null, null);

                              // Show a toast message depending on whether or not the update was successful.
                              if (rowsAffected == 0) {
                                        // If no rows were affected, then there was an error with the update.
                                        Toast.makeText(this,
                                                  getString(R.string.EditorUpdateReminderFailed),
                                                  Toast.LENGTH_SHORT).show();
                              } else {
                                        // Otherwise, the update was successful and we can display a toast.
                                        Toast.makeText(this,
                                                  getString(R.string.EditorUpdateReminderSuccessful),
                                                  Toast.LENGTH_SHORT).show();
                              }
                    }

                    // Create a new notification
                    if (mActive.equals("true") &&
                              (selectedTimestamp >= timeNow || timeNow-  selectedTimestamp < milMinute)) {
                              if (mRepeat.equals("true")) {
                                        new ERScheduler ().setRepeatAlarm(getApplicationContext(),
                                                  selectedTimestamp,
                                                  mCurrentReminderUri,
                                                  mRepeatTime);
                              } else if (mRepeat.equals("false")) {
                                        new ERScheduler ().setAlarm(getApplicationContext(),
                                                  selectedTimestamp,
                                                  mCurrentReminderUri);
                              }

                              Toast.makeText(this, "Alarm time is " + selectedTimestamp,
                                        Toast.LENGTH_LONG).show();
                    }

                    // Create toast to confirm new reminder
                    Toast.makeText(getApplicationContext(), "Saved",
                              Toast.LENGTH_LONG).show();

          }

          // On pressing the back button
          @Override
          public void onBackPressed() {
                    super.onBackPressed();

          }

          private String generateTimeInAmOrPm( int hourOfDay, int minute){
                    String amOrPm = "am";
                    String time;
                    if(hourOfDay > 12){
                              hourOfDay = hourOfDay%12;
                              amOrPm = "pm";
                    }
                    if (minute < 10) {
                              time= hourOfDay + ":" + "0" + minute+amOrPm;
                    } else {
                              time=  hourOfDay + ":" + minute+amOrPm;
                    }
                    if(hourOfDay < 10){
                              time = "0"+time;
                    }
                    return time;
          }

          // LoaderManager overridden functions
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
                    return new CursorLoader(this,   // Parent activity context
                              mCurrentReminderUri,         // Query the content URI for the current reminder
                              projection,             // Columns to include in the resulting Cursor
                              null,                   // No selection clause
                              null,                   // No selection arguments
                              null);                  // Default sort order
          }

          @Override
          public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                   try{
                             if (cursor == null || cursor.getCount() < 1) {
                                       return;
                             }

                             // Proceed with moving to the first row of the cursor and reading data from it
                             // (This should be the only row in the cursor)
                             if (cursor.moveToFirst()) {
                                       int titleColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_TITLE);
                                       int dateColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_DATE);
                                       int timeColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_TIME);
                                       int repeatColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT);
                                       int repeatNoColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT_NO);
                                       int repeatTypeColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT_TYPE);
                                       //int activeColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_ACTIVE);

                                       // Extract out the value from the Cursor for the given column index
                                       String title = cursor.getString(titleColumnIndex);
                                       String date = cursor.getString(dateColumnIndex);
                                       String time = cursor.getString(timeColumnIndex);
                                       String repeat = cursor.getString(repeatColumnIndex);
                                       String repeatNo = cursor.getString(repeatNoColumnIndex);
                                       String repeatType = cursor.getString(repeatTypeColumnIndex);
                                       //String active = cursor.getString(activeColumnIndex);

                                       mOldTitl = title;
                                       oldHour = Integer.parseInt (time.substring (0,2));
                                       oldMinute = Integer.parseInt (time.substring (3,5));
                                       oldAmOrPm = time.substring (5,7);
                                       oldDay = Integer.parseInt (date.substring (0,2));
                                       oldMonth = Integer.parseInt (date.substring (3,5));
                                       oldYear = Integer.parseInt (date.substring (6,10));

                                       if(oldAmOrPm.equals ("pm")){
                                                 oldHour=oldHour + 12;
                                       }


                                       mTitleText.setText(title);
                                       mDateText.setText(date);
                                       mTimeText.setText(time);
                                       mRepeatNoText.setText(repeatNo);
                                       mRepeatTypeText.setText(repeatType);
                                       mRepeatText.setText(String.format ("Every %s %s(s)", repeatNo, repeatType));
                                       // Setup up active buttons
                                       // Setup repeat switch
                                       if (repeat.equals("false")) {
                                                 mRepeatSwitch.setChecked(false);
                                                 mRepeatText.setText(R.string.RepeatOff);
                                                 mRepeat = "false";
                                       } else if (repeat.equals("true")) {
                                                 mRepeatSwitch.setChecked(true);
                                                 mRepeat = "true";
                                       }

                             }
                   }catch(Exception exc){
                             Log.e (TAG, exc.getMessage());
                   }
          }

          @Override
          public void onLoaderReset(Loader<Cursor> loader) {

          }


}
