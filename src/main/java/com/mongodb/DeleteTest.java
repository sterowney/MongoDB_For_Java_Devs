package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;

/**
 * Created by stevenrowney on 31/01/2016.
 */
public class DeleteTest {

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
            collection.insertOne(new Document().append("_id", i));
        }

        collection.deleteMany(gt("_id", 4));

        collection.deleteOne(eq("_id", 4));


        List<Document> all = collection.find()
                .into(new ArrayList<Document>());

        for (Document doc : all) {
            System.out.println(doc.toJson());
        }

    }
}
