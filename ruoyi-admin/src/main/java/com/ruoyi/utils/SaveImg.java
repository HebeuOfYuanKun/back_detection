package com.ruoyi.utils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.*;

public class SaveImg {
    public static void saveByteImg(Mat src, String filePath) {
        bytesToFile(matToByte(src), filePath);
    }

    /**
     * mat转byte[]
     * @param mat 图片对象
     * @return
     */
    public static byte[] matToByte(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        return byteArray;
    }

    /**
     * byte to file
     * @param buffer
     * @param filePath
     */
    public static void bytesToFile(byte[] buffer, final String filePath){

        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {
            parentDir.mkdirs();
            /*if (parentDir.mkdirs()) {
                System.out.println("文件夹已创建");
            } else {
                System.out.println("无法创建文件夹");
                return;
            }*/
        }
        OutputStream output = null;
        BufferedOutputStream bufferedOutput = null;

        try {

            output = new FileOutputStream(file);

            bufferedOutput = new BufferedOutputStream(output);

            bufferedOutput.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(null!=bufferedOutput){
                try {
                    bufferedOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(null != output){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
