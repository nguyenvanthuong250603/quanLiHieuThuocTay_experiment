package entity;

public class taiKhoan {
	private NhanVien maNV;
	private String matKhau;
	
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}


	public NhanVien getMaNV() {
		return maNV;
	}
	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}
	public taiKhoan(NhanVien maNV, String matKhau) {
		
		this.maNV = maNV;
		this.matKhau = matKhau;
	}
	@Override
	public String toString() {
		return "taiKhoan [maNV=" + maNV + ", matKhau=" + matKhau + "]";
	}


	
	
	

}
