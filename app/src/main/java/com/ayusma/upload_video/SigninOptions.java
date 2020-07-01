package com.ayusma.upload_video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SigninOptions extends AppCompatActivity {

    private int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION=234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_options);
        permission();
        //Get Firebase mAuth instance
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    String user_id = mAuth.getCurrentUser().getUid();
                    Log.d("Tag",user_id);
                    Intent intent = new Intent(SigninOptions.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };

        // Choose authentication providers
        final List<AuthUI.IdpConfig> Facebook = Collections.singletonList(
                new AuthUI.IdpConfig.FacebookBuilder().build());

        final List<AuthUI.IdpConfig> Google = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        final List<AuthUI.IdpConfig> Email = Collections.singletonList(
                new AuthUI.IdpConfig.EmailBuilder().build());


       RelativeLayout google_layout = findViewById(R.id.google);
        google_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Google)
                                .setLogo(R.drawable.ic_launcher_background)
                                .setTheme(R.style.AppTheme)
                                .build(),
                        RC_SIGN_IN);

            }
        });

       RelativeLayout facebook_layout = findViewById(R.id.facebook);
        facebook_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Facebook)
                                .setLogo(R.drawable.ic_launcher_background)
                                .setTheme(R.style.AppTheme)
                                .build(),
                        RC_SIGN_IN);


            }
        });

       RelativeLayout mail_layout = findViewById(R.id.email);
        mail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Email)
                                .setLogo(R.drawable.ic_launcher_background)
                                .setTheme(R.style.AppTheme)
                                .build(),
                        RC_SIGN_IN);

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String user_id = mAuth.getCurrentUser().getUid();
                Log.d("Tag",user_id);
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

    }

    



    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
    public void permission(){
        try {
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(SigninOptions.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SigninOptions.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
            }
        }catch (Exception ex){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
                int grantResultsLength = grantResults.length;
                if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "You grant write external storage permission. Please click original button again to continue.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception ex){

        }
    }

}
