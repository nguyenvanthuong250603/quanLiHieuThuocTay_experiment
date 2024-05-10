package entity;

public class ChiTietHoaDon {
	private HoaDon maHD;
	private Thuoc maThuoc;


	private int soLuongThuoc;
	private double thanhTien;

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

	public double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [maHD=" + maHD + ", maThuoc=" + maThuoc + ", soLuongThuoc=" + soLuongThuoc
				+ ", thanhTien=" + thanhTien + "]";
	}
	public int getSoLuongThuoc() {
		return soLuongThuoc;
	}
	public void setSoLuongThuoc(int soLuongThuoc) {
		this.soLuongThuoc = soLuongThuoc;
	}
	public ChiTietHoaDon(HoaDon maHD, Thuoc maThuoc, int soLuongThuoc, double thanhTien) {
		super();
		this.maHD = maHD;
		this.maThuoc = maThuoc;
		this.soLuongThuoc = soLuongThuoc;
		this.thanhTien = thanhTien;
	}


	
}
