package com.ayusma.upload_video.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayusma.upload_video.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private ArrayList<String> mTopic;
    private ArrayList<String> mLikes;
    private ArrayList<String> mDislikes;
    private ArrayList<String> mUsername;
    private ArrayList<String> mComments;
    private ArrayList<String> mImageView;
    private Context context;
    private itemClickListener ClickListener;



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mCardView;
        TextView mTopic;
        TextView mLikes;
        TextView mDislikes;
        TextView mComments;
        TextView mUsername;
        ImageView mImageView;


        public MyViewHolder(View itemView){
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTopic = (TextView) itemView.findViewById(R.id.topic);
            mLikes = (TextView) itemView.findViewById(R.id.like);
            mDislikes = (TextView) itemView.findViewById(R.id.dislike);
            mComments = (TextView)itemView.findViewById(R.id.comment);
            mUsername = (TextView)itemView.findViewById(R.id.username);
            mImageView = (ImageView) itemView.findViewById(R.id.image_recy);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (ClickListener != null) ClickListener.onItemClick(view, getAdapterPosition());


        }
    }

    public MyAdapter(ArrayList<String> topic,ArrayList<String> like,ArrayList<String> dislike,ArrayList<String> comment,ArrayList<String> username,ArrayList<String> url ){
        mTopic = topic;
        mComments = comment;
        mLikes = like;
        mDislikes = dislike;
        mUsername = username;
        mImageView = url;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return vh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.mTopic.setText(mTopic.get(position));
        holder.mLikes.setText(mLikes.get(position));
        holder.mDislikes.setText(mDislikes.get(position));
        holder.mUsername.setText(mUsername.get(position));
        holder.mComments.setText(mComments.get(position));

        RequestOptions requestOptions = RequestOptions.placeholderOf(R.drawable.ic_launcher_foreground);

        Glide.with(context).load(mImageView.get(position)).thumbnail(0.1f).apply(requestOptions).into(holder.mImageView);

    }

    @Override
    public int getItemCount(){
        return mTopic.size();
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mTopic.get(id);
    }

    // allows clicks events to be caught
   public void setClickListener(itemClickListener ItemClickListener) {
        this.ClickListener = ItemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface itemClickListener {
        void onItemClick(View view, int position);
    }

}
