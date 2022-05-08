import * as React from "react";
import { useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Iconify from "../components/Iconify";

const bull = (
  <Box
    component="span"
    sx={{ display: "inline-block", mx: "2px", transform: "scale(0.8)" }}
  >
    •
  </Box>
);

export default function Home() {
  const navigate = useNavigate();
  return (
    <Box
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        height: "100vh",
      }}
    >
      <Card sx={{ minWidth: 275, m: 10 }}>
        <CardContent>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
            사용자 페이지
          </Typography>
          <Typography variant="h5" component="div">
            User
          </Typography>
          <Iconify icon="ant-design:user-outlined" />
        </CardContent>
        <CardActions>
          <Button size="small" onClick={() => navigate("/user/login")}>
            go go!!
          </Button>
        </CardActions>
      </Card>
      <Card sx={{ minWidth: 275, m: 10 }}>
        <CardContent>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
            관리자 페이지
          </Typography>
          <Typography variant="h5" component="div">
            Manager
          </Typography>
          <Iconify icon="ic:baseline-manage-accounts" />
        </CardContent>
        <CardActions>
          <Button size="small" onClick={() => navigate("/manager/login")}>
            go go!!
          </Button>
        </CardActions>
      </Card>
    </Box>
  );
}
