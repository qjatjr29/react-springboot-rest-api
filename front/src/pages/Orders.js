import { filter } from "lodash";
import { sentenceCase } from "change-case";
import { useState, useEffect } from "react";
import { Link as RouterLink, Link } from "react-router-dom";
// material
import {
  Card,
  Table,
  Stack,
  Avatar,
  Button,
  Checkbox,
  TableRow,
  TableBody,
  TableCell,
  Container,
  Typography,
  TableContainer,
  TablePagination,
} from "@mui/material";
// components
import Page from "../components/Page";
import Label from "../components/Label";
import Scrollbar from "../components/Scrollbar";
import Iconify from "../components/Iconify";
import SearchNotFound from "../components/SearchNotFound";
import {
  UserListHead,
  UserListToolbar,
  UserMoreMenu,
} from "../sections/@dashboard/user";
import { OrderMoreMenu, OrderListHead } from "../sections/order";
// mock
// import USERLIST from '../_mock/user';
import { orderApi } from "../api/api-order";

// ----------------------------------------------------------------------

const TABLE_HEAD = [
  { id: "name", label: "Name", alignRight: false },
  { id: "address", label: "Address", alignRight: false },
  { id: "phone", label: "Phone", alignRight: false },
  { id: "price", label: "Price", alignRight: false },
  { id: "state", label: "상세보기", alignRight: false },
  { id: "" },
];

// ----------------------------------------------------------------------

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === "desc"
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function applySortFilter(array, comparator, query) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  if (query) {
    return filter(
      array,
      (_user) => _user.name.toLowerCase().indexOf(query.toLowerCase()) !== -1
    );
  }
  return stabilizedThis.map((el) => el[0]);
}

const useOrderList = () => {
  const [orderList, setOrderList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [userId, setUserId] = useState("");

  const getData = async () => {
    try {
      let orders = await orderApi.getUserOrderLists(userId);
      setOrderList(orders.data);
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    let userId = sessionStorage.getItem("user");
    setUserId(userId);
  }, []);

  useEffect(() => {
    if (userId !== "") {
      getData();
    }
  }, [userId]);
  console.log(userId, "hhh");
  return { orderList, loading };
};

export default function Orders() {
  const { orderList, loading } = useOrderList();

  console.log(orderList);

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState("asc");

  const [selected, setSelected] = useState([]);

  const [orderBy, setOrderBy] = useState("name");

  const [filterName, setFilterName] = useState("");

  const [rowsPerPage, setRowsPerPage] = useState(5);

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelecteds = orderList.map((n) => n.name);
      setSelected(newSelecteds);
      return;
    }
    setSelected([]);
  };

  const handleDelete = () => {
    try {
      orderList.forEach((order) => {
        if (selected.indexOf(order.name) !== -1) {
          orderApi.deleteOrder(order.id);
        }
      });
    } catch (e) {
      alert("서버 장애");
      console.error(e);
    } finally {
      alert("삭제!");
      window.location.reload();
    }
  };

  const onHandleDelete = (id) => {
    console.log(id);
    try {
      orderApi.deleteOrder(id);
    } catch (e) {
      alert("서버 장애");
      console.error(e);
    } finally {
      alert("삭제!");
      window.location.reload();
    }
  };
  const handleClick = (event, name) => {
    const selectedIndex = selected.indexOf(name);
    let newSelected = [];
    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, name);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1)
      );
    }
    setSelected(newSelected);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleFilterByName = (event) => {
    setFilterName(event.target.value);
  };

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - orderList.length) : 0;

  const filteredUsers = applySortFilter(
    orderList,
    getComparator(order, orderBy),
    filterName
  );

  const isUserNotFound = filteredUsers.length === 0;

  return (
    <Page title="주문 내역">
      <Container>
        <Stack
          direction="row"
          alignItems="center"
          justifyContent="space-between"
          mb={5}
        >
          <Typography variant="h4" gutterBottom>
            주문 내역
          </Typography>
          {/* <Button variant="contained" component={RouterLink} to="#" startIcon={<Iconify icon="eva:plus-fill" />}>
                New User
            </Button> */}
        </Stack>

        <Card>
          <UserListToolbar
            numSelected={selected.length}
            filterName={filterName}
            onFilterName={handleFilterByName}
            onDelete={handleDelete}
          />

          <Scrollbar>
            <TableContainer sx={{ minWidth: 800 }}>
              <Table>
                <OrderListHead
                  order={order}
                  orderBy={orderBy}
                  headLabel={TABLE_HEAD}
                  rowCount={orderList.length}
                  numSelected={selected.length}
                  onRequestSort={handleRequestSort}
                  onSelectAllClick={handleSelectAllClick}
                />
                <TableBody>
                  {filteredUsers
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => {
                      const {
                        id,
                        name,
                        address,
                        phoneNumber,
                        price,
                        orderItems,
                        avatarUrl,
                        orderStatus,
                        isVerified,
                      } = row;
                      const isItemSelected = selected.indexOf(name) !== -1;

                      return (
                        <TableRow
                          hover
                          key={id}
                          tabIndex={-1}
                          role="checkbox"
                          selected={isItemSelected}
                          aria-checked={isItemSelected}
                        >
                          <TableCell padding="checkbox">
                            <Checkbox
                              checked={isItemSelected}
                              onChange={(event) => handleClick(event, name)}
                            />
                          </TableCell>
                          <TableCell component="th" scope="row" padding="none">
                            <Stack
                              direction="row"
                              alignItems="center"
                              spacing={2}
                            >
                              <Avatar alt={name} src={avatarUrl} />
                              <Typography variant="subtitle2" noWrap>
                                {name}
                              </Typography>
                            </Stack>
                          </TableCell>
                          <TableCell align="left">{address}</TableCell>
                          <TableCell align="left">{phoneNumber}</TableCell>
                          <TableCell align="left">{price}</TableCell>
                          <TableCell align="left">
                            <Label
                              variant="ghost"
                              color={
                                (orderStatus === "banned" && "error") ||
                                "success"
                              }
                            >
                              {sentenceCase(orderStatus)}
                            </Label>
                            {/* <Link to={{ pathname : `/user/orderLists/${id}`}} color="inherit" underline="hover" component={RouterLink}>Click</Link> */}
                          </TableCell>

                          <TableCell align="right">
                            <OrderMoreMenu
                              order={row}
                              onOrderDelete={onHandleDelete}
                            />
                          </TableCell>
                        </TableRow>
                      );
                    })}
                  {emptyRows > 0 && (
                    <TableRow style={{ height: 53 * emptyRows }}>
                      <TableCell colSpan={6} />
                    </TableRow>
                  )}
                </TableBody>

                {isUserNotFound && (
                  <TableBody>
                    <TableRow>
                      <TableCell align="center" colSpan={6} sx={{ py: 3 }}>
                        <SearchNotFound searchQuery={filterName} />
                      </TableCell>
                    </TableRow>
                  </TableBody>
                )}
              </Table>
            </TableContainer>
          </Scrollbar>

          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={orderList.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Card>
      </Container>
    </Page>
  );
}
