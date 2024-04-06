package entity;

public class ChiTietHoaDon {
	private HoaDon maHD;
	private Thuoc maThuoc;
	private String tenThuoc;
	public String getTenThuoc() {
		return tenThuoc;
	}
	public void setTenThuoc(String tenThuoc) {
		this.tenThuoc = tenThuoc;
	}
	private String donVi;
	private int soLuong;
	private double donGia,thanhTien;

	public HoaDon getMaHD() {
		return maHD;
	}
	public void setMaHD(HoaDon maHD) {
		this.maHD = maHD;
	}
	public Thuoc getMaThuoc() {
		return maThuoc;
	}
	public void setMaThuoc(Thuoc maThuoc) {
		this.maThuoc = maThuoc;
	}
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [maHD=" + maHD + ", maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", donVi=" + donVi
				+ ", soLuong=" + soLuong + ", donGia=" + donGia + ", thanhTien=" + thanhTien + "]";
	}
	public ChiTietHoaDon(HoaDon maHD, Thuoc maThuoc, String tenThuoc, String donVi, int soLuong, double donGia,
			double thanhTien) {
		super();
		this.maHD = maHD;
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.donVi = donVi;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
	}


	
}
