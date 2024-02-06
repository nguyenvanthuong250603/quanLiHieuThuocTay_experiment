package entity;

public class taiKhoan {
	private String tenTaiKhoan,matKhau;
	private NhanVien maNV;
	public String getTenTaiKhoan() {
		return tenTaiKhoan;
	}
	public void setTenTaiKhoan(String tenTaiKhoan) {
		this.tenTaiKhoan = tenTaiKhoan;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public taiKhoan(String tenTaiKhoan, String matKhau, NhanVien maNV) {
	
		this.tenTaiKhoan = tenTaiKhoan;
		this.matKhau = matKhau;
		this.maNV = maNV;
	}
	public NhanVien getMaNV() {
		return maNV;
	}
	public void setMaNV(NhanVien maNV) {
		this.maNV = maNV;
	}
	@Override
	public String toString() {
		return "taiKhoan [tenTaiKhoan=" + tenTaiKhoan + ", matKhau=" + matKhau + ", maNV=" + maNV + "]";
	}
	
	
	

}
