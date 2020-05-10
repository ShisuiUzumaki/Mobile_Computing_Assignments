package pk.edu.pucit.eventreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreenActivity extends AppCompatActivity {

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


                    //start and finish thread
                    new Handler ().postDelayed (new Runnable () {
                              @Override
                              public void run() {
                                        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                              }
                    }, 2500);
          }


}
