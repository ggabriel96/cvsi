package io.github.ggabriel96.cvsi.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.background.NetworkListener;

public class Home extends AppCompatActivity {

  protected static final FirebaseAuth auth = FirebaseAuth.getInstance();
  protected static final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
  protected static final FirebaseStorage storage = FirebaseStorage.getInstance();
  protected static final NetworkListener networkListener = new NetworkListener();

  private static final String TAG = "Home";
  private static final int PICK_PHOTO = 2;
  private static final int LOGIN_REQUEST = 1;

  private FirebaseUser user;
  private StorageReference storageRef;
  private FirebaseAuth.AuthStateListener authListener;

  /*
   * https://firebase.google.com/docs/database/android/read-and-write
   * https://firebase.google.com/docs/reference/android/com/google/firebase/database/DatabaseReference
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Home.networkListener.register(this);
    this.storageRef = Home.storage.getReferenceFromUrl("gs://cvsi-backend.appspot.com");

    this.authListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Home.this.user = firebaseAuth.getCurrentUser();
        if (Home.this.user != null) {
          // User is signed in
          /**
           * @TODO this is being called more than once when already logged in!?
           */
          Log.d(TAG, "onAuthStateChanged:signed_in: " + Home.this.user.getEmail() + "(" + Home.this.user.getUid() + ")");
          Home.this.init();
        } else {
          // User is signed out
          Log.d(TAG, "onAuthStateChanged:signed_out");
          Home.this.startActivityForResult(new Intent(Home.this, Login.class), Home.LOGIN_REQUEST);
        }
      }
    };
  }

  @Override
  public void onStart() {
    super.onStart();
    Home.auth.addAuthStateListener(this.authListener);
  }

  @Override
  public void onStop() {
    super.onStop();
    if (this.authListener != null) Home.auth.removeAuthStateListener(this.authListener);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (Home.networkListener != null) networkListener.unregister(this);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "onActivityResult");
    switch (requestCode) {
      case Home.LOGIN_REQUEST:
        if (resultCode != Home.RESULT_OK) this.finish();
        break;
      case Home.PICK_PHOTO:
        if (resultCode == Home.RESULT_OK) this.uploadPicture(data);
        break;
      default:
        super.onActivityResult(requestCode, resultCode, data);
    }
  }

  private void init() {
    this.setContentView(R.layout.activity_home);
    this.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Home.auth.signOut();
      }
    });
    this.findViewById(R.id.upload_photo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (Home.networkListener.isOnline()) {
          Intent pickPhoto = new Intent(Intent.ACTION_PICK);
          pickPhoto.setType("image/*");
          Home.this.startActivityForResult(pickPhoto, Home.PICK_PHOTO);
        } else {
          Toast.makeText(Home.this, R.string.disconnected, Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void uploadPicture(Intent data) {
    Uri imageUri = data.getData();
    final String pictureLocation = "images/" + this.user.getUid() + "/" + imageUri.getLastPathSegment();
    StorageReference imageRef = this.storageRef.child(pictureLocation);
    Toast.makeText(Home.this, R.string.upload_started, Toast.LENGTH_SHORT).show();
    imageRef.putFile(imageUri)
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          Log.e(TAG, "Image upload failed.", exception);
          Toast.makeText(Home.this, R.string.upload_failed, Toast.LENGTH_SHORT).show();
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Home.db.child(pictureLocation).setValue(taskSnapshot.getMetadata().getPath());
        Log.d(TAG, "Upload successful, URL: " + taskSnapshot.getMetadata().getPath());
        Toast.makeText(Home.this, R.string.upload_successful, Toast.LENGTH_SHORT).show();
      }
    });
  }
}
