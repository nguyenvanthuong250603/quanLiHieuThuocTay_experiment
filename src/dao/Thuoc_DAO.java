package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import static connectDataBase.connectionData.*;
import entity.Thuoc;

public class Thuoc_DAO {
	public ArrayList<Thuoc> getThuocDataBase(){
		ArrayList<Thuoc> list_Thuoc = new ArrayList<Thuoc>();
		Connection con = accessDataBase();
		try {
			Statement s =con.createStatement();
			ResultSet rs = s.executeQuery("SELECT *FROM Thuoc");
			while (rs.next()) {
				Date date = rs.getDate("NgaySanXuat");
				 LocalDate localDate = date.toLocalDate();
				Thuoc thuoc = new Thuoc(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),rs.getString(6),rs.getString(7), rs.getString(8),localDate,localDate, rs.getString(11));
				list_Thuoc.add(thuoc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_Thuoc;
	}
}
