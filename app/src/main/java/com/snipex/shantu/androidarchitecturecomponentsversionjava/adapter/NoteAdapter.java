package com.snipex.shantu.androidarchitecturecomponentsversionjava.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snipex.shantu.androidarchitecturecomponentsversionjava.R;
import com.snipex.shantu.androidarchitecturecomponentsversionjava.database.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> noteList = new ArrayList<Note>();
    private OnItemClickListener listener;

    public NoteAdapter() {

    }

    public NoteAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_each_row_note, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note note = noteList.get(position);
        holder.tvNoteTitle.setText(note.getTitle());
        holder.tvNoteBody.setText(note.getBody());

    }

    @Override
    public int getItemCount() {
        Log.d("note size", String.valueOf(noteList.size()));
        return noteList.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView tvNoteTitle;
        private TextView tvNoteBody;

        public NoteHolder(final View itemView) {
            super(itemView);
            tvNoteTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            tvNoteBody = (TextView) itemView.findViewById(R.id.tvNoteBody);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(noteList.get(position));
                    }
                }
            });
        }
    }


    public Note getNoteAt(int position) {
        return noteList.get(position);
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void OnItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
