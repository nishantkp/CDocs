package com.example.android.cdocs.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cdocs.databinding.ListItemBinding;
import com.example.android.cdocs.ui.model.Docs;

import java.util.List;

public class DocsAdapter extends RecyclerView.Adapter<DocsAdapter.DocsViewHolder> {

    private List<Docs> mDocsList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onClick(Docs item, int position);
    }

    public DocsAdapter(OnItemClickListener itemClickListener, List<Docs> docsList) {
        mDocsList = docsList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DocsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding listItemBinding = ListItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DocsViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DocsViewHolder holder, int position) {
        holder.bind(mDocsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDocsList.size();
    }

    // Update the RecyclerView data set
    public void swapData(List<Docs> docsList) {
        mDocsList = docsList;
        notifyDataSetChanged();
    }

    // Clear data set of Recycler View
    public void clearData() {
        mDocsList.clear();
        notifyDataSetChanged();
    }

    // View Holder
    class DocsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final ListItemBinding listItemBinding;

        DocsViewHolder(ListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.listItemBinding = itemBinding;
            itemBinding.getRoot().setOnClickListener(this);
        }

        // Bind the data to listItem view with data binding
        void bind(Docs docs) {
            listItemBinding.setItem(docs);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(mDocsList.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
