package entity;

public class chiTietThuoc {
	private Thuoc maThuoc;
	private String chiDinh,lieuDung,baoQuan,diaChiNSX,moTa;
	public Thuoc getMaThuoc() {
		return maThuoc;
	}
	public void setMaThuoc(Thuoc maThuoc) {
		this.maThuoc = maThuoc;
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
	public String getDiaChiNSX() {
		return diaChiNSX;
	}
	public void setDiaChiNSX(String diaChiNSX) {
		this.diaChiNSX = diaChiNSX;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public chiTietThuoc(Thuoc maThuoc, String chiDinh, String lieuDung, String baoQuan, String diaChiNSX, String moTa) {
		super();
		this.maThuoc = maThuoc;
		this.chiDinh = chiDinh;
		this.lieuDung = lieuDung;
		this.baoQuan = baoQuan;
		this.diaChiNSX = diaChiNSX;
		this.moTa = moTa;
	}
	
}
