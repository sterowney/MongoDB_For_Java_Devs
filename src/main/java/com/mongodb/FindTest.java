package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stevenrowney on 31/01/2016.
 */
public class FindTest {

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
            collection.insertOne(new Document("x", i));
        }

        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while(cursor.hasNext()) {
                Document cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }

        long count = collection.count();

        System.out.println(count);
    }
}
