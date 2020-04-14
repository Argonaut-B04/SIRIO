import axios from 'axios';
import SirioAxiosBase from './SirioAxiosBase';
import AuthenticationService from './AuthenticationService';

class EmployeeService {

    constructor() {
        this.axiosInstance = axios.create();
        this.axiosInstance.defaults.headers.common['Authorization'] = AuthenticationService.getToken();
    }

    async getEmployeeList() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Employee/getAll`);
    }

    async addEmployee(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Employee/tambah', data)
    }

    async editEmployee(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + '/Employee/ubah', data)
    }

    async getEmployee(id) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Employee/` + id);
    }

    async checkEmployeeExist(username) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Employee/check/` + username);
    }

    async deleteEmployee(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Employee/hapus`, data)
    }
}

export default new EmployeeService();