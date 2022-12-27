package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class mealPlanFragment extends Fragment {

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    //Recycler view
    RecyclerView recycleViewMeal;
    ArrayList<HistoryMeal> meals;
    AdapterMeal adapter;

    //
    androidx.appcompat.widget.SearchView etSearch;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);

        //declaring and setting variable
        ImageView btnBack = view.findViewById(R.id.btnBack);
        ImageView btnAdd = view.findViewById(R.id.btnAdd);
        etSearch = view.findViewById(R.id.etSearch);
        recycleViewMeal = view.findViewById(R.id.recycleViewMeal);
        etSearch.clearFocus();

        //initiate loading dialog
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());

        //Start Loading Dialog
        loadingDialog.startLoadingDialog();

        //End Load Dialog
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 2500);

        //firebase
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        uid = user.getUid();

        //setup recyclerview
        meals = new ArrayList<>();
        recycleViewMeal.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterMeal(getActivity(), meals);
        recycleViewMeal.setAdapter(adapter);

        readMealHistory();

        //search on change text
        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMeal(newText);
                return false;
            }
        });

        //back onclick
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment homeFrag = new homeFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, homeFrag, null).addToBackStack(null).commit();
            }
        });

        //add onclick
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanAddFrag = new mealPlanAddFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanAddFrag, null).addToBackStack(null).commit();
            }
        });


        return view;
    }

    //function to read meal history
    private void readMealHistory() {
        databaseReference.child(uid).child("mealPlan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    HistoryMeal historyMeal = dataSnapshot.getValue(HistoryMeal.class);
                    meals.add(historyMeal);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //function to search meal history
    private void searchMeal(String text){
        ArrayList<HistoryMeal> searchMeals = new ArrayList<>();
        for (HistoryMeal historyMeal: meals){
            if (historyMeal.getFoodDate().toLowerCase().contains(text.toLowerCase()) || historyMeal.getFoodNameMorning1().toLowerCase().contains(text.toLowerCase())
            || historyMeal.getFoodNameMorning2().toLowerCase().contains(text.toLowerCase()) || historyMeal.getFoodNameMorning3().toLowerCase().contains(text.toLowerCase())
            || historyMeal.getFoodNameAfternoon1().toLowerCase().contains(text.toLowerCase()) || historyMeal.getFoodNameAfternoon2().toLowerCase().contains(text.toLowerCase())
            || historyMeal.getFoodNameAfternoon3().toLowerCase().contains(text.toLowerCase()) || historyMeal.getFoodNameEvening1().toLowerCase().contains(text.toLowerCase())
            || historyMeal.getFoodNameEvening2().toLowerCase().contains(text.toLowerCase()) || historyMeal.getFoodNameEvening3().toLowerCase().contains(text.toLowerCase())){

                searchMeals.add(historyMeal);
            }

            adapter.searchDataMeal(searchMeals);
        }
    }
}