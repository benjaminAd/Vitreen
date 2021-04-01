package c0d3.vitreen.app.models

import java.util.ArrayList

data class User(
    val fullname: String,
    val emailAddress: String,
    val phoneNumber: String,
    val contactByPhone: Boolean = true,
    @field:JvmField
    val isProfessional: Boolean = false,
    val companyName: String? = null,
    val siretNumber: String? = null,
    val advertsId: ArrayList<String>? = null,
    val favoriteAdvertsId: ArrayList<String>? = null
)
