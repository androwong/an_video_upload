package com.ayusma.upload_video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.ayusma.upload_video.Adapter.MyRecyclerViewAdapter;
import com.ayusma.upload_video.Fragment.BottomSheetFragment;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements   MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;

    private BottomSheetFragment mBottomSheet;
    ImageView arrow_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        arrow_back=(ImageView)findViewById(R.id.arrow_back);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBottomSheet = (BottomSheetFragment) BottomSheetFragment.newInstance("Bottom Sheet");
        final Button btn_edit = findViewById(R.id.edit_btn);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheet.show(getSupportFragmentManager(),mBottomSheet.getTag());

            }
        });

        Button btn_options = findViewById(R.id.btn_option);
        btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, HistotyControlActivity.class);
                startActivity(intent);
            }
        });


        // data to populate the RecyclerView with
        final ArrayList<String> topic = new ArrayList<>();
        topic.add("Lets try this app bro..look exciting,Lets try this app bro..look exciting\",Lets try this app bro..look exciting,Lets try this app bro..look exciting,Lets try this app bro..look exciting");
        topic.add("Lets try this app bro..look exciting,Lets try this app bro..look exciting\",Lets try this app bro..look exciting,Lets try this app bro..look exciting,Lets try this app bro..look exciting");



        final ArrayList<String> days = new ArrayList<>();
        days.add("1d");
        days.add("2d");

        final ArrayList<String> buzzme = new ArrayList<>();
        buzzme.add("buzzme");
        buzzme.add("cnn");

        final ArrayList<String> comment = new ArrayList<>();
        comment.add("3");
        comment.add("4");

        final ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.com_facebook_button_icon_white);
        images.add(R.drawable.com_facebook_button_icon);




        // set up the RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, topic,days,buzzme,images,comment);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void gun() {
        final ArrayList<String> topic = new ArrayList<>();


        final ArrayList<String> days = new ArrayList<>();

        final ArrayList<String> buzzme = new ArrayList<>();

        final ArrayList<String> comment = new ArrayList<>();

        final ArrayList<Integer> images = new ArrayList<>();




        // set up the RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, topic,days,buzzme,images,comment);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        mBottomSheet.dismiss();
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
    }

}
