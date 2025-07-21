package com.example.meucontrolefinanceiro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meucontrolefinanceiro.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar a Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Habilita o botão de voltar
        supportActionBar?.title = "Meu Perfil"

        // Dados de exemplo (substitua por dados reais do Firebase Authentication)
        binding.profileNameText.text = "João da Silva"
        binding.profileEmailText.text = "joao.silva@example.com"
        binding.profileAvatarText.text = "JS" // Iniciais do usuário

        binding.logoutButton.setOnClickListener {
            // TODO: Implementar lógica de logout do Firebase
            // Exemplo: FirebaseAuth.getInstance().signOut()
            // Redirecionar para a tela de login
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Lida com o clique no botão de voltar da Toolbar
        return true
    }
}
