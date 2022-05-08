import api from "./api-base";

export const userApi = {
  getUsers: () => api.get("users/all"),
  getUser: (email) => api.get(`users/${email}`),
  getManager: (email) => api.get(`managers/${email}`),
  login: (email, password) =>
    //    api.get(`users/login/${email}/${password}`),
    api.get(`users/login?email=${email}&password=${password}`),
  loginManager: (email, password) =>
    //    api.get(`users/login/${email}/${password}`),
    api.get(`managers/login?email=${email}&password=${password}`),
  insertUser: (user) => api.post("users", user),
  updateUser: (user) => api.put("users", user),
  deleteUser: (id) => api.delete(`users/${id}`),
};
