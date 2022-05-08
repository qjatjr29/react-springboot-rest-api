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

const states = [
  {
    value: 'alabama',
    label: 'Alabama'
  },
  {
    value: 'new-york',
    label: 'New York'
  },
  {
    value: 'san-francisco',
    label: 'San Francisco'
  }
];

const ProfileDetails = ({food}, props) => {
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
                value={food.name}
                variant="outlined"
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="price"
                name="price"
                required
                value={fCurrency(food.price)}
                variant="outlined"
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="Category"
                name="category"
                required
                value={food.category}
                variant="outlined"
              />
            </Grid>
            <Grid item md={6} xs={12} >
              <TextField
                fullWidth
                label="type"
                name="type"
                value={food.subCategory}
                variant="outlined"
              />
            </Grid>
            <Grid item md={6}xs={12}>
              <TextField
                fullWidth
                label="description"
                name="description"
                required
                value={food.description}
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