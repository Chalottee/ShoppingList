import React, { Component } from 'react';
import './App.css';
import AccountApp from './component/AccountApp';
import { BrowserRouter as Router} from 'react-router-dom';

class App extends Component {
  render() {
    return (
      <div className="container">
        <Router>
          <AccountApp />
        </Router>
      </div>
    );
  }
}

export default App;