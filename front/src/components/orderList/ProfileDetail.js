import { useState, useEffect } from 'react';
import { fCurrency } from '../../utils/formatNumber';
import {orderApi} from '../../api/api-order';
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

const useItem = (id) => {
  const {orderItem, setOrderItem} = useState([]);
  const {loading, setLoading} = useState(true);
  const getItems = async () => {
    try {
        let result = await orderApi.getOrderItemList(id);
        console.log(result);
        setOrderItem(result.data);
    } catch (error) {
        alert("가져올 음식 데이터가 없습니다.")
    } finally {
        setLoading(false);
    }
  }
  useEffect(() => {
    getItems();
  },[])
  return {orderItem, loading};
}

const ProfileDetails = ({order}, props) => {
  const {orderItem, loading} = useItem(order.id);
  console.log(orderItem);
  return (
      <Card>
        <CardHeader
          subheader="The information of OrderList"
          title="장바구니 Info"
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
                value={order.name}
                variant="outlined"
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="address"
                name="address"
                required
                value={order.address}
                variant="outlined"
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                fullWidth
                label="Phone"
                name="phone"
                required
                value={order.phoneNumber}
                variant="outlined"
              />
            </Grid>
            {/* <Grid item md={6} xs={12} >
              <TextField
                fullWidth
                label="type"
                name="type"
                value={items.subCategory}
                variant="outlined"
              />
            </Grid> */}
            {/* <Grid item md={6}xs={12}>
              <TextField
                fullWidth
                label="description"
                name="description"
                required
                value={items.description}
                variant="outlined"
              />
            </Grid> */}
          </Grid>
        </CardContent>
        <Divider />
      </Card>
    
  );
};

export default ProfileDetails;