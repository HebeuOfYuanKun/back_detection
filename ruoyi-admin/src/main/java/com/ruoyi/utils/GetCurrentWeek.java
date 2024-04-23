package com.ruoyi.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.utils
 * @Project：ruoyi-vue-service
 * @name：GetCurrentWeek
 * @Date：2024/4/23 18:06
 * @Filename：GetCurrentWeek
 */
public class GetCurrentWeek {
    public static List<String> getPastWeekdays(String currentWeekday) {
        List<String> pastWeekdays = new ArrayList<>();

        String[] weekdays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        int currentIndex = -1;

        for (int i = 0; i < weekdays.length; i++) {
            if (weekdays[i].equalsIgnoreCase(currentWeekday)) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex != -1) {
            for (int i = 0; i < 7; i++) {
                pastWeekdays.add(weekdays[currentIndex]);
                currentIndex = (currentIndex + 1 ) % weekdays.length;
            }
        }

        return pastWeekdays;
    }
}
