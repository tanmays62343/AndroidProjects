package com.trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trx.R
import com.trx.adapters.MyAdapters
import com.trx.database.Inventory
import com.trx.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val guns = Inventory.defaultInventory()

        binding?.GunsListView?.adapter = MyAdapters(this@MainActivity,guns)
        binding?.GunsListView?.layoutManager = GridLayoutManager(this,2)

    }
}