package com.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.halt;

/**
 * Created by stevenrowney on 25/01/2016.
 */
public class HelloWorldMongoDBFreemarkerSparkStyle {

    public static void main(String[] args) {

        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldMongoDBFreemarkerSparkStyle.class, "/");

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(500).build();

        // Create a client
        MongoClient client = new MongoClient(new ServerAddress(), options);

        // Get the db
        MongoDatabase db = client.getDatabase("course").withReadPreference(ReadPreference.secondary());

        // Generic Mongo Collection, default using BSON
        final MongoCollection<Document> collection = db.getCollection("hello");

        collection.drop();

        collection.insertOne(new Document("name", "MongoDB"));

        get("/", (request, response) -> {

            StringWriter writer = new StringWriter();

            try {

                Template helloTemplate = configuration.getTemplate("hello.ftl");

                Document document = collection.find().first();


                helloTemplate.process(document, writer);

            } catch (Exception e) {

                halt(500);
                e.printStackTrace();
            }

            return writer;

        });
    }
}
