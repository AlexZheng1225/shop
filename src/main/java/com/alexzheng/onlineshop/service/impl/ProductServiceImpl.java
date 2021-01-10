package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.dao.ProductDao;
import com.alexzheng.onlineshop.dao.ProductImgDao;
import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ProductExecution;
import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.entity.ProductImg;
import com.alexzheng.onlineshop.enums.ProductStateEnum;
import com.alexzheng.onlineshop.exceptions.ProductOperationException;
import com.alexzheng.onlineshop.service.ProductService;
import com.alexzheng.onlineshop.utils.ImageUtil;
import com.alexzheng.onlineshop.utils.PageCalculator;
import com.alexzheng.onlineshop.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 16:35 2020/5/14
 * @Annotation
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //页码转换为数据库的行码
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        //基于同等查询条件下查询总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    public ProductExecution modifyProduct(Product product, ImageFileHolder thumbnail, List<ImageFileHolder> productImgHolderList) {
        //判断product不为空且关键信息不为空
        if(product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setLastEditTime(new Date());
            //若上传缩略图不为空
            if(thumbnail!=null){
                Product tempProduct = productDao.queryProductById(product.getProductId());
                //若商品缩略图路径不为空
                if(tempProduct.getImgAddr()!=null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }

            //若有新的详情图片存入，则删除原有商品详情图，并添加新的图片
            if(productImgHolderList!=null&&productImgHolderList.size()>0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgHolderList);
            }

            try{
                //更新信息
                int effectedNum = productDao.updateProduct(product);
                if(effectedNum<=0){
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败"+e.toString());
            }
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(Long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    public ProductExecution addProduct(Product product,
                                       ImageFileHolder thumbnail,
                                       List<ImageFileHolder> thumbnailList) throws ProductOperationException {
        //非空判断
        if(product==null || product.getShop()==null || product.getShop().getShopId()==null){
            //TODO
            throw new ProductOperationException("商品所属店铺信息不明确 shopId非法");
        }

        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);

        //判断是否添加缩略图
        if(thumbnail!=null){
            addThumbnail(product,thumbnail);
        }

        try{
            //创建商品信息
            int effectedNum = productDao.insertProduct(product);
            if(effectedNum<=0){
                throw new ProductOperationException("商品创建失败");
            }
        }catch (Exception e){
            throw new ProductOperationException("创建商品失败："+e.getMessage());
        }

        //若商品详情图片不为空则添加
        if(thumbnailList!=null && thumbnailList.size()>0){
            addProductImgList(product,thumbnailList);
        }
        return new ProductExecution(ProductStateEnum.SUCCESS,product);
    }

    /**
     * 批量删除详情图片
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //根据productId获取图片
        List<ProductImg> productImgList = productImgDao.queryProductImgListById(productId);
        //删除
        for(ProductImg productImg:productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库中相关信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 批量添加详情图
     * @param product
     * @param thumbnailList
     */
    private void addProductImgList(Product product, List<ImageFileHolder> thumbnailList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        //遍历一次讲图片存入实体类中，并add到数组中
        for(ImageFileHolder fileHolder:thumbnailList){
            //获得带文件名的图片相对路径
            String imgAddr = ImageUtil.generateNormalImg(fileHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImg.setImgAddr(imgAddr);
            productImgList.add(productImg);
        }

        if(productImgList.size()>0){
            try{
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectedNum<=0){
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败"+e.toString());
            }
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageFileHolder thumbnail) {
        //生成图片所对应文件夹的相对路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        //图片已存储并返回带图片名字的相对路径
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }
}
