package com.application.ui.fragments.navigation.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.R
import com.application.data.models.Lesson
import com.application.databinding.FragmentLessonsRecyclerViewBinding
import com.application.ui.fragments.navigation.training.adapters.LessonsRecyclerViewAdapter
import com.application.ui.fragments.navigation.training.view_models.LessonsRecyclerViewModel
import java.util.*

class LessonsRecyclerViewFragment : Fragment() {

    private lateinit var binding: FragmentLessonsRecyclerViewBinding
    private lateinit var viewModel: LessonsRecyclerViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLessonsRecyclerViewBinding.inflate(inflater, container, false)

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
            (binding.recyclerView.adapter!! as LessonsRecyclerViewAdapter).updateData(value) }
    }
}