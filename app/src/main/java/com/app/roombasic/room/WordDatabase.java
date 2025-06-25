package com.app.roombasic.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase instance;

    public static synchronized WordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")//使用的是applicationContext
                    .build();
        }
        return instance;
    }

    public abstract WordDao getWordDao(); //定义一个抽象方法，返回WordDao接口的实例


}
