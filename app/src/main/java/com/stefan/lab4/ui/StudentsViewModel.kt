package com.stefan.lab4.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.stefan.lab4.model.Student

class StudentsViewModel : ViewModel() {
    private var studentsLiveData: MutableLiveData<List<Student>> = MutableLiveData()

    private val mAuth = FirebaseAuth.getInstance()
    private val database =
        FirebaseDatabase.getInstance("firebasedatabase-url")
    private val studentsRef = database.getReference("students")

    fun getStudentsLiveData(): MutableLiveData<List<Student>> {
        return studentsLiveData
    }

    fun uploadStudent(
        index: String,
        name: String,
        surname: String,
        phone: String,
        address: String
    ) {
        val currentStudent = Student(mAuth.uid!!, index, name, surname, phone, address)

        studentsRef.push().setValue(currentStudent)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("StudentsViewModel", "Success")
                } else {
                    Log.i("StudentsViewModel", task.exception?.message.toString())
                }
            }
    }

    fun retrieveStudents() {
        val studentsList: MutableList<Student> = mutableListOf()

        studentsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (s in snapshot.children) {
                        val student = s.getValue(Student::class.java)
                        studentsList.add(student!!)
                    }
                    studentsLiveData.postValue(studentsList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun deleteStudent(index: String) {
        val query: Query =
            studentsRef.orderByChild("index").equalTo(index)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (studentSnapshot in dataSnapshot.children) {
                    studentSnapshot.ref.removeValue()
                    studentsLiveData.postValue(null)
                    retrieveStudents()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun editStudent(
        originIndex: String,
        index: String,
        name: String,
        surname: String,
        phone: String,
        address: String
    ) {

        val student = Student(mAuth.uid!!, index, name, surname, phone, address)

        val query: Query =
            studentsRef.orderByChild("index").equalTo(originIndex)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("StudentsViewModel", dataSnapshot.toString())
                for (studentSnapshot in dataSnapshot.children) {
                    Log.i("StudentsViewModel", studentSnapshot.toString())
                    studentSnapshot.ref.setValue(student)
                    studentsLiveData.postValue(null)
                    retrieveStudents()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

}