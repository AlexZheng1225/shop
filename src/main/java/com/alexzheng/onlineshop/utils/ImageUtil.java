package com.alexzheng.onlineshop.utils;

import com.alexzheng.onlineshop.dto.ImageFileHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author Alex Zheng
 * @Date created in 14:00 2020/4/7
 * @Annotation
 */
public class ImageUtil {

//    获取classpath绝对路径
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//    格式化时间
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final Random r = new Random();

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    /**
     * 处理详情图
     * @param fileHolder
     * @param addr
     * @return
     */
    public static String generateNormalImg(ImageFileHolder fileHolder,String addr){
        String realFileName = getRandomFileName();
        //获取文件拓展名
        String extension = getFileExtension(fileHolder);
        //生成存放对应店铺的文件夹路径
        makeDirPath(addr);
        //生成图片的相对路径(下面两行)
        String relativeAddr = addr+realFileName+extension; //同时也是返回值
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(fileHolder.getImageFileInputStream()).size(337,640).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath+"watermark.png")),1f).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error("------generateNormalImg Error-------： "+e.getMessage());
            e.printStackTrace();
        }
//        logger.info("-------dest------:"+dest);
        return relativeAddr; //返回图片的相对路径 用于存储到数据库中
    }


    /**
     * 判断传入的path是文件路径还是目录路径
     * 文件路径则删除该文件
     * 目录路径则删除该目录下的所有文件
     * @param path
     */
    public static void deleteFileOrPath(String path){
        File fileOrPath = new File(PathUtil.getImgBasePath()+path);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File[] files = fileOrPath.listFiles();
                for(int i=0;i<fileOrPath.length();i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     * @param imageFileHolder
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageFileHolder imageFileHolder, String targetAddr){
        String realFileName = getRandomFileName();
        //获取文件拓展名
        String extension = getFileExtension(imageFileHolder);
        //生成存放对应店铺的文件夹路径
        makeDirPath(targetAddr);
        //生成图片的相对路径(下面两行)
        String relativeAddr = targetAddr+realFileName+extension; //同时也是返回值
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
        try {
            Thumbnails.of(imageFileHolder.getImageFileInputStream()).size(800,800).watermark(Positions.BOTTOM_RIGHT,
                    ImageIO.read(new File(basePath+"watermark.png")),1f).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error("------generateThumbnail Error-------： "+e.getMessage());
            e.printStackTrace();
        }
//        logger.info("-------dest------:"+dest);
        return relativeAddr; //返回图片的相对路径 用于存储到数据库中
    }

//    创建目标路径所涉及到的目录，包括目录的子目录 都要自动创建出来
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

//    获取文件拓展名
    private static String getFileExtension(ImageFileHolder thumbnail) {
//        String originaFileName = thumbnail.getOriginalFilename();
        String originaFileName = thumbnail.getImageFileName();
        return originaFileName.substring(originaFileName.lastIndexOf("."));
    }

//    随机生成时间
    public static String getRandomFileName() {
        int rannum = r.nextInt(89999)+10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr+rannum;
    }

    /*public static void main(String[] args) throws IOException {
        //改变tianhe.jpg的大小 并打上水印
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Thumbnails.of(new File("E:\\all\\pictest\\tianhe.jpg")).size(720, 405)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.png")), 1f)
                .outputQuality(0.8f).toFile("E:\\all\\pictest\\tianhenew.jpg");
    }*/
}
