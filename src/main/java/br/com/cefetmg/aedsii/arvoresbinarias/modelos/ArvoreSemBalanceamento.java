package br.com.cefetmg.aedsii.arvoresbinarias.modelos;

public class ArvoreSemBalanceamento extends ArvoreBinaria {

    public ArvoreSemBalanceamento() {
        super();
    }

    @Override
    protected No insere(int reg, No p) {
        No novo = new No(reg);
        if (p == null)
            return novo;

        No atual = p;
        while (true) {
            if (Integer.compare(reg, atual.reg) < 0) {
                if (atual.esq == null) {
                    atual.esq = novo;
                    break;
                }
                atual = atual.esq;
            } else if (Integer.compare(reg, atual.reg) > 0) {
                if (atual.dir == null) {
                    atual.dir = novo;
                    break;
                }
                atual = atual.dir;
            } else {
                System.out.println("Erro: Registro ja existente (" + reg + ")");
                break;
            }
        }
        return p;
    }
}