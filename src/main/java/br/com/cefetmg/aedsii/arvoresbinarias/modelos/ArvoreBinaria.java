package br.com.cefetmg.aedsii.arvoresbinarias.modelos;

public abstract class ArvoreBinaria {

    protected static class No {
        public int reg;
        public No  esq;
        public No  dir;

        public No(int reg) {
            this.reg = reg;
            this.esq = null;
            this.dir = null;
        }
    }

    protected No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    // ── Pesquisa ──────────────────────────────────────────────
    public Integer pesquisa(int reg) {
        return pesquisa(reg, this.raiz);
    }

    private Integer pesquisa(int reg, No p) {
        if (p == null)
            return null;
        else if (Integer.compare(reg, p.reg) < 0)
            return pesquisa(reg, p.esq);
        else if (Integer.compare(reg, p.reg) > 0)
            return pesquisa(reg, p.dir);
        else
            return p.reg;
    }

    // ── Inserção (cada filha define seu comportamento) ────────
    public void insere(int reg) {
        this.raiz = insere(reg, this.raiz);
    }

    protected abstract No insere(int reg, No p);

    // ── Travessias ────────────────────────────────────────────
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