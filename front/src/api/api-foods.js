import api from "./api-base";

export const foodApi = {
  getFoods: () => api.get("foods/all"),
  getFood: (foodId) => api.get(`foods/${foodId}`),
  insertFood: (food) => api.post("foods", food),
  updateFood: () => api.put("foods"),
  deleteFood: (foodId) => api.delete(`foods/${foodId}`),
};
