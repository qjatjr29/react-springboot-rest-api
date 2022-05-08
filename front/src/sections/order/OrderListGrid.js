import PropTypes from 'prop-types';
import { Box, Grid, Card,
    Table,
    Stack,
    Avatar,
    Button,
    Checkbox,
    TableRow,
    TableBody,
    TableCell,
    Container,
    Typography,
    TableContainer,
    TablePagination, } from '@mui/material';
import { useState, useEffect } from 'react';
import {orderApi} from '../../api/api-order';
import {foodApi} from '../../api/api-foods';
import SubCard from '../../components/orderList/SubCard';
import MainCard from '../../components/orderList/MainCard';
import SecondaryAction from '../../components/orderList/CardSecondaryAction';
import { gridSpacing } from '../../components/orderList/constant';

const ColorBox = ({ bgcolor, title, data, dark }) => (
    <>
        <Card sx={{ mb: 3 }}>
            <Box
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    py: 4.5,
                    bgcolor,
                    color: dark ? 'grey.800' : '#ffffff'
                }}
            >
                {title && (
                    <Typography variant="subtitle1" color="inherit">
                        {title}
                    </Typography>
                )}
                {!title && <Box sx={{ p: 1.15 }} />}
            </Box>
        </Card>
        {data && (
            <Grid container justifyContent="space-between" alignItems="center">
                <Grid item>
                    <Typography variant="subtitle2">{data.label}</Typography>
                </Grid>
                <Grid item>
                    <Typography variant="subtitle1" sx={{ textTransform: 'uppercase' }}>
                        {data.color}
                    </Typography>
                </Grid>
            </Grid>
        )}
    </>
);

ColorBox.propTypes = {
    bgcolor: PropTypes.string,
    title: PropTypes.string,
    data: PropTypes.object.isRequired,
    dark: PropTypes.bool
};


const useGetItems = (orderItems) => {
    const [foodList, setFoodList] = useState([]);
    console.log(orderItems);
    const getFoods = async (orderItems)=>{
        var list = [];
        for(var i = 0; i < orderItems.length; i++) {
            console.log(orderItems[i]);
            let food = await foodApi.getFood(orderItems[i].foodId);
            console.log(food);
            list.push(food.data);
        }
        // orderItems.forEach(async (orderItem) => {
        //     let food = await foodApi.getFood(orderItem.foodId);
        //     console.log(food);
        //     list.push(food.data);
        // })
        setFoodList(list);
    }
    useEffect = (()=>{
        getFoods(orderItems);
    },[]);
    return {foodList};
}

export default function OrderListGrid({ order }) {
    console.log(order);
    const {foodList} = useGetItems(order.orderItems);
    console.log(foodList);
    return (
     <Grid container spacing={gridSpacing}>
                <Grid item xs={12}>
                    <SubCard title={order.name} address={order.address} phoneNumber={order.phoneNumber}>
                        <Grid container spacing={gridSpacing}>
                            {foodList && foodList.forEach((food)=> {
                                console.log(food);
                                <>
                                    <Grid item xs={12} sm={6} md={4} lg={2}>
                                        <ColorBox bgcolor="primary.light" data={{ label: 'Blue-50', color: '#E3F2FD' }} title="primary[200]" dark />
                                    </Grid>
                                    <Grid item xs={12} sm={6} md={4} lg={2}>
                                        <ColorBox bgcolor="primary.200" data={{ label: 'Blue-200', color: '#90CAF9' }} title="primary[200]" dark />
                                    </Grid>
                                    <Grid item xs={12} sm={6} md={4} lg={2}>
                                        <ColorBox bgcolor="primary.main" data={{ label: 'Blue-500', color: '#2196F3' }} title="primary.main" />
                                    </Grid>
                                    <Grid item xs={12} sm={6} md={4} lg={2}>
                                        <ColorBox bgcolor="primary.dark" data={{ label: 'Blue-600', color: '#1E88E5' }} title="primary.dark" />
                                    </Grid>
                                    <Grid item xs={12} sm={6} md={4} lg={2}>
                                        <ColorBox bgcolor="primary.800" data={{ label: 'Blue-800', color: '#1565C0' }} title="primary[800]" />
                                    </Grid> 
                               </> 
                            })}
                        </Grid>
                    </SubCard>
         </Grid>
    </Grid>
    )}