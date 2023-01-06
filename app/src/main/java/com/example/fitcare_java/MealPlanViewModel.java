package com.example.fitcare_java;

import android.annotation.SuppressLint;
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

public class MealPlanViewModel extends AndroidViewModel {

    private MutableLiveData<List<HistoryMeal>> mItems;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    public MealPlanViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();
        mItems = new MutableLiveData<>();

        databaseReference.child(uid).child("mealPlan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HistoryMeal> items = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    HistoryMeal historyMeal = dataSnapshot.getValue(HistoryMeal.class);
                    items.add(historyMeal);
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

    public LiveData<List<HistoryMeal>> getItems() {
        return mItems;
    }

    public void setItems(List<HistoryMeal> newItems) {
        mItems.setValue(newItems);
    }
}
