package com.example.meucontrolefinanceiro

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meucontrolefinanceiro.R
import com.example.meucontrolefinanceiro.databinding.ActivityMainBinding
import com.example.meucontrolefinanceiro.ui.adapters.TransactionAdapter
import java.text.NumberFormat
import java.util.Locale

// Definição do modelo de dados (pode estar em um ficheiro separado, como models/Transaction.kt)
data class Transaction(
    val id: String,
    val description: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val time: String,
    val iconResId: Int // ID do recurso drawable para o ícone
)

enum class TransactionType {
    EXPENSE, INCOME
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var balanceVisible = true // Estado para a visibilidade do saldo

    override fun onCreate(savedInstanceState: Bundle?) {
        // FORÇAR O TEMA ESCURO AQUI (ANTES DE super.onCreate())
        // Isso fará com que o aplicativo sempre use o modo noturno, independentemente da configuração do sistema.
        // É importante que esta linha seja chamada antes de qualquer inflação de layout ou acesso a recursos de tema.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        super.onCreate(savedInstanceState) // super.onCreate() agora é chamado depois

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.dashboard_title) // Usando string resource

        // Configurar o clique no avatar para ir para a tela de perfil
        binding.userAvatar.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // --- Lógica do Cartão de Saldo ---
        updateBalanceDisplay()

        binding.toggleBalanceVisibility.setOnClickListener {
            balanceVisible = !balanceVisible
            updateBalanceDisplay()
        }

        // --- Lógica das Transações Recentes ---
        setupRecentTransactions()

        // --- Lógica do FAB (Floating Action Button) ---
        binding.fabAddTransaction.setOnClickListener {
            toggleFabOptions()
        }

        // Lógica para os botões das opções do FAB
        binding.fabAddIncome.setOnClickListener {
            val intent = Intent(this, AddIncomeActivity::class.java)
            startActivity(intent)
            toggleFabOptions() // Fecha as opções após o clique
        }

        binding.fabAddExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
            toggleFabOptions() // Fecha as opções após o clique
        }

        binding.viewAllTransactionsButton.setOnClickListener {
            val intent = Intent(this, AllTransactionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateBalanceDisplay() {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

        val currentBalance = 5250.75 // Dados de exemplo
        val monthlyIncome = 3500.00
        val monthlyExpenses = 1250.25

        // Obter as cores do tema (se necessário, para elementos que não pegam automaticamente)
        // Você já tem este código no seu MainActivity.kt
        val typedValueRed = android.util.TypedValue()
        theme.resolveAttribute(R.attr.expenseColor, typedValueRed, true)
        val redColor = typedValueRed.data

        val typedValueGreen = android.util.TypedValue()
        theme.resolveAttribute(R.attr.incomeColor, typedValueGreen, true)
        val greenColor = typedValueGreen.data

        if (balanceVisible) {
            binding.currentBalanceText.text = currencyFormat.format(currentBalance)
            binding.monthlyIncomeText.text = currencyFormat.format(monthlyIncome)
            binding.monthlyExpensesText.text = currencyFormat.format(monthlyExpenses)
            binding.toggleBalanceVisibility.setImageResource(R.drawable.ic_visibility)
            binding.monthlyIncomeText.setTextColor(greenColor)
            binding.monthlyExpensesText.setTextColor(redColor)
        } else {
            binding.currentBalanceText.text = "R$ ••••••"
            binding.monthlyIncomeText.text = "R$ ••••••"
            binding.monthlyExpensesText.text = "R$ ••••••"
            binding.toggleBalanceVisibility.setImageResource(R.drawable.ic_visibility_off)
            binding.monthlyIncomeText.setTextColor(greenColor)
            binding.monthlyExpensesText.setTextColor(redColor)
        }
    }

    private fun setupRecentTransactions() {
        // Certifique-se de que os ícones abaixo existem em res/drawable/
        val transactions = listOf(
            Transaction("1", "Compras de Supermercado", 120.50, TransactionType.EXPENSE, "Alimentação", "Ontem, 18:30", R.drawable.ic_shopping_bag),
            Transaction("2", "Passagem de Ônibus", 4.40, TransactionType.EXPENSE, "Transporte", "Hoje, 08:15", R.drawable.ic_train),
            Transaction("3", "Freelance Projeto X", 800.00, TransactionType.INCOME, "Rendimento", "15 de Julho", R.drawable.ic_wallet),
            Transaction("4", "Café da Manhã", 15.00, TransactionType.EXPENSE, "Alimentação", "Hoje, 07:45", R.drawable.ic_fastfood)
        )

        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.transactionsRecyclerView.adapter = TransactionAdapter(transactions)
    }

    private fun toggleFabOptions() {
        if (binding.fabOptionsLayout.visibility == View.VISIBLE) {
            binding.fabOptionsLayout.visibility = View.GONE
            binding.fabAddTransaction.setImageResource(R.drawable.ic_add)
        } else {
            binding.fabOptionsLayout.visibility = View.VISIBLE
            binding.fabAddTransaction.setImageResource(R.drawable.ic_close) // Ícone de 'X'
        }
    }
}
