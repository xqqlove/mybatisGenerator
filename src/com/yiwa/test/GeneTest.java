package com.yiwa.test;

import com.yiwa.bean.Department;
import com.yiwa.dao.DepartmentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneTest {
    @Test
    public void test() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("src/conf/generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "conf/mybatis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
    @Test
    public void test1() throws IOException{
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            Department department = mapper.selectByPrimaryKey(1);
            System.out.println(department);
        } finally {
            sqlSession.close();
        }
    }
}
