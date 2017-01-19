package aoeiuv020;
import java.util.*;
import java.io.*;
import java.nio.charset.*;
import java.util.regex.*;
import okio.*;
/**
 * @author AoEiuV020
 * @version 1.0, 2017/01/19
 */
public class Main{
	public static void main(String[] args)throws Exception{
		File baidu=new File("res/ch3.txt");
		File google=new File("res/google.txt");
		Charset charsetBaidu=Charset.forName("utf-16le");
		Charset charsetGoogle=Charset.forName("utf-16le");
		Buffer bBaidu=new Buffer();
		//bom,fffe,读取时要跳过，写入时要写在开头，
		ByteString bom=new Buffer()
			.writeByte(0xff)
			.writeByte(0xfe)
			.readByteString();
		bBaidu.writeAll(Okio.source(baidu));
		//跳过bom,
		if(bBaidu.snapshot(2).equals(bom))
			bBaidu.skip(2);
		String sBaidu=bBaidu.readString(charsetBaidu);
		Buffer bGoogle=new Buffer();
		//写入bom,
		bGoogle.write(bom);
		bGoogle.writeString("# User dictionary for Google Pinyin Input\n",charsetGoogle);
		String baiduime="(.*)\\(([a-z|]*)\\) (\\d*)";
		Pattern p=Pattern.compile(baiduime);
		Matcher m=p.matcher(sBaidu);
		while(m.find()){
			String word=m.group(1);
			String pinyin=m.group(2);
			pinyin=pinyin.replace('|',' ');
			String sFrequency=m.group(3);
			long lFrequency=Long.parseLong(sFrequency);
			//这里有点问题，不知道百度的词频是怎么回事，
			//有的看起来像60000+真词频，有的看起来像55000+真词频，
			//但有的刚好60000有的刚好55000,
			if(lFrequency>=60000)
				lFrequency=lFrequency-60000;
			else if(lFrequency>=55000)
				lFrequency=lFrequency-55000;
			if(lFrequency==0)
				lFrequency=1;
			bGoogle.writeString(String.format("%s\t%d\t%s\n",word,lFrequency,pinyin),charsetGoogle);
		}
		bGoogle.readAll(Okio.sink(google));
		System.out.println("OK,"+google.getAbsolutePath());
		bGoogle.close();
	}
}

