package com.example.myapplication;

public class Usuario {
    private long id;
    private String email;
    private long cpf;
    private String chave;
    private float senha;
    public Usuario(long id, String email, long cpf, String chave, float senha) {
        this.id = id;
        this.email = email;
        this.cpf = cpf;
        this.chave = chave;
        this.senha = senha;
    }
    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public long getCpf() { return cpf; }
    public void setCpf(long cpf) { this.cpf = cpf; }
    public String getChave() { return chave; }
    public void setChave(String chave) { this.chave = chave; }
    public float getSenha() { return senha; }
    public void setSenha(float senha) { this.senha = senha; }
    public String getResumo() {
        return email + " | CPF: " + cpf + " | Senha: " + senha;
    }
}
