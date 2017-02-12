package io.github.ggabriel96.cvsi.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import io.github.ggabriel96.cvsi.android.R;

public class Login extends AppCompatActivity {

  private static final String TAG = "Login";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_login);

    final TextView email = (TextView) this.findViewById(R.id.email);
    final TextView password = (TextView) this.findViewById(R.id.password);
    ((Button) this.findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        /**
         * @TODO add loading animation
         */
        email.setEnabled(Boolean.FALSE);
        password.setEnabled(Boolean.FALSE);
        Home.auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
          .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              Log.d(TAG, "signInWithEmail:onComplete:isSuccessful: " + task.isSuccessful());

              // If sign in fails, display a message to the user. If sign in succeeds
              // the auth state listener will be notified and logic to handle the
              // signed in user can be handled in the listener.
              if (task.isSuccessful()) {
                Login.this.finish();
              } else {
                Log.w(TAG, "signInWithEmail:failed", task.getException());
                email.setEnabled(Boolean.TRUE);
                password.setEnabled(Boolean.TRUE);
                Toast.makeText(Login.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
              }
            }
          });
      }
    });

    ((Button) this.findViewById(R.id.signup)).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Login.this.startActivity(new Intent(Login.this, SignUp.class));
        Login.this.finish();
      }
    });
  }
}
