package com.gederin.examples.helloworld;
import com.mongodb.*;

import java.net.UnknownHostException;


public class HelloWorldMongoDB {
    public static void main (String [] args) throws UnknownHostException {
        /**
         * Create Mongo client
         */
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        /**
         * Get needful database
         */
        DB database = client.getDB("course");

        /**
         * Get needful collection
         */
        DBCollection collection = database.getCollection("hello");

        /**
         * Execute command
         */
        DBObject document = collection.findOne();
        System.out.println(document);
    }
}
