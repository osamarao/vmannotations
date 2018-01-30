package osama.me.viewmodelannotations

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import osama.me.vmannotation.main

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view)
        val adapter = MainRecyclerViewAdapter(arrayListOf(AKindOfObject("Osama", 2), AKindOfObject("Nish", 4)))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        var some : main
    }
}