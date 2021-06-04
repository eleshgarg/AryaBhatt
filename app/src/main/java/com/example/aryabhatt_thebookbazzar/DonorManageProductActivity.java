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

public class DonorManageProductActivity extends AppCompatActivity {

    private DatabaseReference productref, deleteref, deletereff;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_manage_product);

        setTitle("Your Products");

        Paper.init(this);

        String phoneuser = Paper.book().read("phonenumber");

        loadingbar = new ProgressDialog(this);
        productref = FirebaseDatabase.getInstance().getReference().child("Donate").child(phoneuser);
        deleteref = FirebaseDatabase.getInstance().getReference();
        deletereff = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recycler_menu1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(DonorManageProductActivity.this, DonorActivity.class);
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
//                        Toast.makeText(DonorManageProductActivity.this, "Well done!", Toast.LENGTH_SHORT).show();
//
//
//
//                        AlertDialog.Builder dialog = new AlertDialog.Builder(DonorManageProductActivity.this);
//                        dialog.setMessage("Delete this Product.");
//                        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                String phoneuserr = Paper.book().read("phonenumber");
//                                deleteref.child("Donate").child(phoneuserr).child(model.getDate() + model.getTime()).removeValue();
//                                deletereff.child("ProductsAll").child(phoneuserr + model.getProductname() + model.getActualprice() + model.getCategory()).removeValue();
//                                //String hello = phoneuserr + model.getProductname() + model.getActualprice() + model.getCategory();
//                                Toast.makeText(DonorManageProductActivity.this, "done!", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//                        dialog.create();
//                        dialog.show();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(DonorManageProductActivity.this);
                        dialog.setMessage("Delete this Product.");
                        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String phoneuserr = Paper.book().read("phonenumber");
                                deleteref.child("Donate").child(phoneuserr).child(model.getDate() + model.getTime()).removeValue();
                                deletereff.child("ProductsAll").child(model.getPhonenumber()+ model.getActualprice() + model.getCategory()).removeValue();
                                Toast.makeText(DonorManageProductActivity.this, "Deleted successfully!", Toast.LENGTH_SHORT).show();

                            }
                        });
                        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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