package com.Trx.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.Trx.adapters.HistoryAdapter
import com.Trx.database.HistoryDao
import com.Trx.database.WorkoutApp
import com.Trx.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.historyToolbar)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "History"
        }
        binding?.historyToolbar?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        val dao = (application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{ allCompletedDates ->
               if(allCompletedDates.isNotEmpty()){
                   binding?.tvHistory?.visibility = View.VISIBLE
                   binding?.rvHistory?.visibility = View.VISIBLE
                   binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                   binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                   val dates = ArrayList<String>()
                   for(date in allCompletedDates){
                       dates.add(date.Date)
                   }
                   val historyAdapter = HistoryAdapter(dates)
                   binding?.rvHistory?.adapter = historyAdapter

               }else{
                   binding?.tvHistory?.visibility = View.GONE
                   binding?.rvHistory?.visibility = View.GONE
                   binding?.tvNoDataAvailable?.visibility = View.VISIBLE
               }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}