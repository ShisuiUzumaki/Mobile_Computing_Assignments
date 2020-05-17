package pk.edu.pucit.eventreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;


import java.util.Calendar;
import java.util.Objects;

public
class AddReminder extends AppCompatActivity implements
          TimePickerDialog.OnTimeSetListener,
          DatePickerDialog.OnDateSetListener{


          Toolbar myToolbar;
          private EditText mTitleText;
          private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
          private Calendar mCalendar;
          private int mYear, mMonth, mHour, mMinute, mDay;
          private long mRepeatTime;
          private Switch mRepeatSwitch;
          private String mTitle;
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

          private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
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
                    Objects.requireNonNull (getSupportActionBar ()).setDisplayHomeAsUpEnabled (true);
                    Objects.requireNonNull (getSupportActionBar ()).setTitle("Add Reminder");
                    myToolbar.setSubtitle("by Shisui Uzumaki");

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

                    // check if its a new reminder
                    if (mCurrentReminderUri == null) {

                              setTitle(getString(R.string.AddReminderActivity_NewReminder));

                              // Invalidate the options menu, so the "Delete" menu option can be hidden.
                              // (It doesn't make sense to delete a reminder that hasn't been created yet.)
                              invalidateOptionsMenu();
                    }

//                    if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
//                              myToolbar.setElevation(10f);
//                    }

          }

          @Override
          protected void onSaveInstanceState (Bundle outState) {
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
                                                  // TODO saveReminder
                                                  ///saveReminder();
                                                  finish();
                                        }
                                        return true;
                              // Respond to a click on the "Delete" menu option
                              case R.id.discard_reminder:
                                        // Pop up confirmation dialog for deletion
                                        //TODO Show delete confirmation dialog box
                                        showDeleteConfirmationDialog();
                                        return true;
                              // Respond to a click on the "Up" arrow button in the app bar
                              case android.R.id.home:
                                        // If the reminder hasn't changed, continue with navigating up to parent activity
                                        // which is the {@link MainActivity}.
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
                                                                      NavUtils.navigateUpFromSameTask(AddReminder.this);
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
                    mMonth = month;
                    mYear = year;
                    mDate = dayOfMonth + "/" + month + "/" + year;
                    mDateText.setText(mDate);
          }

          @Override
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String amOrPm = "am";
                    mHour = hourOfDay;
                    mMinute = minute;
                    if(hourOfDay > 12){
                              hourOfDay = hourOfDay%12;
                              amOrPm = "pm";
                    }
                    if (minute < 10) {
                              mTime = hourOfDay + ":" + "0" + minute+amOrPm;
                    } else {
                              mTime = hourOfDay + ":" + minute+amOrPm;
                    }
                    if(hourOfDay < 10){
                              mTime = "0"+mTime;
                    }
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
                              int rowsDeleted = getContentResolver().delete(mCurrentReminderUri, null, null);

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

          // On pressing the back button
          @Override
          public void onBackPressed() {
                    super.onBackPressed();

          }

}
