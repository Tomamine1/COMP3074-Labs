package ca.gbc.lab4

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var swActive: Switch
    private lateinit var btnAdd: Button
    private lateinit var btnViewAll: Button
    private lateinit var lvCustomerList: ListView

    private lateinit var databaseHelper: DatabaseHelper

    private fun showCustomersOnListView() {

        val customers = databaseHelper.getEveryone()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            customers
        )

        lvCustomerList.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        swActive = findViewById(R.id.swActive)
        btnAdd = findViewById(R.id.btnAdd)
        btnViewAll = findViewById(R.id.btnViewAll)
        lvCustomerList = findViewById(R.id.lvCustomerList)

        databaseHelper = DatabaseHelper(this)

        btnAdd.setOnClickListener {

            try {
                val customer = CustomerModel(
                    name = etName.text.toString(),
                    age = etAge.text.toString().toInt(),
                    isActive = swActive.isChecked
                )

                val success = databaseHelper.addOne(customer)

                Log.d(
                    "INSERT",
                    "Insert executed. result: $success"
                )

                if (success) {

                    etName.text.clear()
                    etAge.text.clear()
                    swActive.isChecked = false

                    showCustomersOnListView()

                    Toast.makeText(
                        this,
                        "Customer added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "Please enter a valid name and age",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnViewAll.setOnClickListener {
            showCustomersOnListView()
        }

        lvCustomerList.setOnItemLongClickListener {
                parent, _, position, _ ->

            val selectedCustomer =
                parent.getItemAtPosition(position) as CustomerModel

            val deleted =
                databaseHelper.deleteOne(selectedCustomer)

            if (deleted) {

                showCustomersOnListView()

                Toast.makeText(
                    this,
                    "Customer deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }

            true
        }

        showCustomersOnListView()
    }
}
