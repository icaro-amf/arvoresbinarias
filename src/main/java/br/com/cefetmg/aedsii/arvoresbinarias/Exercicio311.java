package br.com.cefetmg.aedsii.arvoresbinarias;

import br.com.cefetmg.aedsii.arvoresbinarias.modelos.ArvoreSemBalanceamento;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Exercicio311 {
    public static void main(String[] args) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); // inicia um Dataset para armazenar os dados do grafico a ser produzido

        for (int n = 10000; n <= 100000; n += 10000) { //faz a escolha do ultimo reg da arvore,
            // por iterarar de 1 em 1, representa tmb a qnt de elementos

            ArvoreSemBalanceamento arvore = new ArvoreSemBalanceamento();

            long inicio = System.currentTimeMillis(); // pega o tempo que e iniciada a contagem de 1 ate n.

            for (int i = 1; i <= n; i++) {
                arvore.insere(i); //insere termo a termo na arvore de forma ordenada, de 1 ate n.
            }
            long fim = System.currentTimeMillis(); // pega o tempo que finalizou a contagem de 1 ate n.

            int comp = arvore.pesquisaComContagem(100001); //comp recebe o parametro 100001 para buscar em cada arvore

            dataset.addValue(comp, "Comparações", String.valueOf(n)); //valor eixo Y(Number), nome da linha(String), valores no eixo x (obrigatorio ser um Objeto)

            System.out.println("Elementos inseridos = " + n + " | Comparações: " + comp + " | Tempo: " + (fim - inicio)/1000.0 + "s");
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Comparações na Sem Balanceamento (Ordenada)",
                "Número de elementos",
                "Comparações",
                dataset
        ); //Cria o grafico completo

        ChartFrame frame = new ChartFrame("Gráfico", chart);
        frame.pack();
        frame.setVisible(true);//configura o grafico para se exibido na tela
    }
}