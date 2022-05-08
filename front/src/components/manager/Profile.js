import {
  Avatar,
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Divider,
  Typography,
} from "@mui/material";

const Profile = ({ user }) => (
  <Card>
    <CardContent>
      <Box
        sx={{ alignItems: "center", display: "flex", flexDirection: "column" }}
      >
        <Avatar src={user.image} sx={{ height: 200, mb: 2, width: 200 }} />
        <Typography color="textPrimary" gutterBottom variant="h5">
          {user.name}
        </Typography>
      </Box>
    </CardContent>
    <Divider />
  </Card>
);

export default Profile;
