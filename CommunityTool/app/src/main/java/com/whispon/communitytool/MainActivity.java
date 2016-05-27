package com.whispon.communitytool;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ThreadFactoryBuilder;


import java.io.IOException;
import java.util.*;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    Multimap<String,String> topicMap = ArrayListMultimap.create();
    ArrayList<String> entameList = new ArrayList<>();
    ArrayList<String> foodList = new ArrayList<>();
    ArrayList<String> newsList = new ArrayList<>();
    ArrayList<String> rankingList = new ArrayList<>();
    private Button topicButton0;
    private Button topicButton1;
    private Button topicButton2;
    private Button topicButton3;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //縦画面固定する.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        topicButton0 = (Button) findViewById(R.id.ranking);
        topicButton1 = (Button) findViewById(R.id.food);
        topicButton2 = (Button) findViewById(R.id.entame);
        topicButton3 = (Button) findViewById(R.id.news);


        setupTopic();
        makeTopics();



    }

    private void makeTopics() {
        topicMap.put("食事","昨日は何食べたの？");
        topicMap.put("食事","マックのメニューでは何が好き？");
        topicMap.put("食事","最近料理にはまってるんだよね");
        topicMap.put("エンタメ","最近の映画でおもしろいのある？");
        topicMap.put("エンタメ","ゾンビ系の映画見ない？");
        topicMap.put("エンタメ","芸能人で誰が好きなの？");
        topicMap.put("時事","選挙に行った？");
        topicMap.put("時事","もうすぐ増税だよね");
        topicMap.put("時事","北海道新幹線乗った？");
    }

    private void setupTopic() {
        topicButton1.setText("食事");
        topicButton2.setText("エンタメ");
        topicButton3.setText("時事");
    }

    private void food() {
        foodList.addAll(topicMap.get("食事"));
        topicButton1.setText(foodList.get(0));
        topicButton2.setText(foodList.get(1));
        topicButton3.setText(foodList.get(2));
    }

    private void entame() {
        entameList.addAll(topicMap.get("エンタメ"));
        topicButton1.setText(entameList.get(0));
        topicButton2.setText(entameList.get(1));
        topicButton3.setText(entameList.get(2));
    }

    private void news() {
        newsList.addAll(topicMap.get("時事"));
        topicButton1.setText(newsList.get(0));
        topicButton2.setText(newsList.get(1));
        topicButton3.setText(newsList.get(2));
    }
    public void onClick(View v) {
        if(v.getId() == R.id.ranking){
            handler = new Handler();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        XmlParser xmlParser = new XmlParser();
                        xmlParser.getSelectXml("http://searchranking.yahoo.co.jp/rss/burst_ranking-rss.xml");
                        rankingList = xmlParser.getList();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println(rankingList);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    topicButton0.setText(rankingList.get(0));
                    topicButton1.setText(rankingList.get(1));
                    topicButton2.setText(rankingList.get(2));
                    topicButton3.setText(rankingList.get(3));
                }
            });
        } else if(v.getId() == R.id.entame) {
            entame();
        }else if(v.getId() == R.id.food) {
            food();
        }else if(v.getId() == R.id.news) {
            //news();
            Intent intent = new Intent(MainActivity.this, RssActivity.class);
            startActivity(intent);
            //news();
        }else {
            setupTopic();
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //戻るキーが押されたら
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            setupTopic();
            //trueを返すとActivityが閉じられない
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
