package com.paulo.noteappkmm.android.note_detail

import android.text.format.DateUtils
import com.paulo.noteappkmm.domain.note.Note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulo.noteappkmm.domain.note.NoteDataSource
import com.paulo.noteappkmm.domain.time.DataTimeUtil

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused", false)
    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused", false)
    private val noteColor = savedStateHandle.getStateFlow(
        "noteColor",
        Note.generateRandomColor()
    )

    val state = combine(
        noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { title, isTitleFocused, content, isContentFocused, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocused,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocused,
            noteColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if(existingNoteId == -1L) {
                return@let
            }
            this.existingNoteId = existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text: String) {
        savedStateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text: String) {
        savedStateHandle["noteContent"] = text
    }

    fun onNoteTitleFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteTitleFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused: Boolean) {
        savedStateHandle["isNoteContentFocused"] = isFocused
    }

    fun saveNote() {

        viewModelScope.launch {
            noteDataSource.insert(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DataTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}