package io.github.ggabriel96.cvsi.android.background;

import android.support.annotation.NonNull;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import java.io.IOException;

public class AuthenticatedHttpRequest implements HttpRequestInitializer {

  private final String authToken;

  public AuthenticatedHttpRequest(@NonNull final String authToken) {
    this.authToken = authToken;
  }

  @Override
  public void initialize(HttpRequest request) throws IOException {
    HttpHeaders headers = request.getHeaders();
    if (headers == null) {
      headers = new HttpHeaders();
      request.setHeaders(headers);
    }
    headers.setAuthorization("Bearer " + this.authToken);
  }
}
