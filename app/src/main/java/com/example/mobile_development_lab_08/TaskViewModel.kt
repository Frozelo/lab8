import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_development_lab_08.db.TaskDao
import com.example.mobile_development_lab_08.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    // Создаём LiveData для хранения списка задач
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    fun addTask(content: String, priority: Int) {
        val task = Task(content = content, priority = priority)
        viewModelScope.launch {
            taskDao.insert(task)
            // После добавления задачи можно обновить список задач
            getTasks() // Обновляем список задач после добавления новой
        }
    }

    fun getTasks() {
        viewModelScope.launch {
            val tasksList = taskDao.getAllTasks()
            _tasks.postValue(tasksList) // Обновляем LiveData с новым списком задач
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
            getTasks() // Обновляем список задач после удаления
        }
    }
}
