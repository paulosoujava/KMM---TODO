package com.paulo.noteappkmm.domain.note

interface NoteDataSource {
    suspend fun  insert(note: Note)
    suspend fun getNoteById(noteId:Long): Note?
    suspend fun delete(noteId:Long)
    suspend fun getAllNotes(): List<Note>
}