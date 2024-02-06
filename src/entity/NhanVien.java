package entity;

import java.time.LocalDate;

public class NhanVien {
	private String maNV,hoTen;
	private boolean gioiTinh;
	private LocalDate ngaySinh;
	private String sdt;
	private String cccd;
	private String chucVu;
	private LocalDate ngayVaoLam;
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public LocalDate getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getCccd() {
		return cccd;
	}
	public void setCccd(String cccd) {
		this.cccd = cccd;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public LocalDate getNgayVaoLam() {
		return ngayVaoLam;
	}
	public void setNgayVaoLam(LocalDate ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}
	public NhanVien(String maNV, String hoTen, boolean gioiTinh, LocalDate ngaySinh, String sdt, String cccd,
			String chucVu, LocalDate ngayVaoLam) {
		super();
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.sdt = sdt;
		this.cccd = cccd;
		this.chucVu = chucVu;
		this.ngayVaoLam = ngayVaoLam;
	}
	public NhanVien(String maNV) {
	
		this.maNV = maNV;
	}
	
	
	

}
