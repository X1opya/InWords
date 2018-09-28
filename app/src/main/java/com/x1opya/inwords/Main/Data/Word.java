package com.x1opya.inwords.Main.Data;

public class Word {
    private String word;
    private String translate;

    public Word(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public String getWord() {
        return word;
    }

    public String getTranslate() {
        return translate;
    }

    @Override
    public String toString() {
        return word+":"+translate;
    }
}
