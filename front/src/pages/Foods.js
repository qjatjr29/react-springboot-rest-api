import { useEffect, useState } from "react";
// material
import { Container, Stack, Typography } from "@mui/material";
// components
import Page from "../components/Page";
import { Helmet } from "react-helmet-async";
import {
  ProductSort,
  ProductList,
  ProductCartWidget,
  ProductFilterSidebar,
} from "../sections/@dashboard/products";
import { FoodSort, FoodList } from "../sections/foods";
// mock
import PRODUCTS from "../_mock/products";
import { foodApi } from "../api/api-foods";
import axios from "axios";

// ----------------------------------------------------------------------

const useFoodData = () => {
  const [foods, setFoods] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
    try {
      let foods = await foodApi.getFoods();
      // let foods = await axios.get("http://localhost:8080/foods/all");
      setFoods(foods.data);
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { foods, loading };
};

export default function Foods() {
  const [openFilter, setOpenFilter] = useState(false);
  const { foods, loading } = useFoodData();

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

  return loading ? (
    <></>
  ) : (
    // <Page title="Foods">
    <Container>
      <Typography variant="h4" sx={{ mb: 5 }}>
        Foods
      </Typography>

      <Stack
        direction="row"
        flexWrap="wrap-reverse"
        alignItems="center"
        justifyContent="flex-end"
        sx={{ mb: 5 }}
      >
        <Stack direction="row" spacing={1} flexShrink={0} sx={{ my: 1 }}>
          <ProductFilterSidebar
            isOpenFilter={openFilter}
            onOpenFilter={handleOpenFilter}
            onCloseFilter={handleCloseFilter}
          />
          <ProductSort />
        </Stack>
      </Stack>

      {/* <ProductList products={PRODUCTS} /> */}
      <FoodList foods={foods} />
    </Container>
    // </Page>
  );
}
