package com.example.dwitchapp.viewModel

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.example.dwitchapp.fetchNews
import com.example.dwitchapp.model.news.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _newsState = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> get() = _newsState

    private val _loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> get() = _error

    fun loadNews() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = false
            try {
                _newsState.value = fetchNews() ?: emptyList()
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }
}
@Composable
fun NewsScreen(newsViewModel: NewsViewModel) {
    val news by newsViewModel.news.collectAsState()
    val isLoading by newsViewModel.isLoading.collectAsState()
    val hasError by newsViewModel.hasError.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.loadNews()  // Chargez les nouvelles au démarrage
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            hasError -> {
                Text(
                    text = "Une erreur est survenue lors du chargement des actualités.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            news.isEmpty() -> { // Vérifiez si la liste des news est vide
                Text(
                    text = "Aucune actualité disponible.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> {
                NewsList(news = news)  // Affichage de la liste des nouvelles
            }
        }
    }
}
@Composable
fun NewsList(news: List<News>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(news) { newsItem ->
            Log.d("NewsItem", "Affichage de : ${newsItem.title}")
            NewsCard(newsItem)
        }
    }
}

@Composable
fun NewsCard(
    news: News,
    withImage: Boolean = true // Paramètre pour afficher ou non l'image
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() //  la maximun largeur disponible
            .wrapContentHeight(), // Adaptateur de hauteur(contenu)
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // affichage de l'ecran en hautde la card
            if (withImage) {
                AsyncImage(
                    model = "https://dwitch.pickle-forge.app${news.medias[0].url}",
                    contentDescription = news.title,
                    contentScale = ContentScale.FillWidth, // adaptateur de l'image sur toute la largeur
                    modifier = Modifier
                        .fillMaxWidth() // Image occupe toute la largeur
                        .aspectRatio(16f / 9f) // Définit une proportion pour l'image
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // espacement autour du contenu
            ) {
                // Titre
                news.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge, // Style pour un titre plus grand
                        maxLines = Int.MAX_VALUE // tout le contenu
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Contenu
                news.content?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium, // Style pour le corps du texte
                        maxLines = Int.MAX_VALUE, // git add tout le texte
                        overflow = TextOverflow.Visible
                    )
                }
            }
        }
    }
}

