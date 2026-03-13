package br.com.cefetmg.aedsii.arvoresbinarias.modelos;

public abstract class ArvoreBinaria {
    protected int comparacoes;
    protected No raiz;

    protected static class No {
        public int reg; //representa o valor a ser inserido na arvore, antigo Item reg; no livro.
        public No  esq;
        public No  dir;

        public No(int reg) {
            this.reg = reg;
            this.esq = null;
            this.dir = null;
        }

    }

    public ArvoreBinaria() {
        this.raiz = null;
    }

    public int pesquisaComContagem(int reg) { //implementacao para retornar quantas comparacoes foram feitas para achar determinado valor.
        comparacoes = 0;
        pesquisa(reg, this.raiz);
        return comparacoes;
    }


    public Integer pesquisa(int reg) {
        return pesquisa(reg, this.raiz); //realiza a pesquisa utilizando o valor a ser inserido e a raiz da arvore.
    }

    /*     No metodo de pesquisa com reg e No tiveram alteracoes para que nao estourasse o limite de recursao do java.
           Ao pesquisar, aprendi que o Java possui uma limitacao natural de chamadas recursivas inferior a 20.000 chamadas,
     por isso decidi fazer por iteracao*/
    private Integer pesquisa(int reg, No p) { //metodo de pesquisa completo.
        while (p != null) {
            comparacoes++;
            if (Integer.compare(reg, p.reg) < 0)
                p = p.esq;
            else if (Integer.compare(reg, p.reg) > 0)
                p = p.dir;
            else
                return p.reg;
        }
        return null;
    }

    public void insere(int reg) {
        this.raiz = insere(reg, this.raiz);//realiza a insercao utilizando o valor a ser inserido e a raiz da arvore.
    }

     /*Metodo insere a ser subescrito em suas classes filhas
     por ser muito especifico em cada caso, Sem Balanciameto e na AVL*/
    protected abstract No insere(int reg, No p);

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