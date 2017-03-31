package io.github.ggabriel96.cvsi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.background.NetworkListener;
import io.github.ggabriel96.cvsi.android.camera.ShootingActivity;
import io.github.ggabriel96.cvsi.android.fragment.Albums;
import io.github.ggabriel96.cvsi.android.fragment.Profile;
import io.github.ggabriel96.cvsi.android.util.EntityConverter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Home extends AppCompatActivity {

  public static final FirebaseAuth auth = FirebaseAuth.getInstance();
  public static final EntityConverter entityConverter = new EntityConverter();
  public static final NetworkListener networkListener = new NetworkListener();
  public static final FirebaseStorage storage = FirebaseStorage.getInstance();

  private static final String TAG = "Home";
  private static final int LOGIN_REQUEST = 1;
  private static final int PICK_PHOTO_REQUEST = 2;

  private Albums albums;
  private Profile profile;
  private FirebaseUser firebaseUser;
  private Integer currentFragmentId;
  private FragmentManager fragmentManager;
  private FirebaseAuth.AuthStateListener authListener;

  /*
   * https://firebase.google.com/docs/database/android/read-and-write
   * https://firebase.google.com/docs/reference/android/com/google/firebase/database/DatabaseReference
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Home.networkListener.register(this);
    this.fragmentManager = this.getSupportFragmentManager();
    this.authListener = new FirebaseAuth.AuthStateListener() {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Home.this.firebaseUser = firebaseAuth.getCurrentUser();
        if (Home.this.firebaseUser != null) {
          // User is signed in
          /**
           * @TODO this is being called more than once when already logged in!?
           */
          Log.d(TAG, "onAuthStateChanged:signed_in: " + Home.this.firebaseUser.getEmail() + " (" + Home.this.firebaseUser.getUid() + ")");
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
      case Home.PICK_PHOTO_REQUEST:
        if (resultCode == Home.RESULT_OK) {
          Toast.makeText(Home.this, "Menu placeholder", Toast.LENGTH_SHORT).show();
        }
        break;
      default:
        super.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.upload_picture:
        if (Home.networkListener.isOnline()) {
          Intent pickPhoto = new Intent(Intent.ACTION_PICK);
          pickPhoto.setType("image/*");
          this.startActivityForResult(pickPhoto, Home.PICK_PHOTO_REQUEST);
        } else {
          Toast.makeText(this, R.string.disconnected, Toast.LENGTH_SHORT).show();
        }
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void init() {
    this.instantiateFragments();
    this.setContentView(R.layout.activity_home);
    this.setupBottomNavigation();
    this.showInitialFragment();
  }

  private void instantiateFragments() {
    this.albums = new Albums();
    this.profile = new Profile();
  }

  private void setupBottomNavigation() {
    BottomNavigationView bottomNavigationView = (BottomNavigationView) this.findViewById(R.id.bottom_navigation);
    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (Home.this.currentFragmentId == item.getItemId()) return false;
        Home.this.currentFragmentId = item.getItemId();
        switch (item.getItemId()) {
          case R.id.bottom_navigation_albums:
            Home.this.replaceCurrentFragmentWith(Home.this.albums);
            break;
          case R.id.bottom_navigation_camera:
            Intent camera = new Intent(Home.this, ShootingActivity.class);
            Home.this.startActivity(camera);
            break;
          case R.id.bottom_navigation_profile:
            Home.this.replaceCurrentFragmentWith(Home.this.profile);
            break;
        }
        return true;
      }
    });
  }

  private void showInitialFragment() {
    this.replaceCurrentFragmentWith(this.albums);
    this.currentFragmentId = R.id.bottom_navigation_albums;
  }

  private void replaceCurrentFragmentWith(Fragment fragment) {
    this.fragmentManager.beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commit();
  }

}
