﻿CREATE DATABASE QLHTT
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
    NgayVaoLam DATE
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
	MaKH NVARCHAR(50) NOT NULL PRIMARY KEY,
	TenKH NVARCHAR(255),
	NgaySinh DATE,
	Tuoi INT,
	GioiTinh BIT,
	SDT NVARCHAR(11),
	DiaChi NVARCHAR(255)
);

GO
CREATE TABLE HoaDon(
	MaHD NVARCHAR(50) NOT NULL PRIMARY KEY,
	MaNV NVARCHAR(50),
	MaKH NVARCHAR(50),
	NgayTao DATE,
	MaTHuoc NVARCHAR(50),
	
	LuuY NVARCHAR(255),
	TongTien FLOAT,
	FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
	FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
	FOREIGN KEY (MaThuoc) REFERENCES Thuoc(MaThuoc)
);

GO
INSERT INTO NhaSanXuat VALUES (N'CTY SX1',N'40 Nguyễn Huệ , Quận 1 ,HCM')
INSERT INTO NhaSanXuat VALUES (N'CTY SX2',N'13 Nam Văn , Gò Vấp,HCM')
GO 

INSERT INTO Thuoc VALUES ('TH005',N'Bảo thanh 3',20,10000,N'Kháng sinh','CTY SX2','2024-01-30','2025-01-30','ha2',N'Hộp 1 vĩ x 5 viên nén',N'Dạng viên nén',N'Mọi lứa tuổi','thanhphan2','thanhphan2','thanhphan2','thanhphan2','thanhphan2');

GO
SELECT *FROM KhachHang
GO
INSERT INTO NhanVien VALUES ('NV001',N'Nguyễn Văn Thương',1,'2003-06-25',20,'0794571318','067203000247',N'ĐĂK NÔNG',N'Quản lý','2024-03-20');

GO
INSERT INTO TaiKhoan VALUES('NV001','123')
GO  
INSERT INTO KhachHang VALUES('KH001',N'Nguyễn Văn Nam','2024-03-19','20',1,'01213562326','Đăk lăk')
INSERT INTO KhachHang VALUES('KH002',N'Nguyễn Thị Đào','2024-03-19','20',0,'01213562326','Đăk lăk')
--INSERT INTO  

