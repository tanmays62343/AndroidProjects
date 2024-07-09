package com.trx.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trx.R
import com.trx.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchResultAdapter

    private val dataList = listOf(
        "Apple", "Banana", "Cherry", "Date", "Elderberry",
        "Fig", "Grape", "Honeydew", "Indian Fig", "Jackfruit",
        "Kiwi", "Lemon", "Mango", "Nectarine", "Orange",
        "Papaya", "Quince", "Raspberry", "Strawberry", "Tangerine",
        "Ugli Fruit", "Vanilla Bean", "Watermelon", "Xigua", "Yuzu",
        "Zucchini", "Apricot", "Blueberry", "Coconut", "Durian",
        "Guava", "Huckleberry", "Jujube", "Kumquat", "Lychee",
        "Mulberry", "Nashi Pear", "Olive", "Peach", "Pineapple",
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SearchResultAdapter(emptyList()) // Initially empty
        recyclerView.adapter = adapter

        // Hide recyclerView initially
        recyclerView.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 1) {
                    filterList(newText)
                } else {
                    recyclerView.isVisible = false
                }
                return true
            }
        })

        return true
    }

    private fun filterList(query: String) {
        val filteredList = dataList.filter { it.contains(query, true) }.take(5) // Take up to 5 items
        adapter.updateData(filteredList)
        recyclerView.isVisible = true
    }
}