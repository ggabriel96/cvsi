package io.github.ggabriel96.cvsi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import io.github.ggabriel96.cvsi.android.R;

public class Login extends AppCompatActivity {

  private static final String TAG = "Login";
  private static final int SIGNUP_REQUEST = 1;

  private TextView emailView;
  private TextView passwordView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_login);
    this.emailView = (TextView) this.findViewById(R.id.email);
    this.passwordView = (TextView) this.findViewById(R.id.password);

    this.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (Home.networkListener.isOnline()) {
          /**
           * @TODO add loading animation
           */
          Login.this.disableFields();
          String email = Login.this.emailView.getText().toString();
          String password = Login.this.passwordView.getText().toString();
          if (email.isEmpty()) {
            Toast.makeText(Login.this, R.string.email_empty, Toast.LENGTH_SHORT).show();
            Login.this.enableFields();
          } else if (password.isEmpty()) {
            Toast.makeText(Login.this, R.string.password_weak, Toast.LENGTH_SHORT).show();
            Login.this.enableFields();
          } else {
            Home.auth.signInWithEmailAndPassword(email, password)
              .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  Log.d(TAG, "signInWithEmail:onComplete:isSuccessful: " + task.isSuccessful());

                  // If sign in fails, display a message to the user. If sign in succeeds
                  // the auth state listener will be notified and logic to handle the
                  // signed in user can be handled in the listener.
                  if (task.isSuccessful()) {
                    Login.this.finishResultingOk();
                  } else {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Login.this.enableFields();
                    Toast.makeText(Login.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                  }
                }
              });
          }
        } else {
          Toast.makeText(Login.this, R.string.disconnected, Toast.LENGTH_SHORT).show();
        }
      }
    });

    this.findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Login.this.startActivityForResult(new Intent(Login.this, SignUp.class), Login.SIGNUP_REQUEST);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == Login.SIGNUP_REQUEST) {
      if (resultCode == Login.RESULT_OK) this.finishResultingOk();
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  private void finishResultingOk() {
    this.setResult(Login.RESULT_OK);
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
