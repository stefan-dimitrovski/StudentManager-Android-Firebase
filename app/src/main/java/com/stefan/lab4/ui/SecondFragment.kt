package com.stefan.lab4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stefan.lab4.StudentsRecyclerViewAdapter
import com.stefan.lab4.databinding.FragmentSecondBinding
import com.stefan.lab4.model.Student
import com.stefan.lab4.ui.dialog.UpdateStudentDialog

class SecondFragment : Fragment(), EditClickListener,
    UpdateStudentDialog.UpdateStudentDialogListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentAdapter: StudentsRecyclerViewAdapter
    private lateinit var studentsViewModel: StudentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentAdapter = StudentsRecyclerViewAdapter(this, this)

        studentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        studentsViewModel.retrieveStudents()

        binding.rvStudents.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvStudents.adapter = studentAdapter

        studentsViewModel.getStudentsLiveData().observe(viewLifecycleOwner) {
            studentAdapter.updateStudents(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditClickListener(data: Student) {
        openDialog(data)
    }

    private fun openDialog(data: Student) {
        val dialogInstance = UpdateStudentDialog.newInstance(data.index)
        dialogInstance.setUpdateStudentDialogListener(this)
        dialogInstance.show(childFragmentManager, "Update")
    }

    override fun editStudent(
        originIndex: String,
        index: String,
        name: String,
        surname: String,
        phone: String,
        address: String
    ) {
        studentsViewModel.editStudent(originIndex, index, name, surname, phone, address)
    }
}

interface EditClickListener {
    fun onEditClickListener(data: Student)
}