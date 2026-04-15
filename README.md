# Manipulação de Arquivos, Serialização de Objetos, Coleções, API Streams e Padrões de Projetos em Java

## Introdução

Este documento tem como objetivo guiar os estudantes na construção de um projeto Java chamado **SmartList**, que é um gerenciador de lista de compras. O projeto foi desenvolvido para ensinar conceitos importantes como manipulação de arquivos (texto, binário e JSON), serialização de objetos e o uso da **API Streams** para manipulação de coleções tais como: **List**, **Set** e **Map**. Além disso, serão abordados padrões como **MVC (Model-View-Controller)** -arquitetural, **Singleton**, **Strategy** e **Command** - de projetos.

O projeto é estruturado em uma aplicação Java desktop com interação por linha de comando, onde os estudantes poderão adicionar, remover, salvar e carregar produtos de uma lista de compras, além de aplicar filtros e cálculos sobre os dados.

---
## Objetivos do Projeto

O projeto **SmartList** tem como objetivo prático ensinar os seguintes conceitos:

1. **Leitura e Escrita em Arquivos de Texto**
2. **Leitura e Escrita em Arquivos Binários**
3. **Serialização de Objetos**
4. **Leitura e Escrita em Arquivos JSON**
5. **Manipulação de Coleções (List, Set e Map) com API Streams**
6. **Padrões arquitetural e de projeto(MVC, Singleton, Strategy e Command)**

---
## Requisitos Técnicos

Para executar o projeto, é necessário:

- **Linguagem de Programação:** Java
- **JDK:** Versão 21 ou superior
- **IDE:** IntelliJ IDEA (recomendado)
- **Maven:** Ferramenta de automação e gerenciamento de projetos Java. Usada principalmente para gerenciar dependências, compilar código, rodar testes e empacotar a aplicação. Ele utiliza um arquivo XML chamado pom.xml (Project Object Model) para definir configurações do projeto e dependências externas. 

---
## Estrutura do Projeto

O projeto será organizado em três pacotes principais, seguindo o padrão **MVC (Model-View-Controller)**:

1. **Pacote `model`:** Contém as classes que representam os dados e a lógica de negócio.
2. **Pacote `view`:** Responsável pela exibição dos dados e interação com o usuário.
3. **Pacote `controller`:** Faz a mediação entre a `view` e o `model`, processando as entradas do usuário e atualizando a `view` com os resultados.

Esta estrutura organiza uma aplicação em três componentes principais: Model, View e Controller. Cada um desses componentes é responsável por uma função específica, o que facilita a manutenção e o desenvolvimento da aplicação, além de promover a separação de responsabilidades.

---
## Funcionalidades do Projeto

O projeto **SmartList** oferece as seguintes funcionalidades:

1. **Adicionar Produto**
2. **Remover Produto**
3. **Imprimir Lista**
4. **Salvar Lista em Arquivo de Texto**
5. **Carregar Lista de Arquivo de Texto**
6. **Salvar Lista em Arquivo Binário**
7. **Carregar Lista de Arquivo Binário**
8. **Salvar Lista em Arquivo JSON**
9. **Carregar Lista de Arquivo JSON**
10. **Filtrar Produtos por Quantidade Mínima**
11. **Calcular Valor Total da Lista**
12. **Imprimir Lista em Ordem Alfabética**

---
## Passo a Passo do Projeto

### 1. Configuração do Projeto no IntelliJ IDEA

1. Abra o IntelliJ IDEA e crie um novo projeto.
2. Dê o nome de **SmartList** ao projeto.
3. Em **Build system**, selecione **Maven**.
4. Em **JDK**, selecione a versão 21 ou superior.
5. Clique em **Create** para criar o projeto.

### 2. Implementação das Classes

#### Classe `Produto`

A classe `Produto` representa um item da lista de compras e contém os atributos `nome`, `quantidade` e `preco`.

```java
package br.com.model;

public class Produto {
    private String nome;
    private int quantidade;
    private double preco;

    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " - " + quantidade + " unidades - R$" + preco;
    }
}
```

#### Classe `ListaDeCompras`

A classe `ListaDeCompras` gerencia uma lista de produtos e oferece métodos para adicionar, remover e manipular os produtos.

```java
package br.com.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListaDeCompras {
    private List<Produto> produtos;

    public ListaDeCompras() {
        produtos = new ArrayList<>();
    }

    // Adiciona um produto à lista
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto da lista pelo nome
    public void removerProduto(String nome) {
        produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    @Override
    public String toString() {
        if (produtos.isEmpty()) {
            return "Lista de compras vazia.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Compras ---\n");

        // Itera sobre os produtos e adiciona cada um ao StringBuilder
        for (int i = 0; i < produtos.size(); i++) {
            sb.append((i + 1)).append(". ").append(produtos.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
```
#### Classe `ListaDeComprasView`

A classe `ListaDeComprasView` é responsável por exibir o menu e capturar as entradas do usuário.

```java
package br.com.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ListaDeComprasView {
    private Scanner scanner;

    public ListaDeComprasView() {
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n--- Gerenciador de Lista de Compras ---");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Remover Produto");
        System.out.println("3. Exibir a Lista de Compras");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int lerOpcao() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public String lerNomeProduto() {
        String nome = "";
        boolean nomeValido = false;
        while(!nomeValido) {
            System.out.print("Nome do Produto: ");
            nome = scanner.nextLine().trim();

            if (nome.isEmpty()) {
                System.out.println("Erro: informe o nome do produto");
            }else{
                nomeValido = true;
            }
        }
        return nome;
    }

    public int lerQuantidade() {
        int quantidade = 0;
        boolean quantidadeValida = false;
        while (!quantidadeValida) {
            try {
                System.out.print("Quantidade: ");
                quantidade = scanner.nextInt();
                scanner.nextLine();
                quantidadeValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Quantidade deve ser um número inteiro. Tente novamente.");
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        }
        return quantidade;
    }

    public double lerPreco() {
        System.out.print("Preço: ");
        return scanner.nextDouble();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
```

#### Classe `ListaDeComprasController`

A classe `ListaDeComprasController` faz a mediação entre a `view` e o `model`, processando as entradas do usuário e atualizando a `view` com os resultados.

```java
package br.com.controller;

import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;
import br.com.model.Produto;

public class ListaDeComprasController {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public ListaDeComprasController(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    public void iniciar() {
        int opcao;
        do {
            view.exibirMenu();
            opcao = view.lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarProduto();
                break;
            case 2:
                removerProduto();
                break;
            case 3:
                exibirLista();
                break;
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    private void adicionarProduto() {
        String nome = view.lerNomeProduto();
        int quantidade = view.lerQuantidade();
        double preco = view.lerPreco();
        model.adicionarProduto(new Produto(nome, quantidade, preco));
    }

    private void removerProduto() {
        String nome = view.lerNomeProduto();
        model.removerProduto(nome);
    }

    private void exibirLista(){
        view.exibirMensagem(model.toString());
    }
}
```

#### Classe `Main`

A classe `Main` é o ponto de entrada do programa, onde o `model`, `view` e `controller` são inicializados.

```java
package br.com;

import br.com.controller.ListaDeComprasController;
import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;

public class Main {
    public static void main(String[] args) {
        ListaDeCompras model = new ListaDeCompras();
        ListaDeComprasView view = new ListaDeComprasView();
        ListaDeComprasController controller = new ListaDeComprasController(model, view);

        controller.iniciar();
    }
}
```
#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. Um resultado igual o da figura abaixo deverá ser apresentado.

![image](https://github.com/user-attachments/assets/109ac8cc-3271-44cf-89ff-84859a39277b)

Realizar teste como: adicionar produtos, remover e exibir a lista.

---
# Manipulação de Arquivos em Java

A manipulação de arquivos permite a leitura e gravação de dados em arquivos de texto e binários, trabalhando com fluxo de dados (*streams*). Um fluxo representa uma sequência contínua de dados que pode ser lida ou escrita de forma sequencial.

- **Fluxos de entrada** permitem ler dados de uma fonte, como um arquivo.
- **Fluxos de saída** permitem escrever dados para um destino.

## Tipos de Fluxos

### 1. Fluxos de Caracteres
Usados para manipular dados de texto, permitindo a leitura e gravação caractere por caractere. Principais classes:

- **`Reader`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de entrada de caracteres.
- **`Writer`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de saída de caracteres.

### 2. Fluxos de Bytes
Usados para manipular dados binários, como imagens e sons. Permitem manipular bytes individualmente. Principais classes:

- **`InputStream`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de entrada de bytes.
- **`OutputStream`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de saída de bytes.

## Abertura e Criação de Arquivos Texto
Para manipular arquivo de texto em Java, é necessário primeiro abrir ou criar o arquivo. Isso pode ser feito usando classes como `File`, `FileReader` e `FileWriter`.

### Criação de um Novo Arquivo para Gravação
```java
File file = new File("novo_arquivo.txt");
FileWriter writer = new FileWriter(file);
```
A classe `File` permite manipular arquivos e diretórios no sistema, possibilitando:

- Criar e excluir arquivos;
- Verificar a existência de um arquivo;
- Trabalhar em conjunto com `FileReader` e `FileWriter` para leitura e gravação de dados.

### Métodos do `FileWriter`

| Método | Descrição |
|---------|-------------|
| `void write(String text)` | Escreve uma string no arquivo. |
| `void write(char c)` | Escreve um caractere no arquivo. |
| `void write(char[] c)` | Escreve um array de caracteres no arquivo. |
| `void flush()` | Libera os dados do buffer para o arquivo. |
| `void close()` | Fecha o arquivo. |

## Gravação de Arquivos de Texto
A gravação pode ser feita caractere por caractere ou linha por linha.

### 1. Gravação de Caracteres
```java
FileWriter writer = new FileWriter("arquivo.txt");
writer.write("Olá, mundo!");
writer.close();
```

### 2. Gravação de Linhas (Usando `BufferedWriter`)
```java
BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("arquivo.txt"));
bufferedWriter.write("Linha 1");
bufferedWriter.newLine();
bufferedWriter.write("Linha 2");
bufferedWriter.close();
```
A classe `BufferedWriter` é usada em conjunto com `FileWriter` para melhorar o desempenho da gravação, pois armazena dados em buffer antes de escrever no arquivo físico. Isso melhora o desempenho, pois reduz o número de operações de gravação no disco, que são relativamente lentas.

O método `newLine()` insere uma quebra de linha de forma independente do sistema operacional.

### Uso do `flush()`
```java
BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("arquivo.txt"));
bufferedWriter.write("Escrevendo no arquivo");
bufferedWriter.newLine();
bufferedWriter.flush();
bufferedWriter.close();
```
O `flush()` força o `BufferedWriter` a escrever imediatamente todos os dados que estão no buffer para o arquivo, independentemente de o buffer estar cheio ou não. Se `flush()` não for chamado, os dados podem permanecer no buffer e não serem gravados no arquivo até que o buffer esteja cheio ou o `BufferedWriter` seja fechado. Em algumas situações, como quando se faz necessário que os dados sejam gravados imediatamente, ou quando o programa pode terminar antes que o buffer esteja cheio, é necessário usar `flush()`. No exemplo acima, existe o `bufferedWriter.close()` que também chama o `flush()`, mas é uma boa prática usar o flush antes do close, garantindo que tudo seja escrito, e que não haja perda de dados, caso o programa seja finalizado de maneira não usual. Embora o `close()` garanta que os dados sejam gravados, o `flush()` oferece controle explícito sobre o momento da gravação, o que pode ser fundamental em determinadas situações. Portanto, é uma boa prática incluir `flush()` no código, especialmente quando é preciso que os dados sejam gravados imediatamente.

## Sobrescrever vs. Adicionar ao Final (`append`)
Para adicionar conteúdo ao final de um arquivo existente, usa-se o construtor `FileWriter(String nomeDoArquivo, boolean append)` com `append = true`.

```java
FileWriter writer = new FileWriter("arquivo.txt", true);
```
Caso contrário, o arquivo será sobrescrito.

## Leitura de Arquivos Texto

### 1. Leitura de Caracteres (`FileReader`)
```java
FileReader reader = new FileReader("arquivo.txt");
int character;
while ((character = reader.read()) != -1) {
    System.out.print((char) character);
}
reader.close();
```
O `read()` lê um caractere e retorna como um inteiro. Retorna `-1` quando chega ao final do arquivo.

### 2. Leitura de Linhas (`BufferedReader`)
```java
BufferedReader bufferedReader = new BufferedReader(new FileReader("arquivo.txt"));
String line;
while ((line = bufferedReader.readLine()) != null) {
    System.out.println(line);
}
bufferedReader.close();
```
O `BufferedReader` é uma classe que tem como sua principal função ler texto de um fluxo de entrada de caracteres, armazenando-os em buffer para otimizar a leitura. A leitura de arquivos diretamente do disco pode ser uma operação lenta. O `BufferedReader` resolve esse problema lendo grandes blocos de dados do arquivo e armazenando-os em um buffer na memória. Quando é solicitada a leitura de caracteres, o `BufferedReader` primeiro verifica se os caracteres já estão no buffer. Se estiverem, ele os retorna imediatamente, sem precisar acessar o disco. Isso reduz significativamente o número de operações de leitura no disco, resultando em uma leitura mais rápida e eficiente.

## Fechamento de Arquivos

É indispensável fechar os arquivos após a conclusão das operações de leitura ou gravação para liberar recursos do sistema e garantir que todos os dados sejam corretamente gravados. Isso pode ser feito de duas maneiras:

### 1. Fechamento Manual
```java
FileReader reader = new FileReader("arquivo.txt");
reader.close();

FileWriter writer = new FileWriter("arquivo.txt");
writer.close();
```

### 2. Fechamento Automático (`try-with-resources`)
O `try-with-resources` garante que os arquivos sejam fechados automaticamente, mesmo em caso de exceção.


#### Exemplo com `FileReader`
```java
try (FileReader reader = new FileReader("arquivo.txt")) {
    int character;
    while ((character = reader.read()) != -1) {
        System.out.print((char) character);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

#### Exemplo com `FileWriter`
```java
try (FileWriter writer = new FileWriter("arquivo.txt")) {
    writer.write("Olá, mundo!");
} catch (IOException e) {
    e.printStackTrace();
}
```

Quando se utiliza o `try-with-resources`, o método `close()` é chamado automaticamente no final do bloco try, garantindo que os recursos sejam liberados corretamente. Como o `close()` do `BufferedWriter` já inclui uma chamada ao `flush()`, geralmente não é necessário chamar `flush()` explicitamente dentro do bloco try. 

### 3. Implementação das Classes para trabalhar com Arquivos de Texto na SmartList

#### Classe `ListaDeCompras`

```java
public class ListaDeCompras {

    //código omitido

    public void salvarEmArquivoTexto(String nomeArquivo)  {
        if(!produtos.isEmpty()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, false))) {
                for (Produto produto : produtos) {
                    writer.write(produto.getNome() + " - " + produto.getQuantidade() + " - "+produto.getPreco());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }


    public void carregarDeArquivoTexto(String nomeArquivo)  {
        produtos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                produtos.add(new Produto(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2])));
            }
            System.out.println("Lista do Arquivo de Texto");
                        System.out.println(this.toString());

        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: "+e.getMessage());
        }
    }

	//Código omitido
}
```

#### Classe `ListaDeComprasView`

Adaptar o método `exibirMenu()` para mostrar as novas funcionalidades:

```java
public void exibirMenu() {
    System.out.println("\n--- Gerenciador de Lista de Compras ---");
    System.out.println("1. Adicionar Produto");
    System.out.println("2. Remover Produto");
    System.out.println("3. Imprimir Lista");
    System.out.println("4. Salvar Lista em Arquivo de Texto");
    System.out.println("5. Carregar Lista de Arquivo de Texto");
    System.out.println("0. Sair");
    System.out.print("Escolha uma opção: ");
}
```

#### Classe `ListaDeComprasController`

Adequar o método `processarOpcao()`:

```java
private void processarOpcao(int opcao) {
    switch (opcao) {
        case 1:
            adicionarProduto();
            break;
        case 2:
            removerProduto();
            break;
        case 3:
            exibirLista();
            break;
        case 4:
            salvarEmAqrTexto();
            break;
        case 5:
            carregarDeArqTexto();
            break;    
        case 0:
            view.exibirMensagem("Saindo...");
            break;
        default:
            view.exibirMensagem("Opção inválida!");
    }
}
```
Ainda na classe  `ListaDeComprasController`, implementar os métodos `salvarEmAqrTexto()` e `carregarDeArqTexto()`:

```java
private void salvarEmAqrTexto() {
    model.salvarEmArquivoTexto("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"

}

private void carregarDeArqTexto() {
    model.carregarDeArquivoTexto("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"

}
```
#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, salvar em arquivo texto, verificar se o arquivo foi criado, abrir o arquivo com o bloco de notas para ver o conteúdo, por fim, carregar a lista a partir do arquivo texto.

--- 
## Abertura e Criação de Arquivos Binários

Para manipular arquivos binários em Java, é necessário primeiro abrir ou criar o arquivo. Isso pode ser feito usando classes como `File`, `FileInputStream` e `FileOutputStream`.

### Criação de um Novo Arquivo para Gravação

```java
File file = new File("novo_arquivo.bin");
FileOutputStream outputStream = new FileOutputStream(file);
```

### Gravação de Arquivos Binários
A gravação de arquivos binários pode ser feita de duas formas principais:

- **Gravação de tipos primitivos (int, double, boolean, etc.) e Strings:** Usa-se a classe `DataOutputStream` em conjunto com `FileOutputStream`.
- **Gravação de objetos inteiros:** Usa-se a classe `ObjectOutputStream` junto com `FileOutputStream`.

### 1. Gravação de Bytes (Tipos Primitivos e String):
```java
String nome = "Maria";
int idade = 22;

try (DataOutputStream data = new DataOutputStream(new FileOutputStream("arquivo.bin"))) {
    data.writeUTF(nome);
    data.writeInt(idade);
} catch (IOException e) {
    e.printStackTrace();
}
```
A classe `DataOutputStream` pode ser usada para escrever dados primitivos e strings em um fluxo de saída binário. Alguns métodos úteis incluem:
- writeChar()
- writeDouble()
- writeFloat()
- writeInt()
- writeUTF()

### 2. Gravação de Objetos Inteiros (usando ObjectOutputStream):
```java
// A variável "pessoas" é um List<Pessoa>
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pessoas.bin"))) { 
    oos.writeObject(pessoas); 
} catch (IOException e) {  
    e.printStackTrace();
}
```
Para gravar um objeto inteiro, a classe do objeto deve implementar a interface `Serializable`. Isso permite que os objetos dessa classe sejam serializados. O método `writeObject()` escreve o objeto no fluxo.

### Leitura de Arquivos Binários
Para abrir um arquivo binário existente para leitura, usa-se:
```java
File file = new File("arquivo.bin");
FileInputStream inputStream = new FileInputStream(file);
```

Tipos de leitura:

#### 1. Leitura de Bytes:
```java
StringBuilder sb = new StringBuilder();

try (DataInputStream data = new DataInputStream(new FileInputStream("arquivo.bin"))) {
    sb.append(data.readUTF()).append("\n")
      .append(data.readInt()).append("\n");
} catch (IOException e) {
    e.printStackTrace();
}

return sb.toString();
```
#### 2. Leitura de Objetos:
```java
// A variável "pessoas" é um List<Pessoa>
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pessoas.bin"))) {
    pessoas = (List<Pessoa>) ois.readObject();
} catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
```

---
## Serialização de Objetos

A serialização é o processo de transformar um objeto em uma sequência de bytes, tornando possível:
- Armazená-lo em um arquivo
- Transmiti-lo pela rede
- Persisti-lo em um banco de dados

### Por que serializar?
A serialização permite que um objeto seja recuperado posteriormente, mesmo após o programa ser encerrado. Além disso, é útil para troca de informações entre sistemas.

#### Salvando e Recuperando um Objeto em Arquivo

Quando se serializa um objeto, é possível:
- Gravar o objeto → Ele será armazenado como bytes no disco.
- Ler o objeto → Ele será convertido de volta no objeto original (desserialização).

### Serialização em Java

A serialização só é possível se a classe implementar a interface `Serializable`, do pacote `java.io`.
```java
import java.io.Serializable;

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L; // Identificador de versão

    private String nome;
    private int idade;
    private transient String senha; // Atributo transient

    public Pessoa(String nome, int idade, String senha) {
        this.nome = nome;
        this.idade = idade;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "', idade=" + idade + ", senha='[TRANSIENT]'}";
    }
}
```
#### Considerações Importantes sobre Serialização

- **transient:** Um atributo marcado como transient não será serializado. É útil para informações sensíveis ou que não precisam ser armazenadas.
- **serialVersionUID:** Um identificador único para a versão da classe serializada. Se a classe mudar e o serialVersionUID não for compatível, pode ocorrer erro ao desserializar.
Se não for definido, o Java gerará um automaticamente, o que pode causar incompatibilidades futuras.

### 4. Implementação das Classes para trabalhar com Arquivos de Binários na SmartList

Para a SmartList, o tipo de persitência adotado será objetos inteiros. Para isso, modificar a classe Produto para implementar a classe `Serealizable`. 

#### Classe Produto

```java
package br.com.model;
import java.io.Serializable;

public class Produto implements Serializable{
    //Código omitido
}
```

#### Classe ListaDeCompras
```java
public class ListaDeCompras {
    //Código omitido

    public void salvarEmArquivoBinario(String nomeArquivo) {
        if(!produtos.isEmpty()){
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
                oos.writeObject(produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }

    @SuppressWarnings("unchecked") // Suprime avisos de operações não verificadas, esta anotação é usada para silenciar aviso do compilador.
    public void carregarDeArquivoBinario(String nomeArquivo) {
        produtos.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            produtos = (List<Produto>) ois.readObject();
        } catch (ClassNotFoundException | IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
        }
    }
    //Código omitido
}
```

#### Classe ListaDeComprasView

```java
public class ListaDeComprasView {
    private Scanner scanner;

    public ListaDeComprasView() {
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n--- Gerenciador de Lista de Compras ---");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Remover Produto");
        System.out.println("3. Imprimir Lista");
        System.out.println("4. Salvar Lista em Arquivo de Texto");
        System.out.println("5. Carregar Lista de Arquivo de Texto");
        System.out.println("6. Salvar Lista em Arquivo Binário");
        System.out.println("7.Carregar Lista de Arquivo Binário");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    //Código omitido
}
```
#### Classe ListaDeComprasController

```java
public class ListaDeComprasController {
    //Código omitido

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarProduto();
                break;
            case 2:
                removerProduto();
                break;
            case 3:
                exibirLista();
                break;
            case 4:
                salvarEmAqrTexto();
                break;
            case 5:
                carregarDeArqTexto();
                break;
            case 6:
                salvarEmArquivoBinario();
                break;
            case 7:
                carregarDeArquivoBinario();
                break;
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    //Código omitido

    private void salvarEmArquivoBinario(){
        model.salvarEmArquivoBinario("lista_compras.bin");
    }

    private void carregarDeArquivoBinario(){
        model.carregarDeArquivoBinario("lista_compras.bin");
    }
}
```
#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, salvar em arquivo binário, verificar se o arquivo foi criado, por fim, carregar a lista a partir do arquivo binário.

---

### Detalhes importantes sobre a classe `File`:

Quando se faz várias chamadas como `new File("meuarquivo.dat")` no código-fonte, não está criando o arquivo no disco, apenas instanciando um objeto Java que representa o caminho (meuarquivo.dat). Esse objeto não altera o arquivo físico.

1. `new File()` não cria o arquivo no disco. O exemplo abaixo só cria um objeto em memória que aponta para o caminho meuarquivo.dat:
```java
File arquivo = new File("meuarquivo.dat");
```
O arquivo físico só é criado se você chamar métodos como:
```java
arquivo.createNewFile(); // Cria o arquivo vazio (se não existir)
```
2. Várias instâncias `new File()` referem-se ao mesmo arquivo físico. Se você criar múltiplos objetos `File` com o mesmo caminho, todos representarão o mesmo arquivo no disco:
```java
File arquivo1 = new File("meuarquivo.dat");
File arquivo2 = new File("meuarquivo.dat");
```
- `arquivo1` e `arquivo2` são objetos diferentes na memória, mas ambos apontam para o mesmo arquivo físico.
- Qualquer operação (leitura/escrita) feita por um afetará o outro (já que o alvo é o mesmo).

3. Quando o arquivo físico é realmente criado/modificado?
- Criação:
```java
arquivo.createNewFile(); // Método explícito
new FileOutputStream("meuarquivo.dat"); // Cria/sobrescreve o arquivo
```
- Modificação:
Só ocorre quando você usa classes de I/O (`FileWriter`, `FileInputStream`, etc.).
- Exemplo:
```java
File arquivo1 = new File("dados.txt");
File arquivo2 = new File("dados.txt");

System.out.println(arquivo1 == arquivo2); // false (objetos diferentes na memória)
System.out.println(arquivo1.equals(arquivo2)); // true (mesmo caminho)

// O arquivo físico só é criado aqui:
arquivo1.createNewFile(); // Agora "dados.txt" existe no disco!

// Ambos os objetos continuam referindo-se ao mesmo arquivo:
System.out.println(arquivo2.exists()); // true (arquivo1 criou o arquivo físico)
```
#### Resumindo:
- A classe `File` é apenas uma representação abstrata do caminho no sistema de arquivos;
- `new File()` é apenas uma "referência" ao caminho do arquivo, sem impacto no disco;
- Várias instâncias com o mesmo caminho referem-se ao mesmo arquivo físico;
- O arquivo só é criado/modificado quando você usa operações de I/O explícitas.
	- Para garantir que o arquivo exista antes de usar, faça:
```java
File arquivo = new File("meuarquivo.dat");
if (!arquivo.exists()) {
    arquivo.createNewFile(); // Cria só se não existir
}
```
---
## JSON (JavaScript Object Notation)

JSON é um formato de troca de dados que é fácil para humanos lerem e escreverem, e fácil para máquinas interpretarem e gerarem. Ele é muito utilizado para transmitir dados entre um servidor e uma aplicação web, ou entre diferentes partes de um sistema. Possui uma estrutura hierárquica que permite organizar objetos e arrays aninhados.

### Sintaxe do JSON

O JSON é composto por:

#### Objetos
- Um objeto é uma coleção de pares chave-valor, onde cada chave é uma string e o valor pode ser uma string, número, booleano, array, outro objeto ou `null`.
- Os objetos são delimitados por chaves `{}`.
- Exemplo:
  ```json
  {
    "nome": "Maria",
    "idade": 22,
    "estudante": false,
    "endereco": {
      "rua": "Rua das Araras",
      "numero": 998,
      "cidade": "Hermoso do Sul"
    }
  }
  ```

#### Arrays
- Um array é uma lista ordenada de valores, que podem ser strings, números, booleanos, objetos, outros arrays ou `null`.
- Os arrays são delimitados por colchetes `[]`.
- Exemplo:
```json
[
  {"nome": "Maria", "idade": 22},
  {"nome": "João", "idade": 34},
  {"nome": "Ana", "idade": 27}
]
```
---

### 5.Trabalhando com JSON no Projeto SmartList

Para trabalhar com JSON neste projeto, será utilizada a biblioteca `Jackson`. Ela possui métodos para converter objetos Java para JSON (serialização) e JSON para objetos Java (desserialização).

#### Adicionando a Dependência do `Jackson`
Para usar a biblioteca `Jackson`, adicione a seguinte dependência no arquivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.18.2</version>
    </dependency>
</dependencies>
```

Após adicionar uma dependência no arquivo `pom.xml`, é necessário realizar algumas etapas para garantir que a dependência seja corretamente baixada e disponibilizada para uso no projeto:
- **Salvar o Arquivo `pom.xml`:** Após adicionar a dependência, salve o arquivo `pom.xml`. Isso garante que as alterações sejam registradas.
- **Atualizar o Projeto no Maven:** O Maven precisa atualizar o projeto para baixar a nova dependência e configurar o classpath corretamente. No IntelliJ IDEA, abra o arquivo `pom.xml` e pressione CTRL+SHIFT+O, ou clique na opção destacada na imagem abaixo:
  
   ![image](https://github.com/user-attachments/assets/3e573f87-60ec-4235-90be-6640ac91337e)


### Configurando a Classe Produto

Para que o Jackson consiga realizar a desserialização (converter JSON para um objeto Java), a classe Produto deve possuir um construtor padrão (sem argumentos). Esse construtor é essencial, pois permite que o Jackson crie uma instância da classe e preencha seus campos utilizando os métodos setters ou acessando diretamente os atributos — mesmo que sejam privados — por meio de reflection.

No caso da serialização JSON, a biblioteca Jackson não exige que a classe implemente a interface Serializable. Portanto, adicionar Serializable na classe Produto é redundante e desnecessário para o funcionamento do Jackson. No entanto, neste projeto optamos por manter a implementação de Serializable, pois também utilizamos serialização binária (gravação em arquivos binários), a qual depende dessa interface. Removê-la comprometeria essa funcionalidade.

#### Requisitos para uso do Jackson com JSON

A serialização e desserialização com Jackson requerem apenas:
- Um construtor padrão (sem parâmetros);
- Getters públicos, usados durante a serialização (objeto Java → JSON);
- Setters públicos, usados durante a desserialização (JSON → objeto Java).

#### Uso de Reflection pelo Jackson
Quando dizemos que o Jackson utiliza reflection para serializar e desserializar objetos JSON, estamos nos referindo à capacidade da biblioteca de acessar dinamicamente os atributos e métodos de uma classe Java, sem precisar conhecer sua estrutura em tempo de compilação. Reflection é um recurso do Java que permite:
- Inspecionar classes, métodos, campos e construtores em tempo de execução;
- Modificar ou invocar comportamentos, mesmo que sejam privados (com as devidas permissões);
- Criar objetos dinamicamente.

#### Exemplo de como o Jackson utiliza Reflection: 
```java
ObjectMapper mapper = new ObjectMapper();
String json = mapper.writeValueAsString(pessoa);  // Serializa para JSON
Pessoa pessoa = mapper.readValue(json, Pessoa.class);  // Desserializa de JSON
```
#### Durante a serialização (Java → JSON):
- Analisa a classe Pessoa usando reflection;
- Lista os métodos getters (ex: getNome(), getIdade());
- Invoca os getters para obter os valores dos atributos;
- Mapeia os nomes dos métodos para chaves JSON (ex: getNome() → "nome").

#### Durante a desserialização (JSON → Java):
- Analisa a classe Pessoa usando reflection;
- Procura por setters ou campos correspondentes às chaves do JSON;
- Cria uma nova instância da classe (usando o construtor padrão);
- Define os valores nos atributos usando os setters ou acesso direto aos campos.

Dessa forma, o Jackson consegue manipular qualquer classe Java de forma automática, sem a necessidade de código gerado manualmente.

#### Classe Produto

```java
public class Produto implements Serializable {
    private String nome;
    private int quantidade;
    private double preco;

    // Construtor padrão (sem argumentos)
    public Produto() {
    }

    // Construtor com argumentos
    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Getters e Setters (omitidos)
}
```

#### Classe ListaDeCompras

```java
public class ListaDeCompras {
    private List<Produto> produtos;

    //código omitido

    public void salvarEmArquivoJson(String nomeArquivo) {
        if(!produtos.isEmpty()){
            try  {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formata o JSON para ser legível
                objectMapper.writeValue(new File(nomeArquivo), produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }

    }

    public void carregarDeArquivoJson(String nomeArquivo)  {
        produtos.clear();
        try  {
            ObjectMapper objectMapper = new ObjectMapper();
            //getTypeFactory(): acessa o TypeFactory, que é responsável por construir tipos genéricos e complexos que Jackson não consegue inferir automaticamente (como listas, mapas...)
	    //constructCollectionType(): cria um tipo genérico que representa uma coleção (List) de elementos do tipo Produto.
            produtos = objectMapper.readValue(new File(nomeArquivo), objectMapper.getTypeFactory().constructCollectionType(List.class, Produto.class));
        } catch (IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
        }
    }
    //Código omitido
}
```

#### Classe ListaDeCompraView
```java
public class ListaDeComprasView {
    private Scanner scanner;

    public ListaDeComprasView() {
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n--- Gerenciador de Lista de Compras ---");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Remover Produto");
        System.out.println("3. Imprimir Lista");
        System.out.println("4. Salvar Lista em Arquivo de Texto");
        System.out.println("5. Carregar Lista de Arquivo de Texto");
        System.out.println("6. Salvar Lista em Arquivo Binário");
        System.out.println("7. Carregar Lista de Arquivo Binário");
        System.out.println("8. Salvar Lista em Arquivo JSON");
        System.out.println("9. Carregar Lista de Arquivo JSON");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    //Código omitido
}
```

#### Classe ListaDeComprasController

```java
public class ListaDeComprasController {
    //Código omitido

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarProduto();
                break;
            case 2:
                removerProduto();
                break;
            case 3:
                exibirLista();
                break;
            case 4:
                salvarEmAqrTexto();
                break;
            case 5:
                carregarDeArqTexto();
                break;
            case 6:
                salvarEmArquivoBinario();
                break;
            case 7:
                carregarDeArquivoBinario();
                break;
            case 8:
                salvarEmArquivoJson();
                break;
            case 9:
                carregarDeArquivoJson();
                break;
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    //Código omitido

    private void salvarEmArquivoJson(){
        model.salvarEmArquivoJson("lista_compras.json");
    }

    private void carregarDeArquivoJson(){
        model.carregarDeArquivoJson("lista_compras.json");
    }
}
```

#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, salvar em arquivo json, verificar se o arquivo foi criado, abrir o arquivo com o bloco de notas para ver o conteúdo (que deve ser semelhante ao json abaixo), por fim, carregar a lista a partir do arquivo json.

```json
[
  {
    "nome": "Arroz",
    "quantidade": 2,
    "preco": 10.5
  },
  {
    "nome": "Feijão",
    "quantidade": 3,
    "preco": 8.0
  }
]
```
--- 
# Manipulando Coleções
Na Programação Orientada a Objetos (POO), raramente trabalhamos com um único objeto isolado. Na prática, lidamos com **conjuntos de objetos**, como listas de alunos, produtos ou usuários.

Arrays podem ser usados, mas possuem limitação: são **estruturas estáticas** (tamanho fixo). O **Collections Framework** resolve esse problema oferecendo estruturas dinâmicas e mais flexíveis.

**Visão Geral do Collections Framework**

O Collections Framework é composto por interfaces e classes que permitem manipular grupos de objetos de forma eficiente. A Figura a seguir mostra as principais interfaces e implementações do Collection Framework:
<img width="720" height="505" alt="image" src="https://github.com/user-attachments/assets/7ed138aa-3393-427e-8979-5d242682b656" />

Fonte: https://medium.com/@gui.dani13/cole%C3%A7%C3%B5es-em-java-ba39c5bcea4a, visitado em 25/03/2026

Conforme a Figura, no topo, está a interface `Iterable` e, logo abaixo, a interface `Collection`. As três estruturas principais usadas no dia a dia são: 
- List → Ordenada, permite duplicados
- Set → Não permite duplicados
- Map → Estrutura chave-valor

## List (Listas)
- Mantém ordem de inserção
- Permite elementos duplicados
- Permite acesso por índice

**Sintaxe:**  
```java 
List<String> lista = new ArrayList<>();
```

**Principais operações:**
- `add()` → adicionar
- `get()` → acessar
- `set()` → alterar
- `remove()` → remover
- `size()` → tamanho
  
  Outras operações:
- `indexOf(Object o)`: retorna o índice da primeira ocorrência
- `lastIndexOf(Object o)`: retorna o índice da última ocorrência
- `contains(Object o)`: verifica se a lista contém um elemento, retornando true ou false
- `set(int index, E element)`: substitui o elemento no índice especificado
- `clear()`: remove todos os elementos da lista
- `isEmpty()`: verifica se a lista está vazia

**Exemplo:**
```java
public class ExemploArrayList {
    public static void main(String[] args) {
        ArrayList<String> frutas = new ArrayList<>();

        // Adicionar
        frutas.add("Maçã");
        frutas.add("Banana");
        frutas.add("Maçã"); // Permite duplicado
        frutas.add(0, "Laranja"); // Insere no início

        // Acessar
        System.out.println(frutas.get(1)); // Saída: Banana

        // Modificar
        frutas.set(2, "Uva");

        // Remover
        frutas.remove("Maçã");

        // Tamanho
        System.out.println(frutas.size()); // Saída: 3
        
        //A lista
        System.out.println(frutas);
    }
}
```
## Set (Conjuntos)
Baseado no conceito matemático de conjuntos. 
- Não permite elementos duplicados
- Não garante ordem (`HashSet`)

**Implementações:**
- `HashSet` → mais rápido (sem ordem)
- `TreeSet` → ordenado

**Principais operações:**
- `add(E e)`: Adiciona o elemento especificado se ele ainda não estiver presente. Retorna `true` se adicionado, `false` se já existir.
- `remove(Object o)`: Remove o elemento especificado, se presente.
- `contains(Object o)`: Retorna `true` se o conjunto contiver o elemento especificado.
- `size()`: Retorna o número de elementos no conjunto.
- `clear()`: Remove todos os elementos do conjunto.
- `isEmpty()`: Verifica se o conjunto está vazio (retorna `true` ou `false`).
- `iterator()`: Retorna um `iterador` sobre os elementos, sem ordem específica.

**Exemplo:**
```java
package br.com;

import java.util.HashSet;

public class ExemploHashSet {
    public static void main(String[] args) {
        HashSet<Integer> numeros = new HashSet<>();

        // 1. add()
        numeros.add(10);
        numeros.add(20);
        numeros.add(10); // Ignorado (duplicado)
        System.out.println("Conjunto: "+numeros);

        // 2. contains()
        if (numeros .contains(20)) {
            System.out.println("Está no conjunto.");
        }

        // 3. size()
        System.out.println("Tamanho: " + numeros.size()); // Saída: 2

        // 4. remove()
        numeros.remove(10);

        // 5. clear()
        numeros.clear();
        System.out.println("Está vazio? " + numeros.isEmpty()); // Saída: true
    }
}
```

## Map (Mapas/Dicionários)
O Map não herda de Collection, mas faz parte do framework. Ele trabalha com o conceito de Chave e Valor. Pense em um CPF (chave) vinculado a uma Pessoa (valor). As chaves são únicas. 
- Estrutura de chave e valor
- Chaves são únicas

**Sintaxe:**
  
```java 
Map<String, String> agenda = new HashMap<>();
```

**Principais operações:**
- `put(K chave, V valor)`: Associa o valor especificado à chave especificada.
- `get(Object chave)`: Retorna o valor associado à chave, ou `null` se não houver mapeamento.
- `remove(Object chave)`: Remove o mapeamento para a chave especificada, se existir.
- `containsKey(Object chave)`: Retorna `true` se o mapa contiver um mapeamento para a chave.
- `containsValue(Object valor)`: Retorna `true` se o mapa contiver um ou mais mapeamentos para o valor especificado.
- `size()`: Retorna o número de pares chave-valor no mapa.
- `isEmpty()`: Retorna `true` se o mapa não contiver pares chave-valor.
- `clear()`: Remove todos os mapeamentos do mapa.
- `keySet()`: Retorna um `Set` contendo todas as chaves do mapa.
- `values()`: Retorna uma `Collection` contendo todos os valores do mapa.


***Os métodos **equals() e hashCode()** são fundamentais para o funcionamento de `HashSet` e `HashMap`. Eles determinam quando dois objetos são considerados iguais.***


**Exmplo**:
```java
import java.util.HashMap;

public class ExemploHashMap {
    public static void main(String[] args) {
        // Criando HashMap
        HashMap<String, Double> notas = new HashMap<>();

        // put() - Adicionando dados
        notas.put("Alicia", 7.5);
        notas.put("Aimê", 8.0);

        // get() - Recuperando dados
        System.out.println("Nota de Alicia: " + notas.get("Alicia"));

        // containsKey() - Verificando
        if (notas.containsKey("Aimê")) {
            System.out.println("Aimê está no mapa.");
        }

        // size() - Tamanho
        System.out.println("Tamanho: " + notas.size());
    }
}
```

#### Comparação Geral
| Estrutura	| Ordem	| Duplicados  |  
|-----------|-------|-------------|
| List	    | Sim	| Sim	      |
| Set	    | Não	| Não	      |  
| Map	    | Não	| Chave única | 


**Quando usar cada um?**
- Histórico ordenado → List
- Evitar duplicados → Set
- Associação chave-valor → Map

**Observações:**
- Sempre declare a variável pela interface e instancie pela classe concreta. Isso permite que, se amanhã você descobrir que uma `LinkedList` é melhor para sua performance, você só mude a instância, sem quebrar o resto do código que espera uma `List`. 
```java
// Jeito certo: Programando para a interface 
List<String> nomes = new ArrayList<>();
```
- Perceba o uso de < > (diamante). Isso é o `Generics`. Ele garante que sua lista aceite apenas um tipo específico de objeto, evitando erro de `ClassCastException` em tempo de execução. Por exemplo: Imagine um sistema de biblioteca. Você usaria um `Set<Livro>` para garantir que nenhum livro seja cadastrado duas vezes com o mesmo ID, e uma `List<Emprestimo>` para manter o histórico na ordem em que ocorreram.


# API Stream

A **API Stream** (Java 8+) não armazena dados. Ela é um **fluxo de dados** que permite filtrar, transformar e agrupar coleções de forma declarativa. Imagine que a **Coleção** é um balde cheio de frutas e a **Stream** é uma esteira rolante que processa essas frutas uma a uma. As principais diferenças entre `Collections` e `Streams` são:
- **Collections (Dados)** focam em **como os dados são armazenados**. É uma estrutura de dados em memória que contém todos os elementos. O foco é o acesso e a gestão (adicionar, remover, buscar).
- **Streams (Fluxo/Processamento)** focam em **como os dados são transformados**. Uma Stream não armazena nada! Ela apenas "cursa" os dados de uma fonte (como uma Lista) para realizar operações nelas.

## Pipeline de uma Stream:
- Fonte → `collection.stream()`, transforma lista em stream
- Operações intermediárias → `filter`, `map`, `sorted`, composição de funções que transformam a stream
- Operação terminal → `collect`, `forEach`, `reduce`, coleta os resultados das oprações anteriores e transforma a stream de volta em uma lista
	
**Operações principais:** 
- `filter()` → filtrar
- `map()` → transformar
- `sorted()` → ordenar
- `collect()` → converter
- `reduce()` → reduzir
- `forEach()` → executa uma ação em cada elemento

Para entender na prática os benefícios da API Stream, vamos analisar os exemplos abaixo:

**1. Exemplo sem Stream (modo imperativo com loops e ifs):**
```java
class Aluno {
    String nome;
    double nota;

    public Aluno(String nome, double nota) {
        this.nome = nome;
        this.nota = nota;
    }

    public String getNome() {
        return nome;
    }

    public double getNota() {
        return nota;
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SistemaEscolar {
    public static void main(String[] args) {
        // 1. Criando a Coleção
        List<Aluno> listaAlunos = new ArrayList<>();
        listaAlunos.add(new Aluno("Marinana", 5.5));
        listaAlunos.add(new Aluno("Julia", 9.0));
        listaAlunos.add(new Aluno("Aimê", 8.5));
        listaAlunos.add(new Aluno("Paolo", 4.0));

        // 2. Filtragem e Transformação (Map)
        List<String> nomesAprovados = new ArrayList<>();

        for (Aluno aluno : listaAlunos) {
            if (aluno.getNota() >= 7.0) { // O equivalente ao .filter()
                nomesAprovados.add(aluno.getNome()); // O equivalente ao .map()
            }
        }

        // 3. Ordenação (O equivalente ao .sorted())
        // Como agora temos uma lista de Strings, usamos o Collections.sort()
        Collections.sort(nomesAprovados);

        System.out.println("Alunos Aprovados: " + nomesAprovados);
    }
}
```

**2. Exemplo com Stream (modo declarativo):**
```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaEscolarComStream{
    public static void main(String[] args) {
        // 1. Criando a Coleção
        List<Aluno> listaAlunos = new ArrayList<>();
        listaAlunos.add(new Aluno("Marinana", 5.5));
        listaAlunos.add(new Aluno("Julia", 9.0));
        listaAlunos.add(new Aluno("Aimê", 8.5));
        listaAlunos.add(new Aluno("Paolo", 4.0));

        // 2. Usando Stream para filtrar Aprovados (Nota >= 7) e pegar apenas nomes
        List<String> nomesAprovados = listaAlunos.stream()
                .filter(a -> a.getNota() >= 7.0) // Filtro
                .sorted(Comparator.comparing(a -> a.getNome())) // Ordenação por Nome
                .map(a -> a.getNome()) // Transformação: Aluno -> String
                .collect(Collectors.toList()); // Finalização

        System.out.println("Alunos Aprovados: " + nomesAprovados);
    }
}
```

**O que mudou de um exemplo para o outro?**
- No exemplo 1, tivemos que criar manualmente a lista `nomesAprovados`. No exemplo 2, o `collect(Collectors.toList())` faz isso para nós.
- No exemplo 1, foi usado um laço `for-each` para percorrer os dados. No exemplo 2, a iteração é interna (o Java cuida de como percorrer).
- No exemplo 1, a filtragem e a transformação ficaram "misturadas" dentro do `if`. No exemplo 2, cada etapa é um método isolado (`filter`, `map`), o que facilita a leitura.
- No exemplo 1, a ordenação é feita após a lista estar pronta, usando `Collections.sort()`. No exemplo 2, a ordenação é feita pela operação intermediária `sorted()`.

### Características importantes:
- Streams não alteram a coleção original. Se você filtrar uma `List` usando Stream, a lista original continua intacta. A Stream gera um novo resultado.
- São consumidas apenas uma vez. Você pode percorrer uma `List` 10 vezes. Uma Stream, depois que você chega ao fim dela (operador terminal), ela "morre". Se precisar de novo, tem que abrir outra `.stream()`.
- Possuem execução preguiçosa (`lazy evaluation`). As Streams só trabalham quando você pede o resultado final. Se você só colocar o `.filter()`, o Java não faz nada até você chamar uma operação terminal, por exemplo, `.toList()` ou `.count()`. Isso economiza processamento.

**Exemplo usando List e Set:**
```java
public class Pessoa {
    private String nome;
    private int idade;
    private double salario;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public Pessoa(String nome, int idade, double salario) {
        this.nome = nome;
        this.idade = idade;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(this.nome)
                .append(" Idade: ").append(getIdade())
                .append(" Salário: ").append(getSalario());
        return sb.toString();
    }
}

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListSetStream {

    public static void main(String[] args) {
        List<Pessoa> pessoas = Arrays.asList(
                new Pessoa("Tatiane", 23, 4500.00),
                new Pessoa("Vitor", 19, 1850.00),
                new Pessoa("Carlos", 18, 1800.00),
                new Pessoa("Camila", 22, 5200.00),
                new Pessoa("Marcela", 22, 15500.00),
                new Pessoa("Pedro", 22, 15500.00));

        //Listar o nome das pessoas
        List<String> nomes = pessoas.stream()
                .map(pessoa -> pessoa.getNome())
                .collect(Collectors.toList());
        System.out.println("Nomes das pessoas: "+nomes);
        //Ou, desta forma

        System.out.println("Nomes das pessoas: ");
        pessoas.stream().map(pessoa -> pessoa.getNome()).forEach(nome -> System.out.println(nome));


        //Listar o nome das pessoas em ordem alfabética
        List<String> nomesOrdenados = pessoas.stream()
                .map(pessoa -> pessoa.getNome())
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Nomes das pessoas em ordem: "+nomesOrdenados);

        //Listar todas as pessoas com mais de 20 anos
        List<Pessoa> pessoasMaisDe20 = pessoas.stream()
                .filter(p -> p.getIdade() > 20)
                .collect(Collectors.toList());
        System.out.println("Pessoas com mais de 20 anos: "+pessoasMaisDe20);

        //Listar os salarios
        List<Double> salarios = pessoas.stream()
                .map(pessoa -> pessoa.getSalario())
                .collect(Collectors.toList());
        System.out.println("Salarios: "+salarios);

        //Listar os salarios sem repetir
        Set<Double> salariosDistintos = pessoas.stream()
                .map(pessoa -> pessoa.getSalario())
                .collect(Collectors.toSet());

        //Ou desta forma
        /*
        List<Double> salariosDistintos = pessoas.stream()
                .map(pessoa -> pessoa.getSalario())
                .distinct()
                .collect(Collectors.toList());
        */
        System.out.println("Salarios sem repeticao: "+salariosDistintos);

        //Retornar a quantidade de pessoas com salarios acima de 5.000,00
        long pessoasComSalarioAlto = pessoas.stream()
                .filter(p -> p.getSalario() > 5000)
                .count();
        System.out.println("Quantidade de pessoas com salario acima de 5mil: "+pessoasComSalarioAlto);

        //Retornar a soma dos salarios
        double somaSalarios = pessoas.stream()
                .map(pessoa -> pessoa.getSalario())
                .reduce(0.0, (s1, s2) -> s1 + s2);//Equivalente .reduce(0.0, Integer::sum)
        //Ou desta forma,
        /*
        double somaSalarios = pessoas.stream()
                .mapToDouble(pessoa -> pessoa.getSalario())
                .sum();
        */
        System.out.println("Soma dos salarios: "+somaSalarios);

        //Retornar a média salarial
        double mediaSalarial = pessoas.stream()
                .mapToDouble(pessoa-> pessoa.getSalario())
                .average()
                .orElse(0.0);//se não encontrar nada, retornar 0
        System.out.println("Média dos salarios: "+mediaSalarial);

        /*Optional permite lidar com valores que podem ser nulos de forma mais segura e explícita.
          Força o programador a pensar na possibilidade de um valor nulo, evitando os erros
          NullPointerException. Esta classe possui dois estados: vazio (representado por Optional.empty() )
          ou contendo um valor não nulo*/

        //Retornar o maior salario
        double maiorSalario = pessoas.stream()
                .mapToDouble(pessoa-> pessoa.getSalario())
                .max()
                .orElse(0.0); //fornecer um valor padrão caso o resultado seja vazio ou null
        System.out.println("Maior salário: "+maiorSalario);

        //Retornar o menor salario
        double menorSalario = pessoas.stream()
                .mapToDouble(pessoa-> pessoa.getSalario())
                .min()
                .orElse(0.0); //fornecer um valor padrão caso o resultado seja vazio ou null
        System.out.println("Menor salário: "+menorSalario);
    }
}
```

**Exemplo usando Map:**

Imagine que temos um `Map` onde a Chave é o CPF (String) e o Valor é um objeto Usuario. Nosso objetivo é extrair apenas os nomes de todos os usuários que estão ativos e colocar em uma lista ordenada.

```java
public class Usuario {
    private String nome;
    private boolean ativo;

    public Usuario(String nome, boolean ativo){
        this.nome = nome;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }
}

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapStream {
    public static void main(String[] args) {

        // Criando o Map de dados
        Map<String, Usuario> mapaUsuarios = new HashMap<>();
        mapaUsuarios.put("123", new Usuario("Celso", true));
        mapaUsuarios.put("456", new Usuario("Alícia", true));
        mapaUsuarios.put("789", new Usuario("Aimê", false)); // Inativo

        //Para navegar em um Map usando Streams, geralmente focamos nos valores (values())
        List<String> nomesAtivos = mapaUsuarios.values().stream() // 1. Pega só os objetos Usuario
                .filter(usuario -> usuario.isAtivo())             // 2. Filtra apenas os ativos
                .map(usuario -> usuario.getNome())                // 3. Extrai apenas o Nome (String)
                .sorted()                                         // 4. Coloca em ordem alfabética
                .collect(Collectors.toList());                    // 5. Converte o fluxo em uma List

        System.out.println(nomesAtivos); // Saída: [Alícia, Celso]
    }
}
```
**O que aconteceu nos bastidores?**
- `values().stream()`: Ignoramos as chaves (CPFs) e colocamos os objetos Usuario na "esteira".
- `filter`: O objeto "Aimê" foi retirado da esteira porque ele não era ativo.
- `map`: Aqui ocorre a transformação. A esteira antes levava objetos complexos (Usuario), agora leva apenas Strings (nomes).
- `collect`: É o balde no final da esteira que junta tudo o que sobrou e transforma de volta em uma Coleção.

Perceba que não foi preciso criar uma lista vazia, fazer um `for`, dar um `if`, adicionar manualmente e depois ordenar. Você apenas declarou o que queria que acontecesse. Isso torna o código muito mais fácil de ler e manter. 

## Conceitos importantes:
- **Programação Declarativa**: Em vez de escrever loops explícitos,você descreve o que quer, não como fazer.
- **Imutabilidade**: As operações em Streams geralmente retornam novas coleções, em vez de modificar a original. Isso ajuda a evitar efeitos colaterais e torna o código mais seguro e previsível. 
- **Optional**: Evita erros com valores nulos.
- **Funções de alta ordem:** Streams utilizam funções de alta ordem (como `map`, `filter`, `reduce`) para transformar e manipular dados. Essas funções aceitam outras funções como parâmetro, permitindo uma grande flexibilidade na criação de pipelines de processamento. Mais informações sobre programação funcional [aqui](https://www.alura.com.br/artigos/programacao-funcional-o-que-e?srsltid=AfmBOopwAbT_H2BnFmwFSfLpw2yFoLjEJJCYEz6BkjQqgIrVsgeBumqD) e [aqui](https://medium.com/@marcelomg21/programação-funcional-teoria-e-conceitos-975375cfb010). 



#### Erros comuns (anti-padrões):
    • Usar List quando precisa evitar duplicados.
    • Ignorar Streams e usar loops desnecessários.
    • Usar Map sem necessidade de chave.

Aplicando os conceitos de Stream para manipulações coleções no desenvolvimento das funcionalidades: 10. Filtrar Produtos por Quantidade Mínima, 12.Calcular Valor Total da Lista e 
13.Imprimir Lista em Ordem Alfabética:

#### Classe ListaDeCompras

```java
public class ListaDeCompras {
    private List<Produto> produtos;

    //Código omitido

    // Filtra produtos com quantidade mínima usando streams
    public List<Produto> filtrarPorQuantidadeMinima(int quantidadeMinima) {
        return produtos.stream()
                .filter(p -> p.getQuantidade() >= quantidadeMinima)
                .collect(Collectors.toList());
    }

    // Calcula o valor total da lista usando streams
    public double calcularValorTotal() {
        return produtos.stream()
                .mapToDouble(p -> p.getQuantidade() * p.getPreco())
                .sum();
    }

    // Imprime a lista de produtos em ordem alfabética
    public void imprimirLista() {
        produtos.stream()
                .sorted(Comparator.comparing(p -> p.getNome().toLowerCase())) // Ordena por nome
                .forEach(nome -> System.out.println(nome));
    }


    //código omitido
}
```

#### Classe ListaDeComprasView
```java
public class ListaDeComprasView {
    private Scanner scanner;

    public ListaDeComprasView() {
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n--- Gerenciador de Lista de Compras ---");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Remover Produto");
        System.out.println("3. Imprimir Lista");
        System.out.println("4. Salvar Lista em Arquivo de Texto");
        System.out.println("5. Carregar Lista de Arquivo de Texto");
        System.out.println("6. Salvar Lista em Arquivo Binário");
        System.out.println("7. Carregar Lista de Arquivo Binário");
        System.out.println("8. Salvar Lista em Arquivo JSON");
        System.out.println("9. Carregar Lista de Arquivo JSON");
        System.out.println("10. Filtrar Produtos por Quantidade Mínima ");
        System.out.println("11. Calcular Valor Total da Lista");
        System.out.println("12. Imprimir Lista em Ordem Alfabética");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    //Código omitido

    public int lerQuantidadeMinima() {
        int quantidade = 0;
        boolean quantidadeValida = false;
        while (!quantidadeValida) {
            try {
                System.out.print("Quantidade mínima: ");
                quantidade = scanner.nextInt();
                scanner.nextLine();
                quantidadeValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Quantidade mínima deve ser um número inteiro. Tente novamente.");
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        }
        return quantidade;
    }
}
```

#### Classe ListaDeComprasController

```java
public class ListaDeComprasController {
    // Código omitido

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarProduto();
                break;
            case 2:
                removerProduto();
                break;
            case 3:
                exibirLista();
                break;
            case 4:
                salvarEmAqrTexto();
                break;
            case 5:
                carregarDeArqTexto();
                break;
            case 6:
                salvarEmArquivoBinario();
                break;
            case 7:
                carregarDeArquivoBinario();
                break;
            case 8:
                salvarEmArquivoJson();
                break;
            case 9:
                carregarDeArquivoJson();
                break;
            case 10:
                filtrarPorQuantidadeMinima();
                break;
            case 11:
                calcularValorTotal();
                break;
            case 12:
                imprimirLista();
                break;
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    //Código omitido

    private void filtrarPorQuantidadeMinima(){
        int quantidadeMinima = view.lerQuantidadeMinima();
        System.out.println(model.filtrarPorQuantidadeMinima(quantidadeMinima).toString());
    }

    private void calcularValorTotal(){
        System.out.println("Valor Total R$ "+model.calcularValorTotal());
    }

    private void imprimirLista(){
        model.imprimirLista();
        System.out.println("Valor Total R$ "+model.calcularValorTotal());
    }

}
```

#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, acessar a funcionalidade 10, informar uma quantidade mínima e verificar o resultado; acessar as funcionalidades 11 e 12 e verificar os resultados.

## Melhorando/Refatorando o Código

É possível melhorar o códogo da SmartList construído até o momento. Para isso, podemos aplicar Padrões de Projetos.

Os **Padrões de Projeto** (*Design Patterns*) são soluções reutilizáveis para problemas comuns em projeto de software. Cada padrão é um modelo conceitual ou um guia que descreve como resolver um problema de estrutura ou comportamento de uma forma que já foi testada e validada por outros desenvolvedores ao longo de décadas.

#### Por que usar padrões de projeto?
- **Linguagem Comum:** Desenvolvedores podem dizer "vamos usar um Singleton aqui" e todos entendem a estrutura de classes necessária sem longas explicações.
- **Melhores Práticas:** Eles aplicam princípios sólidos de POO, como o encapsulamento e o baixo acoplamento.
- **Redução de Erros:** Como são soluções testadas e validadas, evitam que você "reinvente a roda" com uma solução que pode ter falhas ocultas.

#### Classificações dos padrões:
Eles podem ser classificados por seu propósito, ou intenção. Os três grupos principais de padrões (clássicos) são:
- **Padrões criacionais:** fornecem mecanismos de criação de objetos que aumentam a flexibilidade e a reutilização de código. Exemplos: `Factory Method`, `Abstract Factory`, `Builder`, `Prototype` e `Singleton`.
- **Padrões estruturais:** explicam como montar objetos e classes em estruturas maiores, enquanto ainda mantém as estruturas flexíveis e eficientes. Exemplos: `Adapter`, `Bridge`, `Composite`, `Decorator`, `Facede`, `Flyweight` e `Proxy`.
- **Padrões comportamentais:** cuidam da comunicação eficiente e da assinalação de responsabilidades entre objetos. Exemplos: `Chain of Responsability`, `Command`, `Iterator`, `Mediator`, `Memento`, `Observer`, `State`, `Strategy`, `Template Method` e `Visitor`.

#### Origem

O conceito foi popularizado pelo livro `Design Patterns: Elements of Reusable Object-Oriented Software` de 1994, escrito por Erich Gamma, Richard Helm, Ralph Johnson e John Vlissides — conhecidos como a *Gang of Four (GoF)*. Até hoje, esse livro é a principal referência para o estudo do tema.

#### Sobre o uso

Embora os padrões de projetos sejam referências, com a evolução das linguagens de programação, muitos deles cairam em desuso ou foram absorvidos pelas próprias linguagens de programação. Porém, alguns continuam essenciais e são usados por frameworks modernos como `Spring`, `React` e `Angular`. São eles:
- `Strategy`: Talvez o mais usado hoje. Usado para trocar algoritmos em tempo de execução e evitar blocos gigantes de `if/else`.
- `Observer`: É a base da programação reativa e de quase todos os sistemas de eventos e interfaces gráficas modernas.
- `Factory` (`Method` e `Abstract`): usado para manter o código desacoplado, especialmente em sistemas que utilizam `Injeção de Dependência`.
- `Adapter`: Fundamental para integrar sistemas novos com legados ou bibliotecas de terceiros sem alterar o código original.
- `Builder`: Muito popular em linguagens como Java para criar objetos complexos de forma legível.

Outro padrão de projeto que continua em uso é o `Command`. Ele transforma um pedido (comando) em um objeto independente que contém toda a informação sobre o pedido. Em resumo, o padrão `Command` diz: *Não chame a função diretamente. Transforme o pedido em um objeto e entregue para alguém que saiba quando e como executá-lo*. A implementação de *Undo/Redo* (Desfazer/Refazer) é o exemplo clássico do `Command`.

O padrão de projeto `Singleton` não está em desuso, mas ele não é aceito por todos os desenvolvedores. O motivo seria porque ele dificulta testes unitários e cria um estado global que pode esconder dependências. Apesar disso, neste projeto iremos usá-lo para fins didáticos, juntamente com os padrões `Strategy` e  `Command`.

Mais informações sobre padrões de projetos podem ser acessadas no site **Refactoring.Guru.** (https://refactoring.guru/pt-br/design-patterns).
É importante destacar que novos padrões estão surgindo. Um deles é o `Repository` para abstrair o acesso a dados, ele será usado na disciplina de Programação para Web.

### Implementando as melhorias:

A primeira melhoria a ser implementada no código será a aplicação do padrão de projeto `Singleton`, para garantir que apenas uma instância da lista de compras seja criada durante a execução do programa. O primeiro passo é modificar o contrutor padrão para privado, para prevenir que outros objetos chamem o `new` da classe `Singleton`. O segundo passo é criar um método estático que atuará como um construtor, por exemplo, `getInstancia()`. Esse método chama o construtor privado para criar um objeto e o salva em um campo estático, por exemplo, o campo `instância`. Todas as chamadas seguintes para esse método retornam o objeto salvo em `instancia`.

Recomenda-se o Padrão `Singleton` quando uma classe deve ter apenas uma instância disponível para todos os seus clientes. Exemplo: objeto de base de dados único compartilhado por diferentes partes de um programa. 

A seguir, as modificações a serem feitas:

#### Classe ListaDeCompras
```java
public class ListaDeCompras {
    private static ListaDeCompras instancia;
    private List<Produto> produtos;

    private ListaDeCompras() {
        produtos = new ArrayList<>();
    }

    public static ListaDeCompras getInstancia() {
        if (instancia == null) {
            instancia = new ListaDeCompras();
        }
        return instancia;
    }
    //Código omitido
}
```

#### Classe Main
```java
public class Main {
    public static void main(String[] args) {
        //ListaDeCompras model = new ListaDeCompras();
        ListaDeCompras model = ListaDeCompras.getInstancia();
        
        //Código omitido
    }
}
```

Outra melhoria possível é permitir que o usuário escolha dinamicamente o método de persistência (texto, binário, JSON ), para isso, pode-se usar o padrão `Strategy` para encapsular cada método em uma classe separada. 

Recomenda-se o Padrão `Strategy` quando se quer usar diferentes variantes de um algoritmo dentro de um objeto e poder trocar de um algoritmo para outro durante a execução.

A seguir, criando a interface `PersistenciaStrategy`:

#### Interface PersistenciaStrategy

```java
package br.com.model;

import java.util.List;

public interface PersistenciaStrategy {
    void salvar(List<Produto> produtos, String caminhoArquivo);
    List<Produto> carregar(String caminhoArquivo);
}
```

Na sequência, as classes que implementam a interface:

#### Classe PersistenciaTexto

```java
public class PersistenciaTexto implements PersistenciaStrategy{
    @Override
    public void salvar(List<Produto> produtos, String nomeArquivo) {

        if(produtos != null && !produtos.isEmpty()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, false))) {
                for (Produto produto : produtos) {
                    writer.write(produto.getNome() + " - " + produto.getQuantidade() + " - "+produto.getPreco());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }

    @Override
    public List<Produto> carregar(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            List<Produto> produtos = new ArrayList<>();
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                produtos.add(new Produto(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2])));
            }
            System.out.println("Lista do Arquivo de Texto");
            System.out.println(produtos);
            return produtos;
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: "+e.getMessage());
            return null;
        }
    }

}
```

#### Classe PersistenciaBinario

```java
public class PersistenciaBinario implements PersistenciaStrategy{

    @Override
    public void salvar(List<Produto> produtos, String nomeArquivo) {
        if(!produtos.isEmpty()){
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
                oos.writeObject(produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }

    @Override @SuppressWarnings("unchecked") // Suprime avisos de operações não verificadas, esta anotação é usada para silenciar aviso do compilador.
    public List<Produto> carregar(String nomeArquivo)  {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            List<Produto> produtos = new ArrayList<>();
            produtos = (List<Produto>) ois.readObject();
            return  produtos;
        } catch (ClassNotFoundException | IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            return null;
        }

    }

}
```

#### Classe PersistenciaJson

```java
public class PersistenciaJson implements PersistenciaStrategy{
    @Override
    public void salvar(List<Produto> produtos, String nomeArquivo)  {
        if(!produtos.isEmpty()){
            try  {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formata o JSON para ser legível
                objectMapper.writeValue(new File(nomeArquivo), produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }

    @Override
    public List<Produto> carregar(String nomeArquivo)  {
        try  {
            List<Produto> produtos = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            produtos = objectMapper.readValue(new File(nomeArquivo), objectMapper.getTypeFactory().constructCollectionType(List.class, Produto.class));
            return produtos;
        } catch (IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            return null;
        }

    }
}
```

Com as novas classes, adequar as classes `ListaDeCompras` e `ListaDeComprasController`.

#### Classe ListaDeCompras

```java
public class ListaDeCompras {
    private static ListaDeCompras instancia;
    private List<Produto> produtos;
    private PersistenciaStrategy estrategiaPersistencia;

    // Código omitido


    public void setEstrategiaPersistencia(PersistenciaStrategy estrategia) {
        this.estrategiaPersistencia = estrategia;
    }

    public void salvar(String nomeArquivo)  {
        estrategiaPersistencia.salvar(produtos, nomeArquivo);
    }

    public void carregar(String nomeArquivo) {
        if(!Files.exists(Paths.get(nomeArquivo)))
            System.out.println("Arquivo não encontrado!");
        else
            produtos = estrategiaPersistencia.carregar(nomeArquivo);
    }
    
    // Código omitido
}
```

#### Classe ListaDeComprasController

```java
public class ListaDeComprasController {
    
	//Código omitido

    private void salvarEmAqrTexto() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.salvar("lista_compras.txt");
    }

    private void carregarDeArqTexto() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.carregar("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"
    }

    private void salvarEmArquivoBinario(){
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.salvar("lista_compras.bin");
    }

    private void carregarDeArquivoBinario(){
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.carregar("lista_compras.bin");
    }

    private void salvarEmArquivoJson(){
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.salvar("lista_compras.json");
    }

    private void carregarDeArquivoJson(){
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.carregar("lista_compras.json");
    }

    //Código omitido
}
```

Se observarmos a classe `ListaDeComprasController`, ela está "inchada". Ela centraliza toda a lógica de decisão do menu, o que viola o "Princípio da Responsabilidade Única". Neste caso, podemos aplicar o padrão de projeto `Command`. Ele pode ajudar a reduzir o acoplamento (o `Controller` não precisará saber como cada operação (adicionar, salvar, filtrar) funciona, ele apenas dirá: comando.executar()), eliminar o `Switch-Case` gigante (com `Command`, vamos mapear a opção para um objeto e o executar) e facilitará adicionar uma nova função sendo necessário apenas criar uma nova classe que implementa a interface `Command`, sem mexer na estrutura do `Controller`.

Primeiramente, vamos criar um pacote chamado `command` para armazenar a interface e as classes referentes ao padrão utilizado. Depois, vamos criar a interface `Commmand`:
```java
package br.com.command;

public interface Command {
    void execute();
}
``` 

Em seguida, vamos criar as classes concretas para cada ação. Cada uma delas irá implementar a interface `Command`. As lógicas dos métodos privados que estão na `Controller` serão trazidas para essas classes. Note em cada classe `Command` que ela recebe as dependências necessárias no construtor:

#### Classe `AdicionarProdutoCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.Produto;
import br.com.view.ListaDeComprasView;

public class AdicionarProdutoCommand implements Command {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public AdicionarProdutoCommand(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void execute() {
        // Código trazido da classe Controller
        String nome = view.lerNomeProduto();
        int quantidade = view.lerQuantidade();
        double preco = view.lerPreco();
        model.adicionarProduto(new Produto(nome, quantidade, preco));
        view.exibirMensagem("Produto adicionado com sucesso!");
    }
}
```

#### Classe `RemoverProdutoCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;

public class RemoverProdutoCommand implements Command {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public RemoverProdutoCommand(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void execute() {
        String nome = view.lerNomeProduto();
        model.removerProduto(nome);
    }
}

```

#### Classe `ExibirListaCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;

public class ExibirListaCommand implements Command {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public ExibirListaCommand(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void execute() {
        view.exibirMensagem(model.toString());
    }
}

```

#### Classe `SalvarEmAqrTextoCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaTexto;

public class SalvarEmAqrTextoCommand implements Command{
    private ListaDeCompras model;

    public SalvarEmAqrTextoCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.salvar("lista_compras.txt");
    }
}

```

#### Classe `CarregarDeArqTextoCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaTexto;

public class CarregarDeArqTextoCommand implements Command{
    private ListaDeCompras model;

    public CarregarDeArqTextoCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.carregar("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"
    }
}

```

#### Classe `SalvarEmArquivoBinarioCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaBinario;

public class SalvarEmArquivoBinarioCommand implements Command {
    private ListaDeCompras model;

    public SalvarEmArquivoBinarioCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.salvar("lista_compras.bin");
    }
}
```

#### Classe `CarregarDeArquivoBinarioCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaBinario;

public class CarregarDeArquivoBinarioCommand implements Command {
    private ListaDeCompras model;

    public CarregarDeArquivoBinarioCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.carregar("lista_compras.bin");
    }
}
```

#### Classe `SalvarEmArquivoJsonCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaJson;

public class SalvarEmArquivoJsonCommand implements Command {
    private ListaDeCompras model;

    public SalvarEmArquivoJsonCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.salvar("lista_compras.json");
    }
}

```

#### Classe `CarregarDeArquivoJsonCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.model.PersistenciaJson;

public class CarregarDeArquivoJsonCommand implements Command {
    private ListaDeCompras model;

    public CarregarDeArquivoJsonCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.carregar("lista_compras.json");
    }
}

```

#### Classe `FiltrarPorQuantidadeMinimaCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;

public class FiltrarPorQuantidadeMinimaCommand implements Command{
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public FiltrarPorQuantidadeMinimaCommand(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void execute() {
        int quantidadeMinima = view.lerQuantidadeMinima();
        System.out.println(model.filtrarPorQuantidadeMinima(quantidadeMinima).toString());
    }
}

```

#### Classe `CalcularValorTotalCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;

public class CalcularValorTotalCommand implements Command{
    private ListaDeCompras model;

    public CalcularValorTotalCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        System.out.println("Valor Total R$ "+model.calcularValorTotal());
    }
}

```

#### Classe `ImprimirListaCommand`:

```java
package br.com.command;

import br.com.model.ListaDeCompras;

public class ImprimirListaCommand implements Command{
    private ListaDeCompras model;

    public ImprimirListaCommand(ListaDeCompras model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.imprimirLista();
        System.out.println("Valor Total R$ "+model.calcularValorTotal());
    }
}
```

Com as classes criadas, vamos refatorar a `Controller`. O obejtivo é tornar a `Controller` apenas um "invocador", que conhece uma lista de comandos, e cada comando cuidará de interagir com a `View` e com o `Model`.
A primeira coisa que iremos fazer é remover o `switch-case` do método `processarOpcao()` e usar a um `Map`. Fazendo isso, para adicionar uma nova opção no menu, não será mais necessário mexer na lógica da `Controller`, apenas registrar um novo comando no mapa. O registro dos comandos será feito em método específico (`registrarComandos()`). 

#### Classe `ListaDeComprasController`:
```java
public class ListaDeComprasController {
    private ListaDeCompras model;
    private ListaDeComprasView view;
    private Map<Integer, Command> comandos = new HashMap<>();

    public ListaDeComprasController(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
        registrarComandos();
    }

    private void registrarComandos() {
        comandos.put(1, new AdicionarProdutoCommand(model, view));
        comandos.put(2, new RemoverProdutoCommand(model, view));
        comandos.put(3, new ExibirListaCommand(model, view));
        comandos.put(4, new SalvarEmAqrTextoCommand(model));
        comandos.put(5, new CarregarDeArqTextoCommand(model));
        comandos.put(6, new SalvarEmArquivoBinarioCommand(model));
        comandos.put(7, new CarregarDeArquivoBinarioCommand(model));
        comandos.put(8, new SalvarEmArquivoJsonCommand(model));
        comandos.put(9, new CarregarDeArquivoJsonCommand(model));
        comandos.put(10, new FiltrarPorQuantidadeMinimaCommand(model, view));
        comandos.put(11, new CalcularValorTotalCommand(model));
        comandos.put(12, new ImprimirListaCommand(model));
    }

    public void iniciar() {
        int opcao;
        do {
            view.exibirMenu();
            opcao = view.lerOpcao();
            if (opcao != 0) {
                processarOpcao(opcao);
            }
        } while (opcao != 0);
        view.exibirMensagem("Saindo...");
    }

    private void processarOpcao(int opcao) {
        Command comando = comandos.get(opcao);
        if (comando != null) {
            comando.execute();
        } else {
            view.exibirMensagem("Opção inválida!");
        }
    }
}
```

Analisando o código refatorado, a classe ficou bem mais enxuta. O método `processarOpcao()`, por exemplo, ficou bem mais limpo. Ele agora apenas despacha a tarefa para quem sabe fazer. 

#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes para verificar se as funcionalidades continuam funcionando como o esperado.


---
## Conclusão

Neste projeto, aprendemos a manipular arquivos de texto, binários e JSON, além de serializar objetos em Java. Também exploramos o uso das `Collections` (**List**, **Set** e **Map**) e da **API Streams** para manipulação de coleções e aplicamos padrões de projeto como **MVC**, **Singleton**, **Strategy** e **Command** para organizar e melhorar a estrutura do código.

### Recapitulando os Conceitos

1. **Manipulação de Arquivos:**
   - **Arquivos de Texto:** Leitura e escrita de dados em formato de texto.
   - **Arquivos Binários:** Leitura e escrita de dados em formato binário, ideal para grandes volumes de dados.
   - **Arquivos JSON:** Leitura e escrita de dados em formato JSON, amplamente utilizado para troca de dados entre sistemas.

2. **Serialização de Objetos:**
   - Transformação de objetos em uma sequência de bytes para armazenamento ou transmissão.
   - Uso da interface `Serializable` para marcar classes que podem ser serializadas.

3. **Collections Framework:**
   - **List:** Ordenada, permite duplicados
   - **Set:** Não permite duplicados
   - **Map:** Estrutura chave-valor
   
4. **API Streams:**
   - Manipulação de coleções de forma declarativa e funcional.
   - Operações como `filter`, `map`, `sorted` e `reduce` para processar dados de forma eficiente.

5. **Padrões Arquitetural e de Projeto:**
   - **MVC (Model-View-Controller):** Separação de responsabilidades entre model (dados), view (interface) e controller (lógica).
   - **Singleton:** Garantia de que apenas uma instância de uma classe seja criada durante a execução do programa.
   - **Strategy:** Encapsulamento de cada método de persistência (texto, binário, JSON) em uma classe separada.
   - **Command:** Transformação de uma solicitação (opção de menu) em um objeto independente, eliminando, por exemplo, blocos gigantes de `if/else` ou `switch-case`.

---

## Próximo Passo

- **Banco de Dados:** Integração com bancos de dados para persistência de dados.

Bons estudos!



