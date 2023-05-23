package devandroid.felipe.gastoviagem

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.appcompat.app.AppCompatActivity
import devandroid.felipe.gastoviagem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val travelExpenses = Viagem()

        binding.editDistance.setOnKeyListener { _, i, event ->
            if (i == KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                travelExpenses.distance = binding.editDistance.text.toString()

                if(validateData(travelExpenses)) {
                    enabledButton(true)
                } else {
                    enabledButton(false)
                }

                true

            } else if(i == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_UP) {
                enabledButton(false)
                true
            } else {
                false
            }
        }

        binding.editPrice.setOnKeyListener { _, i, event ->
            if (i == KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                travelExpenses.price = binding.editPrice.text.toString()

                if(validateData(travelExpenses)) {
                    enabledButton(true)
                } else {
                    enabledButton(false)
                }

                true

            } else if(i == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_UP) {
                enabledButton(false)
                true
            } else {
                false
            }
        }

        binding.editAutonomy.setOnKeyListener { _, i, event ->
            if(i == KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                travelExpenses.autonomy = binding.editAutonomy.text.toString()

                if(validateData(travelExpenses)) {
                    enabledButton(true)
                } else {
                    enabledButton(false)
                }

                true
            } else if(i == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_UP) {
                enabledButton(false)
                true
            } else {
                false
            }
        }

        binding.buttonCalculate.setOnClickListener {
            calculate(travelExpenses)
        }

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