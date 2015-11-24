package entity.OJ.LocalJudge;

import util.Vjudge.Submitter;
import entity.RES;
import entity.Result;
import util.Main;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Syiml on 2015/10/14 0014.
 */
public class LocalJudge {
    private static String workPath = Main.GV.getString("localJudgeWorkPath");
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
        Main.debug(path);
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
                Main.log("Can not delete file:" + file.getAbsolutePath());
            }
        } else if(file.isDirectory()) {
            File[] var2 = file.listFiles();
            for(int var3 = 0; var3 < var2.length; ++var3) {
                deleteFile(var2[var3]);
            }
            if(!file.delete()) {
                Main.log("Can not delete file:" + file.getAbsolutePath());
            }
        }
    }
    private static void delFile(String path){
        File file = new File(path);
        deleteFile(file);
    }
    private static boolean compile(Submitter s,RES res){
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
            Main.debug(cmd);
                proOutStr.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                String errorInfo = "";
                String var15;
                long time = System.currentTimeMillis();
                while (time + 10000L > System.currentTimeMillis() && (var15 = br.readLine()) != null) {
                    errorInfo += var15 + "\n";
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
            }catch (Exception var29) {
                var29.printStackTrace(System.err);
                return false;
            }
        }
        return true;
    }
    public static String fixPath(String var0) {
        return var0.endsWith("\\")?var0:var0 + "\\";
    }
    private static int run( Submitter s, RES res) throws IOException {
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
        Main.debug("时限"+limit.getKey()+"MS");
            runExeOutputStream.write(("30000\n").getBytes());//单点时限
        Main.debug(("30000"));
            file = new File(fixPath(outPath) + s.getSubmitInfo().getRid());
            try {
                String filename = "Main";
                long exMemory = 0L;
                runExeOutputStream.write(((limit.getValue()*1024L +  exMemory) * 1024L + "\n").getBytes());//内存限制MB
            Main.debug("内存限制"+(limit.getValue()*1024L +  exMemory) * 1024L + "B");
                String mainExe = filename+".exe";
                runExeOutputStream.write((file.getAbsolutePath()+"\\"+mainExe + "\n").getBytes());
            Main.debug((file.getAbsolutePath() + "\\" + mainExe));
                runExeOutputStream.write((file.getAbsolutePath() + "\\\n").getBytes());
            Main.debug(file.getAbsolutePath() + "\\");
                runExeOutputStream.write((FileListSize + "\n").getBytes());
            Main.debug(FileListSize + "");
                long TimeUsed = 0L;
                long MemoryUsed = 0L;
                try {
                    for(i = 0; i < FileListSize; ++i) {
                        runExeOutputStream.write(( inFileList.get(i) + "\n").getBytes());
                    Main.debug(inFileList.get(i));
                        File var20 = new File(outFileList.get(i));
                        runExeOutputStream.write((fixPath(file.getAbsolutePath()) + var20.getName() + "\n").getBytes());
                        runExeOutputStream.write((var20.getAbsolutePath() + "\n").getBytes());
                    }
                    runExeOutputStream.flush();
                } catch (IOException ignored) {}
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
                res.setTime(TimeUsed+"MS");
                res.setMemory(MemoryUsed+"KB");
                return ret;
            } catch (Exception var35) {
                var35.printStackTrace(System.err);
                return -1;
            }
        }
    }
    public static RES judge(Submitter s){
        RES res=new RES();
        if(s.getSubmitInfo().language==2){//JAVA 不支持
            res.setR(Result.ERROR);
            return res;
        }
        int ret=-1;
        s.showstatus="Compile";
        if(compile(s,res)){
            s.showstatus="CompileSuccess";
            try {
                s.showstatus="Running";
                ret=run(s,res);
                s.showstatus="runDone";
            } catch (IOException e) {
                e.printStackTrace();
            }
            Main.debug("LocalJudge ret="+ret);
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
            delFile(outPath + s.getSubmitInfo().rid+"\\");
        }
        s.showstatus="res="+res.getR();
        return res;
    }
}
