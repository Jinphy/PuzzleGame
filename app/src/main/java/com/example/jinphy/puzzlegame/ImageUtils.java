package com.example.jinphy.puzzlegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jinphy on 2017/7/16.
 */

public class ImageUtils {


    /*------------------------压缩bitmap-------------------------------------------------*/
    /**
     * 该函数的功能是将一个大的bitmap压缩成指定大小的图片
     * 并返回压缩后的bitmap
     * @param imageFile 待压缩的图片的文件路劲
     * @param reqWidth 要求图片压缩后的宽度
     * @param reqHeight 要求图片压缩后的高度
     * */
    public static Bitmap getSmallBitmap(String imageFile,int reqWidth,int reqHeight) {
        // 检测文件路径是否有效，即检测文件是否存在
        if (!FileUtils.isAvailable(imageFile)) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 未true表示只解码图片的边界
        BitmapFactory.decodeFile(imageFile, options);

        // 计算压缩率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //设置解码整张图片
        options.inJustDecodeBounds = false;
        // 按照设置的压缩率解码图片，并生成bitmap
        return BitmapFactory.decodeFile(imageFile,options);
    }

    /**
     *  该函数的功能是将一个大的bitmap压缩成指定大小的图片
     *  并返回压缩后的bitmap
     *  @param resources resources 对象，你可以从context.getResources()获取
     *  @param resourceId 图片资源文件的ID
     *  @param reqWidth 压缩后的目标宽度
     *  @param reqHeight 压缩后的目标高度
     *  @return 压缩后的bitmap
     * */
    public static Bitmap getSmallBitmap(Resources resources, int resourceId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resourceId, options);

        // 计算压缩率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //设置解码整张图片
        options.inJustDecodeBounds = false;
        // 按照设置的压缩率解码图片，并生成bitmap
        return BitmapFactory.decodeResource(resources, resourceId, options);

    }
    /**
     *  该函数的功能是将一个大的bitmap压缩成指定大小的图片
     *  并返回压缩后的bitmap
     *  @param bytes 存放图片的字节数组
     *  @param reqWidth 压缩后的目标宽度
     *  @param reqHeight 压缩后的目标高度
     *  @return 压缩后的bitmap
     * */

    public static Bitmap getSmallBitmap(byte[] bytes,int reqWidth,int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);


        // 计算压缩率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //设置解码整张图片
        options.inJustDecodeBounds = false;
        // 按照设置的压缩率解码图片，并生成bitmap
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

    }
    /**
     *  该函数的功能是将一个大的bitmap压缩成指定大小的图片
     *  并返回压缩后的bitmap
     *  @param in 指向图片的输入流
     *  @param reqWidth 压缩后的目标宽度
     *  @param reqHeight 压缩后的目标高度
     *  @return 压缩后的bitmap
     * */

    public static Bitmap getSmallBitmap(InputStream in, int reqWidth, int reqHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);


        // 计算压缩率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        //设置解码整张图片
        options.inJustDecodeBounds = false;
        // 按照设置的压缩率解码图片，并生成bitmap
        return BitmapFactory.decodeStream(in, null, options);
    }


    /*-------------------------------------------------------------------------*/
    public static boolean bitmapToFile(Bitmap bitmap, String filePath,int quality) {
        // 字节数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 将bitmap压缩到字节数组输出流中
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(filePath);
            // 从字节数组输出流中获取图片的字节数组，并写入到文件输出流中
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
            return true;// 写入成功
        } catch (IOException e) {
            e.printStackTrace();
            return false;// 写入失败
        }

    }

    // 计算压缩率
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int width = options.outWidth;// 图片的原始宽度
        int height = options.outHeight;// 图片的原始高度
        int inSampleSize = 1;// 压缩率

//        if (width > reqWidth || height > reqHeight) {
//            int ratioW = Math.round((float) width / reqWidth);
//            int ratioH = Math.round((float) height / reqHeight);
//            inSampleSize = Math.max(ratioW, ratioH);
//        }
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}







