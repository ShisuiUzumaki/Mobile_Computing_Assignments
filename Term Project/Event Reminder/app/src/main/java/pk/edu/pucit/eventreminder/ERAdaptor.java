package pk.edu.pucit.eventreminder;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;


public class ERAdaptor extends RecyclerView.Adapter<ERAdaptor.ViewHolder> {

          private Context mContext;
          private Cursor mCursor;

          public ERAdaptor(Context context, Cursor cursor){
                    mContext = context;
                    mCursor = cursor;
          }

          @NonNull
          @Override
          public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflator = LayoutInflater.from (mContext);
                    View view = inflator.inflate (R.layout.rv_item_layout,parent,false);
                    return new ViewHolder (view);
          }

          @Override
          public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

          }

          @Override
          public int getItemCount() {
                    return 0;
          }

          public static class ViewHolder extends RecyclerView.ViewHolder{

                    private ImageView itemImage;
                    private TextView itemTitle;
                    private TextView itemDateTime;
                    private TextView itemRepeatition;

                    ViewHolder(@NonNull View itemView) {
                              super(itemView);
                              itemImage = itemView.findViewById(R.id.ThumbnailImage);
                              itemTitle = itemView.findViewById(R.id.RVITitle);
                              itemDateTime = itemView.findViewById(R.id.RVIDateTime);
                              itemRepeatition = itemView.findViewById(R.id.RVIDateTime);
                    }
          }
}
