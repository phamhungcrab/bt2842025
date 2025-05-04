package com.example.studentmanagement

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddStudentActivity : AppCompatActivity() {
    private var isEditMode = false
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student2)

        val editName = findViewById<EditText>(R.id.edit_name)
        val editMssv = findViewById<EditText>(R.id.edit_mssv)
        val editPhone = findViewById<EditText>(R.id.edit_phone)
        val editEmail = findViewById<EditText>(R.id.edit_email)
        val buttonSave = findViewById<Button>(R.id.button_save)

        intent.getSerializableExtra("student")?.let{
            val student = it as Student
            isEditMode = true
            position = intent.getIntExtra("position", -1)
            editName.setText(student.name)
            editMssv.setText(student.mssv)
            editPhone.setText(student.phone)
            editEmail.setText(student.email)
        }

        buttonSave.setOnClickListener {
            val name = editName.text.toString()
            val mssv = editMssv.text.toString()
            val phone = editPhone.text.toString()
            val email = editEmail.text.toString()

            if (name.isEmpty() || mssv.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                return@setOnClickListener
            }

            val resultStudent = Student(name, mssv, phone, email)
            val intent = intent.apply {
                putExtra("student", resultStudent)
                if (isEditMode) {
                    putExtra("position", position)
                }
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}