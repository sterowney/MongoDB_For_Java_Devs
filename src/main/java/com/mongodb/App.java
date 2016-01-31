package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

public class App {

    public static void main( String[] args ) {

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(500).build();

        // Create a client
        MongoClient client = new MongoClient(new ServerAddress(), options);

        // Get the db
        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());

        // Generic Mongo Collection, default using BSON
        MongoCollection<BsonDocument> collection = db.getCollection("test", BsonDocument.class);


    }
}
