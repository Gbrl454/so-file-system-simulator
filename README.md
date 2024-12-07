# Simulador de Sistema de Arquivos

## Metodologia

O simulador foi desenvolvido utilizando **Java** como linguagem de programação. Ele foi projetado para simular operações comuns de sistemas de arquivos, como criação, remoção, renomeação e navegação entre diretórios.

Apesar de o **journaling** já estar planejado e parcialmente implementado (com uma estrutura básica de registro criada), ainda não está funcional no simulador atual. Esse conceito é explicado no relatório para contextualizar sua importância e aplicação em sistemas de arquivos reais.

Cada comando é processado e os resultados são exibidos diretamente no terminal.

---

## Parte 1: Introdução ao Sistema de Arquivos com Journaling

### O que é um Sistema de Arquivos?

Um sistema de arquivos é uma estrutura organizacional que armazena e gerencia dados em dispositivos físicos, como discos rígidos e SSDs. Ele oferece uma interface lógica para que os usuários possam criar, organizar, acessar e modificar dados.

### O que é Journaling?

O **journaling** é uma técnica utilizada em sistemas de arquivos para garantir a integridade dos dados em casos de falhas, como quedas de energia ou erros inesperados.

O **journaling** funciona registrando operações em um log antes de aplicá-las ao sistema. Esse log (ou _journal_) permite que o sistema seja restaurado a um estado consistente após uma falha, evitando corrupção de dados.

#### Tipos de Journaling

1. **Write-Ahead Logging (WAL):** Registra as alterações antes de executá-las no sistema.
2. **Metadata-Only Journaling:** Apenas alterações nos metadados são registradas, reduzindo a sobrecarga de I/O.
3. **Data Journaling Completo:** Registra tanto metadados quanto dados do arquivo, garantindo maior segurança, mas com maior custo de desempenho.

Embora o journaling seja uma técnica essencial para sistemas reais, neste simulador, ele ainda não está implementado de forma funcional, mas sua estrutura foi planejada.

---

## Parte 2: Arquitetura do Simulador

### Estrutura de Dados

O simulador utiliza classes para representar os principais elementos do sistema de arquivos:

1. **Classe `Directory`:** Representa diretórios, contendo subdiretórios e arquivos.
2. **Classe `File`:** Representa arquivos.
3. **Classe `FileSystemSimulator`:** Gerencia o estado do sistema, incluindo o diretório atual, histórico de comandos e saída do terminal.

### Estrutura de Journaling

Embora ainda não funcional, foi planejada uma classe `Journal` para gerenciar o registro das operações realizadas no simulador. O log conterá informações como:

- Tipo da operação (ex.: criação, remoção, renomeação).
- Nome do arquivo ou diretório afetado.
- Timestamp da operação.

Essa estrutura será usada para simular um sistema de arquivos com suporte a journaling em futuras versões.

---

## Parte 3: Implementação em Java

### Comandos Implementados

O simulador suporta os seguintes comandos, permitindo operações básicas de sistemas de arquivos:

| Comando                     | Descrição                                                      |
| --------------------------- | -------------------------------------------------------------- |
| `cat <nome>`                | Exibe o conteúdo de um arquivo.                                |
| `cd ..`                     | Retorna ao diretório anterior.                                 |
| `cd`                        | Retorna diretamente ao diretório raiz.                         |
| `cd ./path1/path2`          | Navega por múltiplos diretórios em uma única linha de comando. |
| `cd ./path1`                | Navega para um subdiretório diretamente.                       |
| `clear`                     | Limpa o terminal.                                              |
| `cp exemplo.txt documentos` | Copia um arquivo ou diretório.                                 |
| `create <nome>`             | Cria um arquivo vazio no diretório atual.                      |
| `help`                      | Lista todos os comandos disponíveis.                           |
| `history`                   | Exibe o histórico de comandos executados.                      |
| `ls`                        | Lista os arquivos e subdiretórios no diretório atual.          |
| `mkdir <nome>`              | Cria um novo diretório.                                        |
| `renamedir <old> <new>`     | Renomeia um diretório existente.                               |
| `rm <nome>`                 | Remove um arquivo.                                             |
| `rmdir <nome>`              | Remove um diretório vazio.                                     |

---

## Resultados Esperados

Com base no projeto atual, espera-se que o simulador:

1. Forneça uma visão prática do funcionamento de sistemas de arquivos, permitindo a realização de operações básicas.
2. Prepare o sistema para a futura implementação de journaling, onde será possível recuperar informações consistentes mesmo em caso de falhas.
3. Facilite o aprendizado de conceitos fundamentais de sistemas operacionais.

---

## Conclusão

Este simulador representa um primeiro passo para a criação de um sistema de arquivos com suporte a journaling. Embora o conceito de journaling ainda não esteja implementado, a arquitetura modular e os comandos básicos já oferecem uma plataforma sólida para experimentação e aprendizado.

---
