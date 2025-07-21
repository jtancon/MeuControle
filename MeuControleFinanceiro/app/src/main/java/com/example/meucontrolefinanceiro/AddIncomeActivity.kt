package com.example.meucontrolefinanceiro

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meucontrolefinanceiro.databinding.ActivityAddIncomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddIncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Adicionar Rendimento"

        // Configurar DatePicker para o campo de data
        binding.incomeDateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        binding.addIncomeButton.setOnClickListener {
            // TODO: Lógica para adicionar rendimento ao Firebase Firestore
            val description = binding.incomeDescriptionEditText.text.toString()
            val amount = binding.incomeAmountEditText.text.toString().toDoubleOrNull()
            val date = binding.incomeDateEditText.text.toString()

            if (description.isNotBlank() && amount != null && date.isNotBlank()) {
                // Aqui você enviaria os dados para o Firebase
                // Exemplo: saveIncomeToFirebase(description, amount, date)
                finish() // Fecha a tela após adicionar
            } else {
                // Mostrar mensagem de erro ou validação
                binding.incomeDescriptionEditText.error = if (description.isBlank()) "Campo obrigatório" else null
                binding.incomeAmountEditText.error = if (amount == null) "Valor inválido" else null
                binding.incomeDateEditText.error = if (date.isBlank()) "Campo obrigatório" else null
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
                binding.incomeDateEditText.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
