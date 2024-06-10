package com.ruijing.base.local.upload;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruijing.fundamental.remoting.msharp.annotation.GateWayController;
import com.ruijing.fundamental.remoting.msharp.annotation.Ping;
import org.apache.commons.lang3.tuple.Pair;

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
        boolean prod = false;
        String oldVersion = "";
        String newVersion = "_1";
        // 前端域名替换上线时间
        List<Integer> belongSite = Lists.newArrayList(100221, 100222, 100224, 100224, 100225, 260);
        String onlineTime = "2024-06-10 20:00:00";
        List<Pair<String, String>> list = new ArrayList<>();
        list.add(Pair.of("m.test.rj-info.com/BIO/#/", "mbio.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/LAB/#/", "mscholar.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/EBC/#/", "mebc.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/SRS/#/", "msrs.test.rj-info.com/"));
        list.add(Pair.of("m.test.rj-info.com/AI/#/", "mai.test.rj-info.com/"));
        System.out.println("\n\n");

        System.out.println("测试环境自己弄个库复制下数据模拟处理下，没问题再执行");
        System.out.println("1.访问之前的旧链接，确认基础表已经没有旧链接数据进来！！！");
        System.out.println(selectOld(list, onlineTime));
        System.out.println("\n\n");

        System.out.println("2.创建新表和物化视图双写");
        // 创建新页面聚合表
        System.out.println(pageTableCreat(newVersion) + "\n");
        // 事件聚合表
        System.out.println(eventTableCreat(newVersion) + "\n");
        System.out.println("\n\n");

        System.out.println("3.记住上面新数据开始聚合的时间，处理旧表这个时间点前的数据");
        // clickhouse 基础表url处理
        String updateFeLogUrlSql = "ALTER TABLE base." + FE_TABLE + " UPDATE " + URL_FILED + " = " + getUpdateFeLogSql(URL_FILED, list, belongSite);
        System.out.println(updateFeLogUrlSql);
        System.out.println("\n\n");

        System.out.println("4.查看任务进度，一个执行完再执行另外一个");
        System.out.println("SELECT command, is_done FROM system.mutations WHERE table = 'fe_log';\n");

        // clickhouse 基础表page处理
        String updateFeLogPageSql = "ALTER TABLE base." + FE_TABLE + " UPDATE " + PAGE_FILED + " = " + getUpdateFeLogSql(PAGE_FILED, list, belongSite);
        System.out.println(updateFeLogPageSql);
        System.out.println("\n\n");

        System.out.println("5.初始化新表聚合数据，从旧表替换过去就行");
        System.out.println(copyPageOld(list, newVersion) + "\n");
        System.out.println(copyEventOld(list, newVersion) + "\n");


        System.out.println("6.把新表视图加上");
        // 页面物化视图
        System.out.println(pageViewCreat(newVersion) + "\n");
        // 事件物化视图
        System.out.println(eventViewCreat(newVersion));

        System.out.println("\n\n ");
        System.out.println("7.处理推广服务和统计服务链接\n");
        System.out.println("https://gateway.test.rj-info.com/base/popularize/back/replacePopularizeUrl");
        System.out.println("https://gateway.test.rj-info.com/base/statistics/back/replaceUrl");
        System.out.println(getJsonStrings(list));

        System.out.println("8.验收没问题后，删除旧页面、事件表与视图");
        System.out.println(deleteOld(oldVersion));
        System.out.println("9.切换配置中心表配置");
    }

    /**
     * 查询旧表有没有旧数据进来
     *
     * @param list
     * @param onlineTime 前端上线时间
     * @return
     */
    private static String selectOld(List<Pair<String, String>> list, String onlineTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from base.").append(FE_TABLE).append(" where (");

        for (int i = 0; i < list.size(); i++) {
            Pair<String, String> pair = list.get(i);
            sb.append("belongPage like '").append("%").append(pair.getLeft()).append("%' or url like '").append(pair.getLeft()).append("'").append("\n");
            if (i < list.size() - 1) {
                sb.append(" or ");
            }
        }

        sb.append(") and time >= '").append(onlineTime).append("' order by time desc limit 1");

        return sb.toString();
    }


    private static String getUpdateFeLogSql(String filed, List<Pair<String, String>> list, List<Integer> belongSite) {
        StringBuilder sql = new StringBuilder();
        sql.append("CASE\n");

        for (Pair<String, String> pair : list) {
            sql.append("    WHEN ")
                    .append(filed)
                    .append(" LIKE '")
                    .append("%")
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
                    .append("%")
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

    private static String pageTableCreat(String version) {

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


    private static String eventTableCreat(String version) {
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

    private static String copyPageOld(List<Pair<String, String>> list, String version) {
        StringBuilder caseStatement = new StringBuilder();
        caseStatement.append("CASE");

        for (Pair<String, String> pair : list) {
            caseStatement.append(" WHEN belong_page LIKE '").append("%").append(pair.getLeft()).append("%' THEN replaceOne(belong_page, '")
                    .append(pair.getLeft()).append("', '").append(pair.getRight()).append("')").append("\n");
        }

        caseStatement.append(" ELSE belong_page END");

        String sql = "INSERT INTO base." + PAGE_TABLE + version +
                " SELECT belong_site, org_id, " + caseStatement + " AS belong_page, pv, uv, create_time " +
                "FROM base." + PAGE_TABLE;

        return sql;
    }


    private static String copyEventOld(List<Pair<String, String>> list, String newVersion) {
        StringBuilder caseStatementBelongPage = new StringBuilder();
        StringBuilder caseStatementUrl = new StringBuilder();

        caseStatementBelongPage.append("CASE");
        caseStatementUrl.append("CASE");

        for (Pair<String, String> pair : list) {
            caseStatementBelongPage.append(" WHEN belong_page LIKE '").append("%").append(pair.getLeft()).append("%' THEN replaceOne(belong_page, '")
                    .append(pair.getLeft()).append("', '").append(pair.getRight()).append("')").append("\n");
            caseStatementUrl.append(" WHEN url LIKE '").append("%").append(pair.getLeft()).append("%' THEN replaceOne(url, '")
                    .append(pair.getLeft()).append("', '").append(pair.getRight()).append("')").append("\n");
        }

        caseStatementBelongPage.append(" ELSE belong_page END");
        caseStatementUrl.append(" ELSE url END");

        String sql = "INSERT INTO base." + EVENT_TABLE + newVersion +
                " SELECT belong_site, event_id, event_tag, event_category, org_id, " +
                caseStatementBelongPage + " AS belong_page, " +
                caseStatementUrl + " AS url, pv, uv, create_time " +
                "FROM base." + EVENT_TABLE;

        return sql;
    }


    private static String pageViewCreat(String version) {
        return "CREATE MATERIALIZED VIEW base.base_belong_page_view_log_day_view" + version + " TO base.belong_page_view_log_day" + version + "\n" +
                "(\n" +
                "\n" +
                "    `belong_site` Int32,\n" +
                "\n" +
                "    `org_id` Int32,\n" +
                "\n" +
                "    `belong_page` String,\n" +
                "\n" +
                "    `pv` UInt64,\n" +
                "\n" +
                "    `uv` AggregateFunction(uniq,\n" +
                " String),\n" +
                "\n" +
                "    `create_time` Date\n" +
                ") AS\n" +
                "SELECT\n" +
                "    belongSite AS belong_site,\n" +
                "\n" +
                "    orgId AS org_id,\n" +
                "\n" +
                "    belongPage AS belong_page,\n" +
                "\n" +
                "    count(1) AS pv,\n" +
                "\n" +
                "    uniqState(userSystemFlag) AS uv,\n" +
                "\n" +
                "    toDate(time) AS create_time\n" +
                "FROM base.fe_log\n" +
                "WHERE (belongSite != 0) AND (eventId = 0)\n" +
                "GROUP BY\n" +
                "    belongSite,\n" +
                "\n" +
                "    orgId,\n" +
                "\n" +
                "    belongPage,\n" +
                "\n" +
                "    time";
    }

    private static String eventViewCreat(String version) {
        return "CREATE MATERIALIZED VIEW base.base_belong_event_view_log_day_view" + version + " TO base.belong_event_view_log_day" + version + "\n" +
                "(\n" +
                "\n" +
                "    `belong_site` Int32,\n" +
                "\n" +
                "    `event_id` Int32,\n" +
                "\n" +
                "    `event_tag` String,\n" +
                "\n" +
                "    `event_category` String,\n" +
                "\n" +
                "    `org_id` Int32,\n" +
                "\n" +
                "    `belong_page` String,\n" +
                "\n" +
                "    `url` String,\n" +
                "\n" +
                "    `pv` UInt64,\n" +
                "\n" +
                "    `uv` AggregateFunction(uniq,\n" +
                " String),\n" +
                "\n" +
                "    `create_time` Date\n" +
                ") AS\n" +
                "SELECT\n" +
                "    belongSite AS belong_site,\n" +
                "\n" +
                "    eventId AS event_id,\n" +
                "\n" +
                "    eventTag AS event_tag,\n" +
                "\n" +
                "    eventCategory AS event_category,\n" +
                "\n" +
                "    orgId AS org_id,\n" +
                "\n" +
                "    belongPage AS belong_page,\n" +
                "\n" +
                "    url AS url,\n" +
                "\n" +
                "    count(1) AS pv,\n" +
                "\n" +
                "    uniqState(userSystemFlag) AS uv,\n" +
                "\n" +
                "    toDate(time) AS create_time\n" +
                "FROM base.fe_log\n" +
                "WHERE eventId != 0\n" +
                "GROUP BY\n" +
                "    belongSite,\n" +
                "\n" +
                "    eventId,\n" +
                "\n" +
                "    eventTag,\n" +
                "\n" +
                "    eventCategory,\n" +
                "\n" +
                "    orgId,\n" +
                "\n" +
                "    belongPage,\n" +
                "\n" +
                "    url,\n" +
                "\n" +
                "    time";
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