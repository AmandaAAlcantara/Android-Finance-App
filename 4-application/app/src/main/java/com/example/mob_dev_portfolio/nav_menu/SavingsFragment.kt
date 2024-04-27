import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.mob_dev_portfolio.SetGoalActivity
import com.example.mob_dev_portfolio.budget_transaction.AppDatabase
import com.example.mob_dev_portfolio.budget_transaction.Transaction
import com.example.mob_dev_portfolio.databinding.FragmentSavingsBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class SavingsFragment : Fragment() {

    private var _binding: FragmentSavingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactions: List<Transaction>
    private lateinit var savingsTransactions: List<Transaction>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var db: AppDatabase

    private val SET_GOAL_REQUEST_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        savingsTransactions = ArrayList()
        linearLayoutManager = LinearLayoutManager(context)

        db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "transactions").build()

        // Set click listener for the Set Your Saving Goal button
        binding.setGoalBtn.setOnClickListener {
            // Create an Intent to navigate to SetGoalActivity
            val intent = Intent(requireContext(), SetGoalActivity::class.java)
            // Start the activity for result
            startActivityForResult(intent, SET_GOAL_REQUEST_CODE)
        }
    }

    // Receive result from SetGoalActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SET_GOAL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Get the input from SetGoalActivity
            val savingsGoal = data?.getStringExtra("savingsGoal")
            // Do something with the savings goal input
            // For example, you can update your UI with the savings goal value
            binding.savingsGoal.text = savingsGoal

            // Check if achieved savings >= savings goal
            val achievedSavingsString = binding.achievedSavings.text.toString()
            val achievedSavings = achievedSavingsString.toFloatOrNull()
            if (achievedSavings != null && savingsGoal != null) {
                if (achievedSavings >= savingsGoal.toFloat()) {
                    binding.savingsStatus.text = "You have achieved your savings goal!"
                    // Trigger confetti animation
                    binding.confetti.build()
                        .addColors(Color.MAGENTA, Color.BLUE, Color.CYAN)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square, Shape.Circle)
                        .addSizes(Size(12))
                        .setPosition(-50f, binding.confetti.width + 50f, -50f, -50f)
                        .streamFor(300, 3000L)
                } else {
                    binding.savingsStatus.text = "You are almost there! Continue saving."
                }
            }
        }
    }

    private fun fetchSavingsTransactions() {
        GlobalScope.launch {
            transactions = db.transactionDao().getAll()
            requireActivity().runOnUiThread {
                totalAmountOfTransactions()
            }
        }
    }

    private fun totalAmountOfTransactions() {
        val budgetAmount = transactions.filter { it.amount > 0 }.map { it.amount }.sum()
        val totalAmount = transactions.map { it.amount }.sum()

        val savingsPercentage = if (budgetAmount != 0.0) {
            (totalAmount / budgetAmount) * 100
        } else {
            0.0
        }

        binding.percentageSavings.text = "%.2f".format(savingsPercentage)
        binding.achievedSavings.text = "%.2f".format(totalAmount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        fetchSavingsTransactions()
    }
}
