package com.application.ui.fragments.navigation.training.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.application.R
import com.application.data.models.Lesson
import com.application.data.models.LessonQuestion
import com.application.databinding.FragmentLessonItemViewBinding
import com.application.ui.fragments.navigation.training.adapters.LessonsRecyclerViewAdapter.RecyclerViewItemViewHolder

class LessonsRecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewItemViewHolder>() {

    var data: List<Lesson> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Lesson>) {
        data = newData

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewItemViewHolder {
        val binding = FragmentLessonItemViewBinding.inflate(LayoutInflater.from(parent.context))

        return RecyclerViewItemViewHolder(binding.root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewItemViewHolder, position: Int) {
        val lesson = data[position]
        holder.binding.lessonTextName.text = lesson.textName
        holder.binding.lessonTextAuthor.text = lesson.textAuthor
        holder.binding.lessonAgeCategory.text = lesson.ageMin.toString() + '+'
        holder.binding.lessonTextSize.text = lesson.size

        holder.binding.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("text", lesson.text)
            bundle.putInt("questions_count", lesson.questions.size)
            var question: LessonQuestion
            for (i in lesson.questions.indices) {
                question = lesson.questions[i]
                bundle.putString("question$i", question.question)
                bundle.putInt("answers_cnt$i", question.answers.size)
                for (j in question.answers.indices)
                    bundle.putString("answer${i}_$j", question.answers[j])
                bundle.putInt("correct_answer$i", question.correctAnswer)
            }
            findNavController((holder.itemView.context as Activity), R.id.nav_host_fragment)
                .navigate(R.id.action_trainingFragment_to_preparingFragment, bundle)
        }
    }

    override fun getItemCount(): Int = data.size

    class RecyclerViewItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        }

        var binding: FragmentLessonItemViewBinding = FragmentLessonItemViewBinding.bind(itemView)
    }
}