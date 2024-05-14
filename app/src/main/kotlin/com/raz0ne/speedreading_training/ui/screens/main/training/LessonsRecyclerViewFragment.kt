package com.raz0ne.speedreading_training.ui.screens.main.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raz0ne.speedreading_training.R
import com.raz0ne.speedreading_training.data.models.Lesson
import com.raz0ne.speedreading_training.databinding.FragmentLessonsRecyclerViewBinding
import com.raz0ne.speedreading_training.ui.screens.main.training.adapters.LessonsRecyclerViewAdapter
import com.raz0ne.speedreading_training.ui.screens.main.training.view_models.LessonsViewModel
import com.raz0ne.speedreading_training.ui.theme.AppTheme

class LessonsRecyclerViewFragment : Fragment() {

    private lateinit var binding: FragmentLessonsRecyclerViewBinding
    private lateinit var viewModel: LessonsViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLessonsRecyclerViewBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[LessonsViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        binding.recyclerView.addItemDecoration(itemDecorator)
        binding.recyclerView.adapter = LessonsRecyclerViewAdapter()

        viewModel.items.observe(viewLifecycleOwner) { value: List<Lesson> ->
            (binding.recyclerView.adapter!! as LessonsRecyclerViewAdapter).updateData(value) }
    }
}

@Composable
fun LessonsList(lessonsViewModel: LessonsViewModel) {
    LazyColumn {
        items(lessonsViewModel.items.value!!) { lesson ->
            LessonCard(lesson)
        }
    }
}

@Composable
fun LessonCard(lesson: Lesson) {
    Row(Modifier.fillMaxWidth().padding(4.dp)) {
        Column(Modifier.weight(0.5f)) {
            Text(text = lesson.textName, Modifier.padding(4.dp))
            Text(text = lesson.ageMin.toString(), Modifier.padding(4.dp))
        }
        Column(Modifier.weight(0.5f), horizontalAlignment = Alignment.End) {
            Text(text = lesson.textAuthor, Modifier.padding(4.dp))
            Text(text = lesson.size, Modifier.padding(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LessonCardPreview() {
    val lesson = Lesson("Name", "Author", "text", "small", 18, null)
    AppTheme {
        Scaffold { padding ->
            Box(modifier = Modifier.padding(padding)) {
                LessonCard(lesson = lesson)
            }
        }
    }
}