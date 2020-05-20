import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class DashboardKantorService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getAllComponent() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Dashboard/getAll`)
    }

    async getAllComponentByFilter(data) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Dashboard/getAllByFilter`, 
            {
                params: data
            }
        );
    }

}

export default new DashboardKantorService();