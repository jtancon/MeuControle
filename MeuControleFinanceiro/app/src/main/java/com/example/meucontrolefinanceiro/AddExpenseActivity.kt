package com.example.meucontrolefinanceiro

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.meucontrolefinanceiro.databinding.ActivityAddExpenseBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Adicionar Gasto"

        // Configurar DatePicker para o campo de data
        binding.expenseDateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Configurar AutoCompleteTextView para categorias
        val categories = arrayOf("Alimentação", "Transporte", "Lazer", "Contas", "Educação", "Saúde", "Namorada linda <3", "Outros")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        binding.expenseCategoryAutoCompleteText.setAdapter(adapter)

        binding.addExpenseButton.setOnClickListener {
            // TODO: Lógica para adicionar gasto ao Firebase Firestore
            val description = binding.expenseDescriptionEditText.text.toString()
            val amount = binding.expenseAmountEditText.text.toString().toDoubleOrNull()
            val category = binding.expenseCategoryAutoCompleteText.text.toString()
            val date = binding.expenseDateEditText.text.toString()

            if (description.isNotBlank() && amount != null && category.isNotBlank() && date.isNotBlank()) {
                // Aqui você enviaria os dados para o Firebase
                // Exemplo: saveExpenseToFirebase(description, amount, category, date)
                finish() // Fecha a tela após adicionar
            } else {
                // Mostrar mensagem de erro ou validação
                binding.expenseDescriptionEditText.error = if (description.isBlank()) "Campo obrigatório" else null
                binding.expenseAmountEditText.error = if (amount == null) "Valor inválido" else null
                binding.expenseCategoryAutoCompleteText.error = if (category.isBlank()) "Campo obrigatório" else null
                binding.expenseDateEditText.error = if (date.isBlank()) "Campo obrigatório" else null
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
                binding.expenseDateEditText.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
