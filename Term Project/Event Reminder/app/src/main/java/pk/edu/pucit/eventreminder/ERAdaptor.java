package pk.edu.pucit.eventreminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.zip.Inflater;

import pk.edu.pucit.eventreminder.data.ERContract;


public class ERAdaptor extends CursorAdapter {

          private Context mContext;
          private Cursor mCursor;

          private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
          private ImageView mActiveImage , mThumbnailImage;
          private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
          private TextDrawable mDrawableBuilder;

          public ERAdaptor(Context context, Cursor c) {
                    super(context, c, 0 /* flags */);
          }


          @Override
          public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    LayoutInflater inflator = LayoutInflater.from (context);
                    return inflator.inflate (R.layout.rv_item_layout, parent, false);
          }

          @Override
          public void bindView(View view, Context context, Cursor cursor) {
                    mTitleText = view.findViewById(R.id.RVITitle);
                    mDateAndTimeText =  view.findViewById(R.id.RVIDateTime);
                    mRepeatInfoText = view.findViewById(R.id.RVIRepeatInfo);
                    mActiveImage = view.findViewById(R.id.ActiveImage);
                    mThumbnailImage =  view.findViewById(R.id.ThumbnailImage);

                    int titleColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_TITLE);
                    int dateColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_DATE);
                    int timeColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_TIME);
                    int repeatColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT);
                    int repeatNoColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT_NO);
                    int repeatTypeColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT_TYPE);
                    int activeColumnIndex = cursor.getColumnIndex(ERContract.EREntry.EVENT_ACTIVE);

                    String eventTitle = cursor.getString(titleColumnIndex);
                    String eventDate = cursor.getString(dateColumnIndex);
                    String eventTime = cursor.getString(timeColumnIndex);
                    String eventRepeat = cursor.getString(repeatColumnIndex);
                    String eventRepeatNo = cursor.getString(repeatNoColumnIndex);
                    String eventRepeatType = cursor.getString(repeatTypeColumnIndex);
                    String eventIsActive = cursor.getString(activeColumnIndex);

                    String dateTime = eventDate + " " + eventTime;

                    setERDateTime (eventTitle);
                    setERDateTime (dateTime);
                    setERRepeatInfo (eventRepeat,eventRepeatNo,eventRepeatType);
                    setERActiveImage (eventIsActive);

          }

          // Set reminder title view
          public void setERTitle(String title) {
                    mTitleText.setText(title);
                    String letter = "A";

                    if(title != null && !title.isEmpty()) {
                              letter = title.substring(0, 1);
                    }

                    int color = mColorGenerator.getRandomColor();

                    // Create a circular icon consisting of  a random background colour and first letter of title
                    mDrawableBuilder = TextDrawable.builder()
                              .buildRound(letter, color);
                    mThumbnailImage.setImageDrawable(mDrawableBuilder);
          }

          // Set date and time views
          public void setERDateTime(String datetime) {
                    mDateAndTimeText.setText(datetime);
          }

          // Set repeat views
          public void setERRepeatInfo(String repeat, String repeatNo, String repeatType) {
                    if(repeat.equals("true")){
                              mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
                    }else if (repeat.equals("false")) {
                              mRepeatInfoText.setText("Repeat Off");
                    }
          }

          // Set active image as on or off
          public void setERActiveImage(String active){
                    if(active.equals("true")){
                              mActiveImage.setImageResource(R.drawable.ic_notifications);
                    }else if (active.equals("false")) {
                              mActiveImage.setImageResource(R.drawable.ic_notifications_off_black);
                    }
          }
}
