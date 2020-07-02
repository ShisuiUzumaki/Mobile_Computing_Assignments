package pk.edu.pucit.eventreminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreenActivity extends AppCompatActivity {

          private static final String TAG = SplashScreenActivity.class.getSimpleName ();

          Animation fromLeft, fromRight;
          @Override
          protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate (savedInstanceState);
                    setContentView (R.layout.activity_splash_screen);
                    //perform animations
                    TextView tv = findViewById (R.id.SplashScreenText);
                    CircleImageView civ = findViewById (R.id.EventHandlerIcon);
                    fromLeft = AnimationUtils.loadAnimation (this,R.anim.frombottom);
                    fromRight = AnimationUtils.loadAnimation (this,R.anim.fromtop);
                    tv.setAnimation (fromLeft);
                    civ.setAnimation (fromRight);

                    // Hide action bar
                    Objects.requireNonNull (getSupportActionBar ()).hide();

                    try{
                              //start and finish thread
                              new Handler ().postDelayed (new Runnable () {
                                        @Override
                                        public void run() {
                                                  Intent i = new Intent(SplashScreenActivity.this,
                                                            MainActivity.class);
                                                  SplashScreenActivity.this.startActivity(i);
                                                  finish ();
                                        }
                              }, 2500);
                    }
                    catch(Exception exc){
                              Log.e (TAG, Objects.requireNonNull (exc.getMessage ()));
                    }
          }

          @Override
          public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
                    super.onSaveInstanceState (outState, outPersistentState);
          }
}
