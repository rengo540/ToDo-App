package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.taskVH> {

    private List<Task> tasks = new ArrayList<>();
    RecyclerViewClickListener listener;

    public Adapter() {
    }

    public void setList(List<Task> tasks,RecyclerViewClickListener listener) {
        this.tasks = tasks;
        this.listener=listener;
        notifyDataSetChanged();
    }


    class taskVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTaskTitle, textViewDescription , textViewDate;


        public taskVH(@NonNull View itemView) {

            super(itemView);

            textViewTaskTitle = itemView.findViewById(R.id.taskTv);
            textViewDescription = itemView.findViewById(R.id.descriptionTv);
            textViewDate = itemView.findViewById(R.id.dateTv);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            listener.onClick(v ,getAdapterPosition());

        }
    }


    @NonNull
    @Override
    public taskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrived_data, parent, false);

        taskVH b = new taskVH(v);


        return b;
    }


    public void onBindViewHolder(@NonNull taskVH holder, int position) {

        holder.textViewTaskTitle.setText(tasks.get(position).getTask());
        holder.textViewDescription.setText(tasks.get(position).getDescription());
        holder.textViewDate.setText(tasks.get(position).getDate());



    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }



    public interface RecyclerViewClickListener{
        void onClick (View view,int position);
    }

}