package db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by kylemcgrew on 12/4/17.
 */
public class DBClient {

    private static MongoClient mongoClient = new MongoClient();

    public static MongoDatabase getDatabase(String database) {
        return mongoClient.getDatabase(database);
    }

    public static MongoCollection<Document> getCollection(MongoDatabase db, String table) {
        return db.getCollection(table);
    }
}
