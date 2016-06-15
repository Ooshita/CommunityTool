package com.whispon.communitytool;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by noriakioshita on 2016/06/10.
 */
public class SportActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> sportList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport);

        context = this;
        listView = (ListView) findViewById(R.id.sportListView);

        Thread thread = new Thread(() -> {
            try {
                XmlParser xmlParser = new XmlParser();
                xmlParser.getSelectXml("http://news.yahoo.co.jp/pickup/sports/rss.xml");
                sportList = xmlParser.getList();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            //取得までに時間がかかるため
            Thread.sleep(3000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,sportList);
        listView.setAdapter(adapter);


        // リストビューのアイテムがクリックされた時に呼び出されるコールバックリスナーを登録します
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                String item = (String) listView.getItemAtPosition(position);
                Toast.makeText(SportActivity.this, item, Toast.LENGTH_LONG).show();
                System.out.println("item: "+ item);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    MorphAnalysis morphAnalysis = new MorphAnalysis();
                    //meishiList = morphAnalysis.morphemeAnalysis(item);
                    System.out.println("解析結果！: " + morphAnalysis.morphemeAnalysis(context,item));
                });


            }
        });

    }
}
