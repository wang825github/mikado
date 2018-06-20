package com.shinetech.dalian.mikado.util;

import org.apache.commons.lang.StringUtils;

public class FindShortName {
	
	/**
	 * 使用英文名字的前三位，作为缩写
	 * @param enName
	 * @return
	 */
	public static String getSpeciesEnShortName(String enName) {
		String sn = "";
		if (StringUtils.isNotEmpty(enName)) {
			if (enName.length() >= 3) {
				sn = enName.substring(0, 3);
			} else if (enName.length() == 2) {
				sn = "z" + enName.substring(0, 2);
			} else if (enName.length() == 1) {
				sn = "zz" + enName.substring(0, 1);
			}
			sn = sn.toUpperCase();
		}
		return sn;
	}
}
