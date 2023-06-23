package com.satmatgroup.shreeram.category;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import com.myapp.onlysratchapp.category.CategoryResponse;

import com.satmatgroup.shreeram.R;
import com.satmatgroup.shreeram.network.Injection;

import java.util.ArrayList;

public class InnerCategoryActivity extends AppCompatActivity implements CategoryContract.View {
    private CategoryContract.Presenter presenter;
    RecyclerView recyclerViewCategory;
    WebView webView;

    ImageView imgBack;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();

    }

    private void initView() {
        recyclerViewCategory=findViewById(R.id.recyclerview_category);
        webView=findViewById(R.id.webview_category);
        imgBack = findViewById(R.id.ivBackBtn);
        url=getIntent().getStringExtra("url");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerViewCategory.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);

        new CategoryPresenter(Injection.provideLoginRepository(this), this);

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

        webView.loadUrl(url);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);


        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
    }



    @Override
    public void categoryResponse(ArrayList<CategoryResponse> categoryResponse) {



    }

    @Override
    public void retryResponse(String retryResponse) {

    }


    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        this.presenter=presenter;
    }
}