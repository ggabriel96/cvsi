package io.github.ggabriel96.cvsi.android.interfaces;

import android.support.v4.app.DialogFragment;

/**
 * Created by gbrl on 14/07/17.
 */

public interface DialogListener {
  public void onDialogPositiveClick(DialogFragment dialog);

  public void onDialogNegativeClick(DialogFragment dialog);
}
