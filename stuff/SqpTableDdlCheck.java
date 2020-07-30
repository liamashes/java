package org.monitor.data.module.dimension.type;

import org.monitor.data.module.dimension.DoubleKeyMap;
import org.monitor.data.module.dimension.algorithm.fuzzyWuzzy.FuzzySearch;
import org.monitor.data.module.dimension.util.Log;
import org.scalactic.Bool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqpTableDdlCheck implements Type{

    Log logger = new Log();
    private String type = "SqpTbDdl";
    private int SqpTbDdl=1;

    private String ObjectName;
    //private String Engine;

    /**
     * 表结构校验
     */
    private String outDeli=":::";
    private String outInfo;
    private String failedInfo;
    private int score;

    public SqpTableDdlCheck(){

    }

    public  SqpTableDdlCheck(String tableName){
        this.ObjectName = tableName;
        //this.Engine = Engine;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getTypeValue() {
        return SqpTbDdl;
    }

    public String getObjectName() {
        return ObjectName;
    }

    public String getFailedInfo() {
        return failedInfo;
    }
    /**
     * 当前作用于数据库，因此对象是表名
     * @param objectName
     */
    public void setTableName(String objectName) {
        if (objectName.contains(".")){
            this.ObjectName = objectName;
        }else {
            logger.logError("TableName should specify it's dbName");
        }
    }

    public String generateCmd(){
        if (ObjectName.isEmpty()){
            logger.logError("Set ObjectName Before Generate Command");
        }
        String cmd = "";
        cmd = "DESC "+ ObjectName;

        return cmd;
    }

    public String parser(ResultSet set){
        String lineContent="";
        try {
            Boolean end = false;
            while (set.next() && !end) {
                String colName = set.getString(1);
                if (colName.isEmpty()){
                    end=true;
                    continue;
                }else {
                    lineContent += colName + outDeli;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lineContent.toLowerCase();
    }

    public int compareOutInfo(String out1,String out2){
        String[] s1 = out1.split(outDeli);
        String[] s2 = out2.split(outDeli);

        return compareOutInfo(s1,s2);
    }

    public void cleanComparedInfo(){
        failedInfo="";
        score=0;
    }

    public void showComparedInfo(){
        logger.logInfo("得分："+score);
        if (score <100){
            for (String s : failedInfo.split("\n") ) {
                logger.logInfo("可能原因："+s);
            }
        }
    }

    public int compareOutInfo(String[] out1,String[] out2){
        cleanComparedInfo();
        /**
         * 将检查过程中遇到的问题写入failedInfo
         */
        String[] s1,s2;
        if (out1.length > out2.length){
             s1= out1;
             s2= out2;
        }else {
            s1= out2;
            s2= out1;
        }
        int s1Length = s1.length;
        int s2Length = s2.length;

        //定义映射字符串组
        char[] c1 = new char[s1Length];
        char[] c2 = new char[s2Length];

        Map<Integer, String> table2 = new HashMap<>(s2Length);
        for (int k = 0;k<s2Length;++k ) {
            table2.put(k,s2[k]);
        }

        /**
         *检查表A中的字段在表B中的匹配情况，筛选出分数大于80且匹配度最高的那个结果，并将两个字符串在对应的字符串中做替换，替换为字母
         */

        int rPos=0,wPos=0;
        char startCharacter='a';
        int jumpDistance = s1Length + s2Length;
        int cntScore=0;
        if (s1Length!=s2Length){
            cntScore=20;
            failedInfo+="字段数不一致;";
        }


        for (int i = 0;i<s1Length;++i){

            int bestMatch=-1;
            int maxScore = 0;
            for( int j:table2.keySet() ) {
                int thisScore = FuzzySearch.ratio(s1[i],s2[j]);
                if (thisScore > maxScore){
                    maxScore = thisScore;
                    bestMatch = j;
                }

                //如果匹配度大于95，直接认为拿到最佳结果
                if(maxScore > 95){
                    break;
                }
            }
            /**
             * 未找到最佳匹配
             */
            if (maxScore<80){
                failedInfo+="字段："+s1[i]+"未找到最佳匹配字段;";
                c1[i] = (char)(startCharacter + jumpDistance + ++wPos);
            }else{
                c1[i] = (char)(startCharacter + rPos);
                c2[bestMatch] = (char) (startCharacter + rPos++);
                table2.remove(bestMatch);
            }
        }

        for (int i=0;i<s2Length;++i){
            if (c2[i]=='\u0000'){
                c2[i] = (char)(startCharacter + jumpDistance + ++wPos);
            }
        }


        score = FuzzySearch.ratio(charToString(c1),charToString(c2)) - cntScore;
        //当情况都满足，但是未获取到错误原因是，则是次序的问题
        if (score <100 && failedInfo.isEmpty()){
            failedInfo+="字段次序不一致;";
        }
        // showComparedInfo();
        return score;
    }

    public String charToString(char[] chars){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i=0;i<chars.length;++i){
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }
}
