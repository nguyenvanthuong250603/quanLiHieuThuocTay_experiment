CREATE DATABASE QLHTT
USE QLHTT

GO
CREATE TABLE NhanVien (
    MaNV NVARCHAR(50) not null PRIMARY KEY,
    HoTen NVARCHAR(255),
    GioiTinh BIT,
    NgaySinh DATE,
    sdt VARCHAR(10),
    cccd VARCHAR(20),
    ChucVu NVARCHAR(50),
    NgayVaoLam DATE
);
GO
CREATE TABLE TaiKhoan (
 
    MaNV NVARCHAR(50) NOT NULL PRIMARY KEY,
    MatKhau NVARCHAR(255),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);
GO
CREATE TABLE Thuoc(
	MaThuoc NVARCHAR(50) NOT NULL PRIMARY KEY,
	TenThuoc NVARCHAR(255),
	SoLuong INT,
	Gia FLOAT ,
	DonVi NVARCHAR(50),
	LoaiThuoc NVARCHAR(50),
	DoTuoi NVARCHAR(50),
	NhaSanXuat NVARCHAR(255),
	NgaySanXuat DATE,
	NgayHetHan DATE,
	HinhAnh NVARCHAR(255),
	ThanhPhan NVARCHAR(255),
	ChiDinh NVARCHAR(255),
	LieuDung NVARCHAR(255),
	BaoQuan NVARCHAR(255),
	MoTa NVARCHAR(255)
	FOREIGN KEY (NhaSanXuat)  REFERENCES NhaSanXuat(TenNSX)
);

GO 
CREATE TABLE KhachHang(
	MaKH NVARCHAR(50) NOT NULL PRIMARY KEY,
	TenKH NVARCHAR(255),
	DoTuoi NVARCHAR(50),
	GioiTinh BIT,
	SDT varchar(11)
);

GO
CREATE TABLE HoaDon(
	MaHD NVARCHAR(50) NOT NULL PRIMARY KEY,
	MaNV NVARCHAR(50),
	MaKH NVARCHAR(50),
	NgayTao date,
	MaTHuoc NVARCHAR(50),
	
	LuuY NVARCHAR(255),
	TongTien float,
	FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
	FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
	FOREIGN KEY (MaThuoc) REFERENCES Thuoc(MaThuoc)
);
GO
CREATE TABLE NhaSanXuat(
	TenNSX NVARCHAR(255) NOT NULL PRIMARY KEY,
	DiaChiNSX NVARCHAR(255)
);
GO 
SELECT Thuoc.MaThuoc , Thuoc.TenThuoc,Thuoc.SoLuong,Thuoc.Gia,Thuoc.NhaSanXuat,Thuoc.NgaySanXuat,Thuoc.NgayHetHan FROM Thuoc INNER JOIN NhaSanXuat ON Thuoc.NhaSanXuat = NhaSanXuat.TenNSX  WHERE Thuoc.LoaiThuoc = 'Kháng sinh'

 GO 
INSERT INTO Thuoc VALUES ('TH005',N'Bảo thanh 3',20,10000,N'Hộp 1 vĩ x 5 viên nén',N'Kháng sinh',N'Mọi lứa tuổi','CTY SX2','2024-01-30','2025-01-30','ha2','thanhphan2','thanhphan2','thanhphan2','thanhphan2','thanhphan2');

GO

GO
INSERT INTO NhaSanXuat VALUES (N'CTY SX1',N'40 Nguyễn Huệ , Quận 1 ,HCM')
INSERT INTO NhaSanXuat VALUES (N'CTY SX2',N'13 Nam Văn , Gò Vấp,HCM')
--INSERT INTO  
GO
INSERT INTO NhanVien VALUES ('NV001',N'Nguyễn Văn Thương',1,'2003-06-25','0794571318','067203000247',N'Quản lí','2024-03-10')
GO
INSERT INTO taiKhoan VALUES ('NV001','123')
GO
SELECT *FROM NhanVien WHERE MaNV = 'NV001'
 