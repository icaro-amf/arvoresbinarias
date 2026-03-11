package src.main.java.br.com.cefetmg.aedsii.arvoresbinarias.ArvoreSemBalanceamento.modelos;

public class ArvoreBinariaSemBalanceamento {
    private static class No {
        int reg;
        No esq, dir;

        No(int reg) {
            this.reg = reg;
            this.esq = null;
            this.dir = null;
        }
    }

    private No raiz;

    public ArvoreBinariaSemBalanceamento() {
        this.raiz = null;
    }

    public Integer pesquisa(int reg) {
        return this.pesquisa(reg, this.raiz);
    }

    private Integer pesquisa(int reg, No p) {
        if (p == null)
            return null;    //caso nao exista o Nó em questao, retorna null.
        else if (Integer.compare(reg, p.reg) < 0)
            return pesquisa(reg, p.esq);    //chamada recursiva ate encontrar ou nao o valor pesquisado
        else if (Integer.compare(reg, p.reg) > 0)
            return pesquisa(reg, p.dir);    //chamada recursiva ate encontrar ou nao o valor pesquisado
        else
            return p.reg;   //no encontrado
    }

    public void insere(int reg) {
        this.raiz = this.insere(reg, this.raiz);
    }

    private No insere(int reg, No p) {
        if (p == null)
            p = new No(reg);        // criando novo Nó (já que não existe nenhum outro elemento com o valor pesquisado).
        else if (Integer.compare(reg, p.reg) < 0)
            p.esq = insere(reg, p.esq);        // chamada recursiva - pesquisar pelo nó a esquerda se consegue inserir.
        else if (Integer.compare(reg, p.reg) > 0)
            p.dir = insere(reg, p.dir);        // chamada recursiva - pesquisar pelo nó a direita se consegue inserir.
        else
            System.out.println("Erro: Registro ja existente (" + reg + ")");        // encontrado nó já existente - então não cria novo Nó.
        return p;
    }

    // [hipótese] imprime a árvore binária de baixo para cima, tendo sempre preferência pela esquerda.
    public void emOrdem() {
        System.out.print("Em-Ordem: ");
        emOrdem(this.raiz);
        System.out.println();
    }

    private void emOrdem(No p) {
        if (p != null) {
            emOrdem(p.esq);
            System.out.print(p.reg + " ");
            emOrdem(p.dir);
        }
    }

    // [hipótese] imprime a árvore binária de cima para baixo, tendo sempre preferência pela esquerda.
    public void preOrdem() {
        System.out.print("Pre-Ordem: ");
        preOrdem(this.raiz);
        System.out.println();
    }

    private void preOrdem(No p) {
        if (p != null) {
            System.out.print(p.reg + " ");
            preOrdem(p.esq);
            preOrdem(p.dir);
        }
    }
}
