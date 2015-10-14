package CodeCompare.cplusplus;

public class LD {

	  /**  
	     * 计算矢量距离  
	     * Levenshtein Distance(LD)   
	     * @param str1 str1  
	     * @param str2 str2  
	     * @return ld  
	     */  
	    public int ld(String str1, String str2)   
	    {   
	        //Distance   
	        int [][] d;    
	        int n = str1.length();   
	        int m = str2.length();   
	        int i; //iterate str1   
	        int j; //iterate str2   
	        char ch1; //str1    
	        char ch2; //str2     
	        int temp;       
	        if (n == 0)   
	        {   
	            return m;   
	        }   
	        if (m == 0)   
	        {   
	            return n;   
	        }   
	        d = new int[2][m + 1];
	        for (j = 0; j <= m; j++)   
	        {    
	            d[0][j] = j;   
	        }   
	        for (i = 1; i <= n; i++)   
	        {
				d[i%2][0]=0;
	            ch1 = str1.charAt(i - 1);   
	            //match str2      
	            for (j = 1; j <= m; j++)   
	            {   
	                ch2 = str2.charAt(j - 1);   
	                if (ch1 == ch2)   
	                {   
	                    temp = 0;   
	                }   
	                else  
	                {   
	                    temp = 1;   
	                }
	                d[i%2][j] = min(d[(i-1)%2][j] + 1, d[i%2][j - 1] + 1, d[(i-1)%2][j - 1] + temp);
	            }   
	        }
	        return d[n%2][m];
	    }   
	    
	    private int min(int one, int two, int three)   
	    {   
	        int min = one;   
	        if (two < min)   
	        {   
	            min = two;   
	        }   
	        if (three < min)   
	        {   
	            min = three;   
	        }   
	        return min;   
	    }   
	    
	    /**  
	     * 计算相似度  
	     * @param str1 str1  
	     * @param str2 str2  
	     * @return sim    
	     */  
	    public double sim(String str1, String str2)   
	    {   
	        int ld = ld(str1, str2);   
	        return 1 - (double) ld / Math.max(str1.length(), str2.length());   
	    }   
	       
	    /**  
	     * 测试  
	     * @param args  
	     */  
	    public static void main(String[] args)   
	    {   
	        LD ld = new LD();   
	        double num = ld.sim("#include<stdio.h>\n" +
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
	        System.out.println(num);   
	    } 
	}