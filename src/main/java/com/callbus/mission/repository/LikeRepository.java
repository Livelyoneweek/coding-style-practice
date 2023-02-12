package com.callbus.mission.repository;

import com.callbus.mission.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByPostIdAndMemberId(Long postId,Long memberId);

}
