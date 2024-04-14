package dao;

import static connectDataBase.ConnectionData.accessDataBase;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

import entity.NhaSanXuat;


public class NhaSanXuat_DAO {
	public ArrayList<NhaSanXuat> getNhaSanXuatDataBase(){
		ArrayList<NhaSanXuat> list_nhaSanXuat = new ArrayList<NhaSanXuat>();
		Connection con = accessDataBase();
		try {
			Statement s =con.createStatement();
			ResultSet rs = s.executeQuery("SELECT *FROM NhaSanXuat");
			while (rs.next()) {
				NhaSanXuat nsx = new NhaSanXuat(rs.getString(1), rs.getString(2));
				list_nhaSanXuat.add(nsx);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		return list_nhaSanXuat;
	}
}
