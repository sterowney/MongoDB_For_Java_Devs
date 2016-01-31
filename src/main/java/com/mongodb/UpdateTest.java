package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

/**
 * Created by stevenrowney on 31/01/2016.
 */
public class UpdateTest {

    public static void main(String[] args) {

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(500).build();

        // Create a client
        MongoClient client = new MongoClient(new ServerAddress(), options);

        // Get the db
        MongoDatabase db = client.getDatabase("course").withReadPreference(ReadPreference.secondary());

        // Generic Mongo Collection, default using BSON
        MongoCollection<Document> collection = db.getCollection("insertTest");

        collection.drop();

        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document().append("_id", i)
                .append("x", i));
        }

        collection.replaceOne(eq("x", 5), new Document("_id", 5).append("x", 20)
                .append("update", true));

        collection.updateOne(eq("x", 5), new Document("$set", new Document("x", 20)),
                new UpdateOptions().upsert(true));

        collection.updateMany(gt("_id", 5), new Document("$inc", new Document("x", 1)));


        List<Document> all = collection.find()
                .into(new ArrayList<Document>());

        for (Document doc : all) {
            System.out.println(doc.toJson());
        }

    }
}
