package com.platform.common.tool.datetime;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

	private static DateTimeUtil m_instance = null;

	public synchronized static DateTimeUtil getInstance() {

		if (m_instance == null) {

			m_instance = new DateTimeUtil();

		}

		return m_instance;
	}

	private DateTimeUtil() {
	}

	public static String dateformat = "yyyy-MM-dd HHmmss";
	public static String dateformat_yyyymmdd = "yyyy-MM-dd";

	/**
	 * 获得当前时间 yyyy-dd-mm HH:MM:SS
	 * 
	 * @return
	 */
	public String getNowDateTime() {
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.format(new Date());
	}
	/**
	 * 获得当前日期 yyyy-dd-mm
	 * 
	 * @return
	 */
	public String getNowDate() {
		SimpleDateFormat format = new SimpleDateFormat(dateformat_yyyymmdd);
		return format.format(new Date());
	}

	/**
	 * 字符串转换成时间
	 * 
	 * @return
	 */
	public Date formatStringToDate(String date, String dateformat) {

		if (date == null && date.length() == 0) {
			return null;
		}

		if (dateformat == null && dateformat.length() == 0) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 时间换成字符串转
	 * 
	 * @return
	 */
	public String formatDateToString(Date date, String dateformat) {

		if (date == null) {
			return null;
		}
		if (dateformat == null && dateformat.length() == 0) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateformat);

		return format.format(date);

	}

	public Date addTime(Date date, Long time) {

		Date now = new Date();

		long afterTime = (date.getTime() / 1000) + time;

		now.setTime(afterTime * 1000);

		return now;

	}

	public Date reduceTime(Date date, Long time) {

		Date now = new Date();

		long afterTime = (date.getTime() / 1000) - time;

		now.setTime(afterTime * 1000);

		return now;
	}

	public Date reduceNowTime(Long time) {

		Date now = new Date();

		long afterTime = (now.getTime() / 1000) - time;

		now.setTime(afterTime * 1000);

		return now;
	}

	public Date addNowTime(Long time) {

		Date now = new Date();

		long afterTime = (now.getTime() / 1000) + time;

		now.setTime(afterTime * 1000);

		return now;
	}

	public boolean addNowTimeCompare(Date date, Long time) {

		Date now = new Date();

		long afterTime = (date.getTime() / 1000) + time;

		date.setTime(afterTime * 1000);

		return now.after(date);
	}

	public static void main(String args[]){
		System.out.println(DateTimeUtil.getInstance().formatStringToDate(DateTimeUtil.getInstance().getNowDate(), "yyyy-MM-dd"));
	}
}
