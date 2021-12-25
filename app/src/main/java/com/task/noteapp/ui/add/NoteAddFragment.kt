package com.task.noteapp.ui.add

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentNoteAddBinding
import com.task.noteapp.model.Note
import com.task.noteapp.ui.list.BaseFragment
import com.task.noteapp.utils.getImageUrlWithAuthority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NoteAddFragment : BaseFragment<FragmentNoteAddBinding>(R.layout.fragment_note_add) {

    private val noteAddViewModel by viewModels<NoteAddViewModel>()

    private val SELECT_IMAGE_FROM_STORAGE = 101
    private val job = CoroutineScope(Dispatchers.Main)
    private var color = -16743235
    private val args: NoteAddFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
        if (args.note != null) previewNote()
    }

    private fun init() {
        binding.backBtn.setOnClickListener {
            findNavController().navigate(
                NoteAddFragmentDirections.actionNoteAddFragmentToNoteFragment()
            )
        }

        binding.saveNote.setOnClickListener {
            if (binding.etTitle.text.toString().isEmpty() ||
                    binding.etNoteContent.text.toString().isEmpty()) {
                Toast.makeText(context, "Title or Note Cannot Be Empty", Toast.LENGTH_SHORT).show()
            } else {
                noteAddViewModel.apply {
                    saveNote( Note(
                        if (args.note != null) args.note!!.id else 0,
                        binding.etTitle.text.toString(),
                        binding.etNoteContent.text.toString(),
                        getCurrentDate()!!,
                        color,
                        getImagePath(),
                        if (args.note != null) true else false,
                        binding.etUrl.text.toString()
                    ))
                }
                Toast.makeText(context, "Note Save Successful.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    NoteAddFragmentDirections.actionNoteAddFragmentToNoteFragment()
                )
            }
        }

        binding.binFab.setOnClickListener {
            args.note?.let {
                noteAddViewModel.deleteNote(it)
            }
            Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                NoteAddFragmentDirections.actionNoteAddFragmentToNoteFragment()
            )
        }

        binding.lastEdited.text =
            getString(R.string.date, SimpleDateFormat.getDateInstance().format(Date()))

        @Suppress("DEPRECATION")
        binding.noteImage.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also { chooseIntent ->
                chooseIntent.type = "image/*"

                chooseIntent.resolveActivity(activity?.packageManager!!.also {
                    try {
                        startActivityForResult(chooseIntent, SELECT_IMAGE_FROM_STORAGE)
                    } catch (ex: ActivityNotFoundException) {
                    }
                })
            }
        }

        binding.colorPicker.apply {
            setSelectedColor(color)
            setOnColorSelectedListener { value ->
                color = value
                binding.apply {
                    noteAddFragmentParent.setBackgroundColor(color)
                    toolbarFragmentNoteAdd.setBackgroundColor(color)
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == SELECT_IMAGE_FROM_STORAGE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                val selectedImagePath = getImageUrlWithAuthority(
                    requireContext(),
                    uri,
                    requireActivity()
                )
                noteAddViewModel.saveImagePath(selectedImagePath)
                setImage(selectedImagePath)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setImage(filePath: String?) {
        if (filePath != null) {
            val uri = Uri.fromFile(File(filePath))
            binding.noteImage.visibility = View.VISIBLE
            try {
                job.launch {
                    binding.noteImage.setImageURI(uri)
                }
            } catch (e: Exception) {
                binding.noteImage.visibility = View.GONE
            }
        } else binding.noteImage.visibility = View.GONE
    }

    fun previewNote() {
        args.note.apply {
            if (this?.imagePath != null && this.imagePath.isNotEmpty()) {
                val uri = Uri.fromFile(File(this?.imagePath))
                binding.noteImage.setImageURI(uri)
                val selectedImagePath = getImageUrlWithAuthority(
                    requireContext(),
                    uri,
                    requireActivity()
                )
                noteAddViewModel.saveImagePath(selectedImagePath)
            } else {
                binding.noteImage.setImageResource(R.drawable.image_background)
            }
            binding.noteAddFragmentParent.setBackgroundColor(this?.color!!)
            binding.colorPicker.setSelectedColor(this?.color!!)
            binding.etTitle.setText(this?.title)
            binding.etNoteContent.setText(this?.content)
            binding.lastEdited.setText(this?.date)
            binding.etUrl.setText(this?.url)
        }
        binding.binFab.visibility = View.VISIBLE
    }
}