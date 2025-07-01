package com.app.words.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.words.R;
import com.app.words.adapter.MyAdapter;
import com.app.words.databinding.FragmentWordsBinding;
import com.app.words.room.Word;
import com.app.words.viewmodel.WordViewModel;

import java.util.List;


public class WordsFragment extends Fragment {


    private @NonNull FragmentWordsBinding binding;
    private WordViewModel wordViewModel;
    private MyAdapter myAdapterNormal,myAdapterCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentWordsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //获取ViewModel
        wordViewModel=new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        //设置RecyclerView适配器
        binding.rvWords.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapterNormal = new MyAdapter(false, wordViewModel);
        myAdapterCard = new MyAdapter(true, wordViewModel);
        binding.rvWords.setAdapter(myAdapterNormal);
        wordViewModel.getAllWordsLive().observe(requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapterNormal.getItemCount();
                myAdapterNormal.setList(words);
                myAdapterCard.setList(words);
                //更新RecyclerView数据
                if(temp!=words.size()) {
                    myAdapterNormal.notifyDataSetChanged();
                    myAdapterCard.notifyDataSetChanged();
                }
            }
        });
        //设置悬浮按钮的点击事件
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到AddFragment
                NavController navController= Navigation.findNavController(v);
                navController.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });

    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        binding.btnAdd.setOnClickListener(v -> {
//            // 添加单词
//            String[] english = {
//                    "Hello",
//                    "World",
//                    "Android",
//                    "Google",
//                    "Studio",
//                    "Project",
//                    "Database",
//                    "Recycler",
//                    "View",
//                    "String",
//                    "Value",
//                    "Integer"};
//            String[] chinese = {
//                    "你好",
//                    "世界",
//                    "安卓系统",
//                    "谷歌公司",
//                    "工作室",
//                    "项目",
//                    "数据库",
//                    "回收站",
//                    "视图",
//                    "字符串",
//                    "价值",
//                    "整数类型"};
//            for (int i = 0; i < english.length; i++) {
//                Word word = new Word(english[i], chinese[i]);
//                wordViewModel.addWords(word);
//            }
//        });
//        binding.btnUpdate.setOnClickListener(v -> {
//            // 更新单词
//            Word word = new Word("hi", "你好啊");
//            word.setId(70); // 假设要更新的单词ID为1
//            wordViewModel.updateWords(word);
//
//        });
//        binding.btnDelete.setOnClickListener(v -> {
//            // 删除单词
//            Word word = new Word("hello", "你好");
//            word.setId(70); // 假设要删除的单词ID为1
//            wordViewModel.deleteWords(word);
//        });
//        binding.btnClear.setOnClickListener(v -> {
//            // 删除所有单词
//            wordViewModel.clearWords();
//        });
//    }
}