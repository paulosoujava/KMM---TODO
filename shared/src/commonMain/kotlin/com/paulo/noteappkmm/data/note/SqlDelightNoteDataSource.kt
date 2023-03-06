package com.paulo.noteappkmm.data.note

import com.paulo.noteappkmm.database.NoteDatabase
import com.paulo.noteappkmm.domain.note.Note
import com.paulo.noteappkmm.domain.note.NoteDataSource
import com.paulo.noteappkmm.domain.time.DataTimeUtil

class SqlDelightNoteDataSource(
    db: NoteDatabase
) : NoteDataSource {

    private val queries = db.noteQueries

    override suspend fun insert(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DataTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(noteId: Long): Note? {
        return queries.getNoteById(noteId).executeAsOneOrNull()?.toNote()
    }

    override suspend fun delete(noteId: Long) {
        queries.deleteNoteById(noteId)
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNotes().executeAsList().map { it.toNote() }
    }
}