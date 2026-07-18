package ch.privat_network.manga_app.data

import retrofit2.http.GET

interface MangaService {
    @GET("mangas") // Updated to match your FastAPI endpoint
    suspend fun getMangaList(): List<List<Any>>
}