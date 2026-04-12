package com.juseop.hair_simulator.repository;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    UserHistory findFirstByUserOrderByPkIdDesc(User user);
}
