package com.babousn.mobileproject.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.babousn.mobileproject.R;
import com.babousn.mobileproject.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Register extends AppCompatActivity {
    // firebase dep
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    // binding
    private ActivityRegisterBinding binding;
    // var
    Bitmap selectedImage;
    Uri imageData;
    // launchers
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    // user info
    String rEmail, rPassword, rUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // start launchers
        registerLauncher();
        // firebase initials
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void selectImage(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Give Permission", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                            }
                        }).show();
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGallery);

        }

    }

    public void registerLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intentFromResult = result.getData();
                            if (intentFromResult != null) {
                                imageData = intentFromResult.getData();
                                try {
                                    if (Build.VERSION.SDK_INT >= 28) {
                                        ImageDecoder.Source source = ImageDecoder
                                                .createSource(Register.this.getContentResolver(), imageData);
                                        selectedImage = ImageDecoder.decodeBitmap(source);
                                        binding.uploadimage.setImageBitmap(selectedImage);

                                    } else {
                                        selectedImage = MediaStore.Images.Media
                                                .getBitmap(Register.this.getContentResolver(), imageData);
                                        binding.uploadimage.setImageBitmap(selectedImage);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            // perm allow
                            Intent intentToGallery = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            activityResultLauncher.launch(intentToGallery);

                        } else {
                            // perm deny
                            Toast.makeText(Register.this, "Permisson needed!", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    public void signUp() {

        firebaseAuth.createUserWithEmailAndPassword(rEmail, rPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Toast.makeText(Register.this, "User Created", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(Register.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void uploadButtonClicked(View view) {
        // user datas
        rEmail = binding.emailEdittext.getText().toString();
        rPassword = binding.passwordEdittext.getText().toString();
        rUsername = binding.usernameEdittext.getText().toString();

        if (imageData != null) {
            UUID uuid = UUID.randomUUID();
            String imageurlpath = "images/profile/" + uuid + ".png";
            storageReference.child(imageurlpath).putFile(imageData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // get download url
                            StorageReference newRef = firebaseStorage.getReference(imageurlpath);
                            newRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    HashMap<String, Object> userdataP = new HashMap<>();
                                    userdataP.put("useremail", rEmail);
                                    userdataP.put("imageurl", downloadUrl);
                                    userdataP.put("username", rUsername);
                                    firebaseFirestore.collection("Users").add(userdataP)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    // all successed
                                                    signUp();
                                                    Toast.makeText(Register.this, "Uploaded", Toast.LENGTH_SHORT)
                                                            .show();
                                                    Intent intent = new Intent(Register.this, Login.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(Exception e) {
                                                    // collection push error
                                                    Toast.makeText(Register.this,
                                                            "collection push error: " + e.getLocalizedMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    // get image url from database error
                                    Toast.makeText(Register.this,
                                            "get image url from database error: " + e.getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            // image upload error
                            Toast.makeText(Register.this, "image upload error" + e.getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}