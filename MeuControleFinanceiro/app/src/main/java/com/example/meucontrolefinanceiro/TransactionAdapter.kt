package com.example.meucontrolefinanceiro.ui.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meucontrolefinanceiro.R
import com.example.meucontrolefinanceiro.Transaction
import com.example.meucontrolefinanceiro.TransactionType
import com.example.meucontrolefinanceiro.databinding.ItemTransactionBinding
import java.text.NumberFormat
import java.util.Locale

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    /**
     * ViewHolder que usa View Binding.
     */
    inner class TransactionViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Cria um novo ViewHolder, inflando o layout do item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    /**
     * Conecta os dados de uma transação específica com as Views do ViewHolder.
     */
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        val context = holder.itemView.context
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val formattedAmount = currencyFormat.format(transaction.amount)

        // Acessa as Views através de 'holder.binding'
        holder.binding.transactionDescription.text = transaction.description
        holder.binding.categoryIcon.setImageResource(transaction.iconResId)

        // Usa um recurso de string para formatar o texto, evitando avisos
        holder.binding.transactionCategoryTime.text = context.getString(R.string.category_time_format, transaction.category, transaction.time)

        // Lógica para definir cores e valores de despesa/receita
        val expenseColor = resolveThemeColor(context, R.attr.expenseColor)
        val incomeColor = resolveThemeColor(context, R.attr.incomeColor)

        if (transaction.type == TransactionType.EXPENSE) {
            holder.binding.transactionAmount.text = context.getString(R.string.amount_format_expense, formattedAmount)
            holder.binding.transactionAmount.setTextColor(expenseColor)
            holder.binding.categoryIcon.setColorFilter(expenseColor)
        } else {
            holder.binding.transactionAmount.text = context.getString(R.string.amount_format_income, formattedAmount)
            holder.binding.transactionAmount.setTextColor(incomeColor)
            holder.binding.categoryIcon.setColorFilter(incomeColor)
        }

        holder.itemView.setOnClickListener {
            // TODO: Lógica para quando um item é clicado
        }
    }

    /**
     * Retorna o número total de itens na lista.
     */
    override fun getItemCount(): Int = transactions.size

    /**
     * Função auxiliar para obter uma cor do tema atual.
     */
    private fun resolveThemeColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }
}
