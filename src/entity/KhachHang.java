package entity;

import java.time.LocalDate;

public class KhachHang {
	private String maKH, tenKH;
	private LocalDate ngaySinh;
	private int tuoi;
	private boolean gioiTinh;
	private String sDT, diaCHi;
	private int diemThanhVien;

	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", ngaySinh=" + ngaySinh + ", tuoi=" + tuoi
				+ ", gioiTinh=" + gioiTinh + ", sDT=" + sDT + ", diaCHi=" + diaCHi + ", diemThanhVien=" + diemThanhVien
				+ "]";
	}

	public KhachHang(String maKH, String tenKH, LocalDate ngaySinh, int tuoi, boolean gioiTinh, String sDT,
			String diaCHi, int diemThanhVien) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.ngaySinh = ngaySinh;
		this.tuoi = tuoi;
		this.gioiTinh = gioiTinh;
		this.sDT = sDT;
		this.diaCHi = diaCHi;
		this.diemThanhVien = diemThanhVien;
	}

	public int getDiemThanhVien() {
		return diemThanhVien;
	}

	public void setDiemThanhVien(int diemThanhVien) {
		this.diemThanhVien = diemThanhVien;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public int getTuoi() {
		return tuoi;
	}

	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getsDT() {
		return sDT;
	}

	public void setsDT(String sDT) {
		this.sDT = sDT;
	}

	public String getDiaCHi() {
		return diaCHi;
	}

	public void setDiaCHi(String diaCHi) {
		this.diaCHi = diaCHi;
	}

	public KhachHang() {
		super();
	}

	public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}

}
