# SmartList — Guia de estudo para a prova

Projeto: gerenciador de lista de compras em Java (console), seguindo o README da professora.
Pacote base: `org.example` (o README usa `br.com`, só muda o nome).

## 1. O que foi feito nesta atualização

A branch `Main` estava até JSON. Foi atualizada até o fim do README (padrões de projeto):

| Arquivo | O que mudou |
|---|---|
| `model/ListaDeCompras.java` | **Singleton** (construtor privado + `getInstancia()`); **Strategy** (campo `estrategiaPersistencia`, `setEstrategiaPersistencia`, `salvar`, `carregar`); **Streams** (`filtrarPorQuantidadeMinima`, `calcularValorTotal`, `imprimirLista`). Os 6 métodos antigos de texto/binário/JSON saíram daqui. |
| `model/PersistenciaStrategy.java` | Interface com `salvar(lista, caminho)` e `carregar(caminho)` (já existia, sem mudança). |
| `model/PersistenciaTexto.java` | **Estava com erro de compilação** (método dentro de método, variável `nomeArquivo` inexistente). Reescrita. |
| `model/PersistenciaBinario.java` | **Novo.** Strategy para `ObjectOutputStream`/`ObjectInputStream`. |
| `model/PersistenciaJson.java` | **Novo.** Strategy para Jackson. |
| `command/Command.java` | **Novo.** Interface com `execute()`. |
| `command/*Command.java` (12 classes) | **Novos.** Um por opção do menu. |
| `controller/ListaDeComprasController.java` | Removido o `switch-case`; agora usa `Map<Integer, Command>`. |
| `view/ListaDeComprasView.java` | Menu com opções 10, 11, 12 e método `lerQuantidadeMinima()`. |
| `Main.java` | Já usava `getInstancia()`. |

Testado: compilação com `javac` e execução do menu (adicionar, listar, filtrar, total, ordem alfabética, salvar/carregar texto, opção inválida, sair).

## 2. Visão geral: MVC

```
Main  ──cria──▶  Model (ListaDeCompras)  ◀── Controller ──▶ View (ListaDeComprasView)
```

- **Model** (`model/`): dados e regras. `Produto`, `ListaDeCompras`, classes de persistência.
- **View** (`view/`): só `System.out` e `Scanner`. Não sabe nada de regra de negócio.
- **Controller** (`controller/`): lê a opção da View e manda executar. Faz a ponte.

Por quê: separação de responsabilidades. Trocar o console por uma tela gráfica mexe só na View.

## 3. Classe por classe

### `Produto`
`nome`, `quantidade`, `preco`.
- `implements Serializable` → permite gravar em binário (`ObjectOutputStream`).
- Construtor **sem argumentos** + getters/setters → o Jackson precisa deles para ler JSON.
- `toString()` define como o produto aparece impresso.

### `ListaDeCompras` (Model)
Guarda `List<Produto> produtos`. Métodos: `adicionarProduto`, `removerProduto` (`removeIf` ignorando maiúsculas), `toString` (lista numerada), os três de Streams e os de persistência via Strategy.

### `Main`
Cria model, view e controller e chama `controller.iniciar()`.

## 4. Os arquivos (texto, binário, JSON)

| Formato | Classe Java | Como é |
|---|---|---|
| Texto | `BufferedWriter` / `BufferedReader` | `Arroz - 2 - 10.5` por linha; ao ler usa `split(" - ")` |
| Binário | `ObjectOutputStream` / `ObjectInputStream` | Grava o `List<Produto>` inteiro; exige `Serializable` |
| JSON | Jackson `ObjectMapper` | `writeValue` grava; `readValue` com `constructCollectionType(List.class, Produto.class)` lê |

Pontos de prova:
- **try-with-resources**: fecha o arquivo sozinho, mesmo com exceção (`close()` do `BufferedWriter` já faz `flush()`).
- `new FileWriter(nome, false)` sobrescreve; `true` adiciona ao final (append).
- `new File(...)` **não cria** o arquivo no disco, só representa o caminho.
- `serialVersionUID` identifica a versão da classe; `transient` exclui um atributo da serialização.
- Jackson usa **reflection**: construtor padrão + getters (serializar) + setters (desserializar).

## 5. Collections e Streams

- **List**: ordenada, aceita duplicados, acesso por índice. **Set**: sem duplicados. **Map**: chave → valor, chave única.
- Declare pela interface: `List<Produto> x = new ArrayList<>();`.
- `equals()` e `hashCode()` são essenciais para `HashSet`/`HashMap`.

Stream = esteira de processamento, **não armazena** dados:
fonte (`.stream()`) → intermediárias (`filter`, `map`, `sorted`) → terminal (`collect`, `forEach`, `sum`, `count`, `reduce`).

Características: não altera a coleção original; só pode ser consumida uma vez; é *lazy* (só roda quando há operação terminal).

No projeto:
```java
// opção 10: filtra
produtos.stream().filter(p -> p.getQuantidade() >= min).collect(Collectors.toList());
// opção 11: soma
produtos.stream().mapToDouble(p -> p.getQuantidade() * p.getPreco()).sum();
// opção 12: ordena por nome (sem diferenciar maiúscula) e imprime
produtos.stream().sorted(Comparator.comparing(p -> p.getNome().toLowerCase())).forEach(System.out::println);
```

## 6. Os 3 padrões de projeto

### Singleton — "só pode existir uma instância"
```java
private static ListaDeCompras instancia;
private ListaDeCompras() { ... }                 // ninguém dá new de fora
public static ListaDeCompras getInstancia() {    // cria na 1ª vez, depois reaproveita
    if (instancia == null) instancia = new ListaDeCompras();
    return instancia;
}
```
Passos: (1) construtor privado; (2) campo estático; (3) método estático que cria uma vez só.
Crítica: cria estado global e atrapalha testes unitários. Aqui é usado para fins didáticos.

### Strategy — "trocar o algoritmo em tempo de execução"
`PersistenciaStrategy` é a interface; `PersistenciaTexto`, `PersistenciaBinario` e `PersistenciaJson` são as estratégias.
`ListaDeCompras` guarda uma estratégia e delega: `estrategiaPersistencia.salvar(produtos, arquivo)`.
Ganho: não há `if (formato == "json") ... else if ...`. Adicionar XML = nova classe, sem mexer em `ListaDeCompras`.

### Command — "transformar o pedido em objeto"
`Command` tem só `execute()`. Cada opção do menu virou uma classe (`AdicionarProdutoCommand`, `SalvarEmArquivoJsonCommand`, ...).
O controller virou um despachante:
```java
comandos.put(1, new AdicionarProdutoCommand(model, view));
...
comandos.get(opcao).execute();
```
Ganhos: some o `switch-case` gigante; baixo acoplamento (o controller não sabe *como* cada coisa funciona); nova função = nova classe + 1 linha no `registrarComandos()`.
O exemplo clássico de uso do Command é Desfazer/Refazer.

### Como Strategy e Command trabalham juntos aqui
Menu **6** → `SalvarEmArquivoBinarioCommand` → define `new PersistenciaBinario()` no model → `model.salvar("lista_compras.bin")` → a estratégia grava.

## 7. Fluxo completo (exemplo: opção 8)

1. `Main` monta tudo; o `Controller` registra os 12 comandos.
2. `iniciar()` mostra o menu (View) e lê o número.
3. `processarOpcao(8)` busca no `Map` e chama `execute()`.
4. `SalvarEmArquivoJsonCommand` define a estratégia JSON e chama `model.salvar(...)`.
5. `PersistenciaJson.salvar` usa o Jackson e escreve `lista_compras.json`.

## 8. Cuidados e armadilhas (caem em prova)

- `carregar()` só troca a lista se a estratégia retornou algo (se o arquivo estiver corrompido, a lista atual é preservada). O código do README atribuiria `null` e quebraria a próxima operação.
- Se você abrir o menu 4–9 sem ter chamado a estratégia, `estrategiaPersistencia` seria `null`. Por isso cada Command define a estratégia antes de usar.
- `Scanner.nextDouble()` depende da **localidade**: em Windows pt-BR digite `10,5`, não `10.5`.
- `nextInt()` deixa o `\n` no buffer; por isso o `scanner.nextLine()` logo depois.
- Lambdas: `p -> p.getQuantidade() >= min` é uma função passada como parâmetro (funções de alta ordem).

## 9. Perguntas para você se testar

1. Por que `ListaDeCompras` tem construtor privado?
2. Qual a diferença entre `PersistenciaStrategy` e `Command`? (uma troca *como* salvar; a outra representa *qual ação* do menu)
3. O que acontece com a lista original após `.filter().collect()`?
4. Por que o Jackson exige construtor vazio e setters?
5. Para adicionar a opção "13. Remover tudo", quais arquivos mudam? (1 classe nova, 1 linha no `registrarComandos`, 1 linha no menu)
6. Por que `Produto` precisa de `Serializable` se o JSON não exige?
