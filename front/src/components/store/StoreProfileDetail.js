import { useState } from 'react';
import { fCurrency } from '../../utils/formatNumber';
import {
  Box,
  Button,
  Card,
  CardContent,
  CardHeader,
  Divider,
  Grid,
  TextField
} from '@mui/material';
import {Link} from 'react-router-dom';


const ProfileDetails = ({store}, props) => {
  return (
      <Card>
        <CardHeader
          subheader="The information of Food"
          title="Food Info"
        />
        <Divider />
        <CardContent>
          <Grid container spacing={3}>
            <Grid  item md={6} xs={12} >
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
            <Grid item md={6} xs={12} >
              <TextField
                fullWidth
                label="phoneNumber"
                name="phoneNumber"
                value={store.phoneNumber}
                variant="outlined"
              />
            </Grid>
            <Grid item md={6}xs={12}>
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
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'flex-end',
            p: 2
          }}
        >
          <Link to={{pathname:`/user/stores/${store.storeId}/foods`}}>
            <Button color="primary" variant="contained">
              주문하러 가기!
            </Button>
          </Link>
        </Box>
      </Card>
    
  );
};

export default ProfileDetails;