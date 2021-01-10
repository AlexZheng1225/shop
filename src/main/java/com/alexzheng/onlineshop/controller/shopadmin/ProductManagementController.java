package com.alexzheng.onlineshop.controller.shopadmin;

import com.alexzheng.onlineshop.dto.ImageFileHolder;
import com.alexzheng.onlineshop.dto.ProductExecution;
import com.alexzheng.onlineshop.entity.Product;
import com.alexzheng.onlineshop.entity.ProductCategory;
import com.alexzheng.onlineshop.entity.Shop;
import com.alexzheng.onlineshop.enums.ProductStateEnum;
import com.alexzheng.onlineshop.exceptions.ProductOperationException;
import com.alexzheng.onlineshop.service.ProductCategoryService;
import com.alexzheng.onlineshop.service.ProductService;
import com.alexzheng.onlineshop.utils.CodeUtil;
import com.alexzheng.onlineshop.utils.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Alex Zheng
 * @Date created in 22:48 2020/5/14
 * @Annotation
 */
@RestController
@RequestMapping("/shop")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 店铺所允许最大上传数量
     */
    private static final int IMAGEMAXCOUNT = 6;

    /**
     * 获取某个店铺下所有商品信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getproductlistbyshop")
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取前台传过来每页要求返回的商品数上限
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //从session中获取shop信息，主要获取shopId
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //进行非空判断
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            //获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查询商品名去筛选某个店铺下的商品列表
            long productCategoryId = HttpServletRequestUtil.getLong(request,"productCategoryId");
            String productName = HttpServletRequestUtil.getString(request,"productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(),productCategoryId,productName);
            ProductExecution pe = productService.getProductList(productCondition,pageIndex,pageSize);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("count",pe.getCount());
            modelMap.put("success",true);
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    private Product compactProductCondition(long shopId,long productCategoryId,String productName){
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        //若有指定类别要求则添加进去
        if(productCategoryId>-1){
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //若有商品名模糊查询的要求则添加进去
        if(productName!=null){
            productCondition.setProductName(productName);
        }
        return productCondition;
    }


    /**
     * 修改商品信息
     *
     * @param request
     * @return
     */
    @PostMapping("/modifyproduct")
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        //用于商品编辑时还是调用上下架操作时使用
        //若为前者则进行验证码判断，若为后者则跳过验证码操作
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //验证码判断
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        //接受前端传过来的参数
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageFileHolder fileHolder = null;
        List<ImageFileHolder> fileHolderList = new ArrayList<>();
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断请求中是否存在文件流，有的话取出(缩略图/详情图)
        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                //将request请求转换为MultipartHttpServletRequest对象
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //取出缩略图
                CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                if (commonsMultipartFile != null) {
                    fileHolder = new ImageFileHolder(commonsMultipartFile.getOriginalFilename(), commonsMultipartFile.getInputStream());
                }

                //取出详情图
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        ImageFileHolder productImg = new ImageFileHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        fileHolderList.add(productImg);
                    } else {
                        //结束循环
                        break;
                    }
                }

            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "添加商品详情图和缩略图异常:--" + e.toString());
            return modelMap;
        }

        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //非空判断
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //更新店铺信息
                ProductExecution pe = productService.modifyProduct(product, fileHolder, fileHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", ProductStateEnum.EMPTY.getStateInfo());
        }
        return modelMap;
    }


    /**
     * 通过productId获取商品信息
     *
     * @param productId
     * @return
     */
    @GetMapping("/getproductbyid")
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productId < 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        } else {
            //获取店铺信息
            Product product = productService.getProductById(productId);
            //查询该店铺下的商品类别列表
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategory(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", "true");
        }
        return modelMap;
    }


    /**
     * 添加商品
     *
     * @param request
     * @return
     */
    @PostMapping("/addproduct")
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的验证码");
            return modelMap;
        }

        //接收前端传过来的参数
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        //缩略图
        ImageFileHolder fileHolder = null;
        //处理文件流
        MultipartHttpServletRequest multipartRequest = null;
        //详情图列表
        List<ImageFileHolder> fileHolderList = new ArrayList<>();
        //从request的session中获取文件流
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //判断请求中是否存在文件流，有则取出相关文件，包含详情图和缩略图
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                fileHolder = new ImageFileHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                //讲详情图片存储到List<ImageFileHolder>中，最多支支持六张
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    //空值判断
                    if (productImgFile != null) {
                        ImageFileHolder productImg = new ImageFileHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        //通过循环依次存入详情图片
                        fileHolderList.add(productImg);
                    } else {
                        break;
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            //将前端对象转化为后端实例
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //判断前台传过来的product，商品缩略图以及详情图
        if (product != null && fileHolder != null && fileHolderList.size() > 0) {
            try {
                //从session中获取当前店铺的id并复制给product，减少前端依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                //执行添加操作
                ProductExecution pe = productService.addProduct(product, fileHolder, fileHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }
        return modelMap;
    }
}
