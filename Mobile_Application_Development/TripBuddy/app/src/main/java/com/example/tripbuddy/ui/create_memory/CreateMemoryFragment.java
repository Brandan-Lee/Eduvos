package com.example.tripbuddy.ui.create_memory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tripbuddy.R;
import com.example.tripbuddy.back_end.bll.TripBLL;
import com.example.tripbuddy.back_end.dal.dao.BudgetDAO;
import com.example.tripbuddy.back_end.dal.dao.TripActivityDAO;
import com.example.tripbuddy.back_end.dal.dao.TripDAO;
import com.example.tripbuddy.back_end.dal.dto.TripDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.back_end.shared_preferences.SharedPreferencesManager;
import com.example.tripbuddy.databinding.FragmentCreateMemoryBinding;
import com.example.tripbuddy.ui.Models.TripItem;

import java.util.ArrayList;

public class CreateMemoryFragment extends Fragment implements TripListAdapter.OnCreateMemoryClickListener {

    private CreateMemoryViewModel createMemoryViewModel;
    private FragmentCreateMemoryBinding binding;
    private RecyclerView tripsRv;
    private TripListAdapter tripAdapter;
    private SharedPreferencesManager sharedPreferencesManager;
    private ArrayList<TripItem> tripsList;
    private TripBLL tripBLL;
    private TripDAO tripDAO;
    private BudgetDAO budgetDAO;
    private TripActivityDAO tripActivityDAO;
    private MyDatabaseHelper databaseHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        createMemoryViewModel = new ViewModelProvider(this).get(CreateMemoryViewModel.class);
        binding = FragmentCreateMemoryBinding.inflate(inflater, container, false);
        tripsRv = binding.tripsRv;
        View root = binding.getRoot();

        sharedPreferencesManager = new SharedPreferencesManager(requireContext());
        databaseHelper = new MyDatabaseHelper(requireContext());
        tripDAO = new TripDAO(databaseHelper);
        tripActivityDAO = new TripActivityDAO(databaseHelper);
        budgetDAO = new BudgetDAO(databaseHelper);
        tripBLL = new TripBLL(databaseHelper, tripDAO, budgetDAO, tripActivityDAO);

        tripsList = new ArrayList<>();
        tripAdapter = new TripListAdapter(requireContext(), tripsList, this);
        tripsRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        tripsRv.setAdapter(tripAdapter);

        handleRecyclerView();


        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleRecyclerView() {

        int userId = sharedPreferencesManager.getUserId();
        Log.d("CreateMemoryFragment", "userId = " + userId);

        ArrayList<TripDTO> trips = tripBLL.getTripsForUser(userId);

        for (TripDTO trip : trips) {
            tripsList.add(new TripItem(trip.getDestination()));
        }

        tripAdapter.notifyDataSetChanged();

    }


    @Override
    public void createMemoryClick(int position) {
        int userId = sharedPreferencesManager.getUserId();
        ArrayList<TripDTO> trips = tripBLL.getTripsForUser(userId);

        if (position >= 0 && position < trips.size()) {
            TripDTO selectedTrip = trips.get(position);
            int tripId = selectedTrip.getTripId();

            Bundle bundle = new Bundle();
            bundle.putInt("tripId", tripId);

            // Use fragment view for NavController
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_createMemoryFragment_to_createMemoryFormFragment, bundle);
        }
    }


}
