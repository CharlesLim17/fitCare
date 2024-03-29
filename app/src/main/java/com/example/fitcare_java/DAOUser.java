package com.example.fitcare_java;

import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOUser {
    static DatabaseReference databaseReference;

    // firebase connection
    public DAOUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    // add function / storing data
    public Task<Void> add(User user) {
        return databaseReference.push().setValue(user);
    }
}


