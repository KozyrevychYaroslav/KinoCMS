package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public interface DAO<T> {
    public void save(T val);
    public void delete(T val);
    public void update(T val);
    public T get(long id);
    public List<T> getAll();
}
