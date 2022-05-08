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
import { ManageStoreList } from "../sections/stores";
// mock
import PRODUCTS from "../_mock/products";
import { storeApi } from "../api/api-store";
import axios from "axios";
import ContentHeader from "../components/ManageContentHeader";

// ----------------------------------------------------------------------

const useStoreData = () => {
  const [stores, setStores] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
    try {
      let stores = await storeApi.getStores();
      // let foods = await axios.get("http://localhost:8080/foods/all");
      setStores(stores.data);
    } catch (error) {
      alert("가져올 가게 데이터가 없습니다.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { stores, loading };
};

export default function Stores() {
  const [openFilter, setOpenFilter] = useState(false);
  const { stores, loading } = useStoreData();

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
      <ContentHeader title="Stores" text="추가" type="store" />
      <Stack
        direction="row"
        flexWrap="wrap-reverse"
        alignItems="center"
        justifyContent="flex-end"
        sx={{ mb: 5 }}
      ></Stack>

      {/* <ProductList products={PRODUCTS} /> */}
      <ManageStoreList stores={stores} />
    </Container>
    // </Page>
  );
}
