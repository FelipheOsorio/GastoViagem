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

        val dispesasViagem = Viagem()

        binding.editDistance.setOnKeyListener { _, keycode, event ->
            if (keycode == KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

               dispesasViagem.distancia = binding.editDistance.text.toString()

                if(validacaoDosDados(dispesasViagem)) {
                    ativarBotao()
                } else {
                    binding.buttonCalculate.isEnabled = false
                }

                true
            } else {
                false
            }
        }

        binding.editPrice.setOnKeyListener { _, keycode, event ->
            if (keycode == KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                dispesasViagem.preco = binding.editPrice.text.toString()

                if(validacaoDosDados(dispesasViagem)) {
                    ativarBotao()
                } else {
                    binding.buttonCalculate.isEnabled = false
                }

                true
            } else {
                false
            }
        }

        binding.editAutonomy.setOnKeyListener { _, keycode, event ->
            if (keycode == KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {

                dispesasViagem.autonomia = binding.editAutonomy.text.toString()

                if(validacaoDosDados(dispesasViagem)) {
                    ativarBotao()
                } else {
                    binding.buttonCalculate.isEnabled = false
                }

                true
            } else {
                false
            }
        }

        binding.buttonCalculate.setOnClickListener {
            calculate(dispesasViagem)
        }

    }

    private fun validacaoDosDados(informacao: Viagem) : Boolean {
        return (informacao.distancia.isNotEmpty()
                && informacao.distancia.isNotBlank()
                && informacao.preco.isNotEmpty()
                && informacao.preco.isNotBlank()
                && informacao.autonomia.isNotEmpty()
                && informacao.autonomia.isNotBlank())
    }

    private fun ativarBotao() {
        binding.buttonCalculate.isEnabled = true
    }

    private fun calculate(valor: Viagem) {
        val distancia = valor.distancia.toFloat()
        val preco = valor.preco.toFloat()
        val autonomia = valor.autonomia.toFloat()

        if(distancia == 0F || preco == 0F || autonomia == 0F) {
            binding.textValueFull.text = getString(R.string.valor_0)

        } else {
            val valorTotal = (distancia * preco) / autonomia
            binding.textValueFull.text = "R$ ${"%.2f".format(valorTotal)}"
        }
    }
}