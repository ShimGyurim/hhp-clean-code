package io.hhplus.cleancode.infrastructure.repository;

import io.hhplus.cleancode.domain.entity.Student;
import io.hhplus.cleancode.domain.repository.StudentRepository;
import io.hhplus.cleancode.infrastructure.entity.StudentEntity;
import io.hhplus.cleancode.infrastructure.mapper.DDDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

//    @Autowired
//    StudentMapper studentMapper;

    @Autowired
    DDDMapper dddMapper;

    private final StudentJpaRepository studentJpaRepository;

    public StudentRepositoryImpl(StudentJpaRepository studentJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
    }


    public <S extends Student> S save(S pojo) {
        StudentEntity studentEntity = studentJpaRepository.save(dddMapper.studentToEntity(pojo));
        return (S) dddMapper.studentToPojo(studentEntity);
    }

    public Optional<Student> findById(Long aLong) throws Exception{
        Optional<StudentEntity> studentEntityOptional = Optional.ofNullable(studentJpaRepository.findById(aLong).orElseThrow(
                () -> new Exception("데이터 검색 오류.")
        ));
        StudentEntity StudentEntity = studentEntityOptional.get();
        return Optional.of(dddMapper.studentToPojo(StudentEntity));
    }
}
