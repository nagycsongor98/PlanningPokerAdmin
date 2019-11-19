package com.nagycsongor.planningpokeradmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {
    private EditText nameEditText;
    private EditText idEditText;
    private Button startButton;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public LoginFragment() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("groups");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        nameEditText = view.findViewById(R.id.nameEditText);
        idEditText = view.findViewById(R.id.idEditText);
        startButton = view.findViewById(R.id.loginButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEditText.getText().toString();
                final String key = idEditText.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
                    Toast.makeText(view.getContext(), "Complete all box", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = database.getReference("groups").child(key);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Group g = dataSnapshot.getValue(Group.class);
                            if (g != null){
                                if(g.getAdmin().equals(name)){
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_frameLayout, new MainFragment(key));
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }else {
                                    Toast.makeText(getContext(), "Group already exist!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Group group = new Group(key, name);
                                //String id = reference.push().getKey();
                                reference.child(key).setValue(group).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(view.getContext(), "Group added!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_frameLayout, new MainFragment(key));
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        return view;
    }


}
