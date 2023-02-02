package com.TrX

object Constants {

    fun getQuestions():ArrayList<Question>{
        val questions = ArrayList<Question>()

        val ques1 = Question(
            1,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_argentina,
            "Argentina","Vietnam","Russia","Kuwait",
            1
        )
        questions.add(ques1)

        val ques2 = Question(
            2,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_belgium,
            "Jordan","Sudan","Israel","Belgium",
            4
        )
        questions.add(ques2)

        val ques3 = Question(
            3,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_brazil,
            "Jordan","Brazil","Israel","china",
            4
        )
        questions.add(ques3)

        val ques4 = Question(
            4,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_australia,
            "australia","Brazil","Israel","china",
            1
        )
        questions.add(ques4)

        val ques5 = Question(
            5,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_denmark,
            "australia","Brazil","Israel","Denmark",
            4
        )
        questions.add(ques4)

        val ques6 = Question(
            6,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_fiji,
            "australia","fiji","Israel","china",
            2
        )
        questions.add(ques4)

        val ques7 = Question(
            7,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_india,
            "India","Brazil","Israel","USA",
            1
        )
        questions.add(ques4)

        val ques8 = Question(
            8,
            "What country does this flag belong to :",
            R.drawable.ic_flag_of_new_zealand,
            "India","Brazil","New Zealand","USA",
            3
        )
        questions.add(ques4)

        return questions
    }

}