package ch.privat_network.manga_app.domain

/**
 * Domain model for a Manga entry.
 * Mapped from the positional array response of the FastAPI backend.
 */
data class Manga(
    val id: Int,
    val url: String,
    val title: String,
    val imageUrl: String,
    val author: String,
    val status: String,
    val genres: String,
    val rating: String,
    val description: String,
    val latestChapter: String,
    val chapterUrl: String,
    val date: String
)