package com.example.mapchattest2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final int PICK_IMAGE_REQUEST = 2;
    @BindView(R.id.et_full_name)
    EditText etFullName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_reEnter_password)
    EditText etReEnterPassword;
    @BindView(R.id.ll_browse_avatar)
    LinearLayout llBrowseAvatar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.bt_register)
    Button btRegister;

    private Uri avatarUri = null;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initFirebaseDatabase();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            try {
                long size = Utils.getSizefromUri(this, uri);
                Log.d(TAG, "onActivityResult: " + size);
                if (size > 2048) {
                    Toast.makeText(RegisterActivity.this, "The image is langer than 2Mb", Toast.LENGTH_SHORT).show();
                } else {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));;
                    ivAvatar.setImageBitmap(bitmap);
                    avatarUri = uri;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick({R.id.ll_browse_avatar, R.id.bt_cancel, R.id.bt_register, R.id.iv_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_browse_avatar:
                openGalley();
                break;
            case R.id.iv_avatar:
                openGalley();
                break;
            case R.id.bt_cancel:
                onBackPressed();
                break;
            case R.id.bt_register:
                String name = etFullName.getText().toString();
                String notification = name.length() == 0 ? "Name feild is empty"
                        : !Utils.isValidEmail(etEmail.getText()) ? "Invalid email"
                        : !Utils.isValidPassword(etPassword.getText().toString()) ? "Password is mininum 6 characters length"
                        : !Utils.isExactTyping(etPassword.getText().toString(), etReEnterPassword.getText().toString()) ? "Enter password is incorrect!"
                        : avatarUri == null ? "upload avatar unsuccessfully" : "";
                if (notification.length() > 0) {
                    Toast.makeText(RegisterActivity.this, notification, Toast.LENGTH_SHORT).show();
                    break;
                }
                save(avatarUri);


                break;
        }
    }


    private void openGalley() {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void initFirebaseDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("avatars");
    }
    private void save(Uri uri){

        try {
            String key = Utils.convertEmailtoNameFile(etEmail.getText().toString());
            String value = Utils.convertBitmaptoBase64(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
            databaseReference.child(key).setValue(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
