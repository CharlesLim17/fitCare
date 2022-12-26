package com.example.fitcare_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMeal extends RecyclerView.Adapter<AdapterMeal.MealViewHolder>{

    Context context;
    ArrayList<HistoryMeal> meals;

    public AdapterMeal(Context context, ArrayList<HistoryMeal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public AdapterMeal.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_item,parent,false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMeal.MealViewHolder holder, int position) {
        HistoryMeal historyMeal = meals.get(position);

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

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder{

        TextView txtFoodDate, txtFoodNameMorning1, txtFoodNameMorning2, txtFoodNameMorning3, txtFoodNameAfternoon1, txtFoodNameAfternoon2,
                txtFoodNameAfternoon3, txtFoodNameEvening1, txtFoodNameEvening2, txtFoodNameEvening3;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);

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
