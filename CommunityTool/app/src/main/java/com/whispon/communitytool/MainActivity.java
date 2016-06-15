package com.whispon.communitytool;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.*;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> rankingList = new ArrayList<>();
    ArrayList<String> sportNewsList = new ArrayList<>();
    ArrayList<String> japanNewsList = new ArrayList<>();
    ArrayList<String> entemeList = new ArrayList<>();
    ArrayList<String> globalNewsList = new ArrayList<>();
    ArrayList<String> computerNewsList = new ArrayList<>();
    ArrayList<String> keizaiNewsList = new ArrayList<>();
    ArrayList<String> yuruiNewsList = new ArrayList<>();

    private Button topicButton1;
    private Button topicButton2;
    private Button topicButton3;
    private Button topicButton4;
    private Button topicButton5;
    private Button topicButton6;
    private Button topicButton7;
    private Button topicButton8;

    private Handler handler;

    static XmlParser xmlParser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //縦画面固定する.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //本当はListViewにした方がいい。時間がないので今回は割愛
        topicButton1 = (Button) findViewById(R.id.ranking);
        topicButton2 = (Button) findViewById(R.id.sport);
        topicButton3 = (Button) findViewById(R.id.japanese_news);
        topicButton4 = (Button) findViewById(R.id.entame);
        topicButton5 = (Button) findViewById(R.id.global_news);
        topicButton6 = (Button) findViewById(R.id.computer);
        topicButton7 = (Button) findViewById(R.id.keizai_news);
        topicButton8 = (Button) findViewById(R.id.netlab);


    }

    public void onClick(View v) {
        if(v.getId() == R.id.ranking){
            Intent intent = new Intent(this, RankingActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.sport) {
            Intent intent = new Intent(this, SportActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.japanese_news) {
            Intent intent = new Intent(this, JapanNewsActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.entame) {
            Intent intent = new Intent(this, EntameActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.global_news) {
            Intent intent = new Intent(this, GlobalNewsActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.computer) {
            Intent intent = new Intent(this, ComputerActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.keizai_news) {
            Intent intent = new Intent(this, KeizaiActivity.class);
            startActivity(intent);

        }else if(v.getId() == R.id.netlab) {
            Intent intent = new Intent(this,NetlabActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.backToTitle) {
            setupTopic();
        }

    }

    private void setupTopic() {
        handler = new Handler();
        handler.post(() -> {
            topicButton1.setText("ランキング");
            topicButton2.setText("スポーツ");
            topicButton3.setText("国内ニュース");
            topicButton4.setText("エンターテインメント");
            topicButton5.setText("海外ニュース");
            topicButton6.setText("コンピュータ");
            topicButton7.setText("経済");
            topicButton8.setText("ゆるねた");
        });
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
