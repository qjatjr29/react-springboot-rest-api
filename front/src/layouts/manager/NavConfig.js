// component
import Iconify from "../../components/Iconify";

// ----------------------------------------------------------------------

const getIcon = (name) => <Iconify icon={name} width={22} height={22} />;

const navConfig = [
  {
    title: "manager",
    path: "/manager/info",
    icon: getIcon("bxs:user"),
  },
  // {
  //   title: '카테고리 관리',
  //   path: '/user/category',
  //   icon: getIcon('bxs:category'),
  // },
  {
    title: "가게 관리",
    path: "/manager/stores",
    icon: getIcon("bx:store-alt"),
  },
  {
    title: "고객 관리",
    path: "/manager/users",
    icon: getIcon("ant-design:shopping-cart-outlined"),
  },
];

export default navConfig;
