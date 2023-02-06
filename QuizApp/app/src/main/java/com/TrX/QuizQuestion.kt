package com.TrX

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.AdaptiveIconDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestion : AppCompatActivity(), View.OnClickListener {

    private var mUserName : String? = null


    private var myCurrentPosition : Int = 1
    private var myQuestionsList : ArrayList<Question>? = null
    private var mySelectedOption : Int = 0
    private var mCorrectAnswers : Int = 0

    private var progressBar : ProgressBar? = null
    private var tvprogress : TextView? = null
    private var tvQuestion : TextView? = null
    private var image : ImageView? = null
    private var submitBtn : Button? = null

    private var optionOne : TextView? = null
    private var optionTwo : TextView? = null
    private var optionThree : TextView? = null
    private var optionFour : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.User_name)
        progressBar = findViewById(R.id.ProgressBar)
        tvprogress = findViewById(R.id.Progress)
        tvQuestion = findViewById(R.id.question)
        image = findViewById(R.id.image)
        submitBtn = findViewById(R.id.Submit)
        optionOne = findViewById(R.id.tv_option_one)
        optionTwo = findViewById(R.id.tv_option_two)
        optionThree = findViewById(R.id.tv_option_three)
        optionFour = findViewById(R.id.tv_option_four)

        optionOne?.setOnClickListener(this)
        optionTwo?.setOnClickListener(this)
        optionThree?.setOnClickListener(this)
        optionFour?.setOnClickListener(this)
        submitBtn?.setOnClickListener(this)

        setQuestion()
        defaultOptionsView()
    }

    private fun setQuestion() {
        defaultOptionsView()
        myQuestionsList = Constants.getQuestions()

        val question: Question = myQuestionsList!![myCurrentPosition - 1]
        image?.setImageResource(question.image)
        progressBar?.progress = myCurrentPosition
        tvprogress?.text = "$myCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        optionOne?.text = question.optionOne
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

        if(myCurrentPosition == myQuestionsList!!.size){
            submitBtn?.text = "Finish"
        }
    }
    private fun defaultOptionsView(){
        val options = arrayListOf<TextView>()
        submitBtn?.text = "Submit"

        optionOne?.let {
            options.add(0,it )
        }
        optionTwo?.let {
            options.add(1,it)
        }
        optionThree?.let {
            options.add(2,it)
        }
        optionFour?.let {
            options.add(3,it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }

    }

    private fun selectedOptionView(tv:TextView, selectedOptionNum: Int){
        defaultOptionsView()

        mySelectedOption = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_btn_border
        )

    }

    override fun onClick(view : View?) {
        when(view?.id){
            R.id.tv_option_one -> {
                optionOne?.let {
                    selectedOptionView(it,1)
                }
            }
            R.id.tv_option_two -> {
                optionTwo?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.tv_option_three -> {
                optionThree?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.tv_option_four -> {
                optionFour?.let {
                    selectedOptionView(it,4)
                }
            }
            R.id.Submit -> {
                if(mySelectedOption == 0) {
                    myCurrentPosition++
                    when {
                        myCurrentPosition <= myQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else ->{
                            val intent = Intent(this,ActivityResult::class.java)
                            intent.putExtra(Constants.User_name,mUserName)
                            intent.putExtra(Constants.CorrectAnswers,mCorrectAnswers)
                            intent.putExtra(Constants.TotalQuestions,myQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val question = myQuestionsList?.get(myCurrentPosition -1)
                    if(question!!.correctAnswer != mySelectedOption) {
                        answerView(mySelectedOption, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                    if(myCurrentPosition == myQuestionsList!!.size){
                        submitBtn?.text = "Finish"
                    }else{
                        submitBtn?.text ="Next question"
                    }
                    mySelectedOption = 0
                }
            }
        }
    }

    private fun answerView(answer : Int , drawableView: Int){
        when(answer){
            1 -> {
                optionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                optionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                optionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                optionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }


}