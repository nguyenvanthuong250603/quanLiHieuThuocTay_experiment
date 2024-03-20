package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Thuoc {


	public Thuoc() {
		super();
	}
	private String maThuoc,tenThuoc;
	private int soLuong;
	private double gia;
	private String loaiThuoc;
	private NhaSanXuat tenNhaSanXuat;
	private LocalDate ngaySanXuat,ngayHetHan;
	private String hinhAnh;
	private String donVi,dangBaoChe,doTuoi,thanhPhan,chiDinh,lieuDung,baoQuan,moTa;
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
	public String getLoaiThuoc() {
		return loaiThuoc;
	}
	public void setLoaiThuoc(String loaiThuoc) {
		this.loaiThuoc = loaiThuoc;
	}
	public NhaSanXuat getTenNhaSanXuat() {
		return tenNhaSanXuat;
	}
	public void setTenNhaSanXuat(NhaSanXuat tenNhaSanXuat) {
		this.tenNhaSanXuat = tenNhaSanXuat;
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
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	public String getDangBaoChe() {
		return dangBaoChe;
	}
	public void setDangBaoChe(String dangBaoChe) {
		this.dangBaoChe = dangBaoChe;
	}
	public String getDoTuoi() {
		return doTuoi;
	}
	public void setDoTuoi(String doTuoi) {
		this.doTuoi = doTuoi;
	}
	public String getThanhPhan() {
		return thanhPhan;
	}
	public void setThanhPhan(String thanhPhan) {
		this.thanhPhan = thanhPhan;
	}
	public String getChiDinh() {
		return chiDinh;
	}
	public void setChiDinh(String chiDinh) {
		this.chiDinh = chiDinh;
	}
	public String getLieuDung() {
		return lieuDung;
	}
	public void setLieuDung(String lieuDung) {
		this.lieuDung = lieuDung;
	}
	public String getBaoQuan() {
		return baoQuan;
	}
	public void setBaoQuan(String baoQuan) {
		this.baoQuan = baoQuan;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public Thuoc(String maThuoc, String tenThuoc, int soLuong, double gia, String loaiThuoc, NhaSanXuat tenNhaSanXuat,
			LocalDate ngaySanXuat, LocalDate ngayHetHan, String hinhAnh, String donVi, String dangBaoChe, String doTuoi,
			String thanhPhan, String chiDinh, String lieuDung, String baoQuan, String moTa) {
		super();
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.soLuong = soLuong;
		this.gia = gia;
		this.loaiThuoc = loaiThuoc;
		this.tenNhaSanXuat = tenNhaSanXuat;
		this.ngaySanXuat = ngaySanXuat;
		this.ngayHetHan = ngayHetHan;
		this.hinhAnh = hinhAnh;
		this.donVi = donVi;
		this.dangBaoChe = dangBaoChe;
		this.doTuoi = doTuoi;
		this.thanhPhan = thanhPhan;
		this.chiDinh = chiDinh;
		this.lieuDung = lieuDung;
		this.baoQuan = baoQuan;
		this.moTa = moTa;
	}
	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", soLuong=" + soLuong + ", gia=" + gia
				+ ", loaiThuoc=" + loaiThuoc + ", tenNhaSanXuat=" + tenNhaSanXuat + ", ngaySanXuat=" + ngaySanXuat
				+ ", ngayHetHan=" + ngayHetHan + ", hinhAnh=" + hinhAnh + ", donVi=" + donVi + ", dangBaoChe="
				+ dangBaoChe + ", doTuoi=" + doTuoi + ", thanhPhan=" + thanhPhan + ", chiDinh=" + chiDinh
				+ ", lieuDung=" + lieuDung + ", baoQuan=" + baoQuan + ", moTa=" + moTa + "]";
	}
	
	
	
}
