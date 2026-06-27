package dao;

import java.util.List;

public interface AdminDAO {
    List<String> loginAdmin(String user, String password);
}
