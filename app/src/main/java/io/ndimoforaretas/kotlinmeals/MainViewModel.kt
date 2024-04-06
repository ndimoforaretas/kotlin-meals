package io.ndimoforaretas.kotlinmeals

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){

    private val _categoryState = mutableStateOf(RecipeState())
    val categoryState: State<RecipeState> = _categoryState

    init {
        fetchCategories()
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            _categoryState.value = RecipeState(isLoading = true)
            try {
                val response = apiService.getCategories()
                _categoryState.value = _categoryState.value.copy(
                    isLoading = false,
                    list = response.categories,
                    error = null
                )
            } catch (e: Exception){
                _categoryState.value = _categoryState.value.copy(
                    isLoading = false,
                    error = "An error occurred while fetching categories : ${e.message}"
                )
            }
        }
    }

    data class RecipeState(
        val isLoading: Boolean = false,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}