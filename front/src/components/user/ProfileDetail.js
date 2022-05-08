import { useState } from "react";
import { fCurrency } from "../../utils/formatNumber";
import Fab from "@mui/material/Fab";
import EditIcon from "@mui/icons-material/Edit";
import { UserMoreMenu } from "../../sections/user";
import { useNavigate } from "react-router-dom";
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

import { userApi } from "../../api/api-user";

const ProfileDetails = ({ user }, props) => {
  const navigate = useNavigate();

  const handleDelete = async (id) => {
    try {
      let deleteUser = await userApi.deleteUser(id);
      console.log(deleteUser);
      navigate("/manager/users");
    } catch (error) {
      console.log("fail");
    }
  };

  return (
    <Card>
      <Box display="flex" justifyContent="space-between">
        <CardHeader
          subheader="The information of User"
          title={`${user.name} Info`}
        />
        <UserMoreMenu
          // onUserEdit={handleEdit}
          onUserDelete={handleDelete}
          user={user}
        />
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
              value={user.name}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="Address"
              name="address"
              required
              value={user.address}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="Phone"
              name="phone"
              required
              value={user.phoneNumber}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="CreatedAt"
              name="createdAt"
              value={user.createdAt}
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
