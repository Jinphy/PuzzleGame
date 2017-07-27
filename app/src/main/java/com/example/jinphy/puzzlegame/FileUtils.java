package com.example.jinphy.puzzlegame;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jinphy on 2017/7/16.
 */

public class FileUtils {

    public static final String FILE_TYPE_PICTURE = "IMAGE";
    public static final String FILE_TYPE_MUSIC = "AUDIO";
    public static final String FILE_TYPE_VIDEO = "VIDEO";

    /**
     * 判断文件是否有效
     * @param file 要检测的文件
     * @return 如果文件存在，则返回true，否则返回false
     * */
    public static boolean isAvailable(String file) {
        if (TextUtils.isEmpty(file)) {
            return false;
        }
        File f = new File(file);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    /**
     * 根据文件路径（包括目录和文件名）创建新的文件，如果文件存在
     * 则删除旧的文件，并创建新的空文件
     * @param file 要创建的文件，包括文件路径和文件名
     * @return 如果创建成功则返回创建的文件，否则返回null
     * */
    public static File createNewFile(String file) {
        if (TextUtils.isEmpty(file)) {
            return null;
        }
        File out = new File(file);
        out.getParentFile().mkdirs();
        if (out.exists()) {
            out.delete();
        }
        try {
            out.createNewFile();
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据文件路径（包括目录和文件名）创建新的文件，如果文件存在
     * 则删除旧的文件，并创建新的空文件
     * @param filePath 要创建的文件路径
     * @param fileName 要创建的文件名
     * @return 如果创建成功则返回创建的文件，否则返回null
     * @see FileUtils#createNewFile(String)
     * */
    public static File createNewFile(@NonNull String filePath, @NonNull String fileName) {
        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }
        String file = filePath+fileName;

        return createNewFile(file);
    }

    /**
     * 根据传递的文件路径、文件名前缀和文件名后缀创建一个新的空文件
     * 如果要创建的文件已经存在，则在前缀名后面加上“(1)”，再继续创建，
     * 如果仍然存在则继续以上步骤，括号的数字一次增加，直到创建不重复的文件
     * @param path 文件路径
     * @param prefix 文件名前缀
     * @param suffix 文件名后缀
     * @return 创建成功则返回创建后的新文件，否则返回null
     *
     * */
    public static File createFileNoRepeatName(
            @NonNull  String path,
            @NonNull String prefix,
            @NonNull String suffix){
        // 对文件名进行预处理
        if (prefix.endsWith(".")) {
            // 如果前缀以“.”结尾，则去掉“.”
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        if (!suffix.startsWith(".")) {
            // 如果后缀没有以“.”开始，则开头加上“.”
            suffix = "." + suffix;
        }

        // 创建文件所在目录 这里用mkdirs() 而不用mkdir()，因为前者可以创建多级目录，
        // 而后者只能创建当前的目录
        new File(path).mkdirs();

        // 要返回的文件
        File out;
        // 文件创建过程中的重名次数
        int times = 1;

        // 创建文件
        out = new File(path, prefix + suffix);
        // 循环创建文件，直到创建一个没有重名的文件
        while (out.exists()) {
            out = new File(path, prefix + "(" + (times++) + ")" + suffix);
        }

        try {
            out.createNewFile();
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File createMediaFile(@NonNull String path,@NonNull String type) {
        if (null==checkFileType(type)){
            return null;
        }
        long time = System.currentTimeMillis();
        return null;

    }

    public static String getFileNameTimeStamp() {

        StringBuilder timeStamp = new StringBuilder();
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        timeStamp.append(now.get(Calendar.YEAR))
                .append( now.get(Calendar.MONTH))
                .append(now.get(Calendar.DAY_OF_MONTH))
                .append(now.get(Calendar.HOUR_OF_DAY))
                .append(now.get(Calendar.MINUTE))
                .append(now.get(Calendar.SECOND))
                .append(now.get(Calendar.MILLISECOND));
        return timeStamp.toString();
    }

    private static String checkFileType(String type) {
        switch (type) {
            case FILE_TYPE_MUSIC:
                return FILE_TYPE_MUSIC;
            case FILE_TYPE_PICTURE:
                return FILE_TYPE_PICTURE;
            case FILE_TYPE_VIDEO:
                return FILE_TYPE_VIDEO;
            default:
                return null;
        }
    }

}
