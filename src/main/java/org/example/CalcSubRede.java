package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class CalcSubRede {
    public static void main(String[] args) {
        // Máscara 255.255.255.0, pode ser representada em binarios com 11111111.11111111.11111111.0000000
        // Coisas que vou precisar
        // Endereço IP da rede
        // Máscara de sub-rede
        // Calcula o endereço de rede
        // Formula IP & Mask de Rede = o resultado é o endereço broadcast
        // Calcula o endereço de broadcast
        // Calcula o número de endereços IP úteis
        // Converte os endereços IP para inteiros
        // Converte um inteiro para um endereço IP
        // Imprime os resultados

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== CALCULADORA DE SUB-REDE IP ===");

        int[] ip = obterEnderecoValido("Digite o endereço IP (ex: 192.168.1.100): ", scanner);

        int[] mascara = obterEnderecoValido("Digite a máscara de sub-rede (ex: 255.255.255.0): ", scanner);

        // Calcs Principais
        int[] enderecoRede = calcularEnderecoRede(ip, mascara);
        int[] enderecoBroadcast = calcularEnderecoBroadcast(ip, mascara);
        int prefixoCidr = calcularPrefixoCidr(mascara);
        int numeroHosts = calcularNumeroHosts(prefixoCidr);
        String[] faixaHosts = calcularFaixaHostsValidos(enderecoRede, enderecoBroadcast);

        exibirResultados(ip, mascara, enderecoRede, enderecoBroadcast, prefixoCidr, numeroHosts, faixaHosts);

        scanner.close();
    }
    private static int[] obterEnderecoValido(String mensagem, Scanner scanner) {
        while (true) {
            try {
                System.out.println(mensagem);
                String input = scanner.nextLine();

                if(!input.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")) {
                    throw new IllegalAccessException("Formato inválido. Use X.X.X.X");
                }

                // Divide em partes e converte para inteiros
                String[] partes = input.split("\\.");
                int[] octetos = new int[4];

                for(int i = 0; i < 4; i++) {

                    // Valida se cada octeto está entre 0-255
                    if(octetos[i] < 0 || octetos[i] > 255) {
                        throw new IllegalAccessException("Octeto deve estrar entre 0-250");
                    }
                }

                return octetos;

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage() + ". Tente novamente.");
            }
        }
    }

    // calcular endereço de rede
    private static int[] calcularEnderecoRede(int[] ip, int[] mascara) {
        int[] rede = new int[4];

        // AND bit a bit entre IP e máscara
        for (int i = 0; i < 4; i++) {
            rede[i] = ip[i] & mascara[i];
        }

        return rede;
    }

    // calcular endereço de broadcast
    private static int[] calcularEnderecoBroadcast(int[] ip, int[] mascara) {
        int[] broadcast = new int[4];

        for (int i = 0; i < 4; i++) {
            // ~ também chamado de NOT bit a bit
            // NOT da máscara (inverte os bits) + AND com 0xFF para manter apenas 8 bits
            // Depois OR com o IP para calcular broadcast
            broadcast[i] = ip[i] | (~mascara[i] & 0xFF);
        }

        return broadcast;
    }

    // Calcular prefixo CIDR
    private static int calcularPrefixoCidr(int[] mascara) {
        int cidr = 0;

        // quantos bits '1' existem na máscara
        for (int octeto : mascara) {
            cidr += Integer.bitCount(octeto);
        }

        return cidr;
    }

    // Calcular número de hosts
    private static int calcularNumeroHosts(int prefixoCidr) {
        // fórmula: 2^(bits de host) - 2 (rede a broadcast)
        return (int) (Math.pow(2, 32 - prefixoCidr) -2);
    }

    // Calcular faixa de host validos
    private static String[] calcularFaixaHostsValidos(int[] rede, int[] broadcast) {
        int[] primeiroHost = Arrays.copyOf(rede, 4);
        primeiroHost[3] -= 1; // Decrementa ultimo octeto

        int[] ultimoHost = Arrays.copyOf(broadcast, 4);
        ultimoHost[3] -= 1; // Decrementa ultimo octeto

        return new String[] {
                formatarOctetos(primeiroHost),
                formatarOctetos(ultimoHost)
        };
    }

    // formatar octetos para string
    private static String formatarOctetos(int[] octetos) {
        return octetos[0] + "." + octetos[1] + "." + octetos[2] + "." + octetos[3];
    }

    // método para exibir resultados
    private static void exibirResultados(int[] ip, int[] mascara, int[] rede,
                                         int[] broadcast, int cidr, int hosts, String[] faixa) {
        System.out.println("\n=== RESULTADOS ===");
        System.out.println("IP informado:        " + formatarOctetos(ip));
        System.out.println("Máscara informada:  " + formatarOctetos(mascara));
        System.out.println("Notação CIDR:       /" + cidr);
        System.out.println("----------------------------------");
        System.out.println("Endereço de rede:   " + formatarOctetos(rede));
        System.out.println("Endereço broadcast: " + formatarOctetos(broadcast));
        System.out.println("Hosts válidos:      " + faixa[0] + " - " + faixa[1]);
        System.out.println("Número de hosts:    " + hosts);
    }
}
























