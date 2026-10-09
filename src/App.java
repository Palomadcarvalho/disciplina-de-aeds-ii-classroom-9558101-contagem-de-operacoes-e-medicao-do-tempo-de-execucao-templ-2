import java.util.Random;

/** 
 * MIT License
 *
 * Copyright(c) 2024-255 João Caram <caram@pucminas.br>
 *                       Eveline Alonso Veloso
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

public class App {
    static final int[] tamanhosTesteGrande =  { 31_250_000, 62_500_000, 125_000_000, 250_000_000, 500_000_000 };
    static final int[] tamanhosTesteMedio =   {     12_500,     25_000,      50_000,     100_000,     200_000 };
    static final int[] tamanhosTestePequeno = {          3,          6,          12,          24,          48 };
    static Random aleatorio = new Random(42);
    static long operacoes;
    static double nanoToMilli = 1.0/1_000_000;

    /**
     * Código de teste 1. Este método percorre as posições pares do vetor (0, 2, 4...)
     * e conta quantos valores ímpares existem nessas posições.
     * Operação contada: cada visita a uma posição (cálculo do resto da divisão por 2).
     * Operações esperadas: aproximadamente n/2. Crescimento linear, O(n).
     * @param vetor Vetor com dados para teste.
     * @return A quantidade de números ímpares encontrados nas posições pares do vetor.
     */
    static int codigo1(int[] vetor) {
        int resposta = 0;
        for (int i = 0; i < vetor.length; i += 2) {
            resposta += vetor[i]%2;
            operacoes++;
        }
        return resposta;
    }

    /**
     * Código de teste 2. Este método executa um laço externo em que k começa em (n - 1)
     * e é dividido por 2 a cada volta; o laço interno executa (k + 1) vezes.
     * Operação contada: cada execução do laço interno.
     * Operações esperadas: n + n/2 + n/4 + ... ≈ 2n. Crescimento linear, O(n).
     * @param vetor Vetor com dados para teste.
     * @return O total de repetições do laço interno.
     */
    static int codigo2(int[] vetor) {
        int contador = 0;
        for (int k = (vetor.length - 1); k > 0; k /= 2) {
            for (int i = 0; i <= k; i++) {
                contador++;
                operacoes++;
            }
        }
        return contador;
    }

    /**
     * Código de teste 3. Este método ordena o vetor em ordem crescente usando o
     * algoritmo de ordenação por seleção (selection sort).
     * Operação contada: cada comparação entre dois elementos do vetor.
     * Operações esperadas: n(n - 1)/2 ≈ n²/2. Crescimento quadrático, O(n²).
     * @param vetor Vetor com dados para teste.
     */
    static void codigo3(int[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < vetor.length; j++) {
                operacoes++;
                if (vetor[j] < vetor[menor])
                    menor = j;
            }
            int temp = vetor[i];
            vetor[i] = vetor[menor];
            vetor[menor] = temp;
        }
    }

    /**
     * Código de teste 4 (recursivo). Este método calcula o n-ésimo termo da
     * sequência de Fibonacci (1, 1, 2, 3, 5, 8...) de forma recursiva.
     * Operação contada: cada chamada do método.
     * Operações esperadas: cresce aproximadamente como 1,6^n. Crescimento exponencial.
     * Observação: para n = 48 o resultado ultrapassa o limite do int, mas isso não afeta
     * a contagem de operações nem a medição do tempo.
     * @param n Ponto inicial do algoritmo
     * @return O n-ésimo termo da sequência de Fibonacci.
     */
    static int codigo4(int n) {
        operacoes++;
        if (n <= 2)
            return 1;
        else
            return codigo4(n - 1) + codigo4(n - 2);
    }

    /**
     * Gerador de vetores aleatórios de tamanho pré-definido. 
     * @param tamanho Tamanho do vetor a ser criado.
     * @return Vetor com dados aleatórios, com valores entre 1 e (tamanho/2), desordenado.
     */
    static int[] gerarVetor(int tamanho){
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1, tamanho/2);
        }
        return vetor;
    }

    /**
     * Imprime uma linha de resultado no formato "tamanho; operações; tempo".
     * O separador ";" facilita colar os dados na planilha.
     */
    static void imprimirResultado(int tamanho, long inicio, long termino) {
        double duracao = (double)(termino - inicio) * nanoToMilli;
        System.out.printf("%d; %d; %.2f%n", tamanho, operacoes, duracao);
    }

    public static void main(String[] args) {
        int tamanho;
        int[] vetor;
        long inicio, termino;

        System.out.println("Código 1 (Teste Grande)");
        System.out.println("Tamanho; Operações; Tempo (ms)");
        for (int i = 0; i < tamanhosTesteGrande.length; i++) {
            tamanho = tamanhosTesteGrande[i];
            vetor = gerarVetor(tamanho);
            operacoes = 0;
            inicio = System.nanoTime();
            codigo1(vetor);
            termino = System.nanoTime();
            imprimirResultado(tamanho, inicio, termino);
        }

        System.out.println();
        System.out.println("Código 2 (Teste Grande)");
        System.out.println("Tamanho; Operações; Tempo (ms)");
        for (int i = 0; i < tamanhosTesteGrande.length; i++) {
            tamanho = tamanhosTesteGrande[i];
            vetor = gerarVetor(tamanho);
            operacoes = 0;
            inicio = System.nanoTime();
            codigo2(vetor);
            termino = System.nanoTime();
            imprimirResultado(tamanho, inicio, termino);
        }
        vetor = null; // libera a memória dos vetores grandes antes dos próximos testes

        System.out.println();
        System.out.println("Código 3 (Teste Médio)");
        System.out.println("Tamanho; Operações; Tempo (ms)");
        for (int i = 0; i < tamanhosTesteMedio.length; i++) {
            tamanho = tamanhosTesteMedio[i];
            vetor = gerarVetor(tamanho);
            operacoes = 0;
            inicio = System.nanoTime();
            codigo3(vetor);
            termino = System.nanoTime();
            imprimirResultado(tamanho, inicio, termino);
        }

        System.out.println();
        System.out.println("Código 4 (Teste Pequeno) - aqui o tamanho é o próprio n, sem vetor");
        System.out.println("n; Operações; Tempo (ms)");
        for (int i = 0; i < tamanhosTestePequeno.length; i++) {
            tamanho = tamanhosTestePequeno[i];
            operacoes = 0;
            inicio = System.nanoTime();
            codigo4(tamanho);
            termino = System.nanoTime();
            imprimirResultado(tamanho, inicio, termino);
        }
    }
}
