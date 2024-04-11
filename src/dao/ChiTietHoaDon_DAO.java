package dao;

import static connectDataBase.ConnectionData.accessDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Thuoc;

public class ChiTietHoaDon_DAO {
	public ArrayList<ChiTietHoaDon> getcChiTietHoaDons(String maHD) {
		ArrayList<ChiTietHoaDon> lcthd = new ArrayList<ChiTietHoaDon>();
		Connection con = accessDataBase();
		PreparedStatement p = null;

		try {
			p = con.prepareStatement("SELECT *FROM ChiTietHoaDon WHERE MaHD = ?");
			p.setString(1, maHD);
			try (ResultSet rs = p.executeQuery()) {
				while (rs.next()) {
					ChiTietHoaDon cthd = new ChiTietHoaDon(new HoaDon(rs.getString(1)), new Thuoc(rs.getString(2)),
							rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7));
					lcthd.add(cthd);
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

		return lcthd;
	}

	public boolean xoaChiTiet(String maHD,String maThuoc) {
		Connection con = accessDataBase();
		PreparedStatement p = null;
		try {
			p = con.prepareStatement("DELETE ChiTietHoaDon" + (!maHD.equals("") ? " WHERE MaHD= ? AND MaThuoc = ?" : ""));
			if (!maHD.equals("")) {
				p.setString(1, maHD);
				p.setString(2, maThuoc);
			}
			p.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public boolean upDateDoiTra(ArrayList<ChiTietHoaDon> cthd) {
		Connection con = accessDataBase();
		PreparedStatement p = null;
		try {

			String sql = "UPDATE ChiTietHoaDon " + "SET  SoLuong = ?,  ThanhTien = ? " + "WHERE MaHD = ?";
			p = con.prepareStatement(sql);
			for (ChiTietHoaDon chiTietHoaDon : cthd) {
				p.setInt(1, chiTietHoaDon.getSoLuong());
				p.setDouble(2, chiTietHoaDon.getThanhTien());
				p.setString(3, chiTietHoaDon.getMaHD().getMaHD());
				p.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
		Connection con = accessDataBase();
		ArrayList<ChiTietHoaDon> lcthd = getcChiTietHoaDons(chiTietHoaDon.getMaHD().getMaHD());
		PreparedStatement pct = null;
		if (lcthd.contains(chiTietHoaDon)) {
			return false;
		} else {
			try {
				pct = con.prepareStatement("INSERT INTO ChiTietHoaDon VALUES(?,?,?,?,?,?,?)");
				pct.setString(1, chiTietHoaDon.getMaHD().getMaHD());
				pct.setString(2, chiTietHoaDon.getMaThuoc().getMaThuoc());
				pct.setString(3, chiTietHoaDon.getTenThuoc());
				pct.setString(4, chiTietHoaDon.getDonVi());
				pct.setInt(5, chiTietHoaDon.getSoLuong());
				pct.setDouble(6, chiTietHoaDon.getDonGia());
				pct.setDouble(7, chiTietHoaDon.getThanhTien());
				pct.executeUpdate();
				return true;
			} catch (SQLException e) {

			
				return false;
			}finally {
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

}
