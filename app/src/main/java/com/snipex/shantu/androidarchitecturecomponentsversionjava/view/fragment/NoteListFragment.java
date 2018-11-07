package com.snipex.shantu.androidarchitecturecomponentsversionjava.view.fragment;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.R;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.adapter.NoteAdapter;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.Note;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.viewModel.NoteViewModel;

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
    private NoteAdapter noteAdapter;

    public NoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note_list, container, false);

        final TextView tvNoteStatus = (TextView) view.findViewById(R.id.tvNoteStatus);
        RecyclerView recyclerView = view.findViewById(R.id.rvNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteList = noteViewModel.getAllNotes();

        if (noteList.getValue() == null) {

        }

        noteList.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {

                if (!notes.isEmpty()) {
                    noteAdapter.setNoteList(notes);
                    tvNoteStatus.setVisibility(View.GONE);
                }else {
                    tvNoteStatus.setVisibility(View.VISIBLE);
                }
            }
        });

        // For edit
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Note note) {
                editNote(note.getId());
            }
        });

        // For swipe delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    private void editNote(int id) {
        UpdateNoteFragment updateNoteFragment = new UpdateNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(NOTE_ID, id);
        updateNoteFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, updateNoteFragment, NoteListFragment.TAG)
                .addToBackStack(UpdateNoteFragment.TAG)
                .commit();
    }

}
