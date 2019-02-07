package com.example.anggarisky.photoemptystatesapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPhotoAct extends AppCompatActivity {

    ImageView imageView, photoStory;
    TextView textView, textView2, photoTitle;
    Button btnaddpic, btnsavepicture;
    Integer photomax = 1;
    Uri picturelocation;

    String USER_NAME_STORY = "usernamestory";
    String userNameStoryLocal = "";
    String userNameStoryNew = "";

    DatabaseReference reference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        loadDataUsernameLocal();

        imageView = findViewById(R.id.imageView);
        photoStory = findViewById(R.id.photoStory);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        photoTitle = findViewById(R.id.photoTitle);

        btnaddpic = findViewById(R.id.btnaddpic);
        btnsavepicture = findViewById(R.id.btnsavepicture);

        // set default alpha
        btnsavepicture.setAlpha(0);
        photoTitle.setAlpha(0);

        btnsavepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhotoToFirebaseStorage();
            }
        });

        // give event to button add pic
        btnaddpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

    }

    public void savePhotoToFirebaseStorage(){

        reference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userNameStoryNew);

        storageReference = FirebaseStorage.getInstance().getReference().child("Users")
                .child(userNameStoryNew);

        if (picturelocation != null){
            StorageReference storageReference1 = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(picturelocation));

            storageReference1.putFile(picturelocation)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String uripicture = taskSnapshot.getDownloadUrl().toString();
                            reference.getRef().child("picture").setValue(uripicture);
                        }
                    });

        }
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photomax);
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photomax && resultCode == RESULT_OK &&
                data != null && data.getData() != null){

            imageView.animate().alpha(0).setDuration(350).start();
            btnaddpic.animate().alpha(0).setDuration(350).start();
            textView.animate().alpha(0).setDuration(350).start();
            textView2.animate().alpha(0).setDuration(350).start();

            photoTitle.animate().alpha(1).setDuration(350).start();
            btnsavepicture.animate().alpha(1).setDuration(350).start();

            picturelocation = data.getData();
            Picasso.with(this).load(picturelocation).centerCrop().fit().into(photoStory);

        }

    }

    public void loadDataUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USER_NAME_STORY, MODE_PRIVATE);
        userNameStoryNew = sharedPreferences.getString(userNameStoryLocal, "");
    }
}
