package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.text.format.DateFormat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MemosRecyclerAdapter extends FirestoreRecyclerAdapter<Memo,MemosRecyclerAdapter.MemoViewHolder> {

    public MemosRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Memo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MemoViewHolder holder, int position, @NonNull Memo memo){
        holder.memoTextView.setText(memo.getText());
        holder.checkbox.setChecked(memo.isCompleted());
        CharSequence dateCharSeq = DateFormat.format("EEEE, MMM d, yyyy h:mm:ss a", memo.getCreated().toDate());
        holder.dateTextView.setText(dateCharSeq);
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.memo_row, parent, false);
        return new MemoViewHolder(view);
    }

    class MemoViewHolder extends RecyclerView.ViewHolder{

        TextView memoTextView, dateTextView;
        CheckBox checkbox;
        public MemoViewHolder(@NonNull View itemView){
            super(itemView);

            memoTextView = itemView.findViewById(R.id.memoTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            checkbox = itemView.findViewById(R.id.checkBox);
        }
    }
}
