package ArvoreAVL;

import ArvoreAVL.modelos.ArvoreAVL;

public class Main { public static void main(String[] args) {
        System.out.println("=== Arvore AVL — CEFET-MG ===\n");

        // ------------------------------------------------------------------
        // Exemplo 1 — Slide pág. 12: inserção 4 5 7 2 1 3 6
        // O slide mostra passo a passo todas as rotações que ocorrem.
        // ------------------------------------------------------------------
        System.out.println("--- Exemplo 1 (slide pag.12): insercao { 4, 5, 7, 2, 1, 3, 6 } ---");
        ArvoreAVL avl1 = new ArvoreAVL();
        for (int v : new int[]{4, 5, 7, 2, 1, 3, 6}) {
            System.out.println("Inserindo: " + v);
            avl1.insere(v);
        }
        System.out.println();
        avl1.emOrdem();
        avl1.preOrdem();

        // ------------------------------------------------------------------
        // Exemplo 2 — Slide pág. 8 (RES): inserindo 38 desbalanceia 32
        // ------------------------------------------------------------------
        System.out.println("\n--- Exemplo 2 (RES - pag.8): insercao { 32, 31, 35, 33, 36, 38 } ---");
        ArvoreAVL avl2 = new ArvoreAVL();
        for (int v : new int[]{32, 31, 35, 33, 36, 38}) {
            System.out.println("Inserindo: " + v);
            avl2.insere(v);
        }
        System.out.println();
        avl2.emOrdem();
        avl2.preOrdem();

        // ------------------------------------------------------------------
        // Exemplo 3 — Slide pág. 9 (RSD): inserindo 5 desbalanceia 50
        // ------------------------------------------------------------------
        System.out.println("\n--- Exemplo 3 (RSD - pag.9): insercao { 50, 20, 70, 10, 30, 5 } ---");
        ArvoreAVL avl3 = new ArvoreAVL();
        for (int v : new int[]{50, 20, 70, 10, 30, 5}) {
            System.out.println("Inserindo: " + v);
            avl3.insere(v);
        }
        System.out.println();
        avl3.emOrdem();
        avl3.preOrdem();

        // ------------------------------------------------------------------
        // Exemplo 4 — Slide pág. 10 (RDE): inserindo 60 desbalanceia 50
        // ------------------------------------------------------------------
        System.out.println("\n--- Exemplo 4 (RDE - pag.10): insercao { 50, 20, 80, 70, 90, 60 } ---");
        ArvoreAVL avl4 = new ArvoreAVL();
        for (int v : new int[]{50, 20, 80, 70, 90, 60}) {
            System.out.println("Inserindo: " + v);
            avl4.insere(v);
        }
        System.out.println();
        avl4.emOrdem();
        avl4.preOrdem();

        // ------------------------------------------------------------------
        // Exemplo 5 — Slide pág. 11 (RDD): inserindo 30 desbalanceia 50
        // ------------------------------------------------------------------
        System.out.println("\n--- Exemplo 5 (RDD - pag.11): insercao { 50, 20, 90, 10, 40, 30 } ---");
        ArvoreAVL avl5 = new ArvoreAVL();
        for (int v : new int[]{50, 20, 90, 10, 40, 30}) {
            System.out.println("Inserindo: " + v);
            avl5.insere(v);
        }
        System.out.println();
        avl5.emOrdem();
        avl5.preOrdem();

        // ------------------------------------------------------------------
        // Pesquisa — usando a árvore do Exemplo 1
        // ------------------------------------------------------------------
        System.out.println("\n--- Pesquisa na arvore do Exemplo 1 ---");
        for (int chave : new int[]{1, 3, 6, 9}) {
            Integer res = avl1.pesquisa(chave);
            if (res != null)
                System.out.println("pesquisa(" + chave + ") -> ENCONTRADO: " + res);
            else
                System.out.println("pesquisa(" + chave + ") -> NAO encontrado");
        }
    }
}


