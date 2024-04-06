package dao;

import static connectDataBase.ConnectionData.accessDataBase;
import static dao.Trage_DAO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thuoc;

public class HoaDon_DAO {
	private static ChiTietHoaDon_DAO lcthd = new ChiTietHoaDon_DAO();

	public static ArrayList<HoaDon> getHoaDons() {
		ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
		Connection con = accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT *FROM HoaDon");
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString(1), new NhanVien(rs.getString(2)), new KhachHang(rs.getString(3)),
						rs.getString(4), rs.getString(5), chageTimeSQL(rs.getDate(6)),
						changeLoaiThuocToSQLFromUI(rs.getInt(7)), rs.getString(8), rs.getDouble(9),
						lcthd.getcChiTietHoaDons(rs.getString(1)));

				lhd.add(hd);

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

		return lhd;
	}

	public static boolean themHoaDon(HoaDon hd) {
		ArrayList<HoaDon> lhd = getHoaDons();
		Connection con = accessDataBase();
		PreparedStatement p = null;
		PreparedStatement pct = null;
		if (lhd.contains(hd.getMaHD())) {
			return false;
		} else {

			try {
				p = con.prepareStatement("INSERT INTO HoaDon VALUES (?,?,?,?,?,?,?,?,?)");
				p.setString(1, hd.getMaHD());
				p.setString(2, hd.getMaNV().getMaNV());
				p.setString(3, hd.getMaKh().getMaKH());
				p.setString(4, hd.getTenKH());
				p.setString(5, hd.getHinhThucThanhToan());
				p.setDate(6, java.sql.Date.valueOf(hd.getNgayTaoHoaDon()));
				int loaihd;
				if (hd.getLoaiHoaDon() == null) {
					p.setNull(7, java.sql.Types.BIT);
				} else {
					loaihd = hd.getLoaiHoaDon() ? 1 : 0;
					p.setInt(7, loaihd);
				}
				p.setString(8, hd.getTinhTrang());
				p.setDouble(9, hd.getTongTien());
				p.executeUpdate();
				ArrayList<ChiTietHoaDon> lcthd = hd.getListChiTietHoaDon();
				pct = con.prepareStatement("INSERT INTO ChiTietHoaDon VALUES(?,?,?,?,?,?,?)");
				for (ChiTietHoaDon chiTietHoaDon : lcthd) {
					pct.setString(1, chiTietHoaDon.getMaHD().getMaHD());
					pct.setString(2, chiTietHoaDon.getMaThuoc().getMaThuoc());
					pct.setString(3, chiTietHoaDon.getTenThuoc());
					pct.setString(4, chiTietHoaDon.getDonVi());
					pct.setInt(5, chiTietHoaDon.getSoLuong());
					pct.setDouble(6, chiTietHoaDon.getDonGia());
					pct.setDouble(7, chiTietHoaDon.getThanhTien());
					pct.executeUpdate();
				}

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

	public HoaDon getHoaDonByID(String maHD) {
		HoaDon hd = null;
		Connection con = accessDataBase();
		PreparedStatement p = null;
		ChiTietHoaDon_DAO ct = new ChiTietHoaDon_DAO();

		try {
			p = con.prepareStatement("SELECT * FROM HoaDon WHERE MaHD = ?");
			p.setString(1, maHD);

			try (ResultSet rs = p.executeQuery()) {
				if (rs.next()) {
					Boolean loaihd = null;
					int loaihdValue = rs.getInt(7);

					if (loaihdValue == 1 || loaihdValue == 0) {
						loaihd = loaihdValue == 1 ? true : false;
					}

					hd = new HoaDon(rs.getString(1), new NhanVien(rs.getString(2)), new KhachHang(rs.getString(3)),
							rs.getString(4), rs.getString(5), chageTimeSQL(rs.getDate(6)), loaihd, rs.getString(8),
							rs.getDouble(9), ct.getcChiTietHoaDons(maHD));
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

		return hd;
	}

//	update trang thai
	public void themHoaDonKhongTrongDanhSach(String trangthai) {

	}

}
