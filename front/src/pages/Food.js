import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { Box, Container, Grid, Stack, Typography } from "@mui/material";

import {
  ProductSort,
  ProductList,
  ProductFilterSidebar,
} from "../sections/@dashboard/products";

import { foodApi } from "../api/api-foods";
import Profile from "../components/food/Profile";
import ProfileDetails from "../components/food/ProfileDetail";
import Page404 from "./Page404";
import SearchNotFound from "../components/SearchNotFound";
// ----------------------------------------------------------------------

const useFoodData = (id) => {
  const [food, setFood] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
    try {
      let food = await foodApi.getFood(id);
      // let food = await axios.get(`http://localhost:8080/foods/${id}`);
      setFood(food.data);
    } catch (error) {
      <Page404 />;
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { food, loading };
};

export default function Food(props) {
  const { id } = useParams();

  const [openFilter, setOpenFilter] = useState(false);
  const { food, loading } = useFoodData(id);

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

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
            <Profile food={food} />
          </Grid>
          <Grid item lg={8} md={6} xs={12}>
            <ProfileDetails food={food} />
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}
