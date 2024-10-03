package io.hhplus.cleancode.infrastructure.repository;

import io.hhplus.cleancode.domain.entity.SugangSchedule;
import io.hhplus.cleancode.domain.repository.SugangScheduleRepository;
import io.hhplus.cleancode.infrastructure.entity.SugangScheduleEntity;
import io.hhplus.cleancode.infrastructure.mapper.DDDMapper;
import io.hhplus.cleancode.infrastructure.mapper.SugangScheduleMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SugangScheduleRepositoryImpl implements SugangScheduleRepository {

//    @Autowired
//    SugangScheduleMapper sugangScheduleMapper;

    @Autowired
    DDDMapper dddMapper;

    private final SugangScheduleJpaRepository sugangScheduleJpaRepository;

    public SugangScheduleRepositoryImpl(SugangScheduleJpaRepository sugangScheduleJpaRepository) {
        this.sugangScheduleJpaRepository = sugangScheduleJpaRepository;
    }

    public Optional<SugangSchedule> findBySugang_SugangIdAndClassDate(Long sugang, String classDate) {
        Optional<SugangScheduleEntity> sugangScheduleEntityOptional = sugangScheduleJpaRepository.findBySugang_SugangIdAndClassDate(sugang,classDate);

        SugangScheduleEntity sugangScheduleEntity = sugangScheduleEntityOptional.get();


        System.out.println("매퍼 로그 : " + sugangScheduleEntity.toString());
        return Optional.of(dddMapper.sugangScheduleToPojo(sugangScheduleEntity));
    };

    public <S extends SugangSchedule> S save(S item) {
        SugangScheduleEntity sugangScheduleEntity = sugangScheduleJpaRepository.save(dddMapper.sugangScheduleToEntity(item));
        System.out.println("출력: "+dddMapper.sugangScheduleToEntity(item).toString());
        return (S) dddMapper.sugangScheduleToPojo(sugangScheduleEntity);
    }

    public List<SugangSchedule> findAllByClassDateGreaterThanEqual(String classDate) {
        List<SugangScheduleEntity> sugangScheduleEntities = sugangScheduleJpaRepository.findAllByClassDateGreaterThanEqual(classDate);
        return dddMapper.sugangScheduleToPojoList(sugangScheduleEntities);
    }

    public SugangSchedule lockStart(Long sugangSchedule_id, EntityManager entityManager) {
        return dddMapper.sugangScheduleToPojo(entityManager.find(SugangScheduleEntity.class, sugangSchedule_id, LockModeType.PESSIMISTIC_WRITE)) ;
    }

    public SugangSchedule findById(Long scheduleId){
        return dddMapper.sugangScheduleToPojo(sugangScheduleJpaRepository.findById(scheduleId).get());
    }


}
