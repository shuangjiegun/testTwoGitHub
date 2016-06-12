package juyuan;

import static org.junit.Assert.*;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import org.junit.Before;
import org.junit.Test;

public class Test3_pinyin {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//【
/*        String pinyinName = "";  
        char[] nameChar = "欢迎来到最棒的Java中文社区".toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {                 
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);                  
            }else{  
                pinyinName += nameChar[i];  
            }  
        }  
        System.out.println(pinyinName); */ //】
		
		String pinyinName = "";  
        char[] nameChar = "欢“迎来”到最棒的Java中文社区".toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
        	String s = String.valueOf(nameChar[i]);
            //if (nameChar[i] > 128) {  
        	if (s.matches("[\\u4e00-\\u9fa5]")) {
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];    
            }else{  
                pinyinName += nameChar[i];  
            }  
        }  
        System.out.println(pinyinName);
        
        
        
	}

}
