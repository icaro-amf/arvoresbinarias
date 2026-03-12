package br.com.cefetmg.aedsii.arvoresbinarias;

import br.com.cefetmg.aedsii.arvoresbinarias.modelos.ArvoreSemBalanceamento;

public class exerciciosBST {
    public static void main(String[] args) {
        System.out.println("n\t\tComparações (Ordenado)");

        for (int n = 10000; n <= 100000; n += 10000) {
            ArvoreSemBalanceamento arvore = new ArvoreSemBalanceamento();

            long inicio = System.currentTimeMillis();

            for (int i = 1; i <= n; i++)
                arvore.insere(i);

            long fim = System.currentTimeMillis();

            int comp = arvore.pesquisaComContagem(100001);
            System.out.println("n = " + n + " | Comparações: " + comp + " | Tempo: " + (fim - inicio)/1000.0 + "s");
        }
    }
}