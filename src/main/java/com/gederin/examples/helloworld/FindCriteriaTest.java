/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.gederin.examples.helloworld;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;

import java.net.UnknownHostException;

public class FindCriteriaTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");

        System.out.println("\nCount:");
        long count = collection.count();

        System.out.println(count);

       /*  HW 2.1
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
         */

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
        /*
         QueryBuilder builder = QueryBuilder.start("x").is(0)
                .and("y").greaterThan(10).lessThan(70);


        DBObject query = new BasicDBObject("x", 0)
                .append("y", new BasicDBObject("$gt", 10).append("$lt", 90));

        System.out.println("\nCount:");
        long count = collection.count(builder.get());
        System.out.println(count);

        System.out.println("\nFind all: ");
        DBCursor cursor = collection.find(builder.get());
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }           */
    }
}
