package ca.gbc.lab2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var count = 0
    private var step = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val textOutput = findViewById<TextView>(R.id.textOutput)

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            count += step
            textOutput.text = count.toString()
        }

        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)

        buttonSubtract.setOnClickListener {
            count-= step
            textOutput.text = count.toString()
        }

        val buttonReset = findViewById<Button>(R.id.buttonReset)

        buttonReset.setOnClickListener {
            count = 0
            step = 1
            textOutput.text = count.toString()
        }

        val buttonStep = findViewById<Button>(R.id.buttonStep)

        buttonStep.setOnClickListener {
            step = 2
        }
    }
}