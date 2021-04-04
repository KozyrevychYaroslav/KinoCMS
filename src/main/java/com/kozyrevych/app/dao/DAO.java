package com.kozyrevych.app.dao;

import com.kozyrevych.app.model.Advertising;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public interface DAO<T, V> {
    public void save(T val);
    public void delete(V id);
    public void update(T val);
    public T get(V id);
    public List<T> getAll();
}
