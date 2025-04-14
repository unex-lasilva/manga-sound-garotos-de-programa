package br.com.mangarosa.collections;

public class ListaReproducao {
    private String nome;
    private ListaEncadeada musicas;
    public ListaReproducao(String nome) {
        this.nome = nome;
        this.musicas = new ListaEncadeada();
    }
    public String getNome() {
        return nome;
    }
    public ListaEncadeada getMusicas() {
        return musicas;
    }
    public void adicionarMusica(Musica musica) {
        musicas.append(musica);
    }
    public boolean removerMusica(int pos) {
        return musicas.remove(pos);
    }
    public void listarMusicas() {
        for (int i = 0; i < musicas.size(); i++) {
            Musica m = (Musica) musicas.get(i);
            System.out.println((i + 1) + ". " + m.getNome());
        }
    }
    public boolean estaVazia() {
        return musicas.isEmpty();
    }
}



