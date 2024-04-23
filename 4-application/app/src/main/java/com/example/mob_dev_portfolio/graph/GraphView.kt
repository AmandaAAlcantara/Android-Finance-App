package com.example.mob_dev_portfolio.graph

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mob_dev_portfolio.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class GraphView : AppCompatActivity() {
    private lateinit var firstNum_1: EditText
    private lateinit var secondNum_1: EditText
    private lateinit var firstNum_2: EditText
    private lateinit var secondNum_2: EditText
    private lateinit var firstNum_3: EditText
    private lateinit var secondNum_3: EditText
    private lateinit var firstNum_4: EditText
    private lateinit var secondNum_4: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graph_view)

        firstNum_1 = findViewById(R.id.firstNum_1)
        secondNum_1 = findViewById(R.id.secondNum_1)
        firstNum_2 = findViewById(R.id.firstNum_2)
        secondNum_2 = findViewById(R.id.secondNum_2)
        firstNum_3 = findViewById(R.id.firstNum_3)
        secondNum_3 = findViewById(R.id.secondNum_3)
        firstNum_4 = findViewById(R.id.firstNum_4)
        secondNum_4 = findViewById(R.id.secondNum_4)

        val graph = findViewById<GraphView>(R.id.graph)
        val button = findViewById<Button>(R.id.addButton)

        graph.visibility = View.VISIBLE
        button.setOnClickListener {
            val firstInput_1 = firstNum_1.text.toString()
            val secondInput_1 = secondNum_1.text.toString()
            val firstInput_2 = firstNum_2.text.toString()
            val secondInput_2 = secondNum_2.text.toString()
            val firstInput_3 = firstNum_3.text.toString()
            val secondInput_3 = secondNum_3.text.toString()
            val firstInput_4 = firstNum_4.text.toString()
            val secondInput_4 = secondNum_4.text.toString()

            try {
                val series = LineGraphSeries<DataPoint>(arrayOf(
                    DataPoint(0.0, 1.0),
                    DataPoint(firstInput_1.toDouble(), secondInput_1.toDouble()),
                    DataPoint(firstInput_2.toDouble(), secondInput_2.toDouble()),
                    DataPoint(firstInput_3.toDouble(), secondInput_3.toDouble()),
                    DataPoint(firstInput_4.toDouble(), secondInput_4.toDouble())
                ))
                graph.addSeries(series)
            } catch (e: NumberFormatException) {
                Toast.makeText(this@GraphView, "Invalid input", Toast.LENGTH_LONG).show()
            }
        }
    }
}
