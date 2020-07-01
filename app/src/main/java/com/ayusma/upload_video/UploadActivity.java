package com.ayusma.upload_video;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ayusma.upload_video.Model.VideoDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UploadActivity extends AppCompatActivity /*  implementing click listener */ {
    //a constant to track the file chooser intent
    private static final int PICK_VIDEO_REQUEST = 234;

    RelativeLayout relativeLayout;
    //ImageView
    private VideoView videoView;

    //a Uri object to store file path
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    String uid;
    FirebaseFirestore db;
    String radio = "";
    String if_article_video = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();

        //getting views from layout
        Button buttonChoose = findViewById(R.id.buttonChoose);
        Button buttonUpload = findViewById(R.id.buttonUpload);

        videoView = findViewById(R.id.videoView);

        //attaching listener
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            videoView.setVideoURI(filePath);
            videoView.start();




          /*  try {
             //   Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            } */
        }
    }

    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload

        final String username = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();

        final EditText editText = findViewById(R.id.topic);
        final String topic = editText.getText().toString();
        if (topic.isEmpty()) {
            RelativeLayout relativeLayout = findViewById(R.id.activity_upload);
            Snackbar.make(relativeLayout, "Please Enter a Topic", Snackbar.LENGTH_LONG).show();
        }

        if (if_article_video == null) {
            RelativeLayout relativeLayout = findViewById(R.id.activity_upload);
            Snackbar.make(relativeLayout, "Select either Video or Article", Snackbar.LENGTH_LONG).show();
        }

        if (radio == null) {
            RelativeLayout relativeLayout = findViewById(R.id.activity_upload);
            Snackbar.make(relativeLayout, "Please select a category", Snackbar.LENGTH_LONG).show();
        }


        if (filePath != null & if_article_video != null & radio != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            final StorageReference riversRef = storageReference.child("videos/" + uid + "/" + topic);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    VideoDetails videoDetails = new VideoDetails();
                                    videoDetails.setComments("0");
                                    videoDetails.setDislike("0");
                                    videoDetails.setLikes("0");
                                    videoDetails.setUrl(uri.toString());

                                    Map<String, Object> video = new HashMap<>();
                                    video.put("url", uri.toString());
                                    video.put("comments", "0");
                                    video.put("likes", "0");
                                    video.put("dislike", "0");
                                    video.put("videotext", topic);
                                    video.put("username",username);
                                    video.put("type",if_article_video);
                                    video.put("date", new Timestamp(new Date()));


                                  //  db.collection("Videos").document(if_article_video).set(video);
                                  //  db.collection(radio).document(topic).set(video);


                                    db.collection(radio).document(topic).set(video).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("SUCCESS","saved to database successfully");

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        Log.d("ERROR",e.getMessage());
                                        }
                                    });



                                }
                            });

                            //and displaying a success toast
                            RelativeLayout relativeLayout = findViewById(R.id.main_layout);

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                         //   Snackbar.make(relativeLayout, "File Uploaded", Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            progressDialog.setCanceledOnTouchOutside(false);
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();


        switch ((view.getId())) {
            case R.id.entertainment_checkBox:
                if (checked) {
                    radio = "entertainment";
                }
                break;
            case R.id.movie_checkBox:
                if (checked) {
                    radio = "movie";
                }
                break;
            case R.id.sport_checkBox:
                if (checked) {
                    radio = "sport";
                }
                break;
            case R.id.political_checkBox:
                if (checked) {
                    radio = "political";
                }
                break;
            case R.id.bangla_checkBox:
                if (checked) {
                    radio = "bangla";
                }
                break;
            case R.id.russia_checkBox:
                if (checked) {
                    radio = "russia";
                }
                break;
            case R.id.english_checkBox:
                if (checked) {
                    radio = "english";
                }
                break;
            case R.id.tech_checkBox:
                if (checked) {
                    radio = "tech";
                }
                break;
            case R.id.world_checkBox:
                if (checked) {
                    radio = "world";
                }
                break;
        }


    }


    public void articleVideo(View view) {
        boolean checked = ((RadioButton) view).isChecked();


        switch ((view.getId())) {
            case R.id.radio_article:
                if (checked) {
                    if_article_video = "article";
                }
                break;
            case R.id.radio_video:
                if (checked) {
                    if_article_video = "video";
                }
                break;


        }
    }


}