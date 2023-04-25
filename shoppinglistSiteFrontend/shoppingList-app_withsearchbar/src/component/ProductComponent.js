import React, { Component } from 'react';
import withRouter from './withRouter';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import ProductDataService from '../service/ProductDataService';
const ACCOUNT = 'bingye'

class ProductComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            title: '',
            description: '',
            price:'',
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    componentDidMount() {
        console.log(this.state.id)
        
        debugger
        if (this.state.id === -1) {
            return
        }
        ProductDataService.retrieveProduct(ACCOUNT, this.state.id)
            .then(response => this.setState({
                title: response.data.title,
                description: response.data.description,
                price: response.data.price
            })).catch(error => {
                
                return error;
            })

    }

    validate(values) {
        let errors = {}
        if (!values.description) {
            errors.description = 'Enter a Description'
        } else if (values.description.length < 5) {
            errors.description = 'Enter at least 5 Characters in Description'
        } 

        return errors
    }

    onSubmit(values) {
        let username = ACCOUNT

        let product = {
            id: this.state.id,
            title: values.title,
            description: values.description,
            price: values.price,
        }

        if (this.state.id === -1) {
            ProductDataService.createProduct(username, product)
                .then(() => this.props.navigation('/products'), console.log(`Create product successfully`))
                .catch(error => {  
                    return error;
                })
        } else {
            ProductDataService.updateProduct(username, this.state.id, product)
                .then(() => this.props.navigation('/products'), console.log(`Update product ${this.state.id} successfully`))
                .catch(error => {
                    return error;
                })
        }

        console.log(values);
    }

    render() {
        let { title, description, price, id } = this.state
        return (
            <div>
                <h3>Product</h3>
                <div data-testid="productContainer" className="container">
                    <Formik
                        initialValues={{ id, title, description, price }}
                        onSubmit={this.onSubmit}
                        validateOnChange={true}
                        validateOnBlur={true}
                        validate={this.validate}
                        enableReinitialize={true}>
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="description" component="div"
                                        className="alert alert-warning" />
                                    <fieldset className="form-group">
                                        <label>Id</label>
                                        <Field data-testid="productId" className="form-control" type="text" name="id" disabled />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Title</label>
                                        <Field data-testid="productTitle" className="form-control" type="text" name="title" />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Description</label>
                                        <Field data-testid="productDescription" className="form-control" type="text" name="description" />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Price</label>
                                        <Field data-testid="productPrice" className="form-control" type="text" name="price" />
                                    </fieldset>
                                    
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }
}

export default withRouter(ProductComponent);