package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class HoaDon {
	private String maHD;
	private NhanVien maNV;

	public ArrayList<ChiTietHoaDon> getListChiTietHoaDon() {
		return ListChiTietHoaDon;
	}

	public void setListChiTietHoaDon(ArrayList<ChiTietHoaDon> listChiTietHoaDon) {
		ListChiTietHoaDon = listChiTietHoaDon;
	}

	private KhachHang maKh;
	private String tenKH;

	private String hinhThucThanhToan;
	private LocalDate ngayTaoHoaDon;
	private Boolean loaiHoaDon;
	private String tinhTrang;
	private double tongTien;
	private String lyDo;
	private ArrayList<ChiTietHoaDon> ListChiTietHoaDon;

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

	public KhachHang getMaKh() {
		return maKh;
	}

	public void setMaKh(KhachHang maKh) {
		this.maKh = maKh;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getHinhThucThanhToan() {
		return hinhThucThanhToan;
	}

	public void setHinhThucThanhToan(String hinhThucThanhToan) {
		this.hinhThucThanhToan = hinhThucThanhToan;
	}

	public LocalDate getNgayTaoHoaDon() {
		return ngayTaoHoaDon;
	}

	public void setNgayTaoHoaDon(LocalDate ngayTaoHoaDon) {
		this.ngayTaoHoaDon = ngayTaoHoaDon;
	}

	public double getTongTien() {
		return tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public Boolean getLoaiHoaDon() {
		return loaiHoaDon;
	}

	public void setLoaiHoaDon(Boolean loaiHoaDon) {
		this.loaiHoaDon = loaiHoaDon;
	}

	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", maNV=" + maNV + ", maKh=" + maKh + ", tenKH=" + tenKH
				+ ", hinhThucThanhToan=" + hinhThucThanhToan + ", ngayTaoHoaDon=" + ngayTaoHoaDon + ", loaiHoaDon="
				+ loaiHoaDon + ", tinhTrang=" + tinhTrang + ", tongTien=" + tongTien + ", lyDo=" + lyDo
				+ ", ListChiTietHoaDon=" + ListChiTietHoaDon + "]";
	}

	public HoaDon(String maHD, NhanVien maNV, KhachHang maKh, String tenKH, String hinhThucThanhToan,
			LocalDate ngayTaoHoaDon, Boolean loaiHoaDon, String tinhTrang, double tongTien, String lyDo,
			ArrayList<ChiTietHoaDon> listChiTietHoaDon) {
		super();
		this.maHD = maHD;
		this.maNV = maNV;
		this.maKh = maKh;
		this.tenKH = tenKH;
		this.hinhThucThanhToan = hinhThucThanhToan;
		this.ngayTaoHoaDon = ngayTaoHoaDon;
		this.loaiHoaDon = loaiHoaDon;
		this.tinhTrang = tinhTrang;
		this.tongTien = tongTien;
		this.lyDo = lyDo;
		ListChiTietHoaDon = listChiTietHoaDon;
	}

	public HoaDon(String maHD, NhanVien maNV, KhachHang maKh, String tenKH, LocalDate ngayTaoHoaDon, double tongTien,
			String lyDo, ArrayList<ChiTietHoaDon> listChiTietHoaDon) {

		this.maHD = maHD;
		this.maNV = maNV;
		this.maKh = maKh;
		this.tenKH = tenKH;
		this.ngayTaoHoaDon = ngayTaoHoaDon;
		this.tongTien = tongTien;
		this.lyDo = lyDo;
		ListChiTietHoaDon = listChiTietHoaDon;
	}

	public String getLyDo() {
		return lyDo;
	}

	public void setLyDo(String lyDo) {
		this.lyDo = lyDo;
	}

	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	public HoaDon() {

	}

	public HoaDon(String maHD) {

		this.maHD = maHD;
	}

}
