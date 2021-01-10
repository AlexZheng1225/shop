package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.dao.ProductCategoryDao;
import com.alexzheng.onlineshop.dao.ProductDao;
import com.alexzheng.onlineshop.dto.ProductCategoryExecution;
import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.enums.ProductCategoryStateEnum;
import com.alexzheng.onlineshop.exceptions.ProductCategoryOperationExecption;
import com.alexzheng.onlineshop.exceptions.ProductOperationException;
import com.alexzheng.onlineshop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 17:05 2020/4/27
 * @Annotation
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategory(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationExecption {
        if(productCategoryList==null||productCategoryList.size()<=0){
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPETY_LIST);
        }

        try{
            int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
            if(effectedNum<=0){
                throw new ProductCategoryOperationExecption("店铺类别创建失败");
            }else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryOperationExecption("batchAddProductCategory Error:"+e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationExecption {
        //接触tb_product中商品与该productCategoryId的联系
        try{
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if(effectedNum<0){
                throw new ProductOperationException("商品类别置空失败");
            }
        }catch (ProductCategoryOperationExecption e){
            throw new ProductCategoryOperationExecption("deleteProductCategory Error:"+e.getMessage());
        }

        //进行类别删除
        try{
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if(effectedNum<=0){
                throw new ProductCategoryOperationExecption("商品类别删除失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (ProductCategoryOperationExecption e){
            throw new ProductCategoryOperationExecption("deleteProductCategory Error:"+e.getMessage());
        }
    }
}
