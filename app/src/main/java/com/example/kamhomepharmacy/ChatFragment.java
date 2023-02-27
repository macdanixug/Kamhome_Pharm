package com.example.kamhomepharmacy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class ChatFragment extends Fragment {

    private TextInputLayout message;
    private TextInputEditText message_edit_text;
    private FloatingActionButton send_button;
    ArrayList<Message> list;
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;

    private DatabaseReference messageRef;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        message = view.findViewById(R.id.message);
        message_edit_text = view.findViewById(R.id.message_edit_text);
        send_button = view.findViewById(R.id.send_button);
        recyclerView = view.findViewById(R.id.recyclerView);
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
                                        Toast.makeText(getActivity(), "Message Successfully Sent ", Toast.LENGTH_SHORT).show();
                                        message_edit_text.setText("");

                                    } else {
                                        Toast.makeText(getActivity(), "Failed to send message", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        adapter = new RecyclerViewAdapter(getActivity(),list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
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