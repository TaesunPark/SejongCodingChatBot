import React, { useEffect, useState } from 'react'
import { Route, Router } from 'react-router'
import { Redirect } from 'react-router'
import { connect, useDispatch } from 'react-redux';
import { LOGIN_SUCCESS, LOGIN_FAIL, LOGIN_PENDING, LOGIN_BEFORE, LOGIN_ORIGIN } from '../redux/login/loginTypes';
import { changeToTypeLoginSuccess, changeToTypeLoginPending, changeToTypeLoginBefore } from '../redux/login/loginActions';
import axios from 'axios';
import Root from '../contents/Root';

const UserRoute = ({ loginState, pathname, changeType, component: Component, ...rest }) => {
    console.log(loginState);
    console.log(pathname);

    return (
        <Route {...rest} render={props => {

            if (loginState === LOGIN_SUCCESS) {
                return <Component {...props} />
            }

            else {
                return <Redirect to={{
                    pathname: '/pending',
                    state: { path: {pathname} }
                }} />
            }
        }} />
    );
};

const mapStateToProps = ({ login }) => {
    return {
        loginState: login.type,
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        login: (id, pwd) => dispatch(login(id, pwd)),
        changeType: (type) => dispatch(changeType(type)),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserRoute);
