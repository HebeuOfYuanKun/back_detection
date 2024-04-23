package com.ruoyi.business.aidetection.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.business.aidetection.domain.AvAlarm;
import com.ruoyi.business.aidetection.domain.WSMessage;
import com.ruoyi.business.aidetection.service.AvAlarmService;
import com.ruoyi.framework.web.domain.Server;
import com.ruoyi.framework.websocket.WebSocketUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoyi.utils.GetCurrentWeek.getPastWeekdays;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.quartz.task
 * @Project：ruoyi-vue-service
 * @name：GetEchartsData
 * @Date：2024/4/23 11:34
 * @Filename：GetEchartsData
 */
@Component("GetEchartsData")
public class GetEchartsData {
    @Autowired
    private AvAlarmService avAlarmService;
    public void getPanelEchartsData() throws Exception{
        Map<String,Object> map = new HashMap<>();
        WSMessage wsMessage=new WSMessage();
        //从数据库获取预警信息
        QueryWrapper<AvAlarm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);//已处理
        int count1 = avAlarmService.count(queryWrapper);
        map.put("count1",count1);

        queryWrapper.clear();
        queryWrapper.eq("state",2);//未处理
        int count2 = avAlarmService.count(queryWrapper);
        map.put("count2",count2);

        queryWrapper.clear();
        queryWrapper.eq("state",3);//已忽略
        int count3 = avAlarmService.count(queryWrapper);
        map.put("count3",count3);
        //System.out.println();
        Server server = new Server();

        server.setJvmInfo();

        map.put("normaltime",server.getJvm().getRunTime());
        WebSocketUsers.sendMessageToUsersByText(JSONUtil.toJsonStr(new WSMessage("getData",
                "getPanelEchartsData","Panel",JSONUtil.toJsonStr(map))));
        return ;
    }
    public void getLineEchartsData() throws Exception{
        Map<String,Object> map = new HashMap<>();
        WSMessage wsMessage=new WSMessage();
        //获取当前时间
        String now = DateUtil.now();
        Date date = DateUtil.parse(now,"yyyy-MM-dd");
        DateTime newDate = DateUtil.offsetDay(date, -6);
        map.put("CurrentWeek",getPastWeekdays(newDate.dayOfWeekEnum().toChinese()));

        //获取最近一周的预警信息
        QueryWrapper<AvAlarm> queryWrapper = new QueryWrapper<>();
        //保存三个状态数据数组
        int grade1[] = new int[7];
        int grade2[] = new int[7];
        int grade3[] = new int[7];
        for (int i = 0; i < 7; i++) {
            DateTime temDate = DateUtil.offsetDay(date, -6+i);
            DateTime tem1Date = DateUtil.offsetDay(date, -6+i+1);
            queryWrapper.ge("create_time",temDate.toString());//最近一周
            queryWrapper.le("create_time",tem1Date.toString());//最近一周
            queryWrapper.eq("grade",1);//已处理
            grade1[i] = avAlarmService.count(queryWrapper);
            queryWrapper.clear();

            queryWrapper.ge("create_time",temDate.toString());//最近一周
            queryWrapper.le("create_time",tem1Date.toString());//最近一周
            queryWrapper.eq("grade",2);//未处理
            grade2[i] = avAlarmService.count(queryWrapper);
            queryWrapper.clear();

            queryWrapper.ge("create_time",temDate.toString());//最近一周
            queryWrapper.le("create_time",tem1Date.toString());//最近一周
            queryWrapper.eq("grade",3);//已忽略
            grade3[i] = avAlarmService.count(queryWrapper);
            queryWrapper.clear();
        }
        map.put("grade1",grade1);
        map.put("grade2",grade2);
        map.put("grade3",grade3);
        //map.put("count1",count1);

        WebSocketUsers.sendMessageToUsersByText(JSONUtil.toJsonStr(new WSMessage("getData",
                "getLineEchartsData","Line",JSONUtil.toJsonStr(map))));
        return ;
    }
}
