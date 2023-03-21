package com.Trx

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.Trx.databinding.ActivityExcerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExcerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding : ActivityExcerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercise = -1

    private var tts : TextToSpeech? = null
    private var player : MediaPlayer? = null

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

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
    }
    private fun setRestView(){

        try{
            val sound = Uri.parse("android.resource://com.Trx/"+R.raw.app_src_main_res_raw_press_start)
            player = MediaPlayer.create(applicationContext,sound)
            player?.isLooping = false
            player?.start()
        }catch(e:java.lang.Exception){
            e.printStackTrace()
        }

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

        speakOut(exerciseList!![currentExercise].getName())

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
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
        }
        binding = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text : String){
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }
}