package com.vst.quartz.utils.table;

import com.alibaba.fastjson.JSONObject;
import com.vst.common.tools.string.ToolsString;
import com.vst.quartz.enumerate.TypeEnum;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kai
 * sql语句并且和数据处理工具类
 * TODO: 2018/4/23
 */
public class TableTools {

    /**
     * 实体类的绝对路径
     */
    private static String url="com.vst.common.pojo.";


    /**
     * kai
     * 拼接数据保存的sql
     * @param tableName 表名
     * @param maps 需要保存的数据
     * @param file 拼接好的字段
     * @param map 数据类型
     * @return 返回值 拼接的sql语句
     */
    public static String saveData(String tableName, List<Map<String,Object>> maps,String file,Map<String, String> map){
        if (ToolsString.isEmpty(file)){
            return null;
        }

        String[] files = file.split(",");

        //拼接sql
        StringBuilder builder=new StringBuilder();
        builder.append("replace INTO "+tableName+"("+file+") ");
        builder.append(" VALUES");
        int i=0;
        //遍历数据
        for (Map<String, Object> m :maps) {
            builder.append("(");
            //拼接数据
            StringBuilder value= new StringBuilder();
            for (String f: files){

                if (ToolsString.isEmpty(m.get(f))){
                    value.append("'',");
                }else {
                    //获取类型
                    String type = map.get(f);
                    if (type.contains(TypeEnum.VST_TYPE.getTypeString()) || type.contains(TypeEnum.VST_TYPE.getTypeChar())){
                        value.append("'"+m.get(f)+"',");
                    }else {
                        value.append(m.get(f) + ",");
                    }
                }

            }

            //去掉数据最好一个逗号
            String v =value.substring(0,value.lastIndexOf(","));
            i++;
            if (i<maps.size()){
                builder.append(v+"),");
            }else {
                builder.append(v+");");
            }
        }

        return builder.toString();
    }

    /**
     * kai
     * 封装清理的sql
     * @param tableName 表明
     * @param condition 需要保存的数据
     * @return 返回值 拼接的sql
     */
    public static String deleteData(String tableName,String condition){
        if (ToolsString.isEmpty(tableName)){
            return null;
        }
        String sql;//用于接收拼接的sql
        //如果condition为空，则说明是操作全部，没有条件
        if (ToolsString.isEmpty(condition)){
            sql="delete from "+tableName;
        }else {
            sql="delete from "+tableName+" where "+condition;
        }
        return sql;
    }

    /**
     * kai
     * 拼接模糊查询表名的sql
     * TODO: 2018/4/19 17:24:40
     * @param db 数据源
     * @param table 查询表明的条件
     * @return 返回值 String
     */
    public static String selectLikeTable(String db,String table){
        StringBuilder builder = new StringBuilder();
        if (ToolsString.isEmpty(db) || ToolsString.isEmpty(table)){
            return null;
        }

        builder.append("SELECT * FROM information_schema.TABLES WHERE ");
        builder.append("TABLE_SCHEMA='"+db+"' ");
        builder.append(" AND TABLE_NAME like '"+table+"_%'");

        return builder.toString();
    }


    /**
     * kai
     * 拼接根据表名查询的sql
     * TODO: 2018/4/19 17:24:40
     * @param db 数据源
     * @param table 查询表明的条件
     * @return 返回值 String
     */
    public static String selectTable(String db,String table){
        StringBuilder builder = new StringBuilder();
        if (ToolsString.isEmpty(db) || ToolsString.isEmpty(table)){
            return null;
        }

        builder.append("SELECT * FROM information_schema.TABLES WHERE ");
        builder.append("TABLE_SCHEMA = '"+db+"' ");
        builder.append(" AND TABLE_NAME = '"+table+"'");

        return builder.toString();
    }

    /**
     * kai
     * 查询数据源中表的字段名称和类型
     * TODO: 2018/4/18 18：41：46
     * @param db 数据源
     * @param table 表名称
     * @return 返回值String
     */
    public static String getTable(String db,String table){
        StringBuilder builder = new StringBuilder();

        builder.append("SELECT ");
        builder.append("tableInfo_.table_name,columnInfo_.column_type,columnInfo_.column_name,columnInfo_.column_comment ");
        builder.append("FROM INFORMATION_SCHEMA.TABLES tableInfo_ ");
        builder.append("INNER JOIN INFORMATION_SCHEMA.COLUMNS columnInfo_ ON tableInfo_.table_name = columnInfo_.table_name ");
        builder.append("WHERE tableInfo_.table_schema = '"+db+"' AND columnInfo_.table_schema = '"+db+"' ");
        builder.append("AND tableInfo_.table_name = '"+table+"'");

        return builder.toString();
    }


    /**
     * kai
     * 根据反射机制获取实体类中的字段属性
     * @param entityName 实体类名称
     * @return 返回值String 拼成用逗号隔开
     */
    public static String getFiled(String entityName){
        String filed="";
        StringBuilder builder = new StringBuilder();
        try {
            //定义一个class对象
            //通过Class获取实体对象
            Class c=Class.forName(entityName);

            //获取实体对象中的字段属性
            Field[] fields=c.getDeclaredFields();
            //遍历字段
            for (Field field:fields){
               String result=field.getName()+",";
               builder.append(result);

            }
            //去掉最后一个逗号
             filed =  builder.toString().substring(0,builder.toString().lastIndexOf(","));

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return filed;

    }


    /**
     * kai
     * 封装字段的类型
     * TODO: 2018/3/20 17:33:33
     * @param entityName 实体类名称
     * @return 返回值 map
     */
    public static Map<String,String> getType(String entityName){
        Map<String,String> map =new HashMap<>(30);
        try {
            //定义一个class对象
            //通过Class获取实体对象
            Class c=Class.forName(entityName);

            //获取实体对象中的字段属性
            Field[] fields=c.getDeclaredFields();
            //遍历字段
            for (Field field:fields){
                map.put(field.getName(),field.getGenericType().toString());
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return map;
    }




    /**
     * kai
     * 获取遍历的字段名
     * @param maps 查询的表字段属性
     * @return 返回值拼接后的字段
     */
    public static JSONObject getMap(List<Map<String,Object>> maps){
        JSONObject result = new JSONObject();
        //用于封装字段
        StringBuilder builder = new StringBuilder();
        //根据字段保存字段类型
        Map<String,String> map =new HashMap<String, String>(30);
        //根据字段保存字段描述
        Map<String,String> stringMap = new HashMap<String, String>();
        for (Map<String, Object> m : maps) {
            JSONObject object = new JSONObject(m);
            //获取表字段
            String name = object.getString("column_name");

            String file=name+",";
            builder.append(file);

            //获取表字段属性
            String filed = object.getString("column_type");
            map.put(name,filed);

            //获取表字段描述
            String comment = object.getString("column_comment");
            if (ToolsString.isEmpty(comment)){
                stringMap.put(name,name);
            }else {
                stringMap.put(name,comment);
            }
        }

        result.put("column_name",builder.toString().substring(0,builder.toString().lastIndexOf(",")));
        result.put("column_type",map);
        result.put("column_comment",stringMap);

        return result;
    }

}
