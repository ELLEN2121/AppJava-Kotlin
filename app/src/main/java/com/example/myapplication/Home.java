package com.example.myapplication;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
public class Home extends AppCompatActivity {
    ListView listUsuarios;
    BancoDeDados banco;
    ArrayList<String> listaEmails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        listUsuarios = findViewById(R.id.listUsuarios);
        banco = new BancoDeDados(this);
        carregarUsuarios();
    }
    private void carregarUsuarios() {
        ArrayList<HashMap<String, String>> lista = new ArrayList<>();
        listaEmails = new ArrayList<>(); // ← Inicializa a lista de emails
        Cursor consulta = banco.getReadableDatabase().rawQuery("SELECT email, cpf, chave FROM usuarios", null);
        if (consulta.moveToFirst()) {
            do {
                HashMap<String, String> item = new HashMap<>();
                String email = consulta.getString(0);
                item.put("email", "Email: " + email);
                item.put("cpf", "CPF: " + consulta.getString(1));
                item.put("chave", "Chave: " + consulta.getString(2));
                lista.add(item);
                listaEmails.add(email); // ← Armazena o email real
            } while (consulta.moveToNext());
        }
        consulta.close();
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                lista,
                R.layout.visualizar_usuario,
                new String[]{"email", "cpf", "chave"},
                new int[]{R.id.txtEmail, R.id.txtCpf, R.id.txtChave}
        );
        listUsuarios.setAdapter(adapter);
        // ← Clique no item para excluir
        listUsuarios.setOnItemClickListener((parent, view, position, id) -> {
            String emailSelecionado = listaEmails.get(position);
            new android.app.AlertDialog.Builder(Home.this)
                    .setTitle("Excluir Usuário do banco de dados")
                    .setMessage("Deseja excluir o usuário:\n" + emailSelecionado + "?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        boolean excluido = banco.excluirUsuario(emailSelecionado);
                        if (excluido) {
                            Toast.makeText(Home.this, "Usuário excluído com sucesso!", Toast.LENGTH_SHORT).show();
                            carregarUsuarios(); // ← Atualiza lista
                        } else {
                            Toast.makeText(Home.this, "Erro ao excluir usuário.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }
}
