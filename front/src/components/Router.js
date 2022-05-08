import { Routes, Route, useRoutes, useParams } from "react-router-dom";
import Foods from "../pages/Foods";
import Food from "../pages/Food";
import Home from "../pages/Home";
import Stores from "../pages/Stores";
import Store from "../pages/Store";
import Category from "../pages/Category";
import StoreFoods from "../pages/StoreFoods";
import OrderLists from "../pages/OrderLists";
import Orders from "../pages/Orders";
import UserInfo from "../pages/UserInfo";
import StoreCategory from "../pages/StoreCategory";
import OrderListsDetail from "../pages/OrderListsDetail";
import UserLogin from "../pages/UserLogin";
import UserRegister from "../pages/user/UserRegister";

import DashboardLayout from "../layouts/dashboard";

import ManagerRegister from "../pages/manager/ManagerRegister";
import ManageDashboardLayout from "../layouts/manager";
import ManageUsers from "../pages/ManageUsers";
import ManagerLogin from "../pages/ManagerLogin";
import ManageStores from "../pages/ManageStores";
import ManageUser from "../pages/ManageUser";
import ManageStore from "../pages/manager/ManageStore";
import ManageEditUser from "../pages/manager/ManageEditUser";
import AddFood from "../pages/manager/AddFood";
import AddUser from "../pages/manager/AddUser";
import AddStore from "../pages/manager/AddStore";
import ManagerInfo from "../pages/manager/ManagerInfo";
export default function Router() {
  return useRoutes([
    {
      path: "/",
      children: [
        { path: "", element: <Home /> },
        { path: "/user/login", element: <UserLogin /> },
        { path: "/manager/login", element: <ManagerLogin /> },
        { path: "/user/register", element: <UserRegister /> },
        { path: "/manager/register", element: <ManagerRegister /> },
      ],
    },
    {
      path: "/user",
      element: <DashboardLayout />,
      children: [
        { path: "", element: <Stores /> },
        { path: "info", element: <UserInfo /> },
        { path: "foods", element: <Foods /> },
        { path: "stores", element: <Stores /> },
        { path: "category", element: <Category /> },
        { path: "orders", element: <Orders /> },
        { path: "orderLists", element: <OrderLists /> },
        { path: "orderLists/:id", element: <OrderListsDetail /> },
        { path: "orders", element: <OrderLists /> },
        { path: "foods/:id", element: <Food /> },
        { path: "stores/:id", element: <Store /> },
        { path: "stores/category/:category", element: <StoreCategory /> },
        { path: "stores/:id/foods", element: <StoreFoods /> },
      ],
    },

    {
      path: "/manager",
      element: <ManageDashboardLayout />,
      children: [
        { path: "", element: <ManagerInfo /> },
        { path: "info", element: <ManagerInfo /> },
        { path: "stores", element: <ManageStores /> },
        { path: "users", element: <ManageUsers /> },
        { path: "users/:id", element: <ManageUser /> },
        { path: "stores/:id", element: <ManageStore /> },
        { path: "users/edit/:id", element: <ManageEditUser /> },
        { path: "addUser", element: <AddUser /> },
        { path: "addStore", element: <AddStore /> },
        { path: "addFood/:id", element: <AddFood /> },
      ],
    },
  ]);
}
