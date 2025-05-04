package com.example.studentmanagement

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val students = mutableListOf<Student>()
    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter
    private val REQUEST_ADD_STUDENT = 1
    private val REQUEST_EDIT_STUDENT = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_student)


            students.add(Student("Nguyen Van A", "123456", "01200000009", "abcdjjh@jjjjj"))
            students.add(Student("Nguyen Van B", "123457", "01200000009", "abcdjjh@jjjjj"))


        adapter = StudentAdapter(this, students)
        listView.adapter = adapter

        registerForContextMenu(listView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_add_student -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD_STUDENT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.student_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val student = students[position]

        return when (item.itemId) {
            R.id.action_update -> {
                editStudent(position)
                true
            }
            R.id.action_delete -> {
                deleteStudent(position)
                true
            }
            R.id.action_phone -> {
                makeCall(student.phone)
                true
            }
            R.id.action_email -> {
                sendEmail(student.email)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun editStudent(position: Int) {
        Intent(this, AddStudentActivity::class.java).apply {
            putExtra("student", students[position])
            putExtra("position", position)
            startActivityForResult(this, REQUEST_EDIT_STUDENT)
        }
    }

    private fun deleteStudent(position: Int){
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes") { _, _ ->
                students.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun makeCall(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply{
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(intent)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val resultStudent = data?.getSerializableExtra("student") as? Student ?: return

            when (requestCode){
                REQUEST_ADD_STUDENT -> {
                    students.add(resultStudent)
                }
                REQUEST_EDIT_STUDENT -> {
                    val position = data.getIntExtra("position", -1)
                    if (position != -1) {
                        students[position] = resultStudent
                    }
                }
            }
            adapter.notifyDataSetChanged()
        }


    }



}