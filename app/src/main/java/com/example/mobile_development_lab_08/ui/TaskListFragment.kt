package com.example.mobile_development_lab_08.ui

import TaskAdapter
import TaskViewModel
import TaskViewModelFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mobile_development_lab_08.R
import com.example.mobile_development_lab_08.db.TaskDatabase
import com.example.mobile_development_lab_08.model.Priority
import com.example.mobile_development_lab_08.model.Task

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter // Предполагается, что у вас есть адаптер для задач

    // Используем viewModels для создания ViewModel с параметрами
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskDatabase.getDatabase(requireContext()).taskDao())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Инициализация адаптера с пустым списком задач
        adapter = TaskAdapter(mutableListOf(), context = requireContext())
        recyclerView.adapter = adapter

        // Наблюдаем за изменениями в списке задач
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            // Обновляем адаптер с новым списком задач
            adapter.updateTasks(tasks)
        }

        // Пример добавления задачи (можно убрать или изменить по необходимости)
        taskViewModel.addTask(content = "Сделать домашнее задание", priority = Priority.MEDIUM.level)

        // Добавление функциональности свайпа для удаления элементов
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false // Не обрабатываем перемещение
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Удаляем элемент из адаптера по позиции
                val position = viewHolder.adapterPosition
                val taskToDelete = adapter.getTaskAt(position) // Получаем задачу по позиции (добавьте этот метод в адаптер)
                taskViewModel.deleteTask(taskToDelete) // Удаляем задачу через ViewModel
                adapter.removeItem(position) // Метод удаления элемента в адаптере
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    companion object {
        fun newInstance() = TaskListFragment()  // Создание нового экземпляра фрагмента
    }
}
