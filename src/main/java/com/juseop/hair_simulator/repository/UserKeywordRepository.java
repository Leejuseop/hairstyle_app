package com.juseop.hair_simulator.repository;

import com.juseop.hair_simulator.domain.User;
import com.juseop.hair_simulator.domain.UserHistory;
import com.juseop.hair_simulator.domain.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {
    public List<UserKeyword> findByUser(User user);
    List<UserKeyword> findByUserHistory(UserHistory userHistory);

}
