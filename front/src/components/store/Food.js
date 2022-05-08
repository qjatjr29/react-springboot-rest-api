import { filter } from "lodash";
import { sentenceCase } from "change-case";
import { useState, useEffect } from "react";
import { Link as RouterLink, Link, useNavigate } from "react-router-dom";
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
import Page from "../../components/Page";
import Label from "../../components/Label";
import Scrollbar from "../../components/Scrollbar";
import Iconify from "../../components/Iconify";
import SearchNotFound from "../../components/SearchNotFound";

// import { OrderMoreMenu, OrderListHead } from "../../sections/order";
import { FoodListHead } from "../../sections/stores";
import { ManagerFoodListToolbar } from "../../sections/manager";
// mock
// import USERLIST from '../_mock/user';
import { orderApi } from "../../api/api-order";
import { foodApi } from "../../api/api-foods";
import ContentHeader from "../ManageContentHeader";

// ----------------------------------------------------------------------

const TABLE_HEAD = [
  { id: "name", label: "Name", alignRight: false },
  { id: "category", label: "Category", alignRight: false },
  { id: "type", label: "Type", alignRight: false },
  { id: "price", label: "Price", alignRight: false },
  // { id: "state", label: "상세보기", alignRight: false },
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

export function Foods({ foods, store }) {
  const [page, setPage] = useState(0);

  const navigate = useNavigate();

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
      const newSelecteds = foods.map((n) => n.name);
      setSelected(newSelecteds);
      return;
    }
    setSelected([]);
  };

  const handleDelete = () => {
    try {
      foods.forEach((food) => {
        if (selected.indexOf(food.name) !== -1) {
          foodApi.deleteFood(food.id);
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

  const onNewFood = (id) => {
    navigate(`manager/addFood/${id}`);
  };

  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - foods.length) : 0;

  const filteredUsers = applySortFilter(
    foods,
    getComparator(order, orderBy),
    filterName
  );

  const isUserNotFound = filteredUsers.length === 0;
  return (
    <Page title="음식 목록">
      <Container>
        {/* <Stack
          direction="row"
          alignItems="center"
          justifyContent="space-between"
          mb={5}
        >
          <Typography variant="h4" gutterBottom>
            음식 목록
          </Typography>
          <Link to={{ path: `/manager/addFood/${store.id}` }}>
          <Button
            // onCLick={onNewFood}
            variant="contained"
            component={RouterLink}
            to={{ path: `/manager/addFood/${store.id}` }}
            startIcon={<Iconify icon="eva:plus-fill" />}
          >
            New Food
          </Button>
          </Link>
        </Stack> */}
        <Card>
          {/* <ManagerFoodListToolbar
            numSelected={selected.length}
            filterName={filterName}
            onFilterName={handleFilterByName}
            onDelete={handleDelete}
          /> */}

          <Scrollbar>
            <TableContainer sx={{ minWidth: 800 }}>
              <Table>
                <FoodListHead
                  order={order}
                  orderBy={orderBy}
                  headLabel={TABLE_HEAD}
                  rowCount={foods.length}
                  //   numSelected={selected.length}
                  //   onRequestSort={handleRequestSort}
                  //   onSelectAllClick={handleSelectAllClick}
                />
                <TableBody>
                  {filteredUsers
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => {
                      const { id, image, name, category, subCategory, price } =
                        row;
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
                          {/* <TableCell padding="checkbox">
                            <Checkbox
                              checked={isItemSelected}
                              onChange={(event) => handleClick(event, name)}
                            />
                          </TableCell> */}
                          <TableCell component="th" scope="row" padding="none">
                            <Stack
                              direction="row"
                              alignItems="center"
                              spacing={2}
                            >
                              <Avatar alt={name} src={image} />
                              <Typography variant="subtitle2" noWrap>
                                {name}
                              </Typography>
                            </Stack>
                          </TableCell>
                          <TableCell align="left">{category}</TableCell>
                          <TableCell align="left">{subCategory}</TableCell>
                          <TableCell align="left">{price}</TableCell>

                          {/* <TableCell align="right">
                            <OrderMoreMenu
                              order={row}
                              onOrderDelete={onHandleDelete}
                            />
                          </TableCell> */}
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
            count={foods.length}
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
