package br.com.cefetmg.aedsii.arvoresbinarias;

import br.com.cefetmg.aedsii.arvoresbinarias.modelos.ArvoreSemBalanceamento;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercicio312 {
    public static void main(String[] args) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int n = 10000; n <= 100000; n += 10000) {

            ArvoreSemBalanceamento arvore = new ArvoreSemBalanceamento();

            // gera lista de 1 a n e embaralha
            List<Integer> elementos = new ArrayList<>();
            for (int i = 1; i <= n; i++)
                elementos.add(i);
            Collections.shuffle(elementos);

            long inicio = System.currentTimeMillis();

            for (int elem : elementos)
                arvore.insere(elem); // insere em ordem aleatória

            long fim = System.currentTimeMillis();

            int comp = arvore.pesquisaComContagem(100001);

            dataset.addValue(comp, "Comparações", String.valueOf(n));

            System.out.println("Elementos inseridos = " + n +
                    " | Comparações: " + comp +
                    " | Tempo: " + (fim - inicio) / 1000.0 + "s");
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Comparações Sem Balanceamento (Aleatório)",
                "Número de elementos",
                "Comparações",
                dataset
        );

        ChartFrame frame = new ChartFrame("Gráfico", chart);
        frame.pack();
        frame.setVisible(true);
    }
}