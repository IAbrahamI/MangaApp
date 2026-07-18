package ch.privat_network.manga_app.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.privat_network.manga_app.data.MangaRepository
import ch.privat_network.manga_app.domain.Manga
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MangaUiState {
    object Loading : MangaUiState()
    data class Success(val mangaList: List<Manga>) : MangaUiState()
    data class Error(val message: String) : MangaUiState()
}

class MangaViewModel : ViewModel() {
    private val repository = MangaRepository()

    private val _uiState = MutableStateFlow<MangaUiState>(MangaUiState.Loading)
    val uiState: StateFlow<MangaUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchManga()
    }

    fun fetchManga(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _isRefreshing.value = true
            } else {
                _uiState.value = MangaUiState.Loading
            }

            try {
                val list = repository.getMangaList()
                if (list.isNotEmpty()) {
                    _uiState.value = MangaUiState.Success(list)
                } else if (!isRefresh) {
                    // Only show error screen if we weren't just refreshing an existing list
                    _uiState.value = MangaUiState.Error("No manga found or API connection failed.")
                }
            } catch (e: Exception) {
                if (!isRefresh) {
                    _uiState.value = MangaUiState.Error(e.localizedMessage ?: "Unknown error")
                }
            } finally {
                _isRefreshing.value = false
            }
        }
    }
}