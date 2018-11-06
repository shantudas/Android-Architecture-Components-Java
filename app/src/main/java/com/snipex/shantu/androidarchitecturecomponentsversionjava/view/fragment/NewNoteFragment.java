package com.snipex.shantu.androidarchitecturecomponentsversionjava.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.R;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.Note;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.viewModel.NoteViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewNoteFragment extends Fragment {


    public static final String TAG = NewNoteFragment.class.getSimpleName();
    View view;
    private EditText etTitle;
    private EditText etBody;
    private Button btnSubmit;
    NoteViewModel noteViewModel;


    public NewNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_note, container, false);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);


        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etBody = (EditText) view.findViewById(R.id.etBody);

        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String body = etBody.getText().toString().trim();

                if (!title.isEmpty() || !body.isEmpty()) {
                    Note note = new Note(title, body);
                    noteViewModel.insert(note);

                    NoteListFragment fragment = new NoteListFragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment, NoteListFragment.TAG)
                            .commit();

                    Toast.makeText(getActivity(), "Note Added", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), "PLease enter title and body", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

}
