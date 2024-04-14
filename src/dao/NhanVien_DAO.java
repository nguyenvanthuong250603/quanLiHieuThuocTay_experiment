package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import entity.NhanVien;
import static dao.Trage_DAO.*;

public class NhanVien_DAO {
	public ArrayList<NhanVien> getNhanVien() {
		ArrayList<NhanVien> lnv = new ArrayList<NhanVien>();
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT *FROM NhanVien");
			while (rs.next()) {
				int change = rs.getInt(3);
				Boolean gender = change == 1 ? true : false;
				Date date = rs.getDate("ngaySinh");
				LocalDate ns = date.toLocalDate();
				Date dateNVL = rs.getDate("ngayVaoLam");
				LocalDate nvl = dateNVL.toLocalDate();
				NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), gender, ns, rs.getInt(5), rs.getString(6),
						rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), nvl, rs.getString(12));

				lnv.add(nv);
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

		return lnv;
	}

	public NhanVien getNhanVienFindByID(String maNV) {
		NhanVien nv = new NhanVien();
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {
			p = con.prepareStatement("SELECT *FROM NhanVien WHERE MaNV= ?");
			p.setString(1, maNV);
			try (ResultSet rs = p.executeQuery()) {
				while (rs.next()) {
					int change = rs.getInt(3);
					Boolean gender = change == 1 ? true : false;
					Date date = rs.getDate("ngaySinh");
					LocalDate ns = date.toLocalDate();
					Date dateNVL = rs.getDate("ngayVaoLam");
					LocalDate nvl = dateNVL.toLocalDate();
					nv = new NhanVien(rs.getString(1), rs.getString(2), gender, ns, rs.getInt(5), rs.getString(6),
							rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), nvl, rs.getString(12));
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
		return nv;
	}

	public boolean themNhanVien(NhanVien nv) {
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {

			p = con.prepareStatement("INSERT INTO NhanVien VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			p.setString(1, nv.getMaNV());
			p.setString(2, nv.getHoTen());
			p.setInt(3, chageGenderToSQL(nv.isGioiTinh()));
			p.setDate(4, java.sql.Date.valueOf(nv.getNgaySinh()));
			p.setInt(5, nv.getTuoi());
			p.setString(6, nv.getSdt());
			p.setString(7, nv.getCccd());
			p.setString(8, nv.getDiaChi());
			p.setString(9, nv.getChucVu());
			p.setString(10, nv.getTinhTrang());
			p.setDate(11, java.sql.Date.valueOf(nv.getNgayVaoLam()));
			p.setString(12, nv.getHinhAnh());
			p.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public boolean xoaNhanVien(String ma) {
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {

			p = con.prepareStatement("DELETE NhanVien WHERE MaNV = ?");
			p.setString(1, ma);

			p.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean updateNhanVien(NhanVien nv) {
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {

			p = con.prepareStatement(
					"UPDATE NhanVien SET HoTen = ?,GioiTinh=?,NgaySinh ="
					+ " ?,Tuoi = ?,sdt= ?,cccd= ?,DiaChi= ?,ChucVu= ?,TinhTrang= ?,NgayVaoLam= ?,HinhAnh= ? WHERE MaNV=?");

			p.setString(1, nv.getHoTen());
			p.setInt(2, chageGenderToSQL(nv.isGioiTinh()));
			p.setDate(3, java.sql.Date.valueOf(nv.getNgaySinh()));
			p.setInt(4, nv.getTuoi());
			p.setString(5, nv.getSdt());
			p.setString(6, nv.getCccd());
			p.setString(7, nv.getDiaChi());
			p.setString(8, nv.getChucVu());
			p.setString(9, nv.getTinhTrang());
			p.setDate(10, java.sql.Date.valueOf(nv.getNgayVaoLam()));
			p.setString(11, nv.getHinhAnh());
			p.setString(12, nv.getMaNV());
			p.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public ArrayList<NhanVien> locNhanVien(String trangThai,String chucVu){
		ArrayList<NhanVien> lnv = new ArrayList<NhanVien>();
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {
			if(trangThai.equals("")||chucVu.equals("")) {
				p = con.prepareStatement("SELECT *FROM NhanVien WHERE " + (trangThai.equals("")?"ChucVu =?":"TinhTrang = ?"));
				if(trangThai.equals("")) {
					p.setString(1, chucVu);
				}
				else {
					p.setString(1, trangThai);
				}
			}
			else if(!trangThai.equals("")&&!chucVu.equals("")){
				p = con.prepareStatement("SELECT *FROM NhanVien WHERE TinhTrang = ?  AND ChucVu = ?");
				p.setString(1, trangThai);
				p.setString(2, chucVu);
			}
		
			try (ResultSet rs = p.executeQuery()) {
				while (rs.next()) {
					int change = rs.getInt(3);
					Boolean gender = change == 1 ? true : false;
					Date date = rs.getDate("ngaySinh");
					LocalDate ns = date.toLocalDate();
					Date dateNVL = rs.getDate("ngayVaoLam");
					LocalDate nvl = dateNVL.toLocalDate();
					NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), gender, ns, rs.getInt(5), rs.getString(6),
							rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), nvl, rs.getString(12));
					lnv.add(nv);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		return lnv;
	}
}
