import { Box, Typography, Button } from "@mui/material";
import Iconify from "./Iconify";
import { Link as RouterLink, Link } from "react-router-dom";
function ContentHeader(props) {
  const { title, text, type, store } = props;

  if (type === "user") {
    return (
      <Link
        to="/manager/addUser"
        style={{ textDecoration: "none", color: "black" }}
      >
        <Box sx={{ display: "flex" }}>
          <Typography variant="h4" sx={{ mb: 5 }}>
            {title}
          </Typography>
          <Button
            variant="contained"
            style={{ marginLeft: "auto" }}
            startIcon={<Iconify icon="ant-design:plus-circle-filled" />}
            size="large"
          >
            {text}
          </Button>
        </Box>
      </Link>
    );
  } else if (type === "store") {
    return (
      <Link
        to="/manager/addStore"
        style={{ textDecoration: "none", color: "black" }}
      >
        <Box sx={{ display: "flex" }}>
          <Typography variant="h4" sx={{ mb: 5 }}>
            {title}
          </Typography>
          <Button
            variant="contained"
            style={{ marginLeft: "auto" }}
            startIcon={<Iconify icon="ant-design:plus-circle-filled" />}
            size="large"
            color="warning"
          >
            <Link
              to="/manager/addStore"
              style={{ textDecoration: "none", color: "black" }}
            >
              {text}
            </Link>
          </Button>
        </Box>
      </Link>
    );
  } else if (type === "food") {
    return (
      <Link
        to={{ pathname: `/manager/addFood/${store.storeId}` }}
        style={{ textDecoration: "none", color: "black" }}
      >
        <Box sx={{ display: "flex" }}>
          <Typography variant="h4" sx={{ mb: 5 }}>
            {title}
          </Typography>
          <Button
            style={{ marginLeft: "auto" }}
            variant="contained"
            startIcon={<Iconify icon="ant-design:plus-circle-filled" />}
            size="large"
          >
            {text}
          </Button>
        </Box>
      </Link>
    );
  }
}
export default ContentHeader;
