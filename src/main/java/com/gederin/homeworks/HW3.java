package com.gederin.homeworks;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rgederin
 * Date: 26.01.15
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */
public class HW3 {
    public static void main (String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection collection = db.getCollection("students");
        hw1(collection);
    }

    /**
     * HW 3.1
     */
    private static void hw1(DBCollection collection){
        System.out.println("\nHomework 3.1:");

        DBCursor cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                List<Double> homeworks = new ArrayList<Double>();
                DBObject cur = cursor.next();
                List <BasicDBObject> list = (List<BasicDBObject>) cur.get("scores");
                double score = -1;
                for (BasicDBObject basicDBObject : list){
                    String type = (String)basicDBObject.get("type");
                    score =  (Double)basicDBObject.get("score");
                    //System.out.print(type + " : " + score  );
                    if (type.equals("homework")){
                        homeworks.add(score);
                    }

                }
                Collections.sort(homeworks);
                System.out.print(homeworks);
               BasicDBObject obj = new BasicDBObject("scores.type", "homework").append("scores.score", score);
               // collection.update(obj, new DBObject())
                System.out.println();
            }
        } finally {
            cursor.close();
        }
    }
}
