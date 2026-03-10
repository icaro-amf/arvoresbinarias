package ArvoreSemBalanceamento;
import ArvoreSemBalanceamento.modelos.ArvoreBinariaSemBalanceamento;

public class Main {
    public static void main(String[] args) {
        ArvoreBinariaSemBalanceamento arvore = new ArvoreBinariaSemBalanceamento();

        System.out.println("-- Inserindo elementos --");
        int[] valores =  {34, 55, 5, 1, 17, 90, 75, 26, 13, 40, 43, 80};
        
        for (int v : valores) {
            System.out.println("FOREACH Inserindo: " + v);
            arvore.insere(v);
        }

        //System.out.println("Inserindo: 34 (duplicata)");
        //arvore.insere(34);

        System.out.println();
        arvore.emOrdem();
        arvore.preOrdem();
        
        System.out.println("\n-- Pesquisando elementos --");
        int[] buscas = {13, 43, 99, 1, 55};

        for (int chave : buscas) {
            Integer resultado = arvore.pesquisa(chave);
            if (resultado != null)
                System.out.println("Pesquisa(" + chave + ") -> ENCONTRADO: " + resultado);
            else
                System.out.println("Pesquisa(" + chave + ") -> NÃO encontrado");
        }
    }
}