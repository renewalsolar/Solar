package com.example.solar.addressApi;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solar.R;
import com.example.solar.network.Config;

public class AddressApiActivity extends AppCompatActivity {

    private WebView browser;
    private final String SCRIPT_FUNC = "javascript:sample2_execDaumPostcode();";

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            String address = data;
            Intent result = new Intent();
            result.putExtra("address", address.toString());

            // 자신을 호출한 Acstivity로 데이터를 보낸다.
            setResult(RESULT_OK, result);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        browser = (WebView) findViewById(R.id.webView);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        browser.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                browser.loadUrl(SCRIPT_FUNC);
            }
        });

        browser.loadUrl(Config.MAIN_URL+Config.GET_MAP_PHP);
    }
}