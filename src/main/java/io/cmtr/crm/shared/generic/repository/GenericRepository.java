package io.cmtr.crm.shared.generic.repository;

import io.cmtr.crm.shared.generic.model.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<R, T extends GenericEntity<R, T>> extends JpaRepository<T, R> {

}
