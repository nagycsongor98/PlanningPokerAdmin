package com.nagycsongor.planningpokeradmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProblemsAdapter extends RecyclerView.Adapter<ProblemsAdapter.MyViewHolder> {
    private ArrayList<Problem> data;
    private Context context;
    private FragmentTransaction fragmentTransaction;

    public ProblemsAdapter(ArrayList<Problem> data, Context context, FragmentTransaction fragmentTransaction) {
        this.data = data;
        this.context = context;
        this.fragmentTransaction = fragmentTransaction;
    }

    @NonNull
    @Override
    public ProblemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.string_item,parent,false);
        return  new ProblemsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemsAdapter.MyViewHolder holder, final int position) {
        holder.textView.setText(data.get(position).getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.fragment_frameLayout,new ListFragment(data.get(position)));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        ConstraintLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stringTextView);
            layout = itemView.findViewById(R.id.stringItem);
        }
    }
}
