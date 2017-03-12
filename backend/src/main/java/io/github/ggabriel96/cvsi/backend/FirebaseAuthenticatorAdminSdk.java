//package io.github.ggabriel96.cvsi.backend;
//
//import com.google.api.server.spi.auth.common.User;
//import com.google.api.server.spi.config.Authenticator;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseCredentials;
//import com.google.firebase.auth.FirebaseToken;
//import com.google.firebase.tasks.Task;
//import com.google.firebase.tasks.Tasks;
//
//import java.io.FileInputStream;
//import java.util.logging.Logger;
//
//import javax.servlet.http.HttpServletRequest;
//
//public class FirebaseAuthenticatorAdminSdk implements Authenticator {
//
//  private static final Logger LOGGER = Logger.getLogger(FirebaseAuthenticatorAdminSdk.class.getName());
//
//  static {
//    try {
//      FileInputStream serviceAccount = new FileInputStream("WEB-INF/cvsi-backend-firebase.json");
//      FirebaseOptions options = new FirebaseOptions.Builder()
//        .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
//        .setDatabaseUrl("https://cvsi-backend.firebaseio.com/")
//        .build();
//      FirebaseApp.initializeApp(options);
//      LOGGER.info("FirebaseApp successfully initialized!");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  @Override
//  public User authenticate(HttpServletRequest request) {
//    final String authHeader = request.getHeader("Authorization");
//    LOGGER.info("authHeader is null? " + Boolean.toString(authHeader == null));
//
//    if (authHeader == null) return null;
//    final String authToken = authHeader.replace("Bearer", "").trim();
//
//    Task<FirebaseToken> idTokenTask = FirebaseAuth.getInstance().verifyIdToken(authToken);
//    try {
//      Tasks.await(idTokenTask);
//    } catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    }
//
//    FirebaseToken firebaseToken = idTokenTask.getResult();
//    return new User(firebaseToken.getUid(), firebaseToken.getEmail());
//  }
//}
