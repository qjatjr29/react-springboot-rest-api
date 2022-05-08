import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { Box, Container, Grid, Stack, Typography } from "@mui/material";
import { storeApi } from "../../api/api-store";
import "bootstrap/dist/css/bootstrap.css";
import StoreProfile from "../../components/store/StoreProfile";
import ManageProfileDetail from "../../components/store/ManageProfileDetail";
import Page404 from "../../pages/Page404";
import { FoodList } from "../../components/manager/FoodList";
// ----------------------------------------------------------------------

export default function Food(props) {
  const { id } = useParams();

  const [store, setStore] = useState(null);

  const getStoreData = async () => {
    try {
      let store = await storeApi.getStore(id);
      setStore(store.data);
      console.log(store);
    } catch (error) {
      <Page404 />;
      alert("가져올 가게 데이터가 없습니다.");
    }
  };

  const [foods, setFoods] = useState([]);
  const [loading, setLoading] = useState(true);
  const getFoods = async (id) => {
    console.log(id);
    try {
      let food = await storeApi.getFoods(id);
      setFoods(food.data);
    } catch (error) {
      <Page404 />;
      alert("가져올 음식 데이터가 없습니다.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getStoreData();
    // console.log(store.storeId);
  }, []);

  useEffect(() => {
    if (store !== null) getFoods(store.storeId);
  }, store);

  return loading ? (
    <></>
  ) : (
    <>
      <Box component="main" sx={{ flexGrow: 1, py: 8 }}>
        <Container maxWidth="lg">
          <Typography sx={{ mb: 3 }} variant="h4">
            Store Info
          </Typography>
          <Grid container spacing={3}>
            <Grid item lg={4} md={6} xs={12}>
              <StoreProfile store={store} />
            </Grid>
            <Grid item lg={8} md={6} xs={12}>
              <ManageProfileDetail store={store} />
            </Grid>
          </Grid>
        </Container>
      </Box>
      {/* <Box component="main" sx={{ flexGrow: 1, py: 8 }}> */}
      <FoodList foods={foods} store={store} />
      {/* </Box> */}
    </>
  );
}
