package io.github.ggabriel96.cvsi.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.ggabriel96.cvsi.android.R;

public class ProfileFragment extends Fragment {

  private View layout;

  public ProfileFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View profileLayout = inflater.inflate(R.layout.fragment_profile, container, false);
    profileLayout.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Home.auth.signOut();
      }
    });
    return profileLayout;
  }

}
