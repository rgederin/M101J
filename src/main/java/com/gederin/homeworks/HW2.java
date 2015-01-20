package com.gederin.homeworks;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: rgederin
 * Date: 20.01.15
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
public class HW2 {
    public static void main (String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");
    }

    /**
     * HW 2.1
     */
    private static void hw1(DBCollection collection){
        System.out.println("\nHomework 2.1:");

        QueryBuilder builder = QueryBuilder.start("score").greaterThanEquals(65).and("type").is("exam");
        DBCursor cursor = collection.find(builder.get()).sort(new BasicDBObject("score",1));

        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
    }

    private static void hw2 (DBCollection collection){
        System.out.println("\nHomework 2.2:");

        QueryBuilder builder = QueryBuilder.start("type").is("homework");
        DBCursor cursor = collection.find(builder.get())
                .sort(new BasicDBObject("student_id", 1).append("score", 1));
        int previd = -1;
        int currentid = 0;
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                currentid = (Integer)cur.get("student_id");
                if (previd != currentid){
                    System.out.println(cur);
                    collection.remove(new BasicDBObject("student_id",cur.get("student_id")).append("score", cur.get("score")));
                    previd = currentid;
                }
            }
        } finally {
            cursor.close();
        }
    }
}
