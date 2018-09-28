package com.x1opya.inwords.Main.SearchUI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.x1opya.inwords.Main.Data.Word;
import com.x1opya.inwords.R;

import java.util.Collection;
import java.util.List;

public class WordsRecyclerAdapter extends RecyclerView.Adapter<WordsRecyclerAdapter.MyViewHolder> implements AdaptersBehavior {

    LayoutInflater inflater;
    List<Word> list;

    public WordsRecyclerAdapter(Context context, List<Word> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }


    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.search_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.println(Log.ASSERT,getClass().toString(),"Слово:" + list.get(position).getWord()+ " Перевод:"+list.get(position).getTranslate());
        holder.word.setText(list.get(position).getWord());
        holder.translate.setText(list.get(position).getTranslate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public void addAll(@NonNull Collection<? extends Word> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView translate;
        public MyViewHolder(View view) {
            super(view);
            word = view.findViewById(R.id.word);
            translate = view.findViewById(R.id.translate);
        }
    }
}
