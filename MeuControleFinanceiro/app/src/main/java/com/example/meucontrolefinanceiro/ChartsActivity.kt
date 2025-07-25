package com.example.meucontrolefinanceiro

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.meucontrolefinanceiro.databinding.ActivityChartsBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class ChartsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChartsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupPieChart()
        setupBarChart()
    }

    private fun setupPieChart() {
        val pieChart = binding.pieChart

        // 1. Dados de Exemplo
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(40f, "Alimentação"))
        entries.add(PieEntry(25f, "Transporte"))
        entries.add(PieEntry(15f, "Contas"))
        entries.add(PieEntry(10f, "Lazer"))
        entries.add(PieEntry(10f, "Outros"))

        // 2. Cores (NOVA PALETA DE TONS DE AZUL)
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#1e3a8a")) // Azul-800 (mais escuro)
        colors.add(Color.parseColor("#1d4ed8")) // Azul-700
        colors.add(Color.parseColor("#2563eb")) // Azul-600
        colors.add(Color.parseColor("#3b82f6")) // Azul-500 (cor primária do tema)
        colors.add(Color.parseColor("#60a5fa")) // Azul-400 (mais claro)


        // 3. Dataset
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f
        dataSet.valueTypeface = Typeface.DEFAULT_BOLD
        dataSet.sliceSpace = 5f

        // 4. PieData
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))

        // 5. Configuração do Gráfico
        pieChart.data = data
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false

        // Buraco no meio
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.setDrawCenterText(true)
        pieChart.centerText = "Despesas"
        pieChart.setCenterTextColor(Color.WHITE)
        pieChart.setCenterTextSize(18f)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)

        // Rótulos das fatias
        pieChart.setDrawEntryLabels(false)

        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.invalidate()
    }

    private fun setupBarChart() {
        val barChart = binding.barChart

        // 1. Dados de Exemplo
        val incomeEntries = ArrayList<BarEntry>()
        incomeEntries.add(BarEntry(0f, 3000f))
        incomeEntries.add(BarEntry(1f, 3200f))
        incomeEntries.add(BarEntry(2f, 3500f))

        val expenseEntries = ArrayList<BarEntry>()
        expenseEntries.add(BarEntry(0f, 1500f))
        expenseEntries.add(BarEntry(1f, 1800f))
        expenseEntries.add(BarEntry(2f, 1250f))

        // 2. Datasets
        val incomeDataSet = BarDataSet(incomeEntries, "Receitas")
        incomeDataSet.color = ContextCompat.getColor(this, R.color.accent_green)
        incomeDataSet.valueTextColor = Color.WHITE
        incomeDataSet.valueTextSize = 10f

        val expenseDataSet = BarDataSet(expenseEntries, "Despesas")
        expenseDataSet.color = ContextCompat.getColor(this, R.color.accent_red)
        expenseDataSet.valueTextColor = Color.WHITE
        expenseDataSet.valueTextSize = 10f

        // 3. BarData
        val data = BarData(incomeDataSet, expenseDataSet)
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "R$ ${value.toInt()}"
            }
        })
        barChart.data = data

        // 4. Configuração do Gráfico
        val months = arrayOf("Maio", "Junho", "Julho")
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(months)
        xAxis.textColor = ContextCompat.getColor(this, R.color.text_secondary_dark)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        val leftAxis = barChart.axisLeft
        leftAxis.textColor = ContextCompat.getColor(this, R.color.text_secondary_dark)
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = ContextCompat.getColor(this, R.color.dark_surface_variant)
        leftAxis.setDrawAxisLine(false)
        leftAxis.axisMinimum = 0f

        barChart.axisRight.isEnabled = false
        barChart.legend.textColor = Color.WHITE
        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)
        barChart.setDrawBorders(false)

        val groupSpace = 0.4f
        val barSpace = 0.05f
        val barWidth = 0.25f
        data.barWidth = barWidth
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = months.size.toFloat()
        barChart.groupBars(0f, groupSpace, barSpace)

        barChart.animateY(1200)
        barChart.invalidate()
    }
}
