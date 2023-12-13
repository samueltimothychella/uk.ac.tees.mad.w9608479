package uk.ac.tees.mad.w9608479.modal

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Help(
    @StringRes val titleResourceId: Int,
    @StringRes val valueResourceId: Int,
    @DrawableRes val imageResourceId: Int
)