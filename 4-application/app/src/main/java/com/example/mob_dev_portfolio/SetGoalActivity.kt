package com.example.mob_dev_portfolio
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class SetGoalActivity : AppCompatActivity() {

    private lateinit var setGoalInput: TextInputEditText
    private lateinit var addGoalBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_goal)

        setGoalInput = findViewById(R.id.setGoalInput)
        addGoalBtn = findViewById(R.id.addGoalBtn)

        addGoalBtn.setOnClickListener {
            val goalAmount = setGoalInput.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("goalAmount", goalAmount)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
