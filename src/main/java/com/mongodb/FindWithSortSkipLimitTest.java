package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 * Created by stevenrowney on 31/01/2016.
 */
public class FindWithSortSkipLimitTest {

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
            for (int j = 0; j < 10; j++) {
                collection.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());

//        Bson sort = new Document("i", 1).append("j", -1);

        Bson sort = orderBy(ascending("i"), descending("j"));

        List<Document> all = collection.find()
                .sort(sort)
                .projection(projection)
                .skip(20)
                .limit(50)
                .into(new ArrayList<Document>());

        for (Document doc : all) {
            System.out.println(doc.toJson());
        }
    }
}
