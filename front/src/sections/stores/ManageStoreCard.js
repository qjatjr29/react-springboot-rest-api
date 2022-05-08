import PropTypes from "prop-types";
import { Link as RouterLink } from "react-router-dom";
// material
import { Box, Card, Link, Typography, Stack } from "@mui/material";
import { styled } from "@mui/material/styles";
// utils
import { fCurrency } from "../../utils/formatNumber";
// components
import Label from "../../components/Label";
import { ColorPreview } from "../../components/color-utils";

// ----------------------------------------------------------------------

const ProductImgStyle = styled("img")({
  top: 0,
  width: "100%",
  height: "100%",
  objectFit: "cover",
  position: "absolute",
});

// ----------------------------------------------------------------------

ShopProductCard.propTypes = {
  product: PropTypes.object,
};

export default function ShopProductCard({ product }) {
  const { storeId, name, image, address } = product;
  return (
    <Card>
      <Box sx={{ pt: "100%", position: "relative" }}>
        <ProductImgStyle alt={name} src={image} />
      </Box>

      <Stack spacing={2} sx={{ p: 3 }}>
        <Link
          to={{ pathname: `/manager/stores/${storeId}` }}
          color="inherit"
          underline="hover"
          component={RouterLink}
        >
          <Typography variant="subtitle1" noWrap>
            {name}
          </Typography>
        </Link>

        <Stack
          direction="row"
          alignItems="center"
          justifyContent="space-between"
        >
          <Typography variant="subtitle2">
            &nbsp;
            {/* {fCurrency(price)} */}
            {address}
          </Typography>
        </Stack>
      </Stack>
    </Card>
  );
}
