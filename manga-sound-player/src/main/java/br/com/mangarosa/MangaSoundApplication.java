package br.com.mangarosa;

import java.io.File;
import java.nio.file.*;
import java.util.Scanner;
import javax.sound.sampled.*;
import br.com.mangarosa.collections.*;

public class MangaSoundApplication {
    private static ListaEncadeada repositorio = new ListaEncadeada();
    private static ListaEncadeada listas = new ListaEncadeada();
    private static final String REPOSITORIO_DIR = "musicas/";
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        File dir = new File(REPOSITORIO_DIR);
        if (!dir.exists()) {
            dir.mkdir();
            System.out.println("Diretório 'repositorio' criado.");
        }
        menu();
    }
    private static void menu() {
        int opcao;
        do {
            System.out.println("\nMangaSound - Menu");
            System.out.println("1. Adicionar música");
            System.out.println("2. Criar playlist");
            System.out.println("3. Adicionar música à playlist");
            System.out.println("4. Executar playlist");
            System.out.println("5. Listar todas as músicas");
            System.out.println("6. Verificar se uma música está na lista pelo nome");
            System.out.println("7. Limpar repositório de músicas");
            System.out.println("8. Listar todas as playlists");
            System.out.println("9. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    adicionarMusica();
                    break;
                case 2:
                    criarPlaylist();
                    break;
                case 3:
                    adicionarMusicaPlaylist();
                    break;
                case 4:
                    executarPlaylist();
                    break;
                case 5:
                    listarMusicas();
                    break;
                case 6:
                    verificarMusica();
                    break;
                case 7:
                    limparRepositorio();
                    break;
                case 8:
                    listarPlaylists();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 9);
    }
    private static void adicionarMusica() {
        System.out.print("Caminho do arquivo .wav: ");
        String caminho = scanner.nextLine();
        File origem = new File(caminho);
        if (!origem.exists()) {
            System.out.println("Arquivo não encontrado!");
            return;
        }

        if (!origem.getName().toLowerCase().endsWith(".wav")) {
            System.out.println("Somente arquivos .wav são suportados!");
            return;
        }
        try {
            Path destino = Paths.get(REPOSITORIO_DIR + origem.getName());
            if (Files.exists(destino)) {
                System.out.print("Arquivo já existe. Substituir? (S/N): ");
                String resposta = scanner.nextLine();
                if (!resposta.equalsIgnoreCase("S")) {
                    return;
                }
            }
            Files.copy(origem.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
            Musica m = new Musica(origem.getName(), destino.toString());
            repositorio.append(m);
            System.out.println("Música adicionada: " + m.getNome());
        } catch (Exception e) {
            System.out.println("Erro ao copiar arquivo: " + e.getMessage());
        }
    }
    private static void criarPlaylist() {
        System.out.print("Nome da playlist: ");
        String nome = scanner.nextLine();
        listas.append(new ListaReproducao(nome));
        System.out.println("Playlist '" + nome + "' criada.");
    }
    private static void adicionarMusicaPlaylist() {
        if (listas.isEmpty()) {
            System.out.println("Nenhuma playlist disponível!");
            return;
        }
        if (repositorio.isEmpty()) {
            System.out.println("Nenhuma música no repositório!");
            return;
        }
        System.out.println("\nPlaylists disponíveis:");
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + ((ListaReproducao) listas.get(i)).getNome());
        }
        System.out.print("Escolha a playlist: ");
        int idxPlaylist = scanner.nextInt() - 1;
        scanner.nextLine();
        if (idxPlaylist < 0 || idxPlaylist >= listas.size()) {
            System.out.println("Índice inválido!");
            return;
        }
        ListaReproducao playlist = (ListaReproducao) listas.get(idxPlaylist);
        System.out.println("\nMúsicas disponíveis:");
        for (int i = 0; i < repositorio.size(); i++) {
            System.out.println((i + 1) + ". " + ((Musica) repositorio.get(i)).getNome());
        }
        System.out.print("Escolha a música: ");
        int idxMusica = scanner.nextInt() - 1;
        scanner.nextLine();
        if (idxMusica < 0 || idxMusica >= repositorio.size()) {
            System.out.println("Índice inválido!");
            return;
        }
        Musica m = (Musica) repositorio.get(idxMusica);
        playlist.adicionarMusica(m);
        System.out.println("Música '" + m.getNome() + "' adicionada à playlist '" +
                playlist.getNome() + "'");
    }
    private static void executarPlaylist() {
        if (listas.isEmpty()) {
            System.out.println("Nenhuma playlist disponível!");
            return;
        }
        System.out.println("\nPlaylists disponíveis:");
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + ((ListaReproducao) listas.get(i)).getNome());
        }
        System.out.print("Escolha a playlist: ");
        int idx = scanner.nextInt() - 1;
        scanner.nextLine();
        if (idx < 0 || idx >= listas.size()) {
            System.out.println("Índice inválido!");
            return;
        }
        ListaReproducao playlist = (ListaReproducao) listas.get(idx);
        ReprodutorLista.reproduzir(playlist);
    }
    private static void listarMusicas() {
        if (repositorio.isEmpty()) {
            System.out.println("Repositório vazio.");
            return;
        }
        System.out.println("\nMúsicas no repositório:");
        for (int i = 0; i < repositorio.size(); i++) {
            Musica m = (Musica) repositorio.get(i);
            System.out.println((i + 1) + ". " + m.getNome());
        }
    }
    private static void verificarMusica() {
        System.out.print("Digite o nome da música: ");
        String nome = scanner.nextLine();
        boolean encontrada = false;
        for (int i = 0; i < repositorio.size(); i++) {
            Musica m = (Musica) repositorio.get(i);
            if (m.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Música encontrada no repositório: " + m.getNome());
                encontrada = true;
                break;
            }
        }
        if (!encontrada) {
            System.out.println("Música não encontrada.");
        }
    }
    private static void limparRepositorio() {
        if (repositorio.isEmpty()) {
            System.out.println("Repositório já está vazio.");
        } else {
            repositorio.clear();
            System.out.println("Repositório limpo.");
        }
    }
    private static void listarPlaylists() {
        if (listas.isEmpty()) {
            System.out.println("Nenhuma playlist cadastrada.");
        } else {
            System.out.println("\nPlaylists:");
            for (int i = 0; i < listas.size(); i++) {
                ListaReproducao l = (ListaReproducao) listas.get(i);
                System.out.println((i + 1) + ". " + l.getNome());
            }
        }
    }
}