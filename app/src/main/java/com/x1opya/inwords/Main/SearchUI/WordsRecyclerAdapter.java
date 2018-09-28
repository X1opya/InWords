package com.x1opya.inwords.Main.SearchUI;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.x1opya.inwords.R;

public class WordsRecyclerAdapter extends RecyclerView.Adapter<WordsRecyclerAdapter.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView translate;
        public MyViewHolder(View view) {
            super(view);
            word = view.findViewById(R.id.word);
            word = view.findViewById(R.id.translate);
        }
    }
}
