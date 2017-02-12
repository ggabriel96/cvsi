package io.github.ggabriel96.cvsi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.ggabriel96.cvsi.android.R;

public class Home extends AppCompatActivity {

  private static final String TAG = "Home";

  protected static FirebaseAuth auth;

  private FirebaseUser user;
  private FirebaseAuth.AuthStateListener authListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Home.auth = FirebaseAuth.getInstance();
    this.authListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Home.this.user = firebaseAuth.getCurrentUser();
        if (user != null) {
          // User is signed in
          Log.d(TAG, "onAuthStateChanged:signed_in: " + Home.this.user.getEmail() + "(" + Home.this.user.getUid() + ")");
          Home.this.init();
        } else {
          // User is signed out
          Log.d(TAG, "onAuthStateChanged:signed_out");
          Home.this.startActivity(new Intent(Home.this, Login.class));
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

  private void init() {
    this.setContentView(R.layout.activity_home);
    ((Button) this.findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Home.auth.signOut();
      }
    });
  }
}
