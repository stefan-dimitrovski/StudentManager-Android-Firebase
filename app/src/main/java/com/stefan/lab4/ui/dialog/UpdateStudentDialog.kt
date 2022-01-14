package com.stefan.lab4.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.stefan.lab4.R

class UpdateStudentDialog : DialogFragment() {
    private var origin: String? = null

    companion object {
        private const val ARG_ORIGIN = "argOrigin"

        fun newInstance(origin: String) = UpdateStudentDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_ORIGIN, origin)
            }
        }
    }

    lateinit var listener: UpdateStudentDialogListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            origin = it.getString(ARG_ORIGIN)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity?.layoutInflater

        val view = layoutInflater?.inflate(R.layout.dialog_layout, null)

        val indexTE: EditText? = view?.findViewById(R.id.et_Index)
        val nameTE: EditText? = view?.findViewById(R.id.et_Name)
        val surnameTE: EditText? = view?.findViewById(R.id.et_Surname)
        val phoneTE: EditText? = view?.findViewById(R.id.et_Phone)
        val addressTE: EditText? = view?.findViewById(R.id.et_Address)

        builder.setView(view)
            .setTitle("Update Student Information")
            .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                if (!indexTE?.text.isNullOrEmpty() ||
                    !nameTE?.text.isNullOrEmpty() ||
                    !surnameTE?.text.isNullOrEmpty() ||
                    !phoneTE?.text.isNullOrEmpty() ||
                    !addressTE?.text.isNullOrEmpty()
                ) {
                    val index: String = indexTE?.text.toString()
                    val name: String = nameTE?.text.toString()
                    val surname: String = surnameTE?.text.toString()
                    val phone: String = phoneTE?.text.toString()
                    val address: String = addressTE?.text.toString()

                    listener.editStudent(origin!!, index, name, surname, phone, address)
                } else {
                    return@OnClickListener
                }
            })
            .setNegativeButton("Exit") { _, _ ->

            }

        return builder.create()
    }

    fun setUpdateStudentDialogListener(listener: UpdateStudentDialogListener) {
        this.listener = listener
    }

    interface UpdateStudentDialogListener {
        fun editStudent(
            originIndex: String,
            index: String,
            name: String,
            surname: String,
            phone: String,
            address: String
        )
    }
}