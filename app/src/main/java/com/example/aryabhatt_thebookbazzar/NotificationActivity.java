package com.example.aryabhatt_thebookbazzar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class NotificationActivity extends AppCompatActivity {

    private DatabaseReference notiref;
    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setTitle("Notifications");

        Paper.init(this);

        String phoneuser = Paper.book().read("phonenumber");

        notiref = FirebaseDatabase.getInstance().getReference().child("Notifications").child(phoneuser);

        mRecyclerView = findViewById(R.id.recycler_menu2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Notifications> options = new FirebaseRecyclerOptions.Builder<Notifications>()
                .setQuery(notiref, Notifications.class)
                .build();

        FirebaseRecyclerAdapter<Notifications, NotificationsViewHolder> adapter = new FirebaseRecyclerAdapter<Notifications, NotificationsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position, @NonNull Notifications model) {
                holder.titles.setText(model.getTitles());
                holder.contents.setText(model.getContents());
            }

            @NonNull
            @Override
            public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);

                NotificationsViewHolder holder = new NotificationsViewHolder(view);

                return holder;
            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(NotificationActivity.this, HomeActivity.class);
        startActivity(intent);

    }

}