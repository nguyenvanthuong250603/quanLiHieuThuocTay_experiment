package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoan_DAO {
	public ArrayList<TaiKhoan> getTaiKhoan(){
		ArrayList<TaiKhoan> ltk = new ArrayList<TaiKhoan>();
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs =s.executeQuery("SELECT *FROM taiKhoan");
			while (rs.next()) {
			NhanVien nv = new NhanVien(rs.getString(1));
			TaiKhoan tk = new TaiKhoan(nv, rs.getString(2));
			ltk.add(tk);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ltk;
	}
}
