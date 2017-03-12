package io.github.ggabriel96.cvsi.backend;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.X509Util;
import org.jose4j.keys.resolvers.X509VerificationKeyResolver;
import org.jose4j.lang.JoseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class FirebaseAuthenticator implements Authenticator {

  private static final String PROJECT_ID = "cvsi-backend";
  private static final String AUDIENCE = PROJECT_ID;
  private static final String ISSUER = "https://securetoken.google.com/" + PROJECT_ID;
  private static final String KEYS_URL = "https://www.googleapis.com/robot/v1/metadata/x509/securetoken@system.gserviceaccount.com";

  @Override
  public User authenticate(HttpServletRequest request) {
    try {
      final String authorizationHeader = request.getHeader("Authorization");
      final String jwt = authorizationHeader.replace("Bearer", "").trim();
      List<X509Certificate> certificates = this.getCertificates();
      X509VerificationKeyResolver x509VerificationKeyResolver = new X509VerificationKeyResolver(certificates);
      x509VerificationKeyResolver.setTryAllOnNoThumbHeader(true);
      JwtConsumer jwtConsumer = new JwtConsumerBuilder()
        .setJwsAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, "RS256"))
        .setVerificationKeyResolver(x509VerificationKeyResolver)
        .setRequireExpirationTime() // the JWT must have an expiration time
        .setRequireIssuedAt()
        .setExpectedAudience(FirebaseAuthenticator.AUDIENCE) // to whom the JWT is intended for
        .setExpectedIssuer(FirebaseAuthenticator.ISSUER) // whom the JWT needs to have been issued by
        .setRequireSubject() // the JWT must have a subject claim
        .build(); // create the JwtConsumer instance
      JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
      return new User(jwtClaims.getSubject(), null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * @return a list of {@link X509Certificate}s parsed from {@link FirebaseAuthenticator#getCertificates()}
   * @throws JoseException see {@link X509Util#fromBase64Der(String)}
   */
  private List<X509Certificate> getCertificates() throws JoseException {
    // "-----BEGIN CERTIFICATE-----".length()
    final Integer certificateStart = 27;
    final X509Util x509Util = new X509Util();
    List<X509Certificate> certificates = new ArrayList<>();
    final JsonObject certificates64 = this.fetchCertificates();
    Iterator<Map.Entry<String, JsonElement>> iterator = certificates64.entrySet().iterator();
    while (iterator.hasNext()) {
      String certificate = iterator.next().getValue().getAsString();
      Integer certificateEnd = certificate.indexOf("-----END CERTIFICATE-----");
      certificate = certificate.substring(certificateStart, certificateEnd);
      certificates.add(x509Util.fromBase64Der(certificate));
    }
    return certificates;
  }

  /**
   * Fetches the X.509 certificates for Firebase through an {@link HttpURLConnection}
   *
   * @return the certificates listed at {@link FirebaseAuthenticator#KEYS_URL}
   * or {@code null} if any exception is caught
   */
  private JsonObject fetchCertificates() {
    try {
      URL url = new URL(FirebaseAuthenticator.KEYS_URL);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.connect();
      JsonObject certificates = new JsonParser()
        .parse(new InputStreamReader(httpURLConnection.getInputStream())).getAsJsonObject();
      return certificates.getAsJsonObject();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

}
