package com.example.tripbuddy.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;
import com.example.tripbuddy.back_end.bll.TripBLL;
import com.example.tripbuddy.back_end.dal.dao.BudgetDAO;
import com.example.tripbuddy.back_end.dal.dao.TripActivityDAO;
import com.example.tripbuddy.back_end.dal.dao.TripDAO;
import com.example.tripbuddy.back_end.dal.dto.TripDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.back_end.shared_preferences.SharedPreferencesManager;
import com.example.tripbuddy.databinding.FragmentHomeBinding;
import com.example.tripbuddy.ui.Models.TripItem;
import com.example.tripbuddy.ui.budget.BudgetSummaryFragment;
import com.example.tripbuddy.ui.create_memory.CreateMemoryFragment;
import com.example.tripbuddy.ui.create_memory.TripListAdapter;
import com.example.tripbuddy.ui.plan_trip.PlanTripFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private Button planTripBtn, createMemoryBtn, budgetBtn, viewGalleryBtn, logOutBtn;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = NavHostFragment.findNavController(this);

        findButtonBindings();
        handleButtons();

        return root;

    }

    private void findButtonBindings() {

        planTripBtn = binding.planTripBtn;
        createMemoryBtn = binding.createMemoryBtn;
        budgetBtn = binding.budgetBtn;
        viewGalleryBtn = binding.viewGalleryBtn;

    }

    private void handleButtons() {

        planTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_plan_trip);
            }
        });

        createMemoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_create_memory);
            }
        });

        budgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_budget);
            }
        });

        viewGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_view_gallery);
            }
        });

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;

    }

//    @Override
//    public void createMemoryClick(int position) {
//        int userId = sharedPreferencesManager.getUserId();
//        ArrayList<TripDTO> trips = tripBLL.getTripsForUser(userId);
//
//        if (position >= 0 && position < trips.size()) {
//            TripDTO selectedTrip = trips.get(position);
//            int tripId = selectedTrip.getTripId();
//
//            Bundle bundle = new Bundle();
//            bundle.putInt("tripId", tripId);
//
//            // Use fragment view for NavController
//            NavController navController = NavHostFragment.findNavController(this);
//            navController.navigate(R.id.action_createMemoryFragment_to_createMemoryFormFragment, bundle);
//        }
//    }
//
//    private void handleRecyclerView() {
//
//        int userId = sharedPreferencesManager.getUserId();
//        Log.d("CreateMemoryFragment", "userId = " + userId);
//
//        ArrayList<TripDTO> trips = tripBLL.getTripsForUser(userId);
//
//        for (TripDTO trip : trips) {
//            tripsList.add(new TripItem(trip.getDestination()));
//        }
//
//        tripAdapter.notifyDataSetChanged();
//
//    }


}