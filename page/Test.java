package editJvmByte;

import java.io.*;
import java.util.Date;

import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.CPInfo;
import org.gjt.jclasslib.structures.ClassFile;
//import org.gjt.jclasslib.structures.constants.ConstantDoubleInfo;
//import org.gjt.jclasslib.structures.constants.ConstantIntegerInfo;
import org.gjt.jclasslib.structures.constants.ConstantUtf8Info;

public class Test {
	public static void main(String[] args) throws Exception {

		String filePath = "F:\\src2\\edit-jvm-byte\\bin\\ContentAssistPreference.class";
		FileInputStream fis = new FileInputStream(filePath);

		DataInput di = new DataInputStream(fis);
		ClassFile cf = new ClassFile();
		cf.read(di);
		
		CPInfo[] infos = cf.getConstantPool();

		int count = infos.length;
		System.out.println(count);
		
		for (int i = 0; i < count; i++) {
			if (infos[i] != null) {
				System.out.print(i);
				System.out.print(" = ");
				System.out.print(infos[i].getVerbose());
				System.out.print(" = ");
				System.out.println(infos[i].getTagVerbose());
				if (i == 24) {//刚刚找到的是21位置
					ConstantUtf8Info uInfo = (ConstantUtf8Info) infos[i]; //刚刚那里是CONSTANT_Utf-8_info所以这里要用这个
					uInfo.setBytes("<=:.@()[]abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
					infos[i] = uInfo; 
				}
			}
		}
		//这种方式也可以，一样的
/*		if(infos[count] != null) {
			ConstantUtf8Info uInfo = (ConstantUtf8Info) infos[i]; //刚刚那里是CONSTANT_Utf-8_info所以这里要用这个
			uInfo.setBytes("baidu".getBytes());
			infos[count] = uInfo;
		}*/
		
		cf.setConstantPool(infos);
		fis.close();
		File f = new File("F:\\src2\\edit-jvm-byte\\bin\\ContentAssistPreference" + System.currentTimeMillis() + ".class");
		ClassFileWriter.writeToFile(f, cf);
	}
}