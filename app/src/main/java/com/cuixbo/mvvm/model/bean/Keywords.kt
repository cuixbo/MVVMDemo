package com.cuixbo.mvvm.model.bean

import java.io.Serializable

data class Keywords(
    val q: String?,
    val p: String?,
    val g: ArrayList<Word>?,
): Serializable

data class Word(
    val q: String?
): Serializable

