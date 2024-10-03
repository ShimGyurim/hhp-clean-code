package io.hhplus.cleancode.domain.repository;

import io.hhplus.cleancode.domain.entity.Student;
import io.hhplus.cleancode.domain.entity.Sugang;
import io.hhplus.cleancode.infrastructure.entity.StudentEntity;
import jakarta.persistence.LockModeType;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface StudentRepository  {

    <S extends Student> S save(S pojo);
    Optional<Student> findById(Long aLong) throws Exception;
}
