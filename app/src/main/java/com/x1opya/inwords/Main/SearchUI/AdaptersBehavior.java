package com.x1opya.inwords.Main.SearchUI;

import android.support.annotation.NonNull;

import com.x1opya.inwords.Main.Data.Word;

import java.util.Collection;

interface AdaptersBehavior {
    void clear();
    void addAll(@NonNull Collection<? extends Word> collection );
    void notifyDataSetChanged();
}
