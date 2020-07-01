package com.ayusma.upload_video.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayusma.upload_video.Adapter.MyAdapter;
import com.ayusma.upload_video.ContentHomeActivity;
import com.ayusma.upload_video.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class PoliticalFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, MyAdapter.itemClickListener {

    RecyclerView rv;
    MyAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    View rootView;


    public PoliticalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                getData();

            }
        });


        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), ContentHomeActivity.class);
        startActivity(intent);
    }

    public void getData() {
        //    mSwipeRefreshLayout.setRefreshing(true);
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final ArrayList<String> Topic = new ArrayList<>();
        final ArrayList<String> Username = new ArrayList<>();
        final ArrayList<String> dislike = new ArrayList<>();
        final ArrayList<String> likes = new ArrayList<>();
        final ArrayList<String> comments = new ArrayList<>();
        final ArrayList<String> videoUrl = new ArrayList<>();

        CollectionReference enterntainment = db.collection("political");

        Query query = enterntainment.whereEqualTo("comments", "0");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> documentSnapshot = queryDocumentSnapshots.getDocuments();

                if (adapter != null) {
                    Topic.clear();
                    Username.clear();
                    dislike.clear();
                    likes.clear();
                    comments.clear();
                }


                for (int i = 0; i < documentSnapshot.size(); i++) {
                    Object object = documentSnapshot.get(i).get("videotext");
                    String string = object.toString();
                    Topic.add(string);
                }

                for (int i = 0; i < documentSnapshot.size(); i++) {
                    Object object = documentSnapshot.get(i).get("username");
                    String string = object.toString();
                    Username.add(string);
                }

                for (int i = 0; i < documentSnapshot.size(); i++) {
                    Object object = documentSnapshot.get(i).get("dislike");
                    String string = object.toString();
                    dislike.add(string);
                }

                for (int i = 0; i < documentSnapshot.size(); i++) {
                    Object object = documentSnapshot.get(i).get("likes");
                    String string = object.toString();
                    likes.add(string);
                }

                for (int i = 0; i < documentSnapshot.size(); i++) {
                    Object object = documentSnapshot.get(i).get("comments");
                    String string = object.toString();
                    comments.add(string);
                }

                for (int i = 0; i < documentSnapshot.size(); i++) {
                    Object object = documentSnapshot.get(i).get("url");
                    String string = object.toString();
                    videoUrl.add(string);
                }


                adapter = new MyAdapter(Topic, likes, dislike, comments, Username, videoUrl);
                // rv.setOnClickListener(SportFragment.this);
                if (!Topic.isEmpty()) {
                    rv.setAdapter(adapter);
                    adapter.setClickListener(PoliticalFragment.this);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);
                }



                mSwipeRefreshLayout.setRefreshing(false);
            }

        });


    }

    @Override
    public void onRefresh() {
        getData();

    }

    @Override
    public void onItemClick(View view, int position) {
       // Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
      //String string =   view.toString();
        Intent intent = new Intent(getContext(),ContentHomeActivity.class);
        intent.putExtra("category","political");
        intent.putExtra("topic",adapter.getItem(position));
        startActivity(intent);
    }
}
