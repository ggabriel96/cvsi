package io.github.ggabriel96.cvsi.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import io.github.ggabriel96.cvsi.android.R;
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
    return inflater.inflate(R.layout.fragment_albums, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    final EditText et = (EditText) this.getActivity().findViewById(R.id.text_input);
    this.getActivity().findViewById(R.id.send_text_input).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d(Albums.TAG, "onClick");
        new Endpoint(Albums.this.getContext()).execute(et.getText().toString());
      }
    });
  }
}
