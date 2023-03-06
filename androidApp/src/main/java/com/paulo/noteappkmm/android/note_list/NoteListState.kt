package com.paulo.noteappkmm.android.note_list

import com.paulo.noteappkmm.domain.note.Note

data class NoteListState(
    val notes:  List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
