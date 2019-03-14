package com.example.mapchattest2.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class AuthenticationUtils {
    private static final String TAG = "AuthenticationUtils";
    public static final int PASSWORD_ACCOUNT = 0;
    public static final int FACEBOOK_ACCOUNT = 1;
    public static final int GOOGLE_ACCOUNT = 2;
    public static  final int NON_ACCOUNT = -1;

    public static String convertEmailtoNameFile(String email) {
        if (email == null) return null;
        Log.d(TAG, "convertEmailtoNameFile: " + email);
        String result = email.replace('@', '-').replace(".", "--");
        Log.d(TAG, "convertEmailtoNameFile: " + result);
        return result;

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidPassword(String password) {
        return password != null && password.length() > 5;
    }

    public final static boolean isExactTyping(String first, String second) {
        return first.equals(second);
    }

    public final static String convertBitmaptoBase64(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public final static String convertBase64toBitmap(String base64) {
        return null;
    }

    public final static long getSizefromUri(Context context, Uri uri) {
        String scheme = uri.getScheme();
        System.out.println("Scheme type " + scheme);
        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            try {
                InputStream fileInputStream = context.getContentResolver().openInputStream(uri);
                return fileInputStream.available() / (1024);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
            String path = uri.getPath();
            try {
                return (new File(path)).length()/(1024);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    public final static int classifyAccount(FirebaseUser user){
        if(user == null) return NON_ACCOUNT;
        String providerId = user.getProviderId();
        return providerId.contains("facebook") ? FACEBOOK_ACCOUNT
                : providerId.contains("google") ? GOOGLE_ACCOUNT
                : PASSWORD_ACCOUNT;
    }
}
