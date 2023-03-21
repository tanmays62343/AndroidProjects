package com.Trx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.Trx.databinding.ActivityExcerciseBinding

class ExcerciseActivity : AppCompatActivity() {

    private var binding : ActivityExcerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercise = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbar?.setNavigationOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
        setRestView()

        exerciseList = Constants.defaultExerciseList()
    }
    private fun setRestView(){
        binding?.FrestView?.visibility = View.VISIBLE
        binding?.Title?.visibility = View.VISIBLE
        binding?.TvExerciseName?.visibility  = View.INVISIBLE
        binding?.TvImage?.visibility = View.INVISIBLE
        binding?.FexerciseProgressBar?.visibility = View.INVISIBLE

        if(restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }

        restProgressBar()
    }
    private fun setExerciseTimerView(){
        binding?.FrestView?.visibility = View.INVISIBLE
        binding?.Title?.visibility = View.INVISIBLE
        binding?.TvExerciseName?.visibility  = View.VISIBLE
        binding?.TvImage?.visibility = View.VISIBLE
        binding?.FexerciseProgressBar?.visibility = View.VISIBLE
        
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.TvImage?.setImageResource(exerciseList!![currentExercise].getImage())
        binding?.TvExerciseName?.text = exerciseList!![currentExercise].getName()

        exerciseProgressBar()
    }

    private fun restProgressBar (){
        binding?.ProgressBar?.progress = restProgress
        restTimer = object : CountDownTimer(1000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.ProgressBar?.progress = 10-restProgress
                binding?.timer?.text = (10-restProgress).toString()

            }
            override fun onFinish() {
                currentExercise++
                setExerciseTimerView()
            }
        }.start()
    }
    private fun exerciseProgressBar() {
        binding?.ProgressBar?.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.exerciseProgressBar?.progress = 30 - exerciseProgress
                binding?.exerciseTimer?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercise < exerciseList?.size!! - 1){
                    setRestView()
                }else{
                    Toast.makeText(this@ExcerciseActivity, "Congratulations",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding = null
    }
}