import {
    Avatar,
    Box,
    Button,
    Card,
    CardActions,
    CardContent,
    Divider,
    Typography
  } from '@mui/material';
  


const Profile = ({order}) => (
    <Card >
      <CardContent>
        <Box sx={{ alignItems: 'center', display: 'flex', flexDirection: 'column' }} >
          <Avatar src={order.image} sx={{ height: 200, mb: 2, width: 200}}/>
          <Typography color="textPrimary" gutterBottom variant="h5">
            {order.name}
          </Typography>
          
        </Box>
      </CardContent>
      <Divider />
      
    </Card>
  );
 
  export default Profile;