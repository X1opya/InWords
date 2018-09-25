package com.x1opya.inwords.SearchUI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.x1opya.inwords.R;
import com.x1opya.inwords.SearchUI.Data.Word;

import java.util.List;

public class SearchAdapter extends ArrayAdapter<Word>{

    private int layout;
    private List<Word> list;
    private LayoutInflater inflater;


    public SearchAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects) {
        super(context, resource, objects);
        layout = resource;
        list = objects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layout,parent,false);
        TextView tvWord = view.findViewById(R.id.word);
        TextView tvTranslate = view.findViewById(R.id.translate);
        tvWord.setText(list.get(position).getWord());
        tvTranslate.setText(list.get(position).getTranslate());
        return view;
    }
}
