package com.devfelipeshimizu.app_recipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devfelipeshimizu.app_recipe.model.Recipe
import com.devfelipeshimizu.app_recipe.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val repository = RecipeRepository()

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _recipe = MutableLiveData<Recipe?>()
    val recipe: LiveData<Recipe?> = _recipe

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getAllRecipes() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getAllRecipes()
                if (response.isSuccessful) {
                    _recipes.value = response.body()
                } else {
                    _error.value = "Erro ao buscar receitas"
                }
            } catch (e: Exception) {
                _error.value = "Erro de conexão: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getRecipeById(id: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getRecipeById(id)
                if (response.isSuccessful) {
                    _recipe.value = response.body()
                } else {
                    _error.value = "Receita não encontrada"
                    _recipe.value = null
                }
            } catch (e: Exception) {
                _error.value = "Erro de conexão: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun createRecipe(recipe: Recipe) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.createRecipe(recipe)
                if (response.isSuccessful) {
                    _success.value = "Receita criada com sucesso!"
                } else {
                    _error.value = "Erro ao criar receita"
                }
            } catch (e: Exception) {
                _error.value = "Erro de conexão: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateRecipe(id: String, recipe: Recipe) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.updateRecipe(id, recipe)
                if (response.isSuccessful) {
                    _success.value = "Receita atualizada com sucesso!"
                } else {
                    _error.value = "Erro ao atualizar receita"
                }
            } catch (e: Exception) {
                _error.value = "Erro de conexão: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteRecipe(id: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.deleteRecipe(id)
                if (response.isSuccessful) {
                    _success.value = "Receita deletada com sucesso!"
                } else {
                    _error.value = "Erro ao deletar receita"
                }
            } catch (e: Exception) {
                _error.value = "Erro de conexão: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}