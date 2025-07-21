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
            Transaction("5", "Aluguel", 1500.00, TransactionType.EXPENSE, "Moradia", "01 de Julho", R.drawable.ic_home),
            Transaction("6", "Salário Mensal", 4000.00, TransactionType.INCOME, "Rendimento", "05 de Julho", R.drawable.ic_wallet),
            Transaction("7", "Conta de Luz", 180.00, TransactionType.EXPENSE, "Contas", "10 de Julho", R.drawable.ic_lightbulb),
            Transaction("8", "Internet", 90.00, TransactionType.EXPENSE, "Contas", "12 de Julho", R.drawable.ic_wifi),
            Transaction("9", "Jantar Fora", 75.00, TransactionType.EXPENSE, "Alimentação", "18 de Julho", R.drawable.ic_fastfood),
            Transaction("10", "Venda de Item", 250.00, TransactionType.INCOME, "Rendimento Extra", "19 de Julho", R.drawable.ic_money)
        )

        binding.allTransactionsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.allTransactionsRecyclerView.adapter = TransactionAdapter(allTransactions)
    }
}
