import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { Box, Container, Grid, Stack, Typography } from "@mui/material";

import {
  ProductSort,
  ProductList,
  ProductFilterSidebar,
} from "../sections/@dashboard/products";

import { orderApi } from "../api/api-order";
import Profile from "../components/orderList/Profile";
import ProfileDetails from "../components/orderList/ProfileDetail";

// ----------------------------------------------------------------------

const useOrderData = (id) => {
  const [order, setOrder] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
    try {
      let order = await orderApi.getOrderList(id);
      // let food = await axios.get(`http://localhost:8080/foods/${id}`);
      setOrder(order.data);
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { order, loading };
};

export default function OrderListDetail(props) {
  const { id } = useParams();
  const { order, loading } = useOrderData(id);
  return loading ? (
    <></>
  ) : (
    <Box component="main" sx={{ flexGrow: 1, py: 8 }}>
      <Container maxWidth="lg">
        <Typography sx={{ mb: 3 }} variant="h4">
          Detail
        </Typography>
        <Grid container spacing={3}>
          <Grid item lg={4} md={6} xs={12}>
            <Profile order={order} />
          </Grid>
          <Grid item lg={8} md={6} xs={12}>
            <ProfileDetails order={order} />
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}
