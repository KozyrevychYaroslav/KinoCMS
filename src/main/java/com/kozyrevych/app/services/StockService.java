package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.StockDAO;
import com.kozyrevych.app.model.Stock;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Service
@Transactional
public class StockService {
    @Autowired
    StockDAO stockDAO;

    public void save(Stock stock) {
        stockDAO.save(stock);
    }

    public void delete(Stock stock) {
        stockDAO.delete(stock);
    }

    public void deleteByName(String name) {
        Stock c = getByName(name);
        delete(c);
    }

    public void update(Stock stock) {
        stockDAO.update(stock);
    }

    
    public Stock get(long id) {
       return stockDAO.get(id);
    }

    public Stock getByName(String name) {
       return stockDAO.getByName(name);
    }
    
    public List<Stock> getAll() {
        return stockDAO.getAll();
    }
}
