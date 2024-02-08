package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Thuoc {


	private String maThuoc,tenThuoc;
	private int soLuong;
	private double gia;
	private String donVi,loaiThuoc,doTuoi;
	private NhaSanXuat tenNhaSanXuat;
	private LocalDate ngaySanXuat,ngayHetHan;
	private String hinhAnh;
	public Thuoc(String maThuoc, String tenThuoc, int soLuong, double gia, String donVi, String loaiThuoc,
			String doTuoi, NhaSanXuat tenNhaSanXuat, LocalDate ngaySanXuat, LocalDate ngayHetHan, String hinhAnh,
			String thanhPhan, String chiDinh, String lieuDung, String baoQuan, String moTa) {
		
		this.maThuoc = maThuoc;
		this.tenThuoc = tenThuoc;
		this.soLuong = soLuong;
		this.gia = gia;
		this.donVi = donVi;
		this.loaiThuoc = loaiThuoc;
		this.doTuoi = doTuoi;
		this.tenNhaSanXuat = tenNhaSanXuat;
		this.ngaySanXuat = ngaySanXuat;
		this.ngayHetHan = ngayHetHan;
		this.hinhAnh = hinhAnh;
		this.thanhPhan = thanhPhan;
		this.chiDinh = chiDinh;
		this.lieuDung = lieuDung;
		this.baoQuan = baoQuan;
		this.moTa = moTa;
	}
	@Override
	public String toString() {
		return "Thuoc [maThuoc=" + maThuoc + ", tenThuoc=" + tenThuoc + ", soLuong=" + soLuong + ", gia=" + gia
				+ ", donVi=" + donVi + ", loaiThuoc=" + loaiThuoc + ", doTuoi=" + doTuoi + ", tenNhaSanXuat="
				+ tenNhaSanXuat + ", ngaySanXuat=" + ngaySanXuat + ", ngayHetHan=" + ngayHetHan + ", hinhAnh=" + hinhAnh
				+ ", thanhPhan=" + thanhPhan + ", chiDinh=" + chiDinh + ", lieuDung=" + lieuDung + ", baoQuan="
				+ baoQuan + ", moTa=" + moTa + "]";
	}
	private String thanhPhan,chiDinh,lieuDung,baoQuan,moTa;
	
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
	

	public String getMaThuoc() {
		return maThuoc;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maThuoc);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thuoc other = (Thuoc) obj;
		return Objects.equals(maThuoc, other.maThuoc);
	}


	public NhaSanXuat getTenNhaSanXuat() {
		return tenNhaSanXuat;
	}
	public void setTenNhaSanXuat(NhaSanXuat tenNhaSanXuat) {
		this.tenNhaSanXuat = tenNhaSanXuat;
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
		this.maThuoc = maThuoc;
	}
	
	
}
