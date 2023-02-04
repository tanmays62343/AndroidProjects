package com.TrX

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuizQuestion : AppCompatActivity() {

    private var progressBar : ProgressBar? = null
    private var tvprogress : TextView? = null
    private var tvQuestion : TextView? = null
    private var image : ImageView? = null

    private var optionOne : TextView? = null
    private var optionTwo : TextView? = null
    private var optionThree : TextView? = null
    private var optionFour : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        progressBar = findViewById(R.id.ProgressBar)
        tvprogress = findViewById(R.id.Progress)
        tvQuestion = findViewById(R.id.question)
        image = findViewById(R.id.image)
        optionOne = findViewById(R.id.tv_option_one)
        optionTwo = findViewById(R.id.tv_option_two)
        optionThree = findViewById(R.id.tv_option_three)
        optionFour = findViewById(R.id.tv_option_four)

        val QuestionsList = Constants.getQuestions()

        Log.i("Questions Size is :","${QuestionsList.size}")

        for(i in QuestionsList){
            Log.e("Questions",i.question)
        }

        var currentPosition = 1
        val question : Question = QuestionsList[currentPosition-1]
        image?.setImageResource(question.image)
        progressBar?.progress = currentPosition
        tvprogress?.text = "$currentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        optionOne?.text = question.optionOne
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

    }


}