import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
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
import { StoreList } from "../sections/stores";
// mock
import PRODUCTS from "../_mock/products";
import { storeApi } from "../api/api-store";
import axios from "axios";
import Page404 from "./Page404";
import SearchNotFound from "../components/SearchNotFound";
// ----------------------------------------------------------------------
const useStoreData = (category) => {
  const [stores, setStores] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
    try {
      let stores = await storeApi.getStoreByCategory(category);
      // let foods = await axios.get("http://localhost:8080/foods/all");
      setStores(stores.data);
    } catch (error) {
      <SearchNotFound />;
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { stores, loading };
};

export default function StoreCategory(props) {
  const { category } = useParams();
  const [openFilter, setOpenFilter] = useState(false);
  const { stores, loading } = useStoreData(category);

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

  return loading ? (
    <></>
  ) : // <Page title="Foods">
  stores.length > 0 ? (
    <Container>
      <Typography variant="h4" sx={{ mb: 5 }}>
        Stores
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
      <StoreList stores={stores} />
    </Container>
  ) : (
    <Page404 />
  );
  // </Page>
}
