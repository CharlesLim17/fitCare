package com.example.fitcare_java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMeal extends RecyclerView.Adapter<AdapterMeal.MealViewHolder>{

    Context context;
    ArrayList<HistoryMeal> meals;
    private FragmentActivity fragmentActivity;

    public AdapterMeal(Context context, ArrayList<HistoryMeal> meals, FragmentActivity fragmentActivity) {
        this.context = context;
        this.meals = meals;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public AdapterMeal.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_item,parent,false);
        return new MealViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterMeal.MealViewHolder holder, int position) {
        HistoryMeal historyMeal = meals.get(position);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment mealPlanEditFrag = new mealPlanEditFragment();
                FragmentTransaction fm = fragmentActivity.getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frameLayout, mealPlanEditFrag, null).addToBackStack(null).commit();

                passValues(mealPlanEditFrag, fm, historyMeal);
            }
        });

        holder.txtFoodDate.setText(historyMeal.getFoodDate());
        holder.txtFoodNameMorning1.setText("• " + historyMeal.getFoodNameMorning1());
        holder.txtFoodNameMorning2.setText("• " + historyMeal.getFoodNameMorning2());
        holder.txtFoodNameMorning3.setText("• " + historyMeal.getFoodNameMorning3());
        holder.txtFoodNameAfternoon1.setText("• " + historyMeal.getFoodNameAfternoon1());
        holder.txtFoodNameAfternoon2.setText("• " + historyMeal.getFoodNameAfternoon2());
        holder.txtFoodNameAfternoon3.setText("• " + historyMeal.getFoodNameAfternoon3());
        holder.txtFoodNameEvening1.setText("• " + historyMeal.getFoodNameEvening1());
        holder.txtFoodNameEvening2.setText("• " + historyMeal.getFoodNameEvening2());
        holder.txtFoodNameEvening3.setText("• " + historyMeal.getFoodNameEvening3());
    }

    public void passValues(Fragment mealPlanEditFrag, FragmentTransaction fm, HistoryMeal historyMeal){
        int year = historyMeal.getYear();
        int month = historyMeal.getMonth();
        int day = historyMeal.getDay();
        String foodDate = historyMeal.getFoodDate();
        String foodNameMorning1 = historyMeal.getFoodNameMorning1();
        String foodNameMorning2 = historyMeal.getFoodNameMorning2();
        String foodNameMorning3 = historyMeal.getFoodNameMorning3();
        String foodNameAfternoon1 = historyMeal.getFoodNameAfternoon1();
        String foodNameAfternoon2 = historyMeal.getFoodNameAfternoon2();
        String foodNameAfternoon3 = historyMeal.getFoodNameAfternoon3();
        String foodNameEvening1 = historyMeal.getFoodNameEvening1();
        String foodNameEvening2 = historyMeal.getFoodNameEvening2();
        String foodNameEvening3 = historyMeal.getFoodNameEvening3();

        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        bundle.putString("foodDate", foodDate);
        bundle.putString("foodNameMorning1", foodNameMorning1);
        bundle.putString("foodNameMorning2", foodNameMorning2);
        bundle.putString("foodNameMorning3", foodNameMorning3);
        bundle.putString("foodNameAfternoon1", foodNameAfternoon1);
        bundle.putString("foodNameAfternoon2", foodNameAfternoon2);
        bundle.putString("foodNameAfternoon3", foodNameAfternoon3);
        bundle.putString("foodNameEvening1", foodNameEvening1);
        bundle.putString("foodNameEvening2", foodNameEvening2);
        bundle.putString("foodNameEvening3", foodNameEvening3);
        mealPlanEditFrag.setArguments(bundle);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void searchDataMeal(ArrayList<HistoryMeal> searchMeals){
        meals = searchMeals;
        notifyDataSetChanged();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder{

        ImageView btnEdit;
        TextView txtFoodDate, txtFoodNameMorning1, txtFoodNameMorning2, txtFoodNameMorning3, txtFoodNameAfternoon1, txtFoodNameAfternoon2,
                txtFoodNameAfternoon3, txtFoodNameEvening1, txtFoodNameEvening2, txtFoodNameEvening3;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

            btnEdit = (ImageView) itemView.findViewById(R.id.btnEdit);
            txtFoodDate = itemView.findViewById(R.id.txtFoodDate);
            txtFoodNameMorning1 = itemView.findViewById(R.id.txtFoodNameMorning1);
            txtFoodNameMorning2 = itemView.findViewById(R.id.txtFoodNameMorning2);
            txtFoodNameMorning3 = itemView.findViewById(R.id.txtFoodNameMorning3);
            txtFoodNameAfternoon1 = itemView.findViewById(R.id.txtFoodNameAfternoon1);
            txtFoodNameAfternoon2 = itemView.findViewById(R.id.txtFoodNameAfternoon2);
            txtFoodNameAfternoon3 = itemView.findViewById(R.id.txtFoodNameAfternoon3);
            txtFoodNameEvening1 = itemView.findViewById(R.id.txtFoodNameEvening1);
            txtFoodNameEvening2 = itemView.findViewById(R.id.txtFoodNameEvening2);
            txtFoodNameEvening3 = itemView.findViewById(R.id.txtFoodNameEvening3);

        }
    }
}
