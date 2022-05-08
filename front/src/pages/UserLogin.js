import { Link as RouterLink, useNavigate } from "react-router-dom";
// @mui
import { styled } from "@mui/material/styles";
import { Card, Link, Container, Typography } from "@mui/material";
// hooks
import useResponsive from "../hooks/useResponsive";
// components
import Page from "../components/Page";
import Logo from "../components/Logo";
// sections
import { LoginForm } from "../sections/auth/login";
import AuthSocial from "../sections/auth/AuthSocial";

import { userApi } from "../api/api-user";
import { useEffect, useState } from "react";

// ----------------------------------------------------------------------

const RootStyle = styled("div")(({ theme }) => ({
  [theme.breakpoints.up("md")]: {
    display: "flex",
  },
}));

const HeaderStyle = styled("header")(({ theme }) => ({
  top: 0,
  zIndex: 9,
  lineHeight: 0,
  width: "100%",
  display: "flex",
  alignItems: "center",
  position: "absolute",
  padding: theme.spacing(3),
  justifyContent: "space-between",
  [theme.breakpoints.up("md")]: {
    alignItems: "flex-start",
    padding: theme.spacing(7, 5, 0, 7),
  },
}));

const SectionStyle = styled(Card)(({ theme }) => ({
  width: "100%",
  maxWidth: 464,
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  margin: theme.spacing(2, 0, 2, 2),
}));

const ContentStyle = styled("div")(({ theme }) => ({
  maxWidth: 480,
  margin: "auto",
  minHeight: "100vh",
  display: "flex",
  justifyContent: "center",
  flexDirection: "column",
  padding: theme.spacing(12, 0),
}));

// ----------------------------------------------------------------------

export default function Login() {
  const smUp = useResponsive("up", "sm");

  const mdUp = useResponsive("up", "md");

  const [email, setEmail] = useState(null);
  const [password, setPassword] = useState(null);
  const [user, setUser] = useState(null);

  const navigate = useNavigate();

  const handleLoginCheck = async (email, password) => {
    try {
      setEmail(email);
      setPassword(password);
    } catch {
      console.log("실패");
    }
  };

  const getData = async (email, password) => {
    let data;
    try {
      data = await userApi.login(email, password);
    } catch {
      console.log("로그인 실패");
      window.location.reload();
    } finally {
      if (data) {
        sessionStorage.clear();
        sessionStorage.setItem("user", data.data);
        navigate("/user");
      }
    }
  };

  useEffect(() => {
    if (email !== null) {
      getData(email, password);
    }
  }, email);

  return (
    <Page title="Login">
      <RootStyle>
        <HeaderStyle>
          <Logo />

          {smUp && (
            <Typography variant="body2" sx={{ mt: { md: -2 } }}>
              Don’t have an account? {""}
              <Link
                variant="subtitle2"
                component={RouterLink}
                to="/user/register"
              >
                Get started
              </Link>
            </Typography>
          )}
        </HeaderStyle>

        {mdUp && (
          <SectionStyle>
            <Typography variant="h3" sx={{ px: 5, mt: 10, mb: 5 }}>
              Hi, Welcome Back
            </Typography>
            <img
              src="https://p16-sg.tiktokcdn.com/obj/tiktok-obj/0b9c0d2726f523c44ca4dc08fc5eb0ae"
              alt="login"
            />
          </SectionStyle>
        )}

        <Container maxWidth="sm">
          <ContentStyle>
            <Typography variant="h4" gutterBottom>
              Sign in
            </Typography>

            <Typography sx={{ color: "text.secondary", mb: 5 }}>
              Enter your details
            </Typography>

            {/* <AuthSocial /> */}

            <LoginForm onLogin={handleLoginCheck} />

            {!smUp && (
              <Typography variant="body2" align="center" sx={{ mt: 3 }}>
                Don’t have an account?{" "}
                <Link variant="subtitle2" component={RouterLink} to="/register">
                  Get started
                </Link>
              </Typography>
            )}
          </ContentStyle>
        </Container>
      </RootStyle>
    </Page>
  );
}
