import Head from "next/head";
import NextLink from "next/link";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import FormControl from "@mui/material/FormControl";
import { useRouter } from "next/router";
import { useFormik } from "formik";
import * as Yup from "yup";
import {
  Box,
  Button,
  Checkbox,
  Container,
  FormHelperText,
  InputLabel,
  Select,
  MenuItem,
  Link,
  MenuList,
  TextField,
  Typography,
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { userApi } from "../../api/api-user";
import { storeApi } from "../../api/api-store";
import Page404 from "../Page404";

const Register = () => {
  const router = useRouter();
  const navigate = useNavigate();

  const [store, setStore] = useState({
    name: "",
    address: "",
    phoneNumber: "",
    category: "",
    image: "",
  });

  const [selected, setSelected] = useState("");
  const onChangeName = (e) => {
    setStore({ ...store, name: e.target.value });
  };
  const onChangeAddress = (e) => {
    setStore({ ...store, address: e.target.value });
  };
  const onChangePhoneNumber = (e) => {
    setStore({ ...store, phoneNumber: e.target.value });
  };
  const CATEGORY_LIST = ["치킨", "피자", "밥", "면", "햄버거", "카페"];

  const onChangeCategory = (e) => {
    // console.log(ca, CATEGORY_LIST[e.target.value]);
    setStore({ ...store, category: e.target.value });
    // setSelected(ca);
  };

  const getCategory = (idx) => {
    return CATEGORY_LIST[idx];
  };
  const onChangeImage = (e) => {
    setStore({ ...store, image: e.target.value });
  };

  console.log(store);
  const formik = useFormik({
    initialValues: {
      name: "",
      address: "",
      phoneNumber: "",
      category: "",
      image: "",
    },
    validationSchema: Yup.object({
      address: Yup.string()
        .email("Must be a valid email")
        .max(255)
        .required("Address is required"),
      name: Yup.string().max(255).required("name is required"),
      phoneNumber: Yup.string().max(255).required("PhoneNumber is required"),
      image: Yup.string().max(255).required("Image is required"),
      category: Yup.string().max(255).required("Category is required"),
      //   policy: Yup.boolean().oneOf([true], "This field must be checked"),
    }),
  });

  const onSubmit = async () => {
    try {
      let ca = CATEGORY_LIST[store.category];
      console.log(store);

      let create = await storeApi.insertStore({ ...store, category: ca });
      console.log(create);
      navigate("/manager/stores");
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
          <Link href="/manager/stores" passHref>
            <Button
              component="a"
              startIcon={<ArrowBackIcon fontSize="small" />}
            >
              Store List
            </Button>
          </Link>
          <Box sx={{ my: 3 }}>
            <Typography color="textPrimary" variant="h4">
              Create a new store
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
            value={store.name}
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
            value={store.address}
            variant="outlined"
          />
          <TextField
            fullWidth
            helperText={formik.touched.phoneNumber && formik.errors.phoneNumber}
            label="Phone Number"
            margin="normal"
            name="phoneNumber"
            onBlur={formik.handleBlur}
            onChange={onChangePhoneNumber}
            value={store.phoneNumber}
            variant="outlined"
          />
          <InputLabel id="demo-simple-select-label">Category</InputLabel>

          <Select
            fullWidth
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={store.category}
            // defaultValue={category}
            label="Category"
            onChange={onChangeCategory}
          >
            <MenuItem value={0}>치킨</MenuItem>
            <MenuItem value={1}>피자</MenuItem>
            <MenuItem value={2}>밥</MenuItem>
            <MenuItem value={3}>면</MenuItem>
            <MenuItem value={4}>햄버거</MenuItem>
            <MenuItem value={5}>카페</MenuItem>
          </Select>

          {/* <Select
            labelId="demo-simple-select-label"
            fullWidth
            id="demo-simple-select"
            value={store.category}
            label="Category"
            onChange={onChangeCategory}
          >
            <MenuItem value={0}>치킨</MenuItem>
            <MenuItem value={1}>피자</MenuItem>
            <MenuItem value={2}>백반/죽</MenuItem>
            <MenuItem value={3}>국수/면</MenuItem>
            <MenuItem value={4}>햄버거</MenuItem>
            <MenuItem value={5}>카페</MenuItem>
          </Select> */}
          {/* <TextField
            fullWidth
            helperText={formik.touched.category && formik.errors.category}
            label="Category"
            margin="normal"
            name="category"
            onBlur={formik.handleBlur}
            onChange={onChangeCategory}
            value={store.category}
            variant="outlined"
          /> */}
          <TextField
            fullWidth
            helperText={formik.touched.image && formik.errors.image}
            label="Image URL"
            margin="normal"
            name="image"
            onBlur={formik.handleBlur}
            onChange={onChangeImage}
            value={store.image}
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
