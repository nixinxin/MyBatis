package com.imooc.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.imooc.mybatis.dto.GoodsDTO;
import com.imooc.mybatis.entity.GoodsDetail;
import com.imooc.mybatis.entity.Goods;
import com.imooc.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * junit单元测试
 */

public class MyBatisTestor {

    @Test
    public void testSqlSessionFactory() throws IOException {
        Reader resourceAsReader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsReader);
        System.out.println("sqlSessionFactory加载成功");
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            System.out.println(sqlSession);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void testMyBatisUtils() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Connection connection = sqlSession.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }

    }

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            List<Goods> list = sqlSession.selectList("goods.selectAll");
            for (Goods goods : list) {
                System.out.println(goods.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testSelectById() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goods = sqlSession.selectOne("goods.selectById", 1602);
            System.out.println(goods.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testSelectByPriceRange() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();

            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("max", 500);
            map.put("min", 100);
            map.put("limit", 10);
            List<Goods> goodsList = sqlSession.selectList("goods.selectByPriceRange", map);
            int i = 1;
            for (Goods goods: goodsList) {
                System.out.println(i + ":" + goods.getTitle() + "," +goods.getCurrentPrice());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testSelectGoodsMap() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            List<Map> goodsList = sqlSession.selectList("goods.selectGoodsMap");
            int i = 1;
            for (Map goods: goodsList) {
                System.out.println(i + ":" + goods.get("title") + "," +goods.get("current_price"));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testSelectGoodsDTO() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            List<GoodsDTO> goodsList = sqlSession.selectList("goods.selectGoodsDTO");
            int i = 1;
            for (GoodsDTO goodsDTO: goodsList) {
                System.out.println(i + ":" + goodsDTO.toString());
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testInsertGoodsSelectByKey() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goods = new Goods();
            goods.setTitle("测试商品1");
            goods.setSubTitle("测试子标题");
            goods.setOriginalCost(200f);
            goods.setCurrentPrice(100f);
            goods.setDiscount(0.5f);
            goods.setIsFreeDelivery(1);
            goods.setCategoryId(43);
            int num = sqlSession.insert("goods.insertSelectKey", goods);
            sqlSession.commit();
            System.out.println(num + " " + goods.getGoodsId());
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        }
    }

    @Test
    public void testInsertUseGeneratedKeys() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goods = new Goods();
            goods.setTitle("测试商品1");
            goods.setSubTitle("测试子标题");
            goods.setOriginalCost(200f);
            goods.setCurrentPrice(100f);
            goods.setDiscount(0.5f);
            goods.setIsFreeDelivery(1);
            goods.setCategoryId(43);
            int num = sqlSession.insert("goods.insertUseGeneratedKeys", goods);
            sqlSession.commit();
            System.out.println(num + " " + goods.getGoodsId());
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        }
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();

            Goods goods = sqlSession.selectOne("goods.selectById", 739);
            goods.setTitle("更新后的标题");
            int num = sqlSession.update("goods.update", goods);
            sqlSession.commit();
            System.out.println(num + " " + goods.getGoodsId());
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        }
    }

    @Test
    public void testDelete() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            int num = sqlSession.delete("goods.delete", 2677);
            sqlSession.commit();
            System.out.println(num);
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        }
    }

    @Test
    public void testDynamicSQL() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Map<String, Integer> param = new HashMap<String, Integer>();
            param.put("categoryId", 44);
            param.put("currentPrice", 500);
            List<Goods> goodsList = sqlSession.selectList("goods.dynamicSQL", param);
            sqlSession.commit();
            for (Goods goods : goodsList) {
                System.out.println(goods.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        }
    }

    @Test
    public void testLevelOneCache() {
        SqlSession sqlSession = null;
        /*第一个sqlSession*/
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goodsone = sqlSession.selectOne("goods.selectById", 1602);
            /*第二次查询时直接从sqlSession的一级缓存中获取已有的数据*/
            Goods goodsTwo = sqlSession.selectOne("goods.selectById", 1602);
            System.out.println(goodsone.hashCode());
            System.out.println(goodsTwo.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
        /*第二个sqlSession*/
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goodsone = sqlSession.selectOne("goods.selectById", 1602);
            /*commit会强制清空当前命名空间下所有的sqlSession中的缓存*/
            sqlSession.commit();
            Goods goodsTwo = sqlSession.selectOne("goods.selectById", 1602);
            System.out.println(goodsone.hashCode());
            System.out.println(goodsTwo.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testLevelTwoCache() {
        SqlSession sqlSession = null;
        /*第一个sqlSession*/
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goodsone = sqlSession.selectOne("goods.selectById", 1602);
            System.out.println(goodsone.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
        /*第二个sqlSession*/
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goodsTwo = sqlSession.selectOne("goods.selectById", 1602);
            System.out.println(goodsTwo.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testSelectOneToMany() {
        SqlSession sqlSession = null;

        try {
            sqlSession = MyBatisUtils.openSession();
            List<Goods> goodsList = sqlSession.selectList("goods.selectOneToMany");
            for (Goods goods: goodsList) {
                System.out.println(goods.getTitle() + " " + goods.getGoodsDetails().size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testManyToOne() {
        SqlSession sqlSession = null;

        try {
            sqlSession = MyBatisUtils.openSession();
            List<GoodsDetail> goodsDetailList = sqlSession.selectList("goodsDetail.selectManyToOne");
            for (GoodsDetail goodsDetail: goodsDetailList) {
                System.out.println(goodsDetail.getGdPicUrl() + " " + goodsDetail.getGoods().getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    @Test
    public void testSelectPage() {
        SqlSession sqlSession = null;

        try {
            sqlSession = MyBatisUtils.openSession();
            PageHelper.startPage(2, 10);
            List<Goods> pageGoodsList = sqlSession.selectList("goods.selectPage");
            System.out.println(((Page) pageGoodsList).getPages());
            System.out.println(((Page) pageGoodsList).getTotal());
            System.out.println(((Page) pageGoodsList).getStartRow());
            System.out.println(((Page) pageGoodsList).getEndRow());
            System.out.println(((Page) pageGoodsList).getPageNum());
            for (Goods goods: pageGoodsList) {
                System.out.println(goods.getGoodsId() + " " + goods.getTitle() + " " + goods.getCurrentPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
}
