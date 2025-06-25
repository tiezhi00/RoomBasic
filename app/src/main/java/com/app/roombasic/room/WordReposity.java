package com.app.roombasic.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordReposity {
    private final WordDao wordDao;
    private final LiveData<List<Word>> allWordsLive;

    public WordReposity(Context context) {
        wordDao = WordDatabase.getInstance(context.getApplicationContext()).getWordDao();
        allWordsLive = wordDao.getAllWordsLive(); //获取所有单词的LiveData
    }

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public void addWord(Word... words) {
        new AddAsyncTask(wordDao).execute(words);
    }

    public void updateWords(Word... words) {
        new UpdateAsyncTak(wordDao).execute(words);
    }

    public void deleteWords(Word... words) {
        new DeleteAsyncTak(wordDao).execute(words);
    }

    public void clearWords() {
        new ClearAsyncTak(wordDao).execute();
    }

    private static class AddAsyncTask extends AsyncTask<Word, Void, Void> {
        private final WordDao wordDao;

        AddAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    static class UpdateAsyncTak extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        public UpdateAsyncTak(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteAsyncTak extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        public DeleteAsyncTak(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    static class ClearAsyncTak extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        public ClearAsyncTak(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }


}
