package com.app.roombasic.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton
@Database(entities = {Word.class}, version = 5, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase instance;
    private static final Migration MIGRATION_2_3=new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {//添加字段
            database.execSQL("ALTER TABLE word ADD COLUM bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };
    private static final Migration MIGRATION_3_4=new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {//删除字段
            database.execSQL("CREATE TABLE word_temp (id INTEGER PRIMARY KEY NOT NULL, english_word TEXT, chinese_meaning TEXT)");
            database.execSQL("INSERT INTO word_temp (id, english_word, chinese_meaning) SELECT id, english_word, chinese_meaning FROM word");
            database.execSQL("DROP TABLE word");
            database.execSQL("ALTER TABLE word_temp RENAME TO word");
        }
    };
    private static final Migration MIGRATION_4_5=new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUM chinese_invisiable INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static synchronized WordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")//使用的是applicationContext
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_4_5)
                    .build();
        }
        return instance;
    }

    public abstract WordDao getWordDao(); //定义一个抽象方法，返回WordDao接口的实例


}
