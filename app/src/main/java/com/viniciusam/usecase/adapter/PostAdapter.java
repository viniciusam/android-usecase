package com.viniciusam.usecase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viniciusam.usecase.R;
import com.viniciusam.usecase.model.Post;

/**
 * Created by Vinicius on 18/08/2016.
 */
public class PostAdapter extends ListRecyclerAdapter<Post, PostAdapter.ViewHolder> {

    private Callbacks mCallbacks;

    public PostAdapter(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_main, parent, false);
        return new ViewHolder(v, mCallbacks);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Post post) {
        holder.PostTitle.setText(post.getTitle());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView PostTitle;
        public ImageView DeleteButton;

        public ViewHolder(View v, final Callbacks callbacks) {
            super(v);
            PostTitle = (TextView) v.findViewById(R.id.text);
            DeleteButton = (ImageView) v.findViewById(R.id.delete);

            DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callbacks.onDeleteClicked(getAdapterPosition());
                }
            });
        }

    }

    public interface Callbacks {

        void onDeleteClicked(int pos);

    }

}
