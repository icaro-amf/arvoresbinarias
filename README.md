# 🌳 Árvores Binárias

> **1ª Avaliação Prática — Algoritmos e Estruturas de Dados II**  
> Centro Federal de Educação Tecnológica de Minas Gerais — CEFET-MG  
> Prof. Thiago de Souza Rodrigues · Entrega: 17/03/2026

**Integrantes:**
- Ícaro A. Mota Fonseca
- André J. Espíndola Botelho

---

## 📋 Sobre o Projeto

Implementação e comparação experimental de duas árvores binárias de pesquisa em Java:

- **`ArvoreSemBalanceamento`** — Árvore Binária de Pesquisa Sem Balanceamento
- **`ArvoreAVL`** — Árvore Binária de Pesquisa Com Balanceamento (Adelson-Velsky e Landis, 1962)

Ambas armazenam exclusivamente **elementos inteiros** e expõem os métodos de **inserção** e **busca**.  
O objetivo central é comparar o número de comparações realizadas durante a busca em função do tamanho e da ordem de inserção dos elementos, gerando gráficos com a biblioteca **JFreeChart**.

---

## 📁 Estrutura do Repositório

```
arvoresbinarias/
├── src/
│   └── main/
│       └── java/
│           └── br/com/cefetmg/aedsii/arvoresbinarias/
│               ├── modelos/
│               │   ├── ArvoreBinaria.java           # Classe abstrata base
│               │   ├── ArvoreSemBalanceamento.java  # Inserção iterativa, sem balanceamento
│               │   └── ArvoreAVL.java               # Inserção recursiva com rotações AVL
│               ├── Exercicio311.java                # BST ordenada — gráfico
│               ├── Exercicio312.java                # BST aleatória — gráfico
│               ├── Exercicio313.java                # BST ordenada vs aleatória — gráfico único
│               ├── Exercicio321.java                # AVL ordenada — gráfico
│               ├── Exercicio322.java                # AVL aleatória — gráfico
│               └── Exercicio323.java                # AVL ordenada vs aleatória — gráfico único
├── relatorio/
│   └── relatorio.pdf                               # Relatório entregue no Moodle
├── pom.xml
└── README.md
```

---

## 🔧 Como Executar

**Pré-requisitos:** Java 21 · Maven

```bash
# Compilar o projeto
mvn compile

# Executar um exercício específico (exemplo: 3.1.1)
mvn exec:java -Dexec.mainClass="br.com.cefetmg.aedsii.arvoresbinarias.Exercicio311"
```

Cada exercício abre uma **janela com o gráfico** gerado pelo JFreeChart e imprime no console os dados de comparações e tempo de cada árvore.

---

## 🏗️ Implementação

### Design — Herança

O projeto usa uma **classe abstrata `ArvoreBinaria`** como base, centralizando a lógica compartilhada pelas duas estruturas:

```
ArvoreBinaria  (abstrata)
├── No (classe interna)    — reg: int, esq: No, dir: No
├── pesquisa()             — iterativa, herdada por ambas as filhas
├── pesquisaComContagem()  — pesquisa + contador de comparações
├── insere()               — abstrato, cada filha define seu comportamento
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

---

### ArvoreBinaria — Classe Base

O campo `comparacoes` é incrementado a cada nó visitado durante a pesquisa e zerado antes de cada nova busca:

```java
public int pesquisaComContagem(int reg) {
    comparacoes = 0;
    pesquisa(reg, this.raiz);
    return comparacoes;
}
```

A pesquisa é **iterativa** para evitar `StackOverflowError`. O Java possui uma limitação natural de chamadas recursivas inferior a 20.000 — o que seria estourado pela `ArvoreSemBalanceamento` com inserção ordenada, onde a árvore degenera em lista encadeada podendo atingir até 100.000 níveis de profundidade:

```java
private Integer pesquisa(int reg, No p) {
    while (p != null) {
        comparacoes++;
        if (Integer.compare(reg, p.reg) < 0) p = p.esq;
        else if (Integer.compare(reg, p.reg) > 0) p = p.dir;
        else return p.reg;
    }
    return null;
}
```

---

### ArvoreSemBalanceamento

A inserção também é **iterativa** pelo mesmo motivo — inserção ordenada de 100.000 elementos geraria 100.000 chamadas recursivas:

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

Estende `ArvoreBinaria` com o `NoAVL` (acrescenta campo `altura`) e rebalanceamento automático após cada inserção. A inserção pode ser **recursiva** pois o balanceamento garante altura máxima de ~17 níveis para 100.000 elementos — muito abaixo do limite do Java.

#### Fator de Balanceamento

```
FB(v) = he(v) − hd(v)

  FB ∈ {−1, 0, +1} → nó balanceado
  FB > +1           → desbalanceado à esquerda
  FB < −1           → desbalanceado à direita
```

A convenção `altura(null) = -1` garante que o FB de nós folha seja calculado corretamente: `FB = (-1) - (-1) = 0`.

#### Rotações

| Nome | Gatilho | Ação |
|------|---------|------|
| **RSD** — Rotação Simples à Direita | `FB = +2`, `FB(esq) ≥ 0` | Filho esquerdo sobe |
| **RES** — Rotação Esquerda Simples  | `FB = −2`, `FB(dir) ≤ 0` | Filho direito sobe |
| **RDD** — Rotação Dupla à Direita   | `FB = +2`, `FB(esq) < 0` | RES no filho esq → RSD no nó |
| **RDE** — Rotação Dupla à Esquerda  | `FB = −2`, `FB(dir) > 0` | RSD no filho dir → RES no nó |

O rebalanceamento é chamado no **retorno de cada nível de recursão** (subida), verificando o FB de cada nó do caminho percorrido.

---

## 📊 Experimentos

### Metodologia

Para cada estrutura foram realizados dois conjuntos de experimentos, gerando um gráfico por execução via JFreeChart:

| Exercício | Estrutura | Entrada | Gráfico |
|-----------|-----------|---------|---------|
| 3.1.1 | `ArvoreSemBalanceamento` | Ordenada (1 a n) | individual |
| 3.1.2 | `ArvoreSemBalanceamento` | Aleatória (`Collections.shuffle`) | individual |
| 3.1.3 | `ArvoreSemBalanceamento` | Ordenada **e** Aleatória | único com 2 linhas |
| 3.2.1 | `ArvoreAVL` | Ordenada (1 a n) | individual |
| 3.2.2 | `ArvoreAVL` | Aleatória (`Collections.shuffle`) | individual |
| 3.2.3 | `ArvoreAVL` | Ordenada **e** Aleatória | único com 2 linhas |

Em cada árvore gerada busca-se o elemento **100.001** — ausente em todas e maior que qualquer elemento inserido, forçando a busca a percorrer o **caminho mais longo possível** até chegar em `null`.

Os tamanhos de `n` variam de **10.000 até 100.000** com intervalo de **10.000** (10 árvores por experimento).

---

### Resultados

#### ArvoreSemBalanceamento — Inserção Ordenada (pior caso)

Com inserção em ordem crescente a árvore degenera em **lista encadeada**. A busca percorre todos os `n` nós — confirmando O(n):

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

O balanceamento garante altura máxima ≤ 1,44 · log₂(n) em qualquer cenário — ordenado ou aleatório:

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

## 🔩 Dependências

| Dependência | Versão | Uso |
|-------------|--------|-----|
| Java | 21 | Linguagem |
| Maven | — | Build e gerenciamento de dependências |
| JFreeChart | 1.5.6 | Geração dos gráficos |

---

## 📖 Referências

- Adelson-Velsky, G.; Landis, E. (1962). *An algorithm for the organization of information*. Proceedings of the USSR Academy of Sciences.
- Cormen, T. H. et al. *Introduction to Algorithms*. 3ª ed. MIT Press, 2009.
- Slides da disciplina — Prof. Thiago de Souza Rodrigues, CEFET-MG, 2026.

---

*CEFET-MG · Engenharia de Computação · Algoritmos e Estruturas de Dados II · 2026-1*
