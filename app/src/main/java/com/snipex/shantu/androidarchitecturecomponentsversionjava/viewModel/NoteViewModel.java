package com.snipex.shantu.androidarchitecturecomponentsversionjava.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.Note;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        this.allNotes = noteRepository.getAllNotes();
    }

    public LiveData<Note> getNoteItem(int itemId){
        return noteRepository.getNoteItem(itemId);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert(Note note){
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }
}
