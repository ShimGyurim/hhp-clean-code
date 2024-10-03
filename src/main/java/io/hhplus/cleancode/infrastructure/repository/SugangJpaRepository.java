package io.hhplus.cleancode.infrastructure.repository;

import io.hhplus.cleancode.infrastructure.entity.SugangEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SugangJpaRepository extends JpaRepository<SugangEntity, Long> {

    @Override
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends SugangEntity> S save(S entity);

    @Override
    Optional<SugangEntity> findById(Long aLong);
}
