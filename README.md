# 📱 app-recipe (Android)

Aplicativo Android para gerenciamento de receitas culinárias, desenvolvido como projeto acadêmico. Consome a [api-recipe](https://github.com/Felipe-SMZ/api-recipe) via Retrofit para realizar operações CRUD completas.

## 🚀 Tecnologias

- **[Kotlin](https://kotlinlang.org/)** — linguagem principal
- **[Retrofit 2](https://square.github.io/retrofit/)** — cliente HTTP para consumo da API REST
- **[Gson](https://github.com/google/gson)** — serialização/deserialização JSON
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** — chamadas assíncronas
- **[ViewModel + LiveData](https://developer.android.com/topic/libraries/architecture/viewmodel)** — arquitetura MVVM
- **[Material Design 3](https://m3.material.io/)** — componentes visuais
- **[RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)** — listagem de receitas

## 🗂️ Arquitetura

O projeto segue o padrão **MVVM (Model-View-ViewModel)**:

```
app/src/main/java/com/devfelipeshimizu/app_recipe/
├── model/
│   ├── Recipe.kt              # Data class da receita
│   └── Ingredient.kt          # Data class do ingrediente
├── network/
│   ├── RecipeApiService.kt    # Interface Retrofit com os endpoints
│   └── RetrofitClient.kt      # Configuração do cliente HTTP
├── repository/
│   └── RecipeRepository.kt    # Acesso aos dados da API
├── viewmodel/
│   └── RecipeViewModel.kt     # Gerencia estado e lógica da UI
└── ui/
    ├── SplashActivity.kt      # Tela de entrada com logo
    ├── HomeActivity.kt        # Menu principal
    ├── RecipeListActivity.kt  # Lista todas as receitas
    ├── RecipeFormActivity.kt  # Formulário criar/editar
    ├── RecipeSearchActivity.kt# Busca por ID
    ├── RecipeDetailActivity.kt# Detalhes + editar + deletar
    └── RecipeAdapter.kt       # Adapter do RecyclerView
```

## 📋 Telas

| Tela | Descrição |
|------|-----------|
| **Splash** | Logo animado, redireciona para Home após 2s |
| **Home** | Menu com 4 opções de navegação |
| **Lista** | Exibe todas as receitas em cards com RecyclerView |
| **Formulário** | Criação e edição de receitas (reutilizado) |
| **Busca** | Localiza receita pelo ID |
| **Detalhes** | Exibe receita completa com opções de editar e deletar |

## 🔌 Endpoints consumidos

| Método | Rota | Tela |
|--------|------|------|
| `GET` | `/recipes` | Lista de Receitas |
| `GET` | `/recipes/:id` | Busca + Detalhes |
| `POST` | `/recipes` | Formulário (criar) |
| `PUT` | `/recipes/:id` | Formulário (editar) |
| `DELETE` | `/recipes/:id` | Detalhes |

## ⚙️ Como executar

### Pré-requisitos

- Android Studio (Meerkat ou superior)
- JDK 11+
- Emulador Android ou dispositivo físico com API 24+

### 1. Clone o repositório

```bash
git clone https://github.com/Felipe-SMZ/app-recipe.git
cd app-recipe
```

### 2. Abra no Android Studio

`File → Open → selecione a pasta app-recipe`

### 3. Configure a URL da API

Abra `network/RetrofitClient.kt` e verifique a URL:

```kotlin
// Produção (Render)
private const val BASE_URL = "https://api-recipe-fulh.onrender.com/"

// Desenvolvimento local (emulador Android)
// private const val BASE_URL = "http://10.0.2.2:3000/"
```

### 4. Execute o app

Clique em **Run ▶** ou pressione `Shift + F10`

## 📦 Dependências principais

```kotlin
// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

// ViewModel + LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")

// RecyclerView
implementation("androidx.recyclerview:recyclerview:1.3.2")
```

## 📝 Observações

- A API utiliza MongoDB Atlas como banco de dados — os dados persistem entre sessões
- O plano gratuito do Render pode demorar ~50s na primeira requisição após inatividade
- Para uso com emulador Android, use `10.0.2.2` como host no lugar de `localhost`
- Ingredientes devem ser inseridos no formato `nome,quantidade` (um por linha)
- Passos do modo de preparo devem ser inseridos um por linha

## 🌐 API

A API REST que este app consome está disponível em:
- **Repositório:** https://github.com/Felipe-SMZ/api-recipe
- **URL de produção:** https://api-recipe-fulh.onrender.com

## 📝 Licença

Este projeto foi desenvolvido para fins acadêmicos.