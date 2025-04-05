package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class GradesForm : AppCompatActivity() {

    private lateinit var avgButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var subjectsList: MutableList<SubjectGrade>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grades_form)

        val oceny = intent.getIntExtra("oceny", 2)

        val subjectsArray = resources.getStringArray(R.array.subjects)

        subjectsList = subjectsArray.take(oceny).map { SubjectGrade(it) }.toMutableList()

        recyclerView = findViewById(R.id.subjectsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SubjectGradeAdapter(subjectsList)

        avgButton = findViewById(R.id.calculate_avg)

        avgButton.setOnClickListener {
            val sumOfGrades = subjectsList.sumOf { it.grade }
            val avgGrade = sumOfGrades.toDouble() / subjectsList.size
            println(avgGrade)
            val result = Intent()
            result.putExtra("average_grade", avgGrade)
            setResult(RESULT_OK, result)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val gradesArray = subjectsList.map { it.grade }.toIntArray()
        outState.putIntArray("grades", gradesArray)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val restoredGrades = savedInstanceState.getIntArray("grades")
        if (restoredGrades != null) {
            for (i in subjectsList.indices) {
                subjectsList[i].grade = restoredGrades[i]
            }

            recyclerView.adapter?.notifyDataSetChanged()
        }
    }
}
