package com.example.anggarisky.photoemptystatesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PhotoCateAct extends AppCompatActivity {

    RecyclerView myCateList;
    CatesAdapter catesAdapter;
    List<Cates> catesList;
    Button btnsavecate;
    Animation btt;

    // add username local
    String USER_NAME_STORY = "usernamestory";
    String userNameStoryLocal = "";
    String userNameStoryNew = "";

    // setting for database
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_cate);

        loadDataUsernameLocal();

        btnsavecate = findViewById(R.id.btnsavecate);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        catesList = new ArrayList<>();
        catesList.add(
                new Cates(
                      "Juicy",
                      R.drawable.icjuicy
                )
        );
        catesList.add(
                new Cates(
                        "Snorkling",
                        R.drawable.icsnork
                )
        );
        catesList.add(
                new Cates(
                        "Shopping",
                        R.drawable.icshopper
                )
        );
        catesList.add(
                new Cates(
                        "Study",
                        R.drawable.icstudy
                )
        );
        catesList.add(
                new Cates(
                        "Travel",
                        R.drawable.ictravel
                )
        );
        catesList.add(
                new Cates(
                        "DoorPrize",
                        R.drawable.icprize
                )
        );

        myCateList = findViewById(R.id.myCateList);

        final LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);

        myCateList.setLayoutManager(linearLayoutManager);
        myCateList.setHasFixedSize(true);

        catesAdapter = new CatesAdapter(this, catesList);
        myCateList.setAdapter(catesAdapter);

        //snapping from Google
        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(myCateList);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 1ms = 100ms
                RecyclerView.ViewHolder viewHolderDefault = myCateList.
                        findViewHolderForAdapterPosition(0);

                ImageView imageViewDefault = viewHolderDefault.itemView.findViewById(R.id.iconimg);
                imageViewDefault.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100).start();

                TextView icontitleDefault = viewHolderDefault.itemView.findViewById(R.id.icontitle);
                icontitleDefault.animate().alpha(1).setDuration(100).start();

                btnsavecate.animate().alpha(1).setDuration(100).start();

            }
        }, 100);

        reference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userNameStoryNew);

        myCateList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE){

                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder = myCateList.findViewHolderForAdapterPosition(pos);

                    ImageView imageView = viewHolder.itemView.findViewById(R.id.iconimg);
                    imageView.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100).start();

                    final TextView icontitle = viewHolder.itemView.findViewById(R.id.icontitle);
                    icontitle.animate().alpha(1).setDuration(100).start();

                    btnsavecate.setAlpha(1);
                    btnsavecate.startAnimation(btt);

                    btnsavecate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // save category to database firebase
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // get string from current category
                                    String storycate = icontitle.getText().toString();

                                    // save to Firebase
                                    reference.getRef().child("category").setValue(storycate);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Intent a = new Intent(PhotoCateAct.this,ChooseUserNameAct.class);
                            startActivity(a);
                        }
                    });

                } else {

                    View view = snapHelper.findSnapView(linearLayoutManager);
                    int pos = linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder = myCateList.findViewHolderForAdapterPosition(pos);
                    ImageView imageView = viewHolder.itemView.findViewById(R.id.iconimg);
                    imageView.animate().alpha(0.5f).scaleX(0.5f).scaleY(0.5f).setDuration(100).start();

                    TextView icontitle = viewHolder.itemView.findViewById(R.id.icontitle);
                    icontitle.animate().alpha(0).setDuration(100).start();
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                btnsavecate.setAlpha(0);
            }
        });

    }

    public void loadDataUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAME_STORY, MODE_PRIVATE);
        userNameStoryNew = sharedPreferences.getString(userNameStoryLocal, "");
    }

}
