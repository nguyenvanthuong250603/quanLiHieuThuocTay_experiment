package entity;

public class NhaSanXuat {
	private String tenNSX,diaChiNSX;


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


	public String toString() {
		return "NhaSanXuat [  tenNSX=" + tenNSX + ", diaChiNSX=" + diaChiNSX + "]";
	}

	
	

	public NhaSanXuat( String tenNSX, String diaChiNSX) {
		

		this.tenNSX = tenNSX;
		this.diaChiNSX = diaChiNSX;
	}

	public NhaSanXuat(String tenNSX) {
	
		this.tenNSX = tenNSX;
	}




	

	



	
	
}
