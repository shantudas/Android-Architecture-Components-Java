package com.snipex.shantu.androidarchitecturecomponentsversionjava.view.fragment;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.R;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.adapter.NoteAdapter;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.Note;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.extras.RecyclerViewTouchListener;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.interfaces.RecyclerViewClickListener;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.viewModel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment {

    public static final String TAG = NoteListFragment.class.getSimpleName();
    private static final String NOTE_ID = "NOTE_ID";
    private NoteViewModel noteViewModel;
    View view;
    private LiveData<List<Note>> noteList;

    public NoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StaggeredGridLayoutManager sGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sGridLayoutManager);
        recyclerView.setHasFixedSize(true);

        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                Log.d(TAG, "onChanged called");
                noteAdapter.setNoteList(notes);

                for (Note note : notes) {
                    System.out.println("-----------------------");
                    System.out.println(note.getId());
                    System.out.println(note.getTitle());
                    System.out.println(note.getBody());
                }
            }
        });

        noteList = noteViewModel.getAllNotes();


        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Note note) {
                UpdateNoteFragment updateNoteFragment = new UpdateNoteFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(NOTE_ID, note.getId());
                updateNoteFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, updateNoteFragment, NoteListFragment.TAG)
                        .addToBackStack(UpdateNoteFragment.TAG)
                        .commit();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, final int position) {
                noteList.observe(getActivity(), new Observer<List<Note>>() {
                    @Override
                    public void onChanged(@Nullable List<Note> notes) {

                        Toast.makeText(getActivity(), notes.get(position).getTitle() + " is pressed!", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onLongClick(View view, final int position) {
                noteViewModel.getAllNotes().observe(getActivity(), new Observer<List<Note>>() {
                    @Override
                    public void onChanged(@Nullable List<Note> notes) {
                        Toast.makeText(getActivity(), notes.get(position).getId() + " is pressed!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }));


        return view;
    }

}
