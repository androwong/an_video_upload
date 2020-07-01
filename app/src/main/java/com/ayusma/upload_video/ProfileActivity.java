package com.ayusma.upload_video;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class ProfileActivity extends AppCompatActivity {

    ImageView profile_img;

    int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_img = (ImageView) findViewById(R.id.profile_img);

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openImageChooser();
            }
        });
    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        try {profile_img.invalidate();
            profile_img.setImageBitmap(null);
           // Toast.makeText(getApplicationContext(), "in", Toast.LENGTH_SHORT).show();
            int req = requestCode;
            //if (resultCode == RESULT_OK)
            {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                profile_img.invalidate();
                profile_img.setImageBitmap(null);
                profile_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                /*Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                profile_img.invalidate();
                profile_img.setImageBitmap(null);
                img.setImageBitmap(bitmap);*/
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    public void permission(){
        try {
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(ProfileActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProfileActivity.this,
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
