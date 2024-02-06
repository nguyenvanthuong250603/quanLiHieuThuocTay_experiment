package entity;

public class KhachHang {
	private String maKH,tenKH,doTuoi;
	private boolean gioiTinh;
	private String sDT;
	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", doTuoi=" + doTuoi + ", gioiTinh=" + gioiTinh
				+ ", sDT=" + sDT + "]";
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
	public String getDoTuoi() {
		return doTuoi;
	}
	public void setDoTuoi(String doTuoi) {
		this.doTuoi = doTuoi;
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
	public KhachHang(String maKH, String tenKH, String doTuoi, boolean gioiTinh, String sDT) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.doTuoi = doTuoi;
		this.gioiTinh = gioiTinh;
		this.sDT = sDT;
	}
	
	
}
