package com.example.kamhomepharmacy;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatBoxDashboard extends AppCompatActivity {

    private TextInputLayout message;
    private TextInputEditText message_edit_text;
    private FloatingActionButton send_button;
    ArrayList<Message> list;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    private DatabaseReference messageRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box_dashboard);

        message = findViewById(R.id.message);
        message_edit_text = findViewById(R.id.message_edit_text);
        send_button = findViewById(R.id.send_button);
        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();

        messageRef = FirebaseDatabase.getInstance().getReference().child("messages");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageContent = message_edit_text.getText().toString().trim();
                if (!TextUtils.isEmpty(messageContent)) {
                    String dateTime = DateFormat.getDateTimeInstance().format(new Date());
                    String messageId = messageRef.push().getKey();
                    String email = getIntent().getStringExtra("email");
                    messageRef.child(messageId).child("content").setValue(messageContent);
                    messageRef.child(messageId).child("timestamp").setValue(dateTime);
                    messageRef.child(messageId).child("sender").setValue(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ChatBoxDashboard.this, "Message Successfully Sent ", Toast.LENGTH_SHORT).show();
                                        message_edit_text.setText("");

                                    } else {
                                        Toast.makeText(ChatBoxDashboard.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        adapter = new RecyclerViewAdapter(this,list);
        LinearLayoutManager llm = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        receiveMessage();
    }

    private void receiveMessage(){
        messageRef.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren() ){
                    Message message = dataSnapshot.getValue(Message.class);
                    list.add(message);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
