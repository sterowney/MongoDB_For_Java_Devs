package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.Arrays;

/**
 * Created by stevenrowney on 31/01/2016.
 */
public class InsertTest {

    public static void main(String[] args) {

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(500).build();

        // Create a client
        MongoClient client = new MongoClient(new ServerAddress(), options);

        // Get the db
        MongoDatabase db = client.getDatabase("course").withReadPreference(ReadPreference.secondary());

        // Generic Mongo Collection, default using BSON
        MongoCollection<Document> collection = db.getCollection("insertTest");

        collection.drop();

        Document smith = new Document()
                .append("nanme", "Smith")
                .append("age", 30)
                .append("profession", "programmer");

        Document jones = new Document()
                .append("nanme", "Smith")
                .append("age", 30)
                .append("profession", "programmer");

        System.out.println(jones);
        System.out.println(smith);

        collection.insertMany(Arrays.asList(jones, smith));

        System.out.println(jones);
        System.out.println(smith);


    }
}
