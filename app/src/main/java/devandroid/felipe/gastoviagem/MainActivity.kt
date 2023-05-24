package devandroid.felipe.gastoviagem

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import devandroid.felipe.gastoviagem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enabledButton(false)

        val travelExpenses = Viagem()


        binding.editDistance.setOnFocusChangeListener { _, _ ->
            val fieldDistanceFocused = binding.editDistance.isFocused

            if(fieldDistanceFocused) {
                travelExpenses.distance = capturarData(binding.editDistance, travelExpenses)
            } else {
                travelExpenses.distance = binding.editDistance.text.toString()
                if(validateData(travelExpenses)) enabledButton(true) else enabledButton(false)
            }
        }


        binding.editPrice.setOnFocusChangeListener { _, _ ->
            val fieldPriceFocused = binding.editPrice.isFocused

            if(fieldPriceFocused) {
                travelExpenses.price = capturarData(binding.editPrice, travelExpenses)
            } else {
                travelExpenses.price = binding.editPrice.text.toString()
                if(validateData(travelExpenses)) enabledButton(true) else enabledButton(false)
            }
        }


        binding.editAutonomy.setOnFocusChangeListener { _, _ ->
            val fieldAutonomyFocused = binding.editAutonomy.isFocused

            if(fieldAutonomyFocused) {
                travelExpenses.autonomy = capturarData(binding.editAutonomy, travelExpenses)
            } else {
                travelExpenses.autonomy = binding.editAutonomy.text.toString()
                if(validateData(travelExpenses)) enabledButton(true) else enabledButton(false)
            }
        }


        binding.buttonCalculate.setOnClickListener {
            calculate(travelExpenses)
        }

    }

    private fun capturarData(field: EditText, dataField: Viagem) : String {
        var valueField = field.text.toString()

        field.setOnKeyListener { _, i, event ->
            if(i == KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                valueField = field.text.toString()
                if(validateData(dataField)) enabledButton(true) else enabledButton(false)
                return@setOnKeyListener true

            } else if(i == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_UP) {
                valueField = field.text.toString()

                when {
                    valueField.isBlank() || valueField.isEmpty() -> enabledButton(false)
                    else -> if(validateData(dataField)) enabledButton(true)
                }
            }
            return@setOnKeyListener false
        }

        return valueField

    }

    private fun validateData(information: Viagem) : Boolean {
        return (information.distance.isNotEmpty()
                && information.distance.isNotBlank()
                && information.price.isNotEmpty()
                && information.price.isNotBlank()
                && information.autonomy.isNotEmpty()
                && information.autonomy.isNotBlank())
    }

    private fun enabledButton(permission: Boolean) {
        binding.buttonCalculate.isEnabled = permission
    }

    private fun calculate(valor: Viagem) {
        val distance = valor.distance.toFloat()
        val price = valor.price.toFloat()
        val autonomy = valor.autonomy.toFloat()

        if(distance == 0F || price == 0F || autonomy == 0F) {
            binding.textValueFull.text = getString(R.string.valor_0)

        } else {
            val valueFull = (distance * price) / autonomy
            binding.textValueFull.text = "R$ ${"%.2f".format(valueFull)}"
        }
    }
}