package com.example.mobile_development_lab_08

import android.os.Bundle
import android.os.StrictMode
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.mobile_development_lab_08.ui.TaskListFragment

class TaskListActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Включаем режим строгой политики (StrictMode) для выявления потенциальных проблем с производительностью
        StrictMode.enableDefaults()

        // Устанавливаем макет активности из ресурса XML
        setContentView(R.layout.activity_task_list)

        // Проверяем, пуст ли контейнер для фрагмента (savedInstanceState будет null при первом создании активности)
        val isFragmentContainerEmpty = savedInstanceState == null

        // Если контейнер пуст, добавляем новый фрагмент PhotoGalleryFragment
        if (isFragmentContainerEmpty) {
            supportFragmentManager // Получаем экземпляр FragmentManager для управления фрагментами
                .beginTransaction() // Начинаем транзакцию фрагментов
                .add(R.id.fragmentContainer, TaskListFragment.newInstance()) // Добавляем новый экземпляр фрагмента в контейнер
                .commit() // Подтверждаем транзакцию
        }
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
//        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}