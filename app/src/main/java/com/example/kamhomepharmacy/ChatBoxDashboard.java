package com.example.kamhomepharmacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class ChatBoxDashboard extends AppCompatActivity {

    private TextInputLayout message;
    private TextInputEditText message_edit_text;
    private FloatingActionButton send_button;

    private DatabaseReference messageRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box_dashboard);

        message = findViewById(R.id.message);
        message_edit_text = findViewById(R.id.message_edit_text);
        send_button = findViewById(R.id.send_button);

        messageRef = FirebaseDatabase.getInstance().getReference().child("messages");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = message_edit_text.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    String dateTime = DateFormat.getDateTimeInstance().format(new Date());
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String email = currentUser.getEmail();
                        String messageId = messageRef.push().getKey();
                        messageRef.child(messageId).child("content").setValue(message);
                        messageRef.child(messageId).child("timestamp").setValue(dateTime);
                        messageRef.child(messageId).child("sender").setValue(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            message_edit_text.setText("");
                                        } else {
                                            Toast.makeText(ChatBoxDashboard.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(ChatBoxDashboard.this, "No user is currently logged in", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
