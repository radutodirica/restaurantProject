package com.example.foodzz_v2.foodzz_v2.repository.product;

import com.example.foodzz_v2.foodzz_v2.model.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class RepositoryBatchImpl {

    private final JpaRepository jpaRepository;
    private final int batchSize = 50;

    @Autowired
    public RepositoryBatchImpl(JpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public <T> void persistBatch(Collection<T> entitiesList) throws PersistenceException {
        int i = 0;
        try{
            for(T entity : entitiesList){
                jpaRepository.save(entity);
                i++;
                if(i % batchSize == 0)
                    jpaRepository.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
