package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.table.DefaultTableCellRenderer;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import static connectDataBase.ConnectionData.*;
import static dao.Trage_DAO.*;
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

				LocalDate localDateNsx = chageTimeSQL(rs.getDate("NgaySanXuat"));
				LocalDate localDateNhh = chageTimeSQL(rs.getDate("NgayHetHan"));
				Thuoc thuoc = new Thuoc(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						new NhaSanXuat(rs.getString(6)), localDateNsx, localDateNhh, rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
						rs.getString(16), rs.getString(17));
				list_Thuoc.add(thuoc);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list_Thuoc;
	}

	public boolean themThuoc(Thuoc thuoc) {

		ArrayList<Thuoc> list_thuoc = getThuocDataBase();

		Connection con = accessDataBase();
		NhaSanXuat nsx = thuoc.getTenNhaSanXuat();
		PreparedStatement p = null;

		if (list_thuoc.contains(thuoc)) {
			return false;
		} else {
			try {
				p = con.prepareStatement("INSERT INTO Thuoc VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				p.setString(1, thuoc.getMaThuoc());
				p.setString(2, thuoc.getTenThuoc());
				p.setInt(3, thuoc.getSoLuong());
				p.setDouble(4, thuoc.getGia());

				p.setString(5, thuoc.getLoaiThuoc());

				p.setString(6, nsx.getTenNSX());
				p.setDate(7, java.sql.Date.valueOf(thuoc.getNgaySanXuat()));
				p.setDate(8, java.sql.Date.valueOf(thuoc.getNgayHetHan()));
				p.setString(9, thuoc.getHinhAnh());
				p.setString(10, thuoc.getDonVi());
				p.setString(11, thuoc.getDangBaoChe());
				p.setString(12, thuoc.getDoTuoi());
				p.setString(13, thuoc.getThanhPhan());
				p.setString(14, thuoc.getChiDinh());
				p.setString(15, thuoc.getLieuDung());
				p.setString(16, thuoc.getBaoQuan());
				p.setString(17, thuoc.getMoTa());
				p.executeUpdate();
				return true;
			} catch (Exception e) {

				return false;
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	public boolean suaThuoc(Thuoc thuoc, int soLuong, String ma) {

		ArrayList<Thuoc> list_thuoc = new ArrayList<>();

		Connection con = accessDataBase();
		NhaSanXuat nsx = thuoc.getTenNhaSanXuat();
		PreparedStatement p = null;

		if (list_thuoc.contains(thuoc)) {
			return false;
		} else {
			try {
				if (ma.equals("") && soLuong == 0) {
					p = con.prepareStatement(
							"UPDATE Thuoc SET TenThuoc = ? ,SoLuong = ?,Gia = ?,LoaiThuoc=?,NhaSanXuat=?,"
									+ "NgaySanXuat=?,NgayHetHan=?,HinhAnh=?,"
									+ "DonVi = ?,DangBaoChe =  ?,DoTuoi =?,ThanhPhan=?,ChiDinh=?,LieuDung=?,BaoQuan=?,MoTa=? WHERE MaThuoc = ?");

					p.setString(1, thuoc.getTenThuoc());
					p.setInt(2, thuoc.getSoLuong());
					p.setDouble(3, thuoc.getGia());

					p.setString(4, thuoc.getLoaiThuoc());

					p.setString(5, nsx.getTenNSX());
					p.setDate(6, java.sql.Date.valueOf(thuoc.getNgaySanXuat()));
					p.setDate(7, java.sql.Date.valueOf(thuoc.getNgayHetHan()));
					p.setString(8, thuoc.getHinhAnh());
					p.setString(9, thuoc.getDonVi());
					p.setString(10, thuoc.getDangBaoChe());
					p.setString(11, thuoc.getDoTuoi());

					p.setString(12, thuoc.getThanhPhan());
					p.setString(13, thuoc.getChiDinh());
					p.setString(14, thuoc.getLieuDung());
					p.setString(15, thuoc.getBaoQuan());
					p.setString(16, thuoc.getMoTa());
					p.setString(17, thuoc.getMaThuoc());

				} else {
					p = con.prepareStatement("UPDATE Thuoc SET SoLuong = ? WHERE MaThuoc = ?");
					p.setInt(1, soLuong);
					p.setString(2, ma);
				}
				p.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	public ArrayList<Thuoc> timThuoc(String NSX, String loaiThuoc, String tenNSX) {
		ArrayList<Thuoc> list_Thuoc = new ArrayList<Thuoc>();
		Connection con = accessDataBase();
		PreparedStatement p = null;
		try {

			String sql;
			if (!NSX.equals("") && loaiThuoc.equals("") && tenNSX.equals("")) {
				sql = "SELECT *FROM Thuoc WHERE NhaSanXuat = ?";
				p = con.prepareStatement(sql);
				p.setString(1, NSX);

			} else if (NSX.equals("") && !loaiThuoc.equals("") & tenNSX.equals("")) {
				sql = "SELECT *FROM Thuoc WHERE LoaiThuoc = ?";
				p = con.prepareStatement(sql);
				p.setString(1, loaiThuoc);
			} else if (!NSX.equals("") && !loaiThuoc.equals("") & tenNSX.equals("")) {
				sql = "SELECT * FROM Thuoc WHERE NhaSanXuat = ? AND LoaiThuoc = ?";
				p = con.prepareStatement(sql);
				p.setString(1, NSX);
				p.setString(2, loaiThuoc);
			} else if (!NSX.equals("") && !loaiThuoc.equals("") & !tenNSX.equals("")) {
				sql = "SELECT * FROM Thuoc WHERE NhaSanXuat = ? AND LoaiThuoc = ? AND TenThuoc = ?";
				p = con.prepareStatement(sql);
				p.setString(1, NSX);
				p.setString(2, loaiThuoc);
				p.setString(3, tenNSX);
			} else if (!NSX.equals("") && loaiThuoc.equals("") & !tenNSX.equals("")) {
				sql = "SELECT * FROM Thuoc WHERE NhaSanXuat = ? AND TenThuoc = ?";
				p = con.prepareStatement(sql);
				p.setString(1, NSX);
				p.setString(2, tenNSX);
			} else if (NSX.equals("") && !loaiThuoc.equals("") & !tenNSX.equals("")) {
				sql = "SELECT * FROM Thuoc WHERE TenThuoc = ? AND LoaiThuoc = ?";
				p = con.prepareStatement(sql);
				p.setString(1, tenNSX);
				p.setString(2, loaiThuoc);
			} else if (NSX.equals("") && loaiThuoc.equals("") & !tenNSX.equals("")) {
				sql = "SELECT * FROM Thuoc WHERE  TenThuoc = ?";
				p = con.prepareStatement(sql);
				p.setString(1, tenNSX);

			}

			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Date date = rs.getDate("NgaySanXuat");
				Date dateNhh = rs.getDate("NgayHetHan");
				LocalDate localDateNsx = date.toLocalDate();
				LocalDate localDateNhh = dateNhh.toLocalDate();
				Thuoc thuoc = new Thuoc(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						new NhaSanXuat(rs.getString(6)), localDateNsx, localDateNhh, rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
						rs.getString(16), rs.getString(17));

				list_Thuoc.add(thuoc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list_Thuoc;
	}

	public Thuoc getThuocByID(String maThuoc) {
		Thuoc th = new Thuoc();
		Connection con = accessDataBase();
		PreparedStatement p = null;
		try {
			p = con.prepareStatement("SELECT *FROM Thuoc WHERE MaThuoc = ?");
			p.setString(1, maThuoc);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Date date = rs.getDate("NgaySanXuat");
				Date dateNhh = rs.getDate("NgayHetHan");
				LocalDate localDateNsx = date.toLocalDate();
				LocalDate localDateNhh = dateNhh.toLocalDate();
				th = new Thuoc(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						new NhaSanXuat(rs.getString(6)), localDateNsx, localDateNhh, rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15),
						rs.getString(16), rs.getString(17));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return th;
	}

	public boolean xoaThuoc(String ma) {
		Connection con = accessDataBase();
		PreparedStatement p = null;
		try {
			p = con.prepareStatement("DELETE Thuoc WHERE MaThuoc = ?");
			p.setString(1, ma);
			p.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	}
}
