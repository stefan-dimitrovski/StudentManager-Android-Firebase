package com.stefan.lab4.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.stefan.lab4.R
import com.stefan.lab4.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var studentsViewModel: StudentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        binding.btnUpload.setOnClickListener {
            val index = binding.etIndex.text.toString()
            val name = binding.etName.text.toString()
            val surname = binding.etSurname.text.toString()
            val phone = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()

            if (index.isNullOrEmpty() ||
                name.isNullOrEmpty() ||
                surname.isNullOrEmpty() ||
                phone.isNullOrEmpty() ||
                address.isNullOrEmpty()
            ) {
                return@setOnClickListener
            }
            studentsViewModel.uploadStudent(index, name, surname, phone, address)
            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
            binding.etIndex.text.clear()
            binding.etName.text.clear()
            binding.etSurname.text.clear()
            binding.etPhone.text.clear()
            binding.etAddress.text.clear()
        }

        binding.btnViewStudents.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}