package br.com.mangarosa.collections;

import java.io.File;
import java.nio.file.*;
import java.util.Scanner;
import javax.sound.sampled.*;

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
                    //Adicionar Música
                    break;
                case 2:
                    //Criar Playlist
                    break;
                case 3:
                    //Adicionar Musica Playlist
                    break;
                case 4:
                    //Executar Playlist
                    break;
                case 5:
                    //Listar Musicas
                    break;
                case 6:
                    //Verificar Musica
                    break;
                case 7:
                    //Limpar Repositorio
                    break;
                case 8:
                    //Listar Playlists
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 9);
    }
}
