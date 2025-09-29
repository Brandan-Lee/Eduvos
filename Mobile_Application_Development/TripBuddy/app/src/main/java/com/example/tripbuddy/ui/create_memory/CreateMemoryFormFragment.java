package com.example.tripbuddy.ui.create_memory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tripbuddy.R;
import com.example.tripbuddy.back_end.bll.MemoryBLL;
import com.example.tripbuddy.back_end.dal.dao.MediaDAO;
import com.example.tripbuddy.back_end.dal.dao.MemoryDAO;
import com.example.tripbuddy.back_end.dal.dto.MemoryDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.databinding.FragmentCreateMemoryFormBinding;

import java.util.ArrayList;

public class CreateMemoryFormFragment extends Fragment {

    private CreateMemoryFormViewModel createMemoryFormViewModel;
    private FragmentCreateMemoryFormBinding binding;
    private ImageButton addPhotoBtn;
    private Button saveBtn, trackTrips;
    private Spinner musicSpinner;
    private Uri imageUri;
    private int tripId;
    private String chosenSong;
    private MemoryBLL memoryBLL;
    private MyDatabaseHelper databaseHelper;

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        createMemoryFormViewModel = new ViewModelProvider(this).get(CreateMemoryFormViewModel.class);
        binding = FragmentCreateMemoryFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        databaseHelper = new MyDatabaseHelper(requireContext());
        MemoryDAO memoryDAO = new MemoryDAO(databaseHelper.getWritableDatabase());
        MediaDAO mediaDAO = new MediaDAO(databaseHelper.getWritableDatabase());
        memoryBLL = new MemoryBLL(memoryDAO, mediaDAO);

        Bundle args = getArguments();

        if (args != null) {

            tripId = args.getInt("tripId", -1);

            if (tripId != -1) {
                this.tripId = tripId;
            }

        }

        findBindings();
        handleButtons();
        handleSpinner();


        return root;
    }

    private void findBindings() {

        addPhotoBtn = binding.addPhotoBtn;
        saveBtn = binding.saveMemoryBtn;
        trackTrips = binding.trackTripsBtn;
        musicSpinner = binding.musicSpinner;

    }

    private void handleButtons() {

        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryLauncher.launch(new String[]{"image/*"});
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMemory();
            }
        });


        trackTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_create_memory);
            }
        });

    }

    private void handleSpinner() {

        ArrayList<String> musicList = new ArrayList<>();
        musicList.add("Select the song you want to associate with this trip");
        musicList.add("Background Piano");
        musicList.add("Nostalgic Memories");
        musicList.add("Emotional Piano");
        musicList.add("Seasonal Piano");
        musicList.add("Relaxing Ambiance");

        ArrayAdapter<String> musicAdapter = new ArrayAdapter<String>(requireContext(), R.layout.spinner_list, musicList);
        musicAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        binding.musicSpinner.setAdapter(musicAdapter);

        musicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String item = parent.getItemAtPosition(position).toString();

                    if (item.equals("Background Piano")) {
                        chosenSong = "background_piano";
                    } else if (item.equals("Nostalgic Memories")) {
                        chosenSong = "nostalgic_memories";
                    } else if (item.equals("Emotional Piano")) {
                        chosenSong = "emotional_piano";
                    } else if (item.equals("Seasonal Piano")) {
                        chosenSong = "seasonal_piano";
                    } else if (item.equals("Relaxing Ambiance")) {
                        chosenSong = "relaxing_ambiance";
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                musicSpinner.setSelection(0);
            }
        });

    }


    private ActivityResultLauncher<String[]> galleryLauncher = registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {

        if (uri != null) {
            final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
            requireContext().getContentResolver().takePersistableUriPermission(uri, takeFlags);

            imageUri = uri;

        }

    });


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void saveMemory() {

        String memoryText = binding.memoryTextEt.getText().toString();
        String photoUri = "";

        if (imageUri != null) {
            photoUri = imageUri.toString();
        } else {
            photoUri = "";
        }

        if (memoryText.isEmpty() && imageUri == null && chosenSong == null) {
            Toast.makeText(getContext(), "Please add some content to your memory", Toast.LENGTH_SHORT).show();
        }

        MemoryDTO newMemory = new MemoryDTO(
                0,
                tripId,
                memoryText,
                chosenSong,
                photoUri
        );

        long memoryId = memoryBLL.saveMemory(newMemory);

        if (memoryId != -1) {

            Toast.makeText(getContext(), "Memory saved successfully", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putInt("memoryId", (int)memoryId);

            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_createMemoryFormFragment_to_viewMemoryFragment, bundle);


        } else {
            Toast.makeText(getContext(), "Memory save failed", Toast.LENGTH_SHORT).show();
        }

    }

}
