package com.albert.utils.number;

import java.math.BigDecimal;

public class NumUtil {
	public static final long KB = 1024;
	public static final long MB = 1024*1024;
	public static final long GB = 1024*1024*1024;
	
	public static Double retainSingleDecimal(Double data) {
		try {
			BigDecimal bg = new BigDecimal(data);
			double d = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			return d;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0.0;
		}
	}
	public static Double retainSingleDecimal(Double data, int i) {
		try {
			BigDecimal bg = new BigDecimal(data);
			double d = bg.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
			return d;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0.0;
		}
	}
	public static Double retainSingleDecimal(Long data, int i) {
		try {
			BigDecimal bg = new BigDecimal(data);
			double d = bg.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
			return d;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0.0;
		}
	}
	public static Double retainSingleDecimalFloor(Double data) {
		try {
			BigDecimal bg = new BigDecimal(data);
			double d = bg.setScale(1, BigDecimal.ROUND_HALF_DOWN).doubleValue();
			return d;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0.0;
		}
	}
	public static int retainIntegerFloor(Double data) {
		try {
			BigDecimal bg = new BigDecimal(data);
			int d = bg.setScale(0, BigDecimal.ROUND_DOWN).intValue();
			return d;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0;
		}
	}
	
	public static int retainInteger(Double data) {
		try {
			BigDecimal bg = new BigDecimal(data);
			int i = bg.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			return i;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0;
		}
	}
	public static int retainInteger(long data) {
		try {
			BigDecimal bg = new BigDecimal(data);
			int i = bg.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			return i;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0;
		}
	}
	public static long retainLong(Double data) {
		try {
			BigDecimal bg = new BigDecimal(data);
			long i = bg.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			return i;
		} catch (Exception e) {
//			log.error("retain number error", e);
			return 0;
		}
	}
	
	public static double getDivideDouble(String v1, String v2){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal subtract = b1.divide(b2, 1, BigDecimal.ROUND_HALF_UP);
			double f1 = subtract.doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	public static double getDivideDouble(String v1, String v2, int i){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal subtract = b1.divide(b2, i, BigDecimal.ROUND_HALF_UP);
			double f1 = subtract.doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	public static double getDivideDouble(Double v1, Double v2){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal subtract = b1.divide(b2, 1, BigDecimal.ROUND_HALF_UP);
			double f1 = subtract.doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	public static double getDivideDouble(Double v1, Double v2, int i){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal subtract = b1.divide(b2, i, BigDecimal.ROUND_HALF_UP);
			double f1 = subtract.doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	public static double getDivideDouble(Integer v1, Double v2, int i){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal subtract = b1.divide(b2, i, BigDecimal.ROUND_HALF_UP);
			double f1 = subtract.doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	public static double getDivideDouble(Long v1, Long v2, int i){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal subtract = b1.divide(b2, i, BigDecimal.ROUND_HALF_UP);
			double f1 = subtract.doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	public static double getDivideDouble(Long v1, Long v2,int mom, int i){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal m = new BigDecimal(mom);
			BigDecimal multiply = b1.multiply(m);
			BigDecimal subtract = multiply.divide(b2, i, BigDecimal.ROUND_HALF_UP);
			return subtract.doubleValue();
		} catch (Exception e) {
			return 0.0;
		}
	}
	public static double getDivideDouble(Double v1, Double v2, int mom, int i){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal mul = new BigDecimal(mom);
			BigDecimal div = b1.divide(b2, i, BigDecimal.ROUND_HALF_UP);
			double f1 = div.multiply(mul).doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	public static int getDivideDouble(Integer v1, Integer v2){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal div = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal mul = new BigDecimal(100);
			return div.multiply(mul).intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	public static double getDivideDoubleFloor(Double v1, Integer v2){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal div = b1.divide(b2, 3, BigDecimal.ROUND_HALF_DOWN);
			BigDecimal mul = new BigDecimal(100);
			double f1 = div.multiply(mul).doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}
	
	public static int getDivideInt(Double v1, Double v2, int i, int mom){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal mul = new BigDecimal(mom);
			BigDecimal div = b1.divide(b2, i, BigDecimal.ROUND_HALF_UP);
			int f1 = div.multiply(mul).intValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0;
		}
	}
	public static double getSumDouble(Double v1, Double v2){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal add = b1.add(b2);
			double f1 = add.doubleValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0.0;
		}
	}

	public static int getSumInteger(Integer v1, Integer v2){
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			BigDecimal add = b1.add(b2);
			int f1 = add.intValue();
			return f1;
		} catch (Exception e) {
//			log.error("Divide err ", e);
			return 0;
		}
	}

	/**
	 * 进行大小比较
	 * 第一个大，返回>0
	 * 相等，返回0
	 * 第二个大，返回<0
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int compareTo(Double v1, Double v2) {
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			int i = b1.compareTo(b2);
			return i;
		} catch (Exception e) {
			return 0;
		}
	}

}
