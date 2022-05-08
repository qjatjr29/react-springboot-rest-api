import { useRef, useState } from "react";
import { Link as RouterLink, Link, useNavigate } from "react-router-dom";
// material
import {
  Menu,
  Box,
  MenuItem,
  IconButton,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
// component
import Iconify from "../../components/Iconify";
import orderApi from "../../api/api-order";

// ----------------------------------------------------------------------

export default function OrderMoreMenu(props) {
  console.log("ordermore", props);
  const { user, onOrderSubmit, onUserEdit, onUserDelete } = props;
  const navigate = useNavigate();
  const ref = useRef(null);
  const [isOpen, setIsOpen] = useState(false);
  const handleOnDelete = (e) => {
    onUserDelete(user.userId);
  };
  const handleOnEdit = (e) => {
    onUserEdit(user.userId);
  };
  console.log("hhhdd", user);
  return (
    <>
      <IconButton ref={ref} onClick={() => setIsOpen(true)}>
        <Iconify icon="eva:more-vertical-fill" width={20} height={20} />
      </IconButton>

      <Menu
        open={isOpen}
        anchorEl={ref.current}
        onClose={() => setIsOpen(false)}
        PaperProps={{
          sx: { width: 200, maxWidth: "100%" },
        }}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
        transformOrigin={{ vertical: "top", horizontal: "right" }}
      >
        <MenuItem sx={{ color: "text.secondary" }}>
          <Link
            to={{ pathname: `/manager/users/delete/${user.userId}` }}
            color="inherit"
            underline="hover"
            component={RouterLink}
          >
            <Box display="flex">
              <ListItemIcon>
                <Iconify icon="eva:trash-2-outline" width={24} height={24} />
              </ListItemIcon>
              <ListItemText
                primary="Delete"
                onClick={handleOnDelete}
                primaryTypographyProps={{ variant: "body2" }}
              />
            </Box>
          </Link>
        </MenuItem>

        <MenuItem
          component={RouterLink}
          to="#"
          sx={{ color: "text.secondary" }}
        >
          <Link
            to={{ pathname: `/manager/users/edit/${user.userId}` }}
            color="inherit"
            underline="hover"
            component={RouterLink}
          >
            <Box display="flex">
              <ListItemIcon>
                <Iconify icon="eva:edit-fill" width={24} height={24} />
              </ListItemIcon>
              <ListItemText
                primary="Edit"
                // onClick={handleOnEdit}
                primaryTypographyProps={{ variant: "body2" }}
              />
            </Box>
          </Link>
        </MenuItem>

        {/* <MenuItem component={RouterLink} to="#" sx={{ color: 'text.secondary' }}>
          <ListItemIcon>
            <Iconify icon="eva:edit-fill" width={24} height={24} />
          </ListItemIcon>
          <ListItemText primary="order" primaryTypographyProps={{ variant: 'body2' }} />
        </MenuItem> */}
      </Menu>
    </>
  );
}
