package io.github.ggabriel96.cvsi.android.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import io.github.ggabriel96.cvsi.android.R;

public class SignUp extends AppCompatActivity {

  private static final String TAG = "SignUp";

  private TextView emailView;
  private TextView passwordView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    this.emailView = (TextView) this.findViewById(R.id.email);
    this.passwordView = (TextView) this.findViewById(R.id.password);

    this.findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (Home.networkListener.isOnline()) {
          /**
           * @TODO add loading animation
           */
          SignUp.this.disableFields();
          String email = emailView.getText().toString();
          String password = passwordView.getText().toString();
          if (email.isEmpty()) {
            Toast.makeText(SignUp.this, R.string.email_empty, Toast.LENGTH_SHORT).show();
            SignUp.this.enableFields();
          } else if (password.isEmpty()) {
            Toast.makeText(SignUp.this, R.string.password_weak, Toast.LENGTH_SHORT).show();
            SignUp.this.enableFields();
          } else {
            Home.auth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  Log.d(TAG, "createUserWithEmail:onComplete:isSuccessful: " + task.isSuccessful());

                  // If sign in fails, display a message to the user. If sign in succeeds
                  // the auth state listener will be notified and logic to handle the
                  // signed in user can be handled in the listener.
                  if (task.isSuccessful()) {
                    SignUp.this.finishResultingOk();
                  } else {
                    Exception authException = task.getException();
                    Log.w(TAG, "createUserWithEmail:failed", authException);
                    SignUp.this.enableFields();
                    if (authException instanceof FirebaseAuthInvalidCredentialsException) {
                      Toast.makeText(SignUp.this, R.string.email_invalid, Toast.LENGTH_SHORT).show();
                    } else if (authException instanceof FirebaseAuthUserCollisionException) {
                      Toast.makeText(SignUp.this, R.string.email_exists, Toast.LENGTH_SHORT).show();
                    } else if (authException instanceof FirebaseException) {
                      /**
                       * @TODO FirebaseAuthWeakPasswordException not being thrown?
                       */
                      Toast.makeText(SignUp.this, R.string.password_weak, Toast.LENGTH_SHORT).show();
                    }
                  }
                }
              });
          }
        } else {
          Toast.makeText(SignUp.this, R.string.disconnected, Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void finishResultingOk() {
    this.setResult(SignUp.RESULT_OK);
    this.finish();
  }

  private void enableFields() {
    this.emailView.setEnabled(Boolean.TRUE);
    this.passwordView.setEnabled(Boolean.TRUE);
  }

  private void disableFields() {
    this.emailView.setEnabled(Boolean.FALSE);
    this.passwordView.setEnabled(Boolean.FALSE);
  }
}
