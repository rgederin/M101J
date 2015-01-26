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

package com.gederin.blog.dao;

import com.mongodb.*;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPostDAO {
    DBCollection postsCollection;

    public BlogPostDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public DBObject findByPermalink(String permalink) {

        DBObject post = null;
        QueryBuilder queryBuilder = QueryBuilder.start("permalink").is(permalink);
        post = this.postsCollection.findOne(queryBuilder.get());
        // XXX HW 3.2,  Work Here



        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<DBObject> findByDateDescending(int limit) {

        List<DBObject> posts = null;
        posts = new ArrayList<DBObject>();
        DBCursor dbCursor = this.postsCollection.find().sort(new BasicDBObject("date", -1)).limit(limit);
        try {
            while (dbCursor.hasNext()) {
                DBObject dbObject = dbCursor.next();
                posts.add(dbObject);
            }
        } finally {
            dbCursor.close();
        }
        // XXX HW 3.2,  Work Here
        // Return a list of DBObjects, each one a post from the posts collection

        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        BasicDBObject post = new BasicDBObject("title", title)
                .append("author", username)
                .append("body", body)
                .append("permalink", permalink)
                .append("tags", tags)
                .append("comments", new ArrayList<String>())
                .append("date", new Date());
        this.postsCollection.insert(post);

        return permalink;
    }




   // White space to protect the innocent



    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        // XXX HW 3.3, Work Here
        // Hints:
        // - email is optional and may come in NULL. Check for that.
        // - best solution uses an update command to the database and a suitable
        //   operator to append the comment on to any existing list of comments

       /* this.postsCollection.update(new BasicDBObject("permalink", permalink),
                new BasicDBObject("$push", new BasicDBObject("comments.author", name)
                        .append("comments.body", body)
                        .append("comments.email", email)
        ));*/

        BasicDBObject update = new BasicDBObject();
        update.put("permalink", permalink);

        BasicDBObject newComment = null;
        if (email != null){
            newComment = new BasicDBObject("author", name).append("body", body).append("email", email);
        }   else{
            newComment = new BasicDBObject("author", name).append("body", body);
        }
        BasicDBObject updateCommand = new BasicDBObject();
        updateCommand.put( "$push", new BasicDBObject( "comments", newComment ) );
        this.postsCollection.update(update, updateCommand, true, true);
    }


}
