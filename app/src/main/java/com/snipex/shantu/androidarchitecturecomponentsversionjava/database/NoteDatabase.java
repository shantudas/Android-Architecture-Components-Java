package com.snipex.shantu.androidarchitecturecomponentsversionjava.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static String DB_NAME = "note_database";

    public abstract NoteDao noteDao();

    private static NoteDatabase instance;

    public static NoteDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            /*.addCallback(roomCallBack)*/
                            .build();
                }
            }
        }
        return instance;
    }

    private static NoteDatabase.Callback roomCallBack=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateNoteData(instance).execute();
        }
    };

    private static class populateNoteData extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;
        private populateNoteData(NoteDatabase db){
            noteDao=db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title from room call back","body"));
            return null;
        }
    }
}
