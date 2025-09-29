package com.example.tripbuddy.ui.plan_trip;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;
import com.example.tripbuddy.back_end.bll.TripBLL;
import com.example.tripbuddy.back_end.dal.dao.BudgetDAO;
import com.example.tripbuddy.back_end.dal.dao.TripActivityDAO;
import com.example.tripbuddy.back_end.dal.dao.TripDAO;
import com.example.tripbuddy.back_end.shared_preferences.SharedPreferencesManager;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.databinding.FragmentPlanTripBinding;
import com.example.tripbuddy.ui.Models.SelectedItem;
import com.example.tripbuddy.ui.Models.TripBudget;

import java.util.ArrayList;
import java.util.Calendar;

public class PlanTripFragment extends Fragment implements SelectedItemAdapter.OnItemClickListener {

    private PlanTripViewModel planTripViewModel;
    private FragmentPlanTripBinding binding;

    private Button btnStartDate, btnEndDate, btnSaveTrip, btnConfirmTrip;
    private Spinner activitiesSpinner;
    private DatePickerDialog datePickerDialog;
    private ArrayList<Integer> costsList;
    private ArrayList<SelectedItem> selectedItems;
    private LinearLayout budgetSummaryLayout;
    private RecyclerView selectedActivitiesRv;
    private SelectedItemAdapter selectedItemAdapter;
    private TripBLL tripBLL;
    private TripDAO tripDAO;
    private BudgetDAO budgetDAO;
    private TripActivityDAO tripActivityDAO;
    private MyDatabaseHelper databaseHelper;
    private SharedPreferencesManager sharedPreferencesManager;
    final double sightSeeingCost = 200.00;
    final double hikingCost = 55.00;
    final double diningCost = 500.00;
    final double museumToursCost = 300.00;
    final double beachDayCost = 100.00;
    private int userId;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        planTripViewModel = new ViewModelProvider(this).get(PlanTripViewModel.class);

        binding = FragmentPlanTripBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        findBindings();

        btnStartDate.setText(getTodayDate());
        btnEndDate.setText(getTodayDate());

        selectedItems = new ArrayList<>();
        selectedItemAdapter = new SelectedItemAdapter(requireContext(), selectedItems, this);
        selectedActivitiesRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        selectedActivitiesRv.setAdapter(selectedItemAdapter);
        selectedActivitiesRv.setVisibility(View.GONE);

        budgetSummaryLayout.setVisibility(View.GONE);

        costsList = new ArrayList<>();

        Context context = requireContext();

        databaseHelper = new MyDatabaseHelper(context);
        tripDAO = new TripDAO(databaseHelper);
        budgetDAO = new BudgetDAO(databaseHelper);
        tripActivityDAO = new TripActivityDAO(databaseHelper);
        tripBLL = new TripBLL(databaseHelper, tripDAO, budgetDAO, tripActivityDAO);

        sharedPreferencesManager = new SharedPreferencesManager(getContext());

        userId = sharedPreferencesManager.getUserId();

        handleButtons();
        handleSpinner();

        return root;

    }

    private void findBindings() {

        btnStartDate = binding.startDateBtn;
        btnEndDate = binding.endDateBtn;
        btnSaveTrip = binding.saveTripBtn;
        btnConfirmTrip = binding.confirmTripBtn;
        activitiesSpinner = binding.selectedActivitiesSpinner;
        budgetSummaryLayout = binding.budgetSummary;
        selectedActivitiesRv = binding.selectedActivitiesRv;

    }

    private void handleButtons() {

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(btnStartDate);
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(btnEndDate);
            }
        });

        btnSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budgetSummaryLayout.setVisibility(View.VISIBLE);

                String mealsFees = binding.mealsFeesEt.getText().toString();
                String customExpenses = binding.customExpensesEt.getText().toString();
                TripBudget budget = tripBLL.calculateBudget(costsList, mealsFees, customExpenses, userId);
                binding.subTotalTv.setText("Sub Total: R" + budget.getSubTotal());
                binding.discountTv.setText("Discount: R" + budget.getDiscountAmount());
                binding.finalBudgetTv.setText("Final Budget: R" + budget.getFinalBudget());

            }
        });


        btnConfirmTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrip();
            }
        });

    }

    private void handleSpinner() {

        ArrayList<String> activitiesList = new ArrayList<>();
        activitiesList.add("Select your activities: ");
        activitiesList.add("Sightseeing: R" + sightSeeingCost);
        activitiesList.add("Hiking: R" + hikingCost);
        activitiesList.add("Dining: R" + diningCost);
        activitiesList.add("Museum Tours: R" + museumToursCost);
        activitiesList.add("Beach Day: R" + beachDayCost);


        ArrayAdapter<String> activitiesAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_list, activitiesList);
        activitiesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        activitiesSpinner.setAdapter(activitiesAdapter);

        activitiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {

                    String item = parent.getItemAtPosition(position).toString();
                    handleSelectedItemsRecycler(item);
                    activitiesSpinner.setSelection(0);
                    costsList.add(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                activitiesSpinner.setSelection(0);
            }
        });

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;

    }

    //    Implement Date popup when user presses date buttons
//----------------------------------------------------------------
    private String getTodayDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {

        if (month == 1)
            return "Jan";
        if (month == 2)
            return "Feb";
        if (month == 3)
            return "Mar";
        if (month == 4)
            return "Apr";
        if (month == 5)
            return "May";
        if (month == 6)
            return "Jun";
        if (month == 7)
            return "Jul";
        if (month == 8)
            return "Aug";
        if (month == 9)
            return "Sep";
        if (month == 10)
            return "Oct";
        if (month == 11)
            return "Nov";
        if (month == 12)
            return "Dec";

        return "Jan";

    }

    private void initDatePicker(Button btnTarget) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnTarget.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(requireContext(), dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    private void openDatePicker(View view) {

        if (view instanceof Button) {
            initDatePicker((Button) view);
        }

    }
//----------------------------------------------------------------

    private void handleSelectedItemsRecycler(String item) {

        if (selectedActivitiesRv.getVisibility() == View.GONE) {
            selectedActivitiesRv.setVisibility(View.VISIBLE);
        }

        selectedItems.add(new SelectedItem(item));
        selectedItemAdapter.notifyItemInserted(selectedItems.size() - 1);

    }

    @Override
    public void onDeleteClick(int position) {

        if (position >= 0 && position < costsList.size()) {
            costsList.remove(position);
        }

        selectedItems.remove(position);
        selectedItemAdapter.notifyItemRemoved(position);

        String mealsFees = binding.mealsFeesEt.getText().toString();
        String customExpenses = binding.customExpensesEt.getText().toString();

        TripBudget budget = tripBLL.calculateBudget(costsList, mealsFees, customExpenses, userId);
        binding.subTotalTv.setText("Sub Total: R" + budget.getSubTotal());
        binding.discountTv.setText("Discount: R" + budget.getDiscountAmount());
        binding.finalBudgetTv.setText("Final Budget: R" + budget.getFinalBudget());
        budgetSummaryLayout.setVisibility(View.GONE);

        if (selectedItems.isEmpty()) {
            selectedActivitiesRv.setVisibility(View.GONE);
            budgetSummaryLayout.setVisibility(View.GONE);
        }

    }

    private void saveTrip() {

        String destination = binding.destinationEt.getText().toString();
        String startDate = btnStartDate.getText().toString();
        String endDate = btnEndDate.getText().toString();
        String notes = binding.notesEt.getText().toString();
        String customExpenses = binding.customExpensesEt.getText().toString();
        String mealsFees = binding.mealsFeesEt.getText().toString();

        if (validateFields(destination, startDate, endDate)) {
            boolean success = tripBLL.saveTrip(userId, destination, startDate, endDate, notes, customExpenses, mealsFees, selectedItems, costsList);

            if (success) {
                Toast.makeText(requireContext(), "Trip saved successfully", Toast.LENGTH_SHORT).show();
                clearInputs();
            } else {
                Toast.makeText(requireContext(), "Error saving trip", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean validateFields (String destination, String startDate, String endDate){

        if (destination.isEmpty()) {
            binding.destinationEt.setError("Please enter a destination");
            return false;
        }

        if (startDate.isEmpty()) {
            binding.startDateBtn.setError("Please select a start date");
            return false;
        }

        if (endDate.isEmpty()) {
            binding.endDateBtn.setError("Please select an end date");
            return false;
        }

        return true;

    }

    private void clearInputs() {

        binding.destinationEt.setText("");
        btnStartDate.setText(getTodayDate());
        btnEndDate.setText(getTodayDate());
        binding.notesEt.setText("");
        binding.customExpensesEt.setText("");
        selectedItems.clear();
        activitiesSpinner.setSelection(0);
        binding.mealsFeesEt.setText("");
        selectedItemAdapter.notifyDataSetChanged();
        selectedActivitiesRv.setVisibility(View.GONE);
        budgetSummaryLayout.setVisibility(View.GONE);
        costsList.clear();

    }

}
