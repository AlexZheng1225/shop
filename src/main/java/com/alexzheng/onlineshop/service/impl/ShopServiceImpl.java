package com.alexzheng.onlineshop.service.impl;

import com.alexzheng.onlineshop.dao.ShopDao;
import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ShopExecution;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.enums.ShopStateEnum;
import com.alexzheng.onlineshop.exceptions.ShopOperationException;
import com.alexzheng.onlineshop.service.ShopService;
import com.alexzheng.onlineshop.utils.ImageUtil;
import com.alexzheng.onlineshop.utils.PageCalculator;
import com.alexzheng.onlineshop.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author Alex Zheng
 * @Date created in 16:30 2020/4/7
 * @Annotation
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondiction, int pageIndex, int pageSize) {
        //算出要从第几行开始获取分页的数据
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondiction, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondiction);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public Shop getShopById(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageFileHolder imageFileHolder) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                //判断是否需要处理图片
                if (imageFileHolder != null && imageFileHolder.getImageFileInputStream() != null) {
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    //更新图片信息
                    addShopImg(shop, imageFileHolder);
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
                int effected = shopDao.updateShop(shop);
                if (effected <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error: " + e.getMessage());
            }
        }
    }


    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageFileHolder imageFileHolder) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //默认新店铺状态：审核中
            //TODO 这里改为1 方便前端调试
            shop.setEnableStatus(1);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //插入shop
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (imageFileHolder.getImageFileInputStream() != null) {
                    try {
                        //1.存储图片
                        addShopImg(shop, imageFileHolder);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg Error:" + e.getMessage());
                    }
                    //2.更新图片路径
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, ImageFileHolder imageFileHolder) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(imageFileHolder, dest);
        shop.setShopImg(shopImgAddr);
    }
}
