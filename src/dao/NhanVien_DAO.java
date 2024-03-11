package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.NhanVien;

public class NhanVien_DAO {
	public ArrayList<NhanVien> getNhanVien(){
		ArrayList<NhanVien> lnv = new ArrayList<NhanVien>();
		Connection con = connectDataBase.connectionData.accessDataBase();
		try {
			Statement s = con.createStatement();
//			ResultSet rs = s.executeQuery("SELECT *FROM NhanVien");
			while (rs.next()) {
				int change = rs.getInt(3);
				Boolean gender = change==1? true : false; 
				Date date = rs.getDate("ngaySinh");
				LocalDate ns = date.toLocalDate();
				Date dateNVL = rs.getDate("ngayVaoLam");
				LocalDate nvl = dateNVL.toLocalDate();
				NhanVien nv = new NhanVien(rs.getString(1),rs.getString(2), gender, ns, rs.getString(5), rs.getString(6),rs.getString(7), nvl);
				System.out.println(nv);
				lnv.add(nv);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return lnv;
	}
	public NhanVien getNhanVienFindByID(String maNV) {
		Connection con = connectDataBase.connectionData.accessDataBase();
		Statement 
		return 
	}
}