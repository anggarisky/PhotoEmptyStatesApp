package com.example.anggarisky.photoemptystatesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView btnaddphoto, titlepage, subtitlepage;
    Button btnpagephoto, btnpagechat;
    View dotmenu;
    ImageView icstates;
    Animation btt, bttwo, imgbounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        bttwo = AnimationUtils.loadAnimation(this, R.anim.bttwo);
        imgbounce = AnimationUtils.loadAnimation(this, R.anim.imgbounce);

        btnaddphoto = findViewById(R.id.btnaddphoto);
        btnaddphoto.setPaintFlags(btnaddphoto.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // give an event to next act
        btnaddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,PhotoCateAct.class);
                startActivity(a);
            }
        });

        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);

        btnpagephoto = findViewById(R.id.btnpagephoto);
        btnpagechat = findViewById(R.id.btnpagechat);

        dotmenu = findViewById(R.id.dotmenu);

        icstates = findViewById(R.id.icstates);

        btnpagechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icstates.setImageResource(R.drawable.icchat);
                titlepage.setText("No One Chat");
                subtitlepage.setText("Build a circle that impact society");

                btnaddphoto.setText("Find a Friend");
                btnaddphoto.setTextColor(Color.parseColor("#DF5F7D"));

                // pass animation
                icstates.startAnimation(imgbounce);
                titlepage.startAnimation(btt);
                subtitlepage.startAnimation(btt);
                btnaddphoto.startAnimation(bttwo);

                // another animation + img resource
                btnpagephoto.setBackgroundResource(R.drawable.iccamun);
                btnpagechat.setBackgroundResource(R.drawable.icmsgan);

                btnpagephoto.animate().scaleY(0.7f).scaleX(0.7f).setDuration(350).start();
                btnpagechat.animate().scaleY(1).scaleX(1).setDuration(350).start();
                dotmenu.animate().translationX(430).setDuration(350).setStartDelay(100).start();


            }
        });

        btnpagephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icstates.setImageResource(R.drawable.icphotos);
                titlepage.setText("No Photos");
                subtitlepage.setText("Add a picture for your circle");

                btnaddphoto.setText("Add Photo");
                btnaddphoto.setTextColor(Color.parseColor("#706AC9"));

                // pass animation
                icstates.startAnimation(imgbounce);
                titlepage.startAnimation(btt);
                subtitlepage.startAnimation(btt);
                btnaddphoto.startAnimation(bttwo);

                // another animation + img resource
                btnpagephoto.setBackgroundResource(R.drawable.iccaman);
                btnpagechat.setBackgroundResource(R.drawable.icmsgun);

                btnpagechat.animate().scaleY(0.7f).scaleX(0.7f).setDuration(350).start();
                btnpagephoto.animate().scaleY(1).scaleX(1).setDuration(350).start();
                dotmenu.animate().translationX(0).setDuration(350).setStartDelay(100).start();


            }
        });

    }


}
