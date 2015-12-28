package com.github.p4535992.util.database.jooq;

import com.github.p4535992.util.collection.ArrayUtilities;
import com.github.p4535992.util.database.jooq.spring.config.PersistenceContext;
import com.github.p4535992.util.regex.pattern.Patterns;
import com.github.p4535992.util.string.StringUtilities;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Created by 4535992 on 21/10/2015.
 * @author 4535992.
 * @version 2015-12-24.
 */
public class JOOQSupport {

    /**
     * Method utility convert a insert query of JOOQ in a insert query for springframework jdbc.
     * @param queryString the String of the query JOOQ.
     * @param columns the Array of String with names of columns.
     * @return the String of the Query SpringFramework JDBC.
     */
    public static String getQueryInsertValuesParam(String queryString, String[] columns){
        String preQuery = StringUtilities.findWithRegex(queryString, Patterns.MANAGE_SQL_PREQUERY_INSERT);
        String postQuery = queryString.replace(preQuery, "");
        if (StringUtilities.isMatch(postQuery,Patterns.MANAGE_SQL_QUERY_INSERT_CHECK_WHERE )){
            String[] val = postQuery.split(Patterns.MANAGE_SQL_QUERY_INSERT_GET_WHERE_PARAM_3.pattern());
            if(val.length > 2) {
                String postQuery0 = val[0].replace("(","").replace(")", "");
                String postQuery1 = val[1].replace("(","").replace(")","");
            }
        }else {
            //queryString = queryString.replace(preQuery, "");
            postQuery = "";
        }
        preQuery = StringUtilities.findWithRegex(preQuery,Patterns.MANAGE_SQL_QUERY_INSERT_GET_VALUES_PARAM_2v2).trim();
        //values = values.substring(0, values.length() - 1);
        //String[] param = values.split(",");
        //for(String s: param)values = values.replace(s.trim(),"?");
        String[] array = ArrayUtilities.createSingleton("?", columns.length);
        String values = ArrayUtilities.toString(array);
        //return queryString + " values (" + values +")" + supportQuery;
        return preQuery + ArrayUtilities.toString(columns)
                + ") values (" + values +")" + postQuery;
    }

    /**
     * Method utility convert a insert query of JOOQ in a insert query for springframework jdbc.
     * @param queryString the String of the query JOOQ.
     * @return the String of the Query SpringFramework JDBC.
     */
    public static String getQueryInsertWhereParam(String queryString){
        String values = StringUtilities.findWithRegex(queryString,Patterns.MANAGE_SQL_QUERY_INSERT_GET_WHERE_PARAM_1);
        String supportQuery = queryString.replace(values, "");
        if (supportQuery.toLowerCase().contains(" order by ")){
            String[] val = queryString.split(values);
            queryString = val[0];
            supportQuery = val[1];
        }else {
            queryString = queryString.replace(values, "");
            supportQuery = "";
        }
        values = values.replace(StringUtilities.findWithRegex(values,Patterns.MANAGE_SQL_QUERY_INSERT_GET_WHERE_PARAM_2),"");
        values = values.substring(0,values.length()-1);
        String[] paramCond = values.split("(and|or)");
        for(String s: paramCond){
            String[] paramWhere = s.split("(is|=|>=)");
            values = values.replace(paramWhere[1].trim(), "?");
        }
        return queryString + " where (" + values +")" + supportQuery;
    }


    //TODO: MAKE THE FULL CODE FOR INTEGRATION WITH SPRING
    @SuppressWarnings("unchecked")
    public static <T> void startSpring(Class<?> classObject) {
        try (AnnotationConfigApplicationContext applicationContext = 
                new AnnotationConfigApplicationContext(PersistenceContext.class)) {
            T userDetailTest = (T) applicationContext.getBean(classObject);
            //userDetailTest.start();
        }
    }


}
