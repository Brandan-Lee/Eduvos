package com.example.tripbuddy.ui.create_memory;

import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.tripbuddy.databinding.FragmentViewMemoryBinding;

public class ViewMemoryFragment extends Fragment {

    private ViewMemoryViewModel viewMemoryViewModel;
    private FragmentViewMemoryBinding binding;
    private Button backToCreateBtn;
    private ImageView memoryImageIv;
    private String memoryText;
    private MyDatabaseHelper databaseHelper;
    private MemoryDAO memoryDAO;
    private MediaDAO mediaDAO;
    private int memoryId;
    private MemoryBLL memoryBLL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewMemoryViewModel = new ViewModelProvider(this).get(ViewMemoryViewModel.class);
        binding = FragmentViewMemoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        findBindings();

        databaseHelper = new MyDatabaseHelper(requireContext());
        memoryDAO = new MemoryDAO(databaseHelper.getWritableDatabase());
        mediaDAO = new MediaDAO(databaseHelper.getWritableDatabase());
        memoryBLL = new MemoryBLL(memoryDAO, mediaDAO);

        Bundle args = getArguments();
        if (args != null) {
            memoryId = args.getInt("memoryId", -1);

            if (memoryId != -1) {
                displayMemory(memoryId);
            } else {
                Toast.makeText(getContext(), "Error: No memory provided", Toast.LENGTH_SHORT).show();
            }
        }

        binding.backToCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_create_memory);
            }
        });

        return root;

    }

    private void findBindings() {

        backToCreateBtn = binding.backToCreateBtn;
        memoryImageIv = binding.memoryImageIv;

    }

    private void displayMemory(int memoryId) {

        MemoryDTO memory = memoryBLL.getMemoryById(memoryId);
        String photoUriString = memoryBLL.getPhotoUri(memoryId);

        if (memory != null) {

            binding.memoryTextTv.setText(memory.getText());

            if (photoUriString != null && !photoUriString.isEmpty()) {
                binding.memoryImageIv.setImageURI(Uri.parse(photoUriString));
            }

        } else {
            Toast.makeText(getContext(), "Error: Memory not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;

        if (databaseHelper != null) {
            databaseHelper.close();
        }

    }
}
