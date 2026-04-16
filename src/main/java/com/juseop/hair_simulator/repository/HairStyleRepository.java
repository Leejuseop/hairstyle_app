package com.juseop.hair_simulator.repository;

import com.juseop.hair_simulator.domain.HairStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HairStyleRepository extends JpaRepository<HairStyle, Long> {
    List<HairStyle> findByHairGender(String gender);
}
