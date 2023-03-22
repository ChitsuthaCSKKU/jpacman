package nl.tudelft.jpacman.data;
import java.io.FileInputStream;
import java.net.InetAddress;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


public class FirebaseConfig {
    public static void init() throws Exception {
        // Path to your service account key file
        String path = "./data/database/FirebaseKey.json";

        // Initialize Firebase with your service account credentials
        FileInputStream serviceAccount = new FileInputStream(path);
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();
        FirebaseApp.initializeApp(options);
    }

    public static boolean isInternetConnected() {
        try {
            InetAddress address = InetAddress.getByName("8.8.8.8"); // Google's DNS server
            if (address.isReachable(5000)) { // 5000 ms timeout
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
