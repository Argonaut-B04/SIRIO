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

    async getAllBM() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Employee/getAllBranchManager`);
    }

    async getAllQAOfficer() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Employee/getAllQAOfficer`);
    }

    async getAllQAOfficerDD() {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Employee/getAllQAOfficerDD`);
    }

    async checkEmployeeExist(username) {
        return this.axiosInstance.get(SirioAxiosBase.BASEURL + `/Employee/check/username/` + username);
    }

    async checkEmailExist(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Employee/check/email`, data);
    }

    async deleteEmployee(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Employee/hapus`, data)
    }

    async downloadEmployee() {
        return window.location.href = SirioAxiosBase.BASEURL + '/export/employee';
    }

    async changePassword(data) {
        return this.axiosInstance.post(SirioAxiosBase.BASEURL + `/Employee/ubahPassword`, data)
    }
}

export default new EmployeeService();