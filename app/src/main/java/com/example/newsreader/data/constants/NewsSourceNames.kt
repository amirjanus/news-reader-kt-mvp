package com.example.newsreader.data.constants

enum class NewsSourceNames(private val s: String) {

    BBC("bbc-news");

    /**
     * Returns news source name.
     *
     * @return News source name.
     */
    fun getString(): String {
        return s
    }

}
