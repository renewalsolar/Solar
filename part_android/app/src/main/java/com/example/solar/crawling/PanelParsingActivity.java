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

public class PanelParsingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PanelItemObject> list = new ArrayList();
    private int pagingIndex = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!recyclerView.canScrollVertically(1)) {
                    pagingIndex++;
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

            progressDialog = new ProgressDialog(PanelParsingActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://search.shopping.naver.com/search/all.nhn?origQuery=%ED%83%9C%EC%96%91%EA%B4%91%ED%8C%A8%EB%84%90&pagingIndex="+pagingIndex+"&pagingSize=40&viewType=list&sort=rel&frm=NVSHPAG&query=%ED%83%9C%EC%96%91%EA%B4%91%ED%8C%A8%EB%84%90").get();
                Elements mElementDataSize = doc.select("ul[class=goods_list]").select("li[class=_itemSection]"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for(Element elem : mElementDataSize){ //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.select("div[class=info] div[class=tit] a").text();
                    String my_link = elem.select("div[class=info] div[class=tit] a").attr("href");
                    String my_imgUrl = elem.select("div[class=img_area] a img[class=_productLazyImg]").attr("data-original");
                    String my_contents = elem.select("div[class=info] span[class=price] em").text();
                    //Log.d("test", "test" + mTitle);
                    //ArrayList에 계속 추가한다.
                    list.add(new PanelItemObject(my_title, my_imgUrl, my_link, my_contents));
              }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            PanelParsingAdapter myAdapter = new PanelParsingAdapter(list, getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
            recyclerView.scrollToPosition(list.size()-42);
            progressDialog.dismiss();
        }
    }
}