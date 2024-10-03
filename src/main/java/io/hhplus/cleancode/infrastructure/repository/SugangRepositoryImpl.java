package io.hhplus.cleancode.infrastructure.repository;

import io.hhplus.cleancode.domain.entity.Sugang;
import io.hhplus.cleancode.domain.repository.SugangRepository;
import io.hhplus.cleancode.infrastructure.entity.SugangEntity;
import io.hhplus.cleancode.infrastructure.mapper.DDDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SugangRepositoryImpl implements SugangRepository {


    @Autowired
    DDDMapper dddMapper;
    
    private final SugangJpaRepository sugangJpaRepository;

    public SugangRepositoryImpl(SugangJpaRepository sugangJpaRepository) {
        this.sugangJpaRepository=sugangJpaRepository;
    }

    public <S extends Sugang> S save(S item) {
        SugangEntity sugangEntity = sugangJpaRepository.save(dddMapper.sugangToEntity(item));

        return (S) dddMapper.sugangToPojo(sugangEntity);
    };

    public Optional<Sugang> findById(Long aLong){
        Optional<SugangEntity> sugangEntityOptional = sugangJpaRepository.findById(aLong);
        SugangEntity sugangEntity = sugangEntityOptional.get();
        return Optional.of(dddMapper.sugangToPojo(sugangEntity));
    }
}
