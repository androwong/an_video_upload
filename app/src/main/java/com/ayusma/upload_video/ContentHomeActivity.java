package com.ayusma.upload_video;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.khizar1556.mkvideoplayer.MKPlayer;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;


public class ContentHomeActivity extends AppCompatActivity {
    FirebaseFirestore db;
    String videotext;
    String usernamee;
    String url;
    Timestamp timestamp;
    private Map<String, Object> resultt;
    MKPlayer mkplayer;
    int getUrl = 1;
    int updateLike = 1;
    int updateDislike = 1;

    TextView tv_back, tv_comment;

    private String dte_frmt(String dtStart){
        String res="";

        //Date date=null;
        //SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");

        String temp = "Thu Dec 17 15:37:43 GMT+05:30 2015";
        String[] tmp=dtStart.split(" ");
        res=tmp[2]+"/"+tmp[1]+"/"+tmp[tmp.length-1]+"|"+tmp[3];
        /*try {
            date = (Date) formatter.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_home);

        db = FirebaseFirestore.getInstance();

        final TextView headline = findViewById(R.id.content_headline);
        final TextView username = findViewById(R.id.username);
        TextView follow = findViewById(R.id.follow);
        final TextView date = findViewById(R.id.date);
        final TextView details = findViewById(R.id.content_details);
        final TextView like = findViewById(R.id.like);
        final TextView dislike = findViewById(R.id.dislike);
        TextView comment = findViewById(R.id.comment);
        final TextView tv_comment = findViewById(R.id.tv_comment);

        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final Intent intent = getIntent();
        final String reference = intent.getStringExtra("category");
        final String topic = intent.getStringExtra("topic");

        CollectionReference enterntainment = db.collection(reference);


        Query query = enterntainment.whereEqualTo("videotext", topic);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();

                videotext = String.valueOf(documentSnapshot.get(0).get("videotext"));
                usernamee = String.valueOf(documentSnapshot.get(0).get("username"));
                url = String.valueOf(documentSnapshot.get(0).get("url"));

                timestamp = (documentSnapshot.get(0). getTimestamp("date"));

              Toast.makeText(getApplicationContext(), dte_frmt(timestamp.toDate().toString()),Toast.LENGTH_LONG);
                //MKPlayerActivity.configPlayer(activity).play(url)

                headline.setText(reference);
                username.setText(usernamee);
                details.setText(videotext);
               // date.setText(dte_frmt(timestamp.toDate().toString()));
                date.setText("18 Apr 2019  06:16 AM");

                if (getUrl == 1) {
                    getUrl = 2;
                    mkplayer = new MKPlayer(ContentHomeActivity.this);
                    mkplayer.play(url);

                }

                final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Map<String, Object> likesDislike = new HashMap<>();
                likesDislike.put("likes", "0");
                likesDislike.put("dislike", "0");
                likesDislike.put("topic", topic);

                db.collection(uid).document(topic).set(likesDislike);

            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference collectionReference = db.collection(reference);
                Query query = collectionReference.whereEqualTo("videotext", topic);
                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if(like.getText().toString().equalsIgnoreCase("like")) {
                            like.setText("Liked");
                            dislike.setText("Dislike");
                            tv_comment.setTextColor(getResources().getColor(R.color.green));
                        }else  if(like.getText().toString().equalsIgnoreCase("liked")){
                            like.setText("Like");
                            tv_comment.setTextColor(Color.BLACK);
                        }
                        /*List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();

                        String likee = String.valueOf(documentSnapshot.get(0).get("likes"));
                        String dislik = String.valueOf(documentSnapshot.get(0).get("dislike"));

                        int likeToInt = Integer.parseInt(likee);
                        int dislikeToInt = Integer.parseInt(dislik);

                        if (updateLike == 1) {
                            updateLike = 2;

                            int result = likeToInt + 1;
                            resultt = new HashMap<>();
                            resultt.put("likes", result);
                            db.collection(reference).document(topic).update(resultt);
                            like.setText("Liked");
                            dislike.setText("Dislike");
                            tv_comment.setTextColor(Color.GREEN);
                        }else {

                        }*/
                    }

                });
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CollectionReference collectionReference = db.collection(reference);
                Query query = collectionReference.whereEqualTo("videotext", topic);
                query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if(dislike.getText().toString().equalsIgnoreCase("dislike")) {
                            dislike.setText("Disliked");
                            like.setText("Like");
                            tv_comment.setTextColor(getResources().getColor(R.color.red));
                        }else  if(dislike.getText().toString().equalsIgnoreCase("disliked")){
                            dislike.setText("Dislike");
                            tv_comment.setTextColor(Color.BLACK);
                        }
                        /*List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();


                        String dislikee = String.valueOf(documentSnapshot.get(0).get("dislike"));

                        int dislikeToInt = Integer.parseInt(dislikee);

                        if (updateDislike == 1) {
                            updateDislike = 2;

                            int result = dislikeToInt + 1;
                            resultt = new HashMap<>();
                            resultt.put("dislike", result);
                            db.collection(reference).document(topic).update(resultt);
                            dislike.setText("Disliked");
                            like.setText("Like");
                            tv_comment.setTextColor(Color.RED);
                        }*/
                    }

                });
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mkplayer != null) {
            mkplayer.stop();
        }
    }
}