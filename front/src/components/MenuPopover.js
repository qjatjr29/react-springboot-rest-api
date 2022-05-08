import PropTypes from 'prop-types';
// material
import { Popover } from '@mui/material';
import { alpha, styled } from '@mui/material/styles';
import { useEffect } from 'react';
// import { userApi } from '../api/api-user';

// ----------------------------------------------------------------------

const ArrowStyle = styled('span')(({ theme }) => ({
  [theme.breakpoints.up('sm')]: {
    top: -7,
    zIndex: 1,
    width: 12,
    right: 20,
    height: 12,
    content: "''",
    position: 'absolute',
    borderRadius: '0 0 4px 0',
    transform: 'rotate(-135deg)',
    background: theme.palette.background.paper,
    borderRight: `solid 1px ${alpha(theme.palette.grey[500], 0.12)}`,
    borderBottom: `solid 1px ${alpha(theme.palette.grey[500], 0.12)}`,
  },
}));

// ----------------------------------------------------------------------

MenuPopover.propTypes = {
  children: PropTypes.node.isRequired,
  sx: PropTypes.object,
};

export default function MenuPopover({ children, sx, ...other }) {

  // async function login(){
  //   let result;
  //   try {
  //     ({data: result} = await userApi.login())
  //   } catch (error) {
  //     // alert("제대로 보내셈");
  //   } finally {
  //     console.log(result);
  //   }
  // }


  useEffect( () => {
    // login();
  }, [])
  return (
    <Popover
      anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
      transformOrigin={{ vertical: 'top', horizontal: 'right' }}
      PaperProps={{
        sx: {
          p: 1,
          width: 200,
          overflow: 'inherit',
          ...sx,
        },
      }}
      {...other}
    >
      <ArrowStyle className="arrow" />

      {children}
    </Popover>
  );
}
