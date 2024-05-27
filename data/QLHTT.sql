
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
	MaNSX NVARCHAR(50) ,
	TenNSX NVARCHAR(255) NOT NULL PRIMARY KEY ,
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
	DiemThanhVien INT,
	XepHang NVARCHAR(255)
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
	SoLuongThuoc INT,
	ThanhTien FLOAT,
	 PRIMARY KEY (MaHD, MaThuoc),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaThuoc) REFERENCES Thuoc(MaThuoc)
);



INSERT NhaSanXuat VALUES (N'NSX27051122',N'Hà Nam', N'40 Nguyễn Huệ , Quận 1 ,HCM')
INSERT NhaSanXuat VALUES (N'NSX27051098',N'Nam Cao', N'13 Nam Văn , Gò Vấp,HCM')
INSERT NhaSanXuat VALUES (N'NSX27051123',N'Nam Hà', N'40 Nguyễn Huệ , Quận 1 ,HCM')
INSERT NhaSanXuat VALUES (N'NSX27051152',N'Phát Đạt', N'13 Nam Văn , Gò Vấp,HCM')
INSERT NhaSanXuat VALUES (N'NSX27051166',N'Thái Thịnh', N'40 Nguyễn Huệ , Quận 1 ,HCM')

GO
INSERT INTO NhanVien VALUES ('NV12061111',N'Nguyễn Văn Thương',1,'2003-06-25',20,'0794571318','067203000247',N'ĐĂK NÔNG',N'Quản lý',N'Đang làm','2024-03-20','');


GO
INSERT INTO TaiKhoan VALUES('NV12061111','123')
GO  
INSERT INTO KhachHang VALUES('KH12041151',N'Nguyễn Văn Nam','2024-03-19','20',1,'01213562326','Đăk lăk',100,N'Đồng')
INSERT INTO KhachHang VALUES('KH12057982',N'Nguyễn Thị Đào','2024-03-19','20',0,'01213562326','Đăk lăk',570,N'Bạc')
INSERT INTO KhachHang VALUES('KH12128882',N'Nguyễn Văn Hoàng','2024-03-19','20',1,'0794571318','Đăk lăk',1960,N'Vàng')

 ------------------------------------------------------------
SELECT *FROM KhachHang
SELECT *FROM HoaDon where TongTien > 0 and TongTien<120000
SELECT *FROM ChiTietHoaDon
SELECT *FROM NhanVien
SELECT *FROM TaiKhoan
SELECT *FROM NhaSanXuat
SELECT *FROM Thuoc WHERE MaThuoc = 'TH04130035'
SELECT *FROM HoaDon where LoaiHoaDon = 0
SELECT *FROM Thuoc WHERE MaThuoc = 'TH04050241';

SELECT COUNT(*) AS SoLuongHoaDon FROM HoaDon WHERE NgayTaoHoaDon > '2024-03-15';
SELECT * FROM HoaDon WHERE NgayTaoHoaDon BETWEEN '2024-04-01' AND '2024-04-14';
SELECT * FROM HoaDon WHERE MONTH(NgayTaoHoaDon) = 3 AND YEAR(NgayTaoHoaDon) = 2024;

------------------------------------
/*INSERT INTO HoaDon VALUES (N'HĐ1321312',N'NV12061111','KH1205798',N'Nguyễn Thị Đào',N'Tiền mặt','2024-03-1',1,N'Bán ra','200000','')

select *from HoaDon where MaHD like '%1998%'

select MaThuoc,sum (ThanhTien) , sum(SoLuongThuoc) from ChiTietHoaDon   group by MaThuoc

select * from ChiTietHoaDon group by MaThuoc
select MaThuoc,sum(ThanhTien)  ,sum( SoLuongThuoc) from ChiTietHoaDon   c inner join HoaDon h  on c.MaHD = h.MaHD where  MaThuoc ='TH04050001' group by MaThuoc

select MaThuoc,sum(ThanhTien)  , sum(SoLuongThuoc) from ChiTietHoaDon   c inner join HoaDon h  on c.MaHD = h.MaHD where  MaThuoc ='TH04050002'
*/
GO

INSERT INTO Thuoc (MaThuoc, TenThuoc, SoLuong, Gia, LoaiThuoc, NhaSanXuat, NgaySanXuat, NgayHetHan, HinhAnh, DonVi, DangBaoChe, DoTuoi, ThanhPhan, ChiDinh, LieuDung, BaoQuan, MoTa)
VALUES
 ('TH04051341', N'Paracetamol', 4, 20000, N'Thuốc giảm đau, hạ sốt', N'Thái Thịnh', '2023-05-15', '2024-05-19', 'C:\Users\ADMIN\Downloads\ptud\paracetamol.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 2-11 tuổi', N'Paracetamol', N'Điều trị đau nhẹ đến trung bình và hạ sốt', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),

	  ('TH04050341', N'Paracetamol', 8, 20000, N'Thuốc giảm đau, hạ sốt', N'Nam Hà', '2023-05-15', '2024-05-15', 'C:\Users\ADMIN\Downloads\ptud\paracetamol.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 2-11 tuổi', N'Paracetamol', N'Điều trị đau nhẹ đến trung bình và hạ sốt', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),	
  ('TH04050241', N'Paracetamol', 100, 20000, N'Thuốc giảm đau, hạ sốt', N'Nam Hà', '2023-05-15', '2024-05-15', 'C:\Users\ADMIN\Downloads\ptud\paracetamol.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 2-11 tuổi', N'Paracetamol', N'Điều trị đau nhẹ đến trung bình và hạ sốt', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	  ('TH04050141', N'Paracetamol', 9, 20000, N'Thuốc giảm đau, hạ sốt', N'Nam Hà', '2023-05-15', '2024-05-15', 'C:\Users\ADMIN\Downloads\ptud\paracetamol.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 2-11 tuổi', N'Paracetamol', N'Điều trị đau nhẹ đến trung bình và hạ sốt', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04050041', N'Paracetamol', 100, 20000, N'Thuốc giảm đau, hạ sốt', N'Nam Hà', '2023-05-15', '2024-05-15', 'C:\Users\ADMIN\Downloads\ptud\paracetamol.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 2-11 tuổi', N'Paracetamol', N'Điều trị đau nhẹ đến trung bình và hạ sốt', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050042', N'Ibuprofen', 100, 50000, N'Thuốc giảm đau, hạ sốt', N'Thái Thịnh', '2023-09-10', '2024-09-10', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_1.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 12-17 tuổi', N'Ibuprofen', N'Điều trị triệu chứng đau và viêm', N'1 viên mỗi 6 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04050043', N'Aspirin', 80, 40000, N'Thuốc giảm đau, hạ sốt', N'Nam Hà', '2023-10-20', '2024-10-20', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_2.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Mọi lứa tuổi', N'Aspirin', N'Điều trị đau và viêm', N'1 viên mỗi 4 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04050044', N'Naproxen', 120, 55000, N'Thuốc giảm đau, hạ sốt', N'Hà Nam', '2023-11-15', '2024-11-15', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_3.jpg', N'Hộp 1 chai 20 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Naproxen', N'Điều trị đau và viêm', N'1 viên mỗi 8 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050045', N'Acetaminophen', 120, 18000, N'Thuốc giảm đau, hạ sốt', N'Hà Nam', '2023-07-25', '2024-07-25', 'C:\Users\ADMIN\Downloads\ptud\acetaminophen.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 2-11 tuổi', N'Paracetamol', N'Điều trị đau và hạ sốt', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050046', N'Diclofenac', 90, 60000, N'Thuốc giảm đau, hạ sốt', N'Phát Đạt', '2023-12-05', '2024-12-05', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_4.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Mọi lứa tuổi', N'Diclofenac', N'Điều trị đau và viêm', N'1 viên mỗi 12 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050047', N'Ketoprofen', 120, 85000, N'Thuốc giảm đau, hạ sốt', N'Nam Hà', '2023-11-15', '2024-11-15', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_9.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Ketoprofen', N'Điều trị đau và viêm', N'1 viên mỗi 12 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050048', N'Tramadol', 40, 70000, N'Thuốc giảm đau', N'Phát Đạt', '2023-04-12', '2024-04-12', 'C:\Users\ADMIN\Downloads\ptud\tramadol.jpg', N'Hộp 1 chai 20 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Tramadol', N'Điều trị đau cấp tính và mãn tính', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050049', N'Codeine', 30, 60000, N'Thuốc giảm đau', N'Thái Thịnh', '2023-03-20', '2024-03-20', 'C:\Users\ADMIN\Downloads\ptud\codeine.jpg', N'Hộp 1 chai 20 viên', N'Viên nén', N'Từ 12-17 tuổi', N'Codeine', N'Điều trị đau trung bình đến nặng', N'1-2 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050050', N'Morphine', 20, 150000, N'Thuốc giảm đau', N'Phát Đạt', '2023-02-10', '2024-02-10', 'C:\Users\ADMIN\Downloads\ptud\morphine.jpg', N'Hộp 1 chai 10 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Morphine', N'Điều trị đau nặng', N'1 viên mỗi 4-6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04050001', N'Dexamethasone', 60, 38000, N'Thuốc đặc trị', N'Thái Thịnh', '2023-01-05', '2025-01-05', 'C:\Users\ADMIN\Downloads\ptud\Dexamethasone.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Dexamethasone', 'Corticosteroid', N'0.5-10 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050002', N'Ibuprofen', 80, 25000, N'Thuốc đặc trị', N'Nam Hà', '2023-10-20', '2025-10-20', 'C:\Users\ADMIN\Downloads\ptud\ibuprofen.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Ibuprofen', N'Giảm đau, chống viêm', N'400 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050003', N'Omeprazole', 50, 30000, N'Thuốc đặc trị', N'Thái Thịnh', '2023-09-10', '2025-09-10', 'C:\Users\ADMIN\Downloads\ptud\Omeprazole.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Omeprazole', N'Điều trị viêm loét dạ dày', N'20 mg/lần', N'Nơi khô ráo, dưới 30°C', 'Không'),
	('TH04050004', N'Amoxicillin', 120, 35000, N'Thuốc đặc trị', N'Hà Nam', '2023-08-05', '2025-08-05', 'C:\Users\ADMIN\Downloads\ptud\Amoxicillin.jpg', N'Hộp 1 chai 60 viên', N'Viên nén', N'Mọi lứa tuổi', 'Amoxicillin', N'Kháng sinh', N'500 mg/lần', N'Nơi khô mát, dưới 25°C', 'Không'),
	('TH04050005', N'Loratadine', 90, 28000, N'Thuốc đặc trị', N'Thái Thịnh', '2023-07-12', '2025-07-12', 'C:\Users\ADMIN\Downloads\ptud\Loratadine.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Loratadine', N'Giảm triệu chứng dị ứng', N'10 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050006', N'Prednisone', 70, 42000, N'Thuốc đặc trị', N'Thái Thịnh', '2023-06-25', '2025-06-25', 'C:\Users\ADMIN\Downloads\ptud\Prednisone.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Prednisone', 'Corticosteroid', N'5-60 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050007', N'Diazepam', 40, 50000, N'Thuốc đặc trị', N'Nam Cao', '2023-05-18', '2025-05-18', 'C:\Users\ADMIN\Downloads\ptud\Diazepam.jpg', N'Hộp 1 chai 20 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Diazepam', N'An thần, giảm lo âu', N'5-10 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050008', N'Cefuroxime', 110, 38000, N'Thuốc đặc trị', N'Hà Nam', '2023-04-30', '2025-04-30', 'C:\Users\ADMIN\Downloads\ptud\Cefuroxime.jpg', N'Hộp 1 chai 55 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Cefuroxime', N'Kháng sinh', N'125-500 mg/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050009', N'Ciprofloxacin', 65, 45000, N'Thuốc đặc trị', N'Thái Thịnh', '2023-03-14', '2025-03-14', 'C:\Users\ADMIN\Downloads\ptud\Ciprofloxacin.jpg', N'Hộp 1 chai 32 viên', 'NViên nén', N'Từ 18 tuổi trở lên', 'Ciprofloxacin', N'Kháng sinh', N'250-750 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050010', N'Simvastatin', 75, 48000, N'Thuốc đặc trị', N'Nam Cao', '2023-02-09', '2025-02-09', 'C:\Users\ADMIN\Downloads\ptud\Simvastatin.jpg', N'Hộp 1 chai 38 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Simvastatin', N'Giảm cholesterol', N'5-80 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050011', N'Dexamethasone', 60, 38000, N'Thuốc đặc trị', N'Nam Cao', '2023-01-05', '2025-01-05', 'C:\Users\ADMIN\Downloads\ptud\Dexamethasone.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Dexamethasone', 'Corticosteroid', N'0.5-10 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050012', N'Ibuprofen', 80, 25000, N'Thuốc đặc trị', N'Nam Hà', '2023-10-20', '2025-10-20', 'C:\Users\ADMIN\Downloads\ptud\ibuprofen.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Ibuprofen', N'Giảm đau, chống viêm', '400 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050013', N'Omeprazole', 50, 30000, N'Thuốc đặc trị', N'Nam Cao', '2023-09-10', '2025-09-10', 'C:\Users\ADMIN\Downloads\ptud\Omeprazole.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Omeprazole', N'Điều trị viêm loét dạ dày', '20 mg/lần', N'Nơi khô ráo, dưới 30°C', 'Không'),
	('TH04050014', N'Amoxicillin', 120, 35000, N'Thuốc đặc trị', N'Phát Đạt', '2023-08-05', '2025-08-05', 'C:\Users\ADMIN\Downloads\ptud\Amoxicillin.jpg', N'Hộp 1 chai 60 viên', N'Viên nén', N'Mọi lứa tuổi', 'Amoxicillin', N'Kháng sinh', '500 mg/lần', N'Nơi khô mát, dưới 25°C', 'Không'),
	('TH04050015', N'Loratadine', 90, 28000, N'Thuốc đặc trị', N'Phát Đạt', '2023-07-12', '2025-07-12', 'C:\Users\ADMIN\Downloads\ptud\Loratadine.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Loratadine', N'Giảm triệu chứng dị ứng', N'10 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050016', N'Prednisone', 70, 42000, N'Thuốc đặc trị', N'Nam Cao', '2023-06-25', '2025-06-25', 'C:\Users\ADMIN\Downloads\ptud\Prednisone.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Prednisone', 'Corticosteroid', N'5-60 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050017', N'Diazepam', 40, 50000, N'Thuốc đặc trị', N'Thái Thịnh', '2023-05-18', '2025-05-18', 'C:\Users\ADMIN\Downloads\ptud\Diazepam.jpg', N'Hộp 1 chai 20 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Diazepam', N'An thần, giảm lo âu', N'5-10 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050018', N'Cefuroxime', 110, 38000, N'Thuốc đặc trị', N'Hà Nam', '2023-04-30', '2025-04-30', 'C:\Users\ADMIN\Downloads\ptud\Cefuroxime.jpg', N'Hộp 1 chai 55 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Cefuroxime', N'Kháng sinh', N'125-500 mg/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050019', N'Ciprofloxacin', 65, 45000, N'Thuốc đặc trị', N'Nam Cao', '2023-03-14', '2025-03-14', 'C:\Users\ADMIN\Downloads\ptud\Ciprofloxacin.jpg', N'Hộp 1 chai 32 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Ciprofloxacin', N'Kháng sinh', N'250-750 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050020', N'Simvastatin', 75, 48000, N'Thuốc đặc trị', N'Hà Nam', '2023-02-09', '2025-02-09', 'C:\Users\ADMIN\Downloads\ptud\Simvastatin.jpg', N'Hộp 1 chai 38 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Simvastatin', N'Giảm cholesterol', N'5-80 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050101', N'Penicillin', 90, 30000, N'Kháng sinh', N'Hà Nam', '2023-12-20', '2025-12-20', 'C:\Users\ADMIN\Downloads\ptud\Penicillin.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Mọi lứa tuổi', 'Penicillin', N'Kháng sinh', N'250-500 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050102', N'Azithromycin', 70, 42000, N'Kháng sinh', N'Hà Nam', '2023-11-28', '2025-11-28', 'C:\Users\ADMIN\Downloads\ptud\Azithromycin.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Azithromycin', N'Kháng sinh', N'250-1000 mg/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050103', N'Cephalexin', 80, 35000, N'Kháng sinh', N'Phát Đạt', '2023-10-15', '2025-10-15', 'C:\Users\ADMIN\Downloads\ptud\Cephalexin.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Cephalexin', N'Kháng sinh', N'250-500 mg/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050104', N'Clarithromycin', 70, 45000, N'Kháng sinh', N'Phát Đạt', '2023-09-22', '2025-09-22', 'C:\Users\ADMIN\Downloads\ptud\Clarithromycin.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Clarithromycin', N'Kháng sinh', N'250-500 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050105', N'Levofloxacin', 90, 50000, N'Kháng sinh', N'Phát Đạt', '2023-08-30', '2025-08-30', 'C:\Users\ADMIN\Downloads\ptud\Levofloxacin.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Levofloxacin', N'Kháng sinh', N'250-750 mg/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050106', N'Metronidazole', 100, 40000, N'Kháng sinh', N'Phát Đạt', '2023-07-25', '2025-07-25', 'C:\Users\ADMIN\Downloads\ptud\Metronidazole.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Metronidazole', N'Kháng sinh', N'250-500 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050107', N'Tetracycline', 110, 42000, N'Kháng sinh', N'Nam Cao', '2023-06-12', '2025-06-12', 'C:\Users\ADMIN\Downloads\ptud\Tetracycline.jpg', N'Hộp 1 chai 55 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Tetracycline', N'Kháng sinh', N'250-500 mg/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050108', N'Amoxicillin', 120, 35000, N'Kháng sinh', N'Phát Đạt', '2023-05-05', '2025-05-05', 'C:\Users\ADMIN\Downloads\ptud\Amoxicillin.jpg', N'Hộp 1 chai 60 viên', N'Viên nén', N'Mọi lứa tuổi', 'Amoxicillin', N'Kháng sinh', N'500 mg/lần', N'Nơi khô mát, dưới 25°C', 'Không'),
	('TH04050109', N'Ciprofloxacin', 65, 45000, N'Kháng sinh', N'Phát Đạt', '2023-04-18', '2025-04-18', 'C:\Users\ADMIN\Downloads\ptud\Ciprofloxacin.jpg', N'Hộp 1 chai 32 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Ciprofloxacin', N'Kháng sinh', N'250-750 mg/lần', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050110', N'Doxycycline', 85, 48000, N'Kháng sinh', N'Thái Thịnh', '2023-03-10', '2025-03-10', 'C:\Users\ADMIN\Downloads\ptud\Doxycycline.jpg', N'Hộp 1 chai 42 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Doxycycline', N'Kháng sinh', N'100-200 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050201', N'Omeprazole', 50, 30000, N'Thuốc tiêu hóa', N'Thái Thịnh', '2023-12-01', '2025-12-01', 'C:\Users\ADMIN\Downloads\ptud\Omeprazole.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Omeprazole', N'Điều trị viêm loét dạ dày', N'20 mg/lần', N'Nơi khô ráo, dưới 30°C', 'Không'),
	('TH04050202', N'Ranitidine', 60, 35000, N'Thuốc tiêu hóa', N'Phát Đạt', '2023-11-05', '2025-11-05', 'C:\Users\ADMIN\Downloads\ptud\Ranitidine.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Ranitidine', N'Điều trị loét dạ dày, trào ngược dạ dày', N'150 mg/ngày', N'Nơi khô mát, dưới 25°C', 'Không'),
	('TH04050203', N'Famotidine', 70, 38000, N'Thuốc tiêu hóa', N'Thái Thịnh', '2023-10-20', '2025-10-20', 'C:\Users\ADMIN\Downloads\ptud\Famotidine.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Famotidine', N'Điều trị loét dạ dày, trào ngược dạ dày', N'20-40 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050204', N'Lansoprazole', 80, 42000, N'Thuốc tiêu hóa', N'Nam Hà', '2023-09-15', '2025-09-15', 'C:\Users\ADMIN\Downloads\ptud\Lansoprazole.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Lansoprazole', N'Điều trị viêm loét dạ dày', N'15-30 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050205', N'Esomeprazole', 90, 45000, N'Thuốc tiêu hóa', N'Hà Nam', '2023-08-25', '2025-08-25', 'C:\Users\ADMIN\Downloads\ptud\Esomeprazole.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Esomeprazole', N'Điều trị viêm loét dạ dày', N'20-40 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050206', N'Pantoprazole', 100, 48000, N'Thuốc tiêu hóa', N'Hà Nam', '2023-07-10', '2025-07-10', 'C:\Users\ADMIN\Downloads\ptud\Pantoprazole.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Pantoprazole', N'Điều trị viêm loét dạ dày', N'40 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050207', N'Dicyclomine', 60, 40000, N'Thuốc tiêu hóa', N'Nam Cao', '2023-06-05', '2025-06-05', 'C:\Users\ADMIN\Downloads\ptud\Dicyclomine.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Dicyclomine', N'Giảm co thắt ruột', N'10-20 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050208', N'Loperamide', 70, 32000, N'Thuốc tiêu hóa', N'Hà Nam', '2023-05-12', '2025-05-12', 'C:\Users\ADMIN\Downloads\ptud\Loperamide.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Loperamide', N'Giảm triệu chứng tiêu chảy', N'2-4 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050209', N'Sucralfate', 80, 38000, N'Thuốc tiêu hóa', N'Nam Cao', '2023-04-18', '2025-04-18', 'C:\Users\ADMIN\Downloads\ptud\Sucralfate.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Sucralfate', N'Bảo vệ niêm mạc dạ dày', N'1 g/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050210', N'Protonix', 90, 50000, N'Thuốc tiêu hóa', N'Thái Thịnh', '2023-03-25', '2025-03-25', 'C:\Users\ADMIN\Downloads\ptud\Protonix.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Protonix', N'Điều trị viêm loét dạ dày', N'40 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050221', N'Omeprazole', 50, 30000, N'Thuốc tiêu hóa', N'Nam Cao', '2023-12-01', '2025-12-01', 'C:\Users\ADMIN\Downloads\ptud\Omeprazole.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Omeprazole', N'Điều trị viêm loét dạ dày', N'20 mg/lần', N'Nơi khô ráo, dưới 30°C', 'Không'),
	('TH04050222', N'Ranitidine', 60, 35000, N'Thuốc tiêu hóa', N'Nam Cao', '2023-11-05', '2025-11-05', 'C:\Users\ADMIN\Downloads\ptud\Ranitidine.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 12 tuổi trở lên', 'Ranitidine', N'Điều trị loét dạ dày, trào ngược dạ dày', N'150 mg/ngày', N'Nơi khô mát, dưới 25°C', 'Không'),
	('TH04050223', N'Famotidine', 70, 38000, N'Thuốc tiêu hóa', N'Phát Đạt', '2023-10-20', '2025-10-20', 'C:\Users\ADMIN\Downloads\ptud\Famotidine.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Famotidine', 'Điều trị loét dạ dày, trào ngược dạ dày', '20-40 mg/ngày', 'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050224', N'Lansoprazole', 80, 42000, N'Thuốc tiêu hóa', N'Nam Hà', '2023-09-15', '2025-09-15', 'C:\Users\ADMIN\Downloads\ptud\Lansoprazole.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 12-17 tuổi', 'Lansoprazole', N'Điều trị viêm loét dạ dày', N'15-30 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050225', N'Esomeprazole', 90, 45000, N'Thuốc tiêu hóa', N'Phát Đạt', '2023-08-25', '2025-08-25', 'C:\Users\ADMIN\Downloads\ptud\Esomeprazole.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Esomeprazole', N'Điều trị viêm loét dạ dày', N'20-40 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050226', N'Pantoprazole', 100, 48000, N'Thuốc tiêu hóa', N'Thái Thịnh', '2023-07-10', '2025-07-10', 'C:\Users\ADMIN\Downloads\ptud\Pantoprazole.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Pantoprazole', N'Điều trị viêm loét dạ dày', N'40 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050227', N'Dicyclomine', 60, 40000, N'Thuốc tiêu hóa', N'Hà Nam', '2023-06-05', '2025-06-05', 'C:\Users\ADMIN\Downloads\ptud\Dicyclomine.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Dicyclomine', N'Giảm co thắt ruột', N'10-20 mg/ngày', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04050228', N'Loperamide', 70, 32000, N'Thuốc tiêu hóa', N'Phát Đạt', '2023-05-12', '2025-05-12', 'C:\Users\ADMIN\Downloads\ptud\Loperamide.jpg', N'Hộp 1 chai 35 viên', N'Viên nén', N'Từ 2-11 tuổi', 'Loperamide', N'Giảm triệu chứng tiêu chảy', N'2-4 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không'),
	('TH04050229', N'Sucralfate', 80, 38000, N'Thuốc tiêu hóa', N'Thái Thịnh', '2023-04-18', '2025-04-18', 'C:\Users\ADMIN\Downloads\ptud\Sucralfate.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Sucralfate', N'Bảo vệ niêm mạc dạ dày', N'1 g/lần', N'Nơi khô mát, dưới 30°C', 'Không'),
	('TH04051021', N'Protonix', 90, 50000, N'Thuốc tiêu hóa', N'Nam Hà', '2023-03-25', '2025-03-25', 'C:\Users\ADMIN\Downloads\ptud\Protonix.jpg', N'Hộp 1 chai 45 viên', N'Viên nén', N'Từ 18 tuổi trở lên', 'Protonix', N'Điều trị viêm loét dạ dày', N'40 mg/ngày', N'Nơi khô ráo, dưới 25°C', 'Không');
GO

	INSERT INTO Thuoc (MaThuoc, TenThuoc, SoLuong, Gia, LoaiThuoc, NhaSanXuat, NgaySanXuat, NgayHetHan, HinhAnh, DonVi, DangBaoChe, DoTuoi, ThanhPhan, ChiDinh, LieuDung, BaoQuan, MoTa)
VALUES 
	('TH04130011', N'Tranexamic Acid', 50, 180000, N'Thuốc dịch truyền', N'Hà Nam', '2023-05-15', '2024-05-15', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_1.jpg', N'Hộp 10 chai 50ml', N'Dạng dịch truyền', N'Mọi độ tuổi', N'Tranexamic acid', N'Thành phần 1', N'1 chai mỗi 6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130012', N'Ringer Lactate', 80, 150000, N'Thuốc dịch truyền', N'Nam Cao', '2023-08-20', '2024-08-20', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_2.jpg', N'Hộp 5 lọ 500ml', N'Dạng dịch truyền', N'Từ 18 tuổi trở lên', N'Ringer lactate', N'Thành phần 1', N'1 lọ mỗi 8 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130013', N'Dextrose 5%', 120, 120000, N'Thuốc dịch truyền', N'Hà Nam', '2023-07-10', '2024-07-10', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_3.jpg', N'Hộp 20 chai 100ml', N'Dạng dịch truyền', N'Từ 18 tuổi trở lên', N'Dextrose 5%', N'Thành phần 1', N'1 chai mỗi 4 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130014', N'Saline Solution', 60, 200000, N'Thuốc dịch truyền', N'Nam Cao', '2023-09-25', '2024-09-25', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_4.jpg', N'Hộp 15 chai 250ml', N'Dạng dịch truyền', N'Mọi độ tuổi', N'Saline solution', N'Thành phần 1', N'1 chai mỗi 6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130015', N'Magnesium Sulfate', 40, 300000, N'Thuốc dịch truyền', N'Phát Đạt', '2023-11-12', '2024-11-12', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_5.jpg', N'Hộp 8 chai 50ml', N'Dạng dịch truyền', N'Từ 18 tuổi trở lên', N'Magnesium sulfate', N'Thành phần 1', N'1 chai mỗi 8 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130016', N'Potassium Chloride', 70, 220000, N'Thuốc dịch truyền', N'Nam Hà', '2023-10-05', '2024-10-05', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_6.jpg', N'Hộp 12 chai 100ml', N'Dạng dịch truyền', N'Từ 18 tuổi trở lên', N'Potassium chloride', N'Thành phần 1', N'1 chai mỗi 6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130017', N'Amiodarone', 55, 270000, N'Thuốc dịch truyền', N'Nam Cao', '2023-12-18', '2024-12-18', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_7.jpg', N'Hộp 7 chai 100ml', N'Dạng dịch truyền', N'Từ 18 tuổi trở lên', N'Amiodarone', N'Thành phần 1', N'1 chai mỗi 12 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130018', N'Heparin', 85, 190000, N'Thuốc dịch truyền', N'Hà Nam', '2023-06-30', '2024-06-30', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_8.jpg', N'Hộp 15 chai 50ml', N'Dạng dịch truyền', N'Từ 18 tuổi trở lên', N'Heparin', N'Thành phần 1', N'1 chai mỗi 8 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130019', N'Furosemide', 65, 230000, N'Thuốc dịch truyền', N'Nam Hà', '2023-04-22', '2024-04-22', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_9.jpg', N'Hộp 10 chai 50ml', N'Dạng dịch truyền', N'Mọi độ tuổi', N'Furosemide', N'Thành phần 1', N'1 chai mỗi 6 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130020', N'Dopamine', 75, 260000, N'Thuốc dịch truyền', N'Phát Đạt', '2023-02-14', '2024-02-14', 'C:\Users\ADMIN\Downloads\ptud\dich_truyen_10.jpg', N'Hộp 10 chai 100ml', N'Dạng dịch truyền', N'Từ 18 tuổi trở lên', N'Dopamine', N'Thành phần 1', N'1 chai mỗi 8 giờ', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không');
GO



INSERT INTO Thuoc (MaThuoc, TenThuoc, SoLuong, Gia, LoaiThuoc, NhaSanXuat, NgaySanXuat, NgayHetHan, HinhAnh, DonVi, DangBaoChe, DoTuoi, ThanhPhan, ChiDinh, LieuDung, BaoQuan, MoTa)
VALUES 
	('TH04130030', N'Ginkgo Biloba', 90, 200000, N'Thực phẩm chức năng', N'Phát Đạt', '2023-03-25', '2024-03-25', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_10.jpg', N'Hộp 1 chai 100 viên', N'Viên nang', N'Mọi độ tuổi', N'Ginkgo biloba', N'Thành phần 1',N'2 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130021', N'Omega-3 Fish Oil', 100, 350000, N'Thực phẩm chức năng', N'Phát Đạt', '2023-09-20', '2025-09-20', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_1.jpg', N'Hộp 1 chai 120 viên', N'Viên nang', N'Từ 18 tuổi trở lên', N'Omega-3 fish oil', N'Thành phần 1', N'2 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130022', N'Probiotics', 80, 280000, N'Thực phẩm chức năng', N'Thái Thịnh', '2023-07-15', '2024-07-15', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_2.jpg', N'Hộp 1 chai 30 viên', N'Viên nang', N'Từ 18 tuổi trở lên', N'Probiotics', N'Thành phần 1', N'1 viên mỗi ngày trước bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130023', N'Vitamin D3', 120, 150000, N'Thực phẩm chức năng', N'Hà Nam', '2023-08-30', '2024-08-30', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_3.jpg', N'Hộp 1 chai 100 viên', N'Viên nang', N'Mọi độ tuổi', N'Vitamin D3', N'Thành phần 1', N'1 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130024', N'Calcium Supplement', 90, 200000, N'Thực phẩm chức năng', N'Phát Đạt', '2023-11-05', '2024-11-05', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_4.jpg', N'Hộp 1 chai 60 viên', N'Viên nang', N'Mọi độ tuổi', N'Calcium supplement', N'Thành phần 1', N'2 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130025', N'Multivitamins', 110, 180000, N'Thực phẩm chức năng', N'Thái Thịnh', '2023-10-10', '2024-10-10', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_5.jpg', N'Hộp 1 chai 90 viên', N'Viên nang', N'Mọi độ tuổi', N'Multivitamins', N'Thành phần 1', N'1 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130026', N'Protein Powder', 70, 250000, N'Thực phẩm chức năng', N'Hà Nam', '2023-12-20', '2024-12-20', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_6.jpg', N'Hộp 1 túi 2kg', N'Bột', N'Mọi độ tuổi', N'Protein powder', N'Thành phần 1', N'2 muỗng mỗi ngày sau tập luyện', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130027', N'Glucosamine Chondroitin', 60, 320000, N'Thực phẩm chức năng', N'Phát Đạt', '2023-09-15', '2024-09-15', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_7.jpg', N'Hộp 1 chai 120 viên', N'Viên nang', N'Từ 18 tuổi trở lên', N'Glucosamine chondroitin', N'Thành phần 1', N'2 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130028', N'Coenzyme Q10', 80, 280000, N'Thực phẩm chức năng', N'Hà Nam', '2023-07-30', '2024-07-30', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_8.jpg', N'Hộp 1 chai 60 viên', N'Viên nang', N'Mọi độ tuổi', N'Coenzyme Q10', N'Thành phần 1', N'1 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
	('TH04130029', N'Turmeric Curcumin', 100, 220000, N'Thực phẩm chức năng', N'Thái Thịnh', '2023-06-10', '2024-06-10', 'C:\Users\ADMIN\Downloads\ptud\thuc_pham_9.jpg', N'Hộp 1 chai 120 viên', N'Viên nang', N'Từ 18 tuổi trở lên', N'Turmeric curcumin', N'Thành phần 1', N'2 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không');

GO




INSERT INTO Thuoc (MaThuoc, TenThuoc, SoLuong, Gia, LoaiThuoc, NhaSanXuat, NgaySanXuat, NgayHetHan, HinhAnh, DonVi, DangBaoChe, DoTuoi, ThanhPhan, ChiDinh, LieuDung, BaoQuan, MoTa)
VALUES 
    ('TH04130099', N'Ibuprofen', 100, 50000, N'Thuốc chống viêm', N'Thái Thịnh', '2023-09-10', '2024-09-10', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_1.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 12-17 tuổi', N'Ibuprofen', N'Thành phần 1', N'1 viên mỗi 6 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130132', N'Aspirin', 80, 40000, N'Thuốc chống viêm', N'Hà Nam', '2023-10-20', '2024-10-20', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_2.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Mọi độ tuổi', N'Aspirin', N'Thành phần 1', N'1 viên mỗi 4 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130033', N'Naproxen', 120, 55000, N'Thuốc chống viêm', N'Nam Cao', '2023-11-15', '2024-11-15', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_3.jpg', N'Hộp 1 chai 20 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Naproxen', N'Thành phần 1', N'1 viên mỗi 8 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130034', N'Diclofenac', 90, 60000, N'Thuốc chống viêm', N'Nam Hà', '2023-12-05', '2024-12-05', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_4.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Mọi độ tuổi', N'Diclofenac', N'Thành phần 1', N'1 viên mỗi 12 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130035', N'Celecoxib', 70, 75000, N'Thuốc chống viêm', N'Nam Hà', '2023-07-30', '2024-07-30', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_5.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Mọi độ tuổi', N'Celecoxib', N'Thành phần 1', N'1 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130036', N'Meloxicam', 100, 65000, N'Thuốc chống viêm', N'Thái Thịnh', '2023-08-20', '2024-08-20', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_6.jpg', N'Hộp 1 chai 60 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Meloxicam', N'Thành phần 1', N'1 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130037', N'Indomethacin', 80, 70000, N'Thuốc chống viêm', N'Hà Nam', '2023-09-10', '2024-09-10', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_7.jpg', N'Hộp 1 chai 30 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Indomethacin', N'Thành phần 1', N'1 viên mỗi 6 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130038', N'Piroxicam', 110, 80000, N'Thuốc chống viêm', N'Nam Hà', '2023-10-30', '2024-10-30', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_8.jpg', N'Hộp 1 chai 20 viên', N'Viên nén', N'Mọi độ tuổi', N'Piroxicam', N'Thành phần 1', N'1 viên mỗi 8 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130039', N'Ketoprofen', 120, 85000, N'Thuốc chống viêm', N'Nam Cao', '2023-11-15', '2024-11-15', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_9.jpg', N'Hộp 1 chai 40 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Ketoprofen', N'Thành phần 1', N'1 viên mỗi 12 giờ sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không'),
    ('TH04130040', N'Etodolac', 150, 90000, N'Thuốc chống viêm', N'Phát Đạt', '2023-12-05', '2024-04-14', 'C:\Users\ADMIN\Downloads\ptud\chong_viem_10.jpg', N'Hộp 1 chai 50 viên', N'Viên nén', N'Từ 18 tuổi trở lên', N'Etodolac', N'Thành phần 1', N'1 viên mỗi ngày sau bữa ăn', N'Nơi khô ráo, tránh ánh sáng trực tiếp', N'Không');
GO

INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120001', N'Seduxen', 10, 35000, N'Thuốc an thần', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan1.jpg', N'Hộp 1 vĩ x10 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'Diazepam là hoạt chất chính', N'điều trị mất ngủ kinh niên', N'mỗi ngày không quá 3 viên', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120002', N'Diazepam', 10, 9000, N'Thuốc an thần', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan2.jpg', N'Hộp 1 vĩ x 10 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'Hoạt chất Diazepam', N'mất ngủ, mệt mỏi căng thẳng, chống trầm cảm.', N'thanhphan2', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120003', N'Lexomil', 30, 150000, N'Thuốc an thần', N'Hà Nam', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan3.jpg', N'Hộp 1 vĩ x 30 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'Bromazepam 6mg', N'thư giãn, thoải mái và giúp bệnh nhân dễ dàng chìm vào giấc ngủ', N'1,5 – 3mg/ 2 – 3 lần/ ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120004', N'Rotunda', 10, 15000, N'Thuốc an thần', N'Nam Hà', CAST(N'2024-04-12' AS Date), CAST(N'2025-05-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan4.jpg', N'Hộp 1 vĩ x 10 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N' cây Stephania Rotunda Menispermaceae', N'an thần, điều hòa huyết áp, giãn các cơn đau ở ruột và tử cung', N'Uống 1 viên 30mg lúc trước khi đi ngủ', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120005', N'Mimosa
', 10, 7000, N'Thuốc an thần', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-05-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan5.jpg', N'Hộp 5 vĩ x 10 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'trinh nữ, lá vông nem, lá sen, lạc tiên và bình vôi', N'an thần, gây ngủ, giảm đau đầu ', N'Dùng 1 – 2 viên/ lần/ ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120006', N'Phenobarbital', 40, 21000, N'Thuốc an thần', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2025-05-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan6.jpg', N'Hộp 500 viên 10mg', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'Phenobarbital', N'thiếu ngủ, bị động kinh cơn lớn, động kinh giật cơ', N'Sử dụng 30 – 120mg, chia thành 2 – 3 lần uống trong ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120007', N'Amitriptyline', 30, 99000, N'Thuốc an thần', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-05-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan7.jpg', N'Lọ 1000 viên', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N' Amitriptyline', N'thiếu ngủ, bị động kinh cơn lớn, động kinh giật cơ', N'75mg x 2 – 3 lần/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120008', N'Scopolamine', 10, 170000, N'Thuốc an thần', N'Nam Hà', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan8.jpg', N'Hộp 5 vĩ x 10 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'Scopolamine', N'thiếu ngủ, bị động kinh cơn lớn, động kinh giật cơ', N'75mg x 2 – 3 lần/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120009', N'Diphenhydramine', 20, 99000, N'Thuốc an thần', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan9.jpg', N'Hộp 5 vĩ x 10 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'hoạt chất Diphenhydramine', N'trước khi đi ngủ chừng 30 phút', N'1 liều duy nhất 76mg', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120010', N'Haloperidol', 30, 11000, N'Thuốc an thần', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\thuocanthan10.jpg', N'Hộp 1 vĩ x 30 viên nén', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'Zaleplon', N'trước khi đi ngủ chừng 30 phút', N'1 liều duy nhất 76mg', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120101', N'Herbland IQKARE', 30, 55000, N'Vitamin', N'Hà Nam', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin1.jpg', N'2 lọ x 30 viên', N'Dạng viên nén', N'Mọi lứa tuổi', N'dầu cá, vitamin A, vitamin E,...', N'Phụ nữ mang thai và cho con bú', N'Uống 2 viên mỗi ngày.', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120102', N'DHC', 50, 27500, N'Vitamin', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin2.jpg', N'1 lọ x 90 viên', N'Dạng viên nén', N'Mọi lứa tuổi', N'12 loại dưỡng chất thiết yếu', N'Người trưởng thành bị thiếu chất, có nhu cầu bổ sung vitamin.', N'Uống 1 viên/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120103', N'One A Day', 80, 57000, N'Vitamin', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin3.jpg', N'1 lọ x 300 viên', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'vitamin C, vitamin A, vitamin D cùng kẽm, canxi,...', N'Phụ nữ từ 18 - 50 tuổi', N'Uống 1 viên/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120104', N'Daily Multi Kirkland Signature', 30, 53500, N'Vitamin', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin4.jpg', N'1 lọ x 500 viên', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'vitamin và khoáng chất', N'Người trên 18 tuổi có nhu cầu bổ sung vitamin và khoáng chất', N'Uống 1 viên/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120105', N'Blackmores Multivitamin + Energy', 50, 45000, N'Vitamin', N'Hà Nam', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin5.jpg', N'1 lọ x 50 viên', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'vitamin và khoáng chất', N'Người trưởng thành trên 18 tuổi', N'Uống 1 viên/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120106', N'Puritan’s Pride', 45, 35000, N'Vitamin', N'Hà Nam', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin6.jpg', N'1 lọ x 100 viên', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'vitamin và khoáng chất', N'Người trưởng thành trên 18 tuổi', N'Uống 1 viên/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120107', N'Centrum Adults Multivitamin', 60, 12000, N'Vitamin', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin7.jpg', N'1 lọ x 60 viên', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'vitamin và khoáng chất', N'Nam nữ từ 18 - 50 tuổi thiếu dinh dưỡng, thường xuyên mệt mỏi.', N'Uống 1 viên/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120108', N'Orihiro Most Chewable', 30, 39000, N'Vitamin', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin8.jpg', N'1 lọ x 180 viên', N'Dạng viên nén', N'Từ 2-11 tuổi', N'vitamin và khoáng chất', N'trẻ từ 3 tuổi trở lên', N'Uống 2 viên/ngày vào sáng và chiều', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120109', N'Siro Pediakid 22 Vitamines', 20, 27000, N'Vitamin', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin9.jpg', N'1 lọ x 125ml', N'Dạng siro', N'Từ 1-23 tháng', N'vitamin và khoáng chất', N'trẻ từ 1 tuổi trở lên', N'Uống 2 lần/ngày vào sáng và chiều', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120110', N'Healthza Vitamin D3 1000IU ', 75, 13000, N'Vitamin', N'Nam Hà', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\vitamin10.jpg', N'1 lọ x 60 viên', N'Dạng viên nén', N'Từ 2-11 tuổi', N'vitamin và khoáng chất', N'trẻ từ 6 tuổi trở lên', N'Uống 1 - 2 viên/ngày.', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120201', N'Dung dịch sát khuẩn Povidine 10%', 20, 700, N'Thuốc sát khuẩn , khử trùng', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung1.jpg', N'1 lọ x 20ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Povidon iod 2g', N'Sát khuẩn ngăn ngừa nhiễm khuẩn ở vết cắt, vết trầy, vết bỏng ', N'Bôi một lượng nhỏ thuốc đến vùng bị bệnh 1-3 lần/ngày.', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120202', N'Oxy Già OPC', 60, 300, N'Thuốc sát khuẩn , khử trùng', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung2.jpg', N'1 lọ x 60ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Nước oxy già đậm đặc (50%) 3,6g', N'Dùng tại chỗ để làm sạch, sát trùng các vết thương nhỏ. ', N'Dùng gòn thấm thuốc và bôi lên vết thương ', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120203', N'Cồn Alcool 90 độ OPC', 60, 500, N'Thuốc sát khuẩn , khử trùng', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung3.jpg', N'1 lọ x 60ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Ethanol 96%, xanh methylen, nước tinh khiết', N'Sát trùng ngoài da, vật dụng. Đốt tiệt trùng dụng cụ bằng cồn.', N'Dùng băng gòn sạch tẩm thuốc bôi sạch vết thương nhẹ nhàng', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120204', N'Nước muối Natri Clorid 0.9%', 15, 1100, N'Thuốc sát khuẩn , khử trùng', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung4.jpg', N'1 lọ x 500ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Natri clorid 4.5g', N'Rửa vết thương hở và kín.', N'Bơm rửa các vết thương hở và kín trước khi dùng thuốc', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120205', N'Dung dịch sát khuẩn Betadine 10%', 30, 3800, N'Thuốc sát khuẩn , khử trùng', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung5.jpg', N'1 lọ x 30ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Povidon-Iod 10% kl/tt', N'Để sát khuẩn và giúp vệ sinh cá nhân tốt hơn.', N'Phết dung dịch mẹ (không pha loãng) dàn đều vào nơi cần điều trị', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120206', N'Gạc Povidine iodine 10%', 10, 700, N'Thuốc sát khuẩn , khử trùng', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung6.jpg', N'Hộp 10 miếng', N'Dạng gạc', N'Mọi lứa tuổi', N'Povidon iod 10g', N'Được dùng cho các vết thương, vết bỏng, điều trị một số bệnh da.', N'Đắp miếng gạc tẩm thuốc lên vết thương, gỡ bỏ lớp nylon', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120207', N'Dung dịch sát khuẩn Leopovidone', 15, 1600, N'Thuốc sát khuẩn , khử trùng', N'Hà Nam', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung7.jpg', N'1 lọ x 15ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Povidon-iod 10% ', N'ngăn ngừa nhiễm trùng đối với các vi khuẩn nhạy cảm.', N'Sau khi làm sạch vết thương, bôi lên khu vực bị nhiễm trùng', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120208', N'Dung dịch Natri Clorid HDPharma 0.9%', 30, 1000, N'Thuốc sát khuẩn , khử trùng', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung8.jpg', N'1 lọ x 500ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Natri clorid, Nước tinh khiết', N'Rửa vết thương trong phẫu thuật, vết thương ngoài da, súc miệng ', N'Rửa vết thương: Theo chỉ định của bác sỹ.', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120209', N'Gel sát khuẩn Leopovidone', 20, 6300, N'Thuốc sát khuẩn , khử trùng', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung9.jpg', N'1 tuýp x 20g', N'Dạng gel', N'Mọi lứa tuổi', N'Povidon iodin 2g', N'Dùng để điều trị vết bỏng, vết đứt và các vết thương nhỏ', N'Thuốc bôi ngoài da', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120210', N'Dung dịch sát khuẩn Povidon-Iod HD 10%', 20, 700, N'Thuốc sát khuẩn , khử trùng', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\khutrung10.jpg', N'1 lọ x 20ml', N'Dạng dung dịch', N'Mọi lứa tuổi', N'Povidon iodin 2g', N'Sát trùng da, niêm mạc trước khi phẫu thuật, tiêm chích.', N'Sát trùng da, niêm mạc', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120301', N'Thuốc dị ứng cetirizin', 10, 1000, N'Thuốc chống dị ứng', N'Hà Nam', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung1.jpg', N'Hộp 10 vĩ x 10 viên ', N'Dạng viên nén', N'Từ 18 tuổi trở lên', N'Cetirizine dihydrocloride: 10 mg', N'Viêm mũi dị ứng theo mùa, viêm mũi dị ứng quanh năm ', N'Người lớn và trẻ em từ 12 tuổi trở lên dùng 5 - 10 mg/ ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120302', N'Thuốc dị ứng Avamys', 27, 2000, N'Thuốc chống dị ứng', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-19' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung2.jpg', N'1 chai x 27 mcg', N'Dạng xịt', N'Từ 2-11 tuổi', N'Glucose anhydrous, microcrystalline cellulose', N'Điều trị các triệu chứng chảy nước mũi, xung huyết mũi, ngứa mũi ', N'2 nhát xịt (27,5microgram/nhát xịt) vào mỗi bên mũi x 1 lần/ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120303', N'Thuốc dị ứng Loratadin', 10, 1500, N'Thuốc chống dị ứng', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2024-04-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung3.jpg', N'Hộp 5 vĩ x 10 viên', N'Dạng viên nén', N'Từ 1-23 tháng', N'Loratadin 10mg', N'giảm triệu chứng viêm mũi dị ứng gồm hắt hơi, sổ mũi, ngứa mũi', N'Uống 10 mg/ 1 lần/ ngày.', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120304', N'Thuốc Medrol', 10, 2000, N'Thuốc chống dị ứng', N'Hà Nam', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung4.jpg', N'Hộp 3 vĩ x 10 viên', N'Dạng viên nén', N'Mọi lứa tuổi', N'Methylprednisolone 16mg

', N'Viêm da dị ứng, viêm đường hô hấp, viêm khớp, bệnh về máu

', N'160 mg/ngày x 1 tuần', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120305', N'Thuốc dị ứng Clorpheniramin ', 30, 1500, N'Thuốc chống dị ứng', N'Nam Hà', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung5.jpg', N'Hộp 10 vĩ x 30 viên', N'Dạng viên nén', N'Từ 1-23 tháng', N'Clorpheniramin maleat: 4mg', N'Viêm da dị ứng, viêm đường hô hấp, viêm khớp, bệnh về máu

', N'1 viên x 4 - 6 giờ/ lần', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120306', N'Thuốc dị ứng Telfast HD', 10, 3000, N'Thuốc chống dị ứng', N'Nam Hà', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung6.jpg', N'Hộp 1 vĩ x 10 viên', N'Dạng viên nén', N'Từ 12 tuổi trở lên', N'Fexofenadine: 60 mg', N'Viêm da dị ứng, viêm đường hô hấp, viêm khớp, bệnh về máu

', N'60 mg/ lần x 2 lần/ ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120307', N'Thuốc dị ứng Theralene', 40, 4000, N'Thuốc chống dị ứng', N'Thái Thịnh', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung7.jpg', N'1 lọ x 100ml', N'Dạng siro', N'Từ 18 tuổi trở lên', N'Alimemazine: 0,050 g/ 100ml', N'Điều trị các triệu chứng chảy nước mũi, xung huyết mũi, ngứa mũi ', N'8 - 32 ml/ 1 lần trước khi ngủ', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120308', N'Thuốc dị ứng A.T Desloratadin', 27, 2000, N'Thuốc chống dị ứng', N'Phát Đạt', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung8.jpg', N'Hộp 1 vĩ x 4 ống', N'Dạng siro', N'Từ 18 tuổi trở lên', N'Desloratadin 2,5 mg', N'giảm triệu chứng gồm hắt hơi, sổ mũi, ngứa mũi', N'2 ống/ ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120309', N'Thuốc dị ứng Bilaxten', 50, 4000, N'Thuốc chống dị ứng', N'Nam Hà', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung9.jpg', N'Hộp 1 vĩ x 10 viên ', N'Dạng viên nén', N'Mọi lứa tuổi', N'Bilastine: 20 mg', N'giảm triệu chứng gồm hắt hơi, sổ mũi, ngứa mũi', N'1 lần duy nhất trong ngày', N'khô ráo, tránh ẩm ước', N'không')
INSERT [dbo].[Thuoc] ([MaThuoc], [TenThuoc], [SoLuong], [Gia], [LoaiThuoc], [NhaSanXuat], [NgaySanXuat], [NgayHetHan], [HinhAnh], [DonVi], [DangBaoChe], [DoTuoi], [ThanhPhan], [ChiDinh], [LieuDung], [BaoQuan], [MoTa]) VALUES (N'TH04120310', N'Thuốc trị dị ứng Prednison', 35, 1500, N'Thuốc chống dị ứng', N'Nam Cao', CAST(N'2024-04-12' AS Date), CAST(N'2025-06-12' AS Date), N'C:\Users\ADMIN\Downloads\ptud\diung10.jpg', N'Hộp 10 vĩ x 10 viên', N'Dạng viên nén', N'Mọi lứa tuổi', N'Prednisolon: 5 mg', N'giảm triệu chứng gồm hắt hơi, sổ mũi, ngứa mũi', N'20 - 40 mg/ ngày ', N'khô ráo, tránh ẩm ước', N'không')
GO




INSERT INTO KhachHang VALUES('KH14050001',N'Lê Thị Hoài','2003-03-19','21',1,'0123456789','Dac Nong',100,N'Đồng')
INSERT INTO KhachHang VALUES('KH14050002',N'Nguyễn Văn An','2004-04-25','20',1,'0987654321','TP. Ho Chi Minh',200,N'Đồng');
INSERT INTO KhachHang VALUES('KH14050003',N'Phạm Thị Hương','1999-05-10','25',1,'0123456789','Ha Noi',300,N'Đồng');
INSERT INTO KhachHang VALUES('KH14050004',N'Trần Đức Thanh','2002-06-05','22',1,'0909090909','Da Nang',600,N'Bạc');
INSERT INTO KhachHang VALUES('KH14050005',N'Lê Thị Hằng','1994-07-20','30',1,'0393939393','Quang Binh',800,N'Bạc');
INSERT INTO KhachHang VALUES('KH14050006',N'Nguyễn Thị Tâm','1996-08-15','28',1,'0777777777','Can Tho',1200,N'Bạc');
INSERT INTO KhachHang VALUES('KH14050007',N'Hoàng Văn Long','1997-09-01','27',1,'0555555555','TP. Ho Chi Minh',2100,N'Vàng');
INSERT INTO KhachHang VALUES('KH14050008',N'Trần Thị Thu','2001-10-10','23',1,'0333333333','Ha Noi',2500,N'Vàng');
INSERT INTO KhachHang VALUES('KH14050009',N'Vũ Đức Anh','1998-11-20','26',1,'0444444444','Hai Phong',6500,N'Bạch Kim');
INSERT INTO KhachHang VALUES('KH14050010',N'Nguyễn Thị Loan','1995-12-05','29',1,'0666666666','Da Nang',11000,N'Kim Cương');
GO

SELECT * FROM KhachHang

INSERT INTO NhanVien VALUES ('NV12020006',N'Hoàng Thị Lan',0,'2003-12-10',21,'0111111111','067203000252',N'Hải Phòng',N'Nhân viên',N'Đang làm','2024-03-20','');
INSERT INTO NhanVien VALUES ('NV12020002',N'Trần Thị Mai',0,'2002-08-15',19,'0222222222','067203000248',N'Đà Nẵng',N'Nhân viên',N'Đang làm','2024-03-25','');
INSERT INTO NhanVien VALUES ('NV12020003',N'Phạm Văn Đức',1,'2004-01-10',18,'0333333333','067203000249',N'Hồ Chí Minh',N'Nhân viên',N'Đang làm','2024-04-02','');
INSERT INTO NhanVien VALUES ('NV12020004',N'Lê Thị Hằng',0,'2001-11-20',23,'0444444444','067203000250',N'Hà Nội',N'Nhân viên',N'Đã nghỉ','2024-02-10','');
INSERT INTO NhanVien VALUES ('NV12020005',N'Nguyễn Văn Đạt',1,'2000-09-05',24,'0555555555','067203000251',N'Quảng Bình',N'Nhân viên',N'Nghỉ phép','2024-04-05','');


----------------
INSERT INTO NhanVien VALUES ('NV09040001',N'Nguyễn Thị Hương',0,'1990-05-15',31,'0912345678','067203000248',N'Hà Nội',N'Nhân viên bán hàng',N'Đang làm','2010-08-12','');
INSERT INTO NhanVien VALUES ('NV09040002',N'Trần Văn Bình',1,'1985-10-25',36,'0987654321','067203000249',N'Hồ Chí Minh',N'Kế toán',N'Đang làm','2015-02-28','');
INSERT INTO NhanVien VALUES ('NV09040003',N'Lê Thị Mai',0,'1995-08-07',26,'0978123456','067203000250',N'Đà Nẵng',N'Nhân viên bảo vệ',N'Đang làm','2018-11-05','');
INSERT INTO NhanVien VALUES ('NV09040004',N'Nguyễn Đức Anh',1,'1980-02-28',41,'0965432789','067203000251',N'Hải Phòng',N'Trưởng phòng',N'Đang làm','2013-04-20','');
INSERT INTO NhanVien VALUES ('NV09040005',N'Hoàng Văn Hưng',1,'1975-12-10',46,'0912345678','067203000252',N'Cần Thơ',N'Giám đốc',N'Đang làm','2005-10-15','');
INSERT INTO NhanVien VALUES ('NV09040006',N'Vũ Thị Hương',0,'2000-09-18',23,'0987654321','067203000253',N'Hà Tĩnh',N'Nhân viên kỹ thuật',N'Đang làm','2019-07-10','');
INSERT INTO NhanVien VALUES ('NV09040007',N'Nguyễn Thị Lan',0,'1988-04-30',33,'0978123456','067203000254',N'Quảng Bình',N'Nhân viên IT',N'Đang làm','2016-09-22','');
INSERT INTO NhanVien VALUES ('NV09040008',N'Trần Văn Đức',1,'1992-11-22',29,'0965432789','067203000255',N'Bình Định',N'Nhân viên marketing',N'Đang làm','2020-03-15','');
INSERT INTO NhanVien VALUES ('NV09040009',N'Phạm Thị Loan',0,'1987-07-15',34,'0912345678','067203000256',N'An Giang',N'Nhân viên hành chính',N'Đang làm','2017-12-01','');
INSERT INTO NhanVien VALUES ('NV09040010',N'Lê Văn Long',1,'1998-03-05',26,'0987654321','067203000257',N'Bắc Ninh',N'Nhân viên kỹ thuật',N'Đang làm','2021-05-20','');
INSERT INTO NhanVien VALUES ('NV09040011',N'Trần Thị Hồng',0,'1972-01-20',52,'0978123456','067203000258',N'Hải Dương',N'Nhân viên bảo trì',N'Đang làm','2014-08-18','');
INSERT INTO NhanVien VALUES ('NV09040012',N'Nguyễn Văn Dương',1,'1991-09-10',33,'0912345678','067203000259',N'Phú Yên',N'Nhân viên sản xuất',N'Đã nghĩ','2019-10-30','');
INSERT INTO NhanVien VALUES ('NV09040013',N'Hoàng Thị Lan',0,'1983-07-20',41,'0987654321','067203000260',N'Bình Phước',N'Tiếp tân',N'Đã nghĩ','2018-06-25','');
INSERT INTO NhanVien VALUES ('NV09040014',N'Phạm Văn Bảo',1,'1995-05-03',29,'0978123456','067203000261',N'Hà Nam',N'Nhân viên bảo trì',N'Nghỉ phép','2022-04-15','');
INSERT INTO NhanVien VALUES ('NV09040015',N'Lê Thị Ánh',0,'1986-08-12',35,'0965432789','067203000262',N'Thái Nguyên',N'Kế toán',N'Nghỉ phép','2023-02-28','');


GO
INSERT INTO TaiKhoan VALUES('NV12061111','123')
GO  
INSERT INTO KhachHang VALUES('KH12010001', N'Trần Thị Ánh', '1990-05-15', 31, 0, '0912345678', N'Hà Nội', 1500, N'Bạc')
INSERT INTO KhachHang VALUES('KH12010002', N'Phạm Văn Bình', '1985-10-25', 36, 1, '0987654321', N'Hồ Chí Minh', 3500, N'Vàng')
INSERT INTO KhachHang VALUES('KH12010003', N'Lê Thị Mai', '1995-08-07', 26, 0, '0978123456', N'Đà Nẵng', 800, N'Bạc')
INSERT INTO KhachHang VALUES('KH12010004', N'Nguyễn Đức Anh', '1980-02-28', 41, 1, '0965432789', N'Hải Phòng', 4500, N'Vàng')
INSERT INTO KhachHang VALUES('KH12010005', N'Hoàng Thị Hương', '1975-12-10', 46, 0, '0912345678', N'Cần Thơ', 7000, N'Bạch kim')
INSERT INTO KhachHang VALUES('KH12010006', N'Vũ Văn Tú', '2000-09-18', 23, 1, '0987654321', N'Hà Tĩnh', 300, N'Đồng')
INSERT INTO KhachHang VALUES('KH12010007', N'Nguyễn Thị Lan', '1988-04-30', 33, 0, '0978123456', N'Quảng Bình', 2100, N'Bạch kim')
INSERT INTO KhachHang VALUES('KH12010008', N'Trần Văn Đức', '1992-11-22', 29, 1, '0965432789', N'Bình Định', 8000, N'Kim cương')
INSERT INTO KhachHang VALUES('KH12010009', N'Phạm Thị Loan', '1987-07-15', 34, 0, '0912345678', N'An Giang', 150, N'Đồng')
INSERT INTO KhachHang VALUES('KH12010010', N'Lê Văn Long', '1998-03-05', 26, 1, '0987654321', N'Bắc Ninh', 2600, N'Bạch kim')
INSERT INTO KhachHang VALUES('KH12010011', N'Trần Thị Hồng', '1972-01-20', 52, 0, '0978123456', N'Hải Dương', 10500, N'Kim cương')
-----------------

INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH001', N'Nguyễn Văn Nam', CAST(N'2024-03-19' AS Date), 20, 1, N'01213562326', N'Ðak lak', NULL, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH002', N'Nguyễn Thị Đào', CAST(N'2024-03-19' AS Date), 20, 0, N'01213562326', N'Ðak lak', NULL, NULL)
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040001', N'Nguyên Văn Khánh', CAST(N'2000-10-19' AS Date), 24, 1, N'07666161663', N'Hồ Chí Minh', 250, N'Đồng')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040002', N'Lê Thị Thanh', CAST(N'2003-10-12' AS Date), 21, 0, N'01223989879', N'Hồ Chí Minh', 1500, N'Bạc')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040003', N'Đinh Hải Văn', CAST(N'2002-09-19' AS Date), 22, 1, N'08194811234', N'Hồ Chí Minh', 3000, N'Vàng')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040004', N'Bùi Thị Trang', CAST(N'2001-10-30' AS Date), 23, 0, N'07162736481', N'Bình Dương', 8000, N'Bạch kim')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040005', N'Lý Công Định', CAST(N'2004-11-21' AS Date), 20, 1, N'06123141518', N'Điện Biên', 11000, N'Kim cương')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040011', N'Lê Thị Thuỷ', CAST(N'2000-10-23' AS Date), 23, 0, N'04123155178', N'Khánh Hoà', 185, N'Đồng')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040012', N'Khương Tử Văn', CAST(N'2003-12-21' AS Date), 21, 1, N'09123551235', N'Lai Châu', 1200, N'Bạc')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040013', N'Trần Thuỳ Trang', CAST(N'2002-12-19' AS Date), 22, 0, N'09812348912', N'Hà Nội', 4000, N'Vàng')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040014', N'Hồ Khánh Toàn', CAST(N'2001-01-20' AS Date), 23, 1, N'07123497980', N'Vinh', 7000, N'Bạch kim')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH12040015', N'Trần Thị Thuỳ Linh', CAST(N'2003-05-25' AS Date), 21, 0, N'08997194791', N'Trà Vinh', 20000, N'Kim cương')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH1204115', N'Nguyễn Văn Nam', CAST(N'2024-03-19' AS Date), 20, 1, N'01213562326', N'Ðak lak', 100, N'Đồng')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH1205798', N'Nguyễn Thị Đào', CAST(N'2024-03-19' AS Date), 20, 0, N'01213562326', N'Ðak lak', 570, N'Bạc')
INSERT [dbo].[KhachHang] ([MaKH], [TenKH], [NgaySinh], [Tuoi], [GioiTinh], [SDT], [DiaChi], [DiemThanhVien], [XepHang]) VALUES (N'KH1212888', N'Nguyễn Văn Hoàng', CAST(N'2024-03-19' AS Date), 20, 1, N'0794571318', N'Ðak lak', 1960, N'Vàng')
GO
