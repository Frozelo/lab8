package com.example.mobile_development_lab_08.ui

import TaskViewModel
import TaskViewModelFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mobile_development_lab_08.R
import com.example.mobile_development_lab_08.db.TaskDatabase
import com.example.mobile_development_lab_08.model.Priority
import com.example.mobile_development_lab_08.model.Task

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    // Используем viewModels для создания ViewModel с параметрами
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskDatabase.getDatabase(requireContext()).taskDao())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAddTask: Button = view.findViewById(R.id.buttonAddTask)
        val editTextTaskContent: EditText = view.findViewById(R.id.editTextTaskContent)
        val radioGroupPriority: RadioGroup = view.findViewById(R.id.radioGroupPriority)

        // Обработка нажатия на кнопку "Добавить задачу"
        buttonAddTask.setOnClickListener {
            val taskContent = editTextTaskContent.text.toString().trim()

            if (taskContent.isEmpty()) {
                // Устанавливаем ошибку, если поле пустое
                editTextTaskContent.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            val priority = when (radioGroupPriority.checkedRadioButtonId) {
                R.id.radioHigh -> Priority.HIGH.level
                R.id.radioMedium -> Priority.MEDIUM.level
                R.id.radioLow -> Priority.LOW.level
                else -> Priority.LOW.level // Значение по умолчанию, если ничего не выбрано
            }

            // Создаём новую задачу и добавляем её через ViewModel
            val newTask = Task(content = taskContent, priority = priority)
            taskViewModel.addTask(newTask.content, newTask.priority)

            // Закрываем текущую активность
            requireActivity().finish()
        }
    }

    companion object {
        fun newInstance() = TaskFormFragment() // Создание нового экземпляра фрагмента
    }
}
