package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 文件操作，都在rootPath目录下操作
 * Created by Syiml on 2015/10/21 0021.
 */
public class FILE {
    private static String rootPath= Main.config.localJudgeWorkPath;

    /**
     * 为题目创建一个保存数据的目录
     * @param pid 题目本地id
     * @return true成功、false失败
     */
    public static boolean createDirectory(int pid){
        File f=new File(rootPath+"data/"+pid);
        return (f.exists() && f.isDirectory()) || f.mkdirs();
    }

    /**
     * 获取指定题目下测试数据路径下的文件列表
     * @param pid 题目本地id
     * @return 文件列表的文件名
     */
    public static List<File> getFiles(int pid){
        File f=new File(rootPath+"data/"+pid);
        List<File> files=new ArrayList<File>();
        if(f.exists()&&f.isDirectory()){
            File[] pathFiles = f.listFiles();
            if (pathFiles != null) {
                Collections.addAll(files, pathFiles);
            }
        }
        return files;
    }

    /**
     * 删除题目测试数据目录下的文件
     * @param pid 题目本地id
     * @param filename 文件名
     * @return true成功、false失败
     */
    public static boolean delFile(int pid,String filename){
        File file = new File(rootPath+"data/"+pid+"/"+filename);
        if(file.isFile()) {
            if(!file.delete()) {
                Tool.log("Can not delete file:" + file.getAbsolutePath());
                return false;
            }else{
                Main.svnDelFileCommit(pid+"/"+filename);
            }
            return true;
        }
        return false;
    }

    /**
     * 上传数据文件，覆盖原文件
     * @param pid 题目本地id
     * @param filename 文件名
     * @param f 文件
     * @return true成功、false失败
     */
    public static boolean uploadFile(int pid,String filename,File f){
        try {
            if(f.length()>Main.config.maxSampleFileSize){
                return false;
            }
            Main.uploadFile(f,rootPath+"data/"+pid+"/"+filename);
            //svn commit
            Main.svnAddFileCommit();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static InputStream download(int pid,String filename){
        try {
            return new FileInputStream(rootPath+"data/"+pid+"/"+filename);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Tool.debug(rootPath+"data/"+pid+"/"+filename+"文件未找到");
        }
        return null;
    }
}
