package com.example.studentmanagement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(context: Context, private val students: List<Student>) :
    ArrayAdapter<Student>(context, 0, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)
        val student = students[position]

        val nameTextView = view.findViewById<TextView>(R.id.textview_name)
        val mssvTextView = view.findViewById<TextView>(R.id.textview_mssv)
        nameTextView.text = student.name
        mssvTextView.text = student.mssv
        return view

    }
}