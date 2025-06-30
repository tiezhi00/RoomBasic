package com.app.roombasic;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.roombasic.adapter.MyAdapter;
import com.app.roombasic.databinding.ActivityMainBinding;
import com.app.roombasic.room.Word;
import com.app.roombasic.viewmodel.WordViewModel;

public class MainActivity extends AppCompatActivity {
    private @NonNull ActivityMainBinding binding;
    private WordViewModel wordViewModel;
    private MyAdapter myAdapterNormal, myAdapterCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        //RecyclerView初始化
        myAdapterNormal = new MyAdapter(false, wordViewModel);
        myAdapterCard = new MyAdapter(true, wordViewModel);
        binding.rvWords.setAdapter(myAdapterNormal);
        binding.rvWords.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        //switch切换布局
        binding.swCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.rvWords.setAdapter(myAdapterCard);
                } else {
                    binding.rvWords.setAdapter(myAdapterNormal);
                }
            }
        });
        //初始化ViewModel
        wordViewModel.getAllWordsLive().observe(this, words -> {
            int temp = myAdapterNormal.getItemCount();
            myAdapterNormal.setList(words);
            myAdapterCard.setList(words);
            if(temp!= words.size()) {
                myAdapterNormal.notifyDataSetChanged();
                myAdapterCard.notifyDataSetChanged();
            }
        });

        binding.btnAdd.setOnClickListener(v -> {
            // 添加单词
            String[] english = {
                    "Hello",
                    "World",
                    "Android",
                    "Google",
                    "Studio",
                    "Project",
                    "Database",
                    "Recycler",
                    "View",
                    "String",
                    "Value",
                    "Integer"};
            String[] chinese = {
                    "你好",
                    "世界",
                    "安卓系统",
                    "谷歌公司",
                    "工作室",
                    "项目",
                    "数据库",
                    "回收站",
                    "视图",
                    "字符串",
                    "价值",
                    "整数类型"};
            for (int i = 0; i < english.length; i++) {
                Word word = new Word(english[i], chinese[i]);
                wordViewModel.addWords(word);
            }
        });
        binding.btnUpdate.setOnClickListener(v -> {
            // 更新单词
            Word word = new Word("hi", "你好啊");
            word.setId(70); // 假设要更新的单词ID为1
            wordViewModel.updateWords(word);

        });
        binding.btnDelete.setOnClickListener(v -> {
            // 删除单词
            Word word = new Word("hello", "你好");
            word.setId(70); // 假设要删除的单词ID为1
            wordViewModel.deleteWords(word);
        });
        binding.btnClear.setOnClickListener(v -> {
            // 删除所有单词
            wordViewModel.clearWords();
        });
    }
}