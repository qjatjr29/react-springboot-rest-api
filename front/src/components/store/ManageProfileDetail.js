import { useState } from "react";
import { fCurrency } from "../../utils/formatNumber";
import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  Divider,
  Grid,
  TextField,
} from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { ManageStoreMoreMenu } from "../../sections/manager";
import { storeApi } from "../../api/api-store";
import { foodApi } from "../../api/api-foods";

const ProfileDetails = ({ store }, props) => {
  const navigate = useNavigate();
  const handleDelete = async (storeId) => {
    try {
      let deleteData = await storeApi.deleteStore(storeId);
      //   let deleteFoods = await storeApi.getFoods(storeId);
      //   deleteFoods.data.map(async (food) => {
      //     let food = await foodApi.deleteFood(food.ic);
      //   });
      console.log(deleteData.data);
      navigate("/manager/stores");
    } catch (error) {}
  };
  return (
    <Card>
      <Box display="flex" justifyContent="space-between">
        <CardHeader subheader="The information of Store" title="Store Info" />
        <ManageStoreMoreMenu store={store} onStoreDelete={handleDelete} />
      </Box>
      <Divider />
      <CardContent>
        <Grid container spacing={3}>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="Name"
              name="Name"
              required
              value={store.name}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="Address"
              name="Address"
              required
              value={store.address}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="Category"
              name="category"
              required
              value={store.category}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="phoneNumber"
              name="phoneNumber"
              value={store.phoneNumber}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="createdAt"
              name="createdAt"
              required
              value={store.createdAt}
              variant="outlined"
            />
          </Grid>
        </Grid>
      </CardContent>
      <Divider />
    </Card>
  );
};

export default ProfileDetails;
