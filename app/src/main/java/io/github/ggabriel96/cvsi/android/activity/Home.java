package io.github.ggabriel96.cvsi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.background.NetworkListener;

public class Home extends AppCompatActivity {

  protected static final FirebaseAuth auth = FirebaseAuth.getInstance();
  protected static final NetworkListener networkListener = new NetworkListener();
  private static final String TAG = "Home";
  private static final int LOGIN_REQUEST = 1;
  private FirebaseUser user;
  private FirebaseAuth.AuthStateListener authListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Home.networkListener.register(this);

    this.authListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Home.this.user = firebaseAuth.getCurrentUser();
        if (Home.this.user != null) {
          // User is signed in
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
    if (requestCode == Home.LOGIN_REQUEST) {
      if (resultCode != Login.RESULT_OK) this.finish();
    } else {
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
  }
}
