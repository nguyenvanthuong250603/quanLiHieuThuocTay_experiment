package dao;

import static connectDataBase.ConnectionData.accessDataBase;
import static dao.Trage_DAO.chageGenderToSQL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.KhachHang;
import entity.NhaSanXuat;
import entity.NhanVien;

public class NhaSanXuat_DAO {
	public ArrayList<NhaSanXuat> getNhaSanXuatDataBase() {
		ArrayList<NhaSanXuat> list_nhaSanXuat = new ArrayList<NhaSanXuat>();
		Connection con = accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT *FROM NhaSanXuat");
			while (rs.next()) {
				NhaSanXuat nsx = new NhaSanXuat(rs.getString(1), rs.getString(2), rs.getString(3));
				list_nhaSanXuat.add(nsx);
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
		return list_nhaSanXuat;
	}

	public boolean themKhachHang(NhaSanXuat kh) {
	    Connection con = null;
	    PreparedStatement p = null;

	    try {
	        con = connectDataBase.ConnectionData.accessDataBase();
	        p = con.prepareStatement("INSERT INTO NhaSanXuat (MaNSX, TenNSX, DiaChiNSX) VALUES (?, ?, ?)");
	        p.setString(1, kh.getMaNSX());
	        p.setString(2, kh.getTenNSX());
	        p.setString(3, kh.getDiaChiNSX());

	        int rowsAffected = p.executeUpdate();
	        return rowsAffected > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (p != null) {
	                p.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public NhaSanXuat getNhanVienFindByID(String maNV) {
		NhaSanXuat nv = new NhaSanXuat();
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {
			p = con.prepareStatement("SELECT *FROM NhaSanXuat WHERE MaNSX= ?");
			p.setString(1, maNV);
			try (ResultSet rs = p.executeQuery()) {
				while (rs.next()) {
					nv = new NhaSanXuat(rs.getString(1), rs.getString(2), rs.getString(3));
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

	public boolean xoaNhanVien(String ma) {
		Connection con = connectDataBase.ConnectionData.accessDataBase();
		PreparedStatement p = null;
		try {

			p = con.prepareStatement("DELETE NhaSanXuat WHERE MaNSX = ?");
			p.setString(1, ma);

			p.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	public boolean updateNSX(String ten,String diaChi,String maNSX) {
	    Connection con = null;
	    PreparedStatement p = null;

	    try {
	        con = connectDataBase.ConnectionData.accessDataBase();
	        p = con.prepareStatement("UPDATE NhaSanXuat  SET  TenNSX  = ? , DiaChiNSX = ? WHERE MaNSX = ?");
	        p.setString(1, ten);
	        p.setString(2, diaChi);
	        p.setString(3, maNSX);

	        int rowsAffected = p.executeUpdate();
	        return rowsAffected > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (p != null) {
	                p.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

}
