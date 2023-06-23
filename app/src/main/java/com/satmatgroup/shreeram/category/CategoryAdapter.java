package com.satmatgroup.shreeram.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.myapp.onlysratchapp.category.CategoryResponse;
import com.satmatgroup.shreeram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder>  {
    ArrayList<CategoryResponse> arrayList;
    Context context;
    View.OnClickListener itemClick;

    public CategoryAdapter(ArrayList<CategoryResponse> arrayList, Context context, View.OnClickListener itemClick) {
        this.arrayList = arrayList;
        this.context = context;
        this.itemClick=itemClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_dashboard,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        CategoryResponse model=arrayList.get(i);

        myHolder.tvTitle.setText(model.getName());

    if (!model.getImage().equals(""))
        {
            Picasso.get().load(model.getImage()).into(myHolder.imgIcon);
        }else {
       // Picasso.get().load(R).into(myHolder.imgIcon);
    }



        myHolder.card_lay.setTag(i);
        myHolder.card_lay.setOnClickListener(itemClick);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgIcon;
        public CardView card_lay;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            card_lay=itemView.findViewById(R.id.card_lay);
            imgIcon=itemView.findViewById(R.id.img_icon);
            tvTitle=itemView.findViewById(R.id.text_title);

        }
    }


}

