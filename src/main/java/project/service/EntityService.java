package main.java.project.service;

import main.java.project.entities.Entity;

import java.sql.Connection;
import java.util.List;

public interface EntityService {
    void addEntity();
    void upDateEntity(int id);
    void deleteEntity(int id);
    List<Entity> showAllEntity();
    Entity getEntityID(int id);
    List<Entity> showEntityByParentId(int parentID);
    List<Entity> entityList(String sql, int id);
}
