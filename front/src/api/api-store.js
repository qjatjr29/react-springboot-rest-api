import api from "./api-base";

export const storeApi = {
  getStores: () => api.get("stores/all"),
  getStore: (storeId) => api.get(`stores/${storeId}`),
  getFoods: (storeId) => api.get(`foods/store/${storeId}`),
  getStoreByCategory: (category) => api.get(`stores/category/${category}`),
  insertStore: (store) => api.post("stores", store),
  updateStore: () => api.put("stores"),
  deleteStore: (storeId) => api.delete(`stores/${storeId}`),
};
