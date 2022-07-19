package mohammed.susu.com.saraha;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView bottle;
    private Random rNum = new Random();
    public int x;
    private int last_Direction ;
    private boolean spinning;
    private long backPressed;
    private Toast backToast;
    private Button skipButton;
   private MediaPlayer media ;

    private String Questions[] = {
            "what is your name ?" ,
            "what is your nickName ?" ,
            "what is your lastName ?" ,
            "what is your father's name ?" ,
            "what is your wife's name ?" ,
            "what is your car's name ?" ,
            "what is your job ?" ,
            "what is your id ?" ,"what is your Goal ?" ,"what is your hope ?" ,
    };

    TextView questionView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottle = findViewById(R.id.bottle);
        questionView = findViewById(R.id.question_View);
        skipButton = findViewById(R.id.skip_Button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x = rNum.nextInt(10);
                questionView.setText(Questions[x]);



            }
        });


        CountDownTimer timer = new CountDownTimer(60*1000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                Toast.makeText(MainActivity.this, "Times up !", Toast.LENGTH_SHORT).show();
            }
        };
    }

   public void  spin_Bottle(View v)
    {
        if(!spinning) {
             x = rNum.nextInt(10);
            int new_Direction = rNum.nextInt(4320);
            float middleX = bottle.getWidth() / 2;
            float middleY = bottle.getHeight() / 2;

            Animation rotate = new RotateAnimation(last_Direction, new_Direction, middleX, middleY);
            rotate.setDuration(3000);
            rotate.setFillAfter(true);
            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    spinning = true;
                    questionView.setText("?????  ???????? ???????  ???????   ?????????? .");
                   play_Sound_Bottle();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    spinning = false;
                    questionView.setText(Questions[x]);
                    media.release();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            last_Direction = new_Direction;
            bottle.startAnimation(rotate);
        }
    }

    @Override
    public void onBackPressed() {

        if(backPressed+500 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();

        }

    else
    {
        backToast = Toast.makeText(this , "press Back again to Exit" , Toast.LENGTH_SHORT);
        backToast.show();
    }
    backPressed = System.currentTimeMillis();

}
    public  void play_Sound_Bottle()
    {
        media = MediaPlayer.create(this , R.raw.bottle_spin);
        media.start();
        media.seekTo(1000);
    }

}
