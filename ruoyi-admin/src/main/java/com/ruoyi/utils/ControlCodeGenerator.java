package com.ruoyi.utils;



import com.ruoyi.common.utils.uuid.UUID;

import java.util.Random;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.utils
 * @Project：ruoyi-vue-service
 * @name：ControlCodeGenerator
 * @Date：2024/4/19 16:59
 * @Filename：ControlCodeGenerator
 */
public class ControlCodeGenerator {


    public static String genControlCode() {
        // 产生随机布控编号
        String prefix = "c";
        UUID uuid1 = UUID.randomUUID();
        UUID uuid5 = UUID.nameUUIDFromBytes(uuid1.toString().getBytes());

        // 从uuid5中获取前部分
        String[] parts = uuid5.toString().split("-");
        String a = parts[0];

        // 生成随机数
        Random random = new Random();
        int randomNumber = 10000 + random.nextInt(90000); // 生成10000到99999之间的随机数

        // 组合最终的控制码
        String code = prefix + a + randomNumber;

        return code;
    }
}
