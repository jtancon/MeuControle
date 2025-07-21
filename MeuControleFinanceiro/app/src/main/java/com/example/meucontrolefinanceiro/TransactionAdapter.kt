package com.example.meucontrolefinanceiro.ui.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.meucontrolefinanceiro.R // Importe o R do namespace do seu projeto
import com.example.meucontrolefinanceiro.databinding.ItemTransactionBinding // Importe o Binding do namespace correto
import com.example.meucontrolefinanceiro.Transaction
import com.example.meucontrolefinanceiro.TransactionType
import java.text.NumberFormat
import java.util.Locale

class TransactionAdapter(private val transactions: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    /**
     * ViewHolder que usa View Binding.
     * Ele guarda uma referência direta e segura para as Views do seu layout.
     */
    inner class TransactionViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Cria um novo ViewHolder, inflando o layout do item usando View Binding.
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

        // Acessa as Views através de 'holder.binding'
        holder.binding.transactionDescription.text = transaction.description
        holder.binding.transactionCategoryTime.text = "${transaction.category} • ${transaction.time}"
        holder.binding.categoryIcon.setImageResource(transaction.iconResId)

        // Lógica para definir cores e valores de despesa/receita
        val expenseColor = resolveThemeColor(context, R.attr.expenseColor)
        val incomeColor = resolveThemeColor(context, R.attr.incomeColor)

        if (transaction.type == TransactionType.EXPENSE) {
            holder.binding.transactionAmount.text = "- ${currencyFormat.format(transaction.amount)}"
            holder.binding.transactionAmount.setTextColor(expenseColor)
            holder.binding.categoryIconBackground.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red_light))
            holder.binding.categoryIcon.setColorFilter(expenseColor)
        } else {
            holder.binding.transactionAmount.text = "+ ${currencyFormat.format(transaction.amount)}"
            holder.binding.transactionAmount.setTextColor(incomeColor)
            holder.binding.categoryIconBackground.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green_light))
            holder.binding.categoryIcon.setColorFilter(incomeColor)
        }

        holder.itemView.setOnClickListener {
            // TODO: Lógica para quando um item é clicado
        }
    }

    /**
     * Retorna o número total de itens na lista. (NOME CORRIGIDO)
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