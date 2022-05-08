import PropTypes from "prop-types";
// material
import { Grid } from "@mui/material";
import ShopProductCard from "./UserCard";

// ----------------------------------------------------------------------

ProductList.propTypes = {
  users: PropTypes.array.isRequired,
};

export default function ProductList({ users, ...other }) {
  return (
    <Grid container spacing={3} {...other}>
      {users.map((user) => (
        <Grid key={user.id} item xs={12} sm={6} md={3}>
          <ShopProductCard product={user} />
        </Grid>
      ))}
    </Grid>
  );
}
