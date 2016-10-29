package action.Mall;

import action.BaseAction;
import servise.MallMain;
import util.Main;
import util.Tool;

import java.io.File;

/**
 * Created by QAQ on 2016/10/2.
 */
public class ActionAddGoods extends BaseAction {

    private static long maxHeadImgSize= Main.GV.getLong("maxHeadImgSize");

    private int id;
    private String title;
    private int acb;
    private int stock;//库存
    private String des;//描述
    private String isHidden;
    private int buyLimit = -1;
    private int buyVerifyLimit = 1;

    private File upload;
    private String uploadFileName;


    public String addGoods() throws Exception{
//        Tool.debug(id+","+title+","+acb+","+stock+","+des+",isHidden="+isHidden+","+ upload +","+ uploadFileName+","+buyVerifyLimit);
        //Tool.debug("上传封面文件大小：" + upload.length());
        if(upload !=null && upload.length()>=maxHeadImgSize){
            return ERROR;
        }
        if(id==-1) {
            id = MallMain.addGoods(this);
        }else{
            MallMain.editGoods(this);
        }
        if(id == -1){
            return ERROR;
        }
        if(upload !=null){
            String path=Main.getRealPath("/");
            return Main.uploadFile(upload,path+"\\pic\\goods\\"+id+".jpg");
        }else{
            return SUCCESS;
        }
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsHidden() {

        return isHidden;
    }

    public void setIsHidden(String isHidden) {
        this.isHidden = isHidden;
    }

    public int getAcb() {
        return acb;
    }

    public void setAcb(int acb) {
        this.acb = acb;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(int buyLimit) {
        this.buyLimit = buyLimit;
    }

    public String getDes() {
        if(des==null) return "";
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public int getBuyVerifyLimit() {
        return buyVerifyLimit;
    }

    public void setBuyVerifyLimit(int buyVerifyLimit) {
        this.buyVerifyLimit = buyVerifyLimit;
    }
}
