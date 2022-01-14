package com.stefan.lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.stefan.lab4.model.Student
import com.stefan.lab4.ui.EditClickListener
import com.stefan.lab4.ui.StudentsViewModel

class StudentsRecyclerViewAdapter(
    owner: ViewModelStoreOwner,
    private val editClickListener: EditClickListener
) : RecyclerView.Adapter<StudentsRecyclerViewAdapter.ViewHolder>() {

    private val students: MutableList<Student> = ArrayList()
    private var studentsViewModel = ViewModelProvider(owner).get(StudentsViewModel::class.java)

    fun updateStudents(students: List<Student>?) {
        this.students.clear()
        if (students != null) {
            this.students.addAll(students)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val index: TextView = view.findViewById(R.id.tv_Index)
        val name: TextView = view.findViewById(R.id.tv_Name)
        val surname: TextView = view.findViewById(R.id.tv_Surname)
        val phone: TextView = view.findViewById(R.id.tv_Phone)
        val address: TextView = view.findViewById(R.id.tv_Address)
        val delete: Button = view.findViewById(R.id.btn_Delete)
        val edit: Button = view.findViewById(R.id.btn_Edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val studentView = inflater.inflate(R.layout.recyclerview_student_row, parent, false)

        return ViewHolder(studentView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studentItem: Student = students[position]

        holder.index.text = studentItem.index
        holder.name.text = studentItem.name
        holder.surname.text = studentItem.surname
        holder.phone.text = studentItem.phone
        holder.address.text = studentItem.address

        holder.delete.setOnClickListener {
            studentsViewModel.deleteStudent(holder.index.text.toString())
            students.clear()
        }

        holder.edit.setOnClickListener {
            editClickListener.onEditClickListener(studentItem)
        }

    }

    override fun getItemCount(): Int {
        return students.size
    }

}