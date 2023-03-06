package com.paulo.noteappkmm.domain.note

import com.paulo.noteappkmm.domain.time.DataTimeUtil

class SearchNotes {

    fun execute(notes: List<Note>, query: String): List<Note> {
        if (query.isBlank()) {
            return notes
        }
        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.content.lowercase().contains(query.lowercase())
        }.sortedBy {
            DataTimeUtil.toEpochMillis(it.created)
        }
    }
}