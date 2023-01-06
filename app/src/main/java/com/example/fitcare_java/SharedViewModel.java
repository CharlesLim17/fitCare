package com.example.fitcare_java;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends AndroidViewModel {

    private MutableLiveData<List<AlarmHistory>> mItems;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();
        mItems = new MutableLiveData<>();

        databaseReference.child(uid).child("alarms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AlarmHistory> items = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    AlarmHistory alarmHistory = dataSnapshot.getValue(AlarmHistory.class);
                    items.add(alarmHistory);
                }
                mItems.setValue(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }

    @Override
    protected void onCleared() {
        super.onCleared();
        databaseReference.removeEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public LiveData<List<AlarmHistory>> getItems() {
        return mItems;
    }

    public void setItems(List<AlarmHistory> newItems) {
        mItems.setValue(newItems);
    }

    private void loadItems() {

    }
}
