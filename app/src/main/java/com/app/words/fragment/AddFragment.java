package com.app.words.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.app.words.R;
import com.app.words.databinding.FragmentAddBinding;
import com.app.words.room.Word;
import com.app.words.viewmodel.WordViewModel;

public class AddFragment extends Fragment {

    private @NonNull FragmentAddBinding binding;
    private WordViewModel wordViewModel;
    private InputMethodManager imm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //初始化ViewBinding
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //提交按钮初始为置灰
        binding.btnAdd.setEnabled(false);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //修改按钮是否可用
                String english = binding.etEnglish.getText().toString().trim();
                String chinese = binding.etChinese.getText().toString().trim();
                binding.btnAdd.setEnabled(!english.isEmpty() && !chinese.isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.etEnglish.addTextChangedListener(textWatcher);
        binding.etChinese.addTextChangedListener(textWatcher);
        wordViewModel= new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        //设置提交按钮点击事件
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //添加单词，并退出界面
                wordViewModel.addWords(new Word(binding.etEnglish.getText().toString(),binding.etChinese.getText().toString()));
                NavController navController= Navigation.findNavController(v);
                navController.navigateUp();
            }
        });
        imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Fragment可见时显示输入法
        binding.etEnglish.requestFocus();
        imm.showSoftInput(binding.etEnglish, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        //不可见时隐藏输入法
        imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }
}