import React, { Component } from 'react';
import { Routes, Route } from 'react-router-dom';
import LoginComponent from './LoginComponent';
import LogoutComponent from './LogoutComponent';
import ProductComponent from './ProductComponent';
import MenuComponent from './MenuComponent';
import ListProductsComponent from './ListProductsComponent';
import MyProvider from './MyProvider';

class AccountApp extends Component {
    render() {
        return (
            <>
                <MyProvider>
                    <MenuComponent />
                    <Routes>
                        <Route path="/" element={<LoginComponent />} />
                        <Route path="/login" element={<LoginComponent />} />
                        <Route path="/logout" element={<LogoutComponent />} />
                        <Route path="/products" element={<ListProductsComponent />} />
                        <Route path="/products/:id" element={<ProductComponent />} />
                    </Routes>
                </MyProvider>
            </>
        )
    }
}

export default AccountApp;