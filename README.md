# 🎬 Filmes Favoritos

App Android para visualizar e editar sua lista de filmes favoritos: nome, nota e foto.

Projeto desenvolvido em **Kotlin**, com arquitetura **MVVM**, **View Binding** e carregamento de imagens com **[Coil](https://github.com/coil-kt/coil)**.

## Funcionalidades

- Listar filmes com foto, nome e nota (estrelas)
- Adicionar um novo filme (botão **+**)
- Editar nome, nota (0 a 5, em passos de 0,5) e URL da foto
- Excluir filmes da lista
- Validação do nome (não permite salvar vazio)
- Mensagem exibida quando a lista está vazia

## Tecnologias

| Item | Uso |
|---|---|
| Kotlin | Linguagem |
| MVVM + `ViewModel` + `StateFlow` | Arquitetura e estado da tela |
| View Binding | Acesso aos elementos do layout |
| [Coil 2.7.0](https://github.com/coil-kt/coil) | Carregamento das fotos por URL |
| RecyclerView + `ListAdapter` + `DiffUtil` | Lista eficiente |
| Material Components | Cards, botões, campos de texto e FAB |

## Arquitetura

```
app/src/main/java/com/example/filmesfavoritos/
├── data/
│   ├── Movie.kt              # Modelo (id, título, nota, URL da foto)
│   └── MovieRepository.kt    # Fonte de dados em memória
├── viewmodel/
│   └── MovieViewModel.kt     # Lógica: salvar (criar/editar), excluir, validar
└── ui/
    ├── MainActivity.kt       # Observa o estado e abre o diálogo de edição
    └── MovieAdapter.kt       # Adapter da lista (View Binding + Coil)
```

- **Model:** `Movie` e `MovieRepository`.
- **ViewModel:** expõe a lista como `StateFlow` e concentra a lógica de edição.
- **View:** a `MainActivity` apenas observa o estado e repassa as ações do usuário.

## Como executar

**Requisitos**
- Android Studio (versão recente)
- JDK 17 ou 21 (o JDK 25 não funciona com o Gradle 8.7 do projeto)
- Android SDK Platform 34
- Conexão com a internet (para o Gradle e para carregar as fotos)

**Passos**
1. Clone o repositório:
   ```bash
   git clone https://github.com/whtxfps-ux/FilmesFavoritos.git
   ```
2. Abra a pasta no Android Studio (**File > Open**) e aguarde o *Gradle Sync*.
3. Crie ou conecte um dispositivo (emulador API 34 ou celular com depuração USB).
4. Clique em ▶ **Run**.

> ⚠️ **Atenção ao caminho do projeto:** ele não pode ter acentos ou outros caracteres não ASCII, ou o sync do Gradle falha com o erro *"Your project path contains non-ASCII characters"*. Use algo como `C:\Projetos\FilmesFavoritos`.

## Observações

- Os dados ficam **somente em memória**: ao fechar o app, a lista volta ao estado inicial (3 filmes de exemplo).
- Para persistir, basta trocar o `MovieRepository` por uma implementação com Room ou DataStore, sem alterar a interface.

## Próximos passos

- [ ] Persistência local (Room)
- [ ] Escolher a foto da galeria
- [ ] Buscar e ordenar filmes pela nota
- [ ] Testes unitários do `MovieViewModel`
