import Head from "next/head";
import NextLink from "next/link";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useRouter } from "next/router";
import { useFormik } from "formik";
import * as Yup from "yup";
import {
  Box,
  Button,
  Checkbox,
  Container,
  FormHelperText,
  Link,
  TextField,
  Typography,
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { userApi } from "../../api/api-user";
import Page404 from "../Page404";

const Register = () => {
  const router = useRouter();
  const navigate = useNavigate();

  const [user, setUser] = useState({
    name: "",
    address: "",
    phoneNumber: "",
    email: "",
    password: "",
  });

  const onChangeName = (e) => {
    setUser({ ...user, name: e.target.value });
  };
  const onChangeAddress = (e) => {
    setUser({ ...user, address: e.target.value });
  };
  const onChangePhoneNumber = (e) => {
    setUser({ ...user, phoneNumber: e.target.value });
  };
  const onChangeEmail = (e) => {
    setUser({ ...user, email: e.target.value });
  };
  const onChangePassword = (e) => {
    setUser({ ...user, password: e.target.value });
  };

  const formik = useFormik({
    initialValues: {
      email: "",
      address: "",
      loginId: "",
      password: "",
      phoneNumber: "",
      policy: false,
    },
    validationSchema: Yup.object({
      email: Yup.string()
        .email("Must be a valid email")
        .max(255)
        .required("Email is required"),
      name: Yup.string().max(255).required("name is required"),
      address: Yup.string().max(255).required("Last name is required"),
      phoneNumber: Yup.string().max(255).required("Last name is required"),
      password: Yup.string().max(255).required("Password is required"),
      //   policy: Yup.boolean().oneOf([true], "This field must be checked"),
    }),
    // onSubmit: async () => {
    //   try {
    //     console.log(formik.values.password);
    //     let user = await userApi.insertUser(
    //       formik.values.name,
    //       formik.values.address,
    //       formik.values.phoneNumber,
    //       formik.values.email,
    //       formik.values.password
    //     );
    //     console.log("test");
    //     console.log(user);
    //     navigate("manager/users");
    //   } catch {
    //     <Page404 />;
    //   }
    // },
  });

  const onSubmit = async () => {
    try {
      console.log(formik.values.name);
      let users = await userApi.insertUser(user);
      console.log(users);
      navigate("/manager/users");
    } catch {
      //   <Page404 />;
    }
  };

  return (
    <>
      <Box
        component="main"
        sx={{
          alignItems: "center",
          display: "flex",
          flexGrow: 1,
          minHeight: "100%",
        }}
      >
        <Container maxWidth="sm">
          <Link href="/manager/users" passHref>
            <Button
              component="a"
              startIcon={<ArrowBackIcon fontSize="small" />}
            >
              User List
            </Button>
          </Link>
          <Box sx={{ my: 3 }}>
            <Typography color="textPrimary" variant="h4">
              Create a new user
            </Typography>
            <Typography color="textSecondary" gutterBottom variant="body2">
              Use your email to create a new user
            </Typography>
          </Box>
          <TextField
            fullWidth
            helperText={formik.touched.name && formik.errors.name}
            label="Name"
            margin="normal"
            name="name"
            onBlur={formik.handleBlur}
            onChange={onChangeName}
            value={user.name}
            variant="outlined"
          />
          <TextField
            fullWidth
            helperText={formik.touched.address && formik.errors.address}
            label="Address"
            margin="normal"
            name="address"
            onBlur={formik.handleBlur}
            onChange={onChangeAddress}
            value={user.address}
            variant="outlined"
          />
          <TextField
            fullWidth
            //   helperText={
            //     formik.touched.phoneNumber && formik.errors.phoneNumber
            //   }
            label="Phone Number"
            margin="normal"
            name="phoneNumber"
            onBlur={formik.handleBlur}
            type="password"
            onChange={onChangePhoneNumber}
            value={user.phoneNumber}
            variant="outlined"
          />
          <TextField
            fullWidth
            helperText={formik.touched.email && formik.errors.email}
            label="Email"
            margin="normal"
            name="email"
            onBlur={formik.handleBlur}
            type="email"
            onChange={onChangeEmail}
            value={user.email}
            variant="outlined"
          />
          <TextField
            fullWidth
            helperText={formik.touched.password && formik.errors.password}
            label="Password"
            margin="normal"
            name="password"
            onBlur={formik.handleBlur}
            onChange={onChangePassword}
            value={user.password}
            type="password"
            variant="outlined"
          />
          {/* <Box
            sx={{
              alignItems: "center",
              display: "flex",
              ml: -1,
            }}
          >
            <Checkbox
              checked={formik.values.policy}
              name="policy"
              onChange={formik.handleChange}
            />
            <Typography color="textSecondary" variant="body2">
              I have read the{" "}
              <NextLink href="#" passHref>
                <Link color="primary" underline="always" variant="subtitle2">
                  Terms and Conditions
                </Link>
              </NextLink>
            </Typography>
          </Box>
          {Boolean(formik.touched.policy && formik.errors.policy) && (
            <FormHelperText error>{formik.errors.policy}</FormHelperText>
          )} */}
          <Box sx={{ py: 2 }}>
            <Button
              color="primary"
              onClick={onSubmit}
              //   disabled={formik.isSubmitting}
              // disabled={false}
              fullWidth
              size="large"
              type="submit"
              variant="contained"
            >
              등록
            </Button>
          </Box>
          {/* <Typography color="textSecondary" variant="body2">
              Have an account?{" "}
              <NextLink href="/login" passHref>
                <Link variant="subtitle2" underline="hover">
                  Sign In
                </Link>
              </NextLink>
            </Typography> */}
        </Container>
      </Box>
    </>
  );
};

export default Register;
