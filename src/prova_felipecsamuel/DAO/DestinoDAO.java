package prova_felipecsamuel.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import prova_felipecsamuel.jdbc.ConnectionFactory;
import prova_felipecsamuel.model.Destino;

/**
 *
 * @author Felipe
 */
public class DestinoDAO implements GenericDAO<Destino> {

    Connection connection = null;
    
    @Override
    public void save(Destino entity) throws SQLException {
        connection = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO DESTINO(CD_DESTINO, DS_DESTINO,")
                    .append("DT_INICIO, DT_TERMINO, VL_TOTAL) ")
                    .append("VALUES(?, ?, ?, ?, ?)");

            pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigo());
            pstm.setString(2, entity.getDescricao());
            pstm.setDate(3, new Date(
                    entity.getInicio().getYear(), 
                    entity.getInicio().getMonth(), 
                    entity.getInicio().getDate()
            ));
            pstm.setDate(4, new Date(
                    entity.getTermino().getYear(), 
                    entity.getTermino().getMonth(), 
                    entity.getTermino().getDate()
            ));
            pstm.setDouble(5, entity.getValor());
            pstm.execute();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao comunicar com o banco.");
            sqle.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Ocorreu um erro, contate o suporte.");
            ex.printStackTrace();
        } finally {
            pstm.close();
            connection.close();
        }
    }

    @Override
    public void update(Destino entity) throws SQLException {
        connection = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE DESTINO SET DS_DESTINO = ?, DT_INICIO = ?,")
                    .append("DT_TERMINO = ?, VL_TOTAL = ? ")
                    .append("WHERE CD_DESTINO = ?");
            pstm = connection.prepareStatement(sql.toString());
            pstm.setString(1, entity.getDescricao());
            pstm.setDate(2, new Date(
                    entity.getInicio().getYear(), 
                    entity.getInicio().getMonth(), 
                    entity.getInicio().getDate()
            ));
            pstm.setDate(3, new Date(
                    entity.getTermino().getYear(), 
                    entity.getTermino().getMonth(), 
                    entity.getTermino().getDate()
            ));
            pstm.setDouble(4, entity.getValor());
            pstm.setInt(5, entity.getCodigo());
            pstm.execute();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao comunicar com o banco.");
            sqle.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Ocorreu um erro, contate o suporte.");
            ex.printStackTrace();
        } finally {
            pstm.close();
            connection.close();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        connection = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            String sql = "DELETE FROM DESTINO WHERE ID = " + id;
            pstm = connection.prepareStatement(sql);
            pstm.execute();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            pstm.close();
            connection.close();
        }
    }

    @Override
    public Destino getById(int id) throws SQLException {
        Destino destino = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            String sql = "SELECT * FROM DESTINO WHERE CD_DESTINO = " + id;
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            destino = new Destino();
            destino.setCodigo(rs.getInt("CD_DESTINO"));
            destino.setDescricao(rs.getString("DS_DESTINO"));
            destino.setInicio(rs.getDate("DT_INICIO"));
            destino.setInicio(rs.getDate("DT_TERMINO"));
            destino.setValor(rs.getDouble("VL_TOTAL"));
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao comunicar com o banco.");
            sqle.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro, contate o suporte.");
            ex.printStackTrace();
        } finally {
            pstm.close();
            connection.close();
        }
        return destino;
    }

    @Override
    public List<Destino> getByName(String name) throws SQLException {
        List<Destino> destinoList = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            String sql = "SELECT * FROM DESTINO WHERE DS_DESTINO = '%" 
                    + name + "%'";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            Destino destino;
            while (rs.next()) {
                destino = new Destino();
                destino.setCodigo(rs.getInt("CD_DESTINO"));
                destino.setDescricao(rs.getString("DS_DESTINO"));
                destino.setInicio(rs.getDate("DT_INICIO"));
                destino.setInicio(rs.getDate("DT_TERMINO"));
                destino.setValor(rs.getDouble("VL_TOTAL"));
                destinoList.add(destino);
            } 
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao comunicar com o banco.");
            sqle.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro, contate o suporte.");
            ex.printStackTrace();
        } finally {
            pstm.close();
            connection.close();
        }
        return destinoList;
    }

    @Override
    public List<Destino> getAll() throws SQLException {
        PreparedStatement pstm = null;
        List<Destino> destinoList = null;
        try{
            this.connection = new ConnectionFactory().getConnection();
            String sql = "SELECT * FROM DESTINO ORDER BY CD_DESTINO";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            destinoList = new ArrayList<>();
            Destino destino;
            while (rs.next()) {
                destino = new Destino();
                destino.setCodigo(rs.getInt("CD_DESTINO"));
                destino.setDescricao(rs.getString("DS_DESTINO"));
                destino.setInicio(rs.getDate("DT_INICIO"));
                destino.setInicio(rs.getDate("DT_TERMINO"));
                destino.setValor(rs.getDouble("VL_TOTAL"));
                destinoList.add(destino);
            }
            pstm.close();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao comunicar com o banco.");
            sqle.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro, contate o suporte.");
            ex.printStackTrace();
        } finally {
            pstm.close();
            connection.close();
        }
        return destinoList;
    }

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            String sql = "SELECT COALESCE(MAX(CD_CUSTO), 1) "
                    + "AS ULTIMO_CD FROM CUSTO";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            return (rs.getInt("ULTIMO_CD"));
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, 
                    "Erro ao comunicar com o banco.");
            sqle.printStackTrace();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                    "Ocorreu um erro, contate o suporte.");
            ex.printStackTrace();
        } finally {
            pstm.close();
            connection.close();
        }
        return 1;
    }
    
}
