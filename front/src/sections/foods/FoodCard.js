import PropTypes from 'prop-types';
import { useEffect, useState } from 'react';
import { storeApi } from '../../api/api-store';
import { Link as RouterLink } from 'react-router-dom';
// material
import { Box, Card, Link, Typography, Stack } from '@mui/material';
import { styled } from '@mui/material/styles';
// utils
import { fCurrency } from '../../utils/formatNumber';
import { fDate } from '../../utils/formatTime';
// components
import Label from '../../components/Label';
import { ColorPreview } from '../../components/color-utils';

// ----------------------------------------------------------------------

const ProductImgStyle = styled('img')({
  top: 0,
  width: '100%',
  height: '100%',
  objectFit: 'cover',
  position: 'absolute',
});

// ----------------------------------------------------------------------

ShopProductCard.propTypes = {
  product: PropTypes.object,
};

const useStoreName = (storeId) => {
  const [storeName, setStoreName] = useState(null);
  const [loading, setLoading] = useState(true);

  const getData = async () => {
      try {
          let name = await storeApi.getStore(storeId);
          // let foods = await axios.get("http://localhost:8080/foods/all");
          setStoreName(name.data.name);
      } catch (error) {
          alert("가져올 음식 데이터가 없습니다.")
      } finally {
          setLoading(false);
      }
  }

  useEffect(() => {
      getData();
  },[])
  return {storeName, loading};
}

export default function ShopProductCard({ product }) {

  const { foodId, name, image, price, storeId } = product;
  const {storeName, loading} = useStoreName(storeId);
  return (
    loading ? (
        <>
        </>
    ) : (
      <Card>
      <Box sx={{ pt: '100%', position: 'relative' }}>
        <ProductImgStyle alt={name} src={image} />
      </Box>

      <Stack spacing={2} sx={{ p: 3 }}>
        <Link to={{ pathname : `/user/foods/${foodId}`}} color="inherit" underline="hover" component={RouterLink}>
          <Typography variant="subtitle2" noWrap>
            {name}
          </Typography>
        </Link>

        <Stack direction="row" alignItems="center" justifyContent="space-between">
          <Typography variant="subtitle1">
          <Typography
              component="span"
              variant="body1"
              sx={{
                color: 'text.disabled',
                // textDecoration: 'line-through',
              }}
            >
              {storeName}
            </Typography>
            &nbsp;
            {fCurrency(price)}
          </Typography>
        </Stack>
      </Stack>
    </Card>
    )
    
  );
}
