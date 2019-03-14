package com.example.mapchattest2.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mapchattest2.R;
import com.example.mapchattest2.utils.AuthenticationUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

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
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.bt_register)
    Button btRegister;

    private Uri avatarUri = null;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initFirebaseAuth();

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            Uri uri = data.getData();
//
//
//            try {
//                long size = AuthenticationUtils.getSizefromUri(this, uri);
//                Log.d(TAG, "onActivityResult: " + size);
//                if (size > 2048) {
//                    Toast.makeText(RegisterActivity.this, "The image is langer than 2Mb", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                    // Log.d(TAG, String.valueOf(bitmap));;
//               //     ivAvatar.setImageBitmap(bitmap);
//                    avatarUri = uri;
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @OnClick({ R.id.bt_cancel, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                onBackPressed();
                break;
            case R.id.bt_register:
                String name = etFullName.getText().toString();
                String notification = name.length() == 0 ? "Name feild is empty"
                        : !AuthenticationUtils.isValidEmail(etEmail.getText()) ? "Invalid email"
                        : !AuthenticationUtils.isValidPassword(etPassword.getText().toString()) ? "Password is mininum 6 characters length"
                        : !AuthenticationUtils.isExactTyping(etPassword.getText().toString(), etReEnterPassword.getText().toString()) ? "Enter password is incorrect!"
                      //  : avatarUri == null ? "upload avatar unsuccessfully"
                        : "";
                if (notification.length() > 0) {
                    Toast.makeText(RegisterActivity.this, notification, Toast.LENGTH_SHORT).show();
                    break;
                }

               createUserWithEmailPassword(etEmail.getText().toString(), etPassword.getText().toString());


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

//    private void initFirebaseDatabase(){
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("avatars");
//    }
//    private void save(Uri uri){
//
//        try {
//            String key = AuthenticationUtils.convertEmailtoNameFile(etEmail.getText().toString());
//            String value = AuthenticationUtils.convertBitmaptoBase64(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
//            databaseReference.child(key).setValue(value);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void initFirebaseAuth(){
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private void createUserWithEmailPassword(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        if(user == null){

        }else{
            updateProfileUser(user);
            sendEmailVerification(user);

        }

    }
    private void updateProfileUser(FirebaseUser user){

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(etFullName.getText().toString())
                .setPhotoUri(Uri.parse("https://profiles.utdallas.edu/img/default.png"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
    private void sendEmailVerification(FirebaseUser user){
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            CountDownTimer countDownTimer = new CountDownTimer(200,100) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    Toast.makeText(RegisterActivity.this, "An email verification is sent to your email", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFinish() {
                                    onBackPressed();
                                }
                            }.start();


                        }
                    }
                });
    }

}
