package com.snipex.shantu.androidarchitecturecomponentsversionjava.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
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

    public LiveData<Note> getNoteItem(int itemId) {
        return noteDao.getNoteItemById(itemId);
    }

    public void insert(Note note) {
        new insertAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new updateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new deleteAsyncTask(noteDao).execute(note);
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

    private static class updateAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskDao;

        private updateAsyncTask(NoteDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao mAsyncTaskDao;

        private deleteAsyncTask(NoteDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }


}
