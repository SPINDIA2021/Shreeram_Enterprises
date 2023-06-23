package com.satmatgroup.shreeram.category;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.myapp.onlysratchapp.category.CategoryResponse;
import com.satmatgroup.shreeram.R;
import com.satmatgroup.shreeram.network.Injection;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {
    private CategoryContract.Presenter presenter;
    RecyclerView recyclerViewCategory;
    WebView webView;
    ImageView imgBack;
    ArrayList<CategoryResponse> categoryResponseArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();

    }

    private void initView() {
        recyclerViewCategory=findViewById(R.id.recyclerview_category);
        imgBack = findViewById(R.id.ivBackBtn);
        webView=findViewById(R.id.webview_category);

        recyclerViewCategory.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        new CategoryPresenter(Injection.provideLoginRepository(this), this);

        presenter.getCategory(this);
    }


    @Override
    public void categoryResponse(ArrayList<CategoryResponse> categoryResponse) {

        if (categoryResponse.size()!=0)
        {
            categoryResponseArrayList=categoryResponse;
            recyclerViewCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
            CategoryAdapter adapter=new CategoryAdapter(categoryResponse, getApplicationContext(),itemClick);
            recyclerViewCategory.setAdapter(adapter);
        }

    }

    @Override
    public void retryResponse(String retryResponse) {

    }

    View.OnClickListener itemClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int i= (int) v.getTag();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(categoryResponseArrayList.get(i).getUrl())));
           /* Intent intent = new Intent(getApplicationContext(), InnerCategoryActivity.class);
            intent.putExtra("url",categoryResponseArrayList.get(i).getUrl());
            startActivity(intent);*/
        }
    };

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        this.presenter=presenter;
    }
}