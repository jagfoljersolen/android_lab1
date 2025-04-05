package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged


class MainActivity : AppCompatActivity() {

    private lateinit var imie: EditText
    private lateinit var nazwisko: EditText
    private lateinit var oceny: EditText
    private lateinit var ocenyButton : Button
    private lateinit var avg: TextView
    private lateinit var endButton : Button


    private var imieValid = false
    private var nazwiskoValid = false
    private var ocenyValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imie = findViewById(R.id.imie)
        nazwisko = findViewById(R.id.nazwisko)
        oceny = findViewById(R.id.oceny)
        ocenyButton = findViewById(R.id.oceny_button)
        avg = findViewById(R.id.avg)
        endButton = findViewById(R.id.end_button)

        imie.doAfterTextChanged { s ->
            imieValid = s?.isNotBlank() == true
            updateButton()
        }

        nazwisko.doAfterTextChanged { s ->
            nazwiskoValid = s?.isNotBlank() == true
            updateButton()
        }

        oceny.doAfterTextChanged { s ->
            val input = s.toString()
            val number = input.toIntOrNull()
            ocenyValid = number != null && number in 5..15
            updateButton()
        }

        imie.setOnFocusChangeListener { _, hasFocus ->
            avg.visibility = View.GONE
            endButton.visibility = View.GONE
            if(!hasFocus){
                imieValid = imie.text.isNotBlank()
                if(!imieValid) {
                    imie.setError(getString(R.string.empty_name))
                    Toast.makeText(this, getString(R.string.empty_name), Toast.LENGTH_SHORT).show()
                }

            }
            updateButton()
        }
        nazwisko.setOnFocusChangeListener { _, hasFocus ->
            avg.visibility = View.GONE
            endButton.visibility = View.GONE
            if(!hasFocus) {
                nazwiskoValid = nazwisko.text.isNotBlank()
                if (!nazwiskoValid) {
                    nazwisko.setError(getString(R.string.empty_surname))
                    Toast.makeText(this, getString(R.string.empty_surname), Toast.LENGTH_SHORT).show()
                }

            }
            updateButton()
        }

        oceny.setOnFocusChangeListener { _, hasFocus ->
            avg.visibility = View.GONE
            endButton.visibility = View.GONE
            if(!hasFocus){
                val input = oceny.text.toString()
                val number = input.toIntOrNull()
                ocenyValid = number != null && number in 5..15 && oceny.text.isNotBlank()
                if(!ocenyValid){
                    oceny.setError(getString(R.string.invalid_grades))
                    Toast.makeText(this, getString(R.string.invalid_grades), Toast.LENGTH_SHORT).show()
                }

            }
            updateButton()
        }

        ocenyButton.setOnClickListener {
            val intent = Intent(this, GradesForm::class.java)
            val input = oceny.text.toString()
            val number = input.toIntOrNull()
            intent.putExtra("oceny", number)
            startActivityForResult(intent, 1)
        }
    }
    private fun updateButton() {
        ocenyButton.visibility = if (imieValid && nazwiskoValid && ocenyValid) Button.VISIBLE else Button.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("imieValid", imieValid)
        outState.putBoolean("nazwiskoValid", nazwiskoValid)
        outState.putBoolean("ocenyValid", ocenyValid)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imieValid = savedInstanceState.getBoolean("imieValid")
        nazwiskoValid = savedInstanceState.getBoolean("nazwiskoValid")
        ocenyValid = savedInstanceState.getBoolean("ocenyValid")
        updateButton()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1 && resultCode==RESULT_OK) {
            val avgGrade = data?.getDoubleExtra("average_grade", 0.0)
            avg.visibility = View.VISIBLE
            avg.text = getString(R.string.avg) + " " + String.format("%.2f", avgGrade)
            ocenyButton.visibility = View.GONE

            if (avgGrade != null) {
                if(avgGrade >= 3.0){
                    endButton.text = getString(R.string.super_b)
                    endButton.visibility = View.VISIBLE

                    endButton.setOnClickListener {
                        Toast.makeText(this,getString(R.string.gratulacje), Toast.LENGTH_LONG).show()
                        finish()
                    }
                }else{
                    endButton.text = getString(R.string.nie_poszlo_b)
                    endButton.visibility = View.VISIBLE

                    endButton.setOnClickListener {
                        Toast.makeText(this,getString(R.string.warunek), Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }

    }
}