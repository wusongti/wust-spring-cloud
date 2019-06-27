package com.wust.springcloud.common.util;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实体类生成工具
 * Created by WST on 2019/6/13.
 */
public class GenEntityUtil {

    private static String tableName;// 表名
    private static String[] colNames; // 列名数组
    private static String[] colTypes; // 列名类型数组
    private static int[] colSizes; // 列名大小数组
    private static boolean needUtil = false; // 是否需要导入包java.util.*
    private static boolean needSql = false; // 是否需要导入包java.sql.*
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String SQL = "SELECT * FROM ";// 数据库操作

    private static String packageOutPath = "com.wust.springcloud.common.entity.tmp";// 指定实体生成所在包的路径
    private static String authorName = "wust";// 作者名字
    private static String[] generateTables = null;//指定需要生成的表的表名，全部生成设置为null


    private GenEntityUtil() {
    }

    /**
     * 生成class的所有内容
     *
     * @return
     */
    private static String parse() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + packageOutPath + ";\r\n");
        sb.append("\r\n");
        // 判断是否导入工具包
        if (needUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (needSql) {
            sb.append("import java.sql.*;\r\n");
        }
        // 注释部分
        sb.append("/**\r\n");
        sb.append(" * table name:  " + tableName + "\r\n");
        sb.append(" * author name: " + authorName + "\r\n");
        sb.append(" * create time: " + SDF.format(new Date()) + "\r\n");
        sb.append(" */ \r\n");
        // 实体部分
        sb.append("public class " + getTransStr(tableName, true) + "{\r\n\r\n");
        processAllAttrs(sb);// 属性
        sb.append("\r\n");
        processAllMethod(sb);// get set方法
        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 生成所有成员变量
     *
     * @param sb
     */
    private static void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + getTransStr(colNames[i], false) + ";\r\n");
        }
    }

    /**
     * 生成所有get/set方法
     *
     * @param sb
     */
    private static void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\tpublic void set" + getTransStr(colNames[i], true) + "(" + sqlType2JavaType(colTypes[i]) + " "
                    + getTransStr(colNames[i], false) + "){\r\n");
            sb.append("\t\tthis." + getTransStr(colNames[i], false) + "=" + getTransStr(colNames[i], false) + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + getTransStr(colNames[i], true) + "(){\r\n");
            sb.append("\t\treturn " + getTransStr(colNames[i], false) + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
     * 将传入字符串的首字母转成大写
     *
     * @param str
     * @return
     */
    private static String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z')
            ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    /**
     * 将mysql中表名和字段名转换成驼峰形式
     *
     * @param before
     * @param firstChar2Upper
     * @return
     */
    private static String getTransStr(String before, boolean firstChar2Upper) {
        before = before.toLowerCase();
        //不带"_"的字符串,则直接首字母大写后返回
        if (!before.contains("_"))
            return firstChar2Upper ? initCap(before) : before;
        String[] strs = before.split("_");
        StringBuffer after = null;
        if (firstChar2Upper) {
            after = new StringBuffer(initCap(strs[0]));
        } else {
            after = new StringBuffer(strs[0]);
        }
        if (strs.length > 1) {
            for (int i = 1; i < strs.length; i++)
                after.append(initCap(strs[i]));
        }
        return after.toString();
    }

    /**
     * @return
     * @description 查找sql字段类型所对应的Java类型
     * @author paul
     * @date 2017年8月18日 下午4:55:41
     * @update 2017年8月18日 下午4:55:41
     * @version V1.0
     */
    private static String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "byte[]";
        } else if (sqlType.equalsIgnoreCase("blob")) {
            return "byte[]";
        }
        return null;
    }

    /**
     * @throws Exception
     * @description 生成方法
     * @author paul
     * @date 2017年8月18日 下午2:04:20
     * @update 2017年8月18日 下午2:04:20
     * @version V1.0
     */
    public static void generate(final String URL,final String DRIVER,final String NAME,final String PASS) {
        //与数据库的连接
        Connection con;
        PreparedStatement pStemt = null;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            return;
        }
        try {
            con = DriverManager.getConnection(URL, NAME, PASS);
        } catch (SQLException e) {
            return;
        }
        System.out.println("connect database success...");
        //获取数据库的元数据
        DatabaseMetaData db = null;
        try {
            db = con.getMetaData();
        } catch (SQLException e) {
            return;
        }
        //是否有指定生成表，有指定则直接用指定表，没有则全表生成
        List<String> tableNames = new ArrayList<>();
        if (generateTables == null) {
            //从元数据中获取到所有的表名
            ResultSet rs = null;
            try {
                rs = db.getTables(null, null, null, new String[]{"TABLE"});
                while (rs.next()) tableNames.add(rs.getString(3));
            } catch (SQLException e) {
                return;
            }
        } else {
            for (String tableName : generateTables) tableNames.add(tableName);
        }
        String tableSql;
        PrintWriter pw = null;
        for (int j = 0; j < tableNames.size(); j++) {
            try {
                tableName = tableNames.get(j);
                tableSql = SQL + tableName;
                pStemt = con.prepareStatement(tableSql);
                ResultSetMetaData rsmd = pStemt.getMetaData();
                int size = rsmd.getColumnCount();
                colNames = new String[size];
                colTypes = new String[size];
                colSizes = new int[size];
                //获取所需的信息
                for (int i = 0; i < size; i++) {
                    colNames[i] = rsmd.getColumnName(i + 1);
                    colTypes[i] = rsmd.getColumnTypeName(i + 1);
                    if (colTypes[i].equalsIgnoreCase("datetime"))
                        needUtil = true;
                    if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text"))
                        needSql = true;
                    colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
                }
                //解析生成class的所有内容
                String content = parse();
                //输出生成文件
                String dirName = "D:\\entity";
                File dir = new File(dirName);
                if (!dir.exists() && dir.mkdirs()) System.out.println("generate dir 【" + dirName + "】");
                String javaPath = dirName + "/" + getTransStr(tableName, true) + ".java";
                FileWriter fw = new FileWriter(javaPath);
                pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                System.out.println("create class 【" + tableName + "】");
            } catch (Exception e) {
                continue;
            }
        }
        if (pw != null)
            pw.close();
    }

}
