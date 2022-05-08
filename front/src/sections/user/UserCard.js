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
  const { userId, name, address, phoneNumber, createdAt } = product;
  return (
    <Card>
      <Link
        to={{ pathname: `/manager/users/${userId}` }}
        color="inherit"
        underline="hover"
        component={RouterLink}
      >
        <Box sx={{ pt: "100%", position: "relative" }}>
          <ProductImgStyle alt={name} src="/static/logo.png" />
        </Box>

        <Stack spacing={2} sx={{ p: 3 }}>
          <Typography variant="subtitle1" noWrap>
            {name}
          </Typography>
          <Stack
            direction="row"
            alignItems="center"
            justifyContent="space-between"
          >
            <Typography variant="subtitle2">
              &nbsp;
              {address}
            </Typography>
          </Stack>
          <Typography variant="subtitle2">
            &nbsp;
            {phoneNumber}
          </Typography>
        </Stack>
      </Link>
    </Card>
  );
}
