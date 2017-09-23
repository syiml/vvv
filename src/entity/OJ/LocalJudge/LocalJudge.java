package entity.OJ.LocalJudge;

import util.Pair;
import util.Tool;
import util.Vjudge.VjSubmitter;
import entity.RES;
import entity.Result;
import util.Main;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Syiml on 2015/10/14 0014.
 */
public class LocalJudge {
    private static String workPath = Main.config.topConfig.localJudgeWorkPath;
    private static String path     = workPath+"data\\";//标准数据存放 = paht/pid
    private static String outPath  = workPath+"run\\";//临时输出路径 = outPath/rid
    private static String gccPath  = workPath+"bin\\gcc\\bin\\";
    private static String shell    = workPath+"bin\\com.exe";
    private static String runshell = workPath+"bin\\run.exe";

    private static String codeFileExt(int lang){
        if(lang==0) return ".cc";
        if(lang==1) return ".c";
        return ".cc";
    }
    private static String getCmd(String path,String filename,int lang){
        if(lang==1){
            return gccPath+"gcc.exe -fno-asm -s -w -O1 -DONLINE_JUDGE -o "+path+"\\"+filename+" "+path+"\\"+filename+".c";
        }else{
            return gccPath+"g++.exe -fno-asm -s -w -O1 -DONLINE_JUDGE -o "+path+"\\"+filename+" "+path+"\\"+filename+".cc";
        }
    }
    private static void createFile(String path,String text) throws IOException {
//        Tool.debug(path);
        File outFile = null;
        outFile = new File(path);
        outFile.createNewFile();
        FileOutputStream outFileOutput = new FileOutputStream(outFile);
        outFileOutput.write(text.getBytes());
        outFileOutput.flush();
        outFileOutput.close();
    }
    private static void deleteFile(File file){
        if(file.isFile()) {
            if(!file.delete()) {
                Tool.log("Can not delete file:" + file.getAbsolutePath());
            }
        } else if(file.isDirectory()) {
            File[] var2 = file.listFiles();
            for(int var3 = 0; var3 < var2.length; ++var3) {
                deleteFile(var2[var3]);
            }
            if(!file.delete()) {
                Tool.log("Can not delete file:" + file.getAbsolutePath());
            }
        }
    }
    private static void delFile(String path){
        File file = new File(path);
        deleteFile(file);
    }
    private static boolean compile(VjSubmitter s, RES res){
        File workPath = new File(outPath + s.getSubmitInfo().rid+"\\");
        String fileName = "Main";
        if(!workPath.mkdirs()) {
            return false;
        } else {
            try {
                createFile(workPath.getAbsolutePath() +"\\"+ fileName +codeFileExt(s.getSubmitInfo().language), s.getSubmitInfo().code);
                String cmd = getCmd(workPath.getAbsolutePath(), fileName, s.getSubmitInfo().language);
                Runtime rt = Runtime.getRuntime();
                Process pro = rt.exec(shell);
                OutputStream proOutStr = pro.getOutputStream();
                proOutStr.write((cmd + "\n").getBytes());
            Tool.debug(cmd);
                proOutStr.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                String errorInfo = "";
                long time = System.currentTimeMillis();
                while (time + 10000L > System.currentTimeMillis() && (errorInfo = br.readLine()) != null) {
                    errorInfo = errorInfo + "\n";
                }
                br.close();
                try {
                    pro.waitFor();
                } catch (InterruptedException ignored){}
                File exe = new File(workPath.getAbsolutePath() +"\\"+ fileName + ".exe");
                //ServerConfig.debug(Tool.fixPath(workPath.getAbsolutePath()) + fileName + ".exe");
                if (!exe.isFile()) {
                    res.setR(Result.CE);
                    res.setCEInfo(errorInfo);
                    return false;
                }
            }catch (Exception e) {
                e.printStackTrace(System.err);
                return false;
            }
        }
        return true;
    }
    public static boolean existFile(File[] files,String filename){
        for (File file : files) {
            if (file.getName().equals(filename)) {
                return true;
            }
        }
        return false;
    }
    public static boolean compileSpjCPP(String pid,RES res){
        File workPath = new File(path+"\\"+pid+"\\");
        String fileName = "spj";
        try {
            String cmd = gccPath+"g++.exe -fno-asm -s -w -O1 -DONLINE_JUDGE -o "+path+pid+"\\spj "+path+pid+"\\spj.cpp";
            Runtime rt = Runtime.getRuntime();
            Process pro = rt.exec(shell);
            OutputStream proOutStr = pro.getOutputStream();
            proOutStr.write((cmd + "\n").getBytes());
            Tool.debug(cmd);
            proOutStr.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
            String errorInfo = "";
            long time = System.currentTimeMillis();
            while (time + 10000L > System.currentTimeMillis() && (errorInfo = br.readLine()) != null) {
                errorInfo = errorInfo + "\n";
            }
            br.close();
            try {
                pro.waitFor();
            } catch (InterruptedException ignored){}
            File exe = new File(workPath.getAbsolutePath() +"\\spj.exe");
            //ServerConfig.debug(Tool.fixPath(workPath.getAbsolutePath()) + fileName + ".exe");
            if (!exe.isFile()) {
                res.setR(Result.ERROR);
                res.setCEInfo("spj.cpp 编译错误\n"+errorInfo);
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }
    public static String fixPath(String path) {
        return path.endsWith("\\")?path:path + "\\";
    }
    private static int spjRun(String shell,String f_in,String f_out,String f_user) throws IOException {
        Process runExe = Runtime.getRuntime().exec(shell + " " + f_in + " " + f_out + " " + f_user);
        Tool.debug("spj执行:"+shell + " " + f_in + " " + f_out + " " + f_user);
        try {
            runExe.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return runExe.exitValue();
    }
    private static int spjRun(String file1,String file2,VjSubmitter s,RES res) throws IOException {
        //返回0表示AC
        //返回1表示WA
        Tool.debug("spj:"+file1+","+file2);
        Process runExe = Runtime.getRuntime().exec(runshell);
        OutputStream runExeOutputStream = runExe.getOutputStream();

        int FileListSize = 1;
        Pair<Integer,Integer> limit=Main.problems.getProblemLimit(Integer.parseInt(s.getSubmitInfo().pid));
        runExeOutputStream.write((limit.getKey()+"\n").getBytes());//时限(MS)
        runExeOutputStream.write(("30000\n").getBytes());//单点时限
        File file = new File(fixPath(outPath) + s.getSubmitInfo().getRid());
        try {
            String filename = "Main";
            long exMemory = 0L;
            runExeOutputStream.write(((limit.getValue() * 1024L + exMemory) * 1024L + "\n").getBytes());//内存限制MB
            String mainExe = filename+".exe";
            runExeOutputStream.write((file.getAbsolutePath() + "\\" + mainExe + "\n").getBytes());
            runExeOutputStream.write((file.getAbsolutePath() + "\\\n").getBytes());
            runExeOutputStream.write((FileListSize + "\n").getBytes());
            long TimeUsed = 0L;
            long MemoryUsed = 0L;
            File var20 = new File(file2);
            try {
                runExeOutputStream.write((file1 + "\n").getBytes());
                runExeOutputStream.write((fixPath(file.getAbsolutePath()) + var20.getName() + "\n").getBytes());
                runExeOutputStream.write((var20.getAbsolutePath() + "\n").getBytes());
            } catch (IOException ignored) {}
            finally {
                runExeOutputStream.flush();
            }
            String isOut = null;
            BufferedReader is = new BufferedReader(new InputStreamReader(runExe.getInputStream()));
            BufferedReader es = new BufferedReader(new InputStreamReader(runExe.getErrorStream()));
            String esOut = "";
            try {
                isOut = is.readLine();
                TimeUsed = Long.parseLong(isOut);
                isOut = is.readLine();
                MemoryUsed = Long.parseLong(isOut);
                while((isOut = es.readLine())!= null){
                    esOut = esOut + isOut;
                }
            } catch (Exception ignored) {}
            MemoryUsed -=  exMemory;
            runExe.waitFor();
            try {
                es.close();
                is.close();
            } catch (IOException ignored) {}

            int ret = runExe.exitValue();
            if(ret != 0 && esOut.contains("Exception")) {
                ret = 5;
            }
            Long TimeUsed_pre = Long.parseLong(res.getTime());
            Long MemoryUsed_pre = Long.parseLong(res.getTime());
            res.setTime(Math.max(TimeUsed_pre,TimeUsed)+"");
            res.setMemory(Math.max(MemoryUsed_pre, MemoryUsed) + "");
            Tool.debug("judge return:"+ret +" esOut:"+esOut);
            if(ret == 0 || ret == 1 || ret == 4){
                //SPJ
                ret = spjRun(path+s.getSubmitInfo().pid+"\\spj.exe",file1,file2,fixPath(file.getAbsolutePath()) + var20.getName());
                Tool.debug("spj return:"+ret);
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return -1;
        }
    }
    private static int spjRun(VjSubmitter s,RES res) throws IOException{
        //spj 程序
        File pathFile = new File(path+"\\"+s.getSubmitInfo().pid+"\\");
        if(!pathFile.isDirectory()) {
            System.err.println(path + "not exists");
            return -1;
        } else {
            File[] pathFiles = pathFile.listFiles();
            File file;
            int i;
            //先检查是否存在spj.exe文件
            if(!existFile(pathFiles,"spj.exe")){
                if(existFile(pathFiles,"spj.cpp")){
                    if(!compileSpjCPP(s.getSubmitInfo().pid,res)){
                        return -1;
                    }
                }else{
                    res.setR(Result.ERROR);
                    res.setCEInfo("spj程序不存在 请上传spj.cpp文件到数据目录下");
                    return -1;
                }
            }
            int ret = -1;
            res.setTime("0");
            res.setMemory("0");
            for(i = 0; i < pathFiles.length; ++i) {
                file = pathFiles[i];
                if(file.getName().endsWith(".in")) {
                    File var11 = new File(fixPath(pathFile.getAbsolutePath()) + file.getName().substring(0, file.getName().length() - ".in".length()) + ".out");
                    if(var11.isFile()) {
                        ret = spjRun(file.getAbsolutePath(),var11.getAbsolutePath(),s,res);
                        if(ret!=0){
                            break;
                        }
                    }else{
                        res.setR(Result.ERROR);
                        res.setCEInfo("数据文件不匹配。"+file.getName()+"没有对应的.out文件");
                        return -1;
                    }
                }
                if(file.getName().endsWith(".out")) {
                    File var11 = new File(fixPath(pathFile.getAbsolutePath()) + file.getName().substring(0, file.getName().length() - ".out".length()) + ".in");
                    if(!var11.isFile()) {
                        res.setR(Result.ERROR);
                        res.setCEInfo("数据文件不匹配。"+file.getName()+"没有对应的.in文件");
                        return -1;
                    }
                }
            }
            res.setTime(res.getTime()+"MS");
            res.setMemory(res.getMemory()+"KB");
            return ret;
        }
    }
    private static int run(VjSubmitter s, RES res) throws IOException {
        Process runExe = Runtime.getRuntime().exec(runshell);
        OutputStream runExeOutputStream = runExe.getOutputStream();
        File pathFile = new File(path+"\\"+s.getSubmitInfo().pid+"\\");
        if(!pathFile.isDirectory()) {
            System.err.println(path + "not exists");
            return -1;
        } else {
            ArrayList<String> inFileList = new ArrayList<String>();
            ArrayList<String> outFileList = new ArrayList<String>();
            File[] pathFiles = pathFile.listFiles();
            int i;
            File file;
            for(i = 0; i < pathFiles.length; ++i) {
                file = pathFiles[i];
                if(file.getName().endsWith(".in")) {
                    File var11 = new File(fixPath(pathFile.getAbsolutePath()) + file.getName().substring(0, file.getName().length() - ".in".length()) + ".out");
                    if(var11.isFile()) {
                        inFileList.add(file.getAbsolutePath());
                        outFileList.add(var11.getAbsolutePath());
                    }
                }
            }
            int FileListSize = inFileList.size();
            Pair<Integer,Integer> limit=Main.problems.getProblemLimit(Integer.parseInt(s.getSubmitInfo().pid));
            runExeOutputStream.write((limit.getKey()+"\n").getBytes());//时限(MS)
        //Tool.debug("时限"+limit.getKey()+"MS");
            runExeOutputStream.write(("30000\n").getBytes());//单点时限
        //Tool.debug(("30000"));
            file = new File(fixPath(outPath) + s.getSubmitInfo().getRid());
            try {
                String filename = "Main";
                long exMemory = 0L;
                runExeOutputStream.write(((limit.getValue()*1024L +  exMemory) * 1024L + "\n").getBytes());//内存限制MB
//            Tool.debug("内存限制"+(limit.getValue()*1024L +  exMemory) * 1024L + "B");
                String mainExe = filename+".exe";
                runExeOutputStream.write((file.getAbsolutePath()+"\\"+mainExe + "\n").getBytes());
//            Tool.debug((file.getAbsolutePath() + "\\" + mainExe));
                runExeOutputStream.write((file.getAbsolutePath() + "\\\n").getBytes());
//            Tool.debug(file.getAbsolutePath() + "\\");
                runExeOutputStream.write((FileListSize + "\n").getBytes());
//            Tool.debug(FileListSize + "");
                long TimeUsed = 0L;
                long MemoryUsed = 0L;
                try {
                    if(FileListSize==0){
                        return -1;//没有文件 输出-1
                    }
                    for(i = 0; i < FileListSize; ++i) {
                        runExeOutputStream.write(( inFileList.get(i) + "\n").getBytes());
                    Tool.debug(inFileList.get(i));
                        File var20 = new File(outFileList.get(i));
                        runExeOutputStream.write((fixPath(file.getAbsolutePath()) + var20.getName() + "\n").getBytes());
                        runExeOutputStream.write((var20.getAbsolutePath() + "\n").getBytes());
                    }
                } catch (IOException ignored) {}
                finally {
                    runExeOutputStream.flush();
                }
                String isOut = null;
                BufferedReader is = new BufferedReader(new InputStreamReader(runExe.getInputStream()));
                BufferedReader es = new BufferedReader(new InputStreamReader(runExe.getErrorStream()));
                String esOut = "";
                try {
                    isOut = is.readLine();
                    TimeUsed = Long.parseLong(isOut);
                    isOut = is.readLine();
                    MemoryUsed = Long.parseLong(isOut);
                    while((isOut = es.readLine())!= null){
                        esOut = esOut + isOut;
                    }
                } catch (Exception ignored) {}
                MemoryUsed -=  exMemory;
                runExe.waitFor();
                try {
                    es.close();
                    is.close();
                } catch (IOException ignored) {}

                int ret = runExe.exitValue();
                if(ret != 0 && esOut.contains("Exception")) {
                    ret = 5;
                }
//                Tool.debug("judge return:"+ret +" esOut:"+esOut);
                res.setTime(TimeUsed+"MS");
                res.setMemory(MemoryUsed+"KB");
                return ret;
            } catch (Exception e) {
                e.printStackTrace(System.err);
                return -1;
            }
        }
    }
    public static RES judge(VjSubmitter s){
        RES res=new RES();
        if(s.getSubmitInfo().language==2){//JAVA 不支持
            res.setR(Result.ERROR);
            res.setCEInfo("本oj暂不支持JAVA");
            return res;
        }
        int ret=-1;
        s.showstatus="Compile";
        delFile(outPath + s.getSubmitInfo().rid+"\\");
        if(compile(s,res)){
            s.showstatus="CompileSuccess";
            try {
                s.showstatus="Running";
                if(Main.problems.getProblem(Integer.parseInt(s.getSubmitInfo().pid)).isSpj()){

//                    Tool.debug("LocalJudge SPJ");
                    ret=spjRun(s, res);
                }else{
//                    Tool.debug("LocalJudge");
                    ret=run(s,res);
                }

                s.showstatus="runDone";
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Tool.debug("LocalJudge ret="+ret);
            switch (ret){
                case 0: res.setR(Result.AC); break;
                case 1: res.setR(Result.PE); break;
                case 2: res.setR(Result.TLE);break;
                case 3: res.setR(Result.MLE);break;
                case 4: res.setR(Result.WA); break;
                case 5: res.setR(Result.RE); break;
                case 6: res.setR(Result.OLE);break;
                case 7: res.setR(Result.CE); break;
                default: res.setR(Result.ERROR);break;
            }
            if(Main.config.topConfig.delRun) delFile(outPath + s.getSubmitInfo().rid+"\\");
        }
        s.showstatus="res="+res.getR();
        return res;
    }
}
