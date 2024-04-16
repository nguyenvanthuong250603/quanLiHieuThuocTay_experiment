package dao;

import static connectDataBase.ConnectionData.accessDataBase;
import static dao.Trage_DAO.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

	public ArrayList<HoaDon> getHoaDons() {
		ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
		Connection con = accessDataBase();
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT *FROM HoaDon");
			while (rs.next()) {
				HoaDon hd = new HoaDon(rs.getString(1), new NhanVien(rs.getString(2)), new KhachHang(rs.getString(3)),
						rs.getString(4), rs.getString(5), chageTimeSQL(rs.getDate(6)),
						changeLoaiThuocToSQLFromUI(rs.getInt(7)), rs.getString(8), rs.getDouble(9),rs.getString(10),
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

	public boolean themHoaDon(HoaDon hd) {
		ArrayList<HoaDon> lhd = getHoaDons();
		Connection con = accessDataBase();
		PreparedStatement p = null;
		PreparedStatement pct = null;
		String maHD = hd.getMaHD();
		if (lhd.contains(hd)) {
			return false;
		} else {

			try {
				p = con.prepareStatement("INSERT INTO HoaDon VALUES (?,?,?,?,?,?,?,?,?,?)");
				p.setString(1, maHD);
				p.setString(2, hd.getMaNV().getMaNV());

				if (hd.getMaKh().getMaKH().equals("")) {
					p.setNull(3, java.sql.Types.VARCHAR);
				} else {
					p.setString(3, hd.getMaKh().getMaKH());
				}

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
				
					p.setString(10, hd.getLyDo());
							
				

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
							rs.getDouble(9), rs.getString(10),ct.getcChiTietHoaDons(maHD));
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
	public boolean themHoaDonVaoLoai(Boolean loai, String trangthai, String ma) {

		Connection con = accessDataBase();
		PreparedStatement p = null;

		try {
			p = con.prepareStatement("UPDATE HoaDon SET LoaiHoaDon = ?, TinhTrang = ?  WHERE MaHD = ?");
			int loaihd;

			if (loai == null) {
				p.setNull(1, java.sql.Types.BIT);
			} else {
				loaihd = loai ? 1 : 0;
				p.setInt(1, loaihd);
			}

			p.setString(2, trangthai);
			p.setString(3, ma);

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

	public ArrayList<HoaDon> getHoaDonToLuuTam(String sdt, int LoaiHD) {
		ArrayList<HoaDon> lhd = new ArrayList<HoaDon>();
		Connection con = accessDataBase();
		PreparedStatement p = null;
		try {
			
			if (sdt.equals("")) {
				p = con.prepareStatement("SELECT *FROM HoaDon WHERE  LoaiHoaDon " + (LoaiHD==3 ? "IS NULL" : "= ?"));
				if (LoaiHD !=3 ) {
				    p.setInt(1, LoaiHD);
				}
//				System.out.println("hiiiii");

			}else if(!sdt.equals("")&&LoaiHD==3){
				p = con.prepareStatement(
						"SELECT *FROM HoaDon WHERE LoaiHoaDon IS NULL AND  MaKH IN (SELECT MaKH FROM KhachHang WHERE SDT =?)");
			
				p.setString(1, sdt);
//				System.out.println("hiii");
			}
			else if (LoaiHD==4) {
				System.out.println(LoaiHD);
				p = con.prepareStatement(
						"SELECT *FROM HoaDon WHERE  MaKH IN (SELECT MaKH FROM KhachHang WHERE SDT =?)");
				p.setString(1, sdt);
//				System.out.println("hii");
			}
			
			else {
				p = con.prepareStatement(
						"SELECT *FROM HoaDon WHERE LoaiHoaDon = ? AND  MaKH IN (SELECT MaKH FROM KhachHang WHERE SDT =?)");
				p.setInt(1, LoaiHD);
				p.setString(2, sdt);
//				System.out.println("hi");

			}
			try (ResultSet rs = p.executeQuery()) {
				while (rs.next()) {
					HoaDon hd = new HoaDon(rs.getString(1), new NhanVien(rs.getString(2)),
							new KhachHang(rs.getString(3)), rs.getString(4), rs.getString(5),
							chageTimeSQL(rs.getDate(6)), changeLoaiThuocToSQLFromUI(rs.getInt(7)), rs.getString(8),
							rs.getDouble(9), rs.getString(10),lcthd.getcChiTietHoaDons(rs.getString(1)));

					lhd.add(hd);
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

		return lhd;
	}
	
	public boolean themHoaDonVaoLoaiLyDo(Boolean loai, String trangthai, String ma, String lyDo) {
	    Connection con = accessDataBase();
	    PreparedStatement p = null;

	    try {
	        String sql = "UPDATE HoaDon SET LoaiHoaDon = ?, TinhTrang = ?, Lydo = ? WHERE MaHD = ?";
	        p = con.prepareStatement(sql);
	        
	        int loaihd;
	        if (loai == null) {
	            p.setNull(1, java.sql.Types.BIT);
	        } else {
	            loaihd = loai ? 1 : 0;
	            p.setInt(1, loaihd);
	        }
	        p.setString(2, trangthai);
	        p.setString(3, lyDo); 
	        p.setString(4, ma);

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

	
	
	public boolean xoaHoaDonLuuTam(String maHD) {
		Connection con = accessDataBase();
		PreparedStatement p =null;
		
		try {
			p  = con.prepareStatement("DELETE HoaDon " +(!maHD.equals("")? " WHERE  MaHD= ?": "WHERE LoaiHoaDon IS NULL"));
			if(!maHD.equals("")) {
				p.setString(1, maHD);
			}
			p.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		
	}
	public int thongKeHoaDon(String loai,LocalDate lv) {
		Connection con =accessDataBase();
		PreparedStatement p = null;
		int soLuongHoaDon = 0;
		try {
			p =con.prepareStatement("SELECT COUNT(*) AS SoLuong FROM HoaDon WHERE TinhTrang = ? AND NgayTaoHoaDon = ?");
			p.setString(1, loai);
			p.setDate(2, java.sql.Date.valueOf(lv));
			try (ResultSet rs = p.executeQuery()){
				while (rs.next()) {
					 soLuongHoaDon = rs.getInt("SoLuong");
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return soLuongHoaDon;
	}
	
	public int thongKeHoaDonThangNay() {
		Connection con =accessDataBase();
		PreparedStatement p = null;
		int soLuongHoaDon = 0;
		try {
			p =con.prepareStatement("SELECT * FROM HoaDon WHERE MONTH(NgayTaoHoaDon) = 4 ");
			try (ResultSet rs = p.executeQuery()){
				while (rs.next()) {
					 soLuongHoaDon = rs.getInt("SoLuong");
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return soLuongHoaDon;
	}
}
