package com.eboy.common.util;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: StringUtil 
 * @Description: 字符串工具类TODO 
 * @author ExceptionalBoy 
 * @date 2017年7月18日 上午7:51:30 
 *
 */
public class StringUtil {
	
	/**
	 * 半角数字字符数组
	 */
	private static final Character[] DBC_CASE_NUMBER = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
	/**
	 * 半角大写字母字符数组
	 */
	private static final Character[] DBC_UPPER_CASE_LETTER = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	/**
	 * 半角小写字母字符数组
	 */
	private static final Character[] DBC_LOWER_CASE_LETTER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
	/**
	 * 半角符号字符数组
	 */
	private static final Character[] DBC_CASE_SYMBOL = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '\\', '|', '[', ']', ';', ':', '\'', '"', ',', '<', '.', '>', '/', '?'};
	/**
	 * 全角数字字符数组
	 */
	private static final Character[] SBC_CASE_NUMBER = { '１', '２', '３', '４', '５', '６', '７', '８', '９', '０'};
	/**
	 * 全角大写字母字符数组
	 */
	private static final Character[] SBC_UPPER_CASE_LETTER = {'Ａ', 'Ｂ', 'Ｃ', 'Ｄ', 'Ｅ', 'Ｆ', 'Ｇ', 'Ｈ', 'Ｉ', 'Ｊ', 'Ｋ', 'Ｌ', 'Ｍ', 'Ｎ', 'Ｏ', 'Ｐ', 'Ｑ', 'Ｒ', 'Ｓ', 'Ｔ', 'Ｕ', 'Ｖ', 'Ｗ', 'Ｘ', 'Ｙ', 'Ｚ'};
	/**
	 * 全角小写字母字符数组
	 */
	private static final Character[] SBC_LOWER_CASE_LETTER = {'ａ', 'ｂ', 'ｃ', 'ｄ', 'ｅ', 'ｆ', 'ｇ', 'ｈ', 'ｉ', 'ｊ', 'ｋ', 'ｌ', 'ｍ', 'ｎ', 'ｏ', 'ｐ', 'ｑ', 'ｒ', 'ｓ', 'ｔ', 'ｕ', 'ｖ', 'ｗ', 'ｘ', 'ｙ', 'ｚ'};
	/**
	 * 全角符号字符数组
	 */
	private static final Character[] SBC_CASE_SYMBOL = {'！', '＠', '＃', '＄', '％', '︿', '＆', '＊', '（', '）', '－', '＿', '＝', '＋', '＼', '｜', '【', '】', '；', '：', '\'', '"', '，', '〈', '。', '〉', '／', '？'};
	/**
	 * false
	 */
	private static final Boolean FALSE = Boolean.FALSE;
	/**
	 * true
	 */
	private static final Boolean TRUE = Boolean.TRUE;
	/**
	 * 空字符串
	 */
	private static final String EMPTY = "";

	/**
	 * 真假标志(默认为false)
	 */
	private static Boolean flag = Boolean.FALSE;
	
	
	/**
	 * 
	 * @Title: MD5String 
	 * @Description: 获取字符串MD5加密后的结果TODO
	 * @author ExceptionalBoy 
	 * @param @param originalString 原始字符串
	 * @param @param salt 盐
	 * @param @param hashIterations MD5加密次数
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年7月18日 上午8:43:17
	 */
	public static String MD5String(String originalString, String salt, int hashIterations){
		Md5Hash md5 = new Md5Hash(originalString, salt, hashIterations);
		return md5.toString();
	}
	
	/**
	 * 
	 * @Title: checkString 
	 * @Description: 此方法用于检测字符串是否为空，如果为空则返回"",不为空则返回string.trim()TODO
	 * @author ExceptionalBoy 
	 * @param @param string
	 * @param @return 
	 * @return String   
	 * @throws
	 * @date 2017年8月14日 上午10:06:19
	 */
	public static String checkString(String string){
		if(string == null || "".equals(string.trim())){
			return "";
		}else{
			return string.trim();
		}
	}
	
	/**
	 * 
	 * @Title: isEmpty 
	 * @Description:检测字符串是否为空 TODO 
	 * @param @param string
	 * @param @return 
	 * @return boolean 
	 * @throws
	 */
	public static boolean isEmpty(String string){
		if(string != null && !"".equals(string.trim()))
			return false;
		else 
			return true;
	}
	/**
	 * 
	 * @Title: isEmpty
	 * @Description: TODO(字符串是否为空)
	 * @param @param source 需要判断的字符串
	 * @param @param spaceSensitive 是否将只有空格的字符串判断为空字符串
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isEmpty(String source,Boolean spaceSensitive){
		return (spaceSensitive) ? ((null == source || EMPTY.equals(source.trim())) ? TRUE : FALSE) : ((null == source || EMPTY.equals(source)) ? TRUE : FALSE);
	}

	/**
	 * 
	 * @Title: isContain
	 * @Description: TODO(判断字符串中是否包含目标字符串)
	 * @param @param source
	 * @param @param isAllSensitive	是否要求全部包含(true: source中包含所有targets返回true false: source中只要包含targets中的一个就返回true)
	 * @param @param targets
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isContain(String source,Boolean isAllSensitive, String...targets){
		if(isEmpty(source, TRUE)){
			return FALSE;
		}
		int length = targets.length;
		if(targets == null || length < 1){
			return FALSE;
		}
		int count = 0;
		for (String s : targets) {
			if(source.contains(s)){
				count ++;
			}
		}
		return (isAllSensitive && count == length) ? TRUE : ((!isAllSensitive && count < length)? TRUE : FALSE);
	}

	/**
	 * 
	 * @Title: isIncludeNumber
	 * @Description: TODO(判断字符串中是否含有数字)
	 * @param @param source 需要判断的字符串
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeNumber(String source){
		String regEx = "[0-9]";
		return regExInclude(source, regEx);
	}

	/**
	 * 
	 * @Title: isIncludeSymbol
	 * @Description: TODO(判断字符串中是否包含特殊字符)
	 * @param @param source 需要判断的字符串
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeSymbol(String source){
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		return regExInclude(source, regEx);
	}

	/**
	 * 
	 * @Title: isIncludeLetter
	 * @Description: TODO(判断字符串中是否包含字母(不区分大小写))
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeLetter(String source){
		String regEx = "[a-zA-Z]";
		return regExInclude(source, regEx);
	}
	/**
	 * 
	 * @Title: isIncludeUpperCase
	 * @Description: TODO(判断字符串中是否包含大写字母)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeUpperCase(String source){
		String regEx = "[A-Z]";
		return regExInclude(source, regEx);
	}

	/**
	 * 
	 * @Title: isIncludeLowerCase
	 * @Description: TODO(判断字符串中是否包含小写字母)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeLowerCase(String source){
		String regEx = "[a-z]";
		return regExInclude(source, regEx);
	}
	/**
	 * 
	 * @Title: isIncludeDBCCase
	 * @Description: TODO(判断字符串中是否包含半角字符)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeDBCCase(String source){
		flag = isEmpty(source, TRUE);
		if(flag){
			return FALSE;
		}else{
			String regEx = "[^\\x00-\\xff]";
			char[] charArray = source.toCharArray();
			for (char c : charArray) {
				String temp = String.valueOf(c);
				if(!temp.matches(regEx)){
					flag = TRUE;
					break;
				}
			}
			return flag;
		}
	}
	/**
	 * 
	 * @Title: isIncludeSBCCase
	 * @Description: TODO(判断字符串中是否包含全角字符)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeSBCCase(String source){
		flag = isEmpty(source, TRUE);
		if(flag){
			return FALSE;
		}else{
			String regEx = "[^\\x00-\\xff]";
			char[] charArray = source.toCharArray();
			for (char c : charArray) {
				String temp = String.valueOf(c);
				if(temp.matches(regEx)){
					flag = TRUE;
					break;
				}
			}
			return flag;
		}
	}
	/**
	 * 
	 * @Title: isIncludeCHS
	 * @Description: TODO(判断字符串中是否包含汉字)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isIncludeCHS(String source){
		flag = isEmpty(source, TRUE);
		if(flag){
			return FALSE;
		}else{
			String regEx = "[\\u4e00-\\u9fa5]";
			char[] charArray = source.toCharArray();
			for (char c : charArray) {
				String temp = String.valueOf(c);
				if(temp.matches(regEx)){
					flag = TRUE;
					break;
				}
			}
			return flag;
		}
	}

	/**
	 * 
	 * @Title: isAllNumber
	 * @Description: TODO(判断字符串是否都是数字)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isAllNumber(String source){
		String regEx = "[0-9]+";
		return regExMatche(source, regEx);
	}

	/**
	 * 
	 * @Title: isAllLetter
	 * @Description: TODO(判断字符串是否全部是字母(不区分大小写))
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isAllLetter(String source){
		String regEx = "[a-zA-Z]+";
		return regExMatche(source, regEx);
	}

	/**
	 * 
	 * @Title: isAllUpperCase
	 * @Description: TODO(判断字符串是否都是大写字母)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isAllUpperCase(String source){
		String regEx = "[A-Z]+";
		return regExMatche(source, regEx);
	}

	/**
	 * 
	 * @Title: isAllLowerCase
	 * @Description: TODO(判断字符串都是小写字母)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isAllLowerCase(String source){
		String regEx = "[a-z]+";
		return regExMatche(source, regEx);
	}

	/**
	 * 
	 * @Title: isAllNumberAndLetter
	 * @Description: TODO(判断字符串是否为数字和字母的组合(字母不区分大小写))
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 * 
	 */
	public static Boolean isAllNumberAndLetter(String source){
		String regEx = "[0-9a-zA-Z]+";
		flag = regExMatche(source, regEx);
		return flag ? (isIncludeNumber(source) && isIncludeLetter(source)) : flag;
	}

	/**
	 * 
	 * @Title: isAllCHS
	 * @Description: TODO(判断字符串是否全部为中文)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isAllCHS(String source){
		String regEx = "[\\u4e00-\\u9fa5]+";
		return regExMatche(source, regEx);
	}

	/**
	 * 
	 * @Title: isAllBSCCase
	 * @Description: TODO(判断字符串是否都是全角字符)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isAllBSCCase(String source){
		String regEx = "[^\\x00-\\xff]+";
		return regExMatche(source, regEx);
	}

	/**
	 * 
	 * @Title: isAllDSCCase
	 * @Description: TODO(判断字符串是否都是半角字符)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isAllDSCCase(String source){
		String regEx = "[\\x00-\\xff]+";
		return regExMatche(source, regEx);
	}

	/**
	 * 
	 * @Title: isStartOrEndWithSpace
	 * @Description: TODO(判断字符串是否以空格开头或结尾)
	 * @param @param source
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	public static Boolean isStartOrEndWithSpace(String source){
		if(isEmpty(source, FALSE)){
			return FALSE;
		}
		return (source.trim().equals(source)) ? FALSE : TRUE;
	}
	/**
	 * 
	 * @Title: regExInclude
	 * @Description: TODO(根据正则表达式判断字符串中是否包含特定的字符)
	 * @param @param source 需要判断的字符串
	 * @param @param regEx	正则表达式
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	private static Boolean regExInclude(String source, String regEx){
		flag = (isEmpty(source, TRUE) || isEmpty(regEx, TRUE));
		if(flag){
			return FALSE;
		}else{
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(source);
			return matcher.find();
		}
	}

	/**
	 * 
	 * @Title: regExMatche
	 * @Description: TODO(根据正则表达式判断字符串是否匹配)
	 * @param @param source
	 * @param @param regEx
	 * @param @return    参数
	 * @return Boolean    返回类型
	 * @throws
	 */
	private static Boolean regExMatche(String source,String regEx){
		flag = (isEmpty(source, TRUE) || isEmpty(regEx, TRUE));
		if(flag){
			return FALSE;
		}else{
			Pattern pattern = Pattern.compile(regEx);
			return pattern.matcher(source).matches();
		}
	}

	////////////////////////////////////////////////////////
	///////////////////////字符串处理方法////////////////////////
	////////////////////////////////////////////////////////

	/**
	 * 
	 * @Title: getRandomString
	 * @Description: TODO(随机生成一个字符串)
	 * @param @param length 随机字符串的长度(等于0返回"",大于0正常返回，小于0返回一个0-20位随机长度的字符串)
	 * @param @param hasNumber 随机字符串中是否包含数字字符
	 * @param @param hasUpperCase 随机字符串中是否包含大写字母字符
	 * @param @param hasLowerCase 随机字符串中是否包含小写字母字符
	 * @param @param hasSymbol 随机字符串中是否包含特殊符号字符
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String getRandomString(int length,Boolean hasNumber,Boolean hasUpperCase,Boolean hasLowerCase,Boolean hasSymbol){

		List<Character> finalList = new ArrayList<Character>();
		StringBuffer buffer = new StringBuffer();

		if(hasNumber){
			finalList.addAll(Arrays.asList(DBC_CASE_NUMBER));
		}
		if(hasUpperCase){
			finalList.addAll(Arrays.asList(DBC_UPPER_CASE_LETTER));
		}
		if(hasLowerCase){
			finalList.addAll(Arrays.asList(DBC_LOWER_CASE_LETTER));
		}
		if(hasSymbol){
			finalList.addAll(Arrays.asList(DBC_CASE_SYMBOL));
		}
		if(finalList.size() > 0){
			int maxIndex = finalList.size()-1;
			int size = (length >= 0) ? length : (new Random().nextInt(20)); 
			int i = 0;
			while(i < size){
				buffer.append(finalList.get(new Random().nextInt(maxIndex)));
				i++;
			}
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @Title: changeDBCToSBC
	 * @Description: TODO(将字符串中的全角符号字符全部转换为半角符号字符(中文及特殊符号除外))
	 * @param @param source
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String changeSBCToDBC(String source){
		if(isEmpty(source, TRUE)){
			return source;
		}
		List<Character> DBCSymbol = new ArrayList<Character>();
		DBCSymbol.addAll(Arrays.asList(DBC_CASE_NUMBER));
		DBCSymbol.addAll(Arrays.asList(DBC_LOWER_CASE_LETTER));
		DBCSymbol.addAll(Arrays.asList(DBC_UPPER_CASE_LETTER));
		DBCSymbol.addAll(Arrays.asList(DBC_CASE_SYMBOL));

		List<Character> SBCSymbol = new ArrayList<Character>();
		SBCSymbol.addAll(Arrays.asList(SBC_CASE_NUMBER));
		SBCSymbol.addAll(Arrays.asList(SBC_LOWER_CASE_LETTER));
		SBCSymbol.addAll(Arrays.asList(SBC_UPPER_CASE_LETTER));
		SBCSymbol.addAll(Arrays.asList(SBC_CASE_SYMBOL));

		for(int i = 0;i < SBCSymbol.size();i++){
			String s = SBCSymbol.get(i).toString();
			String r = DBCSymbol.get(i).toString();
			source = source.replace(s, r);
		}
		//中文“”
		source  = source.replace("“","\"");
		source  = source.replace("”","\"");
		return source;
	}

	/**
	 * 
	 * @Title: getUnicode
	 * @Description: TODO(获取字符转码为Unicode的字符串值)
	 * @param @param ch
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String getUnicode(char source) {  
		if (source < 0x10) {  
			return "\\u000" + Integer.toHexString(source);  
		} else if (source < 0x100) {  
			return "\\u00" + Integer.toHexString(source);  
		} else if (source < 0x1000) {  
			return "\\u0" + Integer.toHexString(source);  
		}  
		return "\\u" + Integer.toHexString(source);  
	}  

	/**
	 * 
	 * @Title: toString
	 * @Description: TODO(将对象转换为字符串，如果为null返回"")
	 * @param @param object
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String toString(Object object){
		return (null == object) ? EMPTY : object.toString();
	}

	/**
	 * 
	 * @Title: toString
	 * @Description: TODO(将对象转换为字符串，如果为null返回target)
	 * @param @param object
	 * @param @param target
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String toString(Object object,String target){
		return (null == object) ? target : object.toString();
	}

	/**
	 * 
	 * @Title: removeStart
	 * @Description: TODO(如果源字符串以目标字符串开头，则返回从源字符串中去除开头的目标子符串的结果，否则返回源字符串)
	 * @param @param source
	 * @param @param target
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String removeStart(String source,String target){
		if(isEmpty(source, TRUE) || isEmpty(target, TRUE)){
			return source;
		}
		if(source.startsWith(target)){
			source = source.substring(target.length());
		}
		return source;
	}

	/**
	 * 
	 * @Title: removeEnd
	 * @Description: TODO(如果源字符串以目标字符串结尾，则返回从源字符串中去除结尾的目标子符串的结果，否则返回源字符串)
	 * @param @param source
	 * @param @param target
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String removeEnd(String source,String target){
		if(isEmpty(source, TRUE) || isEmpty(target, TRUE)){
			return source;
		}
		if(source.endsWith(target)){
			source = source.substring(0,source.length()-target.length());
		}
		return source;
	}
	
	/**
	 * 
	 * @Title: repeat
	 * @Description: TODO(返回source字符串重复times次的新字符串)
	 * @param @param source
	 * @param @param times
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String repeat(String source,int times){
		if(isEmpty(source, FALSE)){
			return null;
		}
		if(times < 1){
			return EMPTY;
		}
		StringBuffer buffer = new StringBuffer();
		for(int i = 0;i < times;i++){
			buffer.append(source);
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * @Title: repeat
	 * @Description: TODO(返回source字符串重复times次且中间用separator分割的新字符串)
	 * @param @param source
	 * @param @param separator
	 * @param @param times
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String repeat(String source,String separator,int times){
		String result = repeat(source + separator, times);
		return removeEnd(result, separator);
	}

	/**
	 * 
	 * @Title: rightPad
	 * @Description: TODO(判断字符串是否达到指定的长度，如果没有，将目标字符拼接在原字符串尾部凑够长度)
	 * @param @param source
	 * @param @param size
	 * @param @param target
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String rightPad(String source,int size,char target){
		if(isEmpty(source, FALSE)){
			return null;
		}
		int length = source.length();
		if(length < size){
			int count = size - length;
			for(int i = 0; i<count; i++){
				source = source.concat(String.valueOf(target));
			}
		}
		return source;
	}
	
	/**
	 * 
	 * @Title: rightPad
	 * @Description: TODO(判断字符串是否达到指定的长度，如果没有，将目标字符串拼接在原字符串尾部凑够长度)
	 * @param @param source
	 * @param @param size
	 * @param @param target
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String rightPad(String source,int size,String target){
		if(isEmpty(source, FALSE)){
			return null;
		}
		if(isEmpty(target, FALSE)){
			target = " ";
		}
		int length = source.length();
		if(length < size){
			char[] targets = target.toCharArray();
			List<Character> list = new ArrayList<Character>();
			for (char c : targets) {
				list.add(c);
			}
			int count = size - length;
			List<Character> finalTarget = new ArrayList<Character>();
			for(int i = 0 ;i < (count/(list.size()) +1);i++){
				Arrays.asList(targets);
				finalTarget.addAll(list);
			}
			for(int i = 0;i<count;i++){
				source = source.concat(String.valueOf(finalTarget.get(i)));
			}
		}
		return source;
 	}
	
	/**
	 * 
	 * @Title: leftPad
	 * @Description: TODO(判断字符串是否达到指定的长度，如果没有，将目标字符拼接在原字符串前部凑够长度)
	 * @param @param source
	 * @param @param size
	 * @param @param target
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String leftPad(String source,int size,char target){
		if(isEmpty(source, FALSE)){
			return null;
		}
		int length = source.length();
		StringBuffer buffer = new StringBuffer();
		if(length < size){
			int count = size - length;
			for(int i = 0; i<count; i++){
				buffer.append(String.valueOf(target));
			}
		}
		buffer.append(source);
		return buffer.toString();
	}
	
	/**
	 * 
	 * @Title: leftPad
	 * @Description: TODO(判断字符串是否达到指定的长度，如果没有，将目标字符串拼接在原字符串前部凑够长度)
	 * @param @param source
	 * @param @param size
	 * @param @param target
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String leftPad(String source,int size,String target){
		if(isEmpty(source, FALSE)){
			return null;
		}
		if(isEmpty(target, FALSE)){
			target = " ";
		}
		int length = source.length();
		StringBuffer buffer = new StringBuffer();
		if(length < size){
			char[] targets = target.toCharArray();
			List<Character> list = new ArrayList<Character>();
			for (char c : targets) {
				list.add(c);
			}
			int count = size - length;
			List<Character> finalTarget = new ArrayList<Character>();
			for(int i = 0 ;i < (count/(list.size()) +1);i++){
				Arrays.asList(targets);
				finalTarget.addAll(list);
			}
			for(int i = 0;i<count;i++){
				buffer.append(String.valueOf(finalTarget.get(i)));
			}
		}
		buffer.append(source);
		return buffer.toString();
	}
	
	/**
	 * 
	 * @Title: reverse
	 * @Description: TODO(字符串转置)
	 * @param @param source
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String reverse(String source) {  
        if (isEmpty(source, FALSE)) {  
            return null;  
        }  
        return new StringBuilder(source).reverse().toString();  
    }
	
	/**
	 * 
	 * @Title: swapCase
	 * @Description: TODO(字符串大小写反转)
	 * @param @param source
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String swapCase(String source){
		if(isEmpty(source, TRUE)){
			return source;
		}
		char[] sourceArr = source.toCharArray();
		flag = TRUE;
		for(int i = 0; i < sourceArr.length; i++){
			char temp = sourceArr[i];
			if(Character.isUpperCase(temp)){
				sourceArr[i] = Character.toLowerCase(temp);
				flag = FALSE;
			}else if(Character.isTitleCase(temp)){
				sourceArr[i] = Character.toLowerCase(temp);
				flag = FALSE;
			}else if(Character.isLowerCase(temp)){
				if(flag){
					sourceArr[i] = Character.toTitleCase(temp);
					flag = FALSE;
				}else{
					sourceArr[i] = Character.toUpperCase(temp);
				}
			}else{
				flag = Character.isWhitespace(temp);
			}
		}
		return new String(sourceArr);
	}
	
	/**
	 * 
	 * @Title: subStringAfterLast
	 * @Description: TODO(截取目标字符串在源字符串中最后一次出现位置之后的字符串)
	 * @param @param source
	 * @param @param targets
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String subStringAfterLast(String source,String targets){
		if(isEmpty(source, TRUE) || isEmpty(targets, FALSE)){
			return source;
		}
		int pos = source.lastIndexOf(targets);  
        if (pos == -1) {  
            return source;  
        }  
        return source.substring(pos + targets.length());
	}
	
	/**
	 * 
	 * @Title: subStringBeforeLast
	 * @Description: TODO(截取目标字符串在源字符串中最后一次出现位置之前的字符串)
	 * @param @param source
	 * @param @param targets
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String subStringBeforeLast(String source,String targets){
		if(isEmpty(source, TRUE) || isEmpty(targets, FALSE)){
			return source;
		}
		int pos = source.lastIndexOf(targets);  
        if (pos == -1) {  
            return source;  
        }  
        return source.substring(0,pos);
	}
	
	/**
	 * 
	 * @Title: subStringAfterLast
	 * @Description: TODO(截取目标字符串在源字符串中第一次出现位置之后的字符串)
	 * @param @param source
	 * @param @param targets
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String subStringAfter(String source,String targets){
		if(isEmpty(source, TRUE) || isEmpty(targets, FALSE)){
			return source;
		}
		int pos = source.indexOf(targets);  
        if (pos == -1) {  
            return source;  
        }  
        return source.substring(pos + targets.length());
	}
	
	/**
	 * 
	 * @Title: subStringBeforeLast
	 * @Description: TODO(截取目标字符串在源字符串中第一次出现位置之前的字符串)
	 * @param @param source
	 * @param @param targets
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String subStringBefore(String source,String targets){
		if(isEmpty(source, TRUE) || isEmpty(targets, FALSE)){
			return source;
		}
		int pos = source.indexOf(targets);  
        if (pos == -1) {  
            return source;  
        }  
        return source.substring(0,pos);
	}
	
	/**
	 * 
	 * @Title: trimString
	 * @Description: TODO(如果字符串没有超过最长显示长度返回原字符串，否则从开头截取指定长度并加...返回)
	 * @param @param source
	 * @param @param length
	 * @param @return    参数
	 * @return String    返回类型
	 * @throws
	 */
	public static String trimString(String source, int length){
		if(isEmpty(source, FALSE)){
			return source;
		}
		if(source.length() > length){
			return (length-3 > 0) ? (source.substring(0, length-3) + "...") : (source.substring(0, length) + "...");
		}else{
			return source;
		}
	}
}
