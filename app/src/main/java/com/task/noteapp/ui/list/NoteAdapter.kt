package com.task.noteapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.databinding.NoteItemBinding
import com.task.noteapp.model.Note


class NoteAdapter (
    val itemClicked: (Note) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list = AsyncListDiffer(this, NoterDiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder  {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Holder).bind(list.currentList[position])
    }

    override fun getItemCount() = list.currentList.size

    fun submitList(noteList: List<Note>) {
        this.list.submitList(noteList)
    }

    inner class Holder(val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.model = item
            binding.root.setOnClickListener {
                itemClicked(item)
            }
        }
    }

    object NoterDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

}