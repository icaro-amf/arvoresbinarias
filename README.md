# 🌳 Árvores Binárias de Pesquisa — BST & AVL

> **1ª Avaliação Prática — Algoritmos e Estruturas de Dados II**  
> Centro Federal de Educação Tecnológica de Minas Gerais — CEFET-MG  
> Prof. Thiago de Souza Rodrigues · Entrega: 17/03/2026

**Integrantes:**
- Ícaro A. Mota Fonseca
- André Botelho

---

## 📋 Sobre o Projeto

Implementação e comparação experimental de duas estruturas de dados hierárquicas:

- **BST** — Árvore Binária de Pesquisa Sem Balanceamento
- **AVL** — Árvore Binária de Pesquisa Com Balanceamento (Adelson-Velsky e Landis, 1962)

Ambas armazenam exclusivamente **elementos inteiros** e expõem os métodos de **inserção** e **busca**.  
O objetivo central é comparar o número de comparações realizadas durante a busca em função do tamanho e da ordem de inserção dos elementos.

---

## 📁 Estrutura do Repositório

```
.
├── src/
│   ├── ArvoreBinaria.java   # BST — inserção e busca
│   ├── ArvoreAVL.java       # AVL — inserção, rotações e busca
│   └── Experimentos.java    # Geração das árvores e coleta de comparações
├── resultados/
│   ├── tabela_bst.csv       # Comparações da BST (ordenado e aleatório)
│   ├── tabela_avl.csv       # Comparações da AVL (ordenado e aleatório)
│   ├── grafico_bst.png      # Gráfico n × comparações — BST ordenada
│   └── grafico_avl.png      # Gráfico n × comparações — AVL ordenada
├── relatorio/
│   └── relatorio.pdf        # Relatório final entregue no Moodle
└── README.md
```

---

## 🔧 Como Executar

**Pré-requisito:** Java 11 ou superior.

```bash
# Compilar
javac src/*.java -d out/

# Executar os experimentos (gera os dados das tabelas)
java -cp out/ Experimentos

# Executar as demonstrações das árvores individualmente
java -cp out/ ArvoreBinaria
java -cp out/ ArvoreAVL
```

---

## 🏗️ Implementação

### Estrutura comum dos nós

Ambas as árvores usam uma **classe interna estática `No`** com o campo `reg` (valor inteiro) e referências `esq` e `dir`. A AVL acrescenta o campo `altura` para o cálculo do Fator de Balanceamento.

```
No (BST)          No (AVL)
─────────         ──────────
reg: int          reg: int
esq: No           esq: No
dir: No           dir: No
                  altura: int
```

### 1. BST — `ArvoreBinaria.java`

Implementação recursiva seguindo o padrão dos slides do professor:

```java
// Pesquisa recursiva
private Integer pesquisa(int reg, No p) {
    if (p == null)         return null;          // não encontrado
    if (reg < p.reg)       return pesquisa(reg, p.esq);
    else if (reg > p.reg)  return pesquisa(reg, p.dir);
    else                   return p.reg;         // encontrado
}

// Inserção recursiva
private No insere(int reg, No p) {
    if (p == null)         return new No(reg);   // ponto de inserção
    if (reg < p.reg)       p.esq = insere(reg, p.esq);
    else if (reg > p.reg)  p.dir = insere(reg, p.dir);
    else /* duplicata */   System.out.println("Erro: Registro ja existente");
    return p;
}
```

> **Propriedade invariante:** `SAE < raiz < SAD` para todo nó.

---

### 2. AVL — `ArvoreAVL.java`

Estende a lógica da BST com **rebalanceamento automático** após cada inserção.  
O rebalanceamento é aplicado no **retorno da recursão** (subida), verificando o Fator de Balanceamento de cada nó do caminho percorrido.

#### Fator de Balanceamento

```
FB(v) = he(v) − hd(v)

  FB ∈ {−1, 0, +1} → nó balanceado
  FB > +1           → desbalanceado à esquerda
  FB < −1           → desbalanceado à direita
```

#### Rotações implementadas

| Nome | Gatilho | Ação |
|------|---------|------|
| **RSD** — Rotação Simples à Direita | `FB = +2`, `FB(esq) ≥ 0` | Filho esquerdo sobe |
| **RES** — Rotação Esquerda Simples  | `FB = −2`, `FB(dir) ≤ 0` | Filho direito sobe |
| **RDD** — Rotação Dupla à Direita   | `FB = +2`, `FB(esq) < 0` | RES no filho esq → RSD no nó |
| **RDE** — Rotação Dupla à Esquerda  | `FB = −2`, `FB(dir) > 0` | RSD no filho dir → RES no nó |

#### Exemplo visual — RSD (Rotação Simples à Direita)

```
    A (+2)              B (0)
   /      \            /     \
  B (+1)  AR   →    BL       A (0)
 /    \                     /    \
BL    BR                   BR    AR
```

#### Inserção recursiva com rebalanceamento

```java
private No insere(int reg, No p) {
    // Passo 1 — descida BST normal
    if (p == null)        return new No(reg);
    if (reg < p.reg)      p.esq = insere(reg, p.esq);
    else if (reg > p.reg) p.dir = insere(reg, p.dir);
    else { /* duplicata */ return p; }

    // Passo 2 — rebalanceamento na subida
    return rebalanceia(p);
}
```

---

## 📊 Experimentos

### Metodologia

Para cada estrutura (BST e AVL) foram realizados dois conjuntos de experimentos:

| Conjunto | Entrada | Tamanhos (n) |
|----------|---------|--------------|
| Ordenado  | Elementos de `1` a `n` inseridos em ordem crescente | 10k, 20k, ..., 100k |
| Aleatório | Elementos de `1` a `n` inseridos em ordem aleatória (`Collections.shuffle`) | 10k, 20k, ..., 100k |

Em cada árvore gerada, busca-se o elemento **100.001** (ausente em todas) e conta-se o número de **comparações realizadas** até o retorno `null`.

### Por que buscar 100.001?

Como 100.001 é maior que todos os elementos inseridos, a busca percorre o **caminho mais longo possível** até chegar em `null` — o que evidencia com clareza a diferença de desempenho entre as estruturas e entre inserções ordenadas vs. aleatórias.

---

### Resultados Esperados

#### BST — Inserção Ordenada (pior caso)

Quando os elementos são inseridos em ordem crescente, a BST degenera em uma **lista encadeada**. A busca percorre todos os nós.

| n | Comparações |
|---|-------------|
| 10.000 | ~10.000 |
| 20.000 | ~20.000 |
| 30.000 | ~30.000 |
| 40.000 | ~40.000 |
| 50.000 | ~50.000 |
| 60.000 | ~60.000 |
| 70.000 | ~70.000 |
| 80.000 | ~80.000 |
| 90.000 | ~90.000 |
| 100.000 | ~100.000 |

> **Complexidade:** O(n) — crescimento **linear**

#### BST — Inserção Aleatória (caso médio)

| n | Comparações (aproximadas) |
|---|---------------------------|
| 10.000 | ~27 |
| 20.000 | ~29 |
| 30.000 | ~30 |
| 40.000 | ~31 |
| 50.000 | ~32 |
| 60.000 | ~32 |
| 70.000 | ~33 |
| 80.000 | ~33 |
| 90.000 | ~34 |
| 100.000 | ~34 |

> **Complexidade:** O(log n) — crescimento **logarítmico**

#### AVL — Inserção Ordenada e Aleatória

O balanceamento garante altura máxima ≤ 1,44 · log₂(n) em qualquer cenário.

| n | Comparações (ordenado) | Comparações (aleatório) |
|---|------------------------|-------------------------|
| 10.000 | ~14 | ~15 |
| 20.000 | ~15 | ~16 |
| 30.000 | ~15 | ~16 |
| 40.000 | ~16 | ~17 |
| 50.000 | ~16 | ~17 |
| 60.000 | ~16 | ~17 |
| 70.000 | ~17 | ~17 |
| 80.000 | ~17 | ~18 |
| 90.000 | ~17 | ~18 |
| 100.000 | ~17 | ~18 |

> **Complexidade:** O(log n) — crescimento **logarítmico garantido** independente da ordem de inserção

---

### Análise Comparativa

```
Comparações × n  (inserção ordenada)

100.000 ┤                                              ╭── BST Ordenada
 90.000 ┤                                         ╭───╯
 80.000 ┤                                    ╭────╯
 70.000 ┤                               ╭────╯
 60.000 ┤                          ╭────╯
 50.000 ┤                     ╭────╯
 40.000 ┤                ╭────╯
 30.000 ┤           ╭────╯
 20.000 ┤      ╭────╯
 10.000 ┤ ╭────╯
     17 ┤•••••••••••••••••••••••••••••••••••••••••••── AVL Ordenada
        └──────────────────────────────────────────
        10k  20k  30k  40k  50k  60k  70k  80k  90k  100k
```

| Estrutura | Inserção Ordenada | Inserção Aleatória |
|-----------|-------------------|--------------------|
| BST | **O(n)** — degenerada | O(log n) — caso médio |
| AVL | **O(log n)** — sempre | O(log n) — sempre |

> A AVL garante O(log n) em **qualquer** cenário graças às rotações de rebalanceamento,  
> enquanto a BST sem balanceamento pode degradar para O(n) com entradas ordenadas.

---

## 📚 Conceitos-chave

### Propriedade BST
Para todo nó `v`: todos os valores na subárvore esquerda são **menores** que `v.reg`, e todos na direita são **maiores**.

### Propriedade AVL
Uma árvore binária `T` é **AVL** quando, para qualquer nó `v`:

```
|FB(v)| = |he(v) − hd(v)| ≤ 1
```

Se uma árvore T é AVL, todas as suas subárvores também são AVL.

### Altura garantida da AVL

```
h(n) ≤ 1,44 · log₂(n + 2) − 1,328
```

Isso significa que a busca na AVL nunca ultrapassa ~1,44 vezes a altura de uma árvore perfeitamente balanceada.

---

## 📖 Referências

- Adelson-Velsky, G.; Landis, E. (1962). *An algorithm for the organization of information*. Proceedings of the USSR Academy of Sciences.
- Cormen, T. H. et al. *Introduction to Algorithms*. 3ª ed. MIT Press, 2009.
- Slides da disciplina — Prof. Thiago de Souza Rodrigues, CEFET-MG, 2026.

---

*CEFET-MG · Engenharia de Computação · Algoritmos e Estruturas de Dados II · 2026-1*
