package com.example.mobile_development_lab_08.ui

import TaskAdapter
import TaskViewModel
import TaskViewModelFactory
import android.content.Intent
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
import com.example.mobile_development_lab_08.TaskFormActivity
import com.example.mobile_development_lab_08.db.TaskDatabase
import com.example.mobile_development_lab_08.model.Priority
import com.example.mobile_development_lab_08.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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
//        taskViewModel.addTask(content = "Сделать домашнее задание", priority = Priority.MEDIUM.level)
        taskViewModel.getTasks()
        // Добавление функциональности свайпа для удаления элементов
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false // Не обрабатываем перемещение
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Удаляем элемент из адаптера по позиции
                val position = viewHolder.adapterPosition
                val taskToDelete = adapter.getTaskAt(position)
                taskViewModel.deleteTask(taskToDelete)
                adapter.removeItem(position)

                // Показываем Snackbar для отмены удаления (если нужно)
                Snackbar.make(view, "Задача удалена", Snackbar.LENGTH_LONG)
                    .setAction("Отменить") {
                        taskViewModel.addTask(taskToDelete.content, taskToDelete.priority) // Возвращаем задачу обратно в список
                    }.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Обработка нажатия на FloatingActionButton (FAB)
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            // Здесь можно открыть диалоговое окно или активность для добавления новой задачи.
            // Например, просто добавим новую задачу с фиксированным содержимым:
//            taskViewModel.addTask(content = "Новая задача", priority = Priority.MEDIUM.level)
            val intent = Intent(requireContext(), TaskFormActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        // Обновляем список задач при возвращении к активности
        taskViewModel.getTasks() // Метод для получения задач из базы данных
    }

    companion object {
        fun newInstance() = TaskListFragment()  // Создание нового экземпляра фрагмента
    }
}
