package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {

    private DatabaseReference productref,deleteref;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setTitle("Cart");

        Paper.init(this);

        String phoneuser = Paper.book().read("phonenumber");

        loadingbar = new ProgressDialog(this);
        productref = FirebaseDatabase.getInstance().getReference().child("Cart").child(phoneuser);
        deleteref = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_menu1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(CartActivity.this, HomeActivity.class);
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

                        AlertDialog.Builder dialog = new AlertDialog.Builder(CartActivity.this);
                        dialog.setTitle("Order this Product");
                        dialog.setMessage(model.getQuantity() + " Piece/s");
                        dialog.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String phoneuserr = Paper.book().read("phonenumber");

                                Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
                                intent.putExtra("name", model.getProductname());
                                intent.putExtra("desp", model.getDescription());
                                intent.putExtra("price", model.getPrice());
                                intent.putExtra("date", model.getDate());
                                intent.putExtra("time", model.getTime());
//                                intent.putExtra("image1", model.getImage1());
//                                intent.putExtra("image2", model.getImage2());
//                                intent.putExtra("image3", model.getImage3());
                                intent.putExtra("image4", model.getImage4());
                                intent.putExtra("phone", model.getPhonenumber());
                                intent.putExtra("quantity",model.getQuantity());
                                intent.putExtra("category",model.getCategory());
                                startActivity(intent);

                                //Toast.makeText(CartActivity.this, "done!", Toast.LENGTH_SHORT).show();

                            }
                        });
                        dialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String phoneuserr = Paper.book().read("phonenumber");
                                deleteref.child("Cart").child(phoneuserr).child(model.getPhonenumber() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime()).removeValue();
                                //String message = model.getPhonenumber() + model.getProductname() + model.getPrice() + model.getDate() + model.getTime();
                                //Toast.makeText(CartActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        dialog.create();
                        dialog.show();



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