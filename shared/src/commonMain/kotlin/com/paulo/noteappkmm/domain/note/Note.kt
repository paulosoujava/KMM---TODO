package com.paulo.noteappkmm.domain.note

import com.paulo.noteappkmm.presentation.BabyBlueHex
import com.paulo.noteappkmm.presentation.LightGreenHex
import com.paulo.noteappkmm.presentation.RedOrangeHex
import com.paulo.noteappkmm.presentation.RedPinkHex
import com.paulo.noteappkmm.presentation.VioletHex
import kotlinx.datetime.LocalDateTime

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long,
    val created: LocalDateTime
) {
    companion object {
        private val colors = listOf(
            RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex
        )

        fun generateRandomColor() = colors.random()
    }
}
