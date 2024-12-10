package com.example.mobile_development_lab_08.ui

import TaskViewModel
import TaskViewModelFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mobile_development_lab_08.R
import com.example.mobile_development_lab_08.db.TaskDatabase
import com.example.mobile_development_lab_08.model.Priority
import com.example.mobile_development_lab_08.model.Task
import com.google.android.material.textfield.TextInputEditText

class TaskFormFragment : Fragment(R.layout.fragment_task_form) {

    // Используем viewModels для создания ViewModel с параметрами
    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskDatabase.getDatabase(requireContext()).taskDao())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем ссылку на MenuHost для добавления меню во фрагменте
        val menuHost: MenuHost = requireActivity()

        // Добавляем MenuProvider для управления меню в этом фрагменте
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.menu_main, menu) // Инфляция меню из ресурса
//            }
//
//            override fun onMenuItemSelected(item: MenuItem): Boolean {
//                return when (item.itemId) {
//                    R.id.action_settings -> true
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner)

        val buttonAddTask: Button = view.findViewById(R.id.buttonAddTask)
        val editTextTaskContent: EditText = view.findViewById(R.id.editTextTaskContent)
        val radioGroupPriority: RadioGroup = view.findViewById(R.id.radioGroupPriority)

        // Обработка нажатия на кнопку "Добавить задачу"
        buttonAddTask.setOnClickListener {
            val taskContent = editTextTaskContent.text.toString()
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
        fun newInstance() = TaskFormFragment()  // Создание нового экземпляра фрагмента
    }
}
