package prova_felipecsamuel.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import prova_felipecsamuel.jdbc.ConnectionFactory;
import prova_felipecsamuel.model.Custo;
import prova_felipecsamuel.model.Destino;

/**
 *
 * @author Felipe
 */
public class CustoDAO implements GenericDAO<Custo> {

    private Connection connection;
    
    @Override
    public void save(Custo entity) throws SQLException {
        connection = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO CUSTO(CD_CUSTO, CD_DESTINO, DS_CUSTO,")
                    .append("TP_CUSTO, VL_CUSTO) VALUES")
                    .append("(?, ?, ?, ?, ?)");
            pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getCodigo());
            pstm.setInt(2, entity.getDestino().getCodigo());
            pstm.setString(3, entity.getDescricao());
            pstm.setInt(4, entity.getTipo());
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
    public void update(Custo entity) throws SQLException {
        connection = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CUSTO SET ID_DESTINO = ?,")
                    .append("DS_CUSTO ?, TP_CUSTO = ?, VL_CUSTO = ? ")
                    .append("WHERE CD_CUSTO = ?");
            pstm = connection.prepareStatement(sql.toString());
            pstm.setInt(1, entity.getDestino().getCodigo());
            pstm.setString(2, entity.getDescricao());
            pstm.setInt(3, entity.getTipo());
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
            String sql = "DELETE FROM CUSTO WHERE ID = " + id;
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
    public Custo getById(int id) throws SQLException {
        Custo custo = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT C.CD_CUSTO, C.DS_CUSTO,")
                    .append("C.TP_CUSTO, C.VL_CUSTO,")
                    .append("D.CD_DESTINO, D.DS_DESTINO,")
                    .append("D.DT_INICIO, D.DT_TERMINO, D.VL_TOTAL ")
                    .append("FROM CUSTO AS C JOIN DESTINO AS D ")
                    .append("USING (CD_DESTINO) WHERE CD_CUSTO = ")
                    .append(id);
            pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            rs.next();
            custo.setCodigo(rs.getInt("CD_CUSTO"));
            custo.setDescricao(rs.getString("DS_CUSTO"));
            custo.setTipo(rs.getInt("TP_CUSTO"));
            custo.setValor(rs.getDouble("VL_CUSTO"));
            custo.setDestino(populaDestino(
                    rs.getInt("CD_DESTINO"),
                    rs.getString("DS_DESTINO"), 
                    rs.getDate("DT_INICIO"),
                    rs.getDate("DT_TERMINO"),
                    rs.getDouble("VL_DESTINO")
            ));
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
        return custo;
    }

    @Override
    public List<Custo> getByName(String name) throws SQLException {
        List<Custo> custoList = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT C.CD_CUSTO, C.DS_CUSTO,")
                    .append("C.TP_CUSTO, C.VL_CUSTO,")
                    .append("D.CD_DESTINO, D.DS_DESTINO,")
                    .append("D.DT_INICIO, D.DT_TERMINO, D.VL_TOTAL ")
                    .append("FROM CUSTO AS C JOIN DESTINO AS D ")
                    .append("USING (CD_DESTINO) WHERE DS_CUSTO = ")
                    .append("'%").append(name).append("%'");
            pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            custoList = new ArrayList<>();
            Custo custo;
            while (rs.next()) {
                custo = new Custo();
                custo.setCodigo(rs.getInt("CD_CUSTO"));
                custo.setDescricao(rs.getString("DS_CUSTO"));
                custo.setTipo(rs.getInt("TP_CUSTO"));
                custo.setValor(rs.getDouble("VL_CUSTO"));
                custo.setDestino(populaDestino(
                        rs.getInt("CD_DESTINO"), 
                        rs.getString("DS_DESTINO"), 
                        rs.getDate("DT_INICIO"), 
                        rs.getDate("DT_TERMINO"), 
                        rs.getDouble("VL_DESTINO")
                ));
                custoList.add(custo);
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
        return custoList;
    }

    @Override
    public List<Custo> getAll() throws SQLException {
        List<Custo> custoList = null;
        PreparedStatement pstm = null;
        try {
            connection = new ConnectionFactory().getConnection();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT C.CD_CUSTO, C.DS_CUSTO,")
                    .append("C.TP_CUSTO, C.VL_CUSTO,")
                    .append("D.CD_DESTINO, D.DS_DESTINO,")
                    .append("D.DT_INICIO, D.DT_TERMINO, D.VL_TOTAL ")
                    .append("FROM CUSTO AS C JOIN DESTINO AS D ")
                    .append("USING (CD_DESTINO)");
            pstm = connection.prepareStatement(sql.toString());
            ResultSet rs = pstm.executeQuery();
            custoList = new ArrayList<>();
            Custo custo;
            while (rs.next()) {
                custo = new Custo();
                custo.setCodigo(rs.getInt("CD_CUSTO"));
                custo.setDescricao(rs.getString("DS_CUSTO"));
                custo.setTipo(rs.getInt("TP_CUSTO"));
                custo.setValor(rs.getDouble("VL_CUSTO"));
                custo.setDestino(populaDestino(
                        rs.getInt("CD_DESTINO"),
                        rs.getString("DS_DESTINO"),
                        rs.getDate("DT_INICIO"),
                        rs.getDate("DT_TERMINO"),
                        rs.getDouble("VL_TOTAL")
                ));
                custoList.add(custo);
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
        return custoList;
    }

    @Override
    public int getLastId() throws SQLException {
        PreparedStatement pstm = null;
        int valor = 1;
        try {
            connection = new ConnectionFactory().getConnection();
            String sql = "SELECT COALESCE(MAX(CD_CUSTO), 1) "
                    + "AS ULTIMO_CD FROM CUSTO";
            pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            return (valor = rs.getInt("ULTIMO_CD"));
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
        return valor;
    }
    
    public Destino populaDestino(int codigo, String descricao, 
            Date inicio, Date termino, double valor) {
        
        Destino d = new Destino();
        d.setCodigo(codigo);
        d.setDescricao(descricao);
        d.setInicio(inicio);
        d.setTermino(termino);
        d.setValor(valor);
        return d;
    }
}
