import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { Box, Container, Grid, Stack, Typography } from "@mui/material";

import {
  ProductSort,
  ProductList,
  ProductFilterSidebar,
} from "../../sections/@dashboard/products";

import { userApi } from "../../api/api-user";
import Profile from "../../components/user/Profile";
import ProfileDetails from "../../components/user/ProfileDetail";
import UserProfileEdit from "../../components/manager/UerProfileEdit";
import Page404 from "../Page404";
import SearchNotFound from "../../components/SearchNotFound";
// ----------------------------------------------------------------------
export default function ManageEditUser() {
  const params = useParams();

  console.log(params);
  const [user, setUser] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async (userId) => {
    try {
      console.log(userId);
      let user = await userApi.getUser(params.id);
      setUser(user.data);
    } catch (error) {
      <Page404 />;
    } finally {
      setLoading(false);
    }
  };
  console.log("re", user);

  useEffect(() => {
    getData();
  }, []);

  return loading ? (
    <></>
  ) : (
    <Box component="main" sx={{ flexGrow: 1, py: 8 }}>
      <Container maxWidth="lg">
        <Typography sx={{ mb: 3 }} variant="h4">
          Edit User
        </Typography>
        <Grid container spacing={3}>
          <Grid item lg={4} md={6} xs={12}>
            <Profile user={user} />
          </Grid>
          <Grid item lg={8} md={6} xs={12}>
            <UserProfileEdit user={user} />
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}
