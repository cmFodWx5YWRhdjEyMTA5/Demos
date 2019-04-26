package com.enigneerbabu.demos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UpdateProfileActivity extends AppCompatActivity {
    String name, email, mobile, photo;
    EditText edittext_Name,edittext_EmailId,edittext_Phone;
    ImageView imageview_Photo;
    Button button_Add_User;
    GoogleSignInAccount mAccount;
    HelperFile mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        mAccount = GoogleSignIn.getLastSignedInAccount(this);
        name = mAccount.getDisplayName();
        email = mAccount.getEmail();
        photo = String.valueOf(mAccount.getPhotoUrl());



        initView();

    }

    private void initView() {
        edittext_Name = findViewById(R.id.edittext_Name);
        edittext_EmailId = findViewById(R.id.edittext_EmailId);
        edittext_Phone = findViewById(R.id.edittext_Phone);
        button_Add_User = findViewById(R.id.button_Add_User);
        imageview_Photo = findViewById(R.id.imageview_Photo);
        edittext_Name.setText(name);
        edittext_EmailId.setText(email);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.user_img)
                .error(R.mipmap.ic_launcher_round);
        try {
            Glide.with(this)
                    .load(photo)
                    .apply(options)
                    .into(imageview_Photo);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not Load image.." + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        button_Add_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateProfileActivity.this,name,Toast.LENGTH_SHORT).show();
            }
        });
        }






}