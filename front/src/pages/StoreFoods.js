import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.css";
import "../App.css";
import { Box, Container, Grid, Stack, Typography } from "@mui/material";

import { storeApi } from "../api/api-store";
import { orderApi } from "../api/api-order";
import api from "../api/api-base";
import Profile from "../components/food/Profile";
import StoreFoodProfileDetail from "../components/store/StoreFoodProfileDetail";

import { FoodList } from "../components/FoodList";
import { OrderSummary } from "../components/OrderSummary";

// ----------------------------------------------------------------------

const useFoodData = (id) => {
  const [foods, setFoods] = useState([]);
  const [loading, setLoading] = useState(true);
  const [store, setStore] = useState(null);

  const getData = async () => {
    try {
      let foods = await storeApi.getFoods(id);
      setFoods(foods.data);
      let store = await storeApi.getStore(id);
      setStore(store.data.name);
    } catch (error) {
      alert("가져올 음식 데이터가 없습니다.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { foods, store, loading };
};

export default function Food(props) {
  const { id } = useParams();
  const { foods, store, loading } = useFoodData(id);
  const [items, setItems] = useState([]);
  const navigate = useNavigate();

  const handleAddClicked = (foodId) => {
    const food = foods.find((v) => v.foodId === foodId);
    const found = items.find((v) => v.foodId === foodId);
    const updatedItems = found
      ? items.map((v) =>
          v.foodId === foodId ? { ...v, count: v.count + 1 } : v
        )
      : [...items, { ...food, count: 1 }];
    setItems(updatedItems);
  };
  const handleOrderSubmit = (order, totalPrice) => {
    if (items.length === 0) {
      alert("음식을 추가해 주세요");
    } else {
      try {
        orderApi.insertOrder(order, totalPrice, items);
      } catch (e) {
        alert("서버 장애");
        console.error(e);
      } finally {
        alert("주문 완료!");
        navigate("/user/orders");
        // window.location.reload();
      }
    }
  };
  const handleOrderListSubmit = (order, totalPrice) => {
    if (items.length === 0) {
      alert("음식을 추가해 주세요");
    } else {
      try {
        orderApi.insertOrderList(order, totalPrice, items);
      } catch (e) {
        alert("서버 장애");
        console.error(e);
      } finally {
        alert("주문 완료!");
        navigate("/user/orders");
        // window.location.reload();
      }
    }
  };
  return loading ? (
    <></>
  ) : (
    <div className="container-fluid">
      <div className="row justify-content-center m-4">
        <h1 className="text-center">{store}</h1>
      </div>
      <div className="card">
        <div className="row">
          <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
            <FoodList foods={foods} onAddClick={handleAddClicked} />
          </div>
          <div className="col-md-4 summary p-4">
            <OrderSummary
              items={items}
              onOrderListSubmit={handleOrderListSubmit}
              onOrderSubmit={handleOrderSubmit}
            />
          </div>
        </div>
      </div>
    </div>
  );
}
