package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class mealPlanFragment extends Fragment{

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    //Recycler view
    RecyclerView recycleViewMeal;
    ArrayList<HistoryMeal> meals;
    AdapterMeal adapter;
    MealPlanViewModel viewModel;

    //search
    androidx.appcompat.widget.SearchView etSearch;

    //live retrieving/updating meals in recycler view
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MealPlanViewModel.class);
        viewModel.getItems().observe(this, new Observer<List<HistoryMeal>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<HistoryMeal> items) {
                meals.clear();
                meals.addAll(items);
                adapter.notifyDataSetChanged();
            }
        });
        meals = new ArrayList<>();
    }

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

        //set color for search view
        SpannableString hintText = new SpannableString("Search...");
        hintText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, hintText.length(), 0);
        etSearch.setQueryHint(hintText);

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
        recycleViewMeal.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterMeal(getActivity(), meals, getActivity());
        recycleViewMeal.setAdapter(adapter);

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
            @SuppressLint("NotifyDataSetChanged")
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