package com.dwbi.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @hundune~
 * @version1.0
 */
public class TextSplitUtils {

    public static ArrayList<String> readerFile(MultipartFile file) {

        ArrayList<String> list= new ArrayList<>();
        try {
            // 获取文件内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                // 处理文件内容，例如输出到控制台
                // todo 可以使用 redis 来进行优化
                if (builder.length()+line.length()<990){
                    builder.append(line);
                }else {
                    //保存数据库
                    list.add(builder.toString());
                    builder.delete(0,builder.length());
                }
                if (builder.length()==0){
                    builder.append(line);
                }
            }
            //数据可能不超过990
            list.add(builder.toString());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
