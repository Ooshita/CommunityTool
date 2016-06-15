package com.whispon.communitytool;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by noriakioshita on 2016/06/10.
 */
public class MorphAnalysis extends AppCompatActivity {
    static String excludeWord = null;
    //記事のタイトル
    ArrayList<String> titleList;
    //形態素解析後の名詞を入れるリスト
    ArrayList<String> meishiList;

    //形態素解析を行う
    public ArrayList<String> morphemeAnalysis(Context context,String topicTitle) {
        Tokenizer tokenizer = new Tokenizer();
        //記事のタイトルを形態素解析をする
        List<Token> tokens =tokenizer.tokenize(topicTitle);
        meishiList = new ArrayList<String>();

        for (Token token : tokens) {
            //形態素解析器によって分割された単語を入手
            String word = token.getPartOfSpeechLevel1();
            if (word.equals("名詞")) {
                meishiList.add(token.getSurface());
            }
        }
        return meishiList;
    }

}
