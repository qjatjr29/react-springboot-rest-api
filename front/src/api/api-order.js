import api from "./api-base";

export const orderApi = {
  getOrders: () => api.get("orders"),
  getOrderLists: () => api.get("orders/list"),
  getUserOrderLists: (userId) => api.get(`orders/list/orders/${userId}`),
  getOrder: (orderId) => api.get(`orders/${orderId}`),
  getOrderList: (orderId) => api.get(`orders/list/${orderId}`),
  getOrderItemList: (orderId) => api.get(`orders/orderItems/${orderId}`),
  insertOrder: (order, totalPrice, items) => {
    console.log("order api: ", order, totalPrice, items);
    return api.post("orders", {
      name: order.name,
      address: order.address,
      phoneNumber: order.phoneNumber,
      userId: order.userId,
      price: totalPrice,
      orderItems: items.map((v) => ({
        foodId: v.foodId,
        category: v.category,
        price: v.price,
        quantity: v.count,
      })),
    });
  },
  insertOrderList: (order, totalPrice, items) =>
    api.post("orders/list", {
      name: order.name,
      address: order.address,
      phoneNumber: order.phoneNumber,
      price: totalPrice,
      orderItems: items.map((v) => ({
        foodId: v.foodId,
        category: v.category,
        price: v.price,
        quantity: v.count,
      })),
    }),
  updateOrder: () => api.put("orders"),
  deleteOrder: (orderId) => api.delete(`orders/${orderId}`),
  deleteOrderList: (orderId) => api.delete(`orders/list/${orderId}`),
};
