package nl.tudelft.jpacman.data;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import java.util.*;

public class ScoreRepository {
    public static boolean canConnect() {
        boolean canConnect = FirebaseConfig.isInternetConnected();
        return canConnect;
    }
    public static void write(Score score) {
        Firestore db = FirestoreClient.getFirestore();

        Map<String, Object> data = score.toMap();

        CollectionReference scoresRef = db.collection("scores");
        scoresRef.add(data);

    }

    public static List<Score> readAll() throws Exception {
        Firestore db = FirestoreClient.getFirestore();

        CollectionReference scoresRef = db.collection("scores");

        ApiFuture<QuerySnapshot> future = scoresRef.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Score> scores = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            scores.add(document.toObject(Score.class));
        }

        return scores;
    }

}
