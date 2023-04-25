import React, { Component } from 'react';
import AuthenticationService from '../service/AuthenticationService';

export const MContext = React.createContext();  //exporting context object
class MyProvider extends Component {

state = {isUserLoggedIn: false}
render() {
        this.state.isUserLoggedIn = AuthenticationService.isUserLoggedIn();
        return (
            <MContext.Provider value={
            {   state: this.state,
                setIsUserLoggedIn: (value) => this.setState({
                    isUserLoggedIn: value })}}>
            {this.props.children}   
            </MContext.Provider>)
    }
}

export default MyProvider

//context component, used to pass data from login component to menu component to make it diaplayed