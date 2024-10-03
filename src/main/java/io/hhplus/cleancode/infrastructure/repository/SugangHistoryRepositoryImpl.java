package io.hhplus.cleancode.infrastructure.repository;

//import org.springframework.data.jpa.repository.JpaRepository;

import io.hhplus.cleancode.domain.entity.SugangHistory;
import io.hhplus.cleancode.domain.repository.SugangHistoryRepository;
import io.hhplus.cleancode.infrastructure.entity.SugangHistoryEntity;
import io.hhplus.cleancode.infrastructure.mapper.DDDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SugangHistoryRepositoryImpl implements SugangHistoryRepository {

//    @Autowired
//    SugangHistoryMapper sugangHistoryMapper;

    @Autowired
    DDDMapper dddMapper;

    private final SugangHistoryJpaRepository sugangHistoryJpaRepository;

    public SugangHistoryRepositoryImpl(SugangHistoryJpaRepository sugangHistoryJpaRepository){
        this.sugangHistoryJpaRepository=sugangHistoryJpaRepository;
    }
    public <S extends SugangHistory> S save(S pojo) {
        SugangHistoryEntity sugangHistoryEntity = sugangHistoryJpaRepository.save(dddMapper.sugangHistoryToEntity(pojo));
        return (S) dddMapper.sugangHistoryToPojo(sugangHistoryEntity);
    }

    public List<SugangHistory> findAllByStudent_StudentId(Long studentId) {
        return dddMapper.sugangHistoryToPojoList(
                sugangHistoryJpaRepository.findAllByStudent_StudentId(studentId));
    }

    public List<SugangHistory> findAllBySugangSchedule_ClassDateAndStudent_StudentId(String classDate,Long studentId){
        return dddMapper.sugangHistoryToPojoList(
                sugangHistoryJpaRepository.findAllBySugangSchedule_ClassDateAndStudent_StudentId(classDate,studentId)
        );
    }

    public long countBySugangSchedule_ClassDateAndStudent_StudentId(String classDate, Long studentId){
        return sugangHistoryJpaRepository.countBySugangSchedule_ClassDateAndStudent_StudentId(classDate,studentId);

    }

    public long countBySugangSchedule_ClassDateAndSugang_SugangId(String classDate,Long sugangId){
        return sugangHistoryJpaRepository.countBySugangSchedule_ClassDateAndSugang_SugangId( classDate, sugangId);

    }
}
