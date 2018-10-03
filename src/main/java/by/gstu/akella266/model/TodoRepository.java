package by.gstu.akella266.model;

import by.gstu.akella266.model.dto.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository implements by.gstu.akella266.model.Repository {

    private DataSource dataSource;

    @Autowired
    public TodoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Todo todo) {
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into todolist (title) values (?)");
            ps.setString(1, todo.getTitle());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error insert");
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Todo todo) {
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("update todolist set title = ?, status = ? where id=?");
            ps.setString(1, todo.getTitle());
            ps.setInt(2, todo.isStatus() ? 1 : 0);
            ps.setInt(3, Integer.parseInt(todo.getId()));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error update");
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void remove(String id) {
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from todolist where id=?");
            ps.setInt(1, Integer.parseInt(id));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error remove");
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Todo> getAll() {
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from todolist");
            List<Todo> list = new ArrayList<>();
            while(rs.next()){
                list.add(new Todo(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getInt("status") == 1
                ));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getAll");
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ArrayList<>();
    }

    @Override
    public Todo getTodo(String id) {
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from todolist where id = ?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Todo(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getInt("status") == 1
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getTodo");
        }
        finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
