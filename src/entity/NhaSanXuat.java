package entity;

public class NhaSanXuat {
	private String maNSX, tenNSX, diaChiNSX;

	public String getMaNSX() {
		return maNSX;
	}

	public void setMaNSX(String maNSX) {
		this.maNSX = maNSX;
	}

	public String getTenNSX() {
		return tenNSX;
	}

	public void setTenNSX(String tenNSX) {
		this.tenNSX = tenNSX;
	}

	public String getDiaChiNSX() {
		return diaChiNSX;
	}

	public void setDiaChiNSX(String diaChiNSX) {
		this.diaChiNSX = diaChiNSX;
	}

	public NhaSanXuat(String maNSX, String tenNSX, String diaChiNSX) {
		super();
		this.maNSX = maNSX;
		this.tenNSX = tenNSX;
		this.diaChiNSX = diaChiNSX;
	}

	@Override
	public String toString() {
		return "NhaSanXuat [maNSX=" + maNSX + ", tenNSX=" + tenNSX + ", diaChiNSX=" + diaChiNSX + "]";
	}

	public NhaSanXuat() {

	}

	public NhaSanXuat(String tenNSX) {

		this.tenNSX = tenNSX;
	}

}
