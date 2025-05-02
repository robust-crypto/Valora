import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valora.Budget
import com.example.valora.databinding.BudgetCardItemBinding

class BudgetAdapter(private val budgetList: List<Budget>) :
    RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    inner class BudgetViewHolder(val binding: BudgetCardItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BudgetCardItemBinding.inflate(inflater, parent, false)
        return BudgetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = budgetList[position]

        // Set title
        holder.binding.budgetName.text = budget.title

        // Set category name to start time - adjust as needed
        holder.binding.categoryName.text = "Start: ${budget.startTime} | End: ${budget.endTime}"

        // Set description (can be actual amount)
        holder.binding.budgetDescription.text = "R${budget.amount}"

        // Set min/max
        holder.binding.minAmount.text = "Min: R${budget.minAmount}"
        holder.binding.maxAmount.text = "Max: R${budget.maxAmount}"

        // Set SeekBar progress (relative to amount)
        val progress = ((budget.amount - budget.minAmount) / (budget.maxAmount - budget.minAmount) * 100).toInt().coerceIn(0, 100)
        holder.binding.seekBarProgress.progress = progress
    }

    override fun getItemCount(): Int = budgetList.size
}
