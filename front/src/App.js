import { Link, Routes, Route, BrowserRouter } from 'react-router-dom';
import ThemeProvider from './theme';
import './App.css';
import Navbar from './components/Navbar';
import ScrollToTop from './components/ScrollToTop';
import { BaseOptionChartStyle } from './components/chart/BaseOptionChart';
import Router from './components/Router';
import DashboardLayout from './layouts/dashboard/index'
function App() {
  return (  
    <ThemeProvider>
      <ScrollToTop />
      {/* <BaseOptionChartStyle /> */}
      <Router/>
    </ThemeProvider>
  )
}

export default App;
