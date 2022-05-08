import { useReducer, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fCurrency } from "../../utils/formatNumber";
import Fab from "@mui/material/Fab";
import EditIcon from "@mui/icons-material/Edit";
import { UserMoreMenu } from "../../sections/user";
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
import Page404 from "../../pages/Page404";

const ProfileDetails = ({ user }) => {
  const navigate = useNavigate();
  const [changeUser, setChangeUser] = useState({
    userId: user.userId,
    email: user.email,
    password: user.password,
    name: "",
    address: "",
    phoneNumber: "",
    createdAt: user.createdAt,
  });
  // const [changeUser, setChangeUser] = useState({ user });
  const onChangeName = (e) => {
    setChangeUser({ ...changeUser, name: e.target.value });
    console.log(changeUser);
  };
  const onChangeAddress = (e) => {
    setChangeUser({ ...changeUser, address: e.target.value });
    console.log(changeUser);
  };
  const onChangePhoneNumber = (e) => {
    setChangeUser({ ...changeUser, phoneNumber: e.target.value });
    console.log(changeUser);
  };

  const onSubmit = async () => {
    try {
      console.log(changeUser);
      let user = await userApi.updateUser(changeUser);
      console.log(user);
      navigate("/manager/users");
    } catch {
      <Page404 />;
    }
  };

  // console.log(user);
  return (
    <Card>
      <Box display="flex" justifyContent="space-between">
        <CardHeader
          subheader="The information of User"
          title={`${user.name} `}
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
              defaultValue={user.name}
              onChange={onChangeName}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="Address"
              name="address"
              required
              defaultValue={user.address}
              onChange={onChangeAddress}
              variant="outlined"
            />
          </Grid>
          <Grid item md={6} xs={12}>
            <TextField
              fullWidth
              label="Phone"
              name="phone"
              required
              defaultValue={user.phoneNumber}
              onChange={onChangePhoneNumber}
              variant="outlined"
            />
          </Grid>
        </Grid>
      </CardContent>
      <Divider />
      <Box
        sx={{
          display: "flex",
          justifyContent: "flex-end",
          p: 2,
        }}
      >
        <Button onClick={onSubmit} color="primary" variant="contained">
          내용 수정!
        </Button>
      </Box>
    </Card>
  );
};

export default ProfileDetails;
