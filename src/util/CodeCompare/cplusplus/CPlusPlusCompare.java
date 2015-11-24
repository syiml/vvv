package util.CodeCompare.cplusplus;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;

public class CPlusPlusCompare{
	/*C++关键字*/
	private static String keyWords="and|asm|auto|bad_cast|bad_typeid|bool|break|case|catch|char|class|const|const_cast"+
	"|continue|default|delete|do|double|dynamic_cast|else|enum|except|explicit|extern|false|finally|float|for"+
	"|friend|goto|if|inline|int|long|mutable|namespace|new|operator|or|private|protected|public|register|reinterpret_cast"+
	"|return|short|signed|sizeof|static|static_cast|struct|switch|template|this|throw|true|try|type_info|typedef"+
	"|typeid|typename|union|unsigned|using|virtual|void|volatile|wchar_t|while";
	private static HashSet<String>keyWordSet = new HashSet<String>();
	private static LD ld = new LD();
	static{
		String list[]=keyWords.split("\\|");
		Collections.addAll(keyWordSet, list);
	}
	public CPlusPlusCompare(){}
	private String delVariables(String code){
		code = "   "+code+"  ";
	//System.out.println("!"+code);
		int pos1 = 0,pos2=0;
		int len = code.length();
		boolean isVariables=false;
		StringBuffer ret = new StringBuffer();
		while(pos1<len){
			pos2++;
			if(isVariables){
				if(code.substring(pos2,pos2+2).replaceAll("[0-9a-zA-Z_][^a-zA-Z_]", "").equals("")){
					isVariables = false;
					String vv = code.substring(pos1,pos2+1);
					if(keyWordSet.contains(vv)){
						ret.append(vv);
						//System.out.println("vv="+vv);
					}
					pos1 = pos2+1;
				}
			}else{
				if(code.substring(pos2,pos2+2).replaceAll("[^\\._a-zA-Z][_a-zA-Z]", "").equals("")){
					isVariables = true;
					ret.append(code.substring(pos1,pos2+1));
					//System.out.println(code.substring(pos1,pos2+1));
					pos1 = pos2+1;
				}
			}
			if(pos2 == len-2)break;
		}
		
		return ret.toString().trim();
		//return code.replaceAll("(?<=([^\\._a-zA-Z]))[a-zA-Z_]+[0-9_a-zA-Z]*(?=([^a-zA-Z_]))", "");
	}
	public String getPreProcessedCode(String code) {
		// TODO Auto-generated method stub
		try{
			int pos1 = 0,pos2 = 0;
			int len = code.length();
			boolean isString = false;
			StringBuffer ret = new StringBuffer();
			while(pos1<len){
				pos2++;
				if(isString){
					if(pos2<len-1){
						if(code.substring(pos2, pos2+1).equals("\"") && !code.subSequence(pos2-1, pos2).equals("\\")){
							isString  = false;
							ret.append(delVariables(code.substring(pos1, pos2+1)));
							pos1 = pos2+1;
						}
					}else{
						break;
					}
				}else{
					if(pos2<len-1){
						if(code.substring(pos2, pos2+1).equals("\"")){
							isString  = true;
							ret.append(delVariables(code.substring(pos1, pos2)));
							pos1 = pos2;
						}
					}else{
						ret.append(delVariables(code.substring(pos1, code.length())));
						break;
					}
				}
			}
			code = ret.toString();
			//删除所有空格和换行
			code=code.replaceAll("\\s", "");

		}catch(Exception e){
			e.printStackTrace();
		}
		return code;
	}
	public double getSimilarity(String code1, String code2) {
		// TODO Auto-generated method stub
		code1=getPreProcessedCode(code1);
		code2=getPreProcessedCode(code2);
		return 1-ld.ld(code1, code2)*1.0/Math.max(code1.length(), code2.length());
	}
	public static void main(String[]args) throws IOException{
		CPlusPlusCompare cmp = new CPlusPlusCompare();
		double s=cmp.getSimilarity("#include<stdio.h>\n" +
						"char s[1000010];\n" +
						"int main()\n" +
						"{\n" +
						"  int i,n,k,co,aa,aai,o,j;\n" +
						"  int a[10];\n" +
						"  while(scanf(\"%d\",&n)!=EOF)\n" +
						"  {\n" +
						"    aa=100010;\n" +
						"    for(aai=9,i=1;i<=9;i++)\n" +
						"    {\n" +
						"      scanf(\"%d\",&a[i]);\n" +
						"      if(a[i]<=aa)\n" +
						"      {\n" +
						"        aa=a[i];\n" +
						"        aai=i;\n" +
						"      }\n" +
						"    }\n" +
						"    if(n<aa)\n" +
						"      printf(\"-1\\n\");\n" +
						"    else{\n" +
						"    co=n/aa;\n" +
						"    k=n%aa;\n" +
						"    j=0;\n" +
						"    while(k>0||co>0)\n" +
						"    {\n" +
						"      if(k>0)\n" +
						"      {\n" +
						"        for(i=9;i>aai;i--)\n" +
						"          if(k+a[aai]>=a[i])\n" +
						"            break;\n" +
						"        if(i==aai) k=0;\n" +
						"        else k=k+aa-a[i];\n" +
						"        o=i;\n" +
						"      }\n" +
						"      else o=aai;\n" +
						"      s[++j]='0'+o;\n" +
						"      co--;\n" +
						"}\n" +
						"    }\n" +
						"    s[j]=0;\n" +
						"    printf(\"%s\\n\",s);\n" +
						"  }\n" +
						"  return 0;\n" +
						"}"

				,


				"#include<stdio.h>\n" +
						"char s[1000010];\n" +
						"int main()\n" +
						"{\n" +
						"  int a[10];\n" +
						"  int i,n,o,j,k,co,mt,mti;\n" +
						"  while(scanf(\"%d\",&n)!=EOF)\n" +
						"  {\n" +
						"    mt=100010;\n" +
						"    for(mti=9,i=1;i<=9;i++)\n" +
						"    {\n" +
						"      scanf(\"%d\",&a[i]);\n" +
						"      if(a[i]<=mt)\n" +
						"      {\n" +
						"        mt=a[i];\n" +
						"        mti=i;\n" +
						"      }\n" +
						"    }\n" +
						"    if(n<mt)\n" +
						"    {\n" +
						"      printf(\"-1\\n\");\n" +
						"      continue;\n" +
						"    }\n" +
						"    co=n/mt;\n" +
						"    k=n%mt;\n" +
						"    j=0;\n" +
						"    while(k>0||co>0)\n" +
						"    {\n" +
						"      if(k>0)\n" +
						"      {\n" +
						"        for(i=9;i>mti;i--)\n" +
						"        {\n" +
						"          if(k+a[mti]>=a[i])\n" +
						"            break;\n" +
						"        }\n" +
						"        if(i==mti) k=0;\n" +
						"        else k=k+mt-a[i];\n" +
						"        o=i;\n" +
						"      }\n" +
						"      else o=mti;\n" +
						"      s[j++]=o+'0';\n" +
						"      co--;\n" +
						"    }\n" +
						"    s[j]=0;\n" +
						"    printf(\"%s\\n\",s);\n" +
						"  }\n" +
						"  return 0;\n" +
						"}");

		System.out.println(s);
//
//
//		File dic= new File("F:\\AllSubmits");
//		String names[]={"3568.cpp","3569.cpp","3570.cpp","3571.cpp","3572.cpp"};
//
//		for(String name:names){
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("F:/"+name)));
//			//bw.write("题目："+name);
//			System.out.println("题目："+name);
//			bw.newLine();
//			ArrayList<String> idList =new ArrayList<String>();
//			ArrayList<String> codeList =new ArrayList<String>();
//			for(File f1:dic.listFiles()){
//				File f2 = new File(f1.getAbsoluteFile()+"\\"+name);
//				if(f2.exists()){
//					idList.add(f1.getName());
//					codeList.add(cmp.getPreProcessedCode(f2.getAbsolutePath()));
//				}
//			}
//			for(int i=0;i<codeList.size();i++){
//				for(int j=i+1;j<codeList.size();j++){
//					double s = cmp.getSimilarity(codeList.get(i), codeList.get(j));
//					if(s>=0.7){
//						bw.write(idList.get(i)+"\t"+idList.get(j)+"\t"+s);
//						bw.newLine();
//					}
//				}
//			}
//			bw.close();
//		}
//
	}
}
