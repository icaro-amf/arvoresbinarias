package br.com.cefetmg.aedsii.arvoresbinarias;

import br.com.cefetmg.aedsii.arvoresbinarias.modelos.ArvoreSemBalanceamento;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class exerciciosBST {
    public static void main(String[] args) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int n = 10000; n <= 100000; n += 10000) {

            ArvoreSemBalanceamento arvore = new ArvoreSemBalanceamento();

            long inicio = System.currentTimeMillis();

            for (int i = 1; i <= n; i++)
                arvore.insere(i);

            long fim = System.currentTimeMillis();

            int comp = arvore.pesquisaComContagem(100001);

            dataset.addValue(comp, "Comparações", String.valueOf(n));

            System.out.println("Elementos inseridos = " + n +
                    " | Comparações: " + comp +
                    " | Tempo: " + (fim - inicio)/1000.0 + "s");
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Comparações na BST",
                "Número de elementos",
                "Comparações",
                dataset
        );

        ChartFrame frame = new ChartFrame("Gráfico", chart);
        frame.pack();
        frame.setVisible(true);
    }
}