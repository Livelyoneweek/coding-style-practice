package com.callbus.mission.service;

import com.callbus.mission.entity.Like;
import com.callbus.mission.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;

    public Like save(Like like) {
        return likeRepository.save(like);
    }

    public void deleteById(Long id) {
        likeRepository.deleteById(id);
    }


}
