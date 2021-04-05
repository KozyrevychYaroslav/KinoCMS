package com.kozyrevych.app.services;

import com.kozyrevych.app.dao.ImageDAO;
import com.kozyrevych.app.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Service
public class ImageService {
    @Autowired
    ImageDAO imageDAO;

    
    public void save(Image image) {
        imageDAO.save(image);
    }

    
    public void delete(Image image) {
        imageDAO.delete(image);
    }

    public void deleteByName(String name) {
        Image c = getByName(name);
        delete(c);
    }

    
    public void update(Image image) {
        imageDAO.update(image);
    }

    
    public Image get(long id) {
      return imageDAO.get(id);
    }

    public Image getByName(String name) {
        return imageDAO.getByName(name);
    }

    
    public List<Image> getAll() {
        return imageDAO.getAll();
    }
}
