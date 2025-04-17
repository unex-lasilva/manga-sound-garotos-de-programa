package br.com.mangarosa.collections;


//Criando a Classe Música com seus getters.
public class Musica {
    private String nome;
    private String caminho;

    public Musica(String nome, String caminho) {
        this.nome = nome;
        this.caminho = caminho;
    }

    public String getNome() {
        return nome;
    }

    public String getCaminho() {
        return caminho;
    }
}

