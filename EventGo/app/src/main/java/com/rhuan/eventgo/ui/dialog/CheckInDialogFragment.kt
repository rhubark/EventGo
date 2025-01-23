package com.rhuan.eventgo.ui.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

import androidx.fragment.app.DialogFragment
import com.rhuan.eventgo.databinding.FragmentDialogBinding
import com.rhuan.eventgo.domain.request.User
import com.rhuan.eventgo.ui.viewModels.EventFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckInDialogFragment(private val eventId: String) : DialogFragment() {

    private val viewModel: EventFragmentViewModel by viewModel()
    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val DIALOG_FRAGMENT_KEY = "MyDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)

        binding.cancelButton.setOnClickListener { dismiss() }

        binding.checkInButton.setOnClickListener {
            viewModel.setCheckIn(
                User(
                    eventId = eventId,
                    name = binding.nameEditText.text.toString(),
                    email = binding.emailEditText.text.toString()
                ))
            dismiss()
        }
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}