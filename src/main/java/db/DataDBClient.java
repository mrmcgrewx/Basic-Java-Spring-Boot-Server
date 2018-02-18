package db;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.MLData;
import org.bson.Document;

public class DataDBClient {

    private static MongoDatabase dataDatabase = DBClient.getDatabase("data");
    private static MongoCollection<Document> collection = DBClient.getCollection(dataDatabase,"dataPool");

    public static void storeToDataPool(MLData data) {
        Document doc = new Document("username",data.getUsername())
                .append("class1", data.getClass1())
                .append("class2", data.getClass2())
                .append("lat", data.getLat())
                .append("lon", data.getLon())
                .append("alt", data.getAlt())
                .append("imageData", data.getImageData());
        collection.insertOne(doc);
    }
}
