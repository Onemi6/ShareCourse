package sharecourse.others;

public class TimeChange {
	public static String timeGet(String year, String month, String day) {
		String time = "" + year;
		if (month.length() == 1) {
			month = "0" + month;
		}
		time = time +"-"+ month;
		if (day.length() == 1) {
			day = "0" + day;
		}
		time = time +"-"+ day;
		return time;
	}

	public static String timeGet(String year, String month, String day,
			String hour, String min) {
		String time = "" + year;
		if (month.length() == 1) {
			month = "0" + month;
		}
		time = time +"-"+ month;
		if (day.length() == 1) {
			day = "0" + day;
		}
		time = time +"-"+ day;
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		time = time +":"+ hour;
		if (min.length() == 1) {
			min = "0" + min;
		}
		time = time +":"+ min;
		return time;
	}
}
