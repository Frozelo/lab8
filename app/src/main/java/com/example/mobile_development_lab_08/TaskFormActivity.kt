package com.example.mobile_development_lab_08

import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.mobile_development_lab_08.ui.TaskFormFragment
import com.example.mobile_development_lab_08.ui.TaskListFragment

class TaskFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Устанавливаем макет активности из ресурса XML
        setContentView(R.layout.activity_task_form)

        // Проверяем, пуст ли контейнер для фрагмента (savedInstanceState будет null при первом создании активности)
        val isFragmentContainerEmpty = savedInstanceState == null

        // Если контейнер пуст, добавляем новый фрагмент PhotoGalleryFragment
        if (isFragmentContainerEmpty) {
            supportFragmentManager // Получаем экземпляр FragmentManager для управления фрагментами
                .beginTransaction() // Начинаем транзакцию фрагментов
                .add(
                    R.id.fragmentContainer,
                    TaskFormFragment.newInstance()
                ) // Добавляем новый экземпляр фрагмента в контейнер
                .commit() // Подтверждаем транзакцию
        }

    }
}