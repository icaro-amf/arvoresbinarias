package br.com.cefetmg.aedsii.arvoresbinarias.modelos;

public class ArvoreAVL extends ArvoreBinaria {

    // Nó estendido com altura, específico da AVL
    protected static class NoAVL extends No {
        int altura;

        NoAVL(int reg) {
            super(reg);
            this.altura = 0;
        }
    }

    public ArvoreAVL() {
        super();
    }

    // ── Helpers de altura e fator de balanceamento ────────────
    private int altura(No p) {
        return (p == null) ? -1 : ((NoAVL) p).altura;
    }

    private void atualizaAltura(No p) {
        ((NoAVL) p).altura = 1 + Math.max(altura(p.esq), altura(p.dir));
    }

    private int FB(No p) {
        return (p == null) ? 0 : altura(p.esq) - altura(p.dir);
    }

    // ── Rotações ──────────────────────────────────────────────
    private No RSD(No A) {
        No B  = A.esq;
        A.esq = B.dir;
        B.dir = A;
        atualizaAltura(A);
        atualizaAltura(B);
        return B;
    }

    private No RES(No A) {
        No B  = A.dir;
        A.dir = B.esq;
        B.esq = A;
        atualizaAltura(A);
        atualizaAltura(B);
        return B;
    }

    private No RDD(No A) {
        A.esq = RES(A.esq);
        return RSD(A);
    }

    private No RDE(No A) {
        A.dir = RSD(A.dir);
        return RES(A);
    }

    // ── Rebalanceamento ───────────────────────────────────────
    private No rebalanceia(No p) {
        atualizaAltura(p);
        int fb = FB(p);

        if (fb > 1) {
            if (FB(p.esq) >= 0) {
                System.out.println("  [AVL] RSD no no " + p.reg
                        + " (FB=" + fb + ", FB(esq)=" + FB(p.esq) + ")");
                p = RSD(p);
            } else {
                System.out.println("  [AVL] RDD no no " + p.reg
                        + " (FB=" + fb + ", FB(esq)=" + FB(p.esq) + ")");
                p = RDD(p);
            }
        } else if (fb < -1) {
            if (FB(p.dir) <= 0) {
                System.out.println("  [AVL] RES no no " + p.reg
                        + " (FB=" + fb + ", FB(dir)=" + FB(p.dir) + ")");
                p = RES(p);
            } else {
                System.out.println("  [AVL] RDE no no " + p.reg
                        + " (FB=" + fb + ", FB(dir)=" + FB(p.dir) + ")");
                p = RDE(p);
            }
        }
        return p;
    }

    // ── Inserção com rebalanceamento ──────────────────────────
    @Override
    protected No insere(int reg, No p) {
        if (p == null)
            p = new NoAVL(reg);
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
}