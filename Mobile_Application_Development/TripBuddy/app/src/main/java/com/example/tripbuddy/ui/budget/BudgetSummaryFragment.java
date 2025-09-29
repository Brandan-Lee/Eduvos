package com.example.tripbuddy.ui.budget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.back_end.bll.TripBLL;
import com.example.tripbuddy.back_end.dal.dao.BudgetDAO;
import com.example.tripbuddy.back_end.dal.dao.TripActivityDAO;
import com.example.tripbuddy.back_end.dal.dao.TripDAO;
import com.example.tripbuddy.back_end.dal.dto.TripDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.back_end.shared_preferences.SharedPreferencesManager;
import com.example.tripbuddy.databinding.FragmentBudgetSummaryBinding;
import com.example.tripbuddy.ui.Models.TripBudgetItem;

import java.util.ArrayList;

public class BudgetSummaryFragment extends Fragment {

    private BudgetSummaryViewModel budgetSummaryViewModel;
    private FragmentBudgetSummaryBinding binding;
    private RecyclerView budgetSummaryRv;
    private BudgetListAdapter budgetAdapter;
    private ArrayList<TripBudgetItem> budgetList;
    private TripBLL tripBLL;
    private TripDAO tripDAO;
    private BudgetDAO budgetDAO;
    private TripActivityDAO tripActivityDAO;
    private MyDatabaseHelper databaseHelper;
    private SharedPreferencesManager sharedPreferencesManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        budgetSummaryViewModel = new ViewModelProvider(this).get(BudgetSummaryViewModel.class);
        binding = FragmentBudgetSummaryBinding.inflate(inflater, container, false);

        findBindings();

        View root = binding.getRoot();

        sharedPreferencesManager = new SharedPreferencesManager(requireContext());
        databaseHelper = new MyDatabaseHelper(requireContext());
        tripDAO = new TripDAO(databaseHelper);
        tripActivityDAO = new TripActivityDAO(databaseHelper);
        budgetDAO = new BudgetDAO(databaseHelper);
        tripBLL = new TripBLL(databaseHelper, tripDAO, budgetDAO, tripActivityDAO);

        budgetList = new ArrayList<>();
        budgetAdapter = new BudgetListAdapter(requireContext(), budgetList);
        budgetSummaryRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        budgetSummaryRv.setAdapter(budgetAdapter);


        handleRecyclerView();

        return root;
    }

    private void findBindings() {

        budgetSummaryRv = binding.tripsBudgetRv;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleRecyclerView() {

        int userId = sharedPreferencesManager.getUserId();
        ArrayList<TripDTO> trips = tripBLL.getTripsForUser(userId);

        ArrayList<TripBudgetItem> tripBudgetItems = new ArrayList<>();
        double totalFinalBudget = 0.0;

        for (TripDTO trip : trips) {
            double tripBudget = tripBLL.getBudgetForTrip(trip.getTripId());
            totalFinalBudget += tripBudget;
            tripBudgetItems.add(new TripBudgetItem(trip.getDestination(), tripBudget));
        }

        budgetList.clear();
        budgetList.addAll(tripBudgetItems);

        budgetAdapter.notifyDataSetChanged();

        binding.totalFinalBudgetTv.setText("Total Final Budget: R" + totalFinalBudget);

    }

}
