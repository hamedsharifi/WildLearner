package com.wildlearner.core.model

import com.google.gson.annotations.SerializedName


data class SearchModel (

    @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf(),
    @SerializedName("image_results" ) var imageResults : ArrayList<String>  = arrayListOf(),
    @SerializedName("total"         ) var total        : Int?               = null,
    @SerializedName("answers"       ) var answers      : ArrayList<String>  = arrayListOf(),
    @SerializedName("ts"            ) var ts           : Double?            = null,
    @SerializedName("device_type"   ) var deviceType   : String?            = null

)

data class AdditionalLinks (

    @SerializedName("text" ) var text : String? = null,
    @SerializedName("href" ) var href : String? = null

)

data class Cite (

    @SerializedName("domain" ) var domain : String? = null,
    @SerializedName("span"   ) var span   : String? = null

)

data class Results (

    @SerializedName("title"            ) var title           : String?                    = null,
    @SerializedName("link"             ) var link            : String?                    = null,
    @SerializedName("description"      ) var description     : String?                    = null,
    @SerializedName("additional_links" ) var additionalLinks : ArrayList<AdditionalLinks> = arrayListOf(),
    @SerializedName("cite"             ) var cite            : Cite?                      = Cite()

)