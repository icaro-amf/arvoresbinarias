# 🌳 arvoresbinarias

> **1ª Avaliação Prática — Algoritmos e Estruturas de Dados II**  
> Centro Federal de Educação Tecnológica de Minas Gerais — CEFET-MG  
> Prof. Thiago de Souza Rodrigues · Entrega: 17/03/2026

**Integrantes:**
- Ícaro A. Mota Fonseca
- André Botelho

---

## 📋 Sobre o Projeto

Implementação e comparação experimental de duas árvores binárias de pesquisa em Java:

- **`ArvoreSemBalanceamento`** — Árvore Binária de Pesquisa Sem Balanceamento
- **`ArvoreAVL`** — Árvore Binária de Pesquisa Com Balanceamento (Adelson-Velsky e Landis, 1962)

Ambas armazenam exclusivamente **elementos inteiros** e expõem os métodos de **inserção** e **busca**.  
O objetivo central é comparar o número de comparações realizadas durante a busca em função do tamanho e da ordem de inserção dos elementos.

---

## 📁 Estrutura do Repositório

```
arvoresbinarias/
├── src/
│   └── main/
│       └── java/
│           └── br/com/cefetmg/aedsii/arvoresbinarias/
│               ├── modelos/
│               │   ├── ArvoreBinaria.java          # Classe abstrata base
│               │   ├── ArvoreSemBalanceamento.java  # BST sem balanceamento
│               │   └── ArvoreAVL.java               # AVL com rotações
│               └── exerciciosBST.java               # Main — experimentos
├── relatorio/
│   └── relatorio.pdf                                # Relatório entregue no Moodle
├── pom.xml
└── README.md
```

---

## 🔧 Como Executar

**Pré-requisitos:** Java 21 · Maven

```bash
# Compilar
mvn compile

# Executar os experimentos
mvn exec:java -Dexec.mainClass="br.com.cefetmg.aedsii.arvoresbinarias.exerciciosBST"
```

---

## 🏗️ Implementação

### Design — Herança

O projeto usa uma **classe abstrata `ArvoreBinaria`** como base, centralizando a lógica comum às duas estruturas:

```
ArvoreBinaria  (abstrata)
├── pesquisa()         — iterativa, compartilhada por ambas as filhas
├── insere()           — abstrato, cada filha define seu comportamento
├── emOrdem()
└── preOrdem()
    │
    ├── ArvoreSemBalanceamento
    │     └── insere() — iterativo, sem rebalanceamento
    │
    └── ArvoreAVL
          ├── NoAVL extends No  — acrescenta campo "altura"
          ├── insere()          — recursivo, com rebalanceamento na subida
          └── rotações: RSD, RES, RDD, RDE
```

> A pesquisa é **iterativa** em ambas as estruturas para evitar `StackOverflowError`
> na `ArvoreSemBalanceamento` com inserção ordenada, onde a árvore degenera em lista
> encadeada podendo atingir profundidade de até 100.000 níveis.

---

### ArvoreSemBalanceamento

Inserção **iterativa** — desce a árvore com um ponteiro `atual` até encontrar a posição `null`:

```java
@Override
protected No insere(int reg, No p) {
    No novo = new No(reg);
    if (p == null) return novo;

    No atual = p;
    while (true) {
        if (Integer.compare(reg, atual.reg) < 0) {
            if (atual.esq == null) { atual.esq = novo; break; }
            atual = atual.esq;
        } else if (Integer.compare(reg, atual.reg) > 0) {
            if (atual.dir == null) { atual.dir = novo; break; }
            atual = atual.dir;
        } else {
            System.out.println("Erro: Registro ja existente (" + reg + ")");
            break;
        }
    }
    return p;
}
```

> **Propriedade invariante:** `SAE < raiz < SAD` para todo nó.

---

### ArvoreAVL

Inserção **recursiva com rebalanceamento** — após cada inserção, o método `rebalanceia()` é chamado na subida da recursão verificando o Fator de Balanceamento de cada nó do caminho percorrido.

#### Fator de Balanceamento

```
FB(v) = he(v) − hd(v)

  FB ∈ {−1, 0, +1} → nó balanceado
  FB > +1           → desbalanceado à esquerda
  FB < −1           → desbalanceado à direita
```

#### Rotações

| Nome | Gatilho | Ação |
|------|---------|------|
| **RSD** — Rotação Simples à Direita | `FB = +2`, `FB(esq) ≥ 0` | Filho esquerdo sobe |
| **RES** — Rotação Esquerda Simples  | `FB = −2`, `FB(dir) ≤ 0` | Filho direito sobe |
| **RDD** — Rotação Dupla à Direita   | `FB = +2`, `FB(esq) < 0` | RES no filho esq → RSD no nó |
| **RDE** — Rotação Dupla à Esquerda  | `FB = −2`, `FB(dir) > 0` | RSD no filho dir → RES no nó |

#### Exemplo visual — RSD

```
    A (+2)              B (0)
   /      \            /     \
  B (+1)  AR   →    BL       A (0)
 /    \                     /    \
BL    BR                   BR    AR
```

---

## 📊 Experimentos

### Metodologia

Para cada estrutura foram realizados dois conjuntos de experimentos:

| Conjunto | Entrada | Tamanhos (n) |
|----------|---------|--------------|
| Ordenado  | Elementos de `1` a `n` inseridos em ordem crescente | 10k, 20k, ..., 100k |
| Aleatório | Elementos de `1` a `n` inseridos em ordem aleatória | 10k, 20k, ..., 100k |

Em cada árvore gerada busca-se o elemento **100.001** (ausente em todas) contando o número de **comparações realizadas** e o **tempo de inserção**.

#### Por que buscar 100.001?

Como 100.001 é maior que todos os elementos inseridos, a busca percorre o **caminho mais longo possível** até chegar em `null` — evidenciando com clareza a diferença de desempenho entre as estruturas.

---

### Resultados

#### ArvoreSemBalanceamento — Inserção Ordenada (pior caso)

Com inserção em ordem crescente a árvore degenera em **lista encadeada** — cada novo elemento vai sempre para a direita do anterior. A busca percorre todos os `n` nós.

| n | Comparações |
|---|-------------|
| 10.000 | 10.000 |
| 20.000 | 20.000 |
| 30.000 | 30.000 |
| 40.000 | 40.000 |
| 50.000 | 50.000 |
| 60.000 | 60.000 |
| 70.000 | 70.000 |
| 80.000 | 80.000 |
| 90.000 | 90.000 |
| 100.000 | 100.000 |

> **Complexidade:** O(n) — crescimento **linear** ✅ confirmado experimentalmente

#### ArvoreSemBalanceamento — Inserção Aleatória (caso médio)

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

#### ArvoreAVL — Ordenada e Aleatória

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

100.000 ┤                                              ╭── ArvoreSemBalanceamento
 90.000 ┤                                         ╭───╯
 80.000 ┤                                    ╭────╯
 70.000 ┤                               ╭────╯
 60.000 ┤                          ╭────╯
 50.000 ┤                     ╭────╯
 40.000 ┤                ╭────╯
 30.000 ┤           ╭────╯
 20.000 ┤      ╭────╯
 10.000 ┤ ╭────╯
     17 ┤•••••••••••••••••••••••••••••••••••••••••••── ArvoreAVL
        └──────────────────────────────────────────
        10k  20k  30k  40k  50k  60k  70k  80k  90k  100k
```

| Estrutura | Inserção Ordenada | Inserção Aleatória |
|-----------|-------------------|--------------------|
| `ArvoreSemBalanceamento` | **O(n)** — degenerada | O(log n) — caso médio |
| `ArvoreAVL` | **O(log n)** — sempre | O(log n) — sempre |

> A `ArvoreAVL` garante O(log n) em **qualquer** cenário graças às rotações de rebalanceamento,
> enquanto a `ArvoreSemBalanceamento` degenera para O(n) com entradas ordenadas.

---

## 📚 Conceitos-chave

### Propriedade da Árvore Binária de Pesquisa
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

---

## 📖 Referências

- Adelson-Velsky, G.; Landis, E. (1962). *An algorithm for the organization of information*. Proceedings of the USSR Academy of Sciences.
- Cormen, T. H. et al. *Introduction to Algorithms*. 3ª ed. MIT Press, 2009.
- Slides da disciplina — Prof. Thiago de Souza Rodrigues, CEFET-MG, 2026.

---

*CEFET-MG · Engenharia de Computação · Algoritmos e Estruturas de Dados II · 2026-1*
