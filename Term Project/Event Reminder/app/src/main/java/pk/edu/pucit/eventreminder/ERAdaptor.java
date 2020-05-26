package pk.edu.pucit.eventreminder;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Inflater;

import pk.edu.pucit.eventreminder.data.ERContract;


public class ERAdaptor extends RecyclerView.Adapter<ERAdaptor.ViewHolder> {

          private static ArrayList<Long> eventIds = new ArrayList<> ();
          private OnEventClickListener onEventClickListener;
          private Context mContext;
          private Cursor mCursor;


          ERAdaptor(Context context, Cursor cursor, @NonNull  OnEventClickListener onEventClickListener) {
                    mContext = context;
                    mCursor = cursor;
                    this.onEventClickListener = onEventClickListener;
          }


          @NonNull
          @Override
          public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflator = LayoutInflater.from (mContext);
                    View view = inflator.inflate (R.layout.rv_item_layout, parent, false);
                    return new ViewHolder (view, this.onEventClickListener);
          }

          @Override
          public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                    try{
                              if(!mCursor.moveToPosition (position)){
                                        return;
                              }
                              long id = mCursor.getLong (mCursor.getColumnIndex (ERContract.EREntry.EVENT_ID));
                              eventIds.add (position,id);
                              int titleColumnIndex = mCursor.getColumnIndex(ERContract.EREntry.EVENT_TITLE);
                              int dateColumnIndex = mCursor.getColumnIndex(ERContract.EREntry.EVENT_DATE);
                              int timeColumnIndex = mCursor.getColumnIndex(ERContract.EREntry.EVENT_TIME);
                              int repeatColumnIndex = mCursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT);
                              int repeatNoColumnIndex = mCursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT_NO);
                              int repeatTypeColumnIndex = mCursor.getColumnIndex(ERContract.EREntry.EVENT_REPEAT_TYPE);
                              //int activeColumnIndex = mCursor.getColumnIndex(ERContract.EREntry.EVENT_ACTIVE);

                              String eventTitle = mCursor.getString(titleColumnIndex);
                              String eventDate = mCursor.getString(dateColumnIndex);
                              String eventTime = mCursor.getString(timeColumnIndex);
                              String eventRepeat = mCursor.getString(repeatColumnIndex);
                              String eventRepeatNo = mCursor.getString(repeatNoColumnIndex);
                              String eventRepeatType = mCursor.getString(repeatTypeColumnIndex);
                              //String eventIsActive = mCursor.getString(activeColumnIndex);

                              String dateTime = eventDate + " " + eventTime;

                              holder.setERTitle (eventTitle);
                              holder.setERDateTime (dateTime);
                              holder.setERRepeatInfo (eventRepeat,eventRepeatNo,eventRepeatType);
                              //holder.setERActiveImage (eventIsActive);

                    }catch(Exception ex){
                              Log.e ("ERAdaptor", Objects.requireNonNull (ex.getMessage ()));
                    }

          }

          @Override
          public int getItemCount() {
                    return (mCursor==null)? 0:mCursor.getCount ();
          }

          void swapCursors(Cursor cursor) {
                    if(mCursor != null){
                              mCursor.close ();
                    }
                    mCursor = cursor;
                    if(cursor != null){
                              notifyDataSetChanged ();
                    }
          }

          public static class ViewHolder
                    extends RecyclerView.ViewHolder
                    implements View.OnClickListener {

                    TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
                    ImageView mActiveImage , mThumbnailImage;
                    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
                    private OnEventClickListener onEventClickListener;


                    ViewHolder(@NonNull View view, OnEventClickListener onEventClickListener) {
                              super (view);
                              view.setOnClickListener (this);
                              mTitleText = view.findViewById(R.id.RVITitle);
                              mDateAndTimeText =  view.findViewById(R.id.RVIDateTime);
                              mRepeatInfoText = view.findViewById(R.id.RVIRepeatInfo);
                              mActiveImage = view.findViewById(R.id.ActiveImage);
                              mThumbnailImage =  view.findViewById(R.id.ThumbnailImage);
                              this.onEventClickListener = onEventClickListener;

                    }

                    // Set reminder title view
                    void setERTitle(String title) {
                              mTitleText.setText(title);
                              String letter = "A";

                              if(title != null && !title.isEmpty()) {
                                        letter = title.substring(0, 1);
                              }

                              int color = mColorGenerator.getRandomColor();

                              // Create a circular icon consisting of  a random background colour and first letter of title
                              TextDrawable
                                        mDrawableBuilder =
                                        TextDrawable.builder ()
                                                  .buildRound (letter, color);
                              mThumbnailImage.setImageDrawable(mDrawableBuilder);
                    }

                    // Set date and time views
                    void setERDateTime(String datetime) {
                              mDateAndTimeText.setText(datetime);
                    }

                    // Set repeat views
                    void setERRepeatInfo(String repeat, String repeatNo, String repeatType) {
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

                    @Override
                    public void onClick(View v) {
                              int position = getAdapterPosition ();
                              this.onEventClickListener.onEventClick (position, eventIds.get(position));
                    }
          }

          public interface OnEventClickListener{
                    void onEventClick(int position, long id);
          }

}
