package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerResource_zh_CN;

import static connectDataBase.connectionData.*;

import entity.NhaSanXuat;
import entity.Thuoc;

public class Thuoc_DAO {
	public ArrayList<Thuoc> getThuocDataBase() {

		ArrayList<Thuoc> list_Thuoc = new ArrayList<Thuoc>();

		Connection con = accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM Thuoc");
			while (rs.next()) {
				Date date = rs.getDate("NgaySanXuat");
				Date dateNhh = rs.getDate("NgayHetHan");
				LocalDate localDateNsx = date.toLocalDate();
				LocalDate localDateNhh = dateNhh.toLocalDate();
				Thuoc thuoc = new Thuoc(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), new NhaSanXuat(rs.getString(8)), localDateNsx, localDateNhh,
						rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
						rs.getString(16));
				list_Thuoc.add(thuoc);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list_Thuoc;
	}

	public boolean themThuoc(Thuoc thuoc) {

		ArrayList<Thuoc> list_thuoc = new ArrayList<>();

		Connection con = accessDataBase();
		NhaSanXuat nsx = thuoc.getTenNhaSanXuat();
		PreparedStatement p = null;

		if (list_thuoc.contains(thuoc)) {
			return false;
		} else {
			try {
				p = con.prepareStatement("INSERT INTO Thuoc VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?	)");
				p.setString(1, thuoc.getMaThuoc());
				p.setString(2, thuoc.getTenThuoc());
				p.setInt(3, thuoc.getSoLuong());
				p.setDouble(4, thuoc.getGia());
				p.setString(5, thuoc.getDonVi());
				p.setString(6, thuoc.getLoaiThuoc());
				p.setString(7, thuoc.getDoTuoi());
				p.setString(8, nsx.getTenNSX());
				p.setDate(9, java.sql.Date.valueOf(thuoc.getNgaySanXuat()));
				p.setDate(10, java.sql.Date.valueOf(thuoc.getNgayHetHan()));
				p.setString(11, thuoc.getHinhAnh());
				p.setString(12, thuoc.getThanhPhan());
				p.setString(13, thuoc.getChiDinh());
				p.setString(14, thuoc.getLieuDung());
				p.setString(15, thuoc.getBaoQuan());
				p.setString(16, thuoc.getMoTa());
				p.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

	}

	public boolean suaThuoc(Thuoc thuoc) {

		ArrayList<Thuoc> list_thuoc = new ArrayList<>();

		Connection con = accessDataBase();
		NhaSanXuat nsx = thuoc.getTenNhaSanXuat();
		PreparedStatement p = null;

		if (list_thuoc.contains(thuoc)) {
			return false;
		} else {
			try {
				p = con.prepareStatement(
						"UPDATE Thuoc SET TenThuoc = ? ,SoLuong = ?,Gia = ?,DonVi=?,LoaiThuoc=?,DoTuoi=?,NhaSanXuat=?,NgaySanXuat=?,NgayHetHan=?,HinhAnh=?,ThanhPhan=?,ChiDinh=?,LieuDung=?,BaoQuan=?,MoTa=? WHERE MaThuoc = ?");

				p.setString(1, thuoc.getTenThuoc());
				p.setInt(2, thuoc.getSoLuong());
				p.setDouble(3, thuoc.getGia());
				p.setString(4, thuoc.getDonVi());
				p.setString(5, thuoc.getLoaiThuoc());
				p.setString(6, thuoc.getDoTuoi());
				p.setString(7, nsx.getTenNSX());
				p.setDate(8, java.sql.Date.valueOf(thuoc.getNgaySanXuat()));
				p.setDate(9, java.sql.Date.valueOf(thuoc.getNgayHetHan()));
				p.setString(10, thuoc.getHinhAnh());
				p.setString(11, thuoc.getThanhPhan());
				p.setString(12, thuoc.getChiDinh());
				p.setString(13, thuoc.getLieuDung());
				p.setString(14, thuoc.getBaoQuan());
				p.setString(15, thuoc.getMoTa());
				p.setString(16, thuoc.getMaThuoc());
				p.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}

	}

	public ArrayList<Thuoc> timThuoc(String NSX,String loaiThuoc) {
		ArrayList<Thuoc> list_Thuoc = new ArrayList<Thuoc>(); 
		Connection con = accessDataBase();
		PreparedStatement p = null;
		try {	
	
		      String sql = "SELECT * FROM Thuoc INNER JOIN NhaSanXuat ON Thuoc.NhaSanXuat = NhaSanXuat.TenNSX WHERE Thuoc.NhaSanXuat = ? AND Thuoc.LoaiThuoc = ?";
		        p = con.prepareStatement(sql);
		        
		        // Đặt giá trị cho các tham số
		        p.setString(1, NSX);
		        p.setString(2, loaiThuoc);
			
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Date date = rs.getDate("NgaySanXuat");
				Date dateNhh = rs.getDate("NgayHetHan");
				LocalDate localDateNsx = date.toLocalDate();
				LocalDate localDateNhh = dateNhh.toLocalDate();
				Thuoc thuoc = new Thuoc(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), new NhaSanXuat(rs.getString(8)), localDateNsx, localDateNhh,
						rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
						rs.getString(16));
 				list_Thuoc.add(thuoc);
			}
 		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return list_Thuoc;
	}

}
