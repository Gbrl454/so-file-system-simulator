# Sistema de Páginação - Simulador

## Comandos

### Navegação e Acesso a Diretórios

| **Comando**       | **Ação**                                                  |
| ----------------- | --------------------------------------------------------- |
| `cd`              | Volta ao diretório ROOT.                                  |
| `cd ..`           | Volta uma pasta em relação ao diretório atual.            |
| `cd ~/path`       | Vai para o diretório `path` a partir do diretório ROOT.   |
| `cd ./path`       | Vai para o diretório `path` a partir do diretório atual.  |
| `cd /path`        | Vai para o diretório absoluto `path`.                     |
| `cd /path1/path2` | Cria uma fila e executa `cd /path1` e depois `cd /path2`. |

### Criação e Exclusão de Arquivos

| **Comando**              | **Ação**                                                            |
| ------------------------ | ------------------------------------------------------------------- |
| `create filename`        | Cria um arquivo chamado `filename` no diretório atual.              |
| `rm filename`            | Apaga o arquivo `filename` do diretório atual.                      |
| `cp filename path`       | Copia o arquivo `filename` para o diretório especificado em `path`. |
| `mv filename path`       | Move o arquivo `filename` para o diretório especificado em `path`.  |
| `rename oldname newname` | Renomeia o arquivo `oldname` para `newname`.                        |

### Criação e Exclusão de Diretórios

| **Comando**                 | **Ação**                                                              |
| --------------------------- | --------------------------------------------------------------------- |
| `mkdir dirname`             | Cria um diretório chamado `dirname` no diretório atual.               |
| `rmdir dirname`             | Apaga o diretório vazio `dirname` do diretório atual.                 |
| `rmdir -r dirname`          | Apaga o diretório `dirname` e todos os seus subdiretórios e arquivos. |
| `mvdir dirname path`        | Move o diretório `dirname` para o diretório especificado em `path`.   |
| `renamedir oldname newname` | Renomeia o diretório `oldname` para `newname`.                        |

### Listagem e Visualização

| **Comando**     | **Ação**                                                                              |
| --------------- | ------------------------------------------------------------------------------------- |
| `ls`            | Lista os arquivos e diretórios no diretório atual.                                    |
| `ls -l`         | Lista os arquivos e diretórios no diretório atual com detalhes (tamanho, data, etc.). |
| `ls -a`         | Lista todos os arquivos, incluindo os ocultos (iniciados com `.`).                    |
| `ls path`       | Lista os arquivos e diretórios do diretório especificado em `path`.                   |
| `find filename` | Pesquisa por arquivos com o nome `filename` no diretório atual e subdiretórios.       |

### Informações e Manipulação de Arquivos

| **Comando**                  | **Ação**                                                                       |
| ---------------------------- | ------------------------------------------------------------------------------ |
| `cat filename`               | Exibe o conteúdo do arquivo `filename`.                                        |
| `more filename`              | Exibe o conteúdo do arquivo `filename` página por página.                      |
| `less filename`              | Exibe o conteúdo do arquivo `filename` com rolagem interativa.                 |
| `touch filename`             | Atualiza a data de modificação de `filename` ou cria o arquivo se não existir. |
| `file filename`              | Exibe o tipo de arquivo de `filename`.                                         |
| `chmod permissions filename` | Modifica as permissões de `filename` (e.g., `chmod 755 filename`).             |

### Outras Ações

| **Comando**                  | **Ação**                                                                                      |
| ---------------------------- | --------------------------------------------------------------------------------------------- |
| `exit`                       | Encerra o simulador.                                                                          |
| `clear`                      | Limpa a tela do terminal.                                                                     |
| `history`                    | Exibe os comandos anteriores usados.                                                          |
| `cp -r source destination`   | Copia o diretório `source` e todo o seu conteúdo para o diretório `destination`.              |
| `cp -i filename destination` | Solicita confirmação antes de substituir o arquivo `filename` ao copiá-lo para `destination`. |
| `help`                       | Exibe a ajuda com os comandos disponíveis.                                                    |
| `man command`                | Exibe o manual do comando especificado (`command`).                                           |

### Exemplos de Comandos

- **Criar diretório:** `mkdir novoDiretorio`
- **Mudar para o diretório `/home/user`:** `cd /home/user`
- **Apagar diretório vazio:** `rmdir diretorioVazio`
- **Criar arquivo:** `create arquivo.txt`
- **Copiar arquivo para outro diretório:** `cp arquivo.txt /home/user/`
- **Mover diretório:** `mvdir /home/user/oldname /home/user/newname`

### Comandos Adicionais

| **Comando** | **Ação**                                                       |
| ----------- | -------------------------------------------------------------- |
| `tree`      | Exibe a estrutura de diretórios e arquivos em forma de árvore. |
| `du -sh`    | Exibe o uso de disco total de um diretório.                    |
| `df`        | Exibe o espaço disponível no sistema de arquivos.              |

### Conclusão

Esses comandos podem ser usados em um sistema de arquivos simulado para realizar as ações de navegação, manipulação de arquivos e diretórios e exibição de informações do sistema. A implementação pode ser expandida conforme necessário para suportar mais funcionalidades e manipulação de arquivos.
