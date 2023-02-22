package com.example.kamhomepharmacy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Signup extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword;
    ImageView image;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    StorageReference storageReference;
    int Reqeust_Code = 10;
    private Uri imageUri;
    ProgressBar progressBar;

    private static final int IMAGE_PICK_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        image = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            image.setImageURI(imageUri);
                        }else{
                            Toast.makeText(Signup.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(imageUri != null){
//                   uploadTOFirebase(imageUri);
//                }else{
//                    Toast.makeText(Signup.this, "Please Select Image", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();

                HelperClass helperClass = new HelperClass(name, email, username, password);
                reference.child(username).setValue(helperClass);

                Toast.makeText(Signup.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean pick = true;
                if(pick == true){
                //pick from camera
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else pickImage();
                }
                else{
                //pick from gallery
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else pickImage();


                }

            }
        });

    }

    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            if (data == null) {
                throw new NullPointerException("data object is null");
            }

            Uri imageUri = data.getData();
            image.setImageURI(imageUri);

        }
    }
//
//    private void uploadTOFirebase(Uri uri){
//        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
//
//        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        HelperClass helperClass = new HelperClass(uri.toString(),name, email, username, password);
//                        String key = reference.push().getKey();
//                        reference.child(key).setValue(dataClass);
//                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(Signup.this, "Upload Sucessful", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(Signup.this, "Upload Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private String getFileExtension(Uri fileUri){
//        ContentResolver contentResolver = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mine.getExtensionFromMimeType(contentResolver.getType(fileUri));
//    }




}