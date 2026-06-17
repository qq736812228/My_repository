package com.obai.platform.repository;

import com.obai.platform.entity.Invitation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByInviterUserIdOrderByCreatedAtDesc(Long inviterUserId);
}
