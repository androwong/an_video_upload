package com.ayusma.upload_video.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ayusma.upload_video.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mTopic;
    private List<String> mDays;
    private List<String> mBuzzMe;
    private List<Integer> mImage;
    private List<String> mComment;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int lastPosition = -1;
    private Context context;

    // data is passed into the constructor
 public    MyRecyclerViewAdapter(Context context, List<String> topic,List<String> days,List<String> buzzme,List<Integer> image,List<String> comment) {
        this.mInflater = LayoutInflater.from(context);
        this.mTopic = topic;
        this.mDays = days;
        this.mBuzzMe = buzzme;
        this.mImage = image;
        this.mComment = comment;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String topic = mTopic.get(position);
        String comment = mComment.get(position);
        String buzzme = mBuzzMe.get(position);
        Integer Image = mImage.get(position);
        String days = mDays.get(position);

        holder.topic.setText(topic);
        holder.comment.setText(comment);
        holder.buzz_me.setText(buzzme);
        holder.days.setText(days);
        holder.imageView.setImageResource(Image);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mTopic.size();


    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView days,buzz_me,comment;
        ImageView imageView,img;
        EditText topic;

        ViewHolder(View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topic);
            days= itemView.findViewById(R.id.days);
            buzz_me = itemView.findViewById(R.id.buzzme);
            comment = itemView.findViewById(R.id.comment);
            imageView = itemView.findViewById(R.id.image);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
   public String getItem(int id) {
        return mTopic.get(id);
    }

    // allows clicks events to be caught
  public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}