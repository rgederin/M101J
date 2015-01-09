package com.gederin.helloworld;

import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;


/**
 * Created with IntelliJ IDEA.
 * User: rgederin
 * Date: 09.01.15
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldMongoDBSparkFreemarker {
    public static void main(String[] args) throws UnknownHostException {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreemarker.class, "/");

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
        final DBCollection collection = database.getCollection("hello");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");

                    DBObject document = collection.findOne();

                    helloTemplate.process(document, writer);

                } catch (IOException e) {
                    halt(500);
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (TemplateException e) {
                    halt(500);
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return writer;
            }
        });

    }
}
