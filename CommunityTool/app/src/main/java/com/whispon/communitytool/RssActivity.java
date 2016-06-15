package com.whispon.communitytool;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import android.os.Handler;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
/**
 * Created by noriaki_oshita on 16/05/25.
 */
public class RssActivity extends AppCompatActivity {
    static String excludeWord = null;
    ArrayList<String> titleList;
    private Handler handler;
    TextView text;
    ArrayList<String> meishiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_activity);
        //縦画面固定する
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        handler = new Handler();

        text = (TextView) findViewById(R.id.textView);
        Thread thread = new Thread(() -> {
            try {
                XmlParser xml = new XmlParser();
                XmlParser.getXml();
                titleList = xml.getList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        handler.post(() -> {
                text.setText("");
        });


    }

    //形態素解析を行う
    private ArrayList morphemeAnalysis(int titleNumber) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens =tokenizer.tokenize(titleList.get(titleNumber));
        meishiList = new ArrayList<String>();

        for (Token token : tokens) {
            //形態素解析器によって分割された単語を入手
            meishiList.add(token.getSurface());
        }
        excludeWord = getFile(getApplicationContext());

        //カンマごとに分割して配列に代入
        String[] joshiArr = excludeWord.split(",",0);

        for(int i=0;i<joshiArr.length;i++) {
            //excludeWord.txtの中身を表示
            //System.out.println(joshiArr[i]);
            Iterator iterator = meishiList.iterator();
            while (iterator.hasNext()) {
                String tango = (String) iterator.next();
                if (tango.equals(joshiArr[i])) {
                    iterator.remove();
                }
            }
        }
        //すべてのリストの空白を含むリストを削除
        meishiList.removeAll(Collections.singleton(" "));

        return meishiList;
    }


    //助詞の定義をgetする。
    private static String getFile(Context context) {
        try {
            InputStream file = context.getResources().openRawResource(R.raw.exclude_word);
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            excludeWord = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excludeWord;
    }


}




