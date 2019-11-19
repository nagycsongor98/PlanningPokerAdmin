package com.nagycsongor.planningpokeradmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private Problem problem;
    private TextView titleTextView;
    private Button startButton;
    private Button stopButton;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Vote> votes;


    public ListFragment(Problem problem) {
        this.problem = problem;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("problems").child(problem.getGrupKey()).child(problem.getName());
        votes = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        startButton = view.findViewById(R.id.startButton);
        stopButton = view.findViewById(R.id.stopButton);
        if (problem.isAvailable()){
            stopButton.setBackgroundColor(Color.GREEN);
            startButton.setBackgroundColor(Color.RED);
            startButton.setClickable(false);
        }else {
            startButton.setBackgroundColor(Color.GREEN);
            stopButton.setBackgroundColor(Color.RED);
            stopButton.setClickable(false);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("available").setValue(true);
                stopButton.setBackgroundColor(Color.GREEN);
                stopButton.setClickable(true);
                startButton.setBackgroundColor(Color.RED);
                startButton.setClickable(false);
//                Toast.makeText(getContext(), "START", Toast.LENGTH_SHORT).show();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("available").setValue(false);
                startButton.setBackgroundColor(Color.GREEN);
                startButton.setClickable(true);
                stopButton.setBackgroundColor(Color.RED);
                stopButton.setClickable(false);
//                Toast.makeText(getContext(), "STOP", Toast.LENGTH_SHORT).show();
            }
        });

        titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(problem.getName());
        recyclerView = view.findViewById(R.id.answerRecyclerView);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new VotesListAdapter(getContext(),votes);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        database.getReference().child("votes").child(problem.getGrupKey()).child(problem.getName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();
                for (DataSnapshot voteSnapshot : dataSnapshot.getChildren()){
                    Vote vote = voteSnapshot.getValue(Vote.class);
                    votes.add(vote);
                }
                mAdapter = new VotesListAdapter(getContext(),votes);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
