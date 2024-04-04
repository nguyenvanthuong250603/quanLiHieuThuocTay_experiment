package dao;

import java.sql.Date;
import java.time.LocalDate;

public class Trage_DAO {
	public static LocalDate chageTimeSQL(Date date) {
		Date datee =date;
		LocalDate localDate= datee.toLocalDate();
		
		return localDate;
	}
	public static int chageGenderToSQL(boolean gender) {
		boolean x = gender;
		int gt = x == true ? 1 :0;
		return gt;
	}
}
