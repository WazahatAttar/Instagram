package com.example.instagram.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.MainActivity
import com.example.instagram.Post
import com.example.instagram.PostAdapter
import com.example.instagram.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


open class HomeFragment : Fragment() {

    lateinit var postRecyclerView: RecyclerView

    lateinit var adapter: PostAdapter

    var allPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Setup logics

        postRecyclerView = view.findViewById(R.id.postRecyclerView)

        adapter = PostAdapter(requireContext(), allPosts)
        postRecyclerView.adapter = adapter

        postRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()
    }
    //Query all post in the server
    open fun queryPosts() {
        //    specify class to query
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //    Find all Post objects
        query.include(Post.KEY_USER)
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



    companion object {
        const val TAG = "HomeFragment"
    }
}