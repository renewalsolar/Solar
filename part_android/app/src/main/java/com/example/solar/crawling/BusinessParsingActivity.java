package com.example.solar.crawling;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solar.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BusinessParsingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ItemObject> list = new ArrayList();
    private int executeCnt = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (!recyclerView.canScrollVertically(1)) {
                    //AsyncTask 작동시킴(파싱)
                    executeCnt += 10;

                    new Description().execute();
                }
            }
        });
        new Description().execute();
    }


    private class Description extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(BusinessParsingActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://www.google.com/search?q=%ED%83%9C%EC%96%91%EA%B4%91+%EC%A0%95%EB%B6%80%EC%A7%80%EC%9B%90%EC%82%AC%EC%97%85&ei=bwK5Xf7VEvTGmAXf1oPIBw&start=" + executeCnt + "&sa=N&ved=0ahUKEwi-yZurhMPlAhV0I6YKHV_rAHk4ChDy0wMIiwE&biw=522&bih=578").get();
                Elements mElementDataSize = doc.select("div[class=srg]").select("div[class=rc]"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for (Element elem : mElementDataSize) {
                    String my_title = elem.select("div[class=r] h3").text();
                    String my_link = elem.select("div[class=r] a").attr("href");
                    String my_contents = elem.select("div[class=s]").text();
                    list.add(new ItemObject(my_title, my_link, my_contents));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            BusinessParsingAdapter myAdapter = new BusinessParsingAdapter(list, getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
            recyclerView.scrollToPosition(list.size()-17);
            progressDialog.dismiss();
        }
    }
}
