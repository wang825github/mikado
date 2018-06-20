package com.shinetech.dalian.mikado.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shinetech.dalian.mikado.dao.DataManageDao;
import com.shinetech.dalian.mikado.entity.DataManageEntity;
import com.shinetech.dalian.mikado.entity.SeedEntity;
import com.shinetech.dalian.mikado.entity.SpeciesEntity;
import com.shinetech.dalian.mikado.entity.VarietyEntity;

/**
 * @author Justin
 * 
 *	由于涉及到数据库，所以方法需要实例化才能使用
 */
@Component
public class MakeNumbersDetails {
	
	static final  String  INITIALIZATIONLOTNUM = "0001";
	@Autowired
	DataManageDao dmDao;
	
	/** 
	 * 生成单元识别码格式 （20位）：
	 * 种子名称(MD5后 4位)+当前日期（十六进制 5位）+时间戳（后6位）+原产地(MD5纯字母 3位)+随机数字（3位）
	 * 例如 ：0C60          313eb	   126106  + PLA + 986
	 *        ：领先陈品 日期201707  时间戳    原产地  随机数
	 *        
	 *  现变更为种子名称(MD5后 4位) +UUID 16位
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public String generateUnitIdentificationCode(SpeciesEntity speciesEntity ) throws NoSuchAlgorithmException{
		String speciesNameCn = speciesEntity.getSpeciesNameCn();
//		String SeedName = variety.getSpecies().getSpeciesNameCn();
//		String OriginalName = variety.getCommercialName();//商品名称
		
		speciesNameCn = incaseEmpty(speciesNameCn);//防止是空的
//		OriginalName = incaseEmpty(OriginalName);//防止字符串是空的
		MessageDigest md = MessageDigest.getInstance("MD5");
		 md.update(speciesNameCn.getBytes());
		 
		 //种子名称(MD5 4位)
		 String SeedNameForCode = new BigInteger(1, md.digest()).toString(16).substring(0,4).toUpperCase();

		 //当前日期（十六进制 5位）
//		 String dateForCode = Integer.toHexString(Integer.valueOf(date));
		 
		 //时间戳（后6位）
//		 String timeStamp = nowTime.substring(nowTime.length()-6, nowTime.length());
		 //原产地(MD5纯字母 3位)
//		 String countryOrigin = getMD5Letters(new BigInteger(1, md.digest()).toString(16)) ;
		 //随机3位数字
		 //现在随机
//		 String randomNumSix = String.valueOf(RandomValue.getNum(100000, 999999));
		 
		return (SeedNameForCode+UUID.randomUUID().toString().replace("-", "").substring(0,16)).toUpperCase();
	}
	/**
	 *  格式是 2017+作物英文名字前3个字母
	 *  @return
	 */
	public  String makeLotNumbers(VarietyEntity variety ){
		 
		String lotNumbers = "";
		Calendar cal = Calendar.getInstance();//获取年份
		String speciesName = variety.getSpecies().getNameEn();
		String speciesNameForShort  = FindShortName.getSpeciesEnShortName(speciesName);
		String nowYear = String.valueOf(cal.get(Calendar.YEAR));
		List<DataManageEntity>  dmList = dmDao.listDataManageBySpecies(nowYear, speciesNameForShort);
		
		if(dmList.size() == 0){
			//今年这个作物还没有批次
			lotNumbers = nowYear+speciesNameForShort+"-"+INITIALIZATIONLOTNUM;
		}else{
			int maxNum = findMaxLotNumbers(dmList, speciesNameForShort, nowYear)+1;
			//补位数
			lotNumbers = nowYear+speciesNameForShort+"-"+fillingZero(maxNum);
		}
		return lotNumbers;
	}
	
	private int findMaxLotNumbers(List<DataManageEntity>  dmList,String speciesNameForShort,String nowYear){
		
		int maxNums = 0 ; 
		int tempNum;
		//格式一 有 " - "
		if(dmList.get(0).getLotNumbers().indexOf("-") != -1){
			for (int i = 0; i < dmList.size(); i++) {
				tempNum = Integer.valueOf(dmList.get(i).getLotNumbers().replace(nowYear+speciesNameForShort+"-", ""));
				if(maxNums <tempNum){
					maxNums = tempNum;
				}
			}
			return maxNums;
		}else{
			//格式一 没有  " - " 
			for (int i = 0; i < dmList.size(); i++) {
				tempNum = Integer.valueOf(dmList.get(i).getLotNumbers().replace(nowYear+speciesNameForShort, ""));
				if(maxNums <tempNum){
					maxNums = tempNum;
				}
			}
			return maxNums;
		}
	}
	
	public static String getMD5Letters(String str){
		String md5 = str.replaceAll("\\d+","");
		if(md5.length() < 3 ){
			md5 = "AZX";
		}else{
			md5 = md5.substring(0,3).toUpperCase();
		}
		return md5;
	}
	
	private static String incaseEmpty(String str){
		if(StringUtils.isEmpty(str)){
			str = String.valueOf(RandomValue.getNum(0, 9));
		}
		return str;
	} 
	private String fillingZeroForMonth(int maxNum){
		int length = String.valueOf(maxNum).length();
		String res = "";
		switch (length) {
		case 1:
			res = "0"+String.valueOf(maxNum);
			break;
		case 2:
			res =  String.valueOf(maxNum);
			break;
		default:
			res =  "01";
		}
		return res;
	}
	private String fillingZero(int maxNum){
		
		int length = String.valueOf(maxNum).length();
		String res = "";
		switch (length) {
		case 1:
			res = "000"+String.valueOf(maxNum);
			break;
		case 2:
			res = "00"+String.valueOf(maxNum);
			break;
		case 3:
			res = "0"+String.valueOf(maxNum);
			break;
		case 4:
			res = String.valueOf(maxNum);
			break;
		default:
			res = "0001";
			break;
		}
		return res;
		
	} 
	
}
