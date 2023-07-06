package edu.application.ui.fragments.recycler_view_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import edu.application.R
import edu.application.data.models.Lesson
import edu.application.databinding.LessonsRecyclerViewFragmentBinding
import edu.application.ui.adapters.LessonsRecyclerViewAdapter
import edu.application.ui.view_models.LessonsRecyclerViewModel
import java.util.*

class LessonRecyclerViewFragment : Fragment() {

    private lateinit var binding: LessonsRecyclerViewFragmentBinding
    private lateinit var viewModel: LessonsRecyclerViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = LessonsRecyclerViewFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[LessonsRecyclerViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        binding.recyclerView.addItemDecoration(itemDecorator)
        binding.recyclerView.adapter = LessonsRecyclerViewAdapter()

        viewModel.items.observe(viewLifecycleOwner) { value: List<Lesson> ->
            (binding.recyclerView.adapter!! as LessonsRecyclerViewAdapter).updateData(value)
        }
    }
}