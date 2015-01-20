package com.gederin.examples.helloworld;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created with IntelliJ IDEA.
 * User: rgederin
 * Date: 08.01.15
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldSpark {
    public static void main (String[] args){
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World from Spark";
            }
        });
    }
}
