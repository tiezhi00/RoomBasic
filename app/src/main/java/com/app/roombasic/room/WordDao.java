package com.app.roombasic.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//访问数据库操作的接口
public interface WordDao {
    @Insert
//插入
    void insertWords(Word... words);//可变参数，插入多个单词

    @Update
//更新
    void updateWords(Word... words);//更新单词

    @Delete
//删除
    void deleteWords(Word... words);//删除单词

    @Query("DELETE FROM WORD")
//删除所有单词
    void deleteAllWords();//删除所有单词

    @Query("SELECT * FROM WORD ORDER BY id DESC")
//查询所有单词，按id降序排列
//    List<Word> getAllWords();//获取所有单词
    LiveData<List<Word>> getAllWordsLive();//获取所有单词，按id降序排列

}
