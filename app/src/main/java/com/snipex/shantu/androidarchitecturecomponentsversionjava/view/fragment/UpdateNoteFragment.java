package com.snipex.shantu.androidarchitecturecomponentsversionjava.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.R;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.Note;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.viewModel.NoteViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateNoteFragment extends Fragment {

    public static final String TAG = UpdateNoteFragment.class.getSimpleName();
    private static final String NOTE_ID ="NOTE_ID" ;
    private NoteViewModel noteViewModel;
    View view;
    private EditText etUpdateTitle, etUpdateBody;
    private Button btnSubmit;
    private int noteID;
    private String noteTitle="",noteBody="";


    public UpdateNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_update_note, container, false);


        if (getArguments() != null) {
            noteID = getArguments().getInt(NOTE_ID);
        }
        Log.d(TAG, " noteID:: " + noteID);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        etUpdateTitle = (EditText) view.findViewById(R.id.etUpdateTitle);
        etUpdateBody = (EditText) view.findViewById(R.id.etUpdateBody);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        noteViewModel.getNoteItem(noteID).observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                if (note != null) {
                    noteTitle=note.getTitle();
                    noteBody=note.getBody();
                    etUpdateTitle.setText(noteTitle);
                    etUpdateBody.setText(noteBody);
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteTitle=etUpdateTitle.getText().toString().trim();
                noteBody=etUpdateBody.getText().toString().trim();

                if (!noteTitle.isEmpty() || !noteBody.isEmpty()) {
                    Note note = new Note(noteTitle, noteBody);
                    note.setId(noteID);
                    noteViewModel.update(note);

                    NoteListFragment fragment = new NoteListFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment, NoteListFragment.TAG)
                            .commit();

                    Toast.makeText(getActivity(), "Note Updated", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "PLease enter title and body", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

}
