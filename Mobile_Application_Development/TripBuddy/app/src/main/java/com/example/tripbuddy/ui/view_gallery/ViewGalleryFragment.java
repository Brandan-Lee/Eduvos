package com.example.tripbuddy.ui.view_gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.back_end.bll.TripBLL;
import com.example.tripbuddy.back_end.dal.dao.BudgetDAO;
import com.example.tripbuddy.back_end.dal.dao.TripActivityDAO;
import com.example.tripbuddy.back_end.dal.dao.TripDAO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.back_end.shared_preferences.SharedPreferencesManager;
import com.example.tripbuddy.databinding.FragmentViewGalleryBinding;
import com.example.tripbuddy.ui.Models.BaseGalleryItem;
import com.example.tripbuddy.ui.Models.ViewGalleryItem;


import java.util.ArrayList;


public class ViewGalleryFragment extends Fragment implements ViewGalleryListAdapter.onGalleryImageClickListener {

    private ViewGalleryViewModel viewGalleryViewModel;
    private FragmentViewGalleryBinding binding;
    private RecyclerView mediaGalleryRv;
    private ViewGalleryListAdapter viewGalleryListAdapter;
    private ArrayList<BaseGalleryItem> galleryItems;
    private int tripId;
    private TripBLL tripBLL;
    private TripDAO tripDAO;
    private TripActivityDAO tripActivityDAO;
    private BudgetDAO budgetDAO;
    private MyDatabaseHelper databaseHelper;
    private SharedPreferencesManager sharedPreferencesManager;
    int userId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGalleryViewModel viewGalleryViewModel = new ViewModelProvider(this).get(ViewGalleryViewModel.class);
        binding = FragmentViewGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        galleryItems = new ArrayList<>();

        databaseHelper = new MyDatabaseHelper(getContext());
        tripDAO = new TripDAO(databaseHelper);
        tripActivityDAO = new TripActivityDAO(databaseHelper);
        budgetDAO = new BudgetDAO(databaseHelper);
        tripBLL = new TripBLL(databaseHelper, tripDAO, budgetDAO, tripActivityDAO);

        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        userId = sharedPreferencesManager.getUserId();

        findBindings();
        viewGalleryListAdapter = new ViewGalleryListAdapter(galleryItems, this);
        mediaGalleryRv.setAdapter(viewGalleryListAdapter);

        setUpGridLayout();
        mediaGalleryRv.addItemDecoration(new GridDecoration(1, 50, true));
        loadGalleryItems(userId);
    }

    private void findBindings() {
        mediaGalleryRv = binding.mediaGalleryRv;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadGalleryItems(int userId) {

        galleryItems.clear();
        galleryItems.addAll(tripBLL.getAllGalleryItemsForUser(userId));
        viewGalleryListAdapter.notifyDataSetChanged();

    }

    private void setUpGridLayout() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {

                int viewType = viewGalleryListAdapter.getItemViewType(position);
                if (viewType == BaseGalleryItem.TYPE_HEADER) {
                    return 2;
                } else {
                    return 1;
                }

            }
        });

        mediaGalleryRv.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void galleryImageClick(int position) {

        BaseGalleryItem item = galleryItems.get(position);

        if (item instanceof ViewGalleryItem) {

            ViewGalleryItemFull image = (ViewGalleryItemFull) item;
            String imagePath = image.getFilePath();

            Intent intent = new Intent(getContext(), FullScreenActivity.class);
            intent.putExtra("path", imagePath);
            startActivity(intent);

        }

    }

}
