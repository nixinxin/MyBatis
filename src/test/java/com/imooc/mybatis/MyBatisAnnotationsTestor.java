package com.imooc.mybatis;

import com.imooc.mybatis.dao.GoodsAnnotationDAO;
import com.imooc.mybatis.dto.GoodsDTO;
import com.imooc.mybatis.entity.Goods;
import com.imooc.mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyBatisAnnotationsTestor {
    @Test
    public void testSelectByPriceRange() {
        Float min = 1f;
        Float max = 100f;
        Integer limit = 10;
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            GoodsAnnotationDAO goodsAnnotationDAO = sqlSession.getMapper(GoodsAnnotationDAO.class);
            List<Goods> goodsList = goodsAnnotationDAO.selectByPriceRange(min, max, limit);
            for (Goods goods : goodsList) {
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
    public void testInsertGoodsSelectByKey() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goods = new Goods();
            goods.setTitle("测试商品");
            goods.setSubTitle("测试子标题");
            goods.setOriginalCost(200f);
            goods.setCurrentPrice(100f);
            goods.setDiscount(0.5f);
            goods.setIsFreeDelivery(1);
            goods.setCategoryId(43);
            GoodsAnnotationDAO goodsAnnotationDAO = sqlSession.getMapper(GoodsAnnotationDAO.class);
            int num = goodsAnnotationDAO.insert(goods);
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
    public void testSelectAll() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            GoodsAnnotationDAO goodsAnnotationDAO = sqlSession.getMapper(GoodsAnnotationDAO.class);
            List<Goods> goodsList = goodsAnnotationDAO.selectAll();
            for (Goods goods : goodsList) {
                System.out.println(goods);
            }
            System.out.println(goodsList.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
}
