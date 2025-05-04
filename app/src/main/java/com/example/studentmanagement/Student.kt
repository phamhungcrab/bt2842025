package com.example.studentmanagement

import java.io.Serializable

data class Student(
    val name: String,
    val mssv: String,
    val phone: String,
    val email: String
) : Serializable
