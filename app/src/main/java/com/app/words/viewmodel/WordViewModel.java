package com.app.words.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.words.room.Word;
import com.app.words.room.WordReposity;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordReposity wordReposity;
    private LiveData<List<Word>> allWordsLive;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordReposity = new WordReposity(application);
        allWordsLive = wordReposity.getAllWordsLive(); //获取所有单词的LiveData
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public void addWords(Word... words) {
        wordReposity.addWord(words);
    }

    public void updateWords(Word... words) {
        wordReposity.updateWords(words);
    }

    public void deleteWords(Word... words) {
        wordReposity.deleteWords(words);
    }

    public void clearWords() {
        wordReposity.clearWords();
    }

}
