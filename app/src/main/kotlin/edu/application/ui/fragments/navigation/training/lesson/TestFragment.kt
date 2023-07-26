package edu.application.ui.fragments.navigation.training.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import edu.application.R
import edu.application.databinding.FragmentLessonReadingQuestionsBinding

class TestFragment : Fragment() {

    private lateinit var binding: FragmentLessonReadingQuestionsBinding
    private var questionIdx = 0
    private var correctAnswers = 0

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLessonReadingQuestionsBinding
            .inflate(inflater, container, false)

        val answers = ArrayList<String>()

        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_list_item_1, answers) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val textView = super.getView(position, convertView, parent) as TextView
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                return textView
            }
        }
        binding.answersList.adapter = adapter

        binding.questionTv.text = arguments!!.getString("question" + 0)
        for (j in 0 until arguments!!.getInt("answers_cnt" + 0))
            answers.add(arguments!!.getString("answer0_$j")!!)
        adapter.notifyDataSetChanged()

        binding.answersList.onItemClickListener = OnItemClickListener { _: AdapterView<*>?,
                                                                        _: View?,
                                                                        position: Int, _: Long ->
            if (position + 1 == arguments!!.getInt("correct_answer$questionIdx"))
                correctAnswers++
            questionIdx++
            if (questionIdx == arguments!!.getInt("questions_count")) {
                val bundle = Bundle()
                bundle.putInt("correct_answers", correctAnswers)
                bundle.putInt("questions_cnt", arguments!!.getInt("questions_count"))
                findNavController(requireActivity(), R.id.nav_host_fragment).navigate(
                    R.id.action_lessonReadingQuestionsFragment_to_lessonReadingResultFragment,
                    bundle)
            }
            else {
                answers.clear()
                binding.questionTv.text = arguments!!.getString("question$questionIdx")
                for (j in 0 until arguments!!.getInt("answers_cnt$questionIdx"))
                    answers.add(arguments!!.getString("answer" + questionIdx + '_' + j)!!)
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }
}