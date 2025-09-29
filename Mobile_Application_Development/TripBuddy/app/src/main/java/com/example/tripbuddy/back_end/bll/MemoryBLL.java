package com.example.tripbuddy.back_end.bll;

import com.example.tripbuddy.back_end.dal.dao.MediaDAO;
import com.example.tripbuddy.back_end.dal.dao.MemoryDAO;
import com.example.tripbuddy.back_end.dal.dto.MemoryDTO;

import java.util.ArrayList;

public class MemoryBLL {

    private MemoryDAO memoryDAO;
    private MediaDAO mediaDAO;

    public MemoryBLL(MemoryDAO memoryDAO, MediaDAO mediaDAO) {
        this.memoryDAO = memoryDAO;
        this.mediaDAO = mediaDAO;
    }

    public long saveMemory(MemoryDTO memoryDTO) {

        long memoryId = -1;

        try {
            memoryId = memoryDAO.saveMemory(memoryDTO);
        } catch (Exception e) {
            return -1;
        }

        if (memoryId == -1) {
            return -1;
        }

        if (memoryDTO.getImageUri() != null && !memoryDTO.getImageUri().isEmpty()) {
            long mediaId = mediaDAO.saveMedia(
                    memoryId,
                    memoryDTO.getTripId(),
                    memoryDTO.getImageUri(),
                    "image"
            );

            if (mediaId == -1) {
                return -1;
            }

        }

        return memoryId;


    }

    public MemoryDTO getMemoryById(int memoryId) {

        return memoryDAO.getMemoryById(memoryId);

    }

    public String getPhotoUri(int memoryId) {

        return memoryDAO.getPhotoUri(memoryId);

    }
}
