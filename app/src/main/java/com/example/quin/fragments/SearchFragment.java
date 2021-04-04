package com.example.quin.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quin.AdminLogin;
import com.example.quin.ProductViewHolder;
import com.example.quin.Products;
import com.example.quin.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class SearchFragment extends Fragment {
    View myFragment;
    private EditText searchEdit;
    private DatabaseReference ProductRefs;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private ImageView searchImg;
    private String input;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment= inflater.inflate(R.layout.fragment_search, container, false);
        ProductRefs= FirebaseDatabase.getInstance().getReference().child("Posts");
        recyclerView=myFragment.findViewById(R.id.search_rv);
        searchImg=myFragment.findViewById(R.id.search_img);
        searchEdit=myFragment.findViewById(R.id.search_edt_txt);
        input=searchEdit.getText().toString().toLowerCase();
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductRefs.orderByChild("search").startAt(input),Products.class)
                        .build();
                FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {
                        productViewHolder.textproductName.setText(products.getUname());
                        productViewHolder.textProductInfo.setText(products.getPcaption());
                        if (products.getImage()==null){
                            productViewHolder.ImageCard.setVisibility(View.INVISIBLE);
                            productViewHolder.noImg.setVisibility(View.VISIBLE);
                        }
                        productViewHolder.txtProductPrice.setText(products.getLocation());
                        Picasso.get().load(products.getImage()).into(productViewHolder.ProductImage);
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item,parent,false);
                        ProductViewHolder holder=new ProductViewHolder(view);
                        return holder;
                    }
                };
                recyclerView.setAdapter(adapter);
                adapter.startListening();

            }
        });
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return myFragment;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductRefs,Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull final Products products) {
                productViewHolder.textproductName.setText(products.getUname());
                productViewHolder.textProductInfo.setText(products.getPcaption());
                if (products.getImage()==null){
                    productViewHolder.ImageCard.setVisibility(View.INVISIBLE);
                    productViewHolder.noImg.setVisibility(View.VISIBLE);
                }
                productViewHolder.txtProductPrice.setText(products.getLocation());
                Picasso.get().load(products.getImage()).into(productViewHolder.ProductImage);
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}