import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_development_lab_08.R
import com.example.mobile_development_lab_08.model.Task
import com.example.mobile_development_lab_08.ui.TaskHolder

class TaskAdapter(private var tasks: MutableList<Task>, private val context: Context) : RecyclerView.Adapter<TaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task_list, parent, false)
        return TaskHolder(view, context, this)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    // Метод для получения задачи по позиции (добавьте этот метод)
    fun getTaskAt(position: Int): Task {
        return tasks[position]
    }
}
