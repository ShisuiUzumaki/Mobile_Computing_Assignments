package pk.edu.pucit.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity<flag> extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final ArrayList<BookInfo> books = new ArrayList<>();
    private final  int PERMISSION_REQUEST_CODE = 20013;
    boolean permissionsGranted;
//    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStorageAccessPermissions ();
        if (getDataFromFile ())
        {
            initRecyclerView();
        }

    }

    private void getStorageAccessPermissions() {
        boolean flag = false;
        int results = PackageManager.PERMISSION_DENIED;
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.M) {
            results = ContextCompat.checkSelfPermission (this,
                      Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        else {
            results = checkSelfPermission (Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (results == PackageManager.PERMISSION_GRANTED) {
            flag = true;
            permissionsGranted = true;
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions (new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                                    PERMISSION_REQUEST_CODE);
                flag = permissionsGranted;
            } else {
                ActivityCompat.requestPermissions (this, new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                          PERMISSION_REQUEST_CODE);
                flag = permissionsGranted;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                for (int i = 0; i < grantResults.length; i++) {
                    Toast.makeText (this, permissions[i] +
                                        " has been " + (grantResults[i] == PackageManager.PERMISSION_DENIED ? "denied" : "granted"),
                              Toast.LENGTH_LONG).show ();
                    permissionsGranted = grantResults[i] != PackageManager.PERMISSION_DENIED;
                }

            default:
                super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        }
    }

    private boolean getDataFromFile() {
        boolean flag;
        InputStream is = getResources().openRawResource(R.raw.issues);
        try {
            byte[] data = new byte[is.available ()];
            while (true){
                if (is.read (data) == -1) break;
            }
            JSONObject jsonObject = new JSONObject (new String (data));
            JSONArray booksArray = jsonObject.getJSONArray ("books");
            for (int i = 0; i < booksArray.length (); i++) {
                BookInfo book = new BookInfo ();
                JSONObject bookObject = booksArray.getJSONObject (i);
                book.setBookTitle (bookObject.getString ("title"));
                book.setBookLevel (bookObject.getString ("level"));
                book.setBookInfo (bookObject.getString ("info"));
                book.setBookUrl (bookObject.getString ("url"));
                book.setBookCover (bookObject.getString ("cover"));
                books.add (book);
            }
            flag= true;
        }
        catch (Exception exc){
            exc.printStackTrace ();
            flag = false;
        }
        finally {
            try{
                is.close ();
            }
            catch (Exception exc){
                exc.printStackTrace ();
                flag = false;
            }
        }
        return  flag;
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview. called ");
        RecyclerView recyclerView = findViewById(R.id.MyBooksRV);
        MyRVAdaptor adapter = new MyRVAdaptor(this, books, permissionsGranted);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


//    @Override
//    protected Dialog onCreateDialog(int id) {
//            pDialog = new ProgressDialog(this);
//            pDialog.setMessage("Downloading file. Please wait...");
//            pDialog.setIndeterminate(false);
//            pDialog.setMax(100);
//            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            pDialog.setCancelable(true);
//            pDialog.show();
//            return pDialog;
//        }
}
