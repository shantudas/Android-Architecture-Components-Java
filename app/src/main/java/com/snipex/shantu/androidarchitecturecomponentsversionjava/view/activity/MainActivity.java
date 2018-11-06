package com.snipex.shantu.androidarchitecturecomponentsversionjava.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.R;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.view.fragment.NewNoteFragment;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.view.fragment.NoteListFragment;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Note list fragment if this is first creation
        if (savedInstanceState == null) {
            NoteListFragment fragment = new NoteListFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, NoteListFragment.TAG).commit();
        }



        FloatingActionButton fabAddNote=findViewById(R.id.fabAddNote);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewNoteFragment newNoteFragment=new NewNoteFragment();
                getSupportFragmentManager()
                        .beginTransaction()
//                        .addToBackStack("product")
                        .replace(R.id.fragment_container,
                                newNoteFragment, NewNoteFragment.TAG).commit();

//                noteViewModel.insert();
            }
        });
    }
}
