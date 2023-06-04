package org.example.repository;


import org.example.entity.Entity;
import org.example.util.ConnectionManager;

import java.util.List;

public abstract class CrudRepository<E extends Entity> {

    ConnectionManager connectionManager;

    public CrudRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    abstract public List<E> findAll();

    abstract public E findById(long id);

    abstract public void saveOrUpdate(E e);

    abstract public void delete(long id);

}
