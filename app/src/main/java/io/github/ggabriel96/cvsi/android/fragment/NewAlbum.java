package io.github.ggabriel96.cvsi.android.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.ggabriel96.cvsi.android.R;
import io.github.ggabriel96.cvsi.android.interfaces.DialogListener;

public class NewAlbum extends DialogFragment {
  private DialogListener dialogListener;

  public NewAlbum() {
    // Required empty public constructor
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
    builder.setTitle(R.string.album_new);
    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        NewAlbum.this.dialogListener.onDialogPositiveClick(NewAlbum.this);
      }
    });
    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        NewAlbum.this.dialogListener.onDialogNegativeClick(NewAlbum.this);
      }
    });
    // Get the layout inflater
    LayoutInflater inflater = this.getActivity().getLayoutInflater();

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(inflater.inflate(R.layout.fragment_new_album, null));

    // Create the AlertDialog object and return it
    return builder.create();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_new_album, container, false);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      // Instantiate the NoticeDialogListener so we can send events to the host
      this.dialogListener = (DialogListener) this.getActivity();
    } catch (ClassCastException e) {
      // The activity doesn't implement the interface, throw exception
      throw new ClassCastException(this.getActivity().toString() + " must implement " + DialogListener.class.getName());
    }
  }

}
