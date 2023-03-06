package com.paulo.noteappkmm.di

import com.paulo.noteappkmm.data.local.DatabaseDriverFactory
import com.paulo.noteappkmm.data.note.SqlDelightNoteDataSource
import com.paulo.noteappkmm.database.NoteDatabase
import com.paulo.noteappkmm.domain.note.NoteDataSource

class DatabaseModule {

    val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}