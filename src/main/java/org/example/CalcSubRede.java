package org.example;

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


}
