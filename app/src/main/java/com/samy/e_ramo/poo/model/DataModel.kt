package com.samy.e_ramo.poo.model

data class DataModel(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
){

    data class Data(
        val `data`: List<DataX>,
        val index: Int,
        val name: String,
        val type: String
    )
    data class DataX(
        val banner: String,
        val brand_id: Int,
        val brand_logo: String,
        val brand_name: String,
        val brand_url: String,
        val button_color: Any,
        val button_text: String,
        val code: String,
        val coupons: List<Coupon>,
        val cover: String,
        val details: String,
        val discount_range: String,
        val extra: Extra,
        val featured: Int,
        val font_color: Any,
        val id: Int,
        val image: String,
        val is_disabled: Int,
        val is_fav: Int,
        val is_new: Int,
        val last_used: String,
        val logo: String,
        val name: String,
        val position: Position,
        val rate: Int,
        val status: Int,
        val subtitle: String,
        val text_image_color: Any,
        val text_on_image: String,
        val title: String,
        val type: String,
        val url: String,
        val used: Int
    )

    data class Coupon(
        val brand_id: Int,
        val brand_logo: String,
        val brand_name: String,
        val brand_url: String,
        val code: String,
        val details: String,
        val discount_range: String,
        val featured: Int,
        val id: Int,
        val is_disabled: Int,
        val is_fav: Int,
        val is_new: Int,
        val last_used: String,
        val rate: Int,
        val status: Int,
        val subtitle: String,
        val title: String,
        val type: String,
        val used: Int
    )

    data class Position(
        val mobile_bottom_homescreen: String,
        val mobile_middle_homescreen: String,
        val mobile_top_homescreen: String
    )

    data class Extra(
        val brand_id: Int,
        val brand_logo: String,
        val brand_name: String,
        val brand_url: String,
        val code: String,
        val details: String,
        val discount_range: String,
        val featured: Int,
        val id: Int,
        val is_disabled: Int,
        val is_fav: Int,
        val is_new: Int,
        val last_used: String,
        val rate: Int,
        val status: Int,
        val subtitle: String,
        val title: String,
        val type: String,
        val used: Int
    )

}