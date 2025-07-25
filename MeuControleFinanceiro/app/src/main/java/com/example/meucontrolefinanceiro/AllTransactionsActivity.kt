package com.example.meucontrolefinanceiro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meucontrolefinanceiro.databinding.ActivityAllTransactionsBinding
import com.example.meucontrolefinanceiro.ui.adapters.TransactionAdapter

class AllTransactionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllTransactionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Habilita o botão de voltar
        supportActionBar?.title = "Todas as Transações"

        // Lógica para carregar e exibir todas as transações
        setupAllTransactionsRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Lida com o clique no botão de voltar da Toolbar
        return true
    }

    private fun setupAllTransactionsRecyclerView() {
        // Dados de exemplo (substitua por dados reais do Firebase)
        val allTransactions = listOf(
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

        binding.allTransactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.allTransactionsRecyclerView.adapter = TransactionAdapter(allTransactions)
    }
}
