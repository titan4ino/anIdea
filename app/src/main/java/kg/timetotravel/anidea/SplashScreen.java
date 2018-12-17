package kg.timetotravel.anidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread(){
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                /*try {
                    sleep(1500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        };
        myThread.start();
    }
}
