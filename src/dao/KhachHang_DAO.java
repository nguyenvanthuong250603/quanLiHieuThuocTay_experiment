package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import static dao.Trage_DAO.*;
import entity.KhachHang;

public class KhachHang_DAO {
	public ArrayList<KhachHang> getListKhachHang() {
		ArrayList<KhachHang> lkh = new ArrayList<KhachHang>();
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT *FROM KhachHang");
			while (rs.next()) {
				int change = rs.getInt(5);
				Boolean gender = change == 1 ? true : false;

				LocalDate localDate_NgaySinh = chageTimeSQL(rs.getDate("NgaySinh"));

				KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), localDate_NgaySinh, rs.getInt(4), gender,
						rs.getString(6), rs.getString(7), rs.getInt(8));

				lkh.add(kh);
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
		return lkh;
	}

	public KhachHang getKhachHangByID(String maKh) {
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		KhachHang kh = new KhachHang();
		PreparedStatement p = null;
		try {

			p = con.prepareStatement("SELECT *FROM KhachHang WHERE MaKH= ?");
			p.setString(1, maKh);
			try (ResultSet rs = p.executeQuery()) {

				while (rs.next()) {
					Boolean gender = rs.getInt(5) == 1 ? true : false;
					LocalDate localDate_NgaySinh = chageTimeSQL(rs.getDate("NgaySinh"));
					kh = new KhachHang(rs.getString(1), rs.getString(2), localDate_NgaySinh, rs.getInt(4), gender,
							rs.getString(6), rs.getString(7), rs.getInt(8));
				}
			} catch (Exception e) {
				e.printStackTrace();
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
		return kh;
	}

	public boolean themKhachHang(KhachHang kh) {
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {
			p = con.prepareStatement("INSERT INTO KhachHang VALUES (?,?,?,?,?,?,?,?)");
			p.setString(1, kh.getMaKH());
			p.setString(2, kh.getTenKH());
			p.setDate(3, java.sql.Date.valueOf(kh.getNgaySinh()));
			p.setInt(4, kh.getTuoi());
			int gender = chageGenderToSQL(kh.isGioiTinh());
			p.setInt(5, gender);
			p.setString(6, kh.getsDT());
			p.setString(7, kh.getDiaCHi());
			p.setInt(8, kh.getDiemThanhVien());
			p.executeUpdate();
			return true;

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
		return false;
	}
}
