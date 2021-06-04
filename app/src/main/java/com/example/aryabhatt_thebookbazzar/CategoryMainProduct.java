package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class CategoryMainProduct extends AppCompatActivity {

    private DatabaseReference productref;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_main_product);

        setTitle("Products");

        Paper.init(this);

        String phoneuser = Paper.book().read("phonenumber");

        final String categorye = getIntent().getStringExtra("category");
        productref = FirebaseDatabase.getInstance().getReference().child(categorye);

        mRecyclerView = findViewById(R.id.recycler_menu1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(CategoryMainProduct.this, MainCategory.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(productref, Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductsViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull final Products model) {

                holder.showname.setText(model.getProductname());
                holder.showdesp.setText(model.getDescription());
                holder.showprice.setText(model.getPrice() + "Rs");
                holder.showdate.setText(model.getDate());
                holder.showtime.setText(model.getTime());

                Picasso.get().load(model.getImage4()).into(holder.showimage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(CategoryMainProduct.this, ProductDetailsActivity.class);
                        intent.putExtra("name", model.getProductname());
                        intent.putExtra("desp", model.getDescription());
                        intent.putExtra("price", model.getPrice());
                        intent.putExtra("date", model.getDate());
                        intent.putExtra("time", model.getTime());
                        intent.putExtra("image1", model.getImage1());
                        intent.putExtra("image2", model.getImage2());
                        intent.putExtra("image3", model.getImage3());
                        intent.putExtra("image4", model.getImage4());
                        intent.putExtra("phone", model.getPhonenumber());
                        startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);

                ProductsViewHolder holder = new ProductsViewHolder(view);

                return holder;
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}