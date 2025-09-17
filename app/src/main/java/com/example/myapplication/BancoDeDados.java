package com.example.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
public class BancoDeDados extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "usuarios.db";
    private static final int VERSAO = 1;
    public BancoDeDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }
    @Override
    //id é chave primaria e é autoincrementada e o email deve ser unico, senao retorna erro
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT UNIQUE, " +
                "cpf INTEGER, " +
                "chave TEXT, " +
                "senha REAL)";
        db.execSQL(sql);
    }
    @Override
    // excluir tabela e criar novamente
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
    // Inserir usuário com objeto
    public boolean inserirUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("email", usuario.getEmail());
        valores.put("cpf", usuario.getCpf());
        valores.put("chave", usuario.getChave());
        valores.put("senha", usuario.getSenha());
        long resultado = db.insert("usuarios", null, valores);
        db.close();
        return resultado != -1;
    }
    // Excluir usuário pelo email
    public boolean excluirUsuario(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int linhasAfetadas = db.delete("usuarios", "email = ?", new String[]{email});
        db.close();
        return linhasAfetadas > 0;
    }
    // Verificar login
    public boolean verificarLogin(String email, float senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor consultaBD = db.rawQuery("SELECT * FROM usuarios WHERE email = ? AND senha = ?",
                new String[]{email, String.valueOf(senha)});
        boolean valido = consultaBD.moveToFirst();
        consultaBD.close();
        db.close();
        return valido;
    }
    // Atualizar senha com base em email, cpf e chave
    public boolean atualizarSenha(String email, long cpf, String chave, float novaSenha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("senha", novaSenha);
        int linhasAfetadas = db.update(
                "usuarios",
                valores,
                "email = ? AND cpf = ? AND chave = ?",
                new String[]{email, String.valueOf(cpf), chave}
        );
        db.close();
        return linhasAfetadas > 0;
    }
    // Listar todos os usuarios
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor consultaBD = db.rawQuery("SELECT * FROM usuarios", null);
        if (consultaBD.moveToFirst()) {
            do {
                Usuario u = new Usuario(
                        consultaBD.getLong(0),
                        consultaBD.getString(1),
                        consultaBD.getLong(2),
                        consultaBD.getString(3),
                        consultaBD.getFloat(4)
                );
                lista.add(u);
            } while (consultaBD.moveToNext());
        }
        consultaBD.close();
        db.close();
        return lista;
    }
}
