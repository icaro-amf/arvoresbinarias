package br.com.cefetmg.aedsii.arvoresbinarias.ArvoreSemBalanceamento.modelos;

import br.com.cefetmg.aedsii.arvoresbinarias.modelos.ArvoreBinaria;

public class ArvoreBinariaSemBalanceamento extends ArvoreBinaria {

    public ArvoreBinariaSemBalanceamento() {
        super();
    }

    @Override
    protected No insere(int reg, No p) {
        if (p == null)
            p = new No(reg);
        else if (Integer.compare(reg, p.reg) < 0)
            p.esq = insere(reg, p.esq);
        else if (Integer.compare(reg, p.reg) > 0)
            p.dir = insere(reg, p.dir);
        else
            System.out.println("Erro: Registro ja existente (" + reg + ")");
        return p;
    }
}