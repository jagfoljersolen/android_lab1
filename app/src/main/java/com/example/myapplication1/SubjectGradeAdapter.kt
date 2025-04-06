package com.example.myapplication1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class SubjectGrade (
    var subjectName : String,
    var grade : Int = 2
)
class SubjectGradeAdapter (private val subjects: List<SubjectGrade>)
    : RecyclerView.Adapter<SubjectGradeAdapter.SubjectGradeViewHolder>()
{

    inner class SubjectGradeViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
                val subjectNameTextView: TextView = itemView.findViewById(R.id.subjectName)
                val gradesRadioGroup: RadioGroup = itemView.findViewById(R.id.grades)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectGradeViewHolder {
        val view = LayoutInflater.from(parent.context)
                            .inflate(R.layout.subject_grade_item, parent, false)

        return SubjectGradeViewHolder(view)
    }
    override fun onBindViewHolder(holder: SubjectGradeViewHolder, position: Int) {
        val currentSubject = subjects[position]
        holder.subjectNameTextView.text = currentSubject.subjectName

        when (currentSubject.grade) {
            2 -> holder.gradesRadioGroup.check(R.id.ndst)
            3 -> holder.gradesRadioGroup.check(R.id.dst)
            4 -> holder.gradesRadioGroup.check(R.id.db)
            5 -> holder.gradesRadioGroup.check(R.id.bdb)
            else -> holder.gradesRadioGroup.clearCheck()
        }

        holder.gradesRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedGrade = when (checkedId) {
                R.id.ndst -> 2
                R.id.dst -> 3
                R.id.db -> 4
                R.id.bdb -> 5
                else -> 2
            }
            currentSubject.grade = selectedGrade
        }
    }


    override fun getItemCount() = subjects.size
}

