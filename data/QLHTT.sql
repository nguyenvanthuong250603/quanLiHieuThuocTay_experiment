CREATE DATABASE QLHTT
GO
USE QLHTT

GO
CREATE TABLE NhanVien (
    MaNV NVARCHAR(50) not null PRIMARY KEY,
    HoTen NVARCHAR(255),
    GioiTinh BIT,
    NgaySinh DATE,
	Tuoi INT,	
    sdt VARCHAR(10),
    cccd VARCHAR(20),
	DiaChi NVARCHAR(255),
    ChucVu NVARCHAR(50),
	TinhTrang NVARCHAR(50),
    NgayVaoLam DATE,
	HinhAnh NVARCHAR(255)
);
GO
CREATE TABLE TaiKhoan (
 
    MaNV NVARCHAR(50) NOT NULL PRIMARY KEY,
    MatKhau NVARCHAR(255),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO

CREATE TABLE NhaSanXuat(
	TenNSX NVARCHAR(255) NOT NULL PRIMARY KEY,
	DiaChiNSX NVARCHAR(255)
);
GO
CREATE TABLE Thuoc(
	MaThuoc NVARCHAR(50) NOT NULL PRIMARY KEY,
	TenThuoc NVARCHAR(255),
	SoLuong INT,
	Gia FLOAT ,	
	LoaiThuoc NVARCHAR(50),
	NhaSanXuat NVARCHAR(255),
	NgaySanXuat DATE,
	NgayHetHan DATE,
	HinhAnh NVARCHAR(255),
	DonVi NVARCHAR(50),
	DangBaoChe NVARCHAR(50),
	DoTuoi NVARCHAR(50),
	ThanhPhan NVARCHAR(255),
	ChiDinh NVARCHAR(255),
	LieuDung NVARCHAR(255),
	BaoQuan NVARCHAR(255),
	MoTa NVARCHAR(255)
	FOREIGN KEY (NhaSanXuat)  REFERENCES NhaSanXuat(TenNSX)
);

GO 
CREATE TABLE KhachHang(
	MaKH NVARCHAR(50)  PRIMARY KEY,
	TenKH NVARCHAR(255),
	NgaySinh DATE,
	Tuoi INT,
	GioiTinh BIT,
	SDT NVARCHAR(11),
	DiaChi NVARCHAR(255),
	DiemThanhVien INT
);


GO
CREATE TABLE HoaDon(
	MaHD NVARCHAR(50) NOT NULL PRIMARY KEY,
	MaNV NVARCHAR(50),
	MaKH NVARCHAR(50),
	TenKH NVARCHAR(50),
	HinhThucThanhToan NVARCHAR(50), 
	NgayTaoHoaDon DATE,
	LoaiHoaDon BIT,
	TinhTrang NVARCHAR(255),
	TongTien FLOAT,
	Lydo NVARCHAR(255),
	FOREIGN KEY (MaNV) REFERENCES nhanVien(MaNV),
	FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
)
CREATE TABLE ChiTietHoaDon(
	MaHD NVARCHAR(50) NOT NULL,
	MaThuoc NVARCHAR(50) NOT NULL,
	TenThuoc NVARCHAR(255),
	DonVi NVARCHAR(50),
	SoLuong INT,
	DonGIA FLOAT,
	ThanhTien FLOAT,
	
	 PRIMARY KEY (MaHD, MaThuoc),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaThuoc) REFERENCES Thuoc(MaThuoc)
);

GO
INSERT INTO NhaSanXuat VALUES (N'CTY SX1',N'40 Nguyễn Huệ , Quận 1 ,HCM')
INSERT INTO NhaSanXuat VALUES (N'CTY SX2',N'13 Nam Văn , Gò Vấp,HCM')
GO 

INSERT INTO Thuoc VALUES ('TH005',N'Bảo thanh 3',20,10000,N'Kháng sinh','CTY SX2','2024-01-30','2025-01-30','C:\Users\ADMIN\Downloads\ech.jpg',N'Hộp 1 vĩ x 5 viên nén',N'Dạng viên nén',N'Mọi lứa tuổi','thanhphan2','thanhphan2','thanhphan2','thanhphan2','thanhphan2');
INSERT INTO Thuoc VALUES ('TH001',N'Bảo thanh 3',20,10000,N'Thuốc sát khuẩn , khử trùng','CTY SX1','2024-01-30','2025-01-30','C:\Users\ADMIN\Downloads\TRAI-NGHIEM-CUOC-SONG-DINH-CU-O-ANH-1.jpg',N'Hộp 1 vĩ x 5 viên nén',N'Dạng viên nén',N'Từ 2-11 tuổi','thanhphan2','thanhphan2','thanhphan2','thanhphan2','thanhphan2');
INSERT INTO Thuoc VALUES ('TH006',N'Bảo thanh 3',20,10000,N'Thuốc sát khuẩn , khử trùng','CTY SX1','2024-01-30','2025-01-30','C:\Users\ADMIN\Downloads\TRAI-NGHIEM-CUOC-SONG-DINH-CU-O-ANH-1.jpg',N'Hộp 1 vĩ x 5 viên nén',N'Dạng viên nén',N'Từ 2-11 tuổi','thanhphan2','thanhphan2','thanhphan2','thanhphan2','thanhphan2');

GO 


GO
INSERT INTO NhanVien VALUES ('NV001',N'Nguyễn Văn Thương',1,'2003-06-25',20,'0794571318','067203000247',N'ĐĂK NÔNG',N'Quản lý',N'Đang làm','2024-03-20','');


GO
INSERT INTO TaiKhoan VALUES('NV001','123')
GO  
INSERT INTO KhachHang VALUES('KH001',N'Nguyễn Văn Nam','2024-03-19','20',1,'01213562326','Đăk lăk',100)
INSERT INTO KhachHang VALUES('KH002',N'Nguyễn Thị Đào','2024-03-19','20',0,'01213562326','Đăk lăk',2000)
INSERT INTO KhachHang VALUES('KH003',N'Nguyễn Văn Hoàng','2024-03-19','20',0,'0794571318','Đăk lăk',20)
INSERT INTO HoaDon VALUES ('HĐ07040018486', 'NV001', null, '', N'Tiền mặt', '2024-04-07', 0, N'Mới bán', 0.0,'')
INSERT INTO HoaDon VALUES ('HĐ07040018485', 'NV001', 'KH001', '', N'Tiền mặt', '2024-04-07', null, N'Mới bán', 0.0,'')
 ------------------------------------------------------------
SELECT *FROM HoaDon 
SELECT *FROM KhachHang
SELECT *FROM ChiTietHoaDon
SELECT *FROM NhanVien
SELECT *FROM TaiKhoan
SELECT *FROM NhaSanXuat
SELECT *FROM Thuoc
SELECT *FROM HoaDon WHERE MaHD ='HĐ11040019268'
SELECT *FROM HoaDon WHERE LoaiHoaDon IS NULL;

------------------------------------
DELETE HoaDon WHERE MaHD ='HĐ11040019268'
delete ChiTietHoaDon