import Head from "next/head";
import NextLink from "next/link";
import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
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
import { foodApi } from "../../api/api-foods";

const Register = ({ props }) => {
  const { id } = useParams();
  console.log(id);

  const router = useRouter();
  const navigate = useNavigate();

  const CHICKEN_LIST = ["후라이드", "양념", "반반"];
  const PIZZA_LIST = ["쉬림프", "치즈", "콤비네이션"];
  const DESSERT_LIST = ["케이크", "커피", "음료"];
  const RICE_LIST = ["죽", "찌개", "볶음밥"];
  const NOODLE_LIST = ["짜장면", "짬뽕", "냉면"];
  const HAMBURGER_LIST = ["치킨패티", "소고기패티", "감자튀김"];

  const [food, setFood] = useState({
    name: "",
    price: "",
    category: "",
    subCategory: "",
    description: "",
    image: "",
    storeId: "",
  });

  const getCategory = async () => {
    // console.log(storeCategory);
    // console.log(id);
    setFood({ ...food, storeId: id });
    try {
      let category = await storeApi.getStore(id);
      let categoryName = category.data.category;
      //   console.log(categoryName);
      if (categoryName === "CHICKEN") categoryName = "치킨";
      else if (categoryName === "PIZZA") categoryName = "피자";
      else if (categoryName === "RICE") categoryName = "밥";
      else if (categoryName === "NOODLE") categoryName = "면";
      else if (categoryName === "HAMBURGER") categoryName = "햄버거";
      else if (categoryName === "DESSERT") categoryName = "디저트";

      setFood({ ...food, category: categoryName });
    } catch {
      <Page404 />;
    } finally {
      console.log(food.category);
      console.log(food.storeId);
    }
    // setFood({ ...food, category: storeCategory });
  };
  useEffect(() => {
    getCategory();
  }, []);

  const onChangeName = (e) => {
    setFood({ ...food, name: e.target.value });
  };
  const onChangeSubCategory = (e) => {
    setFood({ ...food, subCategory: e.target.value });
  };
  const onChangePrice = (e) => {
    setFood({ ...food, price: e.target.value });
  };
  const onChangeCategory = (e) => {
    setFood({ ...food, category: e.target.value });
  };
  const onChangeImage = (e) => {
    setFood({ ...food, image: e.target.value });
  };
  const onChangeDescription = (e) => {
    setFood({ ...food, description: e.target.value });
  };

  const formik = useFormik({
    initialValues: {
      name: "",
      price: "",
      category: "",
      subCategory: "",
      description: "",
      image: "",
    },
    validationSchema: Yup.object({
      name: Yup.string().max(255).required("name is required"),
      price: Yup.string().max(255).required("price is required"),
      subCategory: Yup.string().max(255).required("subCategory is required"),
      image: Yup.string().max(255).required("Image is required"),
      description: Yup.string().max(255).required("description is required"),
      //   policy: Yup.boolean().oneOf([true], "This field must be checked"),
    }),
  });

  const onSubmit = async () => {
    try {
      console.log(food);
      let result;
      if (food.category === "치킨") result = CHICKEN_LIST[food.subCategory];
      if (food.category === "피자") result = PIZZA_LIST[food.subCategory];
      if (food.category === "밥") result = RICE_LIST[food.subCategory];
      if (food.category === "면") result = NOODLE_LIST[food.subCategory];
      if (food.category === "햄버거") result = HAMBURGER_LIST[food.subCategory];
      if (food.category === "디저트") result = DESSERT_LIST[food.subCategory];
      setFood({ ...food, storeId: id });
      let create = await foodApi.insertFood({
        ...food,
        storeId: id,
        subCategory: result,
      });
      console.log(create);
      navigate(`/manager/stores/${id}`);
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
              Back
            </Button>
          </Link>

          <Box sx={{ my: 3 }}>
            <Typography color="textPrimary" variant="h4">
              Create a new Food
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
            value={food.name}
            variant="outlined"
          />
          <TextField
            fullWidth
            helperText={formik.touched.category && formik.errors.category}
            label="Category"
            margin="normal"
            name="category"
            onBlur={formik.handleBlur}
            onChange={onChangeCategory}
            value={food.category}
            variant="outlined"
          />
          {/* <TextField
            fullWidth
            helperText={formik.touched.subCategory && formik.errors.subCategory}
            label="SubCategory"
            margin="normal"
            name="subCategory"
            onBlur={formik.handleBlur}
            onChange={onChangeSubCategory}
            value={food.subCategory}
            variant="outlined"
          /> */}
          <InputLabel id="demo-simple-select-label">SubCategory</InputLabel>
          {food.category === "치킨" ? (
            <Select
              fullWidth
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              defaultValue=""
              value={food.subCategory}
              label="Category"
              onChange={onChangeSubCategory}
            >
              <MenuItem value={0}>후라이드</MenuItem>
              <MenuItem value={1}>양념</MenuItem>
              <MenuItem value={2}>반반</MenuItem>
            </Select>
          ) : (
            <></>
          )}

          {food.category === "피자" ? (
            <Select
              fullWidth
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              defaultValue=""
              value={food.subCategory}
              label="Category"
              onChange={onChangeSubCategory}
            >
              <MenuItem value={0}>쉬림프</MenuItem>
              <MenuItem value={1}>치즈</MenuItem>
              <MenuItem value={2}>콤비네이션</MenuItem>
            </Select>
          ) : (
            <></>
          )}
          {food.category === "밥" ? (
            <Select
              fullWidth
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              defaultValue=""
              value={food.subCategory}
              label="Category"
              onChange={onChangeSubCategory}
            >
              <MenuItem value={0}>죽</MenuItem>
              <MenuItem value={1}>찌개</MenuItem>
              <MenuItem value={2}>볶음밥</MenuItem>
            </Select>
          ) : (
            <></>
          )}
          {food.category === "면" ? (
            <Select
              fullWidth
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              defaultValue=""
              value={food.subCategory}
              label="Category"
              onChange={onChangeSubCategory}
            >
              <MenuItem value={0}>짜장면</MenuItem>
              <MenuItem value={1}>짬뽕</MenuItem>
              <MenuItem value={2}>냉면</MenuItem>
            </Select>
          ) : (
            <></>
          )}
          {food.category === "햄버거" ? (
            <Select
              fullWidth
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              defaultValue=""
              value={food.subCategory}
              label="Category"
              onChange={onChangeSubCategory}
            >
              <MenuItem value={0}>치킨패티</MenuItem>
              <MenuItem value={1}>소고기패티</MenuItem>
              <MenuItem value={2}>감자튀김</MenuItem>
            </Select>
          ) : (
            <></>
          )}
          {food.category === "디저트" ? (
            <Select
              fullWidth
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              defaultValue=""
              value={food.subCategory}
              label="Category"
              onChange={onChangeSubCategory}
            >
              <MenuItem value={0}>케이크</MenuItem>
              <MenuItem value={1}>커피</MenuItem>
              <MenuItem value={2}>음료</MenuItem>
            </Select>
          ) : (
            <></>
          )}

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
            helperText={formik.touched.price && formik.errors.price}
            label="Price"
            margin="normal"
            name="price"
            onBlur={formik.handleBlur}
            onChange={onChangePrice}
            value={food.price}
            variant="outlined"
          />
          <TextField
            fullWidth
            helperText={formik.touched.image && formik.errors.image}
            label="Image URL"
            margin="normal"
            name="image"
            onBlur={formik.handleBlur}
            onChange={onChangeImage}
            value={food.image}
            variant="outlined"
          />
          <TextField
            fullWidth
            helperText={formik.touched.description && formik.errors.description}
            label="Description"
            margin="normal"
            name="description"
            onBlur={formik.handleBlur}
            onChange={onChangeDescription}
            value={food.description}
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
