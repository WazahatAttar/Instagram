package com.example.instagram.fragments

import android.util.Log
import com.example.instagram.MainActivity
import com.example.instagram.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : HomeFragment() {

    override fun queryPosts() {
        //    specify class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //    Find all Post objects
        query.include(Post.KEY_USER)

//      Only return posts from the currently signed user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())

//      Return posts in descending order: ie newer posts will appear first
        query.addAscendingOrder("createdAt")

        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if(e != null) {
                    Log.e(MainActivity.TAG, "Error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(MainActivity.TAG, "Post: " + post.getDescription() + " , Username: " + post.getUser()?.username)
                        }

                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

}