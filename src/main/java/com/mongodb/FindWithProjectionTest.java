package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by stevenrowney on 31/01/2016.
 */
public class FindWithProjectionTest {

    public static void main(String[] args) {

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(500).build();

        // Create a client
        MongoClient client = new MongoClient(new ServerAddress(), options);

        // Get the db
        MongoDatabase db = client.getDatabase("course").withReadPreference(ReadPreference.secondary());

        // Generic Mongo Collection, default using BSON
        MongoCollection<Document> collection = db.getCollection("insertTest");

        collection.drop();

        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                    .append("x", new Random().nextInt(2))
                    .append("y", new Random().nextInt(100))
                    .append("i", i)
            );
        }

//        Bson filter = new Document("x", 0)
//                .append("y", new Document("$gt", 10).append("$lt",90));

        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

//        Bson projection = new Document("x", 0).append("_id", 0);

//        Bson projection = new Document("x", 1).append("y", 1).append("_id", 0);

        Bson projection = Projections.include("y", "i");

        List<Document> all = collection.find(filter).projection(projection).into(new ArrayList<Document>());

        for (Document doc : all) {
            System.out.println(doc.toJson());
        }
    }
}
