package db;

/**
 * Created by kylemcgrew on 11/25/17.
 */

import ch.qos.logback.core.db.dialect.DBUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import models.Client;
import models.Credentials;
import util.HashManager;
import util.UUIDManager;

import static com.mongodb.client.model.Filters.eq;

public class UserDBClient extends DBUtil {

    private static MongoDatabase userDatabase = DBClient.getDatabase("models");
    private static MongoCollection<Document> clientsCollection = DBClient.getCollection(userDatabase,"clients");

    public void storeEvent() {
        Document doc = new Document("name","MongoDB");
        clientsCollection.insertOne(doc);
    }

    public void printCount() {
        Document myDoc = clientsCollection.find().first();
        System.out.println(myDoc.toJson());
    }

    public static Integer createNewClient(Client client) {
        String username = client.getUsername();
        String firstname = client.getFirstname();
        String lastname = client.getLastname();
        String email = client.getEmail();

        if ((username == null) || (email == null) ||  (firstname == null) ||  (lastname == null)) {
            return 2;
        }

        Document doc = clientsCollection.find(eq("username",username)).first();

        if (doc == null) {
            String hashedPW = HashManager.hashpw(client.getPassword());
            String uuid = UUIDManager.generateUUID();

            doc = new Document("id",uuid)
                    .append("username",username)
                    .append("password",hashedPW)
                    .append("firstname",firstname)
                    .append("lastname",lastname)
                    .append("email",email);
            clientsCollection.insertOne(doc);

            return 0;
        } else {
            return 1;
        }

    }

    public static Boolean checkCredentials(Credentials credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        Document doc = clientsCollection.find(eq("username",username)).first();

        if (doc == null)
            return false;
        else {
            String hashPass = doc.getString("password");
            if (HashManager.checkpw(password,hashPass)) {
                return true;
            }
            return false;
        }
    }

}
