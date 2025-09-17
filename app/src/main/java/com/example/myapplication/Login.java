package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class Login extends AppCompatActivity {
    EditText Email, Senha;
    BancoDeDados db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Layout da tela de login
        // Inicialização dos elementos
        db = new BancoDeDados(this);
        Email = findViewById(R.id.Email);
        Senha = findViewById(R.id.SenhaApp);
        Button btnEntrar = findViewById(R.id.btnLogin);
        TextView Cadastro = findViewById(R.id.IrParaCadastro);
        TextView EsqueciSenha = findViewById(R.id.EsqueciSenha);
        // Ação do botão "Entrar"
        btnEntrar.setOnClickListener(v -> {
            String email = Email.getText().toString().trim();
            String senhaTexto = Senha.getText().toString().trim();
            if (email.isEmpty() || senhaTexto.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                float senhaFloat = Float.parseFloat(senhaTexto);
                if (db.verificarLogin(email, senhaFloat)) {
                    Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Home.class);
                    startActivity(intent);
                    finish(); // fecha a tela de login após sucesso
                } else {
                    Toast.makeText(this, "Email ou senha invalidos", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Senha invalida (precisa ser numérica)", Toast.LENGTH_SHORT).show();
            }
        });
        // Ir para cadastro
        Cadastro.setOnClickListener(v -> {
            Intent intent = new Intent(this, Cadastro.class);
            startActivity(intent);
        });
        // Ir para recuperação de senha
        EsqueciSenha.setOnClickListener(v -> {
            Intent intent = new Intent(this, EsqueciMinhaSenha.class);
            startActivity(intent);
        });
    }
}
