package com.example.quin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textproductName,textProductInfo,txtProductPrice,noImg;
    public ImageView ProductImage;
    public CardView ImageCard;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        ImageCard=(CardView)itemView.findViewById(R.id.img_card);
        ProductImage=(ImageView)itemView.findViewById(R.id.cardimg);
        textproductName=(TextView) itemView.findViewById(R.id.card_title);
        noImg=(TextView) itemView.findViewById(R.id.card_noImage);
        textProductInfo=(TextView)itemView.findViewById(R.id.card_dist);
        txtProductPrice=(TextView)itemView.findViewById(R.id.card_people);
    }

    @Override
    public void onClick(View v) {

    }
}
