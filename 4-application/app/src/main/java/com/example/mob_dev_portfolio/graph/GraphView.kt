package com.example.mob_dev_portfolio.graph
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.mob_dev_portfolio.R
import com.example.mob_dev_portfolio.budget_transaction.AppDatabase
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GraphView : AppCompatActivity() {
    private lateinit var graph: GraphView
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graph_view)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "transactions")
            .build()

        graph = findViewById(R.id.graph)
        val button = findViewById<Button>(R.id.addButton)

        graph.visibility = View.VISIBLE
        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val incomeTransactions = db.transactionDao().getAllTypeIncome()
                val dataPoints = incomeTransactions.mapIndexed { index, transaction ->
                    DataPoint(index.toDouble(), transaction.amount)
                }.toTypedArray()

                // Update UI on the main thread
                launch(Dispatchers.Main) {
                    val series = LineGraphSeries<DataPoint>(dataPoints)
                    graph.removeAllSeries() // Clear previous series so it can be updated
                    graph.addSeries(series)
                }

                    // Set manual x bounds to ensure that the x-values are appropriate for the graph
                    graph.viewport.isXAxisBoundsManual = true
                    graph.viewport.setMinX(0.0)
                    graph.viewport.setMaxX((incomeTransactions.size - 1).toDouble())
                }
            }
        }
    }