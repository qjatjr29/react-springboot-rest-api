import { useEffect, useState } from "react";

// material
import { Container, Stack, Typography, Box, Button } from "@mui/material";
// components
import Page from "../components/Page";
import { Helmet } from "react-helmet-async";
import {
  ProductSort,
  ProductList,
  ProductCartWidget,
  ProductFilterSidebar,
} from "../sections/@dashboard/products";
import { UserList } from "../sections/user";
// mock
import PRODUCTS from "../_mock/products";
import { userApi } from "../api/api-user";
import axios from "axios";
import ContentHeader from "../components/ManageContentHeader";
// ----------------------------------------------------------------------

const useUserData = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
    try {
      let users = await userApi.getUsers();
      setUsers(users.data);
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { users, loading };
};

export default function Stores() {
  const { users, loading } = useUserData();

  return loading ? (
    <></>
  ) : (
    // <Page title="Foods">
    <Container>
      <ContentHeader title="Manage User" text="추가" type="user" />

      <Stack
        direction="row"
        flexWrap="wrap-reverse"
        alignItems="center"
        justifyContent="flex-end"
        sx={{ mb: 5 }}
      >
        <Stack
          direction="row"
          spacing={1}
          flexShrink={0}
          sx={{ my: 1 }}
        ></Stack>
      </Stack>

      {/* <ProductList products={PRODUCTS} /> */}
      <UserList users={users} />
    </Container>
    // </Page>
  );
}
