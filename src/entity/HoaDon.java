package entity;

import java.sql.Date;

public class HoaDon {
	private String maHD;
	private NhanVien maNV;
	private KhachHang maKH;
	private Date ngayTao;
	private Thuoc maThuoc;
	private String luuY;
	private double tongTien;
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public NhanVien getMaNV() {
		return maNV;
	}
	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}
	public KhachHang getMaKH() {
		return maKH;
	}
	public void setMaKH(KhachHang maKH) {
		this.maKH = maKH;
	}
	public Date getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}
	public Thuoc getMaThuoc() {
		return maThuoc;
	}
	public void setMaThuoc(Thuoc maThuoc) {
		this.maThuoc = maThuoc;
	}
	public String getLuuY() {
		return luuY;
	}
	public void setLuuY(String luuY) {
		this.luuY = luuY;
	}
	public double getTongTien() {
		return tongTien;
	}
	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}
	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", maNV=" + maNV + ", maKH=" + maKH + ", ngayTao=" + ngayTao + ", maThuoc="
				+ maThuoc + ", luuY=" + luuY + ", tongTien=" + tongTien + "]";
	}
	public HoaDon(String maHD, NhanVien maNV, KhachHang maKH, Date ngayTao, Thuoc maThuoc, String luuY,
			double tongTien) {
		super();
		this.maHD = maHD;
		this.maNV = maNV;
		this.maKH = maKH;
		this.ngayTao = ngayTao;
		this.maThuoc = maThuoc;
		this.luuY = luuY;
		this.tongTien = tongTien;
	}
	
	
}
