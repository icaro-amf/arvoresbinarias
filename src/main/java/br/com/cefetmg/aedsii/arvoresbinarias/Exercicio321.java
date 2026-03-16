package br.com.cefetmg.aedsii.arvoresbinarias;

import br.com.cefetmg.aedsii.arvoresbinarias.modelos.ArvoreAVL;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Exercicio321 {
    public static void main(String[] args) {
        //Todo o corpo do codigo sera similar aos das questoes da 3.1, para nao haver repeticao de codigo omitirei informacoes ja citadas.
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int n = 10000; n <= 100000; n += 10000) { //cria arvores de 10k,20k,...,100k de numeros

            ArvoreAVL arvore = new ArvoreAVL();// instancia um arvoreAVL para cada valor de n.

            long inicio = System.currentTimeMillis();

            for (int i = 1; i <= n; i++) {
                arvore.insere(i); // insere em ordenadamente e crescente
            }
            long fim = System.currentTimeMillis();

            int comp = arvore.pesquisaComContagem(100001);

            dataset.addValue(comp, "Comparações", String.valueOf(n));

            System.out.println("Elementos inseridos = " + n +
                    " | Comparações: " + comp +
                    " | Tempo: " + (fim - inicio) / 1000.0 + "s");
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Comparações na AVL (Ordenado)",
                "Número de elementos",
                "Comparações",
                dataset
        );

        ChartFrame frame = new ChartFrame("Gráfico 3.2.1", chart);
        frame.pack();
        frame.setVisible(true);
    }
}