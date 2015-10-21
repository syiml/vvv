package action;

import Main.Main;
import Tool.FILE;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Syiml on 2015/10/21 0021.
 */
public class sampleFile {

    public String getPid() {
        return pid;
    }
    public String getFilename() {
        return filename;
    }
    public File getSamplein() {
        return samplein;
    }
    public String getSampleinFileName() {
        return sampleinFileName;
    }
    public File getSampleout() {
        return sampleout;
    }
    public String getSampleoutFileName() {
        return sampleoutFileName;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public void setSamplein(File samplein) {
        this.samplein = samplein;
    }
    public void setSampleinFileName(String sampleinFileName) {
        this.sampleinFileName = sampleinFileName;
    }
    public void setSampleout(File sampleout) {
        this.sampleout = sampleout;
    }
    public void setSampleoutFileName(String sampleoutFileName) {
        this.sampleoutFileName = sampleoutFileName;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    String pid;
    String filename;
    File samplein;
    String sampleinFileName;
    File sampleout;
    String sampleoutFileName;
    private String fileName;

    public String del(){
        if(Main.loginUserPermission().getAddLocalProblem()){
            if(FILE.delFile(Integer.parseInt(pid),filename)){
                return "success";
            }else  return "error";
        }else{
            return "error";
        }
    }
    public String upload(){
        if(Main.loginUserPermission().getAddLocalProblem()){
            FILE.uploadFile(Integer.parseInt(pid),sampleinFileName,samplein);
            FILE.uploadFile(Integer.parseInt(pid),sampleoutFileName,sampleout);
            return "success";
        }else{
            return "error";
        }
    }
    public InputStream getDownloadFile(){
        //解解乱码
        //filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
        fileName=filename;
        return FILE.download(Integer.parseInt(pid),filename);
    }
    public String download() throws Exception {
        if(Main.loginUserPermission().getAddLocalProblem())
            return "success";
        else return "error";
    }
}
