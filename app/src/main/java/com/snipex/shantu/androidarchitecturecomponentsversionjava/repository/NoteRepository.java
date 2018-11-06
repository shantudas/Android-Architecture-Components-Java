package com.snipex.shantu.androidarchitecturecomponentsversionjava.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.Note;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.NoteDao;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.NoteDatabase;

import java.util.List;



public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        noteDao = db.noteDao();
        allNotes = noteDao.fetchAllNotes();
    }


    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insert (Note note) {
        new insertAsyncTask(noteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskDao;

        private insertAsyncTask(NoteDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }
}