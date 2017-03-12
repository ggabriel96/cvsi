package io.github.ggabriel96.cvsi.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.GetTokenResult;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.activity.Home;
import io.github.ggabriel96.cvsi.android.background.Endpoint;

public class Albums extends Fragment {

  private static final String TAG = "Albums";

  public Albums() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setHasOptionsMenu(true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.albums_menu, menu);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View albumsView = inflater.inflate(R.layout.fragment_albums, container, false);
    final TextInputEditText textInput = (TextInputEditText) albumsView.findViewById(R.id.text_input);
    albumsView.findViewById(R.id.send_text_input).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d(Albums.TAG, "onClick");
        Home.auth.getCurrentUser().getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
          @Override
          public void onComplete(@NonNull Task<GetTokenResult> task) {
            if (Home.networkListener.isOnline()) {
              new Endpoint(Albums.this.getContext(), task.getResult().getToken()).execute(textInput.getText().toString());
            } else {
              Toast.makeText(Albums.this.getContext(), R.string.disconnected, Toast.LENGTH_SHORT).show();
            }
          }
        });
      }
    });
    return albumsView;
  }

  @Override
  public void onStart() {
    super.onStart();
  }
}
