package db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.bson.Document;

import java.security.Key;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by kylemcgrew on 12/4/17.
 */
public class UtilDBClient {

    private static MongoDatabase systemDatabase = DBClient.getDatabase("serversettings");
    private static MongoCollection<Document> collection = DBClient.getCollection(systemDatabase,"settings");

    public static void saveToken() {
        Key key = MacProvider.generateKey();
        Document doc = new Document("token",key.toString());
        collection.insertOne(doc);
    }

    public static void printCount() {
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }

    public static void retrieveToken() {
        Document doc = collection.find(eq("token")).first();
        if (doc != null) {
            System.out.println(doc.toJson());
        } else {
            System.out.println("no doc found");
        }

    }

}
