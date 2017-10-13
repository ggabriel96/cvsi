package io.github.ggabriel96.cvsi.android.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.background.LocalBinder;
import io.github.ggabriel96.cvsi.android.background.LocationHandler;
import io.github.ggabriel96.cvsi.android.background.NetworkListener;
import io.github.ggabriel96.cvsi.android.background.PictureEndpoint;
import io.github.ggabriel96.cvsi.android.background.RotationService;
import io.github.ggabriel96.cvsi.android.controller.RotationAdapter;
import io.github.ggabriel96.cvsi.android.fragment.Albums;
import io.github.ggabriel96.cvsi.android.fragment.Locations;
import io.github.ggabriel96.cvsi.android.fragment.Profile;
import io.github.ggabriel96.cvsi.android.model.SensorData;
import io.github.ggabriel96.cvsi.android.sql.SQLiteContract;
import io.github.ggabriel96.cvsi.android.sql.SQLiteHelper;
import io.github.ggabriel96.cvsi.android.util.EntityConverter;
import io.github.ggabriel96.cvsi.backend.myApi.model.Picture;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Boolean.TRUE;

@Getter
@Setter
public class Home extends AppCompatActivity implements ServiceConnection {

  public static final FirebaseAuth auth = FirebaseAuth.getInstance();
  public static final EntityConverter entityConverter = new EntityConverter();
  public static final NetworkListener networkListener = new NetworkListener();
  public static final FirebaseStorage storage = FirebaseStorage.getInstance();

  private static final String TAG = "Home";
  private static final int LOGIN_REQUEST = 1;
  private static final int PICK_PHOTO_REQUEST = 2;
  private static final int REQUEST_IMAGE_CAPTURE = 3;
  private static final String STATE_FRAGMENT_ID = "currentFragmentId";

  private Albums albums;
  private Profile profile;
  private Locations locations;
  private Uri captureResult;
  private FirebaseUser firebaseUser;
  private Integer currentFragmentId;
  private FragmentManager fragmentManager;
  private BottomNavigationView bottomNavigationView;
  private FirebaseAuth.AuthStateListener authListener;

  private RotationService rotationService;
  private SensorData sensorData;
  private LocationHandler locationHandler;

  private SQLiteHelper sqLiteHelper;
  private SQLiteDatabase sqLiteDatabase;

  /*
   * https://firebase.google.com/docs/database/android/read-and-write
   * https://firebase.google.com/docs/reference/android/com/google/firebase/database/DatabaseReference
   */

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");
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
          Home.this.init(savedInstanceState != null ? savedInstanceState.getInt(Home.STATE_FRAGMENT_ID) : null);
          Home.this.sqLiteHelper = new SQLiteHelper(Home.this);
          Home.this.sqLiteDatabase = Home.this.sqLiteHelper.getWritableDatabase();
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
    Log.d(TAG, "onStart");
    super.onStart();
    Home.auth.addAuthStateListener(this.authListener);
    Intent intent = new Intent(Home.this, RotationService.class);
    Home.this.startService(intent);
    Home.this.bindService(intent, Home.this, Context.BIND_AUTO_CREATE);
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    Log.d(TAG, "onSaveInstanceState");
    if (this.currentFragmentId != null) {
      Log.d(TAG, Home.STATE_FRAGMENT_ID + ": " + this.currentFragmentId.toString());
      savedInstanceState.putInt(Home.STATE_FRAGMENT_ID, this.currentFragmentId);
    }
    // Always call the superclass so it can save the view hierarchy state
    super.onSaveInstanceState(savedInstanceState);
  }

  @Override
  public void onStop() {
    Log.d(TAG, "onStop");
    super.onStop();
    if (this.authListener != null) Home.auth.removeAuthStateListener(this.authListener);
  }

  @Override
  protected void onDestroy() {
    Log.d(TAG, "onDestroy");
    super.onDestroy();
    if (Home.networkListener != null) networkListener.unregister(this);
    this.sqLiteHelper.close();
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
      case Home.REQUEST_IMAGE_CAPTURE:
//        this.rotationService.stopListener();
//        this.locationHandler.stopLocationUpdates();
//        this.locationHandler.disconnect();

        //https://stackoverflow.com/questions/5657411/android-getting-a-file-uri-from-a-content-uri
        //THE PROBLEM IS THAT THIS IS A CONTENT URI, NOT A NORMAL ONE.
        //WE GOTTA ACCESS THE UNDERLYING FILE THROUGH A CONTENT PROVIDER.
        if (resultCode == Home.RESULT_OK) {
          RotationAdapter rotationAdapter = this.rotationService.getRotationAdapter();
//          this.rotationService.stopSelf();
          broadcastNewPicture(this.captureResult);
          savePictureMetadata(Home.this.captureResult, rotationAdapter);
          uploadPictureToStorage(Home.this.captureResult);
        }
        break;
      default:
        super.onActivityResult(requestCode, resultCode, data);
    }
  }

  private void savePictureMetadata(final Uri uri, final RotationAdapter rotationAdapter) {
    final FirebaseUser firebaseUser = Home.auth.getCurrentUser();
    firebaseUser.getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
      @Override
      public void onComplete(@NonNull Task<GetTokenResult> task) {
        if (Home.networkListener.isOnline()) {
          Picture picture = Home.entityConverter.pictureToJson(uri, Home.entityConverter.userToJson(firebaseUser), rotationAdapter);
          new PictureEndpoint(Home.this, task.getResult().getToken()).execute(picture);
          Home.this.saveToSQLite(uri, picture);
        } else {
          Toast.makeText(Home.this, R.string.disconnected, Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
  private void uploadPictureToStorage(final Uri uri) {
    final String pictureLocation = "images/" + Home.auth.getCurrentUser().getUid() + "/" + uri.getLastPathSegment();
    StorageReference imageRef = Home.storage.getReference().child(pictureLocation);
    Toast.makeText(Home.this, R.string.upload_started, Toast.LENGTH_SHORT).show();
    imageRef.putFile(uri)
      .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          Log.e(TAG, "Image upload failed.", exception);
          Toast.makeText(Home.this, R.string.upload_failed, Toast.LENGTH_SHORT).show();
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Log.d(TAG, "Upload successful");
        Toast.makeText(Home.this, R.string.upload_successful, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void saveToSQLite(Uri uri, Picture picture) {
    Log.d(TAG, picture.getLocation().toString() + " loçlaksçak");
    this.sqLiteDatabase.insert(
      SQLiteContract.PictureEntry.TABLE_NAME
      , null
      , SQLiteContract.PictureEntry.getContentValues(
        uri.getPath(), picture, null
      )
    );
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
      case R.id.fix_location:
        Intent maps = new Intent(Home.this, MapsActivity.class);
        Home.this.startActivity(maps);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void init(Integer currentFragmentId) {
    Log.d(TAG, "init");
    this.instantiateFragments();
    this.setContentView(R.layout.activity_home);
    this.setupBottomNavigation();
    this.setCurrentFragment(currentFragmentId);
  }

  private void instantiateFragments() {
    Log.d(TAG, "instantiateFragments");
    this.albums = new Albums();
    this.profile = new Profile();
    this.locations = new Locations();
  }

  private void setupBottomNavigation() {
    Log.d(TAG, "setupBottomNavigation");
    this.bottomNavigationView = (BottomNavigationView) this.findViewById(R.id.bottom_navigation);
    this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (Home.this.currentFragmentId != null && Home.this.currentFragmentId == item.getItemId())
          return false;
        Home.this.currentFragmentId = item.getItemId();
        switch (item.getItemId()) {
          case R.id.bottom_navigation_albums:
            Home.this.setCurrentFragment(Home.this.albums);
            break;
          case R.id.bottom_navigation_camera:
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(Home.this.getPackageManager()) != null) {
              // Create the File where the photo should go
              File photoFile = null;
              try {
                photoFile = createImageFile();
              } catch (IOException ex) {
                Log.e(TAG, "createImageFile Failed", ex);
              }
              // Continue only if the File was successfully created
              if (photoFile != null) {
                Log.d(TAG, "uiasgugfisadugfklsdgvklsdjgvksdj.d k" + photoFile.getPath());
                Home.this.captureResult = FileProvider.getUriForFile(Home.this,
                  Home.this.getResources().getString(R.string.provider_authority),
                  photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Home.this.captureResult);
                Log.d(TAG, String.valueOf(photoFile.exists()) + "335");
                Log.d(TAG, String.valueOf(new File(captureResult.getPath()).exists()) + "335");
                Home.this.startActivityForResult(takePictureIntent, Home.REQUEST_IMAGE_CAPTURE);
              }
            }
            break;
          case R.id.bottom_navigation_profile:
            Home.this.setCurrentFragment(Home.this.profile);
            break;
          case R.id.bottom_navigation_locations:
            Home.this.setCurrentFragment(Home.this.locations);
            break;
        }
        return true;
      }
    });
  }

  private void showInitialFragment() {
    Log.d(TAG, "showInitialFragment");
    this.setCurrentFragment(R.id.bottom_navigation_albums);
  }

  private void setCurrentFragment(Integer currentFragmentId) {
    Log.d(TAG, "setCurrentFragment " + (currentFragmentId == null ? "null" : currentFragmentId.toString()));
    if (currentFragmentId != null)
      Home.this.bottomNavigationView.setSelectedItemId(currentFragmentId);
    else this.showInitialFragment();
  }

  private void setCurrentFragment(Fragment fragment) {
    this.fragmentManager.beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .commit();
  }

  private File createImageFile() throws IOException, SecurityException {
    // Create an image file name
    String timestamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Calendar.getInstance().getTime());
    String imageFileName = "JPEG_" + timestamp + "_";
    File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() +
      File.separator + getResources().getString(R.string.pictures_directory));
    if (!storageDir.exists()) storageDir.mkdir();
    File image = File.createTempFile(
      imageFileName,  /* prefix */
      ".jpg",         /* suffix */
      storageDir      /* directory */
    );
    return image;
  }

  private void broadcastNewPicture(Uri uri) {
    this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
  }

  @Override
  public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
    Log.d(TAG, "onServiceConnected");
    LocalBinder localBinder = (LocalBinder) iBinder;
    this.rotationService = localBinder.getService();
    this.locationHandler = new LocationHandler(this, this.rotationService.getRotationAdapter());
    this.locationHandler.build();
    this.locationHandler.connect(TRUE);
    this.locationHandler.startLocationUpdates();
  }

  @Override
  public void onServiceDisconnected(ComponentName componentName) {
    Log.d(TAG, "onServiceDisconnected");
    this.rotationService = null;
  }

}
