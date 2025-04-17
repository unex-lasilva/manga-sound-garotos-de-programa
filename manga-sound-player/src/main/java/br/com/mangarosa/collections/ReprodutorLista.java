package br.com.mangarosa.collections;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class ReprodutorLista {
    public static void reproduzir(ListaReproducao lista) {
        if (lista.estaVazia()) {
            System.out.println("Lista de reprodução vazia!");
            return;
        }
        for (int i = 0; i < lista.getMusicas().size(); i++) {
            Musica musica = (Musica) lista.getMusicas().get(i);
            tocarMusica(musica);
        }
    }
    private static void tocarMusica(Musica musica) {
        try {
            File arquivo = new File(musica.getCaminho());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            Scanner scanner = new Scanner(System.in);
            boolean ativo = true;
            boolean pausado = false;
            long posicaoPausada = 0;
            clip.start();
            while (ativo) {
                long duracaoSegundos = clip.getMicrosecondLength() / 1_000_000;
                long atualSegundos = clip.getMicrosecondPosition() / 1_000_000;

                System.out.println("\n========= Player ===========");
                System.out.println("Música: " + musica.getNome());
                System.out.println("Tempo: " + atualSegundos + "s / " + duracaoSegundos + "s");
                System.out.println("Comandos: 'p' = pausar, 'c' = continuar, 's' = +10s, 'x' = parar");
                System.out.println("============================");

                if (!clip.isRunning() && !pausado && clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                    ativo = false;
                    break;
                }

                if (System.in.available() > 0) {
                    String comando = scanner.nextLine();
                    switch (comando.toLowerCase()) {
                        case "p":
                            if (clip.isRunning()) {
                                posicaoPausada = clip.getMicrosecondPosition();
                                clip.stop();
                                pausado = true;
                                System.out.println("Música pausada.");
                            }
                            break;
                        case "c":
                            if (pausado) {
                                clip.setMicrosecondPosition(posicaoPausada);
                                clip.start();
                                pausado = false;
                                System.out.println("Música continuando.");
                            }
                            break;
                        case "s":
                            long pos = clip.getMicrosecondPosition();
                            long novaPos = pos + 10_000_000;
                            clip.setMicrosecondPosition(Math.min(novaPos, clip.getMicrosecondLength() - 1));
                            break;
                        case "x":
                            clip.stop();
                            ativo = false;
                            System.out.println("Música parada.");
                            break;
                        default:
                            System.out.println("Comando inválido.");
                    }
                } else {
                    Thread.sleep(1000);
                }
            }

            clip.close();
            audioStream.close();
        } catch (Exception e) {
            System.out.println("Erro ao tocar a música: " + e.getMessage());
        }
    }
}

