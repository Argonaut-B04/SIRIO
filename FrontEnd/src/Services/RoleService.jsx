import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class RoleService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getRoleList() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Role/getAll`);
    }
}

export default new RoleService();