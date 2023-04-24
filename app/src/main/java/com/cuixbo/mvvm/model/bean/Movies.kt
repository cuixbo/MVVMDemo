package com.cuixbo.mvvm.model.bean

import java.io.Serializable

data class MovieResp(
    val count: Int,
    val items: List<MovieItem>,
//    val manual_tags: List<Any>,
    val quick_mark: Any,
    val show_rating_filter: Boolean,
    val start: Int,
    val total: Int
): Serializable

data class MovieItem(
//    val alg_json: String,
    val card: String,
    val card_subtitle: String,
    val comment: Comment,
    val episodes_info: String,
    val following_rating: Any,
    val has_linewatch: Boolean,
//    val honor_infos: List<Comment.HonorInfo>,
    val id: String,
    val interest: Any,
    val item_type: String,
    val photos: List<String>,
    val pic: Comment.Pic,
    val playable_date: Any,
    val playable_date_info: String,
    val rating: Comment.Rating,
//    val tags: List<Comment.Tag>,
    var title: String,
    val type: String,
    val uri: String,
    val vendor_count: Int,
//    val vendor_icons: List<Any>,
    val year: String
)

data class Comment(
    val comment: String,
    val id: String,
    val user: User
) {
    data class User(
        val avatar: String,
        val id: String,
        val is_club: Boolean,
        val kind: String,
        val name: String,
        val type: String,
        val uid: String,
        val uri: String,
        val url: String
    )

    data class HonorInfo(
        val kind: String,
        val rank: Int,
        val title: String,
        val uri: String
    )

    data class Pic(
        val large: String,
        val normal: String
    )

    data class Rating(
        val count: Int,
        val max: Int,
        val star_count: Float,
        val value: Double
    )

    data class Tag(
        val name: String,
        val uri: String
    )
}


