package dao;

import static connectDataBase.ConnectionData.accessDataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
