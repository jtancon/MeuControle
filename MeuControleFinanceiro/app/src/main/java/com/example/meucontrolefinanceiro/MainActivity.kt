package com.example.meucontrolefinanceiro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
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

    // A classe de binding é gerada a partir do nome do seu ficheiro XML (activity_main.xml -> ActivityMainBinding)
    private lateinit var binding: ActivityMainBinding
    private var balanceVisible = true // Estado para a visibilidade do saldo

    override fun onCreate(savedInstanceState: Bundle?) {
        // Força o tema escuro, o que é ótimo para o nosso novo design
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Clique no avatar para ir para a tela de perfil
        binding.userAvatar.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Clique para mostrar/ocultar o saldo
        binding.toggleBalanceVisibility.setOnClickListener {
            balanceVisible = !balanceVisible
            updateBalanceDisplay()
        }

        // Clique no botão "Ver todas"
        binding.viewAllTransactionsButton.setOnClickListener {
            val intent = Intent(this, AllTransactionsActivity::class.java)
            startActivity(intent)
        }

        // Clique nos novos botões de Ação Rápida
        binding.addIncomeButton.setOnClickListener {
            val intent = Intent(this, AddIncomeActivity::class.java)
            startActivity(intent)
        }

        binding.addExpenseButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }

        // Listener para a barra de navegação inferior
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Já estamos no Início, não faz nada
                    true
                }
                R.id.nav_transactions -> {
                    val intent = Intent(this, AllTransactionsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_charts -> {
                    val intent = Intent(this, ChartsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


        // --- Chamadas de Funções para Configurar a UI ---
        updateBalanceDisplay()
        setupRecentTransactions()
    }

    private fun updateBalanceDisplay() {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val currentBalance = 0.75 // Dados de exemplo

        if (balanceVisible) {
            binding.currentBalanceText.text = currencyFormat.format(currentBalance)
            binding.toggleBalanceVisibility.setImageResource(R.drawable.ic_visibility)
        } else {
            binding.currentBalanceText.text = "R$ ••••••"
            binding.toggleBalanceVisibility.setImageResource(R.drawable.ic_visibility_off)
        }

    }

    private fun setupRecentTransactions() {
        val transactions = listOf(
            Transaction("1", "Compras de Supermercado", 120.50, TransactionType.EXPENSE, "Alimentação", "Hoje", R.drawable.ic_shopping_bag),
            Transaction("2", "Salário Mensal", 3500.00, TransactionType.INCOME, "Rendimento", "Ontem", R.drawable.ic_wallet),
            Transaction("3", "Conta de Luz", 98.90, TransactionType.EXPENSE, "Contas", "15 de Julho", R.drawable.ic_lightbulb),
            Transaction("4", "Conta de Agua", 78.90, TransactionType.EXPENSE, "Contas", "15 de Julho", R.drawable.ic_lightbulb),
            Transaction("5", "Bico", 350.00, TransactionType.INCOME, "Rendimento", "Ontem", R.drawable.ic_wallet),
            Transaction("6", "Compras de Supermercado", 120.50, TransactionType.EXPENSE, "Alimentação", "Hoje", R.drawable.ic_shopping_bag),
            Transaction("7", "Aluguel", 1500.00, TransactionType.EXPENSE, "Moradia", "01 de Julho", R.drawable.ic_home),
            Transaction("8", "Salário Mensal", 4000.00, TransactionType.INCOME, "Rendimento", "05 de Julho", R.drawable.ic_wallet),
            Transaction("9", "Conta de Luz", 180.00, TransactionType.EXPENSE, "Contas", "10 de Julho", R.drawable.ic_lightbulb),
            Transaction("10", "Internet", 90.00, TransactionType.EXPENSE, "Contas", "12 de Julho", R.drawable.ic_wifi),
            Transaction("11", "Jantar Fora", 75.00, TransactionType.EXPENSE, "Alimentação", "18 de Julho", R.drawable.ic_fastfood),
            Transaction("12", "Venda de Item", 250.00, TransactionType.INCOME, "Rendimento Extra", "19 de Julho", R.drawable.ic_money)
        )

        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.transactionsRecyclerView.adapter = TransactionAdapter(transactions)
    }

}
