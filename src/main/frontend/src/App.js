import React from "react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";

import RootLayout from "./page/root/Root";
import MyPageRootLayout from "./page/root/MyPageRoot";
import Home from "./page/Home";
import Login from "./page/account/Login";
import MyPage from "./page/account/MyPage";
import MyPageModify from "./page/account/MyPageModify";
import Board from "./page/Board";
import Stage from './page/Stage';
import CheckedPwd from "./page/account/CheckedPwd";
import Deposit from "./page/account/Deposit";
import Interest from "./page/account/Interest";
import MemberDelete from "./page/account/MemberDelete";
import Withdraw from "./page/account/Withdraw";
import DetailsInquiry from "./page/account/DetailsInquiry";
import './App.css';


const App = () => {

  const router = createBrowserRouter([
    {
      path: '/',
      element: <RootLayout />,
      children: [
        {
          index: true,
          element: <Home />
        },
        {
          path: 'login',
          element: <Login />
        },
        {
          path: 'mypage',
          element: <MyPageRootLayout />,
          children: [
            {
              path: 'info',
              element: <MyPage />
            },
            {
              path: 'info/modify/:uNo',
              element: <MyPageModify />
            },
            {
              path: 'info/checkedPwd',
              element: <CheckedPwd />
            },
            {
              path: 'info/interest',
              element: <Interest />
            },
            {
              path: 'info/delete',
              element: <MemberDelete />
            },
            {
              path: 'bankAccount/deposit',
              element: <Deposit />
            },
            {
              path: 'bankAccount/withdraw',
              element: <Withdraw />
            },
            {
              path: 'bankAccount/detailsInquiry',
              element: <DetailsInquiry />
            }
          ]
        },
        {
          path: 'board',
          element: <Board />
        },
        {
          path: 'stage',
          element: <Stage />
        }
      ]
    },
  ]);

  return (
    <div className="App">
      <RouterProvider  router={router}/>
    </div>
  );
};

export default App;

