package entity;

import java.time.LocalDate;

public class Thuoc {
	private String maThuoc,tenThuoc;
	private int soLuong;
	private double gia;
	private String donVi,loaiThuoc,doTuoi,nhaSanXuat;
	private LocalDate ngaySanXuat,ngayHetHan;
	private String hinhAnh;
	public Thuoc(String maThuoc, String tenThuoc, int soLuong, double gia, String donVi, String loaiThuoc,
			String doTuoi, String nhaSanXuat, LocalDate ngaySanXuat, LocalDate ngayHetHan, String hinhAnh) {
		
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.soLuong = soLuong;
		this.gia = gia;
		this.donVi = donVi;
		this.loaiThuoc = loaiThuoc;
		this.doTuoi = doTuoi;
		this.nhaSanXuat = nhaSanXuat;
		this.ngaySanXuat = ngaySanXuat;
		this.ngayHetHan = ngayHetHan;
		this.hinhAnh = hinhAnh;
	}
	public String getMaThuoc() {
		return maThuoc;
	}
	public void setMaThuoc(String maThuoc) {
		this.maThuoc = maThuoc;
	}
	public String getTenThuoc() {
		return tenThuoc;
	}
	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getGia() {
		return gia;
	}
	public void setGia(double gia) {
		this.gia = gia;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public String getLoaiThuoc() {
		return loaiThuoc;
	}
	public void setLoaiThuoc(String loaiThuoc) {
		this.loaiThuoc = loaiThuoc;
	}
	public String getDoTuoi() {
		return doTuoi;
	}
	public void setDoTuoi(String doTuoi) {
		this.doTuoi = doTuoi;
	}
	public String getNhaSanXuat() {
		return nhaSanXuat;
	}
	public void setNhaSanXuat(String nhaSanXuat) {
		this.nhaSanXuat = nhaSanXuat;
	}
	public LocalDate getNgaySanXuat() {
		return ngaySanXuat;
	}
	public void setNgaySanXuat(LocalDate ngaySanXuat) {
		this.ngaySanXuat = ngaySanXuat;
	}
	public LocalDate getNgayHetHan() {
		return ngayHetHan;
	}
	public void setNgayHetHan(LocalDate ngayHetHan) {
		this.ngayHetHan = ngayHetHan;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public Thuoc(String maThuoc) {
		super();
		this.maThuoc = maThuoc;
	}
	
	
}
