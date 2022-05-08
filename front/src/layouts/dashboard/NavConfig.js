// component
import Iconify from '../../components/Iconify';

// ----------------------------------------------------------------------

const getIcon = (name) => <Iconify icon={name} width={22} height={22} />;

const navConfig = [
  {
    title: 'user',
    path: '/user/info',
    icon: getIcon('bxs:user'),
  },
  {
    title: '카테고리',
    path: '/user/category',
    icon: getIcon('bxs:category'),
  },
  {
    title: '가게 목록',
    path: '/user/stores',
    icon: getIcon('bx:store-alt'),
  },
  {
    title: '전체 음식',
    path: '/user/foods',
    icon: getIcon('fluent:food-16-filled'),
  },
  {
    title: '장바구니',
    path: '/user/orderLists',
    icon: getIcon('ant-design:shopping-cart-outlined'),
  },
  {
    title: '주문내역',
    path: '/user/orders',
    icon: getIcon('clarity:list-line'),
  },

];

export default navConfig;
