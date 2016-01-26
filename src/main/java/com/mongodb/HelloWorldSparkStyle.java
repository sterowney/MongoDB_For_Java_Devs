package com.mongodb;

import static spark.Spark.get;

/**
 * Created by stevenrowney on 25/01/2016.
 */
public class HelloWorldSparkStyle {

    public static void main(String[] args) {
        get("/", (req, res) -> "Hello World from Spark");
    }
}
