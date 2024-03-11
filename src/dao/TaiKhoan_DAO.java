package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import entity.NhanVien;
import entity.taiKhoan;

public class TaiKhoan_DAO {
	public ArrayList<taiKhoan> getTaiKhoan(){
		ArrayList<taiKhoan> ltk = new ArrayList<taiKhoan>();
		Connection con = connectDataBase.connectionData.accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs =s.executeQuery("SELECT *FROM taiKhoan");
			while (rs.next()) {
			NhanVien nv = new NhanVien(rs.getString(1));
			taiKhoan tk = new taiKhoan(nv, rs.getString(2));
			ltk.add(tk);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}	
		return ltk;
	}
}
