package com.frxs.wmspick.rest.service;


import com.frxs.wmspick.model.CompletePickOrder;
import com.frxs.wmspick.model.PickingGroup;
import com.frxs.wmspick.model.PickingOrder;
import com.frxs.wmspick.model.PickingOrderCount;
import com.frxs.wmspick.model.UserInfo;
import com.frxs.wmspick.rest.model.ApiResponse;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService<T> {

    //     登录
    @FormUrlEncoded
    @POST("Packing/Login")
    Call<ApiResponse<UserInfo>> UserLogin(@FieldMap Map<String, Object> params);

    // 获取流水线
    @FormUrlEncoded
    @POST("PickingGroup/GetPickingGroup")
    Call<ApiResponse<List<PickingGroup>>> GetPickingGroup(@FieldMap Map<String, Object> params);

    // 获取流水线工位当前已拣数量和未拣数量(拣货单)
    @FormUrlEncoded
    @POST("PickingGroup/GetPickingOrderStatisticCntByApp")
    Call<ApiResponse<PickingOrderCount>> GetPickingOrderStatisticCnt(@FieldMap Map<String, Object> params);

    // 获取门店拣货单
    @FormUrlEncoded
    @POST("PickingGroup/GetPickingOrdersForApp")
    Call<ApiResponse<List<PickingOrder>>> GetPickingOrders(@FieldMap Map<String, Object> params);

    // 获取线路拣货单
    @FormUrlEncoded
    @POST("PickingGroup/GetLinePickingOrdersByApp")
    Call<ApiResponse<List<PickingOrder>>> GetLinePickingOrders(@FieldMap Map<String, Object> params);

    // 获取商品拣货单
    @FormUrlEncoded
    @POST("PickingGroup/GetProductPickingOrdersByApp")
    Call<ApiResponse<List<PickingOrder>>> GetProductPickingOrders(@FieldMap Map<String, Object> params);

    // 完成拣货
//    @FormUrlEncoded
    @POST("PickingGroup/CompletePickOrderForApp")
    Call<ApiResponse<String>> CompletePickOrder(@Body CompletePickOrder completePickOrder);

    //线路维度完成拣货
//    @FormUrlEncoded
    @POST("PickingGroup/CompleteLinePickOrderForApp")
    Call<ApiResponse<String>> CompleteLinePickOrder(@Body CompletePickOrder completePickOrder);

//    // 获得预订单列表
//    @POST("B2B/GetBuyPreAppList")
//    Call<ApiResponse<BuyOrderPre>> GetBuyPreAppList(@Body PostParameters params);
//
//    // 添加货位号
//    @POST("B2B/UpdateShelfCodes")
//    Call<ApiResponse<String>> UpdateShelfCodes(@Body PostParameters params);
//
//    // 添加货位号
//    @FormUrlEncoded
//    @POST("B2B/GetBuyOrderPre")
//    Call<ApiResponse<GetBuyOrderDetail>> GetBuyOrderPre(@FieldMap Map<String, Object> params);
//
//    // 添加货位号
//    @POST("B2B/BuyOrderPreReveiveConfirm")
//    Call<ApiResponse<String>> BuyOrderPreReveiveConfirm(@Body PostParameters params);

//    // 收货明细接口
//    @FormUrlEncoded
//    @POST("Arrival/GetReceivedCompletedOrders")
//    Call<ApiResponse<ReceivedOrder>> GetReceivedCompletedOrders(@FieldMap Map<String, Object> params);
//
//    // 收货明细接口
//    @FormUrlEncoded
//    @POST("Arrival/GetReceivedCompletedDetails")
//    Call<ApiResponse<ReceivedOrderDetail>> GetReceivedCompletedDetails(@FieldMap Map<String, Object> params);
//
//    // 获取代购订单列表接口
//    @FormUrlEncoded
//    @POST("Arrival/GetReceiveOrders")
//    Call<ApiResponse<PendingOrder>> GetReceiveOrders(@FieldMap Map<String, Object> params);
//
//    // 开始收货接口
//    @FormUrlEncoded
//    @POST("Arrival/ReceiveOrderStart")
//    Call<ApiResponse<Void>> ReceiveOrderStart(@FieldMap Map<String, Object> params);
//
//    // 获取商品清单接口
//    @FormUrlEncoded
//    @POST("Arrival/GetReceiveOrderStartedDetails")
//    Call<ApiResponse<ProductInfo>> GetReceiveOrderStartedDetails(@FieldMap Map<String, Object> params);
//
//    // 获取商品清单接口
//    @FormUrlEncoded
//    @POST("Arrival/GetReceiveingOrderCount")
//    Call<ApiResponse<Integer>> GetReceiveingOrderCount(@FieldMap Map<String, Object> params);
//
//    // 完成收货接口
//    @POST("Arrival/ReceiveOrderCompleted")
//    Call<ApiResponse<JsonObject>> ReceiveOrderCompleted(@Body PostCompletedOrder completedOrder);

//
//    //修改密码
//    @FormUrlEncoded
//    @POST("User/UserEditPassword")
//    Call<ApiResponse<String>> UserEditPassword(@FieldMap Map<String, Object> params);
//
//
//    //仓库信息
//    @FormUrlEncoded
//    @POST("Warehouse/WarehouseGet")
//    Call<ApiResponse<Warehouse>> WarehouseGet(@FieldMap Map<String, Object> params);
//
//    //门店送货路线
//    @FormUrlEncoded
//    @POST("Warehouse/WarehouseLineShopGet")
//    Call<ApiResponse<WarehouseLine>> WarehouseLineShopGet(@FieldMap Map<String, Object> params);
//
//    //消息分页查询
//    @FormUrlEncoded
//    @POST("Warehouse/WarehouseMessageShopIsNew")
//    Call<ApiResponse<String>> WarehouseMessageShopIsNew(@FieldMap Map<String, Object> params);
//
//    //消息分页查询
//    @FormUrlEncoded
//    @POST("Warehouse/WarehouseMessageShopGetList")
//    Call<ApiResponse<WarehouseMessageShopGetListRespData>> WarehouseMessageShopGetList(@FieldMap Map<String, Object> params);
//
//
//    //订单取消
//    @POST("Order/OrderShopCancel")
//    Call<ApiResponse<String>> OrderShopCancel(@Body PostOrderCancel orderCancel);
//
//    //订单详情
//    @FormUrlEncoded
//    @POST("Order/vOrderGetAction")
//    Call<ApiResponse<OrderShopGetRespData>> OrderGetAction(@FieldMap Map<String, Object> params);
//
//    //订单跟踪
//    @FormUrlEncoded
//    @POST("Order/SaleOrderGetTrack")
//    Call<ApiResponse<SaleOrderGetTrackRespData>> SaleOrderGetTrack(@FieldMap Map<String, Object> params);
//
//    //订单查询(订单列表)
//    @FormUrlEncoded
//    @POST("Order/OrderQuery")
//    Call<ApiResponse<OrderShopQueryRespData>> OrderQuery(@FieldMap Map<String, Object> params);
//
//    //再次购买
//    @FormUrlEncoded
//    @POST("Order/OrderReBuy")
//    Call<ApiResponse<String>> OrderReBuy(@FieldMap Map<String, Object> params);
//
//    //确认收货
//    @FormUrlEncoded
//    @POST("Order/OrderPreFinished")
//    Call<ApiResponse<String>> OrderPreFinished(@FieldMap Map<String, Object> params);
//
//    //订单编辑（整单编辑）
//    @POST("Order/OrderShopEditAll")
//    Call<ApiResponse<String>> OrderShopEditAll(@Body PostOrderEditAll postOrderEditAll);
//
//    //订单编辑(单条编辑)
//    @POST("Order/OrderShopEditSingle")
//    Call<ApiResponse<String>> OrderShopEditSingle(@Body PostEditCart editCart);
//
//    //销售结算单据列表查询
//    @FormUrlEncoded
//    @POST("Order/SaleSettleGetList")
//    Call<ApiResponse<Bill>> SaleSettleGetList(@FieldMap Map<String, Object> params);
//
//    //销售结算单据明细查询
//    @FormUrlEncoded
//    @POST("Order/SaleSettleGetListActionGetModel")
//    Call<ApiResponse<BillDetails>> SaleSettleGetListActionGetModel(@FieldMap Map<String, Object> params);
//
//    //订单创建
//    @FormUrlEncoded
//    @POST("Order/OrderShopCreateBusiness")
//    Call<ApiResponse<String>> OrderShopCreateBusiness(@FieldMap Map<String, Object> params);
//
//    //用户是否存在未确认订单
//    @FormUrlEncoded
//    @POST("Order/OrderShopExistUnConfirmOrder")
//    Call<ApiResponse<String>> OrderShopExistUnConfirmOrder(@FieldMap Map<String, Object> params);
//
//
//    //获取分类商品
//    @FormUrlEncoded
//    @POST("WProducts/ProductWProductsGetToB2B")
//    Call<ApiResponse<ProductWProductsGetToB2BRespData>> ProductWProductsGetToB2B(@FieldMap Map<String, Object> params);
//
//    //商品详情
//    @FormUrlEncoded
//    @POST("WProducts/WProductsB2BDetailsGet")
//    Call<ApiResponse<ProductWProductsDetailsGetRespData>> WProductsB2BDetailsGet(@FieldMap Map<String, Object> params);
//
//
//    //首页数据
//    @FormUrlEncoded
//    @POST("WAdvertisement/WAdvertisementGetListModel")
//    Call<ApiResponse<List<WAdvertisementGetListModelRespData>>> WAdvertisementGetListModel(@FieldMap Map<String, Object> params);
//
//
//    //消息实体获取
//    @FormUrlEncoded
//    @POST("WAdvertisement/WarehouseMessageGetModel")
//    Call<ApiResponse<WarehouseMessage>> WarehouseMessageGetModel(@FieldMap Map<String, Object> params);
//
//
//    //获取购物车列表
//    @FormUrlEncoded
//    @POST("SaleCart/SaleCartGet")
//    Call<ApiResponse<SaleCartGetRespData>> SaleCartGet(@FieldMap Map<String, Object> params);
//
//    //删除购物车列表
//    @FormUrlEncoded
//    @POST("SaleCart/SaleCartDelete")
//    Call<ApiResponse<String>> SaleCartDelete(@FieldMap Map<String, Object> params);
//
//    //单行修改购物车商品
//    @POST("SaleCart/SaleCartEditSingle")
//    Call<ApiResponse<String>> SaleCartEditSingle(@Body PostEditCart editCart);
//
//    //计算购物车商品总数量
//    @FormUrlEncoded
//    @POST("SaleCart/SaleCartCount")
//    Call<ApiResponse<Integer>> SaleCartCount(@FieldMap Map<String, Object> params);
//
//    //获取运营分类
//    @FormUrlEncoded
//    @POST("ShopCategories/ShopCategoriesGet")
//    Call<ApiResponse<ShopCategoriesGetRespData>> ShopCategoriesGet(@FieldMap Map<String, Object> params);
//
//    //版本更新
//    @FormUrlEncoded
//    @POST("AppVersion/AppVersionUpdateGet")
//    Call<ApiResponse<AppVersionInfo>> AppVersionUpdateGet(@FieldMap Map<String, Object> params);
//
//    /**
//     * 修改密码
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("Arrival/UpdatePwd")
//    Call<ApiResponse> UpdatePwd(@FieldMap Map<String, Object> params);
//
//    /**
//     * 终止收货
//     * @param params
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("Arrival/ReceiveOrderStop")
//    Call<ApiResponse<String>> ReceiverOrderStop(@FieldMap Map<String, Object> params);
//
//    /**
//     * 上传签名内容
//     * @return
//     */
//    @POST("Arrival/BuyOrderSigns")
//    Call<ApiResponse<String>> SubOrderSigns(@Body PostSubOrderSigns postSubOrderSigns);
//
//    /**
//     * 查看签名内容
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("Arrival/GetBuyOrderSigns")
//    Call<ApiResponse<OrderSigns>> GetOrderSign(@FieldMap Map<String, Object> params);
//
//    /**
//     * 上传签名、现场图片
//     * @param parts
//     * @return
//     */
//    @Multipart
//    @POST("ImageApi/SaveVendorSignImages")
//    Call<String> SubmitSignImage(@PartMap Map<String, RequestBody> parts);
//
//    /**
//     * 查看二维码的
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("Arrival/GetBuyOrderQRCodeContent")
//    Call<ApiResponse<JsonObject>> GetBuyOrderQRCodeContent(@FieldMap Map<String, Object> params);
//
//    /**
//     * 打印接口
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("Arrival/AddAutoPrintQueue")
//    Call<ApiResponse<ArrayList<ApiResponse<String>>>> PrintOrder(@FieldMap Map<String, Object> params);
//
//    /**
//     * 过账接口
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("Arrival/PostingOrderCompleted")
//    Call<ApiResponse<String>> PostingOrderCompleted(@FieldMap Map<String, Object> params);
}
