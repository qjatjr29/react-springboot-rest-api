import PropTypes from "prop-types";
// material
import { Grid } from "@mui/material";
import ShopProductCard from "./ManageStoreCard";

// ----------------------------------------------------------------------

ProductList.propTypes = {
  stores: PropTypes.array.isRequired,
};

export default function ProductList({ stores, ...other }) {
  return (
    <Grid container spacing={3} {...other}>
      {stores.map((store) => (
        <Grid key={store.id} item xs={12} sm={6} md={3}>
          <ShopProductCard product={store} />
        </Grid>
      ))}
    </Grid>
  );
}
