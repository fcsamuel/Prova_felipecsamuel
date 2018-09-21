/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prova_felipecsamuel.DAO;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Felipe
 */
public interface GenericDAO <T> {
    
    public void save(T entity) throws SQLException;
    public void update(T entity) throws SQLException;
    public void delete(int id) throws SQLException;
    public T getById(int id) throws SQLException;
    public List<T> getByName(String name) throws SQLException;
    public List<T> getAll() throws SQLException;
    public int getLastId() throws SQLException;
}
