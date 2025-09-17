package com.example.myapplication;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
public class Cadastro extends AppCompatActivity {
    EditText Email, Cpf, Chave, Senha;
    BancoDeDados db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        db = new BancoDeDados(this);
        Email = findViewById(R.id.Email);
        Cpf = findViewById(R.id.cpf);
        Chave = findViewById(R.id.ChaveCadastro);
        Senha = findViewById(R.id.CadastroSenha);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(v -> {
            try {
                String email = Email.getText().toString().trim();
                long cpf = Long.parseLong(Cpf.getText().toString().trim());
                String chave = Chave.getText().toString().trim();
                float senha = Float.parseFloat(Senha.getText().toString().trim());
                Usuario novoUsuario = new Usuario(0, email, cpf, chave, senha);
                if (db.inserirUsuario(novoUsuario)) {
                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Volta para a tela de login
                } else {
                    Toast.makeText(this, "Erro ao cadastrar usuário, pois já existe um usuário com esse email.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
