import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { Box, Container, Grid, Stack, Typography } from "@mui/material";

import {
  ProductSort,
  ProductList,
  ProductFilterSidebar,
} from "../sections/@dashboard/products";

import { userApi } from "../api/api-user";
import Profile from "../components/user/Profile";
import ProfileDetails from "../components/user/ProfileDetail";
import Page404 from "./Page404";
import SearchNotFound from "../components/SearchNotFound";
// ----------------------------------------------------------------------

const useUserData = () => {
  const [user, setUser] = useState([]);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
    try {
      const userId = sessionStorage.getItem("user");
      let user = await userApi.getUser(userId);
      // let food = await axios.get(`http://localhost:8080/foods/${id}`);
      setUser(user.data);
    } catch (error) {
      <Page404 />;
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getData();
  }, []);
  return { user, loading };
};

export default function UserInfo() {
  const { user, loading } = useUserData();

  return loading ? (
    <></>
  ) : (
    <Box component="main" sx={{ flexGrow: 1, py: 8 }}>
      <Container maxWidth="lg">
        <Typography sx={{ mb: 3 }} variant="h4">
          User Info
        </Typography>
        <Grid container spacing={3}>
          <Grid item lg={4} md={6} xs={12}>
            <Profile user={user} />
          </Grid>
          <Grid item lg={8} md={6} xs={12}>
            <ProfileDetails user={user} />
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
}
