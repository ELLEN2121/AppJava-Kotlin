package com.example.myapplication;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class EsqueciMinhaSenha extends AppCompatActivity {
    EditText Email, Chave, Cpf, NovaSenha;
    BancoDeDados db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esquecisenha);
        // Inicializa o banco e os campos
        db = new BancoDeDados(this);
        Email = findViewById(R.id.etEmailRedefinir);
        Chave = findViewById(R.id.etChaveSecreta);
        Cpf = findViewById(R.id.etCpfRedefinir);
        NovaSenha = findViewById(R.id.etNovaSenha);
        Button btnRedefinir = findViewById(R.id.btnRedefinirSenha);
        btnRedefinir.setOnClickListener(v -> {
            try {
                String email = Email.getText().toString().trim();
                String chave = Chave.getText().toString().trim();
                long cpf = Long.parseLong(Cpf.getText().toString().trim());
                float novaSenha = Float.parseFloat(NovaSenha.getText().toString().trim());
                boolean sucesso = db.atualizarSenha(email, cpf, chave, novaSenha);
                if (sucesso) {
                    Toast.makeText(this, "Senha atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Volta para a tela de login
                } else {
                    Toast.makeText(this, "Dados incorretos. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "CPF ou senha invalido! Use apenas números.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
