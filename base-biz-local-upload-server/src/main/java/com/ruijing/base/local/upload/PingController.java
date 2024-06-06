package com.ruijing.base.local.upload;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruijing.fundamental.remoting.msharp.annotation.GateWayController;
import com.ruijing.fundamental.remoting.msharp.annotation.Ping;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.aop.scope.ScopedProxyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ping检测类
 *
 * @author 锐竞-模板生成工具
 **/
@GateWayController(requestMapping = "/")
public class PingController {
    
    @Ping
    public String ping() {
        return "pong";
    }
    
    private static final String FE_TABLE = "fe_log";
    private static final String PAGE_TABLE = "belong_page_view_log_day";
    private static final String EVENT_TABLE = "belong_event_view_log_day";
    private static final String PAGE_FILED = "belongPage";
    private static final String URL_FILED = "url";
    
    public static void main(String[] args) {
        String oldVersion = "";
        String newVersion = "1";
        // 前端域名替换上线时间
        List<Integer> belongSite = Lists.newArrayList(100221, 102547, 100019, 100224, 100225);
        String onlineTime = "2024-06-06 11:11:11";
        
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(Pair.of("m.test.rj-info.com/BIO/#/", "mbio.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/LAB/#/", "mscholar.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/EBC/#/", "mebc.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/SRS/#/", "msrs.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/AI/#/", "mai.test.rj-info.com/"));
        
        System.out.println("\n\n\n");
        System.out.println("确认基础表已经没有旧链接数据进来！！！");
        System.out.println();
        
        // clickhouse 基础表url处理
        String updateFeLogUrlSql = "ALTER TABLE base." + FE_TABLE + " UPDATE " + URL_FILED + " = " + getUpdateFeLogSql(URL_FILED, list, belongSite);
        System.out.println(updateFeLogUrlSql);
        System.out.println("\n\n\n");
        
        System.out.println("查看任务进度");
        
        // clickhouse 基础表page处理
        String updateFeLogPageSql = "ALTER TABLE base." + FE_TABLE + " UPDATE " + PAGE_FILED + " = " + getUpdateFeLogSql(PAGE_FILED, list, belongSite);
        System.out.println(updateFeLogPageSql);
        System.out.println("\n\n\n");
        
        // 创建新页面聚合表
        System.out.println(pageCreat(newVersion));
        // 事件聚合表
        System.out.println(eventCreat(newVersion));
        
        // 初始化两个表基础表旧数据(从创建视图时间之前)
        
        // 丢失前的聚合数据处理(从丢失数据时间之前)
        
        System.out.println("\n\n\n");
        System.out.println("处理推广服务和统计服务链接\n");
        System.out.println("https://gateway.test.rj-info.com/base/popularize/back/replacePopularizeUrl");
        System.out.println("https://gateway.test.rj-info.com/base/statistics/back/replaceUrl");
        System.out.println(getJsonStrings(list));
        
        System.out.println("验收没问题后，删除旧页面、事件表与视图");
        System.out.println(deleteOld(oldVersion));
    }
    
    
    private static String getUpdateFeLogSql(String filed, List<Pair<String, String>> list, List<Integer> belongSite) {
        StringBuilder sql = new StringBuilder();
        sql.append("CASE\n");
        
        for (Pair<String, String> pair : list) {
            sql.append("    WHEN ")
                    .append(filed)
                    .append(" LIKE '")
                    .append(pair.getLeft())
                    .append("%' THEN replaceOne(")
                    .append(filed)
                    .append(", '")
                    .append(pair.getLeft())
                    .append("', '")
                    .append(pair.getRight())
                    .append("')\n");
        }
        
        sql.append("    ELSE ").append(filed).append("\n");
        sql.append("END\n");
        sql.append("WHERE \n");
        sql.append("  belongSite IN (");
        
        for (int i = 0; i < belongSite.size(); i++) {
            sql.append(belongSite.get(i));
            if (i < belongSite.size() - 1) {
                sql.append(", ");
            }
        }
        
        sql.append(") \n");
        sql.append("  AND (");
        
        for (int i = 0; i < list.size(); i++) {
            sql.append(filed)
                    .append(" LIKE '")
                    .append(list.get(i).getLeft())
                    .append("%'");
            if (i < list.size() - 1) {
                sql.append("\n   OR ");
            }
        }
        
        sql.append(");");
        return sql.toString();
    }
    
    
    private static String getJsonStrings(List<Pair<String, String>> list) {
        Gson gson = new Gson();
        StringBuilder jsonStrings = new StringBuilder();
        
        for (Pair<String, String> pair : list) {
            JsonObject json = new JsonObject();
            json.addProperty("fromUrl", pair.getLeft());
            json.addProperty("toUrl", pair.getRight());
            json.addProperty("update", true);
            
            String jsonString = gson.toJson(json);
            
            jsonStrings.append(jsonString).append("\n");
        }
        
        return jsonStrings.toString();
    }
    
    
    private static String pageCreat(String version) {
        
        return "CREATE TABLE base." + PAGE_TABLE + version + "\n" +
                "(\n" +
                "\n" +
                "    `belong_site` Int32 DEFAULT 0 COMMENT '站点Id',\n" +
                "\n" +
                "    `org_id` Int32 DEFAULT 0 COMMENT '单位id',\n" +
                "\n" +
                "    `belong_page` String DEFAULT '' COMMENT '页面',\n" +
                "\n" +
                "    `pv` UInt64 DEFAULT 0 COMMENT 'pv',\n" +
                "\n" +
                "    `uv` AggregateFunction(uniq,\n" +
                " String) COMMENT 'uv',\n" +
                "\n" +
                "    `create_time` Date DEFAULT now() COMMENT '访问时间'\n" +
                ")\n" +
                "ENGINE = SummingMergeTree((pv,\n" +
                " uv))\n" +
                "PARTITION BY toYYYYMM(create_time)\n" +
                "ORDER BY (belong_site,\n" +
                " org_id,\n" +
                " belong_page,\n" +
                " create_time)\n" +
                "SETTINGS index_granularity = 8192;";
    }
    
    
    private static String eventCreat(String version) {
        return "CREATE TABLE base." + EVENT_TABLE + version + "\n" +
                "(\n" +
                "\n" +
                "    `belong_site` Int32 DEFAULT 0 COMMENT '站点Id',\n" +
                "\n" +
                "    `event_id` Int32 DEFAULT 0 COMMENT '事件id',\n" +
                "\n" +
                "    `event_tag` String DEFAULT '' COMMENT '事件标签',\n" +
                "\n" +
                "    `event_category` String DEFAULT '' COMMENT '事件分类',\n" +
                "\n" +
                "    `org_id` Int32 DEFAULT 0 COMMENT '单位id',\n" +
                "\n" +
                "    `belong_page` String DEFAULT '' COMMENT 'belongPage',\n" +
                "\n" +
                "    `url` String DEFAULT '' COMMENT '详细访问地址',\n" +
                "\n" +
                "    `pv` UInt64 DEFAULT 0 COMMENT 'pv',\n" +
                "\n" +
                "    `uv` AggregateFunction(uniq,\n" +
                " String) COMMENT 'uv',\n" +
                "\n" +
                "    `create_time` Date DEFAULT now() COMMENT '访问时间'\n" +
                ")\n" +
                "ENGINE = SummingMergeTree((pv,\n" +
                " uv))\n" +
                "PARTITION BY toYYYYMM(create_time)\n" +
                "ORDER BY (belong_site,\n" +
                " event_id,\n" +
                " event_tag,\n" +
                " event_category,\n" +
                " org_id,\n" +
                " belong_page,\n" +
                " url,\n" +
                " create_time)\n" +
                "SETTINGS index_granularity = 8192;";
    }
    
    
    private static String deleteOld(String oldVersion) {
        String dropPage = "DROP TABLE IF EXISTS base." + PAGE_TABLE + oldVersion + ";";
        String dropPageView = "DROP VIEW IF EXISTS base.base_" + PAGE_TABLE + "_view" + oldVersion + ";";
        String dropEvent = "DROP TABLE IF EXISTS base." + EVENT_TABLE + oldVersion + ";";
        String dropEventView = "DROP VIEW IF EXISTS base.base_" + EVENT_TABLE + "_view" + oldVersion + ";";
        String stringBuilder = dropPage + "\n" +
                dropPageView + "\n" +
                dropEvent + "\n" +
                dropEventView + "\n";
        return stringBuilder;
        
    }
    
}