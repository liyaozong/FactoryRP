package cn.tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 基本的Reository
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public interface BaseRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID>, CrudRepository<T, ID>, JpaSpecificationExecutor<T> {

}
