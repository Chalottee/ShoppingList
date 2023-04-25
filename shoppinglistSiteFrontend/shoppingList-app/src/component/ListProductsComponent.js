import React, { Component } from 'react';
import withRouter from './withRouter';
import ProductDataService from '../service/ProductDataService';
import AuthenticationService from '../service/AuthenticationService';

const ACCOUNT = 'bingye';

class ListProductsComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            products: [],
            message: null
        }

        this.refreshProducts = this.refreshProducts.bind(this)
        this.deleteProductClicked = this.deleteProductClicked.bind(this)
        this.updateProductClicked = this.updateProductClicked.bind(this)
        this.addProductClicked = this.addProductClicked.bind(this)
        
    }



    componentDidMount() {
        this.refreshProducts();
    }

    addProductClicked() {
        //TODO refactor/improve this
        this.props.navigation("/products/-1")
    }

    updateProductClicked(id) {
        console.log('update ' + id)
        this.props.navigation(`/products/${id}`)
    }

    refreshProducts() {
        ProductDataService.retrieveAllProducts(ACCOUNT)//HARDCODED
            .then(
                response => {
                    console.log(response);
                    this.setState({ products: response.data })
                }
            ).catch(error => {
                //TODO better handle errors https://axios-http.com/docs/handling_errors
                return error;
            })
    }

    deleteProductClicked(id) {
        ProductDataService.deleteProduct(ACCOUNT, id)
            .then(
                response => {
                    this.setState({ message: `Delete of product ${id} Successful` })
                    this.refreshProducts()
                }
            ).catch(error => {
                //TODO better handle errors
                return error;
            })

    }

    render() {

        if (!AuthenticationService.isUserLoggedIn()) {
            this.props.navigation('/login')
        } else {
            return (
                

                <div className="container">
                    <h3>All Products</h3>
                    {this.state.message && <div class="alert alert-success">{this.state.message}</div>}
                    <div className="container">
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Title</th>
                                    <th>Description</th>
                                    <th>Price</th>
                                    <th>Update</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.products.map(
                                        product =>
                                            <tr key={product.id}>
                                                <td>{product.id}</td>
                                                <td>{product.title}</td>
                                                <td>{product.description}</td>
                                                <td>{product.price}</td>
                                                <td><button data-testid="updateButton" className="btn btn-success" onClick={() => this.updateProductClicked(product.id)}>Update</button></td>
                                                <td><button data-testid="deleteButton" className="btn btn-warning" onClick={() => {if (window.confirm('Are you sure you wish to delete this item?')) this.deleteProductClicked(product.id)}}>Delete</button></td>
                                            </tr>
                                    )
                                }
                            </tbody>
                        </table>
                    </div>
                    <div className="row">
                        <button className="btn btn-success" onClick={this.addProductClicked}>Add</button>
                    </div>
                </div>
            )


        }

        
    }
}

export default withRouter(ListProductsComponent);