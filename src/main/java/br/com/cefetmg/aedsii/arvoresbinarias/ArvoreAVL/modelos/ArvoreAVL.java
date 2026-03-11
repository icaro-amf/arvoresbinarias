package src.main.java.br.com.cefetmg.aedsii.arvoresbinarias.ArvoreAVL.modelos;

public class ArvoreAVL {
    private static class No {
        int reg;       // valor armazenado
        No  esq, dir;  // filhos esquerdo e direito
        int altura;    // altura do nó (folha = 0)

        No(int reg) {
            this.reg    = reg;
            this.esq    = null;
            this.dir    = null;
            this.altura = 0;
        }
    }

    private No raiz;

    public ArvoreAVL() {
        this.raiz = null;
    }

    private int altura(No p) {
        return (p == null) ? -1 : p.altura;
    }

    private void atualizaAltura(No p) {
        p.altura = 1 + Math.max(altura(p.esq), altura(p.dir));
    }
    
    private int FB(No p) {
        return (p == null) ? 0 : altura(p.esq) - altura(p.dir);
    }

    private No RSD(No A) {
        No B  = A.esq;      // B sobe para a posição de A
        A.esq = B.dir;      // BR passa a ser filho esquerdo de A
        B.dir = A;          // A desce para filho direito de B

        atualizaAltura(A);  // A primeiro (agora está mais abaixo)
        atualizaAltura(B);  // B depois   (agora é a nova raiz local)

        return B;
    }

    private No RES(No A) {
        No B  = A.dir;      // B sobe para a posição de A
        A.dir = B.esq;      // BL passa a ser filho direito de A
        B.esq = A;          // A desce para filho esquerdo de B

        atualizaAltura(A);
        atualizaAltura(B);

        return B;
    }

    private No RDD(No A) {
        A.esq = RES(A.esq);  // passo 1: RES no filho esquerdo
        return RSD(A);        // passo 2: RSD no nó desbalanceado
    }

    private No RDE(No A) {
        A.dir = RSD(A.dir);  // passo 1: RSD no filho direito
        return RES(A);        // passo 2: RES no nó desbalanceado
    }

    private No rebalanceia(No p) {
        atualizaAltura(p);       // recalcula a altura com os novos filhos

        int fb = FB(p);

        // --- Desbalanceamento à ESQUERDA: FB > +1 ---
        if (fb > 1) {
            if (FB(p.esq) >= 0) {
                // filho esquerdo pesa à esquerda (ou igual) -> RSD
                System.out.println("  [AVL] RSD no no " + p.reg
                        + " (FB=" + fb + ", FB(esq)=" + FB(p.esq) + ")");
                p = RSD(p);
            } else {
                // filho esquerdo pesa à direita -> RDD
                System.out.println("  [AVL] RDD no no " + p.reg
                        + " (FB=" + fb + ", FB(esq)=" + FB(p.esq) + ")");
                p = RDD(p);
            }
        }
        // --- Desbalanceamento à DIREITA: FB < -1 ---
        else if (fb < -1) {
            if (FB(p.dir) <= 0) {
                // filho direito pesa à direita (ou igual) -> RES
                System.out.println("  [AVL] RES no no " + p.reg
                        + " (FB=" + fb + ", FB(dir)=" + FB(p.dir) + ")");
                p = RES(p);
            } else {
                // filho direito pesa à esquerda -> RDE
                System.out.println("  [AVL] RDE no no " + p.reg
                        + " (FB=" + fb + ", FB(dir)=" + FB(p.dir) + ")");
                p = RDE(p);
            }
        }

        return p;
    }

    public Integer pesquisa(int reg) {
        return this.pesquisa(reg, this.raiz);
    }

    private Integer pesquisa(int reg, No p) {
        if (p == null)
            return null;    //caso nao exista o Nó em questao, retorna null.
        else if (Integer.compare(reg, p.reg) < 0)
            return pesquisa(reg, p.esq);    //chamada recursiva ate encontrar ou nao o valor pesquisado (ARVORE ESQUERDA)
        else if (Integer.compare(reg, p.reg) > 0)
            return pesquisa(reg, p.dir);    //chamada recursiva ate encontrar ou nao o valor pesquisado (ARVORE DIREITA)
        else
            return p.reg;   //nó encontrado
    }

    public void insere(int reg) {
        this.raiz = this.insere(reg, this.raiz);
    }

    private No insere(int reg, No p) {
        if (p == null)
            p = new No(reg);        // criando novo Nó (já que não existe nenhum outro elemento com o valor pesquisado)
        else if (Integer.compare(reg, p.reg) < 0)
            p.esq = insere(reg, p.esq);
        else if (Integer.compare(reg, p.reg) > 0)
            p.dir = insere(reg, p.dir);
        else {
            System.out.println("Erro: Registro ja existente (" + reg + ")");
            return p;
        }
        return rebalanceia(p);
    }

    public void emOrdem() { //exibe valores em ordem crescente + FB de cada nó.
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
