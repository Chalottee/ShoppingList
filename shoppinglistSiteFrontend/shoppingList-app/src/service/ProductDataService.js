import axios from 'axios'

const ACCOUNT = 'bingye'
const PRODUCT_API_URL = 'http://localhost:8080'
// const PASSWORD = 'bingye'
const ACCOUNT_API_URL = PRODUCT_API_URL+'/accounts/'+ACCOUNT

class ProductDataService {

    retrieveAllProducts(name) {
        return axios.get(ACCOUNT_API_URL+'/products');//,
        // { headers: { authorization: 'Basic ' + window.btoa(ACCOUNT + ":" + PASSWORD) } };
    }

    retrieveProduct(name, id) {
        return axios.get(ACCOUNT_API_URL+'/products/'+id);
    }

    deleteProduct(name, id) {
        //console.log('executed service')
        return axios.delete(ACCOUNT_API_URL+'/products/'+id);
    }

    updateProduct(name, id, product) {
        return axios.put(ACCOUNT_API_URL+'/products/'+id, product);
    }
  
    createProduct(name, product) {
        return axios.post(ACCOUNT_API_URL+'/products', product);
    }
}

export default new ProductDataService()